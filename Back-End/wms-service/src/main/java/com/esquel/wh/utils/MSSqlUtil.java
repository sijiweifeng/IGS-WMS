package com.esquel.wh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MSSqlUtil {

	private String url="";
	private String user ="";
	private String password ="";
	private static Connection conn=null;
	
	public MSSqlUtil(String URL, String USER,String PASSWORD){
		url = URL;
		user = USER;
		password = PASSWORD;
	}   
        
    public Connection getConnection(){
    	try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn=(Connection)DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
