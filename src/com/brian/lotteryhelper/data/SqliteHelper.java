package com.brian.lotteryhelper.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.brian.lotteryhelper.LogUtil;

public class SqliteHelper {

	private static final String DB_NAME = "LotteryDatas.db";

	public static void createOrUpdate(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
			LogUtil.logln("Opened database successfully");

			statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			connection.close();
		} catch (Exception e) {
			LogUtil.logError(e);
		}
	}

	public interface OnReadListener {
		void onRead(ResultSet set);
	}
	
	public static ResultSet query(String sql, final OnReadListener listener) {
//		LogUtil.log(sql);
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
			connection.setAutoCommit(false);
//			LogUtil.log("Opened database successfully");
			statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery(sql);
			listener.onRead(resultSet);
			return resultSet;
		} catch (Exception e) {
			LogUtil.logError(e);
			return null;
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				LogUtil.logError(e);
			}

		}
	}

	public static void insert(ArrayList<String> sqlList) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
			connection.setAutoCommit(false);
//			LogUtil.log("Opened database successfully");

			statement = connection.createStatement();
			for(String sql : sqlList) {
				try {
					statement.executeUpdate(sql);
				} catch (Exception e) {
					System.err.println(e.getClass().getName() + ": " + e.getMessage());
				}
			}
			statement.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			LogUtil.logError(e);
		}
	}
	
	public static void insert(String sql) {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + DB_NAME);
			connection.setAutoCommit(false);
//			LogUtil.log("Opened database successfully");

			statement = connection.createStatement();
			statement.executeUpdate(sql);
			statement.close();
			connection.commit();
			connection.close();
		} catch (Exception e) {
			LogUtil.logError(e);
		}
	}
}
