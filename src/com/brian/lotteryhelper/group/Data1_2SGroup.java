package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.Lottery;

public class Data1_2SGroup extends DataGroup {
	
	public Data1_2SGroup(int numberCnt, int odds) {
		super(numberCnt, odds);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data1 <= 6 && lottery.data2 <= 6
				&& lottery.data1 != lottery.data2;
	}
}
