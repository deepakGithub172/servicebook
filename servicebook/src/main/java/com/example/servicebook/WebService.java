package com.example.servicebook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Service
public class WebService {


	private static final String DOMAIN = "https://10.224.211.191:9980";
	private static final String BODY = "{\"email\":\"api_admin@marriott.com\",\"password\":\"s@ummer1!\"}";

	public WebClient createWebClient() throws SSLException {
		SslContext sslContext = SslContextBuilder
				.forClient()
				.trustManager(InsecureTrustManagerFactory.INSTANCE)
				.build();
		HttpClient httpClient = HttpClient.create().secure( t -> t.sslContext(sslContext));

		return WebClient.builder().clientConnector(new ReactorClientHttpConnector(httpClient)).build();
	}

	public List<Environment> getDataFromEnvironments(String envName) throws SSLException{
		WebClient builder = createWebClient();
		List<Environment> environments = new ArrayList<>();
		//TODO--for loop for multiple environments
		environments.add(getDataFromEnvironment(builder, envName));
		return environments;
	}

	public Environment getDataFromEnvironment(WebClient builder, String envName) {

		Static_Environment static_environment = Static_Environment.valueOf(envName);
		Environment environment = new Environment();
		environment.setName(static_environment.name());
		environment.setDomain(static_environment.getDomain());
		try {
			loginToPortal(builder);
			JSONObject allAPIsObj = getAllAPIS(builder);

			List<EnvironmentService> services = new ArrayList<>();
			if(allAPIsObj != null) {
				JSONObject channel = (JSONObject) allAPIsObj.get("channel");
				JSONArray items = channel.getJSONArray("item");
				StringBuilder sb = new StringBuilder();

				for(int i = 0; i < items.length(); i++) {

					EnvironmentService service = new EnvironmentService();
					String serviceName = ((JSONObject)items.get(i)).getString("title");
					service.setName(serviceName);
					sb.append("Service Name: " + serviceName + "\n");
					String guid = getGUID(items.get(i));
					String latestVersionId = getLatestVersionId(builder, guid);
					try {
						String detailedResult = builder.get().uri(DOMAIN+"/api/apis/versions/"+ latestVersionId + "/implementations/Live")
								.header("cache-control", "no-cache")
								.accept(MediaType.APPLICATION_JSON)
								.retrieve().bodyToMono(String.class).block();

						JSONObject detailedResultObj = getJsonObjFromStr(detailedResult);
						JSONObject endPointsObj = (JSONObject) detailedResultObj.get("Endpoints");
						JSONArray endpointArr = (JSONArray) endPointsObj.get("Endpoint");
						String baseURL = (String) ((JSONObject)endpointArr.get(0)).get("Uri");
						JSONObject apiBindingsObj = (JSONObject) detailedResultObj.get("APIBindings");
						JSONArray apiBindingArr = (JSONArray) apiBindingsObj.get("APIBinding");
						JSONObject bindingOperationsObj = (JSONObject) ((JSONObject) apiBindingArr.get(0)).get("BindingOperations");
						JSONArray bindingOperationArr = (JSONArray) bindingOperationsObj.get("BindingOperation");
						List<String> endpoints = new ArrayList<>();
						for(int j = 0; j < bindingOperationArr.length(); j++) {
							JSONObject httpOperationObj = (JSONObject) ((JSONObject) bindingOperationArr.get(j)).get("HttpOperation");
							String operationType = (String) httpOperationObj.get("Method");
							String operationPath = (String) httpOperationObj.get("Path");
							String endPoint = operationType + " " +baseURL+operationPath + getQueryParamIfAny(httpOperationObj) + "\n";
							sb.append(endPoint);
							endpoints.add(endPoint);
						}
						service.setEndpoints(endpoints);
					}catch(Throwable e) {
						service.setErrorMsg(e.getMessage());
					}
					services.add(service);
				}
				writeToFile(sb);
				environment.setServices(services);
			}
			
		}catch (Throwable e) {
			environment.setErrorMsg(e.getMessage());
		}
		return environment;
	}

	private JSONObject getAllAPIS(WebClient builder) throws Exception{
		try {
			String allApiResult = builder.get().uri(DOMAIN+"/api/apis")
					.header("cache-control", "no-cache")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve().bodyToMono(String.class).block();
			System.out.print(allApiResult);
			return getJsonObjFromStr(allApiResult);
		} catch (Exception e){
			throw new Exception("Excepition in getAllAPIS: " + e.getMessage());
		}
	}

	private String getQueryParamIfAny(JSONObject httpOperationObj) throws Exception {
		try {	
			String queryParms = "";
			JSONArray inputArr = (JSONArray) httpOperationObj.get("Input");
			for(int i = 0; i < inputArr.length(); i++) {
				JSONObject inputObj = (JSONObject) ((JSONObject)inputArr.get(i));
				if(((String)inputObj.get("Type")).equals("query")) {
					if(queryParms == "")
						queryParms = "?"+ ((String)inputObj.get("Name"));
					else
						queryParms += "/" + ((String)inputObj.get("Name"));
				}
			}
			return queryParms;
		} catch (JSONException e){
			throw new Exception("Excepition in getQueryParamIfAny: " + e.getMessage());
		}
	}

	private String getGUID(Object item) throws Exception {
		try {
			JSONObject itemObj = (JSONObject) item;
			JSONObject guidObj = (JSONObject) itemObj.get("guid");
			String guid = (String) guidObj.get("value");
			return guid;
		} catch (JSONException e){
			throw new Exception("Excepition in getGUID: " + e.getMessage());
		}
	}

	private String getLatestVersionId(WebClient builder, String guid) throws Exception {
		try {
			String apiDetailResult = builder.get().uri(DOMAIN+"/api/apis/"+ guid)
					.header("cache-control", "no-cache")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve().bodyToMono(String.class).block();
			JSONObject apiDetailObj = getJsonObjFromStr(apiDetailResult);
			String latestVersionId = (String) apiDetailObj.get("LatestVersionID");
			return latestVersionId;
		} catch (JSONException e){
			throw new Exception("Excepition in getLatestVersionId: " + e.getMessage());
		}
	}

	private JSONObject getJsonObjFromStr(String str) throws Exception {
		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(str);
		} catch (JSONException e) {
			throw new Exception("Excepition in getJsonObjFromStr: " + e.getMessage());
		}
		return jsonObj;
	}

	private void writeToFile(StringBuilder sb) throws IOException {
		File f = new File("result.json");
		if(!f.exists())
			f.createNewFile();
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream os = new ObjectOutputStream(fos);
		os.writeObject(sb.toString());
	}

	private void loginToPortal(WebClient builder)  {

		RequestHeadersSpec<?> spec = builder.post().uri(DOMAIN+ "/api/login").header("cache-control", "no-cache")
				.contentType(MediaType.APPLICATION_JSON).bodyValue(BODY)
				.accept(MediaType.APPLICATION_JSON);
		String loginResult = spec.retrieve()
				.onStatus(HttpStatus :: is4xxClientError, 
						response -> response.bodyToMono(String.class).map(body -> new Exception(body)))
				.onStatus(HttpStatus :: is5xxServerError, 
						response -> response.bodyToMono(String.class).map(body -> new Exception("Server Error : "+body)))
				.bodyToMono(String.class).block();

	}


}
