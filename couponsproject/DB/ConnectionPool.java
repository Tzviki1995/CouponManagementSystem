package com.abigail.couponsproject.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConnectionPool {

	private static ConnectionPool instance = null;
	public static List<Connection> connections = new ArrayList<>();
	private final int CONNECTION_MAX = 10;
	public static Object mutex = new Object();

	static {
		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();

		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}// static block

	private ConnectionPool() {
		for (int i = 0; i < CONNECTION_MAX; i++) {
			connections.add(addConnection());
		}
	}// c-tor

	public static ConnectionPool getInstance() {
		synchronized (mutex) {
			if (instance == null) {

				instance = new ConnectionPool();
			}
		}

		return instance;
	}// getInstance

	public Connection addConnection() {

		Connection _conn = null;
		String connectionUrl = "jdbc:sqlserver://localhost:1433;user=DESKTOP-1GN4D5G;database=CouponManagementSystem;integratedSecurity=true;";

		try {
			_conn = DriverManager.getConnection(connectionUrl);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return _conn;
	}// addConnection

	public synchronized Connection getConnection() throws InterruptedException {
		while (!checkIfConnectionPoolIsEmpty()) {
			this.wait();
		}
		int lastElement = connections.size() - 1;
		Connection conn = connections.get(lastElement);
		connections.remove(lastElement);
		return conn;
	}// getConnection

	private synchronized boolean checkIfConnectionPoolIsEmpty() {
		if (connections.size() == 0) {
			return false;
		}
		return true;
	}// checkIfConnectionPoolIsFull

	public synchronized void returnConnection(Connection connection) throws SQLException {

		if (connections.size() < CONNECTION_MAX) {
			connections.add(connection);
		}
	}// returnConnection

	public static void closeAllConnections() {

		for (int i = 0; i < connections.size(); i++) {
			try {
				if (connections.get(i) != null) {
					connections.get(i).close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: Write to log that we have a resource leak
			}

		}

	}// closeAllConnections

}// ConnectionPool
