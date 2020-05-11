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
import com.thitabank.bo.EmployeeBO;
import com.thitabank.bo.impl.EmployeeBOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Employee;
import com.thitabank.to.Transaction;

/**
 * Servlet implementation class AllTransactionsController
 */
@WebServlet("/allTransactions")
public class AllTransactionsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllTransactionsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		List<Transaction> trans;
		Gson gson=new Gson();
		EmployeeBO bo=new EmployeeBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		
		if(session != null) {
			Employee newE=(Employee) session.getAttribute("employee");
			try {
				trans = bo.getAllTransactions();
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

}
