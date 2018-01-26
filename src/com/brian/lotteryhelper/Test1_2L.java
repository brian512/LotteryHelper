package com.brian.lotteryhelper;

import java.util.ArrayList;

import com.brian.lotteryhelper.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.Data3_4LGroup;

public class Test1_2L {

	
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
		
		LotteryDataHelper.queryDatasAtRangePerDay(20, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
					 LogUtil.log("date=" + lotteryList.get(0).dateStr);
					 
					 int moneyLeft = TestHelper.test(50_000, lotteryList, new Data3_4LGroup(42, 98), 
							 new int[] { 0, //0, 0, 0, 0,
//								20, 30, 45, 75, //125//, 120
//								10, 20, 40, 60, 100//, 120
								10, 20, 40, 80, 150,// 90, 120
//								5, 10, 20, 35, 60,// 90, 120
							});
					 
					 total += moneyLeft-50_000;
					 
//					 TestHelper.count(lotteryList, new Data1_2LGroup(42, 98));
				 }
				 
				 
				 LogUtil.log("total="+ total);
				 LogUtil.log("\n");
			}
		});


	}
	
}
