package com.thitabank.main;

import java.sql.SQLException;
import java.util.Scanner;
import org.apache.log4j.Logger;
import com.thitabank.bo.CustomerBO;
import com.thitabank.bo.EmployeeBO;
import com.thitabank.bo.MethodsBO;
import com.thitabank.bo.impl.CustomerBOImpl;
import com.thitabank.bo.impl.EmployeeBOImpl;
import com.thitabank.bo.impl.MethodsBOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;
import com.thitabank.to.Transaction;
import com.thitabank.to.User;

class MainMethods {
	private static Logger log = Logger.getLogger(MainMethods.class);
	Scanner scanner = new Scanner(System.in);

	void loginOrRegister(int initialMessage) { //choice between login, register, or exit
		String printInitialMessage = "";
		switch(initialMessage) {
			case 1: 
				printInitialMessage = "-------\nHello! Welcome to ThitaBank. "
						+ "\nPlease choose one of the following: "
						+ "\n 1) Login \n 2) Register \n 3) Exit"
						+ "\nPlease type the corresponding number."
						+ "\n(e.g. Type 1 for Login) \n";
				break;
			case 2:
				printInitialMessage = "Invalid selection. "
						+ "\nPlease choose one of the following: "
						+ "\n 1) Login \n 2) Register \n 3) Exit"
						+ "\nPlease type the corresponding number."
						+ "\n(e.g. Type 1 for Login) \n";
				break;
			case 3:
				printInitialMessage = "Would you like to do any of the following?: "
						+ "\n 1) Login \n 2) Register \n 3) Exit"
						+ "\nPlease type the corresponding number."
						+ "\n(e.g. Type 1 for Login) \n";
				break;
		}
		log.info(printInitialMessage);
		
		int choice = 0;
		try {
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}
		switch(choice) {
			case 1:
				login();
				break;
			case 2:
				register();
				break;
			case 3:
				log.info("Thank you for using ThitaBank!");
				Main.run = false;
				return;
			default:
				loginOrRegister(2);
				break;
		}
		
	}

	private void register() {
		int choice = 0;
		String username;
		String password;
		String name;
		int type;
		MethodsBO bo = new MethodsBOImpl();
		
		log.info("-------\nYou are at the register page. "
				+ "\nPlease choose one of the following: "
				+ "\n 1) Customer Registration \n 2) Employee Registration \n 3) Exit \n"
				+ "\nPlease type the corresponding number."
				+ "\n(e.g. Type 1 for Customer Register) \n");

		try { //first choice 
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}

		switch(choice) { //choice is 1 for customer
			case 1:
				log.info("Please enter a name.");
				name = scanner.nextLine();
				
				log.info("\nPlease enter a username.");
				username = scanner.nextLine();
				
				log.info("\nWhat is your password?");
				password = scanner.nextLine();
				log.info("\n");
				try {
					bo.register(name, username, password, choice);
					log.info("Thank you for registering.");
					loginOrRegister(3);
				} catch (Exception e) {
					log.info(e.getMessage());
					register();
				} 
				break;
			case 2: // choice 2 is for employees
				log.info("What is the super secret employee code?");
				String code = scanner.nextLine();
				if(!code.equals("code")) {
					log.info("Wrong code. Very suspicious.");
					register();
				} else {
					log.info("Please enter a name.");
					name = scanner.nextLine();
					
					log.info("Please enter a username.");
					username = scanner.nextLine();
					
					log.info("What is your password?");
					password = scanner.nextLine();
					try {
						bo.register(name, username, password, choice);
						log.info("\nThank you for registering.");
						loginOrRegister(3);
					} catch (Exception e) {
						log.info(e.getMessage());
						register();
					} 
				}
				break;
			case 3:
				log.info("Thank you for using ThitaBank!");
				Main.run = false;
				break;
			default:
				log.info("Invalid selection. \n");
				register();
				break;
		}
		
	}

	private void login(){
		MethodsBO bo = new MethodsBOImpl();
		String username;
		String password;
		
		log.info("Please enter a username.");
		username = scanner.nextLine();
		
		log.info("What is your password?\n");
		password = scanner.nextLine();
		
		try {
			User newUser = bo.login(username, password);
			if(newUser.getClass().getName().equals("com.thitabank.to.Employee")) {
				Employee newE = (Employee) newUser;
				loginEmployee(newE);
			} else if (newUser.getClass().getName().equals("com.thitabank.to.Customer")) {
				Customer newC = (Customer) newUser;
				loginCustomer(newC);			
			} else {
				log.info("Error in Main Method login(). Please contact suppport.");
			}
		} catch (Exception e) {
			log.info(e.getMessage());
			loginOrRegister(3);
		}
	}
	
	//------------------------------------------------ customer -----------------------------------------------------------


	private void loginCustomer(Customer newC) { // Main Customer Page
		int choice;
		log.info("-----\nHello awesome ThitaBank customer " + newC.getName() + "."
				+ "\nPlease choose one of the following: "
				+ "\n 1) Accounts Page \n 2) Transfers Page \n 3) Exit \n 4) Logout"
				+ "\nPlease type the corresponding number."
				+ "\n(e.g. Type 1 for Accounts Page) \n");	
		
		try { //first choice 
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}
		
		switch(choice) { //choice 1 is for Accounts Page
		case 1:
			customerAccountsPage(newC);
			break;
		case 2: // choice 2 is for Transfers Page
			customerTransfersPage(newC);
			break;
		case 3:
			log.info("Thank you for using ThitaBank!");
			Main.run = false;
			break;
		case 4: // choice 4 is for logout
			log.info("You are logging out. Thank you for using ThitaBank!");
			loginOrRegister(1);
			break;
		default:
			log.info("Invalid selection. \n");
			loginCustomer(newC);
			break;
		}
		
	}

	private void customerTransfersPage(Customer newC) { // Customer Transfers Page
		int choice;
		int amount;
		int fromAcc;
		int toAcc;
		int idTrans;
		CustomerBO bo = new CustomerBOImpl();
		log.info("---\nWelcome to the Transfers Page " + newC.getName() + ".");
		try {
			if(bo.getAllPendingTransfers(newC).size() > 0) {
				log.info("You have the following pending transfers.");
				log.info("--");
				for(Transaction trans: bo.getAllPendingTransfers(newC)) {
					log.info(trans);
				}
			} else {
				log.info("You have no pending transfers.");
			}
		} catch (BankException e) {
			log.info(e.getMessage());
		}

		log.info("\nPlease choose one of the following: "
				+ "\n 1) Post a money transfer. \n 2) Accept a money transfer. \n 3) Reject a money transfer. \n 4) Return to Customer main page. \n 5) Exit \n"
				+ "\nPlease type the corresponding number."
				+ "\n(e.g. Type 1 for Post a money transfer.) \n");	
		
		try { //first choice 
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}			
		
		switch(choice) { 
		case 1: //choice 1 is for posting a money transfer
			try {
				if(bo.getAllActiveAccounts(newC).size() > 0) {
					log.info("You have the following active accounts");
					log.info("--");
					for(Account acc: bo.getAllActiveAccounts(newC)) {
						log.info(acc);
					}
				} else {
					log.info("You have no active accounts.");
				}
			} catch (BankException e) {
				log.info(e.getMessage());;
			}
			log.info("Please enter an account to transfer from.");
			fromAcc = Integer.parseInt(scanner.nextLine());
			log.info("Please enter an account to transfer to.");
			toAcc = Integer.parseInt(scanner.nextLine());
			log.info("Please enter an amount to transfer.");
			amount = Integer.parseInt(scanner.nextLine());
			if(amount < 0) {
				log.info("Please enter a number more than 0.");
				customerTransfersPage(newC);
			} else {
				try {
					bo.postTransfer(newC, fromAcc, toAcc, amount);
					log.info("Thank you for your money transfer."
							+ "\nPlease wait for the receiving customer's approval.\n");
				} catch (BankException e) {
					log.info(e.getMessage());
				}

				customerTransfersPage(newC);
			}
			break;
		case 2: //choice 2 is for accepting a money transfer
			log.info("Please enter the ID of the transfer you want to accept.");
			idTrans = Integer.parseInt(scanner.nextLine());
			try {
				bo.acceptTransfer(idTrans);
				log.info("Transfer Approved");
				customerTransfersPage(newC);
			} catch (BankException e) {
				log.info(e.getMessage());
			}
			break;
		case 3: //choice 3 is for rejecting a money transfer
			log.info("Please enter the ID of the transfer you want to reject.");
			idTrans = Integer.parseInt(scanner.nextLine());
			try {
				bo.rejectTransfer(idTrans);
				log.info("Transfer Rejected");
				customerTransfersPage(newC);
			} catch (BankException e) {
				log.info(e.getMessage());
			}
			break;
		case 4: // choice 4 is for returning to customer main page
			loginCustomer(newC);
			break;
		case 5: // choice 5 is for exit
			log.info("Thank you for using ThitaBank!");
			Main.run = false;
			break;
		default:
			log.info("Invalid selection. \n");
			loginCustomer(newC);
			break;
		}
	}

	private void customerAccountsPage(Customer newC) { // Customer Accounts Page
		int choice;
		int amount;
		int accDW;
		CustomerBO bo = new CustomerBOImpl();
		log.info("---\nWelcome to the Accounts Page " + newC.getName() + ".");
		try {
			if(bo.getAllActiveAccounts(newC).size() > 0) {
				log.info("You have the following active accounts");
				log.info("--");
				for(Account acc: bo.getAllActiveAccounts(newC)) {
					log.info(acc);
				}
			} else {
				log.info("You have no active accounts.");
			}
		} catch (BankException e) {
			log.info(e.getMessage());;
		}

		log.info("\nPlease choose one of the following: "
				+ "\n 1) Apply for a new account. \n 2) Deposit \n 3) Withdraw \n 4) Return to Customer main page. \n 5) Exit \n"
				+ "\nPlease type the corresponding number."
				+ "\n(e.g. Type 1 for Apply for a new account) \n");	
		
		try { //first choice 
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}		
		
		switch(choice) { //choice 1 is for Apply for a new account.
		case 1:
			try {
				log.info("Please enter an initial balance.");
				amount = Integer.parseInt(scanner.nextLine());
				if(amount < 0) {
					log.info("Please enter a number more than 0.");
					customerAccountsPage(newC);
				} else {
					bo.applyForAccount(newC, amount);
					log.info("Thank you for applying for a new account"
							+ "\nPlease wait for an employee approval.\n");
					customerAccountsPage(newC);
				}
			} catch (BankException e) {
				log.info(e.getMessage());
				customerAccountsPage(newC);
			} 
			break;
		case 2: // choice 2 is for Deposit
			try {
				log.info("Please enter the account id you wish to deposit to.");
				accDW = Integer.parseInt(scanner.nextLine());
				log.info("Please enter an amount to deposit.");
				amount = Integer.parseInt(scanner.nextLine());
				if(amount < 0) { //checks that deposit amount is positive amount
					log.info("Please enter a positive amount!");
					customerAccountsPage(newC);
				} else {
					bo.deposit(newC.getId(), accDW, amount);
					log.info("Thank you for your deposit!");
					customerAccountsPage(newC);
				}
				
			} catch (BankException e) {
				log.info(e.getMessage());
				customerAccountsPage(newC);
			}
			break;
		case 3: // choice 3 is for Withdraw
			try {
				log.info("Please enter the account id you wish to withdraw from.");
				accDW = Integer.parseInt(scanner.nextLine());
				log.info("Please enter an amount to withdraw.");
				amount = Integer.parseInt(scanner.nextLine());
				if(amount < 0) { //checks that withdrawal amount is positive amount
					log.info("Please enter a positive amount!");
					customerAccountsPage(newC);
				} else {
					bo.withdraw(newC.getId(), accDW, amount);
					log.info("Thank you for your withdrawal!");
					customerAccountsPage(newC);
				}
				
			} catch (BankException e) {
				log.info(e.getMessage());
				customerAccountsPage(newC);
			}	
			break;
		case 4: // choice 4 is for Return to Customer main page.
			loginCustomer(newC);
			break;
		case 5: // choice 5 is for exit
			log.info("Thank you for using ThitaBank!");
			Main.run = false;
			break;
		default:
			log.info("Invalid selection. \n");
			loginCustomer(newC);
			break;
		}
	}
	
//------------------------------------------------ employee -----------------------------------------------------------

	private void loginEmployee(Employee newE) { // Main Employee Page
		int choice;
		log.info("-----\nHello rockstar Thitabank employee " + newE.getName() + "."
				+ "\nPlease choose one of the following: "
				+ "\n 1) Pending Accounts Page \n 2) Customers Page \n 3) Transactions Page \n 4) Exit \n 5) Logout"
				+ "\nPlease type the corresponding number."
				+ "\n(e.g. Type 1 for Accounts Page) \n");	
		
		try { //first choice 
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice=0;
		}
		
		switch(choice) { //choice 1 is for Pending Accounts Page
		case 1:
			employeeAccountsPage(newE);
			break;
		case 2: // choice 2 is for Customers Page
			employeeCustomersPage(newE);
			break;
		case 3: // choice 3 is for Transactions Page
			employeeTransactionsPage(newE);
			break;
		case 4:
			log.info("Thank you for using ThitaBank!"); // choice 4 is for exit
			Main.run = false;
			break;
		case 5: // choice 5 is for logout
			log.info("You are logging out. Thank you for using ThitaBank!");
			loginOrRegister(1);
			break;
		default:
			log.info("Invalid selection. \n");
			loginEmployee(newE);
			break;
		}
	}
	private void employeeAccountsPage(Employee newE) { // Employee Accounts Page
		EmployeeBO bo = new EmployeeBOImpl();
		int choice = 0;
		try {
			log.info("---\nThe following are pending accounts requests: ");
			try {
				for(Account acc: bo.getAllPendingAccounts()) {
					log.info(acc);
				}
			} catch(BankException e) {
				log.info(e.getMessage());
			}


			log.info("\nWhat would you like to do?"
					+ "\n 1) Approve Account Request"
					+ "\n 2) Reject Account Request"
					+ "\n 3) Exit"
					+ "\n 4) Return to Employee Main Page"
					+ "\nPlease type the corresponding number."
					+ "\n(e.g. Type 1 for Approve Account Request) \n");	
			try {  
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				choice=0;
			}
			switch(choice) { //choice 1 is for Approve Account Request
			case 1:
				try {  
					log.info("Which account do you want to accept? Enter the id.");
					int id = Integer.parseInt(scanner.nextLine());
					bo.acceptAccount(id);
					log.info("Account accepted!\n");
					employeeAccountsPage(newE);
				} catch (NumberFormatException e) {
					log.info("Invalid input.");
					employeeAccountsPage(newE);
				}
				break;
			case 2: // choice 2 is for Reject Account Request
				try {  
					log.info("Which account do you want to reject? Enter the id.");
					int id = Integer.parseInt(scanner.nextLine());
					bo.rejectAccount(id);
					log.info("Account rejected!\n");
					employeeAccountsPage(newE);
				} catch (NumberFormatException e) {
					log.info("Invalid input.");
					employeeAccountsPage(newE);
				}
				break;
			case 3:
				log.info("Thank you for using ThitaBank!");
				Main.run = false;
				break;
			case 4: // choice 4 is for Employee main Page
				log.info("Returning to Employee Main Page.");
				loginEmployee(newE);
				break;
			default:
				log.info("Invalid selection. \n");
				loginEmployee(newE);
				break;
			}

		} catch (BankException e) {
			// TODO Auto-generated catch block
			log.info(e.getMessage());
		}
		
	}

	private void employeeCustomersPage(Employee newE) { // Employee Customers Page
		EmployeeBO bo = new EmployeeBOImpl();
		try {
			log.info("---\nThe following is a list of ThitaBank customers: ");
			try {
				for(Customer acc: bo.getAllCustomers()) {
					log.info(acc);
				}
			} catch(BankException e) {
				log.info(e.getMessage());
			}

			log.info("Please enter the id of the customer whose bank accounts you want to see.");
			int id = Integer.parseInt(scanner.nextLine());
			for(Account acc: bo.getAccountsOfUserID(id)) {
				log.info(acc);
			}
			log.info("\nSending you to Employee Main Page");
			loginEmployee(newE);
			
		} catch (BankException e) {
			log.info(e.getMessage());
		}
		
	}

	private void employeeTransactionsPage(Employee newE) { //Employee Transactions Page
		EmployeeBO bo = new EmployeeBOImpl();
		try {
			log.info("The following is a list of all past transactions. \n--");

			for(Transaction t: bo.getAllTransactions()) {
				log.info(t);
			}
			
			log.info("\nSending you to Employee Main Page");
			loginEmployee(newE);
		} catch (BankException e) {
			log.info(e.getMessage());
		}
		
	}


}
