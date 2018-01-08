package com.sdb;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

//extra credit
public class DatabaseAbstraction {
	
	public static Connection connect(){
//	log4j extra credit
		Logger log=Logger.getLogger("Database Connection");
		
//		This block of Code is for connect to the database
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			log.log(Level.SEVERE, "Instantiation");
		} catch (IllegalAccessException e) {
			log.log(Level.SEVERE, "Illegal Access");
		} catch (ClassNotFoundException e) {
			log.log(Level.SEVERE, "Class not Found");
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL exception");
		}
		
		return conn;
		
	}
	
	public static void disconnect(Connection conn,ResultSet rs,PreparedStatement ps){
		Logger log=Logger.getLogger("database disconnect");
		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL closing exception");
		}
	}
	
	public static void disconnect(Connection conn,PreparedStatement ps){
		Logger log=Logger.getLogger("database disconnect");
		try {
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			log.log(Level.SEVERE, "SQL closing exception");
		}
	}
	
	
	
}
