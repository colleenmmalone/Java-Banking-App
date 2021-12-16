package com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.log4j.Logger;
import org.postgresql.util.PSQLException;

import com.model.AccountInfo;
import com.model.Customer;
import com.model.LoginInfo;
import com.util.Connect2SQL;

public class AccountDAO {
	static Connection conn;
	static Logger lg = Logger.getLogger(Connect2SQL.class.getName()); //create logger
	Customer cs;
	AccountInfo ac;
	static long acct = 1000;
	String user1;
	String user2;
	double balance;
	String description;
	String status;
	String q;
	String a;
	
	public AccountDAO(Connection conn) {
		this.conn = conn;
	}
	
	public void addAcct(AccountInfo ac) throws SQLException, FileNotFoundException, IOException{
		acct = ac.getAcct();		
		user1 = ac.getUser1();
		user2 = ac.getUser2();
		balance = ac.getBalance();
		description = ac.getDescription();
		status = ac.getStatus();
		 try(Connection conn = Connect2SQL.getConnection()){ //connect to DB	
			 lg.info("connection to database is successful");
			 if(user2 == null) {
				 q = "INSERT INTO accounts (acct, user1, balance, description, status) VALUES ";
				 a = "('" + acct + "', '" + user1 + "', " + balance + ", '" + description + "', '"+ status+ "')";
				 
			 }else {
				 q = "INSERT INTO accounts (acct, user1, user2, balance, description, status) VALUES ";
				 a = "('" + acct + "', '" + user1 + "', '" + user2 + "', " + balance + ", '" + description + "', '"+ status+ "')";
			 }
			 PreparedStatement statement = conn.prepareStatement(q+a);
			 try {
				 statement.executeQuery();
			 }catch (PSQLException e) {
				 System.out.println("Congratulations! Your account was made!"); 
				 lg.info("an account was made: "+ac);
				 return;
			 }catch (Exception e) { 
				 e.printStackTrace();
			 }return;
		 }
	}
	 
	//retrieve all table data
	public Set<AccountInfo> showMyAccts(Customer cs) throws SQLException{
		//create a set to store data retrieved from SQL
		Set<AccountInfo> myAccts = new HashSet<AccountInfo>();
		//send SQL code to database (select logins table)
		String email = cs.getEml();
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE user1 = '"+email+"' OR user2 = '"+ email + "'");
		ResultSet results = statement.executeQuery();
		
		//for each data set retrieved from SQL
	 	while(results.next()) {
	 		//add to Set
	 		
	 		myAccts.add(new AccountInfo(results.getLong("acct"),results.getString("user1"),results.getString("user2"),results.getDouble("balance"),results.getString("description"),results.getString("status")));
	 	}
	 	return myAccts; //return Set
	}
	
	public double retrieveBalance(long acct) throws SQLException{
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE acct = "+acct);
		ResultSet results = statement.executeQuery();
		double newB = 0;		
	 	while(results.next()) {
	 		newB = results.getDouble("balance");
	 	}return newB; //return acocunt balance
	}
	
	public void updateBalance(long acct, double balance) throws SQLException{
		PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET balance = "+balance+" WHERE acct = "+ acct);
		statement.executeQuery();
		lg.info("Account "+acct+" balance was change to $"+balance);
		return; //return account balance
	}


	public String retrieveStatus(long acct) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE acct = "+acct);
		ResultSet results = statement.executeQuery();
		String stat = null;		
	 	while(results.next()) {
	 		stat = results.getString("status");
	 	}return stat; //return account balance
	}

	public static Set<AccountInfo> getAllAccts() throws SQLException{
		//create a set to store data retrieved from SQL
		Set<AccountInfo> allAccts = new HashSet<AccountInfo>();
		//send SQL code to database (select logins table)
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts");
		ResultSet results = statement.executeQuery();
		
		//for each data set retrieved from SQL
	 	while(results.next()) {
	 		//add to Set
	 		
	 		allAccts.add(new AccountInfo(results.getLong("acct"),results.getString("user1"),results.getString("user2"),results.getDouble("balance"),results.getString("description"),results.getString("status")));
	 	}
	 	return allAccts; //return Set
	}

	public String retrieveUser1(long acct) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE acct = "+acct);
		ResultSet results = statement.executeQuery();
		String user1 = null;		
	 	while(results.next()) {
	 		user1 = results.getString("user1");
	 	}return user1; //return account balance
	}
	
	public String retrieveUser2(long acct) throws SQLException {
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM accounts WHERE acct = "+acct);
		ResultSet results = statement.executeQuery();
		String user2 = null;		
	 	while(results.next()) {
	 		user2 = results.getString("user2");
	 	}return user2; //return account balance
	}
	
	
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
