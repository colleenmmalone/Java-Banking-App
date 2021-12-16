package com.practice;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Maps {
	
	//get
	// put
	

	 public static void main(String args[]){  
		 Map<String,Dude> map = new HashMap<String,Dude>();
		 
		 Scanner sc = new Scanner(System.in);
		 System.out.println("Enter email");
		 String eml = sc.nextLine();
		 System.out.println("Enter password");
		 String pswd = sc.nextLine();
		 
		 Dude d = new Dude(eml, pswd);
		 map.put(eml, d);
		 
		 System.out.println(map.get(eml));	 
		 
		 
		 System.out.println("Enter email");
		 String eml2 = sc.nextLine();
		 System.out.println("Enter password");
		 String pswd2 = sc.nextLine();
		 
		 if(map.get(eml2).getPassword().equals(pswd2)) {
			 System.out.println("You have successfully logged in!");
		 }else {
			 System.out.println("Invalid password");
		 }
		 
		 
		 
		 sc.close();
		 
	 }
		 
	 
		 
		 
		 
		 
		 
//		 Dude d = new Dude("admin@hopo.com", "pass123");
//		 Map<String,Dude> map = new HashMap<String,Dude>();
//		 map.put("admin@hopo.com", d);
//	  
//	  	Dude passCheck = map.get("admin@hopo.com");
//	  	String str = passCheck.getPassword();
//	  	System.out.println(str);
//	  	
//	  
//	  	if (passCheck.getPassword().equals("pass123")) {
//	  		System.out.println("The password matches!");
//	  	}else {
//	  		System.out.println("You have entered a wrong password. Would you like to\n1.Try again\n2. Change password");
//	  	}
	 

	

}
