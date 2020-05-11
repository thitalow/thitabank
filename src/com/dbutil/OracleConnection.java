//credit to Instructor Vinay

package com.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleConnection {

	private static Connection connection;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		String username="revature";
		String password="r3vatur3";
		return connection=DriverManager.getConnection(url, username, password);
		
	}
}
//single ton java class