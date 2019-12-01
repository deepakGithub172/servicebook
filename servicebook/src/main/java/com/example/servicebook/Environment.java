package com.example.servicebook;

import java.util.ArrayList;
import java.util.List;

public class Environment {

	private String name;
	private String domain;
	private List<EnvironmentService> services = new ArrayList<>();
	private String errorMsg;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public List<EnvironmentService> getServices() {
		return services;
	}
	public void setServices(List<EnvironmentService> services) {
		this.services = services;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
