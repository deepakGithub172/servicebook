package com.example.servicebook;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentService {

	private String name;
	private List<String> endpoints = new ArrayList<>();
	private String errorMsg;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getEndpoints() {
		return endpoints;
	}
	public void setEndpoints(List<String> endpoints) {
		this.endpoints = endpoints;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/*
	 * public String getEndpointDisplay() { if(errorMsg != null) return errorMsg;
	 * String endpoints = ""; for (String endpoint : this.endpoints) { endpoints +=
	 * endpoint +"\r\n"; } return endpoints;
	 * 
	 * }
	 */

}
