package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.data.Lottery;

public class Data1_2SGroup extends DataGroup {
	
	public Data1_2SGroup() {
		super(42, 98);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data1 <= 6 && lottery.data2 <= 6
				&& lottery.data1 != lottery.data2;
	}
}
