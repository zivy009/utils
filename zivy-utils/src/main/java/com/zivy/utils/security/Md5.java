package com.zivy.utils.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class Md5 {
	public static void main(String[] args) {
		System.out.println(getMD5By32("123456"));
		//d8578edf8458ce06fbc5bb76a58c5ca4
		//e10adc3949ba59abbe56e057f20f883e
		//e10adc3949ba59abbe56e057f20f883e
	}
	/**
	 * 32 位md5加密
	 * @author zhc
	 * @param plainText
	 * @return
	 *  2016年6月16日
	 */
    public static String getMD5By32(String plainText) {
        String re_md5 = new String();
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
 
            int i;
 
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
 
            re_md5 = buf.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return re_md5;
    }
    /** 
     * MD5加密,加密结果采用Base64进行编码 
     * @param message 要进行MD5加密的字符串 
     * @return 
     */  
    public static String getMD5ByBase64(String message) {  
        MessageDigest md= null;  
        try {  
            md= MessageDigest.getInstance("MD5");  
            byte md5[]=md.digest(message.getBytes());  
            BASE64Encoder base64=new BASE64Encoder();  
            return base64.encode(md5);  
        } catch (Exception e) {  
            throw new RuntimeException();  
        }  
    } 
}
