package com.brian.lotteryhelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.brian.lotteryhelper.LotteryTableHelper.OnQueryListener;

public class LotteryDataHelper {

	public interface OnQueryListByDayListener {
		void onQuery(ArrayList<ArrayList<Lottery>> list);
	}
	
	public static void queryDatasAtRangePerDay(final int dayCnt, int start, int end, final OnQueryListByDayListener listener) {
		if (start <= 0 || end > 120 || start >= end) {
			LogUtil.logError("wrong params start=" + start + "; end=" + end);
			return;
		}
		
		final ArrayList<ArrayList<Lottery>> list = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = 2018;
		int today = calendar.get(Calendar.DAY_OF_YEAR);
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
			String dateStart = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
					+ String.format("%03d", start);
			String dateEnd = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
					+ String.format("%03d", end);
//			LogUtil.log("date=" + date);
			
			LotteryTableHelper.queryDatasAtRange(Long.valueOf(dateStart), Long.valueOf(dateEnd), new OnQueryListener() {
				@Override
				public void onQuery(ArrayList<Lottery> lotteryList) {
					Collections.reverse(lotteryList);
					list.add(lotteryList);
					if (list.size() == dayCnt) {
						listener.onQuery(list);
					}
				}
			} );
		}
		
	}
	
	
	
	/**
	 * 查询最近的数据
	 * @param count
	 * @param listener
	 */
	public static void queryLatestDatas(int count, final OnQueryListener listener) {
		LotteryTableHelper.queryLatestDatas(count, new OnQueryListener() {
			@Override
			public void onQuery(ArrayList<Lottery> lotteryList) {
				Collections.reverse(lotteryList);
				listener.onQuery(lotteryList);
			}
		} );
	}
	
	
	
	
	
	public static void updateDataFromDate(String date) {
		ArrayList<Lottery> lotteries = LotteryParser.fetchLotteryList(date);
		if (lotteries == null) {
			LogUtil.logError("date=" + date);
			return;
		}
		if (lotteries.size() < 120) {
			LogUtil.log(date + "\t lotteryCnt=" + lotteries.size());
		}
		LotteryTableHelper.insertLotteries(lotteries);
	}
	
	
	public static void updateDataWithDates(ArrayList<String> dates) {
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
	
	public static void updateDataFrom(String dateStr) {
		Date dateStart;
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			dateStart = format.parse(dateStr);
			LogUtil.log("dateStart=" + dateStart);
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		Calendar TODAY = Calendar.getInstance();
		TODAY.setTime(new Date());
		int TODAY_DD = TODAY.get(Calendar.DAY_OF_YEAR);
		int TODAY_YYYY = TODAY.get(Calendar.YEAR);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStart);
		int year = calendar.get(Calendar.YEAR);
		int currDay = calendar.get(Calendar.DAY_OF_YEAR);
		for (int dayOfYear = currDay; year <= TODAY_YYYY; dayOfYear++) {
			if (year == TODAY_YYYY && dayOfYear > TODAY_DD) {
				break;
			}
			if (dayOfYear > calendar.getActualMaximum(Calendar.DAY_OF_YEAR)) {
				dayOfYear = 1;
				year += 1;
				calendar.set(Calendar.YEAR, year);
			}
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			String date = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
			LogUtil.log("date=" + date);
			
			
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
