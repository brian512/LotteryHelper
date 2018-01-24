package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.Lottery;

public class Data3_4LGroup extends DataGroup {
	
	public Data3_4LGroup(int numberCnt, int odds) {
		super(numberCnt, odds);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data3 >= 3 && lottery.data4 >= 3
				&& lottery.data3 != lottery.data4;
	}

}
