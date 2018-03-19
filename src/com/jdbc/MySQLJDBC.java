package com.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class MySQLJDBC {
	public final static String userName = "root";
	public final static String psw = "root";
	public final static String url = "jdbc:mysql://localhost:3306/scoremang";
	public static Connection getConnection()
	{
		Connection connection = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(url, userName, psw);
			System.out.println("connection success!");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return connection;
	}
}
