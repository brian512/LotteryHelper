package com.brian.lotteryhelper;

import java.util.ArrayList;
import com.brian.lotteryhelper.group.DataGroup;

public class TestHelper {

	
	public static void count(ArrayList<Lottery> lotteryList, DataGroup dataGroup) {
		int[] counts = new int[30];
		int count = 0;
		for(Lottery lottery : lotteryList) {
			LogUtil.logLottery(lottery.toString());
			if (!dataGroup.checkLucky(lottery)) {
				count++;
			} else {
				LogUtil.log("***************************count=" + count);
				counts[count]++;
				count = 0;
			}
		}
		
		int length = 0;
		for (int i = counts.length-1; i >= 0; i--) {
			if (counts[i] != 0 && length == 0) {
				length = i;
			}
			if (length > 0) {
				LogUtil.log("count " + (length-i) + " = " + counts[length-i]);
			}
				
		}
	}
	
	
	/**
	 * 
	 * @param ownMoney
	 * @param lotteryList
	 * @param dataGroup 需要买的规律数据，包含组数，赔率，单期下单金额列表
	 * @param startIndex 间隔期数
	 */
	public static int test(int ownMoney, ArrayList<Lottery> lotteryList, 
			DataGroup dataGroup, int[] price) {
		final int MONEY_INIT = ownMoney;
		
		
		int inputMoney = 0; // 当期投入的金额
		int inputMoneyPerNum = 0; // 每一组的金额
		int inputMoneySum = 0; // 当前规律投入的总金额
		
		int unluckyCnt = 0;
		int stopLossCnt = 0;
		
		String outPutMsg = null;
		
		
		for (Lottery lottery : lotteryList) {
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
			
			if (dataGroup.checkLucky(lottery)) {
				unluckyCnt = 0;
				inputMoney = 0;
				inputMoneySum = 0;
				
				outPutMsg += "\t+" + (inputMoneyPerNum * (dataGroup.getOdds()-dataGroup.getGroupCnt()));
				ownMoney += inputMoneyPerNum * dataGroup.getOdds();
				
				if (ownMoney - MONEY_INIT > 10_000) {
					break;
				}
			} else {
				unluckyCnt++;
				outPutMsg += "\t-" + (inputMoneyPerNum * dataGroup.getGroupCnt());
			}
			
			outPutMsg += "\t" + ownMoney;
			
//			LogUtil.logLottery(outPutMsg);
			
			if (unluckyCnt >= price.length && inputMoney > 0) {
				stopLossCnt++;
				LogUtil.logUnlucky("lossMoneySum=" + inputMoneySum 
						+ "  **************************************************************");
				inputMoney = 0;
				inputMoneySum = 0;
			}
			
		}
		
		LogUtil.log("\nstopLossCnt=" + stopLossCnt);
		LogUtil.logMoneySum("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		LogUtil.log("\n");
		return ownMoney;
	}
	
	
}
