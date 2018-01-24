package com.zivy.utils.http;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/*******************************************************************************
 * 
 * @desc 请求对象操作工具类
 * @2014-3-8 @author jiangfl
 * @Copyright:
 */
public class RequestUtil {

    private static Logger log = Logger.getLogger(RequestUtil.class);

    /**
     * 获取访问者的IP地址
     * 
     * @desc
     * @param request
     * @return
     * @author jiangfl @2014-3-8
     */
    public static String getIpAddr(HttpServletRequest req) {
        if (req == null) {
            return null;
        }
        String ip = req.getHeader("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip + "|x-forwarded-for";
        }
        ip = req.getHeader("Proxy-Client-IP");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip + "|Proxy-Client-IP";
        }

        ip = req.getHeader("WL-Proxy-Client-IP");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            return ip + "|WL-Proxy-Client-IP";
        }

        return req.getRemoteAddr();
    }

    /**
     * 
     * @desc 获取访问者浏览器信息
     * @param request
     * @return
     * @author jiangfl @2014-3-8
     */
    public static String getUserAgent(HttpServletRequest req) {
        return req.getHeader("User-Agent");
    }

    /**
     * 打印所有URL参数
     * 
     * @desc
     * @param request
     * @author jiangfl @2014-3-15
     */
    @SuppressWarnings("unchecked")
    public static void printParam(HttpServletRequest req) {
        Enumeration<String> em = req.getParameterNames();
        while (em.hasMoreElements()) {
            String key = em.nextElement();
            log.info("key:" + key + "     value:" + req.getParameter(key));
        }
    }

    /**
     * isAjaxRequest:判断请求是否为Ajax请求. <br/>
     * 
     * @author chenzhou
     * @param request
     *            请求对象
     * @return boolean
     * @since JDK 1.6
     */
    public static boolean isAjaxRequest(HttpServletRequest req) {
        String header = req.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * 获得请求中的参数，空的话，给个默认值
     * 
     * @author zhc
     * @param request
     * @param key
     * @param defaultValue
     * @return
     * @throws RuntimeException
     *             2016年5月14日
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue) throws RuntimeException {
        String string = null;
        try {
            string = request.getParameter(key);
            if (StringUtils.isEmpty(string)) {
                string = defaultValue;
            }
        } catch (Exception e) {
            RuntimeException runtimeException = new RuntimeException("获得参数异常" + key);
            throw runtimeException;
        }
        return string;
    }

    /**
     * 
     * @author zhc
     * @param request
     * @param key
     * @return
     * @throws RuntimeException
     *             2016年5月14日
     */
    public static String getString(HttpServletRequest request, String key) throws RuntimeException {
        String string = null;
        try {
            string = request.getParameter(key);
        } catch (Exception e) {

            RuntimeException runtimeException = new RuntimeException("获得参数异常" + key);

            throw runtimeException;
        }
        return string;
    }

    public static Integer getInteger(HttpServletRequest request, String key) {
        boolean isAllowEmpty = false;
        Integer defaultValue = 0;
        return getInteger(request, key, isAllowEmpty, defaultValue);
    }

    public static Integer getIntegerNotZero(HttpServletRequest request, String key) {
        boolean isAllowEmpty = false;

        Integer defaultValue = null;
        return getIntegerNotZero(request, key, isAllowEmpty, defaultValue);
    }

    public static Integer getIntegerNew(HttpServletRequest request, String key) {
        boolean isAllowEmpty = false;
        Integer defaultValue = null;
        return getInteger(request, key, isAllowEmpty, defaultValue);
    }

 

    /**
     * 
     * @author zhc
     * @param key
     * @return 2016年5月14日
     */
    public static Integer getInteger(HttpServletRequest request, String key, boolean isAllowEmpty, Integer defaultValue) {
        Integer integer = null;
        try {
            String string = StringUtils.trimToEmpty(request.getParameter(key));

            if (StringUtils.isAnyEmpty(string)) {
                if (isAllowEmpty) {
                    if (defaultValue != null) {
                        string = defaultValue.toString();
                        integer = Integer.parseInt(string);
                    } else {
                        integer = null;
                    }
                } else {

                    RuntimeException runtimeException = new RuntimeException("参数不允许为空" + key);

                    throw runtimeException;
                }

            } else {
                integer = Integer.parseInt(string);
            }

        } catch (Exception e) {
            RuntimeException runtimeException = new RuntimeException("参数为空或参数非数字类型" + key);
            throw runtimeException;
        }
        return integer;
    }

    /**
     * 
     * zivy
     *
     * 
     * @param request
     * @param key
     * @param isAllowEmpty
     * @param defaultValue
     * @return 2017年1月16日
     */
    public static Double getDouble(HttpServletRequest request, String key, boolean isAllowEmpty, Integer defaultValue) {
        Double dble = null;
        try {
            String string = StringUtils.trimToEmpty(request.getParameter(key)).trim();

            if (StringUtils.isAnyEmpty(string)) {
                if (isAllowEmpty) {
                    if (defaultValue != null) {
                        string = defaultValue.toString();
                        dble = Double.parseDouble(string);
                    } else {
                        dble = null;
                    }
                } else {
                    RuntimeException runtimeException = new RuntimeException("参数不允许为空" + key);
                    throw runtimeException;
                }

            } else {
                dble = Double.parseDouble(string);
            }

        } catch (Exception e) {

            RuntimeException runtimeException = new RuntimeException("参数为空或参数非数字类型" + key);
            throw runtimeException;
        }
        return dble;
    }

    public static Integer getIntegerNotZero(HttpServletRequest request, String key, boolean isAllowEmpty, Integer defaultValue) {
        Integer integer = null;
        try {
            String string = StringUtils.trimToEmpty(request.getParameter(key)).trim();

            if (StringUtils.isAnyEmpty(string)) {
                if (isAllowEmpty) {
                    if (defaultValue != null) {
                        string = defaultValue.toString();
                        integer = Integer.parseInt(string);
                    } else {
                        integer = null;
                    }
                } else {
                    RuntimeException runtimeException = new RuntimeException("参数不允许为空" + key);
                    throw runtimeException;
                }

            } else {
                integer = Integer.parseInt(string);
                if (integer == 0) {// 必填字段。 不允许0；
                    RuntimeException runtimeException = new RuntimeException("必填参数，不能用0代替。" + key);

                    throw runtimeException;
                }
            }

        } catch (Exception e) {

            RuntimeException runtimeException = new RuntimeException("参数为空或参数非数字类型" + key);

            throw runtimeException;
        }
        return integer;
    }

    public Long getLong(HttpServletRequest request, String key) throws RuntimeException {
        Long longs = null;
        try {
            String string = StringUtils.trimToEmpty(request.getParameter(key)).trim();
            longs = Long.parseLong(string);
        } catch (Exception e) {

            RuntimeException runtimeException = new RuntimeException("参数为空或参数非数字类型" + key);

            throw runtimeException;
        }
        return longs;
    }

    /**
     * 文件的相对路径获得绝对路径
     * 
     * @author zhc
     * @param request
     * @param filepath
     * @return 2016年6月22日
     */
    public static String getRealPath(HttpServletRequest request, String filepath) {
        String path = request.getSession().getServletContext().getRealPath(filepath);
        return path;
    }

}
