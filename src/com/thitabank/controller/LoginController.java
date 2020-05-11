package com.thitabank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.thitabank.bo.MethodsBO;
import com.thitabank.bo.impl.MethodsBOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;
import com.thitabank.to.User;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MethodsBO bo=new MethodsBOImpl();
		PrintWriter out=response.getWriter();
		HttpSession session=request.getSession();
		
		Gson gson=new Gson();
		LoginUser newU = gson.fromJson(request.getReader(), LoginUser.class);		
		String username = newU.getUsername();
		String password = newU.getPassword();

		try {
			User loginUser = bo.login(username,password);
			if(loginUser.getClass().getName().equals("com.thitabank.to.Employee")) {
				Employee newE = (Employee) loginUser;
				session.setAttribute("employee", newE);
				response.sendRedirect("employee_main.html");
			} else if (loginUser.getClass().getName().equals("com.thitabank.to.Customer")) {
				Customer newC = (Customer) loginUser;
				session.setAttribute("customer", newC);
				response.sendRedirect("customer_main.html");		
			} else {
				out.print("Error in LoginController(). Please contact suppport.");
				response.setStatus(501);
			}
		} catch (ClassNotFoundException | SQLException | BankException e) {
			out.print(e.getMessage());
			response.setStatus(501);

		}
    }
    
    private class LoginUser {
    	private String username;
    	private String password;
    	
    	private LoginUser() {
    	}

    	private LoginUser(String username, String password) {
    		super();
    		this.username = username;
    		this.password = password;
    	}

    	private String getUsername() {
    		return username;
    	}

    	private void setUsername(String username) {
    		this.username = username;
    	}

    	private String getPassword() {
    		return password;
    	}

    	private void setPassword(String password) {
    		this.password = password;
    	}

    }

}
