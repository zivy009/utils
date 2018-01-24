package com.zivy.utils.image;

public class ImageBfmx {
	/**
	 * 获得缩略图片
	 * @author zhc
	 * @param pic
	 * @return
	 *  2016年4月26日
	 */
	public  static  String obtainThumImg(String pic) {
		pic = pic == null ? "" : pic;
		pic=pic.replaceFirst("ys_", "");
		return pic+"?imageView2/1/w/200/h/200";
	}
	/**
	 * 获得缩略图片
	 * @author zhc
	 * @param pic
	 * @return
	 *  2016年4月26日
	 */
	public static String obtainThumImg(Object pic) {
		String temp = pic == null ? "" : pic.toString();
		return obtainThumImg(temp);
	}
}
