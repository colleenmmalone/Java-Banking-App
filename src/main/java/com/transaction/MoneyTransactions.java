package com.transaction;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import com.dao.AccountDAO;
import com.driver.PrintLogo;
import com.model.DeleteMe;
import com.model.AccountInfo;
import com.model.Customer;
import com.util.Connect2SQL;

public class MoneyTransactions {
	static Connection conn ;
	AccountDAO accountdao = new AccountDAO(conn);
	Customer cs = new Customer();
	static Scanner sc = new Scanner(System.in); //create new scanner object
	static Logger lg = Logger.getLogger(Connect2SQL.class.getName()); //create logger
	int menu;
	Set<AccountInfo> myAccts = new HashSet<AccountInfo>();
	long acct;
	String user1;
	String user2;
	double balance;
	double trns;
	String description;
	String status;
	boolean loggedIn, isOwner;
	static PrintLogo pl = new PrintLogo(); 
		
	
	public void transMenu(Customer cs) throws Exception {
		loggedIn = true;
		while(loggedIn) {
		try(Connection conn = Connect2SQL.getConnection()){
			lg.info("connection to accounts database is successful"); //write to logg if successful			
			//DAO for connection with accounts DB
			AccountDAO accountdao = new AccountDAO(conn);
		
		//display current users accounts
		myAccts = accountdao.showMyAccts(cs);
		System.out.println("\tAccount #\tOwner(s)\t\t\tBalance\t\tDescription\tStatus");
		System.out.println(myAccts);
		System.out.println("1.Deposit\n2.Withdraw\n3.Transfer\n4.Open Account\n5.Close Account\n6.Logout\nPlease select an option");//menu		
		try{
			menu = sc.nextInt();
		}catch (InputMismatchException e) {
			lg.error("A non-integer was entered");
			System.out.println("Please enter a number: 1-5");
			return;
		}catch (NoSuchElementException e1) {
			e1.printStackTrace();			
		}
	    switch (menu) {//execute menu choice
	        case 1:	System.out.println("Which account will you deposit to?");
	        		acct = Long.parseLong(sc.next());
	        		System.out.println("Enter deposit amount:");
	        		trns = Double.parseDouble(sc.next());
	        		isOwner = isOwner(cs.getEml(), acct);
	        		if(isOwner) {
	        			deposit(acct, trns);
	        		}else {
	        			System.out.println(pl.ANSI_RED+"You cannot access this account. Please review your inputs and try again"+pl.ANSI_RESET);
	        		}       			        			        		
	        		break;
	        case 2:	System.out.println("Which account will you withdraw from?");
    				acct = Long.parseLong(sc.next());
    				System.out.println("Enter withdrawal amount:");
    				trns = Double.parseDouble(sc.next());
    				isOwner = isOwner(cs.getEml(), acct);
	        		if(isOwner) {
	        			withdraw(acct, trns);
	        		}else {
	        			System.out.println(pl.ANSI_RED+"You cannot access this account. Please review your inputs and try again"+pl.ANSI_RESET);
	        		}    				
	        		break;
	        case 3:	System.out.println("Which account will you transfer from? (source account)");
    				long acctS = Long.parseLong(sc.next());
    				System.out.println("Which account will you transfer to? (destination account)");
    				long acctD = Long.parseLong(sc.next());
    				System.out.println("Enter transfer amount:");
    				trns = Double.parseDouble(sc.next());
    				isOwner = isOwner(cs.getEml(), acctS);
	        		if(isOwner) {
	        			transfer(acctS, acctD, trns);
	        		}else {
	        			System.out.println(pl.ANSI_RED+"You cannot access this account. Please review your inputs and try again"+pl.ANSI_RESET);
	        		}     				
	        		break;
	        case 4:	createAcct(cs);
	        		break;
	        case 5:System.out.println("Which account would you like to close?");
	        		break;	        	
	        case 6:	System.out.println("Have a nice day! Please come again soon");	        		
	        		loggedIn = false;
	        		break;
	        default:System.out.println(pl.ANSI_RED+"That is not a valid option. Please try again"+pl.ANSI_RESET);
	        		break;	        
	    }   
		
		}
	
		}
		return;
	}
	
	private boolean isOwner(String eml, long acct) {
		String DBuser1;
		String DBuser2;
		try {
			DBuser1 = accountdao.retrieveUser1(acct);	        			        			
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve users");
			return false;
		}
		try {
			DBuser2 = accountdao.retrieveUser2(acct);	        			
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve users");
			return false;
		}
		if(DBuser2 == null) {
			if(DBuser1.equals(eml)){   
				return true;
			}
		}else {
			if(eml.equals(DBuser1) || eml.equals(DBuser2)){    
				return true;
		}else {
			System.out.println(pl.ANSI_RED+"You are not a registered user of this account."+pl.ANSI_RESET);
			lg.warn("Cannot access this account due to permission");
			return false;
		}	
		}
		return false;
	}

	public void adDeposit() throws FileNotFoundException, SQLException, IOException {
		//loggedIn = true;
		//while(loggedIn) {
			try(Connection conn = Connect2SQL.getConnection()){
				lg.info("connection to accounts database is successful"); //write to logg if successful			
				//DAO for connection with accounts DB
				AccountDAO accountdao = new AccountDAO(conn);
				System.out.println("Which account will you deposit to?");
        		acct = Long.parseLong(sc.next());
        		System.out.println("Enter deposit amount:");
        		trns = Double.parseDouble(sc.next());
        		deposit(acct, trns);        		       
	    	}   		
		//}
			return;
	}
	public void adWithdraw() throws FileNotFoundException, SQLException, IOException {
		loggedIn = true;
		//while(loggedIn) {
			try(Connection conn = Connect2SQL.getConnection()){
				lg.info("connection to accounts database is successful"); //write to logg if successful			
				//DAO for connection with accounts DB
				AccountDAO accountdao = new AccountDAO(conn);
				System.out.println("Which account will you withdraw from?");
				acct = Long.parseLong(sc.next());
				System.out.println("Enter withdrawal amount:");
				trns = Double.parseDouble(sc.next());
				withdraw(acct, trns);      		       
	    	}   		
		//}
			return;
	}
	
	
	public void adTransfer() throws FileNotFoundException, SQLException, IOException {
		loggedIn = true;
		//while(loggedIn) {
			try(Connection conn = Connect2SQL.getConnection()){
				lg.info("connection to accounts database is successful"); //write to logg if successful			
				//DAO for connection with accounts DB
				AccountDAO accountdao = new AccountDAO(conn);
				System.out.println("Which account will you transfer from? (source account)");
				long acctS = Long.parseLong(sc.next());
				System.out.println("Which account will you transfer to? (destination account)");
				long acctD = Long.parseLong(sc.next());
				System.out.println("Enter transfer amount:");
				trns = Double.parseDouble(sc.next());
				transfer(acctS, acctD, trns);     		       
	    	}   		
		//}
			return;
	}
	
	
	
	public void deposit(long acct, double deposit) {
		double oldB = 0;
		String stat = "";
		try {
			stat = accountdao.retrieveStatus(acct);			
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve status");
			return;
		}
		if(stat.equals("OPEN") ) {
			try {
				oldB = accountdao.retrieveBalance(acct);
			} catch (SQLException e) {
				System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
				lg.error("unable to retrieve balance");
				return;
			}
			try {
				accountdao.updateBalance(acct, oldB+deposit);
			} catch (PSQLException e) {
				lg.info("$"+deposit+" was added to account "+acct+" for a new balance of $"+ (deposit+oldB));
				System.out.println(pl.ANSI_GREEN+"deposit performed successfully!"+pl.ANSI_RESET);
			} catch (SQLException e) {
				System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
				lg.error("unable to update balance");
				return;
			}catch (Exception e) {
				System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
				lg.error("exception");
				return;
			}		
			return;
		}else{
			System.out.println(pl.ANSI_RED+"Your account is "+stat+". You cannot perform transactions at this time"+pl.ANSI_RESET);
			lg.warn("Account "+acct+" is "+stat+". No transactions can be performed");
			return;
		}
	}
	
	public MoneyTransactions() {
		super();
	}

	public void withdraw(long acct, double withdrawal) {
		double oldB = 0;
		String stat = null;
		try {
			stat = accountdao.retrieveStatus(acct);
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve status");
			return;
		}		
		if(stat.equals("OPEN") ) {
			try {
				oldB = accountdao.retrieveBalance(acct);
			} catch (SQLException e) {
				System.out.println("could not perform function. Please try again");
				lg.error("unable to retrieve balance");
				return;
			}
			if(oldB >= withdrawal) {
				try {
					accountdao.updateBalance(acct, oldB-withdrawal);
				} catch (PSQLException e) {
					lg.info("$"+withdrawal+" was withdrawn from account "+acct+" for a new balance of $"+ (oldB-withdrawal));
					System.out.println(pl.ANSI_GREEN+"deposit performed successfully!"+pl.ANSI_RESET);
				} catch (SQLException e) {
					System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
					lg.error("unable to update balance");
					return;
				}			
				return;
			}else {
				System.out.println(pl.ANSI_RED+"You don't have enough money to perform this withdrawal. Please review your inputs and try again"+pl.ANSI_RESET);
			}
		}else{
			System.out.println(pl.ANSI_RED+"Your account is "+stat+". You cannot perform transactions at this time"+pl.ANSI_RESET);
			lg.warn("Account "+acct+" is "+stat+". No transactions can be performed");
			return;
		}
	}
	
	public void transfer(long src, long dest, double transfer) {
		String statS = null;
		String statD = null;
		double oldBS = 0;
		double oldBD = 0;
		try {
			statS = accountdao.retrieveStatus(src);
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve status");
			return;
		}		
		try {
			statD = accountdao.retrieveStatus(dest);
		} catch (SQLException e1) {
			System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
			lg.error("unable to retrieve status");
			return;
		}	
		if (statS.equals("OPEN") && statD.equals("OPEN")) {
			try {
				oldBS = accountdao.retrieveBalance(src);
			} catch (SQLException e) {
				System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
				lg.error("unable to retrieve balance of "+src);
				return;
			}
			try {
				oldBD = accountdao.retrieveBalance(dest);
			} catch (SQLException e) {
				System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
				lg.error("unable to retrieve balance of "+ dest);
				return;
			}
			if(oldBS >= transfer) {
				try {
					accountdao.updateBalance(src, oldBS-transfer);
				} catch (PSQLException e) {
					lg.info(pl.ANSI_GREEN+"$"+transfer+" was withdrawn from account "+src+" for a new balance of $"+ (oldBS-transfer)+pl.ANSI_RESET);
					System.out.println(pl.ANSI_GREEN+"withdrawal performed successfully!"+pl.ANSI_RESET);
					try {
						accountdao.updateBalance(dest, oldBD+transfer);
					} catch (PSQLException e1) {
						lg.info(pl.ANSI_GREEN+"$"+transfer+" was added to account "+dest+" for a new balance of $"+ (oldBD+transfer)+pl.ANSI_RESET);
						System.out.println(pl.ANSI_GREEN+"deposit perform successfully!"+pl.ANSI_RESET);
					} catch (SQLException e1) {
						System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
						lg.error("unable to update balance");
						return;
					}	
					
				} catch (SQLException e) {
					System.out.println(pl.ANSI_RED+"could not perform function. Please try again"+pl.ANSI_RESET);
					lg.error("unable to perform transfer");
					return;
				}			
				return;
			}else {
				System.out.println(pl.ANSI_RED+"Source account does not have enough money to perform transfer. Please review your inputs and try again"+pl.ANSI_RESET);
			}
		}
		
		
		
		
			return;	
	}
	
	public void createAcct(Customer cs) throws Exception {	
		user1 = cs.getEml();
		System.out.println("Let's set up an account\nWill this be a joint account?");		
		String ans = sc.next().toLowerCase();
		if(ans.equals("y") || ans.equals("yes") ||ans.equals("yasss"))  {
			System.out.println("enter the email address for the second user");
			user2 = sc.next();
		}else {
			user2 = null;
		}
		System.out.println("Enter the starting balance (enter 0 if there is none)");
		balance = sc.nextDouble();
		System.out.println("Is this a Checking or Savings account?");
		description = sc.next();
		if(user2 != null) {
			description = "Joint "+description;
		}
		status = "PENDING";
		
		acct = newAcctNum();
		AccountInfo ac = new AccountInfo(acct, user1, user2, balance, description, status);

		accountdao.addAcct(ac);
		return;
	}
	
	public static void closeAccount(long acct) throws SQLException{
		PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET status = 'CLOSED' WHERE acct = "+ acct);
		PreparedStatement statement2 = conn.prepareStatement("UPDATE accounts SET balance = "+0+" WHERE acct = "+ acct);
		try {
		statement.executeQuery();
		}catch(PSQLException e) {	
			lg.info("Account "+acct+" status was changed to CLOSED");
			
		}
		try {
			statement2.executeQuery();
			}catch(PSQLException e) {	
				lg.info("the remaining balance was returned to the owner(s)");
				
			}
		return; //return account balance
	}
	
	public long newAcctNum() throws Exception{
		try(Connection conn = Connect2SQL.getConnection()){ //connect to DB	
			long mx = 0;
			PreparedStatement statement;
			statement = conn.prepareStatement("SELECT MAX(acct) FROM accounts");
			try {
				ResultSet results = statement.executeQuery();
				while(results.next()) {
		 		mx=Long.parseLong(results.getString("max"));
				}
			
			return mx + 1;
			
			}catch (Exception e) { 
				 e.printStackTrace();
			}
					
		} catch (Exception e) {
			e.printStackTrace();
		} 

		return 0;
		
		
		
	}
}
