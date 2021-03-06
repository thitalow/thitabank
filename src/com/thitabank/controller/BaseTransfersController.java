package com.thitabank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.thitabank.to.Customer;
import com.thitabank.to.Transaction;

/**
 * Servlet implementation class BaseTransfersController
 */
@WebServlet("/transfer")
public class BaseTransfersController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseTransfersController() {
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
		List<Transaction> trans;
		Gson gson=new Gson();
		CustomerBO bo=new CustomerBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		
		if(session != null) {
			Customer newC=(Customer) session.getAttribute("customer");
			try {
				trans = bo.getAllPendingTransfers(newC);
				out.print(gson.toJson(trans));
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
		
		Transaction newT = gson.fromJson(request.getReader(), Transaction.class);
//		System.out.println(newA);
//		System.out.println(newA.getBalance());
		int fromAcc = newT.getFromAcc();
		int toAcc = newT.getToAcc();
		int amount = newT.getAmount();


		if(session != null) {
			Customer newC=(Customer) session.getAttribute("customer");
			
			try {
				bo.postTransfer(newC, fromAcc, toAcc, amount);
				out.print("Transfer Posting Successful!");
			} catch (BankException e) {
				// TODO Auto-generated catch block
				out.print(e);
				response.setStatus(501);
			}
			
		} else {
			out.print("You need to sign in as a customer.\n Redirecting to main page.");
			response.sendRedirect("index.html");		
		}
	}

}
