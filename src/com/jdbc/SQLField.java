package com.jdbc;

public class SQLField {
	private String name;
	private String value;
	private String type;
	private String fieldType;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public SQLField(String name, String type, String fieldType) {
		super();
		this.name = name;
		this.type = type;
		this.fieldType = fieldType;
	}
	
}
