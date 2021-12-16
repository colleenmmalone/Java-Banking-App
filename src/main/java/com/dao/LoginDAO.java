package com.dao;

import com.driver.PrintLogo;
import com.model.AccountInfo;
import com.model.Customer;
import com.model.LoginInfo;
import com.transaction.MoneyTransactions;
import com.util.Connect2SQL;

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

public class LoginDAO {
	
	static Connection conn;
	Scanner sc = new Scanner(System.in); //scanner
	static Logger lg = Logger.getLogger(Connect2SQL.class.getName()); //create logger
	Customer cs = new Customer();	
	Customer em = new Customer();
	static PrintLogo pl = new PrintLogo(); 
	Set<AccountInfo> pending = new HashSet<AccountInfo>();
	String s;
	int lor;
	int p;
	long acct;
	boolean isEmp = true;
	MoneyTransactions mt = new MoneyTransactions();
	
	public LoginDAO(Connection conn) {//constructor. input is connection to SQL
		this.conn = conn;
	}
	
	public Customer loginOrReg() throws SQLException, FileNotFoundException, IOException {//ask user to login or register
		System.out.println("1.Login\n2.Register\n3.Employee Login\n4.Admin Login\n5.cancel\nPlease select an option");//menu

		s = sc.next();	
		lor = Integer.parseInt(s);	
		
	    switch (lor) {//execute menu choice
	        case 1:	cs = login(); //login, request email and pswd
	        		break;
	        case 2:	cs = register("CUSTOMER");//create new login credentials
	        		if(cs == null) {
	        			break;
	        		}
	    			//lg.info(cs.getFirstName()+" is logged in.");
	        		break;
	        case 3:	System.out.println("Employee Login");
	        		em = empLogin();
	        		if(em == null) {	
	        			cs = null;
	        			break;
	        		}
	        		//lg.info(em.getFirstName()+" "+em.getLastName()+" is logged in.");
	        		System.out.println(getAllUsers());
	        		System.out.println(getAllAccts());
	        		while(isEmp) {
	        		
	        		isEmp = empMenu();
	        		}
	        		cs = null;
	        		break;	 
	        case 4:	System.out.println("Admin Login");
		    		em = adLogin();
		    		if(em == null) {
		    			cs = null;
		    			break;
		    		}
		    		//lg.info(em.getFirstName()+" is logged in.");
		    		System.out.println(getAllUsers());
		    		System.out.println(getAllAccts());
		    		while(isEmp) {
		    		
		    			isEmp = adMenu();
		    		}
		    		cs = null;
		    		break;	
	        case 5:	System.out.println("Have a nice day! Please come again soon");
		    		cs = null;
		    		break;
	        default:System.out.println("Please enter a number: 1, 2, 3");      
	        		break;	        
	    }return cs;
	}
	
	private boolean empMenu() throws SQLException {
		p = countPending();
		System.out.println(pl.ANSI_BLUE+"There are "+p+" pending accounts"+pl.ANSI_RESET);
		System.out.println("Please select an option\n1. View Accounts\n2. Approve/Deny Pending Accounts\n3.Logout");
		lor = Integer.parseInt(sc.next());
		switch(lor) {
		case 1: System.out.println(getAllUsers());
				System.out.println(getAllAccts());
				break;
		case 2: if(p==0) {
					System.out.println("There are no accounts for you to approve");
					break;
				}
				try {
					pending = getPending();
					if (pending == null) {
						lg.error("account information could not be retrieved");
						break;
					}
					for ( AccountInfo a : pending) {
						acct = a.getAcct();
						System.out.println(a);
						System.out.println("Will you approve? y/n");
						s = sc.next().toLowerCase();
						if(s.equals("y")||s.equals("yes")) {
							updateStatus(acct, "OPEN");
						}else {
							closeAccount(acct);		
						}
					}
				
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
				break;
				
		case 3: System.out.println("logging out...");
				isEmp = false;
				break;
		default: System.out.println("invalid option"); 
				break;			
		}
		return isEmp;
	}


	private boolean adMenu() throws SQLException, FileNotFoundException, IOException {
			try(Connection conn = Connect2SQL.getConnection()){ 
				MoneyTransactions money = new MoneyTransactions();
				p = countPending();
				System.out.println(pl.ANSI_BLUE+"There are "+p+" pending accounts"+pl.ANSI_RESET);
				System.out.println("Please select an option\n1. View Accounts\n2. Approve/Deny Pending Accounts\n3. Deposit\n4. Withdraw\n5. Transfer\n6. Close Account\n7. Register New Employee\n8. Logout");
				lor = Integer.parseInt(sc.next());
				switch(lor) {
				case 1: System.out.println(getAllUsers());
						System.out.println(getAllAccts());
						break;
				case 2: if(p==0) {
							System.out.println("There are no accounts for you to approve");
							break;
						}
						try {
							pending = getPending();
							if (pending == null) {
								lg.error("account information could not be retrieved");
								break;
							}
							for ( AccountInfo a : pending) {
								acct = a.getAcct();
								System.out.println(a);
								System.out.println("Will you approve? y/n");
								s = sc.next().toLowerCase();
								if(s.equals("y")||s.equals("yes")) {
									updateStatus(acct, "OPEN");
								}else {
									closeAccount(acct);	
								}
							}
						
						} catch (SQLException e) {
						
							e.printStackTrace();
						}
						break;
						
				case 3: money.adDeposit();
						break;
				case 4: money.adWithdraw();	
						break;
				case 5: money.adTransfer();
						break;
				
				case 6: System.out.println("enter the acct# for the account you want to close");	
						long c = Long.parseLong(sc.next());
						closeAccount(c);
						break;
				case 7:	System.out.println("Registering new employee");
						register("EMPLOYEE");
						break;	
				case 8:	System.out.println("logging out...");
						isEmp = false;
						break;
				default: System.out.println(pl.ANSI_RED+"invalid option"+pl.ANSI_RESET); 
						break;			
				}return isEmp;
			}
		}

	//logging in - comparing password
	public Customer login() throws SQLException{
		// Set<LoginInfo> matchPassword = new HashSet<LoginInfo>();//create new set

		 System.out.println("Please enter your email to login");
		 String email = sc.next();
		 System.out.println("Please enter your password");
		 String pswd = sc.next();
		 //request data entry matching only that email
		 PreparedStatement statement = conn.prepareStatement("SELECT * FROM logins WHERE email = '"+email+"'");
		 ResultSet results = statement.executeQuery(); //save response to Set

		 if(results.next()) { //if data exists
			 String userPswd = results.getString("pswd");			 
			 if(userPswd.equals(pswd)) {
				 System.out.println(pl.ANSI_GREEN+"You have successfully logged in!"+pl.ANSI_RESET);
				 lg.info(results.getString("first_name")+" "+results.getString("last_name")+" is logged in.");
				 cs.setEml(email);
				 cs.setPswd(userPswd);
				 cs.setFirstName(results.getString("first_name"));
				 cs.setLastName(results.getString("last_name"));
				 //retrieve bank accounts
				
				 return cs;
			 }else { //password does not match
				 lg.warn("You have entered a wrong password");
				 return null;
			 }
		 }else { //the email is not in database
			 lg.warn(email+" is not an email registered with us.");
			 String yon = wannaRegister();
			 if(yon.equals("yes") || yon.equals("y")) {
				 cs = register("CUSTOMER");
				 return cs;
			 }else {
				 return null;
			 }			 
		 }
	 }
	
	//logging in - comparing password
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>needs to be employee
	public Customer empLogin() throws SQLException{
		// Set<LoginInfo> matchPassword = new HashSet<LoginInfo>();//create new set

		 System.out.println("Please enter your email to login");
		 String email = sc.next();
		 System.out.println("Please enter your password");
		 String pswd = sc.next();
		 //request data entry matching only that email
		 PreparedStatement statement = conn.prepareStatement("SELECT * FROM logins WHERE email = '"+email+"'");
		 ResultSet results = statement.executeQuery(); //save response to Set

		 if(results.next()) { //if data exists
			 String id = results.getString("id");
			 if(id.equals("EMPLOYEE") || id.equals("ADMIN")) {
				 String userPswd = results.getString("pswd");			 
				 if(userPswd.equals(pswd)) {
					 System.out.println(pl.ANSI_GREEN+"You have successfully logged in!"+pl.ANSI_RESET);
					 lg.info(results.getString("first_name")+" "+results.getString("last_name")+" is logged in.");
					 em.setEml(email);
					 em.setPswd(userPswd);
					 em.setFirstName(results.getString("first_name"));
					 em.setLastName(results.getString("last_name"));
					 return em;
				 }else { //password does not match
					 lg.warn("You have entered a wrong password");
					 return null;
				 }
			 }else { //the email is not in database
				 lg.warn("You are not an employee at the Bank of Hocus Pocus. Please use the customer login");
				 return null;			 			 
		 }}
		return null;
		 
	 }
	
	public Customer adLogin() throws SQLException{
		// Set<LoginInfo> matchPassword = new HashSet<LoginInfo>();//create new set

		 System.out.println("Please enter your email to login");
		 String email = sc.next();
		 System.out.println("Please enter your password");
		 String pswd = sc.next();
		 //request data entry matching only that email
		 PreparedStatement statement = conn.prepareStatement("SELECT * FROM logins WHERE email = '"+email+"'");
		 ResultSet results = statement.executeQuery(); //save response to Set

		 if(results.next()) { //if data exists
			 String id = results.getString("id");
			 if(id.equals("ADMIN")) {
				 String userPswd = results.getString("pswd");			 
				 if(userPswd.equals(pswd)) {
					 System.out.println(pl.ANSI_GREEN+"You have successfully logged in!"+pl.ANSI_RESET);
					 lg.info(results.getString("first_name")+" "+results.getString("last_name")+" is logged in.");
					 em.setEml(email);
					 em.setPswd(userPswd);
					 em.setFirstName(results.getString("first_name"));
					 em.setLastName(results.getString("last_name"));
					 return em;
				 }else { //password does not match
					 lg.warn("You have entered a wrong password");
					 return null;
				 }
			 }else { //the email is not in database
				 lg.warn("You are not an admin at the Bank of Hocus Pocus. Please use the proper login");
				 return null;			 			 
		 }}
		return null;
		 
	 }
		
	//create a new user
	public Customer register(String newID) throws SQLException{
		System.out.println("Please enter first name");
		String firstName = sc.next();	 
		System.out.println("Please enter last name");
		String lastName = sc.next();
		System.out.println("Please enter email");
		String email = sc.next();	 
		System.out.println("Please enter password");
		String pswd = sc.next();	
		//PreparedStatement statement = conn.prepareStatement("INSERT INTO logins (first_name, last_name, email, pswd, id) VALUES ('"+ firstName+ "', '"+ lastName+ "', '"+ email+ "', '"+ pswd+"', '"+newID+"')");
		PreparedStatement statement = conn.prepareStatement("CALL InsertNewUser('"+ firstName+ "', '"+ lastName+ "', '"+ email+ "', '"+ pswd+"', '"+newID+"')");
		
		
		try {
			 statement.executeQuery();
		}catch (PSQLException e) {
			 System.out.println(pl.ANSI_GREEN+"Congratulations! Your account was made!"+pl.ANSI_RESET); 
			 lg.info("An account was made for "+firstName+" "+lastName);
			 cs.setFirstName(firstName);
			 cs.setLastName(lastName);
			 cs.setEml(email);
			 cs.setPswd(pswd);			 
			 return cs;
		}catch (Exception e) { 
			 e.printStackTrace();
		}return null;
	}
 
	//ask if user wants to register and return answer
	public String wannaRegister() {
		System.out.println("Would you like to register? y/n");
		return sc.next();
	}
	
	//retrieve all table data
	public static Set<LoginInfo> getAllUsers() throws SQLException{
		//create a set to store data retrieved from SQL
		Set<LoginInfo> allUsers = new HashSet<LoginInfo>();
		//send SQL code to database (select logins table)
		PreparedStatement statement = conn.prepareStatement("SELECT * FROM logins");
		ResultSet results = statement.executeQuery();
		System.out.println(results);
		//for each data set retrieved from SQL
	 	while(results.next()) {
	 		//add to Set
	 		
	 		allUsers.add(new LoginInfo(results.getString("first_name"),results.getString("last_name"),results.getString("email"),results.getString("pswd")));
	 	}
	 	return allUsers; //return Set
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
				
	
	public int countPending() {
		try(Connection conn = Connect2SQL.getConnection()){ //connect to DB	
			PreparedStatement statement;
			statement = conn.prepareStatement("SELECT COUNT(acct) from accounts WHERE status='PENDING'");
			try {
				ResultSet results = statement.executeQuery();
				while(results.next()) {
		 		p=Integer.parseInt(results.getString("count"));
				}
			return p;
			
			}catch (Exception e) { 
				 e.printStackTrace();
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}return 0;	
	}
	
	public static Set<AccountInfo> getPending() throws SQLException{
		//create a set to store data retrieved from SQL
		Set<AccountInfo> pending = new HashSet<AccountInfo>();
		//send SQL code to database (select logins table)
		PreparedStatement statement = conn.prepareStatement("SELECT * from accounts WHERE status='PENDING'");
		ResultSet results = statement.executeQuery();
		
		//for each data set retrieved from SQL
	 	while(results.next()) {
	 		//add to Set
//	 		if(results.getString("user2")==null) {
//	 			pending.add(new AccountInfo(results.getLong("acct"),results.getString("user1"),"",results.getDouble("balance"),results.getString("description"),results.getString("status")));
//		 	}
	 		pending.add(new AccountInfo(results.getLong("acct"),results.getString("user1"),results.getString("user2"),results.getDouble("balance"),results.getString("description"),results.getString("status")));
	 	}
	 	return pending; //return Set
	}
	
	public static void updateStatus(long acct, String status) throws SQLException{
		PreparedStatement statement = conn.prepareStatement("UPDATE accounts SET status = '"+status+"' WHERE acct = "+ acct);
		try {
		statement.executeQuery();
		}catch(PSQLException e) {	
			lg.info("Account "+acct+" status was changed to "+status);
		}
		return; //return account balance
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

}
