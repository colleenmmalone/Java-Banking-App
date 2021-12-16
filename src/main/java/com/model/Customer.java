package com.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String eml;
	private String pswd;

	private List<Account> accts = new ArrayList<Account>();
	//private static List<Account> blank = new ArrayList<Account>();

	//private List<DeleteMe> accts = new ArrayList<DeleteMe>();

	
	public Customer() {		
	}
	


	public Customer(String firstName, String lastName, String eml, String pswd, List<Account> accts) {

		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.eml = eml;
		this.pswd = pswd;
		this.accts = accts;
		System.out.println("Welcome, "+firstName+"! Your account was successfully created under "+eml);
	}

//	public Customer(String firstName, String lastName, String eml, String pswd) {
//		this(firstName, lastName, eml, pswd);
//	}
	

	
	
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public List<Account> getAccts() {
		return accts;
	}
	public void setAccts(List<Account> accts) {
		this.accts = accts;
	}
	
	
	@Override
	public String toString() {
		return "Hello, "+firstName + ". Your default login is "+eml;
	}

}
