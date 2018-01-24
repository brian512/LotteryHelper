package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.Lottery;

public class Data1_2LGroup extends DataGroup {
	
	public Data1_2LGroup(int numberCnt, int odds) {
		super(numberCnt, odds);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data1 >= 3 && lottery.data2 >= 3
				&& lottery.data1 != lottery.data2;
	}
}
