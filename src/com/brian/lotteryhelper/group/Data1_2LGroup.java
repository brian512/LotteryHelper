package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.data.Lottery;

public class Data1_2LGroup extends DataGroup {
	
	public Data1_2LGroup() {
		super(42, 98);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data1 >= 3 && lottery.data2 >= 3
				&& lottery.data1 != lottery.data2;
	}
}
