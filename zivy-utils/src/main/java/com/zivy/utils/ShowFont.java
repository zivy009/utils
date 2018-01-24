package com.zivy.utils;

import java.awt.GraphicsEnvironment;

public class ShowFont {
	/**
	 * 查看 java 支持的字体
	 * @author zhc
	 *  2016年6月29日
	 */
public static void Font(){
	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	String[] fontFamilies = ge.getAvailableFontFamilyNames();
	for (String s : fontFamilies) {
		System.out.println(s);
	}
}
}
