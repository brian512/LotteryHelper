package com.brian.lotteryhelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.brian.lotteryhelper.data.Lottery;
import com.brian.lotteryhelper.group.Data1_2LGroup;
import com.brian.lotteryhelper.group.DataGroup;

public class TestHelper {

	public static int[] count(ArrayList<Lottery> lotteryList, DataGroup dataGroup, boolean logEveryRow) {
		return count(lotteryList, dataGroup, logEveryRow, 0);
	}
	
	public static int[] count(ArrayList<Lottery> lotteryList, DataGroup dataGroup, boolean logEveryRow, int stopLeft) {
		ArrayList<Integer> gaps = new ArrayList<>();
		int[] counts = new int[100];
		int count = 0;
		for(int i=0; i < lotteryList.size(); i++) {
			Lottery lottery = lotteryList.get(i);
			if (logEveryRow) {
				LogUtil.logLottery(lottery.toString());
			}
			if (!dataGroup.checkLucky(lottery)) {
				count++;
			} else {
				if (logEveryRow) {
					LogUtil.logln("***************************count=" + count);
				}
				gaps.add(count);
				counts[count]++;
				count = 0;
				
				if (i > lotteryList.size()-stopLeft) {
					break;
				}
			}
		}
		
		ArrayUtil.printArray(counts);
		LogUtil.logln(gaps.toString());
		
		return ArrayUtil.trim(counts);
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
			LogUtil.logln("期号\t" + "       日期\t时间\t" + "     号码\t\t" 
					+ "当期下注\t" + "累计\t" + "盈利\t" + "结余\t");
		}
		for (Lottery lottery : lotteryList) {
			outPutMsg = lottery.toString();
			
			if (unluckyCnt >= price.length) {
				LogUtil.logln("restart");
				unluckyCnt = 0;
			}
			
			if (unluckyCnt < price.length) {
				if (unluckyCnt >= 4) {
					int index = lotteryList.indexOf(lottery);
					if (index < unluckyCnt) {
						index = unluckyCnt;
					}
					List<Lottery> unluckList = (List<Lottery>) lotteryList.subList(index-unluckyCnt, index);
//					ArrayUtil.printArray(unluckList);
					if (!Data1_2LGroup.shouldStop(unluckList)) {
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
						inputMoney = 0;
						inputMoneyPerNum = 0;
						System.out.println("==========================");
					}
				} else {
					inputMoneyPerNum = price[unluckyCnt];
					inputMoney = inputMoneyPerNum * dataGroup.getGroupCnt();
					inputMoneySum += inputMoney;
					
					if (inputMoney > ownMoney) {
						LogUtil.logError("余额不足");
						break;
					} else {
						ownMoney -= inputMoney;
					}
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
				
				if (ownMoney - MONEY_INIT > 2_000) {
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
	
	
	public static int testData(int ownMoney, ArrayList<Lottery> lotteryList, 
			DataGroup dataGroup, int[] price, boolean logPerLottery, boolean logLostSum) {
		final int MONEY_INIT = ownMoney;
		
		
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
		int max = 0;
		
		
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
			
			if (dataGroup.checkLucky(lottery)) {
				outPutMsg += "\t+" + (inputMoneyPerNum * (dataGroup.getOdds()-dataGroup.getGroupCnt()));
				ownMoney += inputMoneyPerNum * dataGroup.getOdds();
				
				unluckyCnt = 0;
				inputMoney = 0;
				inputMoneySum = 0;
				
				if (ownMoney - MONEY_INIT > max) {
					max = ownMoney - MONEY_INIT;
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
				
//				if (stopLossCnt >= 2) {
//					break;
//				}
			}
			
			if (dataGroup.checkLucky(lottery)) {
				if (ownMoney - MONEY_INIT >= 980) {
					LogUtil.logln("止盈期数=" + i);
					break;
				}
				
				
				if (i > lotteryList.size()-(price.length+5)) {
					break;
				}
			} else {
				if (ownMoney - MONEY_INIT <= -980) {
					LogUtil.logln("止损期数=" + i);
					break;
				}
			}
		}
		
		
//		LogUtil.logln("\nstopLossCnt=" + stopLossCnt);
//		LogUtil.logln("max=" + max);
		if ((ownMoney-MONEY_INIT) < 0) {
			LogUtil.logError("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		} else {
			LogUtil.logMoneySum("finish  ####################   本次盈利=" + (ownMoney-MONEY_INIT));
		}
		
		
		LogUtil.logln("\n");
		return ownMoney;
	}
	
}
