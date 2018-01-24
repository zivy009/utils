package com.zivy.utils.video;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;

/**
 * 优酷 视频
 * 
 * @author zhc 2016年6月17日
 * 
 */
public class Youku {

	
//	public static String getContent(String strUrl) throws Exception {
//		try {
//
//			URL url = new URL(strUrl);
//			URLConnection con = url.openConnection();
//			con.setConnectTimeout(8000); // 设置连接超时为10s
//			con.setReadTimeout(5000); // 读取数据超时也是10s
//			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
//			String s = "";
//			StringBuffer sb = new StringBuffer("");
//			while ((s = br.readLine()) != null) {
//				sb.append(s);
//			}
//			br.close();
//			return sb.toString();
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}
//
//	/**
//	 * 
//	 * 通用代码的iframe
//	 * 
//	 * **/
//	public static String getIframeBySwf(String swfURL) {
//		String id = getSid(swfURL);
//		String strRtn = "<iframe width=\"100%\" height=260  src=\"http://player.youku.com/embed/" + id + "\" frameborder=\"0\" allowfullscreen=\"\"></iframe>";
//
//		return strRtn;
//		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
//	}
//	
//	public static String getIframeBySwfClick(String swfURL,String pic) {
//		String id = getSid(swfURL);
//		
//		String strRtn = "<a href='http://player.youku.com/embed/"+id+"' target='_blank'><img src='"+pic+"' width='100%'/></a>";
//
//		return strRtn;
//		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
//	}
//	
//	public static String getIframeBySwfHttps(String swfURL,String host) {
//		String id = getSid(swfURL);
//		String strRtn = "<iframe width=\"100%\" height=260  src=\"https://"+host+"/youku/" + id + "\" frameborder=\"0\" allowfullscreen=\"\"></iframe>";
//
//		return strRtn;
//		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
//	}
// 
//	public static void main(String[] args) {
//		String regex = "<iframe .+? src=\"(\\S+?)\" .+?></iframe>";
//
//		System.out.println("<iframe height=498 width=510 src=\"http://player.youku.com/embed/XMTY0MjQwNDEzMg==\" frameborder=0 allowfullscreen></iframe>".replaceAll(regex, "$1"));
//	}
//
//	/**
//	 * 
//	 *  获得sid 。
//	 * 
//	 * **/
//	
//	public static String getSid(String url) {
//
//		String regex = "\\S*?/sid/(\\S+?)/\\S*";// "<iframe .+? src=\"http://player.youku.com/embed/(XMTY0MjQwNDEzMg==)\" .+?></iframe>";
//		String sid = url.replaceAll(regex, "$1");// url.replaceAll("\\S*?/sid/(\\S+?)/\\S*",
//													// "$1");
//		if (sid.equals(url)) {// 没有替换
//			sid = url.replaceAll("\\S*/(\\w+?==)", "$1");
//		}
//
//		if (sid.equals(url)) {// 没有替换
//			sid =null;
//		}
//		return sid;
//	}
//
//	/**
//	 * 获得优酷缩略图
//	 * 
//	 * @author zhc
//	 * @param url
//	 * @return 2016年6月17日
//	 */
//	public static String getTumbPic(String url) {
//
//		// String url =
//		// "http://player.youku.com/player.php/sid/XMjU0MjI2NzY0/v.swf";
//		// String url
//		// ="http://player.youku.com/player.php/sid/XMTYxMDgzMzE5Ng==/v.swf";
//		// <iframe height=498 width=510
//		// src="http://player.youku.com/embed/XMTY0MjQwNDEzMg==" frameborder=0
//		// allowfullscreen></iframe>
//		try {
//			// String
//			// regex="\\S*?/sid/(\\S+?)/\\S*";//"<iframe .+? src=\"http://player.youku.com/embed/(XMTY0MjQwNDEzMg==)\" .+?></iframe>";
//			String sid = getSid(url);// url.replaceAll("\\S*?/sid/(\\S+?)/\\S*",
//										// "$1");
//
//			String reqUrl = "http://play.youku.com/play/get.json?vid=" + sid + "&ct=10&ran=1951";
//			String text = getContent(reqUrl);
//
//			JSONObject json = JSONObject.parseObject(text);
//			JSONObject videoJson = json.getJSONObject("data").getJSONObject("video");
//			String pic = videoJson.getString("logo");
//			return pic;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
}
