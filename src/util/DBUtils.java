package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtils {
	
	private static Properties p = new Properties();
	
	private static String driver;
	
	private static String url;
	
	private static String name;
	
	private static String password;
	
	static {
		try {
			p.load(DBUtils.class.getResourceAsStream("connection_info.properties"));
			driver = p.getProperty("driver").trim();
			url = p.getProperty("url").trim();
			name = p.getProperty("name").trim();
			password = p.getProperty("password").trim();			
		} catch (IOException e) {
			System.out.println("未找到数据库连接配置文件");
		}
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, name, password);
		} catch (ClassNotFoundException e) {
			System.out.println("驱动未找到");
		} catch (SQLException e) {
			System.out.println("数据库连接配置选项不正确");
		}
		
		return conn;
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if(rs != null)
				rs.close();
			if(conn != null)
				conn.close();		
			if(stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("数据库连接关闭异常");
		}
	}	
}
