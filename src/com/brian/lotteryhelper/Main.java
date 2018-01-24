package com.brian.lotteryhelper;

import java.util.ArrayList;
import java.util.Random;

import com.brian.lotteryhelper.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;


public class Main {

	public static void main(String[] args) {
//		LotteryTableHelper.createTable(); // 表格仅在第一次运行时创建
/////////////////////////////////////////////////////////////////
		
		// 间隔一段时间需要更新数据
//		LotteryDataHelper.updateDataFrom("20180122");
		
		/**
		上面为数据更新
		
		
		
		
		
		下面为数据测试
		**/
		
		// 取最近 N 期 的数据
		LotteryDataHelper.queryLatestDatas(2000, new OnQueryListener() {
			
			@Override
			public void onQuery(ArrayList<Lottery> lotteryList) {
				LogUtil.log("lotteryList=" + lotteryList.size());
				
				// 测试前两数大
//				TestHelper.test1_2L(lotteryList);
//				TestHelper.test1_2S(lotteryList);
//				TestHelper.test1_2LS(lotteryList);
//				TestHelper.count1_2L(lotteryList);
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
		
		test();
	}
	
	private static void test() {
		Random random = new Random();
		int unluckCnt = 0;
		int total = 50_000;
		
		int singlePrice = 1;
		
		int[] VALUES = new int[] {
				0, 0, 10, 20, 40, 60, 100
		};
		
		for (int i = 0; i < 1000; i++) {
			int num = random.nextInt(100);
			
			if (unluckCnt < VALUES.length) {
				singlePrice = VALUES[unluckCnt];
				total -= singlePrice*42;
				
				if (total < 0) {
					break;
				}
			} else {
				singlePrice = 0;
			}
			if (num >= 42) {
				unluckCnt++;
			} else {
				unluckCnt = 0;
				total += 98*singlePrice;
			}
			
			LogUtil.log("count" + (i+1) + "\t\t " + num + "\t" + singlePrice + "\t" + total);
		}
	}
	
	
}
