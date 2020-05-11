package com.thitabank.to;

public class Transaction {
	private int id;
	private int fromAcc;
	private int toAcc;
	private int amount;
	private int type; //1 for deposit, 2 for withdraw, 3 for transfer
	private int status; //0 for rejected, 1 for pending, 2 for approved
	public Transaction() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFromAcc() {
		return fromAcc;
	}
	public void setFromAcc(int fromAcc) {
		this.fromAcc = fromAcc;
	}
	public int getToAcc() {
		return toAcc;
	}
	public void setToAcc(int toAcc) {
		this.toAcc = toAcc;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
		String typeString = "";
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
		
		switch(type) {
		case(1):
			typeString = "DEPOSIT";
			break;
		case(2):
			typeString = "WITHDRAW";
			break;
		case(3):
			typeString = "TRANSFER";
			break;
	}
		
		return "Transaction [id=" + id + ", fromAcc=" + fromAcc + ", toAcc=" + toAcc + ", amount=" + amount + ", type="
				+ typeString + ", status=" + statusString + "]";
	}

}
