package com.thitabank.bo;

import java.util.List;

import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Transaction;

public interface CustomerBO {

	void applyForAccount(Customer newC, int amount) throws BankException;

	List<Account> getAllActiveAccounts(Customer newC) throws BankException;

	void deposit(int userid, int accDW, int amount) throws BankException;

	void withdraw(int userid, int accDW, int amount) throws BankException;

	void postTransfer(Customer newC, int fromAcc, int toAcc, int amount) throws BankException;

	List<Transaction> getAllPendingTransfers(Customer newC) throws BankException;

	void acceptTransfer(int idTrans) throws BankException;

	void rejectTransfer(int idTrans) throws BankException;


}
