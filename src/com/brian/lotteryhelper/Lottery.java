package com.brian.lotteryhelper;

public class Lottery {

	public int data1;
	
	public int data2;
	
	public int data3;
	
	public int data4;
	
	public int data5;
	
	public String dateStr;
	
	public long lotteryId;
	
	
	@Override
	public String toString() {
		return dateStr
				+ "\t " + lotteryId 
				+ "\t " + data1 + " " + data2 + " " + data3 + " " + data4;
	}
}
