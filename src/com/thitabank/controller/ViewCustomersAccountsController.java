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
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;

/**
 * Servlet implementation class ViewCustomersAccountsController
 */
@WebServlet("/viewCustomersAccounts")
public class ViewCustomersAccountsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewCustomersAccountsController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		int balance = Integer.parseInt(request.getParameter("balance"));		
//		Map<String, String[]> requestParams = request.getParameterMap();
//		System.out.println(requestParams.keySet().toString());
//		System.out.println(requestParams.values().toString());
		//		System.out.println(balance + "hiaopewiuraoweui");
//		System.out.println("asdopuifaowpeuirawdsvoaweiuro");
		
		Gson gson=new Gson();
		EmployeeBO bo=new EmployeeBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession(false);
		List<Account> accounts;

		
		Account newA = gson.fromJson(request.getReader(), Account.class);
//		System.out.println(newA);
//		System.out.println(newA.getBalance());
		int id = newA.getId();


		if(session != null) {
			Employee newE=(Employee) session.getAttribute("Employee");
			
			try {
				accounts = bo.getAccountsOfUserID(id);
				out.print(gson.toJson(accounts));
			} catch (BankException e) {
				// TODO Auto-generated catch block
				out.print(e.getMessage());
				response.setStatus(501);
			}
			
		} else {
			out.print("You need to sign in as a employee.\n Redirecting to main page.");
			response.sendRedirect("index.html");		
		}
	}

}
