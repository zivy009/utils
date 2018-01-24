package com.zivy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:辅助构建SQL语句
 * @Copyright: Copyright 2011 ShenZhen DSE SoftwareCorparation
 * @Company: 深圳东深电子股份有限公司
 * @author ZOUWEI
 * @version 1.0 2011-06-02
 */
public class SqlUtil {

	/**
	 * 修复使用in语句查询时，参数个数不能超过1000个的问题，使用方法如：<br/>
	 * String sql = "SELECT * FROM TEST t WHERE "+SqlUtil.fixInQuery1K("t.id",
	 * "a,b,c,1,2,3,")
	 * 
	 * @param field
	 *            字段名
	 * @param values
	 *            值列表，使用“,”分割的字符串
	 * @return 处理结果
	 */
	public static String fixInQuery1K(String field, String values) {
		String[] _values = ("'" + values.replaceAll(",", "','") + "'").split(",");
		StringBuffer sql = new StringBuffer(" " + field + " IN (");

		for (int i = 0, l = _values.length; i < l; i++) {
			sql.append(_values[i] + ",");
			if (i % 3 == 0 || i == l - 1) {
				sql.deleteCharAt(sql.length() - 1).append(")");
				if (i < l - 1) {
					sql.append(" OR " + field + " IN (");
				}
			}
		}
		return sql.toString();
	}

	/**
	 * 自动生成sql或者hql的not in(?,?,?)格式字符串
	 * 
	 * @param field
	 *            条件字段名称
	 * @param length
	 *            个数 参数个数
	 * @return 字符串
	 * @author jiangfl
	 */
	public static String appendIn(String field, Integer length) {
		return appendInORNotIn(field, length, "in");
	}

	/**
	 * 自动生成sql或者hql的 in(?,?,?)格式字符串
	 * 
	 * @param field
	 *            条件字段名称
	 * @param length
	 *            个数 参数个数
	 * @return 字符串
	 * @author jiangfl
	 */
	public static String appendNotIn(String field, Integer length) {
		return appendInORNotIn(field, length, "not in");
	}

	private static String appendInORNotIn(String field, Integer length, String inOrNotIN) {
		if (length == 0) {
			return "";
		}
		StringBuffer s = new StringBuffer("(" + field + " " + inOrNotIN + "  (");
		for (int i = 1; i <= length; i++) {
			if (i % 1000 == 0) {
				s = new StringBuffer(StringUtilZivy.substringBeforeLast(s.toString(), ","));
				s.append(") or ").append(field).append(" ").append(inOrNotIN).append(" (");
			}
			s.append("?,");
		}
		return StringUtilZivy.substringBeforeLast(s.toString(), ",") + "))";
	}

	/**
	 * 去除HQL查询语句的最后一个order by部分。 如果order by 是子查询 不去除
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @return 返回去除了order by部分的语句。
	 */
	public static String removeOrders(String hql) {
		Pattern p = Pattern.compile("\\sorder\\s", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		if (!m.find()) {
			return hql;
		}
		hql = m.replaceAll(" order ");
		// 最后一个 order的位置
		int len = org.apache.commons.lang3.StringUtils.lastIndexOf(hql, " order ");
		if (len > 0) {
			// order by 的前部分
			String sql_1 = org.apache.commons.lang3.StringUtils.substring(hql, 0, len);
			// order by 及后部分
			String sql_2 = org.apache.commons.lang3.StringUtils.substring(hql, len, hql.length());
			// 判断是否包含 ）
			if (numberOfStr(sql_2, ")") <= 0) {
				return sql_1;
			}
		}
		return hql;
	}

	/**
	 * 根据查询语句返回对应查询总数的语句 去除HQL查询语句的select部分。
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @return 返回去除了select部分的语句。 如select aa from tbale 返回 from tbale
	 */
	public static String removeSelect(String hql) {
		hql = " " + hql;
		Pattern p = Pattern.compile("\\sfrom\\s", Pattern.CASE_INSENSITIVE);
		hql = p.matcher(hql).replaceAll(" from ");
		// 查询语句如果只包含一个form
		if (numberOfStr(hql, " from ") == 1) {
			return hql.substring(hql.indexOf(" from "));
		} else {// 出现多个from 语句可能比较复杂 另外处理
				// 出现的位置
			int index = -1;
			// 循环多次出现的位置
			while ((index = hql.indexOf(" from ", index + 1)) >= 0) {
				String newstr = hql.substring(index);
				// 如果出现位置 截取字符串 中包含()都匹配，就直接返回
				if (numberOfStr(newstr, "(") == numberOfStr(newstr, ")")) {
					return newstr;
				}
			}
		}
		return "";

	}

	/**
	 * 根据查询语句返回对应查询总数的语句 去除HQL查询语句的select部分。返回select count(*) 语句
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @return 返回查询select count(*) form xx 语句。
	 */
	public static String selectCount(String hql) {
		return " select count(*) " + removeSelect(hql);
	}

	/**
	 * 判断出现的位置个数
	 * 
	 * @param str
	 * @param con
	 * @return
	 */
	private static int numberOfStr(String str, String con) {
		return org.apache.commons.lang3.StringUtils.countMatches(str, con);
	}

	/***
	 * sql语句采用 in(?,?,?) 返回里面的参数个数字符串
	 * 
	 * @param lenth
	 * @return
	 * @author jiangfl
	 */
	public static String getPlaceholder(Integer lenth) {
		return StringUtilZivy.getJoinSplitChar("?", ",", lenth);
	}

	public static void main(String[] args) {

		System.out.println(appendNotIn("a.bb", 10));
	}

}
