package com.zivy.utils.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/*******************************************************************************
 * 输出数据到客户端， 格式参考：http://blog.sina.com.cn/s/blog_a03d702f010143tw.html
 * 
 * @Description:
 * @Copyright:
 * @Company:
 * @Author : jiangfl
 * @Date: Dec 21, 2012
 * @Version 1.0
 */
public class ResponseUtil {
	/**
	 * 是否调试模式
	 */
	private static Object debug = null;

	/***************************************************************************
	 * 输出HTML数据
	 * 
	 * @param res
	 * @param data
	 */
	public static void printHtml(HttpServletResponse res, Object data) {
		res.setContentType("text/html; charset=utf-8");
		print(res, data);
	}

	private static void print(HttpServletResponse res, Object data) {
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = null;
		try {
			System.out.println("输出数据：" + data);

			// ServletOutputStream sos = res.getOutputStream();
			// sos.print(data.toString());
			// sos.flush();
			// sos.close();

			pw = res.getWriter();
			pw.print(data);
		} catch (IOException e) {
			System.out.println("输出数据" + data + "事出现异常!!!");
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
				pw = null;
			}
		}
	}

	/***************************************************************************
	 * 输出JSON数据
	 * 
	 * @param res
	 * @param data
	 * @author jiangfl
	 */
	public static void printJson(HttpServletResponse res, Object data) {
		res.setContentType("text/javascript; charset=utf-8");
		print(res, data);
	}

	/***************************************************************************
	 * 普通输出，不设置setContentType属性
	 * 
	 * @param res
	 * @param data
	 * @author jiangfl
	 */
	public static void printNormal(HttpServletResponse res, Object data) {
		print(res, data);
	}

	/**
	 * 输出纯文本数据
	 * 
	 * @param res
	 * @param data
	 * @author jiangfl
	 */
	public static void printText(HttpServletResponse res, Object data) {
		res.setContentType("text/plain; charset=utf-8");
		print(res, data);
	}

	/**
	 * 输出XML数据
	 * 
	 * @param res
	 * @param data
	 * @author jiangfl
	 */
	public static void printXML(HttpServletResponse res, Object data) {
		res.setContentType("application/xml; charset=utf-8");
		print(res, data);
	}

	public static void printFile(HttpServletResponse res, String realFilePath, String displayFileName)  throws Exception{
		res.setContentType("application/x-download");
		res.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(displayFileName, "UTF-8"));
		OutputStream outp = null;
		FileInputStream in = null;
		try {
			outp = res.getOutputStream();
			in = new java.io.FileInputStream(realFilePath);

			byte[] b = new byte[1024];
			int i = 0;

			while ((i = in.read(b)) > 0) {
				outp.write(b, 0, i);
			}
		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
					in = null;
				}
				
				if(outp!=null){
					outp.flush();
					outp.close();
					outp=null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
