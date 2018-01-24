package com.zivy.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.util.StringUtil;

 

/**
 * 
 * @Description:文件操作工具类
 * @Copyright: 
 * @Company: 
 * @Author : jiangfl
 * @Date: 2012-10-15
 * @Version 1.0
 */
public class FileUtil {
	/***
	 * 文件上传根目录
	 */
	public static final String FILE_ROOT_PATH_KEY = "uploadDir";

	/**
	 * 删除单个文件
	 * 
	 * @param path
	 *            绝对路径
	 * @return
	 */
	public static boolean removeFileByAbsolute(String path) throws Exception {
		try {
			File file = new File(path);
			if (file.exists()) {
				file.delete();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	


	/***************************************************************************
	 * 获取路径的后缀
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileSuffix(String fileName) {

		String fileSuffix = null;
		if (StringUtilZivy.notEmpty(fileName)) {
			int i = fileName.lastIndexOf(".");
			if (i >= 0) {
				fileSuffix = fileName.substring(i, fileName.length());
			} else {
				fileSuffix = fileName;
			}
		}
		return fileSuffix;
	}
	 
	/***************************************************************************
	 * 根据路径获取文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String filePath) {
		filePath = filePath.replace("\\", File.separator);

		String fileName = null;
		if (StringUtilZivy.notEmpty(filePath)) {
			int i = filePath.lastIndexOf(File.separator);
			if (i >= 0) {
				fileName = filePath.substring(i + 1, filePath.length());
			} else {
				fileName = filePath;
			}
		}
		return fileName;
	}

	/***************************************************************************
	 * 根据路径获取文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getDirectory(String filePath) {
		filePath = filePath.replace("\\", File.separator);
		filePath = filePath.replaceAll("//", File.separator);

		String dir = null;
		if (StringUtilZivy.notEmpty(filePath)) {
			int i = filePath.lastIndexOf(File.separator);
			if (i >= 0) {
				dir = filePath.substring(0, i + 1);
			} else {
				dir = filePath;
			}
		}
		return dir;
	}

	
	/**
	 * 如果字符串不以/结束，则默认添加一个斜杠
	 * 
	 * @param str
	 * @return
	 */
	public static String appendSeparator(String str) {
		if (str == null) {
			return "";
		}
		return str.endsWith(File.separator) ? str : str + File.separator;
	}

	/***
	 * 检测fileName文件是否存在，如果不存在，则将data写入磁盘
	 * 
	 * @param fileName
	 *            绝对路径
	 * @param data
	 *            数据
	 * @return true:成功,false:失败
	 * @author jiangfl
	 */
	public static boolean writeDisk(String fileName, byte[] data) {
		try {
			FileOutputStream fos = null;
			File file = null;
			try {
				String dir = fileName.replace(File.separator + File.separator,
						File.separator);
				if (dir.indexOf(File.separator) != -1) {
					dir = dir.substring(0, dir.lastIndexOf(File.separator));
				}
				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdirs();
				}

				file = new File(fileName);
				if (!file.exists()) {
					fos = new FileOutputStream(file);
					file.createNewFile();
					fos.write(data);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (StringUtilZivy.notEmpty(fos)) {
						fos.flush();
						fos.close();
						fos = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/***************************************************************************
	 * 检测path路径下是否存在文件，存在返回true,不存在返回false
	 * 
	 * @param filename
	 *            绝对路径
	 * @return
	 * @author jiangfl
	 */
	public static boolean checkDiskFile(String filename) {
		File file = new File(filename);
		return file.exists();
	}

	/***
	 * 创建目录
	 * 
	 * @param path绝对路径
	 * @author jiangfl
	 */
	public static void mkdirsByAbsolute(String fileName) {
		String dir = fileName.replace(File.separator + File.separator,
				File.separator);
		if (dir.indexOf(File.separator) != -1) {
			dir = dir.substring(0, dir.lastIndexOf(File.separator));
		}
		File dirFile = new File(dir);
		if (!dirFile.exists()) {
			dirFile.mkdirs();
		}
	}

	public static boolean isImage(String fileName) {
		String img=getFileSuffix(fileName).toLowerCase();
		//System.out.println(img);
		return ".jpg".equals(img)||".gif".equals(img)||".png".equals(img)||".bmp".equals(img)||".jpeg".equals(img);
	}
	
	public static boolean isJS(String fileName) {
		String js=getFileSuffix(fileName).toLowerCase();
		//System.out.println(img);
		return ".js".equals(js);
	}
	/**
	 * 
	 * @param fileName
	 * @return
	 * zhc
	 *2016年3月9日
	 */
	public static boolean isVideo(String fileName) {
		String video=getFileSuffix(fileName).toLowerCase();
		//System.out.println(img);avi 3gp mp4
		return ".mp4".equals(video)||".avi".equals(video)||".3gp".equals(video);
	}
	public static void main(String[] args) {
		
		String str="https://7xr9xo.com2.z0.glb.qiniucdn.com/f93a0e97c87546848d74bd5898b430c7.mp4";
		String str2=getM3u8ByUrl(str);
		System.out.println(FileUtil.getFileName(str));
		System.out.println(FileUtil.getFileName(str2));
	}
	/**
	 * 只返回文件名的部分，不包括后缀名。
	 * @author zhc
	 * @param urlpath
	 * @return
	 *  2016年4月20日
	 */
	public static String getM3u8ByUrl(String urlpath){
		String str2=urlpath.replaceAll("(^https?://.*?\\w+\\/)(\\w+)(\\.\\w+)", "$1qp_$2.m3u8");
		return str2;
	}
	
	/**
	 * 
	 * @param size	字节
	 * @return true 超过限制大小；
	 * zhc
	 *2016年3月16日
	 */
	public static  boolean chkSizeFile(long size) {
		return false;// 暂时不处理
//		if(size>MyConstants.MAX_SIZE){// 
//			return true;
//		}else{
//			return false;
//		}
	}
}
