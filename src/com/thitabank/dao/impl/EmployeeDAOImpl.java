package com.thitabank.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.OracleConnection;
import com.thitabank.dao.EmployeeDAO;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Transaction;

public class EmployeeDAOImpl implements EmployeeDAO{

	@Override
	public List<Account> getAllPendingAccounts() throws BankException {
		List<Account> accList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.aid, a.balance, a.userid, a.status from P0_ACCOUNT a where a.status=1";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account acc=new Account();
				acc.setId(resultSet.getInt("aid"));
				acc.setBalance(resultSet.getInt("balance"));
				acc.setUserid(resultSet.getInt("userid"));
				acc.setStatus(resultSet.getInt("status"));
				accList.add(acc);
			}
			if(accList.size()==0){
				throw new BankException("There are no pending accounts at this time.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return accList;
	}

	@Override
	public void acceptAccount(int id) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="UPDATE p0_account SET status = 2 where aid = ? AND status = 1";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();

		} catch(Exception e) {

			throw new BankException("Error in EmployeeDAOEmpl.acceptAccount(). Please notify support: "+ e);
		}
	}

	@Override
	public void rejectAccount(int id) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="update p0_account set status = 0 where aid = ? AND status = 1";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);

			preparedStatement.executeUpdate();
			
		} catch(Exception e) {
			throw new BankException("Error in EmployeeDAOEmpl.rejectAccount(). Please notify support: "+ e);
		}		
	}

	@Override
	public List<Customer> getAllCustomers() throws BankException {
		List<Customer> cList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.userid, a.name, a.username, a.password from P0_USER a where a.type=1";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Customer c=new Customer();
				c.setId(resultSet.getInt("userid"));
				c.setName(resultSet.getString("name"));
				c.setUsername(resultSet.getString("username"));
				c.setPassword(resultSet.getString("password"));
				cList.add(c);
			}
			if(cList.size()==0){
				throw new BankException("There are no customers at this time. Go kidnap some!");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return cList;
	}

	@Override
	public List<Account> getAccountsOfUserID(int id) throws BankException {
		List<Account> accList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.aid, a.balance, a.userid, a.status from P0_ACCOUNT a where a.userid=?"; 
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account acc=new Account();
				acc.setId(resultSet.getInt("aid"));
				acc.setBalance(resultSet.getInt("balance"));
				acc.setUserid(resultSet.getInt("userid"));
				acc.setStatus(resultSet.getInt("status"));

				accList.add(acc);
			}
			if(accList.size()==0){
				throw new BankException("Customer has no active accounts at this time.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return accList;		
	}

	@Override
	public List<Transaction> getAllTransactions() throws BankException {
		// TODO Auto-generated method stub
		List<Transaction> tList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select t.ptid, t.fromaccount, t.toaccount, t.amount, t.type, t.status from p0_transaction t"; 
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction t=new Transaction();
				t.setId(resultSet.getInt("ptid"));
				t.setFromAcc(resultSet.getInt("fromaccount"));
				t.setToAcc(resultSet.getInt("toaccount"));
				t.setAmount(resultSet.getInt("amount"));
				t.setType(resultSet.getInt("type"));	
				t.setStatus(resultSet.getInt("status"));

				tList.add(t);
			}
			if(tList.size()==0){
				throw new BankException("There are no transactions at this time. Go kidnap some customers!");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return tList;			
		
	}


}
