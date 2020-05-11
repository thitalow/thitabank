package com.thitabank.bo.impl;

import java.sql.CallableStatement;
import java.sql.SQLException;

import com.thitabank.bo.MethodsBO;
import com.thitabank.dao.MethodsDAO;
import com.thitabank.dao.impl.MethodsDAOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.User;

public class MethodsBOImpl implements MethodsBO {
	private MethodsDAOImpl dao;

	@Override
	public void register(String name, String username, String password, int type) throws BankException, SQLException { //employee = type 2, customer = 1
		try {
			getDAO().register(name, username, password, type);
		} catch (SQLException e) {
			throw e;
		}
	}
	
	@Override
	public User login(String username, String password) throws ClassNotFoundException, SQLException, BankException {
		User newUser;
		try {
			return getDAO().login(username,password);
		} catch (BankException e) {
			throw e;
		}

	}
	
	public MethodsDAO getDAO() {
		if (dao == null) {
			dao = new MethodsDAOImpl();
		}
		return dao;
	}

}
