package com.thitabank.dao.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dbutil.OracleConnection;
import com.thitabank.dao.CustomerDAO;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Transaction;

public class CustomerDAOImpl implements CustomerDAO{

	@Override
	public void applyforAccount(int id, int amount) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="INSERT INTO P0_ACCOUNT (Balance, UserID, Status) VALUES (?, ?, 1)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, amount);
			preparedStatement.setInt(2, id);
			preparedStatement.executeUpdate();

		} catch(Exception e) {
			throw new BankException("Error in CustomerDAOImpl. Please notify support: "+ e);
		}
	}

	@Override
	public List<Account> getAllActiveAccounts(int id) throws BankException {
		List<Account> accList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.aid, a.balance, a.userid, a.status from P0_ACCOUNT a where a.userid=? AND status=2"; 
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
				throw new BankException("You have no active accounts at this time.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return accList;		
	}

	@Override
	public int getUserOfAccount(int accDW) throws BankException {
		int uID;
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.userid from p0_account a where a.aid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, accDW);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				uID = (resultSet.getInt("userid"));
			}else {
				throw new BankException("Account with Id "+accDW+" not found.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following message: "+e);
		}
		return uID;
		
	}

	@Override
	public int getBalanceInAccount(int accDW) throws BankException {
		int balance;
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select a.balance from p0_account a where a.aid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, accDW);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				balance = (resultSet.getInt("balance"));
			}else {
				throw new BankException("Account with Id "+accDW+" not found.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following message: "+e);
		}
		return balance;
				
	}
	
	@Override
	public Transaction getTransactionFromId(int transID) throws BankException {
		Transaction t;
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select t.ptid, t.fromaccount, t.toaccount, t.amount, t.type, t.status from p0_transaction t where t.ptid=?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, transID);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next()) {
				t=new Transaction();
				t.setId(resultSet.getInt("ptid"));
				t.setFromAcc(resultSet.getInt("fromaccount"));
				t.setToAcc(resultSet.getInt("toaccount"));
				t.setAmount(resultSet.getInt("amount"));
				t.setType(resultSet.getInt("type"));	
				t.setStatus(resultSet.getInt("status"));			

			}else {
				throw new BankException("Transaction with Id "+transID+" not found.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following message: "+e);
		}
		return t;
				
	}

	@Override
	public void deposit(int accDW, int amount) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="{call DEPOSIT(?,?,?)}";
			CallableStatement callableStatement=connection.prepareCall(sql);
			callableStatement.setInt(1, accDW);
			callableStatement.setInt(2, amount);
			callableStatement.setInt(3, 1);
			callableStatement.execute();
			
		} catch (Exception e) {
			throw new BankException("Error in CustomerDAOImpl.deposit(). Please contact support." + e);
		} 		
	}

	@Override
	public void withdraw(int accDW, int amount) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="{call WITHDRAW(?,?,?)}";
			CallableStatement callableStatement=connection.prepareCall(sql);
			callableStatement.setInt(1, accDW);
			callableStatement.setInt(2, amount);
			callableStatement.setInt(3, 1);
			callableStatement.execute();
			
		} catch (Exception e) {
			throw new BankException("Error in CustomerDAOImpl.withdraw(). Please contact support." + e);
		} 			
	}

	@Override
	public void postTransfer(int fromAcc, int toAcc, int amount) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="INSERT INTO P0_TRANSACTION (FROMACCOUNT, TOACCOUNT, AMOUNT, TYPE, STATUS) VALUES (?, ?, ?, 3, 1)";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, fromAcc);
			preparedStatement.setInt(2, toAcc);
			preparedStatement.setInt(3, amount);
			preparedStatement.executeUpdate();
			
		} catch(Exception e) {
			throw new BankException("Error in CustomerDAOImpl.postTransfer(). Please notify support: "+ e);
		}		
	}

	@Override
	public List<Transaction> getAllPendingTransfers(int id) throws BankException {
		List<Transaction> tList=new ArrayList<>();
		try(Connection connection=OracleConnection.getConnection()){
			String sql="select t.ptid, t.fromaccount, t.toaccount, t.amount, t.type, t.status "
					+ "FROM p0_transaction t, p0_account a "
					+ "WHERE a.aid = t.toaccount AND t.status = 1 AND a.userid = ?"; 
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet=preparedStatement.executeQuery();
			while(resultSet.next()) {
				Transaction trans=new Transaction();
				trans.setId(resultSet.getInt("ptid"));
				trans.setFromAcc(resultSet.getInt("fromaccount"));
				trans.setToAcc(resultSet.getInt("toaccount"));
				trans.setAmount(resultSet.getInt("amount"));
				trans.setType(resultSet.getInt("type"));
				trans.setStatus(resultSet.getInt("status"));

				tList.add(trans);
			}
			if(tList.size()==0){
				throw new BankException("You have no pending transfers at this time.");
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new BankException("Please contact support with the following error: "+e);
		}
		return tList;		
	}

	@Override
	public void acceptTransfer(int idTrans) throws BankException {
		Transaction t = getTransactionFromId(idTrans);
		try(Connection connection=OracleConnection.getConnection()){
			String sql="{call ACCEPTTRANSFER(?,?,?,?)}";
			CallableStatement callableStatement=connection.prepareCall(sql);
			callableStatement.setInt(1, idTrans);
			callableStatement.setInt(2, t.getAmount());
			callableStatement.setInt(3, t.getFromAcc());
			callableStatement.setInt(4, t.getToAcc());
			callableStatement.execute();
			
		} catch (Exception e) {
			throw new BankException("Error in CustomerDAOImpl.acceptTransfer(). Please contact support." + e);
		} 		

	}

	@Override
	public void rejectTransfer(int idTrans) throws BankException {
		try(Connection connection=OracleConnection.getConnection()){
			String sql="UPDATE p0_transaction p SET status = 0 where p.ptid = ?";
			PreparedStatement preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setInt(1, idTrans);
			preparedStatement.executeUpdate();
			
		} catch(Exception e) {
			throw new BankException("Error in CustomerDAOEmpl.rejectTransfer(). Please notify support: "+ e);
		}	
	}



}
