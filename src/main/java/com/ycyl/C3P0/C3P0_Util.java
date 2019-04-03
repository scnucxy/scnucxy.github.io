package com.ycyl.C3P0;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * C3P0工具类
 * 
 * @author ycyl
 *
 */
public class C3P0_Util {

	/**
	 * 获取默认配置的数据源
	 */
	private static DataSource ds = new ComboPooledDataSource();

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public static DataSource getDateSource() {
		return ds;
	}

	/**
	 * 获取数据连接
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	/**释放相关资源和链接
	 * @param rs
	 * @param conn
	 * @param stmts
	 */
	public static void close(ResultSet rs, Connection conn, Statement... stmts) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (stmts != null && stmts.length != 0) {
			for (Statement statement : stmts) {
				if (statement != null) {
					try {
						statement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (conn != null) {
             try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
