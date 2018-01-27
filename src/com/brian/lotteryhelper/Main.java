package com.brian.lotteryhelper;

import com.brian.lotteryhelper.data.LotteryDataHelper;


public class Main {

	public static void main(String[] args) {
//		LotteryTableHelper.createTable(); // 表格仅在第一次运行时创建
/////////////////////////////////////////////////////////////////
		// 更新漏掉的日期数据
//		ArrayList<String> dates = new ArrayList<>();
//		dates.add("20170826");
//		LotteryDataHelper.updateDataWithDates(dates);
		
		// 间隔一段时间需要更新数据
		LotteryDataHelper.updateDataFrom("20180127");
	}
	
}
