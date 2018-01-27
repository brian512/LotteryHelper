package com.brian.lotteryhelper.parser;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.brian.lotteryhelper.LogUtil;
import com.brian.lotteryhelper.data.Lottery;

public class LotteryParser {
	
	
	
	public static ArrayList<Lottery> fetchLotteryList(String date) {
		String url = String.format("http://kj.13322.com/ssc_cqssc_history_d%s.html", date);
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
		Element tableNode = rootDoc.getElementById("trend_table");
		if (tableNode == null) {
			return null;
		}
		
		Elements rowElements = tableNode.getElementsByTag("tr");
		
		if (rowElements.isEmpty()) {
			LogUtil.log("rowElements.isEmpty");
			return null;
		}
		
		ArrayList<Lottery> lotteryList = new ArrayList<Lottery>();
		for (int i = 0; i < rowElements.size(); i++) {
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
		
//		LogUtil.log("text========\n" + row.text());
		
		Elements datas = row.getElementsByClass("subtab");
		if (datas == null || datas.isEmpty()) {
			return null;
		}
		Elements tabs = row.getElementsByTag("td");
		if (tabs == null || tabs.size() <= 3) {
			return null;
		}
		
		Elements numberNodes = tabs.get(2).getElementsByClass("Ballsc_blue");
//		LogUtil.log("numberNodes========" + numberNodes.toString());
		if (numberNodes == null || numberNodes.size() < 5) {
			return null;
		}
		
		Lottery lottery = new Lottery();
		lottery.lotteryId = Long.valueOf(tabs.get(1).text());
		lottery.dateStr = LotteryParser163.getDateStr(lottery.lotteryId);
		lottery.data1 = Integer.valueOf(numberNodes.get(0).text());
		lottery.data2 = Integer.valueOf(numberNodes.get(1).text());
		lottery.data3 = Integer.valueOf(numberNodes.get(2).text());
		lottery.data4 = Integer.valueOf(numberNodes.get(3).text());
		lottery.data5 = Integer.valueOf(numberNodes.get(4).text());
		
//		LogUtil.log("lottery========" + lottery);
		
		return lottery;
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
