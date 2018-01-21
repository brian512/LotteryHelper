package com.brian.lotteryhelper;

public class LogUtil {

	public static void log(String msg) {
		System.out.println(msg);
	}
	
	public static void logError(String msg) {
		System.out.println(msg);
		System.out.println();
		System.out.println();
	}
	
	public static void logError(Exception e) {
		System.err.println(e.getClass().getName() + ": " + e.getMessage());
	}
	
}
