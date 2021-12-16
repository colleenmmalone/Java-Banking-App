package com.practice;

public class Dude {
	private String email;
	private String password;
	


	public Dude(String eml, String pswd) {
		this.email = eml;
		this.password = pswd;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "An account was created with the email: " + email;
	}
	
	

}
