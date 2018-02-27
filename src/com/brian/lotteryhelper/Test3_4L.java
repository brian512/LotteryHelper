package com.brian.lotteryhelper;

import java.util.ArrayList;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.data.LotteryDataHelper;
import com.brian.lotteryhelper.data.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.data.LotteryTableHelper.OnQueryListener;
import com.brian.lotteryhelper.group.Data3_4LGroup;

public class Test3_4L {

	
	public static void main(String[] args) {
		// 取最近 N 期 的数据
		LotteryDataHelper.queryLatestDatas(2_000, new OnQueryListener() {
			
			@Override
			public void onQuery(ArrayList<Lottery> lotteryList) {
				LogUtil.logln("lotteryList=" + lotteryList.size());
				
				TestHelper.test(50_000, lotteryList, new Data3_4LGroup(), 
						new int[] { 0,// 0, 0, 0, 0,
					10, 20, 40, 60, 100,// 150
				}, true, true);
			}
		});
		
		// 取最近 100 天，每天第 25期-第120期的数据
		LotteryDataHelper.queryDatasAtRangePerDay(30, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
					 LogUtil.logln("date=" + lotteryList.get(0).dateStr);
					 
//					 int moneyLeft = TestHelper.test(50_000, lotteryList, new Data3_4LGroup(42, 98), 
//							 new int[] { //0, 0, 0, 0, 0,
//								10, 20, 40, 60, 100//, 120
////								5, 10, 20, 35, 60, 90, 120
//							});
//					 
//					 total += moneyLeft-50_000;
//					 
//					 LogUtil.log(""+ (moneyLeft-50_000));
					 
					 TestHelper.count(lotteryList, new Data3_4LGroup(), false);
				 }
				 
				 
				 LogUtil.logln("total="+ total);
				 LogUtil.logln("\n");
			}
		});

	}
	
}
