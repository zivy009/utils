package com.zivy.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 
 * @Description:Json工具类
 * @Copyright:
 * @Company:
 * @Author : jiangfl
 * @Date: 2013-12-27
 * @Version 1.0
 */
public class JsonUtil {
	private static Logger log = Logger.getLogger(JsonUtil.class);

	private static String getJsonString(String classPath) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		InputStream input = loader.getResourceAsStream(classPath);
		if (input == null) {
			System.out.println("资源不存在!" + classPath);
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(input, "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = reader.readLine(); // 读取第一行
			while (line != null) { // 如果 line 为空说明读完了
				buffer.append(line); // 将读到的内容添加到 buffer 中
				line = reader.readLine(); // 读取下一行
			}
			return buffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				reader = null;
			}
		}
		return null;
	}

	public static void main(String[] args) {

		System.out.println(System.getProperty("user.dir"));

		// System.out.println(toJsonString(DseSysModule.class));
		// String str = DseJsonUtil
		// .getJsonString("com/dse/sys/module/web/data.json");
		// System.out.println(str);
	}

	public static String toJson(Object obj, String dateFormat) {
		String json = new GsonBuilder().setDateFormat(dateFormat).create().toJson(obj);
		log.info(json);
		return json;
	}

	public static String toJson(Object obj) {
		return toJson(obj, "yyyy-MM-dd");
	}

	/***
	 * 转换成json字符串
	 * 
	 * @param page
	 *            jfinal的page对象
	 * @return
	 * @time:2014-4-13
	 * @author:jiangfl
	 */
//	public static String toEasyuiGrid(Page page) {
//		return toEasyuiGrid(page, "yyyy-MM-dd");
//	}

	/**
	 * 
	 * @param page
	 *            数据page对象
	 * @param dateFormat
	 *            里面的日期转化格式
	 * @return
	 * @author:jiangfl
	 * @date 2015-7-21
	 */
//	public static String toEasyuiGrid(Page page, String dateFormat) {
//		Map<String, Object> m = new HashMap<String, Object>();
//		// 总记录数
//		m.put("total", page.getTotalRecord());
//		// 查询到的数据
//		m.put("rows", page.getDataList());
//		// put("data", page.getDataList());
//		m.put("pageSize", page.getPageSize());
//		String json = new GsonBuilder().setDateFormat(dateFormat).create().toJson(m).toString();
//		log.info(json);
//		return json;
//	}

	public static JsonObject convertJsonObject(String jsonString) {
		JsonObject json = (JsonObject) new JsonParser().parse(jsonString);
		return json;
	}

}
