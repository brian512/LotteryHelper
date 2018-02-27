package com.brian.lotteryhelper.group;

import java.util.Arrays;

import com.brian.lotteryhelper.data.Lottery;

public class Data1764Group extends DataGroup {
	
	public Data1764Group() {
		super(1764, 9620);
		DATA1.addAll(Arrays.asList(1,4,7,3,6,9));
		DATA2.addAll(Arrays.asList(1,4,7,0,2,5,8));
		DATA3.addAll(Arrays.asList(1,4,7,0,2,5,8));
		DATA4.addAll(Arrays.asList(1,4,7,3,6,9));
	}

	@Override
	public boolean checkLucky(Lottery lottery) {
		return DATA1.contains(lottery.data1)
				&& DATA2.contains(lottery.data2)
				&& DATA3.contains(lottery.data3)
				&& DATA4.contains(lottery.data4);
	}

}
