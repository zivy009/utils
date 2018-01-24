package com.zivy.utils.video;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.alibaba.fastjson.JSONObject;
 

/**
 * 优酷
 * 
 * @author zhc 2016年6月17日
 * 
 */
public class QQvideo {
	/**
	 * 验证url 是否正确
	 * 
	 * zivy
	 *
	 * 是否无效url
	 *@param str
	 *@return
	 * 2016年12月13日
	 */
public static boolean isWX(String str){
	if(str==null || str.indexOf("qq.com")<0 || str.indexOf("vid=")<0|| str.indexOf("TPout.swf")<0){
		return true;
	}else{
		return false;
	}
}
	public static String getContent(String strUrl) throws Exception {
		try {

			URL url = new URL(strUrl);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(8000); // 设置连接超时为10s
			con.setReadTimeout(5000); // 读取数据超时也是10s
			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
			String s = "";
			StringBuffer sb = new StringBuffer("");
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * 
	 * 通用代码的iframe
	 * 
	 **/
	public static String getIframeBySwf(String swfURL) {
		String id = getSid(swfURL);
		// <iframe frameborder="0" width="640" height="498"
		// src="http://v.qq.com/iframe/player.html?vid=e03022ztc0i&tiny=0&auto=0"
		// allowfullscreen></iframe>
		String strRtn = "";
		if (id == null || id.length() < 1) {
			strRtn = "<iframe width=\"100%\" frameborder=\"0\" height=260  src=\"" + swfURL + "\"   allowfullscreen=\"\"></iframe>";

		} else {
			strRtn = "<iframe width=\"100%\"  frameborder=\"0\" height=260  src=\"https://v.qq.com/iframe/player.html?vid=" + id + "&tiny=0&auto=0 \"   allowfullscreen=\"\"></iframe>";

		}

		return strRtn;
		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
	}

	/**
	 * 
	 * zivy 分享用
	 * 
	 * @param swfURL
	 * @return 2016年8月31日
	 */
	public static String getIframeBySwfShare(String swfURL) {
		String id = getSid(swfURL);
		// <iframe frameborder="0" width="640" height="498"
		// src="http://v.qq.com/iframe/player.html?vid=e03022ztc0i&tiny=0&auto=0"
		// allowfullscreen></iframe>
		String strRtn = "";
		if (id == null || id.length() < 1) {
			StringBuffer sb = new StringBuffer();
			// sb.append("<script>function iFrameHeight() { ");
			// sb.append("var ifm= document.getElementById(\"iframepage\"); ");
			// sb.append("var subWeb = document.frames ?
			// document.frames[\"iframepage\"].document :
			// ifm.contentDocument;");
			// sb.append("if(ifm != null && subWeb != null) {");
			// sb.append("ifm.height = subWeb.body.scrollHeight;");
			// sb.append("ifm.width = subWeb.body.scrollWidth;");
			// sb.append("} ");
			// sb.append("}</script>");

			sb.append("<iframe width=\"100%\" id=\"iframepage\" frameborder=\"0\" scrolling=\"no\" marginheight=\"0\" marginwidth=\"0\" height=\"800\"  src=\"" + swfURL + "\"   allowfullscreen=\"\"></iframe>");
			strRtn = sb.toString();
		} else {
			strRtn = "<iframe width=\"100%\"  frameborder=\"0\" height=260  src=\"https://v.qq.com/iframe/player.html?vid=" + id + "&tiny=0&auto=0 \"   allowfullscreen=\"\"></iframe>";

		}

		return strRtn;
		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
	}

	public static String getIframeBySwfClick(String swfURL, String pic) {
		String id = getSid(swfURL);
		//
		// String strRtn = "<a href='http://player.youku.com/embed/"+id+"'
		// target='_blank'><img src='"+pic+"' width='100%'/></a>";
		String strRtn = "";
		if (id == null || id.length() < 1) {
			strRtn = "<a href='" + swfURL + "' target='_blank'><img src='" + pic + "' width='100%'/></a>";

		} else {
			strRtn = "<a href='http://static.video.qq.com/TPout.swf?vid=" + id + "&auto=0' target='_blank'><img src='" + pic + "' width='100%'/></a>";

		}

		return strRtn;
		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
	}

	public static String getIframeBySwfHttps(String swfURL, String host) {
		String id = getSid(swfURL);

		String strRtn = "<iframe width=\"100%\" height=260  src=\"https://" + host + "/youku/" + id + "\" frameborder=\"0\" allowfullscreen=\"\"></iframe>";

		return strRtn;
		// http://player.youku.com/player.php/Type/Folder/Fid/27650811/Ob/1/sid/XMTY0NDIwOTE2NA==/v.swf
	}

	public static void main(String[] args) {
		// String regex = "<iframe .+? src=\"(\\S+?)\" .+?></iframe>";
		String str = "http://static.video.qq.com/TPout.swf?vid=e03022ztc0i&auto=0";
		System.out.println(QQvideo.getSid(str));
	}

	/**
	 * 
	 * 获得sid 。
	 * 
	 **/

	public static String getSid(String url) {
		// http://static.video.qq.com/TPout.swf?vid=e03022ztc0i&auto=0
		// https://imgcache.qq.com/tencentvideo_v1/playerv3/TPout.swf?max_age=86400&v=20161117&vid=i0022byoe52&auto=0
		
		String regex = "\\S*?vid=(\\S+?)&auto\\S*";// "<iframe .+?
													// src=\"http://player.youku.com/embed/(XMTY0MjQwNDEzMg==)\"
													// .+?></iframe>";
		String sid = url.replaceAll(regex, "$1");// url.replaceAll("\\S*?/sid/(\\S+?)/\\S*",
													// "$1");

		if (sid.equals(url)) {// 没有替换
			sid = null;
		}
		return sid;
	}
/**
 * 
 * zivy
 *  获得视频图片
 * 
 *@param sid
 *@return
 * 2016年9月20日
 */
	public static String getPic(String sid) {
		// http://shp.qpic.cn/qqvideo_ori/0/w032479x4k4_496_280/0
		if(sid==null && sid.length()<1){
			throw new RuntimeException("视频封面图不能空");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("http://shp.qpic.cn/qqvideo_ori/0/");
		sb.append(sid);
		sb.append("_496_280/0");
		return sb.toString();
	}

}
