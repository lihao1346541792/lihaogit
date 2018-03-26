package com.qqtech.core.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qqtech.core.common.constant.CoreConst;


public class CookieUtil {

	/**
	 * 设置cookie，且永久有效
	 *
	 * @param response
	 * @param key
	 *            cookie名字
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletResponse response, String key, String value) {
		CookieUtil.addCookie(response, key, value, true);
	}

	/**
	 * 设置cookie，且永久有效
	 *
	 * @param response
	 * @param key
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param httpOnly
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, boolean httpOnly) {
		CookieUtil.addCookie(response, key, value, 0, httpOnly);
	}

	/**
	 * 设置cookie
	 *
	 * @param response
	 * @param key
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, int maxAge) {
		addCookie(response, key, value, maxAge, true);
	}

	/**
	 * 设置cookie
	 *
	 * @param response
	 * @param key
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期 以秒为单位
	 * @param httpOnly
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, int maxAge, boolean httpOnly) {
		try {
			if (StringUtil.isNotBlank(key)) {
				key = URLEncoder.encode(key, CoreConst.DEFAULT_CODETYPE);
			}
			if (StringUtil.isNotBlank(value)) {
				value = URLEncoder.encode(value, CoreConst.DEFAULT_CODETYPE);
			}
			Cookie cookie = new Cookie(key, value);
			cookie.setPath("/");
			if (maxAge != 0) {
				cookie.setMaxAge(maxAge);
			}
			cookie.setHttpOnly(httpOnly);
			response.addCookie(cookie);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除cookie
	 * 
	 * @param cookieKey
	 * @param request
	 * @param response
	 */
	public static void delCookie(String cookieKey, HttpServletRequest request, HttpServletResponse response) {
		addCookie(response, cookieKey, null);
	}

	/**
	 * 获取cookie的值
	 * 
	 * @param key
	 * @param request
	 * @return
	 */
	public static String getCookieValue(String key, HttpServletRequest request) {
		String rt = null;
		try {
			if (StringUtil.isNotBlank(key)) {
				key = URLEncoder.encode(key, CoreConst.DEFAULT_CODETYPE);
			}
			Cookie cookie = CookieUtil.getCookie(request, key);
			if (cookie != null) {
				rt = cookie.getValue();
			}
			if (StringUtil.isNotBlank(rt)) {
				rt = URLDecoder.decode(rt, CoreConst.DEFAULT_CODETYPE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rt;
	}

	private static Cookie getCookie(HttpServletRequest request, String key) {
		Map<String, Cookie> cookieMap = getCookieMap(request);
		if (cookieMap.containsKey(key)) {
			Cookie cookie = (Cookie) cookieMap.get(key);
			return cookie;
		} else {
			return null;
		}
	}

	private static Map<String, Cookie> getCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (Cookie cookie : cookies) {
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
