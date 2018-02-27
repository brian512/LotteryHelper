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
		LotteryDataHelper.queryDatasAtRangePerDay(1000, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				ArrayList<int[]> countArrayList = new ArrayList<>();
				
				int moneyLeft = 50_000;
				
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
//					 total += lotteryList.size();
					 String date = String.valueOf(lotteryList.get(0).lotteryId/1000L);
					 LogUtil.logln("\ndate=" + date + "\t" + getDayOfWeek(date));
					 int[] countArray = TestHelper.count(lotteryList, new Data1_2LGroup(), true);
					 countArrayList.add(countArray);
					 
//					 moneyLeft = TestHelper.testData(moneyLeft, lotteryList, new Data1_2LGroup(), 
//							 new int[] {0, 0, 0, 0, 0,// 0, 
////								20, 28, 45, 70, //120, //180, //210, 360
////								10, 20, 40, 60, 105, 180, 320, 555, 970, 1700, 2980, 5210, 9120, 15960, 27940, //48900
//								10, 14, 21, 35, //60, // 330, 560
////								2, 4, 8, 15, 25, 45, //80, 140, 250, 430, 750
////								10, 40, 20, 32, 56, 100, //80, 140, 250, 430, 750
////								2,3,4,7,12,21,37,64,//100,//168//,290,500,800//,100,100,
////								4, 8, 10, 17, 30, 52, 90, 155, //270, 470, //820, 1430, 2400, 400
//							},
//							true, // 是否打印每一期的数据
//							false // 是否打印止损的数据
//					 );
					 
					 
//					 total += moneyLeft-MONEY_INIT;
//					 LogUtil.logln("moneyLeft="+ moneyLeft);
//					 
//					 if (moneyLeft < 1000) {
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
		
		int count = 0;
		for (int i = 0; i < countSumArray.length; i++) {
			count += countSumArray[i]*(i+1);
			
		}
		int inTotal = countSumArray[0] * 560 
				+ countSumArray[1] * 5180
				+ countSumArray[2] * -3500
				+ countSumArray[3] * -3668
				+ countSumArray[4] * -3668
				+ countSumArray[5] * -3556
				;
		int unluckCnt = 0;
		for (int i = 6; i < countSumArray.length; i++) {
			unluckCnt += countSumArray[i];
		}
		
		LogUtil.logln("unluckCnt=" + unluckCnt);
		LogUtil.logln("inTotal=" + (inTotal- unluckCnt * 13356));
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
