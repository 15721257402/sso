/**
 * 
 */
package com.tritonsfs.cac.sso.util;

/**
 * @author chenshunyu
 *
 */
public class PermissionUtil {

	/** 
	* Description:权限正则比对 （pattern带* ，str不带*）
	* @author chenshunyu  
	* @date 2018年4月10日  
	* @version 1.0
	 */
	public static boolean wildMatch(String pattern, String str) {
//		pattern = toJavaPattern(pattern);
		return java.util.regex.Pattern.matches(pattern, str);
	}

	private static String toJavaPattern(String pattern) {
		String result = "";
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			boolean isMeta = false;
			if (!isMeta) {
				if (ch == '*') {
					result += ".*";
				} else {
					result += ch;
				}
			}
		}
		return result;
	}
	private static void test(String pattern, String str) {
		System.out.println(pattern+" " + str + " =>> " + wildMatch(pattern, str));
	}

	public static void main(String[] args) {
		System.out.println(wildMatch("/index","/index"));
	}

}
