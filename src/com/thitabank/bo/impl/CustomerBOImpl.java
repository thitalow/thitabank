package com.thitabank.bo.impl;

import java.util.List;

import com.thitabank.bo.CustomerBO;
import com.thitabank.dao.CustomerDAO;
import com.thitabank.dao.MethodsDAO;
import com.thitabank.dao.impl.CustomerDAOImpl;
import com.thitabank.dao.impl.MethodsDAOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Transaction;

public class CustomerBOImpl implements CustomerBO {
	private CustomerDAOImpl dao;

	public CustomerDAO getDAO() {
		if (dao == null) {
			dao = new CustomerDAOImpl();
		}
		return dao;
	}
	
	@Override
	public void applyForAccount(Customer newC, int amount) throws BankException {
		if(amount < 0) {
			throw new BankException("Please enter a positive integer");
		}
		try {
			getDAO().applyforAccount(newC.getId(), amount);
		} catch (BankException e) {
			throw e; 
		}
	}
	
	@Override
	public List<Account> getAllActiveAccounts(Customer newC) throws BankException {
		return getDAO().getAllActiveAccounts(newC.getId());
	}


	@Override
	public void deposit(int userid, int accDW, int amount) throws BankException {
		if (getDAO().getUserOfAccount(accDW) != userid) {
			throw new BankException("This account does not belong to you");
		}
		if (amount <0) {
			throw new BankException("Please enter a positive amount");
		}

		getDAO().deposit(accDW, amount);
		
	}
	
	@Override
	public void withdraw(int userid, int accDW, int amount) throws BankException {
		if (getDAO().getUserOfAccount(accDW) != userid) {
			throw new BankException("This account does not belong to you");
		}
		
		if (amount <0) {
			throw new BankException("Please enter a positive amount");
		}
		
		if(getDAO().getBalanceInAccount(accDW) < amount) {
			throw new BankException("You do not have enough money to make this withdrawal");
		}
		
		getDAO().withdraw(accDW, amount);
	}

	@Override
	public void postTransfer(Customer newC, int fromAcc, int toAcc, int amount) throws BankException {
		if (getDAO().getUserOfAccount(fromAcc) != newC.getId()) {
			throw new BankException("From account does not belong to you");
		}
		
		if(getDAO().getBalanceInAccount(fromAcc) < amount) {
			throw new BankException("From account does not have enough money to make this transfer");
		}
		
		getDAO().postTransfer(fromAcc, toAcc, amount);
				
	}

	@Override
	public List<Transaction> getAllPendingTransfers(Customer newC) throws BankException{
		return getDAO().getAllPendingTransfers(newC.getId());
	}

	@Override
	public void acceptTransfer(int idTrans) throws BankException {
		getDAO().acceptTransfer(idTrans);		
	}

	@Override
	public void rejectTransfer(int idTrans) throws BankException {
		getDAO().rejectTransfer(idTrans);		
		
	}
}
