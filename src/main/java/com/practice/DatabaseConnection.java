package com.practice;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

//javareactdb.cjbpnp0jr9da.us-east-1.rds.amazonaws.com
// port 5432
// colleen
// bankboss

public class DatabaseConnection {
	
	

		  public static void main(String[] args) {
		    Connection conn = null;
		    try {
		    	
//		    	String url = "jdbc:postgresql://localhost/test";
//		    	Properties props = new Properties();
//		    	props.setProperty("user","fred");
//		    	props.setProperty("password","secret");
//		    	props.setProperty("ssl","true");
//		    	Connection conn = DriverManager.getConnection(url, props);
//
//		    	String url = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";
//		    	Connection conn = DriverManager.getConnection(url);

		
		    	String url = "jdbc:postgresql:/";
		    	Properties props = new Properties();
		    	props.setProperty("user",  "colleen");
		    	props.setProperty("password",  "bankboss");
		    	
		   		conn = DriverManager.getConnection(url);
		   		System.out.println("Got it!");

		    } catch (SQLException e) {
		    	System.out.println("SQL problem");
		       // throw new Error("Problem", e);
//		    }catch (Exception e) {
//		    	System.out.println("other problem");
//		       // throw new Error("Problem", e);
//		    } finally {
//		      try {
//		        if (conn != null) {
//		            conn.close();
//		        }
//		      } catch (SQLException ex) {
//		          System.out.println(ex.getMessage());
//		      }
		    }
		  
		
		  }

}
