package com.brian.lotteryhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.data.LotteryDataHelper;
import com.brian.lotteryhelper.data.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.group.Data1764Group;

public class Test1764 {

	
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
		LotteryDataHelper.queryDatasAtRangePerDay(2000, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				ArrayList<int[]> countArrayList = new ArrayList<>();
				
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
//					 total += lotteryList.size();
					 String date = String.valueOf(lotteryList.get(0).lotteryId/1000L);
					 LogUtil.logln("\ndate=" + date + "\t" + getDayOfWeek(date));
					 int[] countArray = TestHelper.count(lotteryList, new Data1764Group(), false);
					 countArrayList.add(countArray);
				 }
				 
				 
				 LogUtil.logln("\n");
				 
				 anaylyCountArray(countArrayList);
			}
		});


	}
	
	private static void anaylyCountArray(ArrayList<int[]> countArrayList) {
		int[] arrayLengthCnt = new int[100];
		
		int[] countSumArray = new int[100];
		
		for (int[] countArray : countArrayList) {
			arrayLengthCnt[countArray.length]++;
			for (int i = 0; i < countArray.length; i++) {
				countSumArray[i] += countArray[i];
			}
		}
		ArrayUtil.printArray(arrayLengthCnt);
		ArrayUtil.printArray(countSumArray);
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
