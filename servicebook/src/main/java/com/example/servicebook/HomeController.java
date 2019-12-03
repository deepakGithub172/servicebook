package com.example.servicebook;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public ModelAndView getHomePage() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("environments",getEnvironments());
		mv.setViewName("home");
		return mv;
	}
	
	private List<String> getEnvironments(){
		//TODO--
		 return null;
	}
}
