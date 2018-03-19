package com.jdbc;

import java.util.Map;

public class SQLMethod {
	private String methodName;
	private Map<String,SQLField> params;
	private String type;
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Map<String, SQLField> getParams() {
		return params;
	}
	public void setParams(Map<String, SQLField> params) {
		this.params = params;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SQLMethod(String methodName, Map<String, SQLField> params, String type) {
		super();
		this.methodName = methodName;
		this.params = params;
		this.type = type;
	}
	
}
