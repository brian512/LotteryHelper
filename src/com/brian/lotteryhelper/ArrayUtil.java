package com.brian.lotteryhelper;

import java.util.Arrays;
import java.util.List;

import com.brian.lotteryhelper.data.Lottery;

public class ArrayUtil {

	
	public static void printArray(int[] array) {
		int count = array.length;
		for (int i = count-1; i >= 0; i--) {
			if (array[i] != 0) {
				count = i+1;
				break;
			}
		}
		
		String outMsg = "[";
		for (int i = 0; i < count; i++) {
			if (i == 0) {
				outMsg += String.format("%d,", array[i]);
			} else if (i == count-1) {
				outMsg += String.format("%4d", array[i]);
			} else {
				outMsg += String.format("%4d,", array[i]);
			}
		}
		outMsg += "]";
		System.out.println(outMsg);
//		System.out.println(Arrays.toString(array));
	}
	
	public static void printArray(List<Lottery> lotteries) {
		for (int i = 0; i < lotteries.size(); i++) {
			System.out.println(lotteries.get(i));
		}
		System.out.println("==");
	}
	
	public static int[] trim(int[] array) {
		int count = array.length;
		for (int i = count-1; i >= 0; i--) {
			if (array[i] != 0) {
				count = i+1;
				break;
			}
		}
		
		return Arrays.copyOf(array, count);
	}
}
