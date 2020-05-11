package com.thitabank.dao;

import java.util.List;

import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Transaction;

public interface EmployeeDAO {

	List<Account> getAllPendingAccounts() throws BankException;

	void acceptAccount(int id) throws BankException;

	void rejectAccount(int id) throws BankException;

	List<Customer> getAllCustomers() throws BankException;

	List<Account> getAccountsOfUserID(int id) throws BankException;

	List<Transaction> getAllTransactions() throws BankException;

}
