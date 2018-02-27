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
import com.brian.lotteryhelper.group.DataGroup;

public class Test1_2LQueue {

	
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
		LotteryDataHelper.queryDatasAtRangePerDay(200, 25, 110, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				ArrayList<int[]> countArrayList = new ArrayList<>();
				
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
//					 total += lotteryList.size();
//					 String date = String.valueOf(lotteryList.get(0).lotteryId/1000L);
//					 LogUtil.logln("\ndate=" + date + "\t" + getDayOfWeek(date));
//					 int[] countArray = TestHelper.count(lotteryList, new Data1_2LGroup(), false);
//					 countArrayList.add(countArray);
					 
					 test(lotteryList, new Data1_2LGroup());
				 }
			}
		});

	}
	
	private static void test(ArrayList<Lottery> lotteryList, DataGroup dataGroup) {
		int luckyCnt = 0;
		int[] arrayLengthCnt = new int[100];
		int[] unluckCntArray = new int[100];
		
		int unluckyCntAfter6 = 0;
		
		for (int i = 0; i < lotteryList.size(); i++) {
			Lottery lottery = lotteryList.get(i);
			if (dataGroup.checkLucky(lottery)) {
				luckyCnt++;
				
				unluckCntArray[unluckyCntAfter6]++;
				unluckyCntAfter6 = 0;
			} else {
				arrayLengthCnt[luckyCnt]++;
				if ((luckyCnt > 6 && unluckyCntAfter6==0) || unluckyCntAfter6 > 0) {
					unluckyCntAfter6++;
				}
				
				luckyCnt = 0;
			}
		}
		if (ArrayUtil.trim(arrayLengthCnt).length > 6) {
			ArrayUtil.printArray(arrayLengthCnt);
		}
		ArrayUtil.printArray(unluckCntArray);
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
