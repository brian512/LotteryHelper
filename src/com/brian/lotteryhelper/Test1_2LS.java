package com.brian.lotteryhelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.data.LotteryDataHelper;
import com.brian.lotteryhelper.data.LotteryDataHelper.OnQueryListByDayListener;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.Data1_2SGroup;
import com.brian.lotteryhelper.group.Data1_2_49LGroup;
import com.brian.lotteryhelper.group.DataGroup;

public class Test1_2LS {

	
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
		LotteryDataHelper.queryDatasAtRangePerDay(200, 25, 120, new OnQueryListByDayListener() {
			@Override
			public void onQuery(ArrayList<ArrayList<Lottery>> list) {
				int total = 0;
				ArrayList<int[]> countArrayList = new ArrayList<>();
				
				int moneyLeft = 50_000;
				
				 for (ArrayList<Lottery> lotteryList : list) {
					 if (lotteryList.size()<= 0) {
						continue;
					 }
					 
					 int[] price = new int[]{
							 10, 20, 40, 60, 100
					 };
					 String date = String.valueOf(lotteryList.get(0).lotteryId/1000L);
					 LogUtil.logln("\ndate=" + date + "\t" + getDayOfWeek(date));
					 int[] countArray = TestHelper.count(lotteryList, new Data1_2_49LGroup(), true, price.length+5);
					 countArrayList.add(countArray);
					 
//					 moneyLeft = testData(moneyLeft, lotteryList, new Data1_2LGroup(), price, true, false);
//					 
//					 if (moneyLeft < 1000) {
//						break;
//					}
				 }
				 
				 
				 LogUtil.logln("\n");
				 
				 anaylyCountArray(countArrayList);
			}
		});


	}
	
	public static int testData(int ownMoney, ArrayList<Lottery> lotteryList, 
			DataGroup dataGroup, int[] price, boolean logPerLottery, boolean logLostSum) {
		final int MONEY_INIT = ownMoney;
		
		Data1_2SGroup data1_2sGroup = new Data1_2SGroup();
		
		int inputMoney = 0; // 当期投入的金额
		int inputMoneyPerNum = 0; // 每一组的金额
		int inputMoneySum = 0; // 当前规律投入的总金额
		
		int unluckyCnt = 0;
		int stopLossCnt = 0;
		
		String outPutMsg = null;
		
		if (logPerLottery) {
			LogUtil.logln("期号\t" + "       日期\t时间\t" + "     号码\t\t" 
					+ "当期下注\t" + "累计\t" + "盈利\t" + "结余\t");
		}
		
		for (int i = 0; i < lotteryList.size(); i++) {
			Lottery lottery = lotteryList.get(i);
			outPutMsg = lottery.toString();
			
			if (unluckyCnt < price.length) {
				inputMoneyPerNum = price[unluckyCnt];
				inputMoney = inputMoneyPerNum * dataGroup.getGroupCnt();
				inputMoneySum += inputMoney;
				
				if (inputMoney > ownMoney) {
					LogUtil.logError("余额不足");
					break;
				} else {
					ownMoney -= inputMoney;
				}
			} else {
				inputMoneyPerNum = 0;
				inputMoneySum = 0;
				inputMoney = 0;
			}
			outPutMsg += "\t" + inputMoney;
			outPutMsg += "\t" + inputMoneySum;
			
			boolean isLucky = dataGroup.checkLucky(lottery);
//			if (unluckyCnt >= 4) {
//				isLucky = data1_2sGroup.checkLucky(lottery);
//			}
			
			if (isLucky) {
				outPutMsg += "\t+" + (inputMoneyPerNum * (dataGroup.getOdds()-dataGroup.getGroupCnt()));
				ownMoney += inputMoneyPerNum * dataGroup.getOdds();
				
				unluckyCnt = 0;
				inputMoney = 0;
				inputMoneySum = 0;
				
//				if (ownMoney - MONEY_INIT > 2_000) {
//					break;
//				}
			} else {
				unluckyCnt++;
				outPutMsg += "\t-" + (inputMoneyPerNum * dataGroup.getGroupCnt());
			}
			
			outPutMsg += "\t" + ownMoney;
			
			if (logPerLottery) {
				LogUtil.logLottery(outPutMsg);
			}
			
			if (unluckyCnt >= price.length && inputMoney > 0) {
				stopLossCnt++;
				if (logLostSum) {
					LogUtil.logUnlucky("**********************************************");
					LogUtil.logUnlucky(lottery.lotteryId + "\tlossMoneySum=" + inputMoneySum);
					LogUtil.logUnlucky("**********************************************");
				}
				inputMoney = 0;
				inputMoneySum = 0;
				
//				if (stopLossCnt >= 2) {
//					break;
//				}
			}
			
		}
		
		
		LogUtil.logln("\nstopLossCnt=" + stopLossCnt);
		if ((ownMoney-MONEY_INIT) < 0) {
			LogUtil.logError("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		} else {
			LogUtil.logMoneySum("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		}
		LogUtil.logln("\n");
		return ownMoney;
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
