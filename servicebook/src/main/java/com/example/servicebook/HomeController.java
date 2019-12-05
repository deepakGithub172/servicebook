package com.example.servicebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("/home")
	public ModelAndView getHomePage() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("environments", Static_Environment.values());
		mv.setViewName("home");
		return mv;
	}

}
