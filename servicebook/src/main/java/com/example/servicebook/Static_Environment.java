package com.example.servicebook;

public enum Static_Environment {

	DEV1("https://10.224.211.191:9980"),
	DEV2("https://10.224.211.192:9980"),
	TST1("https://10.224.211.193:9980"),
	TST2("https://10.224.211.194:9980"),
	UAT1("https://10.224.211.195:9980"),
	UAT2("https://10.224.211.196:9980");

	private String domain;

	private Static_Environment(String domain) {
		this.domain = domain;
	}

	public String getDomain() {
		return domain;
	}
}
