package com.brian.lotteryhelper;

import java.util.ArrayList;
import java.util.Iterator;

import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.Data1_2SGroup;
import com.brian.lotteryhelper.group.DataGroup;

public class TestHelper {

	
	
	
	
	
	public static void test1_2L(ArrayList<Lottery> lotteryList) {
		test(50_000, lotteryList, new Data1_2LGroup(42, 98), 1);
	}
	
	public static void test1_2S(ArrayList<Lottery> lotteryList) {
		test(50_000, lotteryList, new Data1_2SGroup(42, 98), 1);
	}
	
	public static void test1_2LS(ArrayList<Lottery> lotteryList) {
		testL_S(50_000, lotteryList, new Data1_2LGroup(42, 98), new Data1_2SGroup(42, 98), 1);
	}
	
	public static void count1_2L(ArrayList<Lottery> lotteryList) {
		count(lotteryList, new Data1_2LGroup(42, 98));
	}
	
	
	
	
	
	
	private static void count(ArrayList<Lottery> lotteryList, DataGroup dataGroup) {
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
		
		
		for (int i = 0; i < counts.length; i++) {
			LogUtil.log("count " + i + " = " + counts[i]);
		}
	}
	
	
	
	/**
	 * 
	 * @param ownMoney
	 * @param lotteryList
	 * @param dataGroup 需要买的规律数据，包含组数，赔率，单期下单金额列表
	 * @param startIndex 间隔期数
	 */
	public static void testL_S(int ownMoney, ArrayList<Lottery> lotteryList, 
			DataGroup dataGroupL, DataGroup dataGroupS, int startIndex) {
		
		int inputMoneyL = 0; // 当期投入的金额
		int inputMoneyS = 0; // 当期投入的金额
		int inputMoneyPerNumL = 0; // 每一组的金额
		int inputMoneyPerNumS = 0; // 每一组的金额
		int inputMoneySumL = 0; // 当前规律投入的总金额
		int inputMoneySumS = 0; // 当前规律投入的总金额
		
		int unluckyCntL = 0;
		int unluckyCntS = 0;
		
		int stopLossCnt = 0;
		
		String outPutMsg = null;
		
		for (Lottery lottery : lotteryList) {
			outPutMsg = lottery.toString();
			
			if (unluckyCntL >= startIndex || unluckyCntS >= startIndex) {
				if (unluckyCntL >= startIndex) {
					int followCnt = unluckyCntL - startIndex;
					inputMoneyPerNumL = dataGroupL.getPrices()[followCnt];
					inputMoneyL = inputMoneyPerNumL * dataGroupL.getGroupCnt();
					inputMoneySumL += inputMoneyL;
					
					if (inputMoneyL > ownMoney) {
						LogUtil.logError("余额不足");
						break;
					}
				} else {
					inputMoneyL = 0;
					inputMoneyPerNumL = 0;
					inputMoneySumL = 0;
				}
				if (unluckyCntS >= startIndex) {
					int followCnt = unluckyCntS - startIndex;
					inputMoneyPerNumS = dataGroupS.getPrices()[followCnt];
					inputMoneyS = inputMoneyPerNumS * dataGroupS.getGroupCnt();
					inputMoneySumS += inputMoneyS;
					
					if (inputMoneyS > ownMoney) {
						LogUtil.logError("余额不足");
						break;
					}
				} else {
					inputMoneyS = 0;
					inputMoneyPerNumS = 0;
					inputMoneySumS = 0;
				}
				
			} else {
				inputMoneyS = 0;
				inputMoneyPerNumS = 0;
				inputMoneySumS = 0;
				
				inputMoneyL = 0;
				inputMoneyPerNumL = 0;
				inputMoneySumL = 0;
			}
			outPutMsg += "\t" + (inputMoneyL+inputMoneyS);
			outPutMsg += "\t" + (inputMoneySumL+inputMoneySumS);
			
			if (dataGroupL.checkLucky(lottery)) {
				unluckyCntL = 0;
				
				ownMoney += inputMoneyPerNumL * dataGroupL.getOdds();
			} else {
				unluckyCntL++;
				
				ownMoney -= inputMoneyL;
			}
			if (dataGroupS.checkLucky(lottery)) {
				unluckyCntS = 0;
				
				ownMoney += inputMoneyPerNumS * dataGroupS.getOdds();
			} else {
				unluckyCntS++;
				
				ownMoney -= inputMoneyS;
			}
			outPutMsg += "\t" + ownMoney;
			
			LogUtil.logLottery(outPutMsg);
			
			if (unluckyCntL > dataGroupL.getPrices().length) {
				stopLossCnt++;
				LogUtil.logUnlucky("lossMoneySumL=" + inputMoneySumL 
						+ "  **************************************************************");
				inputMoneyL = 0;
				inputMoneySumL = 0;
				unluckyCntL = 0;
			}
			if (unluckyCntS > dataGroupS.getPrices().length) {
				stopLossCnt++;
				LogUtil.logUnlucky("lossMoneySumS=" + inputMoneySumS 
						+ "  **************************************************************");
				inputMoneyS = 0;
				inputMoneySumS = 0;
				unluckyCntS = 0;
			}
			
		}
		
		LogUtil.log("\nstopLossCnt=" + stopLossCnt);
		LogUtil.logMoneySum("\nfinish  ####################   ownMoney=" + ownMoney);
		LogUtil.log("\n\n");
	}
	
	/**
	 * 
	 * @param ownMoney
	 * @param lotteryList
	 * @param dataGroup 需要买的规律数据，包含组数，赔率，单期下单金额列表
	 * @param startIndex 间隔期数
	 */
	private static void test(int ownMoney, ArrayList<Lottery> lotteryList, 
			DataGroup dataGroup, int startIndex) {
		
		int inputMoney = 0; // 当期投入的金额
		int inputMoneyPerNum = 0; // 每一组的金额
		int inputMoneySum = 0; // 当前规律投入的总金额
		
		int unluckyCnt = 0;
		int stopLossCnt = 0;
		
		String outPutMsg = null;
		
		for (Lottery lottery : lotteryList) {
			outPutMsg = lottery.toString();
			
			if (unluckyCnt >= startIndex) {
				int followCnt = unluckyCnt - startIndex;
				inputMoneyPerNum = dataGroup.getPrices()[followCnt];
				inputMoney = inputMoneyPerNum * dataGroup.getGroupCnt();
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
			
			if (dataGroup.checkLucky(lottery)) {
				unluckyCnt = 0;
				
				ownMoney += inputMoneyPerNum * dataGroup.getOdds();
			} else {
				unluckyCnt++;
				
				ownMoney -= inputMoney;
			}
			outPutMsg += "\t" + ownMoney;
			
			LogUtil.logLottery(outPutMsg);
			
			if (unluckyCnt > dataGroup.getPrices().length) {
				stopLossCnt++;
				LogUtil.logUnlucky("lossMoneySum=" + inputMoneySum 
						+ "  **************************************************************");
				inputMoney = 0;
				inputMoneySum = 0;
				unluckyCnt = 0;
			}
			
		}
		
		LogUtil.log("\nstopLossCnt=" + stopLossCnt);
		LogUtil.logMoneySum("finish  ####################   ownMoney=" + ownMoney);
		LogUtil.log("\n\n");
	}
	
	
}
