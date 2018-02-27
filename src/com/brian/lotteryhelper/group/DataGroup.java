package com.brian.lotteryhelper.group;

import java.util.HashSet;

import com.brian.lotteryhelper.data.Lottery;

public abstract class DataGroup {
	
	protected HashSet<Integer> DATA1 = new HashSet<>();
	protected HashSet<Integer> DATA2 = new HashSet<>();
	protected HashSet<Integer> DATA3 = new HashSet<>();
	protected HashSet<Integer> DATA4 = new HashSet<>();
	
	protected int mOdds;
	protected int mNumberCnt;
	
	public DataGroup(int numberCnt, int odds) {
		mOdds = odds;
		mNumberCnt = numberCnt;
	}
	
	public int getOdds() {
		return mOdds;
	}
	
	public int getGroupCnt() {
		return mNumberCnt;
	}
	
	public abstract boolean checkLucky(Lottery lottery);

	
	public int[] getPrices() {
		return getPrices(mNumberCnt);
	}
	
	
	private static int[] getPrices(int numberCnt) {
		if (numberCnt == 42) {
			return new int[] {
					10, 20, 40, 60, 100
			};
		} else {
			return new int[] {
					0
			};
		}
	}
	
}
