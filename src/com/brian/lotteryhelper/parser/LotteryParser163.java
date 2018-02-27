package com.brian.lotteryhelper.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.brian.lotteryhelper.LogUtil;
import com.brian.lotteryhelper.data.Lottery;

public class LotteryParser163 {
	
	public static void main(String[] args) {
		fetchLotteryList("20180126");
	}
	
	public static ArrayList<Lottery> fetchLotteryList(String date) {
		String url = String.format("http://caipiao.163.com/award/cqssc/%s.html", date);
		try {
//			LogUtil.log("url=" + url);
			Document doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)")
					.get();
//			LogUtil.log("" + doc.html());
			return getLotteryList(doc);
		} catch (IOException e) {
			System.err.println("url=" + url);
			return null;
		}
	}
	
	private static ArrayList<Lottery> getLotteryList(Document rootDoc) {
		Element awardList = rootDoc.getElementsByClass("awardList").get(0);
		
		Elements rowElements = awardList.getElementsByClass("start");
		
		if (rowElements.isEmpty()) {
			LogUtil.logln("rowElements.isEmpty");
			return null;
		}
		
		ArrayList<Lottery> lotteryList = new ArrayList<Lottery>();
		for (int i = 1; i < rowElements.size(); i++) {
			Element row = rowElements.get(i);
			Lottery lottery = getLottery(row);
			if (lottery != null) {
				lotteryList.add(lottery);
			}
		}
		return lotteryList;
	}
	
	private static Lottery getLottery(Element row) {
//		LogUtil.log("row========\n" + row.toString());
		
//		LogUtil.log("text========" + row.text());
		
		
		if (!row.hasAttr("data-period") || !row.hasAttr("data-win-number")) {
			return null;
		}
		
		Lottery lottery = new Lottery();
		lottery.lotteryId = Long.valueOf(row.attr("data-period")) + 20000000000L;
		lottery.dateStr = getDateStr(lottery.lotteryId);
		String dataStr = row.attr("data-win-number");
		String[] numbers = dataStr.split(" ");
		if (numbers.length < 5) {
//			LogUtil.log("lotteryId=" + lottery.lotteryId + "; dataStr=" + dataStr);
			return null;
		}
		lottery.data1 = Integer.valueOf(numbers[0]);
		lottery.data2 = Integer.valueOf(numbers[1]);
		lottery.data3 = Integer.valueOf(numbers[2]);
		lottery.data4 = Integer.valueOf(numbers[3]);
		lottery.data5 = Integer.valueOf(numbers[4]);
		
//		LogUtil.log("lottery========" + lottery);
		
		return lottery;
	}
	
	public static String getDateStr(long period) {
		String month = String.format("%02d", (period/1000L%10000/100));
		String day = String.format("%02d", (period/1000L%10000%100));
		int index = (int) (period%1000L);
		
		int time = 0;
		if (1 <= index && index <= 23) {
			time = index/12*100 + 5*(index%12);
		} else if (24 <= index && index <= 95) {
			index = index - 24;
			time = 1000 + index/6*100 + 10*(index%6);
		} else if (96 <= index && index <= 119) {
			index = index - 96;
			time = 2200 + index/12*100 + 5*(index%12);
		}
		
		String timeStr = String.format("%02d:%02d", time/100, time%100);
		
		return month + "-" + day + " " + timeStr;
	}
}
/**
20130505	 lotteryCnt=115
20130819	 lotteryCnt=111
20131028	 lotteryCnt=115
20140105	 lotteryCnt=113
20140318	 lotteryCnt=119
20140327	 lotteryCnt=118
20140529	 lotteryCnt=118
20140530	 lotteryCnt=114
20140825	 lotteryCnt=105
20140827	 lotteryCnt=117
20140906	 lotteryCnt=119
20141226	 lotteryCnt=112
20150304	 lotteryCnt=118
20150305	 lotteryCnt=112
20150306	 lotteryCnt=98
20150307	 lotteryCnt=107
20150308	 lotteryCnt=95
20150310	 lotteryCnt=119
20150312	 lotteryCnt=111
20150313	 lotteryCnt=119
20150314	 lotteryCnt=118
20150315	 lotteryCnt=119
20150318	 lotteryCnt=119
20150324	 lotteryCnt=119
20150401	 lotteryCnt=106
20150405	 lotteryCnt=115
20150406	 lotteryCnt=113
20150407	 lotteryCnt=101
20150408	 lotteryCnt=119
20150409	 lotteryCnt=118
20150410	 lotteryCnt=115
20150411	 lotteryCnt=118
20150412	 lotteryCnt=118
20150413	 lotteryCnt=116
20150414	 lotteryCnt=110
20150415	 lotteryCnt=113
20150416	 lotteryCnt=119
20150417	 lotteryCnt=107
20150418	 lotteryCnt=114
20150419	 lotteryCnt=119
20150424	 lotteryCnt=118
20150425	 lotteryCnt=119
20150426	 lotteryCnt=108
20150428	 lotteryCnt=117
20150429	 lotteryCnt=113
20150430	 lotteryCnt=105
20150501	 lotteryCnt=118
20150505	 lotteryCnt=110
20150506	 lotteryCnt=119
20150507	 lotteryCnt=106
20150519	 lotteryCnt=115
20150520	 lotteryCnt=104
20150523	 lotteryCnt=108
20150524	 lotteryCnt=101
20150526	 lotteryCnt=119
20150528	 lotteryCnt=114
20150529	 lotteryCnt=111
20150531	 lotteryCnt=105
20150601	 lotteryCnt=107
20150602	 lotteryCnt=116
20150603	 lotteryCnt=118
20150611	 lotteryCnt=119
20150617	 lotteryCnt=119
20150618	 lotteryCnt=116
20150620	 lotteryCnt=116
20150621	 lotteryCnt=110
20150625	 lotteryCnt=113
20150628	 lotteryCnt=111
20150701	 lotteryCnt=104
20150707	 lotteryCnt=113
20150712	 lotteryCnt=102
20150713	 lotteryCnt=119
20150714	 lotteryCnt=111
20150715	 lotteryCnt=109
20150717	 lotteryCnt=114
20150718	 lotteryCnt=109
20150719	 lotteryCnt=116
20150720	 lotteryCnt=117
20150721	 lotteryCnt=117
20150723	 lotteryCnt=116
20150724	 lotteryCnt=112
20150725	 lotteryCnt=112
20150726	 lotteryCnt=108
20150729	 lotteryCnt=119
20150730	 lotteryCnt=119
20150731	 lotteryCnt=110
20150801	 lotteryCnt=102
20150802	 lotteryCnt=118
20150803	 lotteryCnt=119
20150806	 lotteryCnt=119
20150807	 lotteryCnt=117
20150809	 lotteryCnt=118
20150824	 lotteryCnt=119
20150825	 lotteryCnt=118
20150826	 lotteryCnt=119
20150828	 lotteryCnt=115
20150829	 lotteryCnt=113
20150831	 lotteryCnt=119
20150901	 lotteryCnt=117
20150902	 lotteryCnt=118
20150903	 lotteryCnt=115
20150904	 lotteryCnt=119
20150905	 lotteryCnt=117
20150906	 lotteryCnt=119
20150907	 lotteryCnt=119
20150909	 lotteryCnt=116
20150912	 lotteryCnt=117
20150913	 lotteryCnt=119
20150914	 lotteryCnt=118
20150917	 lotteryCnt=119
20151002	 lotteryCnt=118
20151018	 lotteryCnt=119
20151118	 lotteryCnt=119
20151122	 lotteryCnt=119
20151123	 lotteryCnt=119
20151124	 lotteryCnt=119
20151125	 lotteryCnt=113
20151127	 lotteryCnt=119
20151203	 lotteryCnt=119
20151206	 lotteryCnt=118
20151207	 lotteryCnt=119
20151219	 lotteryCnt=119
20151220	 lotteryCnt=119
20151223	 lotteryCnt=119
20151230	 lotteryCnt=119
20160201	 lotteryCnt=119
20160215	 lotteryCnt=118
20160225	 lotteryCnt=119
20160229	 lotteryCnt=119
20160302	 lotteryCnt=119
20160311	 lotteryCnt=119
20160405	 lotteryCnt=119
20160420	 lotteryCnt=118
20160512	 lotteryCnt=105
20160530	 lotteryCnt=119
20160601	 lotteryCnt=117
20160610	 lotteryCnt=118
20160702	 lotteryCnt=119
20160705	 lotteryCnt=119
20160707	 lotteryCnt=119
20160710	 lotteryCnt=117
20160802	 lotteryCnt=119
20170126	 lotteryCnt=119
20170203	 lotteryCnt=119
20170321	 lotteryCnt=119
20170826	 lotteryCnt=110
20171219	 lotteryCnt=98
20171223	 lotteryCnt=97
20171227	 lotteryCnt=97
20180125	 lotteryCnt=97
**/