package com.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	private long num;
	private List<Customer> customers = new ArrayList<Customer>();
	private double balance;
	private String status; //pending, approved, denied, canceled
	
	
	public Account () {
		
	}
	
	//to apply for joint account
	// Customer first = map.get(email);
	// Customer second = map.get(email2);
// 		Account checking = new Account(100.00, first, second);
	public Account(double openingBalance, Customer c, Customer...otherCustomers) {
		num = 19231;
		status = "pending";
		balance = openingBalance;
		customers.add(c);
		for (Customer cc: otherCustomers) {
			customers.add(cc);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public long getNum() {
		return num;
	}
	public void setNum(long num) {
		this.num = num;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	@Override
	public String toString() {
		return "Account [num=" + num + ", customer=" + customers + ", balance=" + balance + "]";
	}


}
