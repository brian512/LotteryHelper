package com.brian.lotteryhelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;


public class Main {

	public static void main(String[] args) {
//		LotteryTableHelper.createTable();
		
//		ArrayList<String> dates = new ArrayList<>();
//		dates.add("20180121");
//		refreshExeptionDate(dates);
		
//		updateHistoryData();
		
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(new Date());
//		int year = 2018;
//		int today = calendar.get(Calendar.DAY_OF_YEAR); // 20180121
//		for (int dayOfYear = today; dayOfYear >= 0; dayOfYear--) {
//			if (dayOfYear == 0) {
//				year -= 1;
//				calendar.set(Calendar.YEAR, year);
//				dayOfYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
//				// 数据从2015-07-01开始
//				if (year <= 2015 && calendar.get(Calendar.MONTH) < 6) {
//					break;
//				}
//			}
//			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
//			String dateStart = String.valueOf(calendar.get(Calendar.YEAR)) 
//					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
//					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
//					+ "072";
//			String dateEnd = String.valueOf(calendar.get(Calendar.YEAR)) 
//					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
//					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
//					+ "096";
////			LogUtil.log("date=" + date);
//			
//			LotteryTableHelper.queryDatasAtRange(Long.valueOf(dateStart), Long.valueOf(dateEnd), new OnQueryListener() {
//				@Override
//				public void onQuery(ArrayList<Lottery> lotteryList) {
//					Collections.reverse(lotteryList);
//					test1_2L(lotteryList);
//				}
//			} );
//		}
		
		
		LotteryTableHelper.queryLatestDatas(2000, new OnQueryListener() {
			@Override
			public void onQuery(ArrayList<Lottery> lotteryList) {
				Collections.reverse(lotteryList);
				test1_2L(lotteryList);
			}
		} );
	}
	
	
	
	private static void test1_2L(ArrayList<Lottery> lotteryList) {
		int[] VALUES = new int[] {
				10, 20, 40, 60, 100
		};
		test(lotteryList, 42, 1, VALUES);
	}
	
	private static void test(ArrayList<Lottery> lotteryList, int numberCnt, int startIndex, final int[] VALUES) {
		
		int ownMoney = 50_000;
		
		int inputMoney = 0; // 当期投入的金额
		int inputMoneyPerNum = 0; // 每一组的金额
		int inputMoneySum = 0; // 当前规律投入的总金额
		
		int unluckyCnt = 0;
		
		String outPutMsg = null;
		
		for (Lottery lottery : lotteryList) {
			outPutMsg = lottery.toString();
			
			if (unluckyCnt >= startIndex) {
				int followCnt = unluckyCnt - startIndex;
				inputMoneyPerNum = VALUES[followCnt];
				inputMoney = inputMoneyPerNum * numberCnt;
				inputMoneySum += inputMoney;
				
				if (inputMoney > ownMoney) {
					LogUtil.logError("余额不足");
					break;
				}
			} else {
				inputMoneyPerNum = 0;
				inputMoneySum = 0;
				inputMoney = 0;
			}
			outPutMsg += "\t" + inputMoney;
			outPutMsg += "\t" + inputMoneySum;
			
			if (TestHelper.check1_2Big(lottery)) {
				unluckyCnt = 0;
				
				ownMoney += inputMoneyPerNum * 98;
			} else {
				unluckyCnt++;
				
				ownMoney -= inputMoney;
			}
			outPutMsg += "\t" + ownMoney;
			
			LogUtil.log(outPutMsg);
			
			if (unluckyCnt > VALUES.length) {
				LogUtil.logError("inputMoneySum=" + inputMoneySum 
						+ "**********************************************************************");
				inputMoney = 0;
				inputMoneySum = 0;
				unluckyCnt = 0;
			}
			
		}
		
		LogUtil.log("finish####################ownMoney=" + ownMoney);
//		LogUtil.log("finish####################");
//		LogUtil.log("finish####################");
	}
	
	
	
	private static void refreshExeptionDate(ArrayList<String> dates) {
		for (String date : dates) {
			ArrayList<Lottery> lotteries = LotteryParser.fetchLotteryList(date);
			if (lotteries == null) {
				LogUtil.logError("date=" + date);
				continue;
			}
			if (lotteries.size() < 120) {
				LogUtil.log(date + "\t lotteryCnt=" + lotteries.size());
			}
			LotteryTableHelper.insertLotteries(lotteries);
		}
	}
	

	private static void updateHistoryData() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = 2018;
		int today = calendar.get(Calendar.DAY_OF_YEAR); // 20180121
		for (int dayOfYear = today; dayOfYear >= 0; dayOfYear--) {
			if (dayOfYear == 0) {
				year -= 1;
				calendar.set(Calendar.YEAR, year);
				dayOfYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				// 数据从2015-07-01开始
				if (year <= 2015 && calendar.get(Calendar.MONTH) < 6) {
					break;
				}
			}
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			String date = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
//			LogUtil.log("date=" + date);
			
			
			ArrayList<Lottery> lotteries = LotteryParser.fetchLotteryList(date);
			if (lotteries == null) {
				LogUtil.logError("date=" + date);
				continue;
			}
			if (lotteries.size() < 120) {
				LogUtil.log(date + "\t lotteryCnt=" + lotteries.size());
			}
			LotteryTableHelper.insertLotteries(lotteries);
		}
	}
/**
 
20171227	 lotteryCnt=97
20171223	 lotteryCnt=97
20171219	 lotteryCnt=98
20170826	 lotteryCnt=109
20170413	 lotteryCnt=118
20170412	 lotteryCnt=119
20170411	 lotteryCnt=114
20170321	 lotteryCnt=118
20170203	 lotteryCnt=118


20161218	 lotteryCnt=118
20161122	 lotteryCnt=119
20160802	 lotteryCnt=119
20160710	 lotteryCnt=117
20160707	 lotteryCnt=119
20160705	 lotteryCnt=119
20160702	 lotteryCnt=119
20160610	 lotteryCnt=118
20160601	 lotteryCnt=117
20160530	 lotteryCnt=119
20160517	 lotteryCnt=118
20160516	 lotteryCnt=116
20160512	 lotteryCnt=105
20160420	 lotteryCnt=118
20160405	 lotteryCnt=119
20160311	 lotteryCnt=119
20160302	 lotteryCnt=119
20160229	 lotteryCnt=119
20160225	 lotteryCnt=119
20160215	 lotteryCnt=118


20160201	 lotteryCnt=119
20151230	 lotteryCnt=119
20151223	 lotteryCnt=119
20151220	 lotteryCnt=119
20151219	 lotteryCnt=119
20151207	 lotteryCnt=119
20151206	 lotteryCnt=118
20151203	 lotteryCnt=119
20151125	 lotteryCnt=113
20151124	 lotteryCnt=119
20151123	 lotteryCnt=119
20151118	 lotteryCnt=119
20151018	 lotteryCnt=119
20150917	 lotteryCnt=119
20150914	 lotteryCnt=118
20150913	 lotteryCnt=119
20150912	 lotteryCnt=117
20150909	 lotteryCnt=116
20150907	 lotteryCnt=119
20150906	 lotteryCnt=119
20150905	 lotteryCnt=117
20150904	 lotteryCnt=119
20150903	 lotteryCnt=115
20150902	 lotteryCnt=118
20150901	 lotteryCnt=117
20150831	 lotteryCnt=119
20150829	 lotteryCnt=113
20150828	 lotteryCnt=115
20150826	 lotteryCnt=119
20150825	 lotteryCnt=118
20150824	 lotteryCnt=119
20150809	 lotteryCnt=118
20150807	 lotteryCnt=117
20150806	 lotteryCnt=119
20150803	 lotteryCnt=119
20150802	 lotteryCnt=118
20150801	 lotteryCnt=102
20150731	 lotteryCnt=110
20150730	 lotteryCnt=119
20150729	 lotteryCnt=119
20150726	 lotteryCnt=108
20150725	 lotteryCnt=112
20150724	 lotteryCnt=112
20150723	 lotteryCnt=116
20150721	 lotteryCnt=117
20150720	 lotteryCnt=117
20150719	 lotteryCnt=116
20150718	 lotteryCnt=109
20150717	 lotteryCnt=114
20150715	 lotteryCnt=109
20150714	 lotteryCnt=111
20150713	 lotteryCnt=119
20150712	 lotteryCnt=102
20150707	 lotteryCnt=113
20150701	 lotteryCnt=103


 */
	
	
	
}
