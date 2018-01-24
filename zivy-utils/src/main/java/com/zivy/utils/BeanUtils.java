package com.zivy.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;

import com.zivy.utils.date.DateUtil;

/**
 * 一个将map对象的属性复制到某个对象中去的工具类
 * 
 * @author jiangfl
 * 
 */
public class BeanUtils {

	/**
	 * 拷贝属性，将源req参数的属性值，拷贝到t对象中属性中去,为了性能不负责进行内置对象数据的拷贝
	 * 
	 * @param req
	 *            源
	 * @param obj
	 *            目标
	 * @param args
	 *            忽略拷贝的字段名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void copyProperties(HttpServletRequest req, Object t, String... args) {
		Class cls = t.getClass();
		Field fields[] = cls.getDeclaredFields();
		String fullName = null;
		String shortName = null;
		Method method = null;
		Object sourceValue = null;
		Object params[] = null;
		Map reqMap = req.getParameterMap();
		for (Field f : fields) {
			try {
				shortName = f.getName();
				// 当前字段是否忽略
				if (ignore(args, shortName)) {
					continue;
				}
				// 如果在map中没有包含该属性键，则跳过，不进行复制
				if (!reqMap.containsKey(shortName)) {
					continue;
				}
				sourceValue = req.getParameter(shortName);
				params = getClass(f.getType(), sourceValue);
				if (params[0] != null) {// params[0]为null的话，说明该属性为对象属性，忽略注入
					fullName = "set" + getCapitalise(shortName);
					method = cls.getDeclaredMethod(fullName, (Class) params[0]);
					method.invoke(t, params[1]);
				}
			} catch (Exception e) {
				if (params[1] == null) {
					System.out.println(shortName + "参数注入出错!请确保" + shortName + "参数为封装数据类型,否则无法将null注入到值类型变量中!");
				}
				e.printStackTrace();
			}
		}
	}

	
	private static String getCapitalise(String str){
	   
		if(StringUtils.isNotEmpty(str)){
			return str.substring(0,1).toUpperCase()+str.substring(1,str.length());
		}
		return str;
	}
	/**
	 * 将源对象source中的属性拷贝到目标对象target中，为了性能不负责进行内置对象数据的拷贝
	 * 
	 * @param source
	 * @param target
	 * @param args
	 *            忽略拷贝的字段名称
	 * @author jiangfl
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	public static void copyProperties(Object source, Object target, String... args) {
		Class cls = target.getClass();
		Field fields[] = cls.getDeclaredFields();
		String fullName = null;
		String shortName = null;
		Method method = null;
		Object sourceValue = null;
		Object params[] = null;

		Class sourceCls = source.getClass();
 
		for (Field f : fields) {
			try {
				shortName = f.getName();
				 
				// 当前字段是否忽略
				if (ignore(args, shortName)) {
					continue;
				}
				 
				// 如果在map中没有包含该属性键，则跳过，不进行复制
				if (sourceCls.getDeclaredField(shortName) == null) {
					continue;
				}
				method = sourceCls.getDeclaredMethod("get" + getCapitalise(shortName));
				sourceValue = method.invoke(source);

				params = getClass(f.getType(), sourceValue);
				if (params[0] != null) {// params[0]为null的话，说明该属性为对象属性，忽略注入
					fullName = "set" + getCapitalise(shortName);
					method = cls.getDeclaredMethod(fullName, (Class) params[0]);
					method.invoke(target, params[1]);
				}
			} catch (Exception e) {
				if (params[1] == null) {
					System.out.println(shortName + "参数注入出错!请确保" + shortName + "参数为封装数据类型,否则无法将null注入到值类型变量中!");
				}
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}
	/**
	 * 
	 * @param source
	 * @param target
	 * @param args
	 * zhc
	 *2016年3月4日
	 */
	public static void copySameProperties(Object source, Object target, String... args) {
		Class cls = target.getClass();
		Field fields[] = cls.getDeclaredFields();
		String fullName = null;
		String shortName = null;
		Method method = null;
		Object sourceValue = null;
		Object params[] = null;

		Class sourceCls = source.getClass();
 
		for (Field f : fields) {
			try {
				shortName = f.getName();
				
				// 当前字段是否忽略
				if (args!=null && ignore(args, shortName)) {
					continue;
				}
				 
				// 如果在map中没有包含该属性键，则跳过，不进行复制
				if (sourceCls.getDeclaredField(shortName) == null) {
					continue;
				}
				method = sourceCls.getDeclaredMethod("get" + getCapitalise(shortName));
				//System.out.println("method= "+method);
				sourceValue = method.invoke(source);

				params = getClass(f.getType(), sourceValue);
			
				if (params[0] != null) {// params[0]为null的话，说明该属性为对象属性，忽略注入
					fullName = "set" + getCapitalise(shortName);
					method = cls.getDeclaredMethod(fullName, (Class) params[0]);
					
					if(params[1]!=null){
					//	System.out.println("method= "+method);
					//	System.out.println("params[1]= "+params[1]);
						
						method.invoke(target, params[1]);
					}
					
				}
			} catch (Exception e) {
				if (params[1] == null) {
					System.out.println(shortName + "参数注入出错!请确保" + shortName + "参数为封装数据类型,否则无法将null注入到值类型变量中!");
				}
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * 拷贝属性，将源map里面的属性值，拷贝到obj对象中属性中去,为了性能不负责进行内置对象数据的拷贝
	 * 
	 * @param map
	 *            源
	 * @param obj
	 *            目标
	 * @param upperCase
	 *            是否转换成大写
	 * @param args
	 *            忽略拷贝的字段名称
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void copyProperties(Map map, Object t, boolean upperCase, String... args) {
		Class cls = t.getClass();
		Field fields[] = cls.getDeclaredFields();
		String fullName = null;
		String shortName = null;
		Method method = null;
		Object sourceValue = null;
		Object params[] = null;

		for (Field f : fields) {
			try {
				shortName = f.getName();
				// 当前字段是否忽略
				if (ignore(args, upperCase ? shortName.toUpperCase() : shortName)) {
					continue;
				}
				// 如果在map中没有包含该属性键，则跳过，不进行复制
				if (!map.containsKey(upperCase ? shortName.toUpperCase() : shortName)) {
					continue;
				}
				sourceValue = map.get(upperCase ? shortName.toUpperCase() : shortName);

				params = getClass(f.getType(), sourceValue);
				if (params[0] != null) {// params[0]为null的话，说明该属性为对象属性，忽略注入
					fullName = "set" + getCapitalise(shortName);
					params = getClass(f.getType(), sourceValue);
					if(params[1]!=null){
						method = cls.getDeclaredMethod(fullName, (Class) params[0]);
						method.invoke(t, params[1]);
						//System.out.println("params[1]= "+params[1]);
					} 
					
				}

			} catch (Exception e) {
				if (params[1] == null) {
					System.out.println(shortName + "参数注入出错!请确保" + shortName + "参数为封装数据类型,否则无法将null注入到值类型变量中!");
				}
				e.printStackTrace();
			}
		}

	}

	/**
	 * 忽略字段
	 * 
	 * @param args
	 * @param item
	 * @return
	 */
	private static boolean ignore(String args[], String item) {
		for (String o : args) {
			if (item.equals(o)) {
				return true;
			}
		}
		return false;
	}
 
	/**
	 * 
	 * @param type
	 * @param data
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	private static Object[] getClass(Class type, Object data) throws Exception {
		Object obj[] = new Object[] { null, null };
		try {
			if (Byte.class == type) {
				obj[0] = Byte.class;
				 
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Byte.valueOf(data.toString());
				}
				return obj;
			} else if (byte.class == type) {
				obj[0] = byte.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Byte.parseByte(data.toString());
				}
				return obj;
			} else if (Short.class == type) {
				obj[0] = Short.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Short.valueOf(data.toString());
				}
				return obj;
			} else if (short.class == type) {
				obj[0] = short.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Short.parseShort(data.toString());
				}
				return obj;
			} else if (Integer.class == type) {
				obj[0] = Integer.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Integer.valueOf(data.toString());
				}
				return obj;
			} else if (int.class == type) {
				obj[0] = int.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Integer.parseInt(data.toString());
				}
				return obj;
			} else if (Float.class == type) {
				obj[0] = Float.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Float.valueOf(data.toString());
				}
				return obj;
			} else if (float.class == type) {
				obj[0] = float.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Float.parseFloat(data.toString());
				}
				return obj;
			} else if (Double.class == type) {
				obj[0] = Double.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Double.valueOf(data.toString());
				}
				return obj;
			} else if (double.class == type) {
				obj[0] = double.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Double.parseDouble(data.toString());
				}
				return obj;
			} else if (String.class == type) {
				obj[0] = String.class;
				if (data != null) {
					obj[1] = String.valueOf(data.toString());
				}
				return obj;
			} else if (Long.class == type) {
				obj[0] = Long.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Long.valueOf(data.toString());
				}
				return obj;
			} else if (long.class == type) {
				obj[0] = long.class;
				if (StringUtilZivy.notEmpty(data)) {
					obj[1] = Long.parseLong(data.toString());
				}
				return obj;
			} else if (Date.class == type) {
				obj[0] = Date.class;
				if (data != null) {
					if (data.getClass() == Date.class) {
						obj[1] = (Date) data;
					} else if (data.getClass() == String.class) {
						obj[1] = DateUtil.getDate(data.toString());
					}
				}
				return obj;
			} else if (Timestamp.class == type) {
				obj[0] = Timestamp.class;
				if (data != null) {
					if (data.getClass() == Date.class) {
						obj[1] = DateUtil.getTimestamp((Date) data);
					} else if (data.getClass() == String.class) {
						obj[1] = DateUtil.getTimestamp(data.toString());
					} else if (data.getClass() == Timestamp.class) {
						obj[1] = (Timestamp) data;
					}
				}
				return obj;
			}

		} catch (Exception e) {
			System.err.println("注入属性类型" + type + "值为：" + data + "出错");
			e.printStackTrace();
			throw e;
		}
		return obj;
	}

	/**
	 * 
	 * @param cls
	 *            类 class
	 * @param alias
	 *            别名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getFieldsAsAlias(Class cls, String alias) {
		String str = "";
		Field fields[] = cls.getDeclaredFields();
		for (Field f : fields) {
			if (StringUtilZivy.notEmpty(alias)) {
				str += alias + "." + f.getName() + " as " + f.getName() + ",";
			} else {
				str += f.getName() + " as " + f.getName() + ",";
			}
		}
		if (str.length() != 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	/**
	 * 
	 * @param cls
	 *            类 class
	 * @param alias
	 *            别名
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getFields(Class cls, String alias) {
		String str = "";
		Field fields[] = cls.getDeclaredFields();
		for (Field f : fields) {
			if (StringUtilZivy.notEmpty(alias)) {
				str += alias + "." + f.getName() + ",";
			} else {
				str += f.getName() + ",";
			}
		}
		if (str.length() != 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	public static void main(String[] args) {
		// System.out.println(BeanUtils.getFields(WrHdzdxzbB.class,""));
	    System.out.println( StringUtils.isNotEmpty("s"));
	}
}
