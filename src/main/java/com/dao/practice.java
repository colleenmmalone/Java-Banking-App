package com.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.postgresql.util.PSQLException;

import com.model.LoginInfo;
import com.util.Connect2SQL;

public class practice {
	static Connection conn;
	 static Set<String> count;
	
	
	
	public static void main(String [] args) throws SQLException {
		try(Connection conn = Connect2SQL.getConnection()){ //connect to DB	
			
			//DAO for connection with 'logins' DB and 'accounts' DB
			//LoginDAO logindao = new LoginDAO(conn);

			long mx = 0;
			PreparedStatement statement;
			//statement = conn.prepareStatement("INSERT INTO logins (first_name, last_name, email, pswd) VALUES ('"+ firstName+ "', '"+ lastName+ "', '"+ email+ "', '"+ pswd+"')");
			statement = conn.prepareStatement("SELECT MAX(acct) FROM accounts");
		try {
			ResultSet results = statement.executeQuery();
			while(results.next()) {
		 		//add to Set
	
		 		mx=Long.parseLong(results.getString("max"));
		 	}
			System.out.println(mx);
		}catch (PSQLException e) {
		}catch (Exception e) { 
			 e.printStackTrace();
		}
					
		} catch (FileNotFoundException e) {
			System.out.println("file not found");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("done");
		

		
	}
}
