package com.example.servicebook;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServiceController {

	@GetMapping("services")
	public ModelAndView getServices(@RequestParam String envName) throws SSLException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("environments",getEnvironmentData(envName));
		mv.setViewName("serviceView");
		return mv;
	}

	@Autowired
	private WebService webService;

	private List<Environment> getDataFromEnvironments(String envName) throws SSLException{
		return webService.getDataFromEnvironments(envName);	
	}

	private List<Environment> getEnvironmentData(String envName){
		
		List<String> rsUrls = new ArrayList<>();
		rsUrls.add("res/room");
		rsUrls.add("res/rate");
		EnvironmentService serv = new EnvironmentService();
		serv.setName("Room");
		serv.setEndpoints(rsUrls);
		List<String> rs1Urls = new ArrayList<>();
		rs1Urls.add("res1/room");
		rs1Urls.add("res1/rate");
		EnvironmentService serv1 = new EnvironmentService();
		serv1.setName("Room1");
		serv1.setEndpoints(rs1Urls);
		List<EnvironmentService>  servs = new ArrayList<>();
		servs.add(serv);
		servs.add(serv1);
		Environment env = new Environment();
		env.setName(envName);
		env.setDomain(Static_Environment.valueOf(envName).getDomain());
		env.setServices(servs);
		env.setErrorMsg(Static_Environment.valueOf(envName).getDomain());
		List<Environment> envs = new ArrayList<>();
		envs.add(env);
		return envs;
	}


}
