package com.thitabank.dao;

import java.sql.SQLException;
import java.util.List;

import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Transaction;

public interface CustomerDAO {

	void applyforAccount(int id, int amount) throws BankException;

	List<Account> getAllActiveAccounts(int id) throws BankException;

	int getUserOfAccount(int accDW) throws BankException;

	int getBalanceInAccount(int accDW) throws BankException;

	void deposit(int accDW, int amount) throws BankException;

	void withdraw(int accDW, int amount) throws BankException;

	void postTransfer(int fromAcc, int toAcc, int amount) throws BankException;

	List<Transaction> getAllPendingTransfers(int id) throws BankException;

	void acceptTransfer(int idTrans) throws BankException;

	Transaction getTransactionFromId(int transID) throws BankException;

	void rejectTransfer(int idTrans) throws BankException;

}
