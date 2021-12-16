package com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//import org.apache.log4j.Level;
import org.apache.log4j.Logger;


public class Connect2SQL {
	
	//connect to logger file
	public final static Logger lg = Logger.getLogger(Connect2SQL.class.getName());
	
	//method to get connection
	public static Connection getConnection() throws SQLException, FileNotFoundException, IOException {
		
			//this is the GOOD way to do it. I'll have to do it when i have my cpu back
			FileInputStream propInput = new FileInputStream("/Users/briarrose/Dropbox/Revature/Project0/application.properties"); //file name
			Properties props = new Properties(); //new properties object
			props.load(propInput); //read properties file at given "address"
			
			String url = (String) props.getProperty("URL"); //saving the info from .properties file
			String username = (String) props.getProperty("username");
			String password = (String) props.getProperty("password");

			return DriverManager.getConnection(url, username, password); //return a connection
	}
	
	
	
	
	
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			
				
				//retrieving data from SQL database and printing first name
				//Set<LoginInfo> loginset = logindao.getAllFirstName();
				
//				for(LoginInfo L : loginset) {
//					System.out.println("First name: "+L.getFirst_name());
//				}
				
//				for(LoginInfo L : loginset) {			
//					if(L.getEmail().equals("minnie@hopo.com")) {
//						System.out.println("It's Minnie");
//						if(L.getPswd().equals("pass2345")) {
//							System.out.println("Login was successful");
//						}else {
//							System.out.println("Invalid password. Try again");
//						}
//					}
//				}

					//String isUser = logindao.matchPass();

					

				
			
			

			
			
		
		
	
	
	
	
	
	
	

}
