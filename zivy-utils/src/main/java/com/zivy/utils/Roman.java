package com.zivy.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Roman {
	private static final Map<Integer,String[]> map = new HashMap<Integer,String[]>();
	static {
		map.put(1, new String[] { "I", "IV", "V", "IX" });
		map.put(2, new String[] { "X", "XL", "L", "XC" });
		map.put(3, new String[] { "C", "CD", "D", "CM" });
		map.put(4, new String[] { "M" });
	}

	public static String parse(int num) {
		if (num < 1 || num > 3999) {
			return "";
		} else {
			int mod = 0, n = num;
			StringBuilder roman = new StringBuilder();
			String tmp = null;
			String r = null;
			for (Integer key : map.keySet()) {
				mod = n % 10;
				n = (n - mod) / 10;
				if (mod == 0)
					continue;
				tmp = "";
				if (key.intValue() == 4) {
					r = (map.get(key))[0];
					while (mod-- > 0) {
						tmp += r;
					}
				} else {
					if (mod <= 5) {
						if (mod < 4) {// 1,2,3
							r = (map.get(key))[0];
							while (mod-- > 0) {
								tmp += r;
							}
						} else if (mod == 4) {// 4
							tmp = (map.get(key))[1];
						} else {// 5
							tmp = (map.get(key))[2];
						}
					} else {
						if (mod < 9) {// 6,7,8
							tmp = (map.get(key))[2];
							r = (map.get(key))[0];
							while (mod-- > 5) {
								tmp += r;
							}
						} else {// 9
							tmp = (map.get(key))[3];
						}
					}
				}
				roman.insert(0, tmp);
			}
			return roman.toString();
		}
	}

	public static void main(String[] args) {
		while (true) {
			System.out.println();
			System.out.print("请输入阿拉伯数字(按Q键退出)：");
			Scanner cin = new Scanner(System.in);
			String romanStr = cin.nextLine();
			if (romanStr.equals("q") || romanStr.equals("Q")) // 退出循环
				break;
			System.out.println(Roman.parse(Integer.valueOf(romanStr)));
		}
		System.out.println("欢迎您再次使用^-^");
	}
}
