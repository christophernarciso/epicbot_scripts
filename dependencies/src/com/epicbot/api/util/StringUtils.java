package com.epicbot.api.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	private static String[] suffix = new String[] { "", "k", "m", "b", "t" };

	public static List<String> expandItemName(String name) {
		ArrayList<String> names = new ArrayList<String>();
		Pattern pattern = Pattern.compile("^(.*?)([0-9]+)~([0-9]+)(.*?)$");
		Matcher matcher = pattern.matcher(name);
		if (matcher.find()) {
			String prepend = matcher.group(1), append = matcher.group(4);
			int start = Integer.parseInt(matcher.group(2)), finish = Integer.parseInt(matcher.group(3)),
					dir = start > finish ? -1 : 1;
			for (int i = start; i * dir <= finish * dir; i += dir) {
				names.add(prepend + i + append);
			}
		} else {
			names.add(name);
		}
		return names;
	}

	/**
	 * This will return the string formated to thousands, millions and billions.
	 *
	 * @param number
	 *            The number to format
	 * @param format
	 *            The DecimalFormat string used to format, example "0.##"
	 * @return String
	 */
	public static String formatValue(double number, String format) {
		int s = 0;
		for (; number >= 1000; s++)
			number /= 1000.0;
		return new DecimalFormat(format).format(number) + suffix[s];
	}

	/**
	 * This will return the string formated to thousands, millions and billions.
	 *
	 * @param number
	 *            The number to format
	 * @return String
	 */
	public static String formatValue(double number) {
		return formatValue(number, "0.##");
	}

	/**
	 * Formats seconds into a H:MM:SS timestamp
	 * 
	 * @param seconds
	 * @return
	 */
	public static String formatTime(long seconds) {
		return formatTime(seconds, true);
	}

	/**
	 * Formats seconds into a HH:MM:SS timestamp
	 * 
	 * @param seconds
	 * @return
	 */
	public static String formatTime(long seconds, boolean padNoHours) {
		if (seconds <= 0)
			return padNoHours ? "0:00:00" : "00:00";
		long hours = seconds / 3600, minutes = (seconds % 3600) / 60, secondss = (seconds % 60);
		if (hours <= 0 && !padNoHours)
			return String.format("%02d:%02d", minutes, secondss);
		return String.format("%d:%02d:%02d", hours, minutes, secondss);
	}

}
