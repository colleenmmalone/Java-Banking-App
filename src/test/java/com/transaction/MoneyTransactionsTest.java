package com.transaction;

import static org.junit.Assert.*;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dao.AccountDAO;
import com.model.Customer;
import com.util.Connect2SQL;

public class MoneyTransactionsTest {
	static Customer cs = new Customer();
	Connection conn;
	MoneyTransactions mt = new MoneyTransactions();
	static long acct, acctD;
	static double initialB, finalB, t, initialBD, finalBD;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {		
		cs.setEml("minnie@hopo.com");
		cs.setPswd("pass345");
		acct = 981; //one of Minnie's accounts
		acctD = 1005;
		t = 5.5;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		try(Connection conn = Connect2SQL.getConnection()){ 
			AccountDAO accountdao = new AccountDAO(conn);			
			mt.withdraw(acct, t);
		}catch(Exception e) {
			e.printStackTrace();
		}
		cs = null;
	}

	@Test
	public void testDeposit() throws Exception {
		try(Connection conn = Connect2SQL.getConnection()){ 
			AccountDAO accountdao = new AccountDAO(conn);
			initialB = accountdao.retrieveBalance(acct);
			mt.deposit(acct, t);
			finalB = accountdao.retrieveBalance(acct);
			//initialB = initialB + 5.5;
		//	System.out.println(initialB);
		//	System.out.println(finalB); 			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		assertTrue((finalB - initialB)==t);
		//fail((finalB-initialB)!=t);
	}
	
	@Test
	public void testWithdraw() throws Exception {
		try(Connection conn = Connect2SQL.getConnection()){ 
			AccountDAO accountdao = new AccountDAO(conn);
			initialB = accountdao.retrieveBalance(acct);
			mt.withdraw(acct, t);
			finalB = accountdao.retrieveBalance(acct);
			//initialB = initialB + 5.5;
		//	System.out.println(initialB);
		//	System.out.println(finalB); 			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		assertTrue((finalB + t)==initialB);
		//fail((finalB-initialB)!=t);
	}
	
	@Test
	public void testTransfer() throws Exception {
		try(Connection conn = Connect2SQL.getConnection()){ 
			AccountDAO accountdao = new AccountDAO(conn);
			initialB = accountdao.retrieveBalance(acct);
			initialBD = accountdao.retrieveBalance(acctD);
			mt.transfer(acct, acctD, t);
			finalB = accountdao.retrieveBalance(acct);
			finalBD = accountdao.retrieveBalance(acctD);
			//initialB = initialB + 5.5;
		//	System.out.println(initialB);
		//	System.out.println(finalB); 			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		assertTrue((finalB + t)==initialB);
		assertTrue((finalBD - t)==initialBD);
		//fail((finalB-initialB)!=t);
	}

}
