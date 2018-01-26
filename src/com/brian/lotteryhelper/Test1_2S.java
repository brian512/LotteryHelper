package com.brian.lotteryhelper;

import java.util.ArrayList;

import com.brian.lotteryhelper.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.Data1_2SGroup;

public class Test1_2S {

	
	public static void main(String[] args) {
		// 取最近 N 期 的数据
//		LotteryDataHelper.queryLatestDatas(2_000, new OnQueryListener() {
//			
//			@Override
//			public void onQuery(ArrayList<Lottery> lotteryList) {
//				LogUtil.log("lotteryList=" + lotteryList.size());
//				
//				TestHelper.test(50_000, lotteryList, new Data1_2LGroup(42, 98), 
//						new int[] { 0,// 0, 0, 0, 0,
//					10, 20, 40, 60, 100,// 150
//				});
//			}
//		});
		
		// 取最近 100 天，每天第 25期-第120期的数据
		LotteryDataHelper.queryDatasAtRangePerDay(50, 25, 120, new OnQueryListByDayListener() {

					@Override
					public void onQuery(ArrayList<ArrayList<Lottery>> list) {
						LogUtil.log("list=" + list.size());

						 for (ArrayList<Lottery> lotteryList : list) {
//						 TestHelper.test(50_000, lotteryList, new Data1_2LGroup(42, 98), 
//									new int[] { 0,// 0, 0, 0, 0,
//								10, 20, 40, 60, 100,// 150
//							});
							 if (lotteryList.size()<= 0) {
								continue;
							}
							 LogUtil.log("date=" + lotteryList.get(0).dateStr);
							 TestHelper.count(lotteryList, new Data1_2LGroup(42, 98));
							 
							 LogUtil.log("\n\n");
						 }
					}
				});

	}
	
}
