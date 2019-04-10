package com.ycyl.TestFTP;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class JdbcTest {

	public static void main(String[] args) throws Exception {
		InputStream in = JdbcTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		System.out.println(in);
		properties.load(in);
		System.out.println(properties.getProperty("url"));
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection conn = DriverManager.getConnection(url, username, password);
		
		PreparedStatement preparedStatement = conn.prepareStatement("select * from t_user");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}
		
	}

}
