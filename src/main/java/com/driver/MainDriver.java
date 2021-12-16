package com.driver;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import java.util.Scanner;
//import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.dao.LoginDAO;
import com.model.Customer;
import com.model.Employee;
import com.transaction.MoneyTransactions;
import com.util.Connect2SQL;

public class MainDriver {
	//sprivate Map<String, Customer> accts = new HashMap<String, Customer>();
	
	
	public static boolean isOpen = true;
	public final static Logger lg = Logger.getLogger(MainDriver.class); //logger
	static PrintLogo pl = new PrintLogo(); // logo + colors
	static Employee em = new Employee();
	static Scanner sc = new Scanner(System.in);
	static Customer cs = new Customer();

	
	
	
	public static void main(String[] args) throws Exception{

		while(isOpen) {
			pl.printLogo(); //logo			
			try(Connection conn = Connect2SQL.getConnection()){ //connect to DB	
				lg.info("connection to logins database is successful"); //write to log if successful			
				//LoginDAO interacts with logins table in DB, needs connection
				LoginDAO logindao = new LoginDAO(conn);
				
				cs = logindao.loginOrReg(); //login/register menu returns customer if logged in, null if not
				if(cs == null) { //if login was unsuccessful...
					System.out.println("logged out");
				}else { //if logged in
					MoneyTransactions money = new MoneyTransactions(); //transactions menu instance
					money.transMenu(cs); //transactions menu method
				}			
			}isOpen = isUser(); //check if there is a customer present, stay open or close bank
		}
	}
	
	public static boolean isUser() { //is there a customer?
		System.out.println("Is there a customer? y/n");
		String ans = sc.next().toLowerCase();
		if(ans.equals("y") || ans.equals("yes") ||ans.equals("yasss"))  {
			return true; //bank stays open, loginOrReg() runs again
		}else {
			System.out.println("The bank is now closing. Please come again soon!");
			sc.close();
			return false; //bank closes
		}
	}
}


