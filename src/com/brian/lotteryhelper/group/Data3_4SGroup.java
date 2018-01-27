package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.data.Lottery;

public class Data3_4SGroup extends DataGroup {
	
	public Data3_4SGroup(int numberCnt, int odds) {
		super(numberCnt, odds);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data3 <= 6 && lottery.data4 <= 6
				&& lottery.data3 != lottery.data4;
	}

}
