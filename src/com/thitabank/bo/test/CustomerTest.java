package com.thitabank.bo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.thitabank.bo.CustomerBO;
import com.thitabank.bo.EmployeeBO;
import com.thitabank.bo.impl.CustomerBOImpl;
import com.thitabank.bo.impl.EmployeeBOImpl;
import com.thitabank.exception.BankException;

class CustomerTest {

	private static CustomerBO customer;

	@BeforeAll //BeforeClass 
	public static void initializeCustomer() {
		customer=new CustomerBOImpl();
	}

	//Deposit Tests
	//param(userid, accountid, amount)
	
	@Test
	void testDepositNegativeAmount() {
		  Assertions.assertThrows(BankException.class, () -> {
			    customer.deposit(35, 15, -1);
			  });
	}
	
	@Test
	void testDepositWrongAccount() {
		  Assertions.assertThrows(BankException.class, () -> {
			    customer.deposit(35, 100, 1);
			  });
	}
	
	//WithdrawTests	
	//param(userid, accountid, amount)

	@Test
	void testWithdrawNegativeAmount() {
		  Assertions.assertThrows(BankException.class, () -> {
			    customer.withdraw(35, 15, -1);
			  });
	}
	
	@Test
	void testWithdrawWrongAccount() {
		  Assertions.assertThrows(BankException.class, () -> {
			    customer.withdraw(35, 100, 1);
			  });
	}
	
	@Test
	void testWithdrawTooMuch() {
		  Assertions.assertThrows(BankException.class, () -> {
			    customer.withdraw(35, 15, 100000000);
			  });
	}
	

}
