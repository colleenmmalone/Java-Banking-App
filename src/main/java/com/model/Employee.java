package com.model;

public class Employee {
	
	private String name;
	private String eml;
	private String pswd;
	
	public Employee() {		
	}
	
	public Employee(String name, String eml, String pswd) {
		super();
		this.name = name;
		this.eml = eml;
		this.pswd = pswd;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEml() {
		return eml;
	}
	public void setEml(String eml) {
		this.eml = eml;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	

	@Override
	public String toString() {
		return "Employee: " + name + ": " + eml;
	}

}
