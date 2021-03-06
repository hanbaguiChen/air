package com.cnmts.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 获取cookie值
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午3:33:11
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookieValueByKey(String key, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(key)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * 设置cookie
	 * 
	 * @author 王璞
	 * @date 2017年1月7日 下午3:39:49
	 * @param key
	 * @param value
	 * @param maxAgeSeconds
	 * @param response
	 * @return
	 */
	public static void setCookie(String key, String value, int maxAgeSeconds, HttpServletResponse response) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(maxAgeSeconds);
		cookie.setPath("/AirBrake/");
		cookie.setHttpOnly(true);
		response.addCookie(cookie);
	}
}
