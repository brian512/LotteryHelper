package com.brian.lotteryhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.data.LotteryDataHelper;
import com.brian.lotteryhelper.data.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.Data1_2_49LGroup;

public class Test1_2_49 {

	
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
		LotteryDataHelper.queryDatasAtRangePerDay(100, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				ArrayList<int[]> countArrayList = new ArrayList<>();
				
				int moneyLeft = 10_000;
				int total = moneyLeft;
				
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
					 int[] price = new int[] {10, 10, 10, //1,//10, 10, 10 
							 };
					 String date = String.valueOf(lotteryList.get(0).lotteryId/1000L);
					 LogUtil.logln("\ndate=" + date + "\t" + getDayOfWeek(date));
					 int[] countArray = TestHelper.count(lotteryList, new Data1_2_49LGroup(), false, price.length+5);
					 countArrayList.add(countArray);
//					 for (int i = 0; i < countArray.length; i++) {
//							if (i < price.length) {
//								total += countArray[i]*49;
//							}
//							int temp = i > price.length ? price.length : i;
//							total -= temp * countArray[i]*49;
//						}
//						LogUtil.logln("total=" + total);
//					 
//					 moneyLeft = TestHelper.testData(moneyLeft, lotteryList, new Data1_2_49LGroup(), 
//							 price,
//							 true, // 是否打印每一期的数据
//							false // 是否打印止损的数据
//					 );
//					 
//					 
////					 total += moneyLeft-MONEY_INIT;
//					 LogUtil.logln("moneyLeft="+ moneyLeft);
//					 
//					 if (moneyLeft < price[0]*100) {
//						 break;
//					 }
				 }
				 
				 
				 LogUtil.logln("\n");
				 
				 anaylyCountArray(countArrayList);
			}
		});


	}
	
	private static void anaylyCountArray(ArrayList<int[]> countArrayList) {
		int[] arrayLengthCnt = new int[30];
		
		int[] countSumArray = new int[30];
		
		for (int[] countArray : countArrayList) {
			arrayLengthCnt[countArray.length]++;
			for (int i = 0; i < countArray.length; i++) {
				countSumArray[i] += countArray[i];
			}
		}
		ArrayUtil.printArray(arrayLengthCnt);
		ArrayUtil.printArray(countSumArray);
		
		int CNT = 1;
//		int total = 10_000;
//		int[] countArray = countSumArray;
//		for (int i = 0; i < countArray.length; i++) {
//			if (i < CNT) {
//				total += countArray[i]*49;
//			}
//			int temp = i > CNT ? CNT : i;
//			total -= temp * countArray[i]*49;
//		}
//		LogUtil.logln("total=" + total);
	}
	
	
	private static String getDayOfWeek(String date) {
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(new SimpleDateFormat("yyyyMMdd").parse(date));
			int week = calendar.get(Calendar.DAY_OF_WEEK);
			switch (week) {
			case Calendar.MONDAY:
				return "星期一";
			case Calendar.TUESDAY:
				return "星期二";
			case Calendar.WEDNESDAY:
				return "星期三";
			case Calendar.THURSDAY:
				return "星期四";
			case Calendar.FRIDAY:
				return "星期五";
			case Calendar.SATURDAY:
				return "星期六";
			case Calendar.SUNDAY:
				return "星期日";
			default:
				return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
}
