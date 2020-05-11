package com.thitabank.dao;

import java.sql.SQLException;

import com.thitabank.exception.BankException;
import com.thitabank.to.User;

public interface MethodsDAO {
	public void register(String name, String username, String password, int type) throws BankException, SQLException;

	public User login(String username, String password) throws ClassNotFoundException, SQLException, BankException;

}
