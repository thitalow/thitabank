package com.thitabank.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.dbutil.OracleConnection;
import com.thitabank.dao.MethodsDAO;
import com.thitabank.exception.BankException;
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;
import com.thitabank.to.User;

public class MethodsDAOImpl implements MethodsDAO {

	@Override
	public void register(String name, String username, String password, int type) throws BankException, SQLException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="{call REGISTERUSER(?,?,?,?)}";
			CallableStatement callableStatement=connection.prepareCall(sql);
			callableStatement.setString(1, name);
			callableStatement.setString(2, username);
			callableStatement.setString(3, password);
			callableStatement.setInt(4, type);
			callableStatement.execute();
			
		} catch (ClassNotFoundException e) {
			throw new BankException("Error Class. Please contact support. ");
		} catch (SQLException e) {
			throw new SQLException("Username already taken. Please choose another username. \n");

		} 
		
	}

	@Override
	public User login(String username, String password) throws ClassNotFoundException, SQLException, BankException {
		User newUser;
		try(Connection connection=OracleConnection.getConnection()){
			String sql="SELECT u.userid, u.name, u.username, u.password, u.type from "
					+ "P0_USER u WHERE u.username=? AND u.password=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);

			ResultSet resultSet=preparedStatement.executeQuery();
			int id;
			String name;
			int type;
			if(resultSet.next()) {
				id = (resultSet.getInt("userid"));
				name = (resultSet.getString("name"));
				username = (resultSet.getString("username"));
				password = (resultSet.getString("password"));
				type = (resultSet.getInt("type"));
			}else {
				throw new BankException("Account not found. \n");
			}
			
			if(type == 1) {
				newUser = new Customer(id, name, username, password);
			} else if (type == 2) {
				newUser = new Employee(id, name, username, password);
			} else {
				throw new BankException("Account Type doesn't exist. Please contact support. \n");
			}

		}
		return newUser; 
		
	}


}
