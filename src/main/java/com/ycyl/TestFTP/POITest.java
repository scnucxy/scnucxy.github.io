package com.ycyl.TestFTP;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;

public class POITest {

	public static void main(String[] args) throws Exception {
		List<String[]> s = POIUtil.readExcel(new File("C:/Users/ycyl/Desktop/6.1.xlsx"));
		System.out.println("解析结束");

		
		InputStream in = POITest.class.getClassLoader().getResourceAsStream("jdbc.properties");
		Properties properties = new Properties();
		properties.load(in);

		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");

		// 连接数据库
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, username, password);

		
		int sum = 0;
		for (int i = 0; i < s.size(); i++) {
			if (insertData(s.get(i),connection) == 1) {
				sum++;
				System.out.println("成功插入第" + sum + "条数据");
			}
		}
	}

	public static int insertData(String[] ss,Connection connection) throws Exception {
		
		PreparedStatement preparedStatement = connection
				.prepareStatement("insert into t_antenna(antenna_no,station_number,"
						+ "machine_leans,machine_position,height,latitude,longitude,manufactor,neighbourhood_type,netele_name,netele_num,center_frequency,optimization_group,bandx) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		preparedStatement.setString(1, ss[3]);
		preparedStatement.setString(2, ss[1]);
		preparedStatement.setString(3, ss[13]);
		preparedStatement.setString(4, ss[14]);
		preparedStatement.setString(5, ss[12]);
		preparedStatement.setString(6, ss[11]);
		preparedStatement.setString(7, ss[10]);
		preparedStatement.setString(8, ss[4]);
		preparedStatement.setString(9, ss[5]);
		preparedStatement.setString(10, ss[2]);
		preparedStatement.setString(11, ss[0]);
		preparedStatement.setString(12, ss[6]);
		preparedStatement.setString(13, ss[5]);
		preparedStatement.setString(14, ss[9]);

		return preparedStatement.executeUpdate();
	}

}
