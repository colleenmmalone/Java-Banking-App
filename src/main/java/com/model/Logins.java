package com.model;

import java.util.Scanner;

public class Logins {
	
	Scanner sc = new Scanner(System.in);
	
	public void login() {

		System.out.println("Enter your Email");
		String eml = sc.nextLine();
		System.out.println("Enter your Password");
		String pswd = sc.nextLine();
		//login(eml, pswd);
		System.out.println("Login successful");
		

}
}
