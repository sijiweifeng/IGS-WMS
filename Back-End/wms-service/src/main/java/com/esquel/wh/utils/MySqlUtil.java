package com.esquel.wh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlUtil {
	private static String USER = "";
	private static String PASSWORD = "";
	private static String HOST = "";
	private static String DBNAME = "";

	private Connection con;

	private MySqlUtil() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// 后面那串长长的参数是为了防止乱码，免去每次都需要在任何语句都加入一条SET NAMES UTF8
			String url = "jdbc:mysql://" + HOST + ":3306/" + DBNAME
					+ "?useUnicode=true&characterEncoding=utf8&useOldAliasMetadataBehavior=true";
			con = DriverManager.getConnection(url, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static MySqlUtil db = null;

	public static MySqlUtil getInstance(String host, String dbname, String user, String pwd) {
		HOST = host;
		DBNAME = dbname;
		USER = user;
		PASSWORD = pwd;

		if (db == null) {
			db = new MySqlUtil();
		}
		return db;
	}

	public List<Object[]> getBySql(String sql) {
		List<Object[]> result_list = new ArrayList<Object[]>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				Object[] row_data_set = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					row_data_set[i - 1] = rs.getObject(i);
				}
				result_list.add(row_data_set);
			}
			return result_list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 查询sql语句带参数的情况
	public List<Object[]> getBySql(String sql, Object[] param) {
		List<Object[]> result_list = new ArrayList<Object[]>();
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ResultSetMetaData md = rs.getMetaData();
				int columnCount = md.getColumnCount();
				Object[] row_data_set = new Object[columnCount];
				for (int i = 1; i <= columnCount; i++) {
					row_data_set[i - 1] = rs.getObject(i);
				}
				result_list.add(row_data_set);
			}
			return result_list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getBySql_result_unique(String sql) {
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Object getBySql_result_unique(String sql, Object[] param) {
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 三、增删改
	// insert、update、delete等修改数据库的语句
	public void setBySql(String sql) {
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// sql语句带参数的情况
	public void setBySql(String sql, Object[] param) {
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			for (int i = 0; i < param.length; i++) {
				ps.setObject(i + 1, param[i]);
			}
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 析构函数，中断数据库的连接
	protected void finalize() throws Exception {
		if (!con.isClosed() || con != null) {
			con.close();
		}
	}
}
