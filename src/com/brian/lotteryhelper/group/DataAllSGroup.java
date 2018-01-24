package com.brian.lotteryhelper.group;

import com.brian.lotteryhelper.Lottery;

public class DataAllSGroup extends DataGroup {
	
	public DataAllSGroup(int numberCnt, int odds) {
		super(numberCnt, odds);
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return lottery.data1 <= 6 && lottery.data2 <= 6
				&& lottery.data3 <= 6 && lottery.data4 <= 6
				;
	}
}
