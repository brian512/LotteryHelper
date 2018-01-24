package com.brian.lotteryhelper;

public class LogUtil {

	public static boolean LOG_LOTTERY = true;
	public static boolean LOG_UNLUCKY = true;
	public static boolean LOG_MONEY_SUM = true;
	
	public static void log(String msg) {
		System.out.println(msg);
	}
	
	public static void logLottery(String msg) {
		if (LOG_LOTTERY) {
			System.out.println(msg);
		}
	}
	public static void logMoneySum(String msg) {
		if (LOG_MONEY_SUM) {
			System.out.println(msg);
		}
	}
	public static void logUnlucky(String msg) {
		if (LOG_UNLUCKY) {
			System.out.println(msg);
		}
	}
	
	public static void logError(String msg) {
		System.out.println(msg);
		System.out.println();
	}
	
	public static void logError(Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
	}
	
}
