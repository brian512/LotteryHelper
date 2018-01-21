package com.brian.lotteryhelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.brian.lotteryhelper.SqliteHelper.OnReadListener;

public class LotteryTableHelper {
	
	private static final String TABLE_NAME = "LOTTERY";

	public static final String SQL_CREATE = "CREATE TABLE " + TABLE_NAME
			+ " (lotteryID       INT PRIMARY KEY  NOT NULL,"
			+ " date       	     TEXT    NOT NULL, "
			+ " data1            INT     NOT NULL, "
			+ " data2            INT     NOT NULL, "
			+ " data3            INT     NOT NULL, "
			+ " data4            INT     NOT NULL, "
			+ " data5            INT     NOT NULL "
			+ ")";
	
	
	
	public static void createTable() {
		SqliteHelper.createOrUpdate(SQL_CREATE);
	}

	
	public static void insertLotteries(ArrayList<Lottery> lotteries) {
		ArrayList<String> sqlList = new ArrayList<>(lotteries.size());
		String sql_template = "REPLACE INTO " + TABLE_NAME 
				+ " VALUES (%d, '%s', %d, %d, %d, %d, %d );";
		for (Lottery lottery : lotteries) {
			String sql = String.format(sql_template, 
					lottery.lotteryId, 
					lottery.dateStr, 
					lottery.data1, 
					lottery.data2,
					lottery.data3,
					lottery.data4,
					lottery.data5
					);
			sqlList.add(sql);
		}
		SqliteHelper.insert(sqlList);
	}
	
	
	public static interface OnQueryListener {
		void onQuery(ArrayList<Lottery> lotteryList);
	}
	
	public static void queryDatasAtRange(long start, long end, final OnQueryListener listener) {
		String sql = "SELECT * FROM " + TABLE_NAME 
				+ " WHERE lotteryID>" + start + " AND lotteryID<" +  end
				+ " ORDER BY lotteryID DESC"
				+ ";"
				;
		queryDatas(sql, listener);
	}
	
	public static void queryLatestDatas(int count, final OnQueryListener listener) {
		String sql = "SELECT * FROM " + TABLE_NAME 
				+ " ORDER BY lotteryID DESC"
				+ " LIMIT " + count 
				+ ";"
				;
		queryDatas(sql, listener);
	}
	
	
	private static void queryDatas(String sql, final OnQueryListener listener) {
		SqliteHelper.query(sql, new OnReadListener() {
			
			@Override
			public void onRead(ResultSet resultSet) {
				ArrayList<Lottery> lotteryList = new ArrayList<>();
				if (resultSet == null) {
					LogUtil.logError("resultSet=null");
					listener.onQuery(lotteryList);
					return;
				}
				try {
					while (resultSet.next()) {
						Lottery lottery = new Lottery();
						lottery.lotteryId = resultSet.getLong("lotteryID");;
						lottery.data1 = resultSet.getInt("data1");;
						lottery.data2 = resultSet.getInt("data2");;
						lottery.data3 = resultSet.getInt("data3");;
						lottery.data4 = resultSet.getInt("data4");;
						lottery.data5 = resultSet.getInt("data5");;
						lottery.dateStr = resultSet.getString("date");
						lotteryList.add(lottery);
					  }
					resultSet.close();
					
					listener.onQuery(lotteryList);
				} catch (SQLException e) {
					LogUtil.logError(e);
				}				
			}
		});
	}
}
