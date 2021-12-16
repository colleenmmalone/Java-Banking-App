package com.model;

public class LoginInfo {
	String first_name;
	String last_name;
	String email;
	String pswd;
	String id;
	
	public LoginInfo(String first_name, String last_name, String email, String pswd) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.pswd = pswd;
		this.id = "CUSTOMER";
	}
	
	public LoginInfo(String pswd) {
		this.pswd = pswd;
	}

	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPswd() {
		return pswd;
	}
	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	public String getID() {
		return id;
	}
	
	@Override
	public String toString() {
		int l = pswd.length() + 1;
		String pswdS = "";
		for(int i = 0; i < l ; i++ ) {
			pswdS = pswdS + "*";
		}
		return "\nLogin Credentials: " + first_name + " " + last_name + ", " + email + " "+ pswdS;
	}

}
