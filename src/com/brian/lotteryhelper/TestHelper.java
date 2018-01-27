package com.brian.lotteryhelper;

import java.util.ArrayList;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.group.DataGroup;

public class TestHelper {

	
	public static void count(ArrayList<Lottery> lotteryList, DataGroup dataGroup, boolean logEveryRow) {
		int[] counts = new int[30];
		int count = 0;
		for(Lottery lottery : lotteryList) {
			if (logEveryRow) {
				LogUtil.logLottery(lottery.toString());
			}
			if (!dataGroup.checkLucky(lottery)) {
				count++;
			} else {
				if (logEveryRow) {
					LogUtil.log("***************************count=" + count);
				}
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
			DataGroup dataGroup, int[] price, boolean logPerLottery, boolean logLostSum) {
		final int MONEY_INIT = ownMoney;
		
		
		int inputMoney = 0; // 当期投入的金额
		int inputMoneyPerNum = 0; // 每一组的金额
		int inputMoneySum = 0; // 当前规律投入的总金额
		
		int unluckyCnt = 0;
		int stopLossCnt = 0;
		
		String outPutMsg = null;
		
		if (logPerLottery) {
			LogUtil.log("期号\t" + "       日期\t时间\t" + "     号码\t\t" 
					+ "当期下注\t" + "累计\t" + "盈利\t" + "结余\t");
		}
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
			}
			
		}
		
		LogUtil.log("\nstopLossCnt=" + stopLossCnt);
		LogUtil.logMoneySum("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		LogUtil.log("\n");
		return ownMoney;
	}
	
	
}
