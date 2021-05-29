package chae.web.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class DBConnectionManager implements ServletContextListener {

	private Connection conn;

	public DBConnectionManager() {
	}

	public void contextDestroyed(ServletContextEvent event) {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void contextInitialized(ServletContextEvent event) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Load JDBC Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Fail to load JDBC Driver");
		}

		ServletContext context = event.getServletContext();

		String dbURL = context.getInitParameter("dbURL");
		String dbUser = context.getInitParameter("dbUser");
		String dbPW = context.getInitParameter("dbPW");

		try {
			conn = DriverManager.getConnection(dbURL, dbUser, dbPW);
			
			if(conn != null) {
				context.setAttribute("DBConnection", conn);
				System.out.println("Succeed DB connection");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Fail DB connection");
		}
	}

}
