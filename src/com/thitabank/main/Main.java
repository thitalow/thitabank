package com.thitabank.main;

import com.thitabank.exception.BankException;

//import java.util.Scanner;

public class Main {
//	private Scanner scanner = new Scanner(System.in);
	static boolean run = true;
	
	public static void main(String[] args) throws Exception {
		MainMethods program = new MainMethods();

		do {
			program.loginOrRegister(1);
		}
		while(run);
	}
	


}
