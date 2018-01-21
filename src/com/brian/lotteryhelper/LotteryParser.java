package com.brian.lotteryhelper;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		lottery.dateStr = tabs.get(0).text();
		lottery.lotteryId = Long.valueOf(tabs.get(1).text());
		lottery.data1 = Integer.valueOf(numberNodes.get(0).text());
		lottery.data2 = Integer.valueOf(numberNodes.get(1).text());
		lottery.data3 = Integer.valueOf(numberNodes.get(2).text());
		lottery.data4 = Integer.valueOf(numberNodes.get(3).text());
		lottery.data5 = Integer.valueOf(numberNodes.get(4).text());
		
//		LogUtil.log("lottery========" + lottery);
		
		return lottery;
	}
	
}
