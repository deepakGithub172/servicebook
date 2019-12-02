package com.example.servicebook;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ServiceController {

	@RequestMapping("services")
	public ModelAndView getServices() throws SSLException {
		ModelAndView mv = new ModelAndView();
		mv.addObject("environments",getEnvironmentData());
		mv.setViewName("serviceView");
		return mv;
	}

	@Autowired
	private WebService webService;

	private List<Environment> getDataFromEnvironments() throws SSLException{
		return webService.getDataFromEnvironments();	
	}

	private List<Environment> getEnvironmentData(){

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
		env.setName("Test");
		env.setDomain("10.123.12");
		env.setServices(servs);
		env.setErrorMsg("error");
		List<Environment> envs = new ArrayList<>();
		envs.add(env);
		return envs;
	}


}
