package com.brian.lotteryhelper;

import java.util.ArrayList;
import java.util.Random;

import com.brian.lotteryhelper.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.DataGroup;


public class Main {

	public static void main(String[] args) {
//		LotteryTableHelper.createTable(); // 表格仅在第一次运行时创建
/////////////////////////////////////////////////////////////////
		
		// 间隔一段时间需要更新数据
		LotteryDataHelper.updateDataFrom("20180124");
		
		/**
		上面为数据更新
		
		
		
		
		
		下面为数据测试
		**/
		
		// 取最近 N 期 的数据
		LotteryDataHelper.queryLatestDatas(10_000, new OnQueryListener() {
			
			@Override
			public void onQuery(ArrayList<Lottery> lotteryList) {
				LogUtil.log("lotteryList=" + lotteryList.size());
			}
		});
		
		// 取最近 100 天，每天第 25期-第120期的数据
		LotteryDataHelper.queryDatasAtRangePerDay(7, 25, 120, new OnQueryListByDayListener() {
			
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				LogUtil.log("list=" + list.size());
				
//				for (ArrayList<Lottery> lotteryList : list) {
//					TestHelper.test1_2L(lotteryList);
//				}
			}
		});
		
	}
	
}
