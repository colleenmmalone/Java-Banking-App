package com.model;

public class AccountInfo {
	public long acct;
	private String user1;
	private String user2;
	private double balance;
	private String description;
	private String status;
	

	//constructor
	public AccountInfo(long acct, String user1, String user2, double balance, String description, String status) {
		super();
		this.acct = acct;
		this.user1 = user1;
		this.user2 = user2;
		this.balance = balance;
		this.description = description;
		this.status = status;
		
	}
	
	public AccountInfo(long acct, String user1, String user2, String description, String status) {
		super();
		this.acct = acct;
		this.user1 = user1;
		this.user2 = user2;
		this.balance = 0.00;
		this.description = description;
		this.status = status;
	}
	
	public long getAcct() {
		return acct;
	}
	public void setAcct(long acct) {
		this.acct = acct;
	}
	public String getUser1() {
		return user1;
	}
	public void setUser1(String user1) {
		this.user1 = user1;
	}
	public String getUser2() {
		return user2;
	}
	public void setUser2(String user2) {
		this.user2 = user2;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	@Override
	public String toString() {
		if(user2==null) { //check # of users and toString accordingly
			return "\nAccount: " + acct + "\t" + user1 + "\t\t\t\t$" + balance + "\t" + description + "\t\t" + status;
		}
		return "\nAccount: " + acct + "\t" + user1 + ", "+user2+ "\t$" + balance+ "\t" + description + "\t\t" + status;
	}
	
	
	
}
