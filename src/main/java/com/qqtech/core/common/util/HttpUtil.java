package com.qqtech.core.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.qqtech.core.common.constant.CoreConst;

public class HttpUtil {
	private static Scanner scanner;

	public static JSONObject getRequestHeader(HttpServletRequest request) throws JSONException {
		JSONObject obj = new JSONObject();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			obj.put(headerName, request.getHeader(headerName));
		}
		return obj;
	}

	public static JSONObject getResponseHeader(HttpServletResponse response) throws JSONException {
		JSONObject obj = new JSONObject();
		Iterator<String> headerNames = response.getHeaderNames().iterator();
		while (headerNames.hasNext()) {
			String headerName = (String) headerNames.next();
			obj.put(headerName, response.getHeader(headerName));
		}
		return obj;
	}

	public static JSONObject getRequestParameters(HttpServletRequest request) throws JSONException {
		JSONObject obj = new JSONObject();
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			obj.put(paramName, request.getParameter(paramName));
		}
		return obj;
	}

	public static String getRequestBody(HttpServletRequest request) {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			Scanner s = null;
			try {
				scanner = new Scanner(request.getInputStream(), CoreConst.DEFAULT_CODETYPE);
				s = scanner.useDelimiter("\\A");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return s.hasNext() ? s.next() : "";
		}
		return "";
	}

	/**
	 * 获取请求流中的数据
	 * 
	 * @param request
	 * @return
	 */
	public static String getBodyData(ServletRequest request) {
		StringBuilder sb = new StringBuilder();
		InputStream inputStream = null;
		BufferedReader reader = null;
		try {
			inputStream = request.getInputStream();
			reader = new BufferedReader(
					new InputStreamReader(inputStream, Charset.forName(CoreConst.DEFAULT_CODETYPE)));
			String line = "";
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}
}
