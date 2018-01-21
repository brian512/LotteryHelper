package com.brian.lotteryhelper;

public class TestHelper {

	
	public static boolean check1_2Small(Lottery lottery) {
		return lottery.data1 <= 6 && lottery.data2 <= 6 
				&& lottery.data1 != lottery.data2;
	}
	
	public static boolean check1_2Big(Lottery lottery) {
		return lottery.data1 >= 3 && lottery.data2 >= 3 
				&& lottery.data1 != lottery.data2;
	}
	
	public static boolean check1_3Big(Lottery lottery) {
		return lottery.data1 >= 3 && lottery.data3 >= 3 
				&& lottery.data1 != lottery.data3;
	}
	
	public static boolean check1_4Big(Lottery lottery) {
		return lottery.data1 >= 3 && lottery.data4 >= 3 
				&& lottery.data1 != lottery.data4;
	}
	
	public static boolean check2_3Big(Lottery lottery) {
		return lottery.data2 >= 3 && lottery.data3 >= 3 
				&& lottery.data2 != lottery.data3;
	}
	
	public static boolean check2_4Big(Lottery lottery) {
		return lottery.data2 >= 3 && lottery.data4 >= 3 
				&& lottery.data2 != lottery.data4;
	}
	
	public static boolean check3_4Big(Lottery lottery) {
		return lottery.data3 >= 3 && lottery.data4 >= 3 
				&& lottery.data3 != lottery.data4;
	}
	
}
