package com.brian.lotteryhelper.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import com.brian.lotteryhelper.LogUtil;
import com.brian.lotteryhelper.data.LotteryTableHelper.OnQueryListener;
import com.brian.lotteryhelper.parser.LotteryParser163;

/**
 * 重庆时时彩数据从20120101开始
 */
public class LotteryDataHelper {

	public interface OnQueryListByDayListener {
		void onQuery(ArrayList<ArrayList<Lottery>> list);
	}
	
	public static void queryDatasAtRangePerDay(int dayCnt, int start, int end, final OnQueryListByDayListener listener) {
		if (start <= 0 || end > 120 || start >= end) {
			LogUtil.logError("wrong params start=" + start + "; end=" + end);
			return;
		}
		final int DAYCNT = dayCnt;
		
		
		final ArrayList<ArrayList<Lottery>> list = new ArrayList<>();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = 2018;
		int today = calendar.get(Calendar.DAY_OF_YEAR);
		for (int dayOfYear = today; dayCnt > 0; dayOfYear--, dayCnt--) {
			if (dayOfYear == 0) {
				year -= 1;
				calendar.set(Calendar.YEAR, year);
				dayOfYear = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
				// 数据从2015-07-01开始
				if (year <= 2012 && calendar.get(Calendar.MONTH) < 0) {
					break;
				}
			}
			calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
			String periodStart = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
					+ String.format("%03d", start);
			String periodEnd = String.valueOf(calendar.get(Calendar.YEAR)) 
					+ String.format("%02d", calendar.get(Calendar.MONTH) + 1)
					+ String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
					+ String.format("%03d", end);
//			LogUtil.log("periodStart=" + periodStart + "; periodEnd=" + periodEnd);
			
			LotteryTableHelper.queryDatasAtRange(Long.valueOf(periodStart)-1, Long.valueOf(periodEnd)+1, new OnQueryListener() {
				@Override
				public void onQuery(ArrayList<Lottery> lotteryList) {
					Collections.reverse(lotteryList);
					list.add(lotteryList);
					if (list.size() == DAYCNT) {
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
	
	
	
	public static void updateDataWithDates(ArrayList<String> dates) {
		for (String date : dates) {
			ArrayList<Lottery> lotteries = LotteryParser163.fetchLotteryList(date);
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
//			LogUtil.log("date=" + date);
			
			
			ArrayList<Lottery> lotteries = LotteryParser163.fetchLotteryList(date);
//			ArrayList<Lottery> lotteries = LotteryParser.fetchLotteryList(date);
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

}
