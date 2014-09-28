package com.example.mycommon.utils;

import java.util.regex.Pattern;

public class ValidateUtil {

	public static String REGEX_MOBILE = "^1(3|5|8)[0-9]{9}$";
	public static String REGEX_NAME = "^\\w{1,10}$";

	private static Pattern PATTERN_MOBILE = Pattern.compile(REGEX_MOBILE);

	/**
	 * 验证是否是手机
	 */
	public static boolean isMobile(String mobile) {
		return PATTERN_MOBILE.matcher(mobile).matches();
	}

	public static boolean len(String m, int min, int max) {
		return m.length() >= min && m.length() <= max;
	}

	public static boolean minValue(String m, int minValue) {
		return Integer.parseInt(m) >= minValue;
	}
	
	public static boolean maxValue(String m, int maxValue) {
		return Integer.parseInt(m) <= maxValue;
	}

	/**
	 * 验证是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || str.trim().toLowerCase().equals("null");
	}

	/**
	 * 验证是否为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
