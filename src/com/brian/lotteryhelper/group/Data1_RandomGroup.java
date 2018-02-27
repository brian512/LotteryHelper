package com.brian.lotteryhelper.group;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.brian.lotteryhelper.LogUtil;
import com.brian.lotteryhelper.data.Lottery;

public class Data1_RandomGroup extends DataGroup {
	
	private HashSet<Integer> RANDOMSET = new HashSet<>();
	
	private Random random = new Random();
	
	public Data1_RandomGroup() {
		super(50, 98);
	}

	public static boolean shouldStop(List<Lottery> lotteries) {
		int count = 0;
		for (int i = 0; i < lotteries.size(); i++) {
			Lottery lottery = lotteries.get(i);
			if (lottery.data1 < 3) {
				count++;
			}
			if (lottery.data2 < 3) {
				count++;
			}
			if (lottery.data3 < 3) {
				count++;
			}
			if (lottery.data4 < 3) {
				count++;
			}
			if (i >= 4) {
				break;
			}
		}
//		return false;
		return count >= 16;
	}
	
	@Override
	public boolean checkLucky(Lottery lottery) {
		return Arrays.asList(1, 3, 5, 7, 9).contains(lottery.data1);
	}
}
