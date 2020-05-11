package com.thitabank.to;

public class Account {
	private int id;
	private int balance;
	private int userid;
	private int status;
	
	public Account() {
		// TODO Auto-generated constructor stub
	}
	
	public Account(int id, int balance, int userid, int status) {
		super();
		this.id = id;
		this.balance = balance;
		this.userid = userid;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		String statusString = "";
		switch(status) {
			case(0):
				statusString = "REJECTED";
				break;
			case(1):
				statusString = "PENDING";
				break;
			case(2):
				statusString = "APPROVED";
				break;
		}
		return "Account [id=" + id + ", balance=" + balance + ", userid=" + userid + ", status=" + statusString + "]";
	}

}
