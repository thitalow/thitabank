package com.thitabank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.thitabank.bo.CustomerBO;
import com.thitabank.bo.impl.CustomerBOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;

/**
 * Servlet implementation class AccountController
 */
@WebServlet("/account")
public class AccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Account> accounts;
		Gson gson=new Gson();
		CustomerBO bo=new CustomerBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		
		if(session != null) {
			Customer newC=(Customer) session.getAttribute("customer");
			try {
				accounts = bo.getAllActiveAccounts(newC);
				out.print(gson.toJson(accounts));
			} catch (BankException e) {
				out.print(e.getMessage());
				response.setStatus(501);
			}
		} else {
			out.print("You need to sign in as a customer.");
			out.print("\n Redirecting to main page");
			response.sendRedirect("index.html");		
		}
	}
		
		

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int balance = Integer.parseInt(request.getParameter("balance"));		
//		Map<String, String[]> requestParams = request.getParameterMap();
//		System.out.println(requestParams.keySet().toString());
//		System.out.println(requestParams.values().toString());
		//		System.out.println(balance + "hiaopewiuraoweui");
//		System.out.println("asdopuifaowpeuirawdsvoaweiuro");
		
		Gson gson=new Gson();
		CustomerBO bo=new CustomerBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		
		Account newA = gson.fromJson(request.getReader(), Account.class);
//		System.out.println(newA);
//		System.out.println(newA.getBalance());
		int balance = newA.getBalance();

		if(session != null) {
			Customer newC=(Customer) session.getAttribute("customer");
			
			try {
				bo.applyForAccount(newC, balance);
				out.print("Application Successful!");
			} catch (BankException e) {
				// TODO Auto-generated catch block
				out.print(e);
				response.setStatus(501);
			}
			
		} else {
			out.print("You need to sign in as a customer.");
			out.print("\n Redirecting to main page");
			response.sendRedirect("index.html");		
		}
	}

}
