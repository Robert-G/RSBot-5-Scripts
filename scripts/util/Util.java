package util;

import java.text.NumberFormat;

public class Util {
	
	private final static NumberFormat f = NumberFormat.getInstance();
	
	public static String formatNumber(int number) {
		return f.format(number);
	}

	public static boolean arrayContainsOneOf(int[] arg0, int... arg1) {
		for (int a : arg0) {
			for (int b : arg1) {
				if (a == b) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static String formatTime(final long time) {
		final int sec = (int) (time / 1000), h = sec / 3600, m = sec / 60 % 60, s = sec % 60;
		return (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s < 10 ? "0" + s : s);
    }

}
