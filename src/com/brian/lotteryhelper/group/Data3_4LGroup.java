package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.data.Lottery;

public class Data3_4LGroup extends DataGroup {
	
	public Data3_4LGroup() {
		super(42, 98);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data3 >= 3 && lottery.data4 >= 3
				&& lottery.data3 != lottery.data4;
	}

}
