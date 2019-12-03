package com.example.servicebook;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServicebookApplication {

	private static final String HOME_PAGE = "http://localhost:8080/home";
	public static void main(String[] args) throws IOException {
		SpringApplication.run(ServicebookApplication.class, args);
		openHomePage();
	}

	private static void openHomePage() throws IOException {
		Runtime rt = Runtime.getRuntime();
		rt.exec("rundll32 url.dll,FileProtocolHandler " + HOME_PAGE);
	}
}

