package com.zivy.utils.http;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * 获得所有的请求头。方便测试
 * 
 * @author zhc 2016年11月25日
 *
 *
 */
public class HttpHeaderUtils {

    public static String obtainAll(HttpServletRequest request) {
        Enumeration<String> headerS = request.getHeaderNames();
        Map<String, String> map = new HashMap<String, String>();
        while (headerS.hasMoreElements()) {
            String key = headerS.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map.toString();
    }
}
