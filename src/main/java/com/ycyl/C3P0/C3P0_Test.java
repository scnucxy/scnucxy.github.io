package com.ycyl.C3P0;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class C3P0_Test {

	public static void main(String[] args) throws Exception {
		Connection connection = C3P0_Util.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement("select * from t_user");
		
		ResultSet resultSet = preparedStatement.executeQuery();
		
		while(resultSet.next()) {
			System.out.println(resultSet.getString("name"));
		}
	}

}
