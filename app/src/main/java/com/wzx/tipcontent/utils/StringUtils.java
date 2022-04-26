package com.wzx.tipcontent.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * 字符串操作工具包
 */
@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class StringUtils {
	private static boolean isHaveAc = false;
	private final static Pattern EMAILER = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	private final static int MAX_VALUE = 5; // 最大值记住5个
	public static ArrayList<String> array = new ArrayList<String>();// 记录近期用户登录数

	public static boolean hasChinese(String str) {
		for (int i = 0; i < str.length(); i++) {
			if (str.substring(i, i + 1).matches("[\\u4e00-\\u9fa5]+")) {
				return true;
			}
		}
		return false;

	}

	/**
	 * 一维数组转换为二维数组
	 *
	 * @param src
	 *            ...
	 * @param row
	 *            ...
	 * @param column
	 *            ...
	 * @return ...
	 */
	public static String[][] arraysConvert(String[] src, int row, int column) {
		String[][] tmp = new String[row][column];
		for (int i = 0; i < row; i++) {
			tmp[i] = new String[column];
			System.arraycopy(src, i * column, tmp[i], 0, column);
		}
		return tmp;
	}

	/**
	 * 获取当前系统的语言环境
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	public static boolean isNumber(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean isEmptyNull(String s) {
		if (TextUtils.isEmpty(s)) {
			return true;
		}
		if (s.equals("null")) {
			s = "";
			return true;
		}
		return false;
	}

	/**
	 * 保存用户名登录账号
	 * 
	 * @param username
	 * @param cookiesUsernameData
	 */
	public static void saveUsernameData(SharedPreferences sp, String username, String cookiesUsernameData) {
		StringBuilder sb = null;
		StringBuilder sbd = null;
		if (isHaveAc) {
			String saveStr = sp.getString("accountnumber", "");
			if (JudgeSame(sp, saveStr, username, 1)) { // 如果和前面用户名相同就不保存
				return;
			}
			sb = new StringBuilder(username);
			if (saveStr != null && !saveStr.equals("")) {
				sb.append("," + saveStr + ",");
			} else {
				sb.append(",");
			}
		} else {
			if (JudgeSame(sp, cookiesUsernameData, username, 2)) { // 如果和前面用户名相同就不保存
				return;
			}
			sb = new StringBuilder(username);
			if (cookiesUsernameData.equals("")) {
				sb.append(",");
			} else {
				sb.append("," + cookiesUsernameData + ",");
			}
		}
		sp.edit().putString("accountnumber", sb.toString()).commit();

		// 如果用户名数大于设置的最大个数就删掉前面的
		List<String> dd = new ArrayList<String>();
		dd.clear();
		String uname = sp.getString("accountnumber", "");
		if (uname != null) {
			String[] unames = uname.split(",");
			for (int i = 0; i < unames.length; i++) {
				dd.add(unames[i]); // 插入值从第一个开始插入
			}
		}
		if (dd.size() > MAX_VALUE) {
			// 删除
			dd.remove(dd.size() - 1);
		}
		sbd = new StringBuilder();
		for (int k = 0; k < dd.size(); k++) {
			sbd.append(dd.get(k) + ",");
		}
		sp.edit().putString("accountnumber", sbd.toString().substring(0, sbd.toString().length() - 1)).commit();// 去掉最后的“，”
	}


	/**
	 * 判断是否是相同的字符
	 * 
	 * @param str
	 *            字符串
	 * @param sameStr
	 *            要判断的字符
	 * @param value
	 *            判断程序是从哪里进来的
	 * @return
	 */
	private static boolean JudgeSame(SharedPreferences sp, String str, String sameStr, int value) {
		if (value == 2) {
			sp.edit().putString("accountnumber", str).commit(); // 从新赋值
		}
		String[] cud = str.split(",");
		for (int i = 0; i < cud.length; i++) {
			if (cud[i].equals(sameStr)) {
				reOrder(sp, str, sameStr); // 重新排序
				return true;
			}
		}
		return false;
	}

	/**
	 * 对于有相同的账号进行从新排序 最近的放在最上面
	 */
	private static void reOrder(SharedPreferences sp, String str, String sameStr) {
		StringBuilder sb = null;
		List<String> lists = new ArrayList<String>();
		lists.clear();
		String[] arrays = str.split(",");
		for (int i = 0; i < arrays.length; i++) {
			if (!arrays[i].equals(sameStr)) {
				lists.add(arrays[i]);
			}
		}
		sb = new StringBuilder(sameStr);
		for (int j = 0; j < lists.size(); j++) {
			sb.append("," + lists.get(j));
		}
		sp.edit().putString("accountnumber", sb.toString()).commit();
	}

	/**
	 * 验证身份证号码
	 *
	 * @param idCard
	 *            居民身份证号码15位或18位，最后一位可能是数字或字母
	 * @return 验证成功返回true，验证失败返回false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * 判断字符串最后一位的字符并去除
	 * 
	 * @param content
	 * @return
	 */
	public static String replaceStr(String content, String marker) {
		if (content.endsWith(marker)) {
			content = content.substring(0, content.length() - 1);
		}
		return content;
	}
}