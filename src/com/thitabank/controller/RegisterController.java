package com.thitabank.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.thitabank.bo.EmployeeBO;
import com.thitabank.bo.MethodsBO;
import com.thitabank.bo.impl.EmployeeBOImpl;
import com.thitabank.bo.impl.MethodsBOImpl;
import com.thitabank.exception.BankException;
import com.thitabank.to.Account;
import com.thitabank.to.Customer;
import com.thitabank.to.Employee;
import com.thitabank.to.User;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String code=request.getParameter("code");
		MethodsBO bo=new MethodsBOImpl();
		PrintWriter out=response.getWriter();

		Gson gson=new Gson();
		RegisterUser newU = gson.fromJson(request.getReader(), RegisterUser.class);
		String username = newU.getUsername();
		String password = newU.getPassword();
		String registerName = newU.getName();
		String code = newU.getCode();
		
		//customer 
//		Customer customer = gson.fromJson(request.getReader(), Customer.class);
		if(code.equals("")) {
			try {
				bo.register(registerName, username, password, 1);
				out.print("Register Customer successful!");
			} catch (BankException | SQLException e) {
				out.print(e.getMessage());
				response.setStatus(501);
			}
		} else if (code.equals("code")) {
			try {
				bo.register(registerName, username, password, 2);
				out.print("Register Employee successful!");
			} catch (BankException | SQLException e) {
				out.print(e.getMessage());
				response.setStatus(501);
			} 
		} else {
			out.print("Wrong Code! Very suspicious.");
			response.setStatus(501);
		}

	}
    
//    private void refresh(PrintWriter out, HttpServletResponse response, String message) {
//    	out.println(message);
//    	out.print("Sending you to main page in 3 seconds. </h1>");
//		response.setHeader("Refresh", "3; URL=http://localhost:2222/p1.5/index.html");
		
//		response.setContentType("text/html");  
//		out.println("<script type=\"text/javascript\">");  
//		out.println("alert("+message+");");  
//		out.println("</script>");
//		try {
//			response.sendRedirect("index.html");
//		} catch (IOException e) {
//			out.println("<script type=\"text/javascript\">");  
//			out.println("alert("+e.getMessage() + ". Please contact support!"+");");  
//			out.println("</script>");
//		}
//    }
    
    private class RegisterUser {
    	private String code;
    	private String name;
    	private String username;
    	private String password;
    	
    	private RegisterUser() {
    	}

    	private RegisterUser(String code, String name, String username, String password) {
    		super();
    		this.code = code;
    		this.name = name;
    		this.username = username;
    		this.password = password;
    	}
    	
    	private String getCode() {
    		return code;
    	}

    	private void setCode(String code) {
    		this.code = code;
    	}

    	private String getName() {
    		return name;
    	}

    	private void setName(String name) {
    		this.name = name;
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
