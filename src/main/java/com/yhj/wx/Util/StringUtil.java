package com.yhj.wx.Util;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 帮助实现一些通用的字符串处理
 * 
 */
public class StringUtil {
	public StringUtil() {
	}

	/**
	 * 将字符串以指定字符串切割后,分配到List中
	 * 
	 * @param strValue-->输入字符串
	 * @return List
	 */
	public static List getTokenizerList(String strValue, String delim) {
		List myList = new ArrayList();
		StringTokenizer stChat = new StringTokenizer(strValue, delim);
		int iLength = stChat.countTokens();
		for (int i = 0; i < iLength; i++) {
			String strTemp = stChat.nextToken();
			if (strTemp == null)
				strTemp = "";
			myList.add(strTemp);
		}
		return myList;
	}

	/**
	 * 将String[]中字符串以逗号分割后拼成一个字符串,不带有单引号
	 * 
	 * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static String parseToSQLString(String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return "";
		String myStr = "";
		for (int i = 0; i < strArray.length - 1; i++) {
			myStr += strArray[i] + ",";
		}
		myStr += strArray[strArray.length - 1];
		return myStr;
	}

	/**
	 * 将String[]中字符串以逗号分割后拼成一个字符串,带有单引号
	 * 
	 * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static String parseToSQLStringComma(String[] strArray) {
		if (strArray == null || strArray.length == 0)
			return "";
		String myStr = "";
		for (int i = 0; i < strArray.length - 1; i++) {
			myStr += "'" + strArray[i] + "',";
		}
		myStr += "'" + strArray[strArray.length - 1] + "'";
		return myStr;
	}

	/**
	 * 将String[]中字符串以逗号分割后拼成一个字符串,带有单引号
	 * 
	 * @param strArray-->输入字符串数组
	 * @return String
	 */
	public static String parseToSQLStringComma(Object[] strArray) {
		if (strArray == null || strArray.length == 0)
			return "";
		String myStr = "";
		for (int i = 0; i < strArray.length - 1; i++) {
			myStr += "'" + strArray[i] + "',";
		}
		myStr += "'" + strArray[strArray.length - 1] + "'";
		return myStr;
	}

	/**
	 * 将ISO字符串转换为GBK编码的字符串。
	 * 
	 * @param original-->输入字符串
	 * @return 经编码后的字符串，如果有异常，则返回原编码字符串
	 */
	public static String isoToGbk(String original) {
		if (original != null) {

			try {
				return new String(original.getBytes("iso-8859-1"), "gbk");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 将iso-8859-1字符串转换为UTF-8编码的字符串。
	 * 
	 * @param original-->输入字符串
	 * @return 经编码后的字符串，如果有异常，则返回原编码字符串
	 */
	public static String isoToUtf8(String original) {
		if (original != null) {

			try {
				return new String(original.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 功能: 把指定字符串original 从encode1 转化到encode2
	 * 
	 * @param original
	 * @param encode1
	 * @param encode2
	 * @return
	 */
	public static String encodeToEncode(String original, String encode1, String encode2) {
		if (original != null) {
			try {
				return new String(original.getBytes(encode1), encode2);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 功能: 以encode1的编码方式获得original
	 * 
	 * @param original
	 * @param encode1
	 * @return
	 */
	public static String getStringByEncode(String original, String encode1) {
		if (original != null) {
			try {
				return new String(original.getBytes(), encode1);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * 功能: 把指定字符串strSource 中的strFrom 全部替换为strTo
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
	public static String replaceAllString(String strSource, String strFrom, String strTo) {
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
		}
		strDest = strDest + strSource;

		return strDest;
	}

	/**
	 * 过滤Html页面中的敏感字符
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(String value) {

		if (value == null) {
			return ("");
		}

		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);

		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
			case '<':
				result.append("&lt;");
				break;
			case '>':
				result.append("&gt;");
				break;
			case '&':
				result.append("&amp;");
				break;
			case '"':
				result.append("&quot;");
				break;
			case '\'':
				result.append("&#39;");
				break;
			case '\n':
				result.append("<BR>");
				break;
			case '\r':
				result.append("<BR>");
				break;
			default:
				result.append(content[i]);
			}
		}

		return result.toString();
	}

	/**
	 * 过滤Html页面中的敏感字符，接受过滤的字符列表和转化后的值
	 * 
	 * @param value
	 * @return
	 */
	public static String replaceStringToHtml(String value, String[][] aString) {

		if (value == null) {
			return ("");
		}

		char content[] = new char[value.length()];
		value.getChars(0, value.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);

		for (int i = 0; i < content.length; i++) {
			boolean isTransct = false;
			for (int j = 0; j < aString.length; j++) {
				if (String.valueOf(content[i]).equals(aString[j][0])) {
					result.append(aString[j][1]);
					isTransct = true;
					break;
				}
			}
			if (!isTransct) {
				result.append(content[i]);
			}
		}

		return result.toString();
	}

	/**
	 * 判断一个数组是否包含一个字符串
	 * 
	 * @param arrayString
	 * @param str
	 * @return
	 */
	public static boolean ArrayContainString(String[] arrayString, String str) {
		if (arrayString == null || arrayString.length == 0) {
			return false;
		}
		for (int i = 0; i < arrayString.length; i++) {
			if (arrayString[i].equals(str))
				return true;
		}
		return false;
	}

	/**
	 * 将Map转换为p1#v1+p2#v2格式的字符串
	 * 
	 * @param map
	 * @return
	 */
	public static String parseMap(Map map) {
		StringBuffer result = new StringBuffer();
		Iterator iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next().toString();
			result.append("+" + key + "#" + map.get(key));
		}
		return result.toString().substring(1, result.length());
	}

	/**
	 * 将p1#v1+p2#v2格式的字符串转换为Map(String,String)
	 * 
	 * @param str
	 * @return
	 */
	public static Map parseString(String str) {
		// LXZTODO 增加校验方法
		Map result = new HashMap();
		if (str == null || str.trim().length() == 0)
			return result;
		String[] elementsArray = str.split("\\+");
		for (int i = 0; i < elementsArray.length; i++) {
			String[] ele = elementsArray[i].split("#");
			if (ele.length != 2)
				continue;
			result.put(ele[0], ele[1]);
		}
		return result;
	}

	/**
	 * 将字符串转换为数组
	 * 
	 * @param str
	 * @param splitStr
	 * @return
	 */
	public static String[] parseString(String str, String splitStr) {
		String[] result = new String[2];
		String[] fieldStr = str.split(splitStr);
		if (fieldStr.length == 1) {
			result[0] = "";
			result[1] = fieldStr[0];
		} else {
			result = fieldStr;
		}
		return result;
	}


	public static boolean isEmpty(Object str) {
		if (str == null || str.toString().length() == 0)
			return true;
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str == null || str.length() == 0)
			return true;
		return false;
	}

	public static String getTrimedStr(String str) {
		if (str == null || str.trim().length() == 0)
			return "";
		return str.trim();
	}

	/**
	 * Turn special characters into escaped characters conforming to JavaScript.
	 * Handles complete character set defined in HTML 4.01 recommendation.
	 * 
	 * org.apache.commons.lang.StringEscapeUtils类中实现了此方法
	 * 
	 * @param input
	 *            the input string
	 * @return the escaped string
	 */
	public static String javaScriptEscape(String input) {
		if (input == null) {
			return input;
		}

		StringBuffer filtered = new StringBuffer(input.length());
		char prevChar = '\u0000';
		char c;
		for (int i = 0; i < input.length(); i++) {
			c = input.charAt(i);
			if (c == '"') {
				filtered.append("\\\"");
			} else if (c == '\'') {
				filtered.append("\\'");
			} else if (c == '\\') {
				filtered.append("\\\\");
			} else if (c == '\t') {
				filtered.append("\\t");
			} else if (c == '\n') {
				if (prevChar != '\r') {
					filtered.append("\\n");
				}
			} else if (c == '\r') {
				filtered.append("\\n");
			} else if (c == '\f') {
				filtered.append("\\f");
			} else {
				filtered.append(c);
			}
			prevChar = c;

		}
		return filtered.toString();
	}

	public static boolean contains(List<String> stringList, String key) {
		for (int i = 0; i < stringList.size(); i++) {
			String str = stringList.get(i);
			if (str.equalsIgnoreCase(key))
				return true;
		}
		return false;
	}

	public static boolean contains(String[] arr, String key) {
		for (int i = 0; i < arr.length; i++) {
			String str = arr[i];
			if (str.equalsIgnoreCase(key))
				return true;
		}
		return false;
	}

	/**
	 * 功能: 把strSource中的第1个strFrom替换为strTo
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @return
	 */
	public static String replaceFirst(String strSource, String strFrom, String strTo) {
		String strDest = "";
		int intFromLen = strFrom.length();
		int intPos;

		while ((intPos = strSource.indexOf(strFrom)) != -1) {
			strDest = strDest + strSource.substring(0, intPos);
			strDest = strDest + strTo;
			strSource = strSource.substring(intPos + intFromLen);
			break;
		}
		strDest = strDest + strSource;

		return strDest;
	}

	/**
	 * 功能：判断某字符串中是否包含某个子字符串
	 * 
	 * @param mainStr
	 * @param subStr
	 * @return
	 */
	public static boolean contains(String mainStr, String subStr) {
		if (isEmpty(mainStr))
			return false;
		else if (mainStr.indexOf(subStr) == -1)
			return false;
		else
			return true;
	}

	/**
	 * 解析出url请求的路径，包括页面
	 * 
	 * @param strURL
	 *            url地址
	 * @return url路径
	 */
	public static String getUrlPage(String strURL) {
		String strPage = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}
		return strPage;
	}

	/**
	 * 去掉url中的路径，留下请求参数部分
	 * 
	 * @param strURL
	 *            url地址
	 * @return url请求参数部分
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}

		return strAllParam;
	}

	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> getUrlParameter(String URL) {
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 数字不足位数左补0
	 * 
	 * @param str
	 * @param strLength
	 * @param flag
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength, String flag) {
		int strLen = str.length();
		if (strLen < strLength) {
			while (strLen < strLength) {
				StringBuffer sb = new StringBuffer();
				if (flag.equals("0")) {
					sb.append("0").append(str);// 左补0
				} else {
					sb.append(str).append("0");// 右补0
				}
				str = sb.toString();
				strLen = str.length();
			}
		}
		return str;
	}

	public static String addPlaceholderForString(String str, int strLength, String flag, String placeholder) {
		StringBuffer newString = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			if (flag.equals("0")) {// 从左边开始
				if (i % strLength == 0 && i > 0) {
					newString.append(placeholder);
				}
				newString.append(str.charAt(i));
			} else {// 从右边开始
				if ((str.length() - i) % strLength == 0 && i > 0) {
					newString.append(placeholder);
				}
				newString.append(str.charAt(i));
			}
		}
		return newString.toString();
	}

	
	
	/**
	 * 获取“”字符串
	 * @param str
	 * @return
	 */
	public static String getEmptyStr(String str){
		if (str==null || str.equals("null")) {
			str="";
		}
		return str;
	}
}