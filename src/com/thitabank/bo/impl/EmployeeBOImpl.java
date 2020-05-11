package com.thitabank.bo.impl;

import java.util.List;
import com.thitabank.bo.EmployeeBO;
import com.thitabank.dao.EmployeeDAO;
import com.thitabank.dao.impl.EmployeeDAOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.dao.impl.EmployeeDAOImpl;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;
import com.thitabank.to.Transaction;

public class EmployeeBOImpl implements EmployeeBO {

	private EmployeeDAOImpl dao;

	public EmployeeDAO getDAO() {
		if (dao == null) {
			dao = new EmployeeDAOImpl();
		}
		return dao;
	}
	
	@Override
	public List<Account> getAllPendingAccounts() throws BankException {
		return getDAO().getAllPendingAccounts();
	}

	@Override
	public void acceptAccount(int id) throws BankException {
		getDAO().acceptAccount(id);
		
	}

	@Override
	public void rejectAccount(int id) throws BankException {
		getDAO().rejectAccount(id);
		
	}

	@Override
	public List<Customer> getAllCustomers() throws BankException {
		// TODO Auto-generated method stub
		return getDAO().getAllCustomers();
	}

	@Override
	public List<Account> getAccountsOfUserID(int id) throws BankException {
		return getDAO().getAccountsOfUserID(id);		
	}

	@Override
	public List<Transaction> getAllTransactions() throws BankException {
		return getDAO().getAllTransactions();
	}


}
