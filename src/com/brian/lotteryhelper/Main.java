package com.brian.lotteryhelper;


public class Main {

	public static void main(String[] args) {
//		LotteryTableHelper.createTable(); // 表格仅在第一次运行时创建
/////////////////////////////////////////////////////////////////
		
		// 间隔一段时间需要更新数据
		LotteryDataHelper.updateDataFrom("20180125");
		
	}
	
}
