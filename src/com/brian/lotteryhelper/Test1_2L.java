package com.brian.lotteryhelper;

import java.util.ArrayList;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.data.LotteryDataHelper;
import com.brian.lotteryhelper.data.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;

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
		
		// 024期是，凌晨两点下注，十点钟开奖，所以需要从第25期开始
		LotteryDataHelper.queryDatasAtRangePerDay(2, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
					 LogUtil.log("\ndate=" + lotteryList.get(0).lotteryId/1000L);
					 TestHelper.count(lotteryList, new Data1_2LGroup(), false);
					 
					 final int MONEY_INIT = 100_000;
					 
					 int moneyLeft = TestHelper.test(MONEY_INIT, lotteryList, new Data1_2LGroup(), 
							 new int[] {//0, //0, 0, 0, 0,
								20, 25, 35,// 60, //100,// 160
//								10, 20, 40, 60, 100//, 120
//								10, 20, 40, 80, 150,// 90, 120
//								5, 10, 20, 35, 60,// 90, 120
							},
							true, // 是否打印每一期的数据
							true // 是否打印止损的数据
					 );
					 
					 total += moneyLeft-MONEY_INIT;
				 }
				 
				 
				 LogUtil.log("total="+ total);
				 LogUtil.log("\n");
			}
		});


	}
	
}
