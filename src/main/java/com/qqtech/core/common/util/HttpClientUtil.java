package com.qqtech.core.common.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qqtech.core.common.constant.CoreConst;

public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static String send(String url, Properties props) {
		return HttpClientUtil.send(url, "GET", null, props);
	}

	public static String send(String url, String method, String requestBody, Properties headerProps) {
		if (StringUtil.isBlank(url)) {
			return null;
		}
		if (StringUtil.isBlank(method)) {
			method = "GET";
		}
		HttpURLConnection httpConn = null;
		try {
			URL postUrl = new URL(url);
			httpConn = (HttpURLConnection) postUrl.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod(method);
			httpConn.setUseCaches(false);
			httpConn.setInstanceFollowRedirects(true);
			httpConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded; charset=" + CoreConst.DEFAULT_CODETYPE);
			if (headerProps != null && headerProps.size() > 0) {
				Enumeration<Object> en = headerProps.keys();
				while (en.hasMoreElements()) {
					String key = en.nextElement().toString();
					String value = headerProps.getProperty(key);
					httpConn.setRequestProperty(key, value);
				}
			}
			httpConn.setConnectTimeout(1000 * 300);
			httpConn.setReadTimeout(1000 * 300);
			httpConn.connect();

			if (StringUtil.isNotBlank(requestBody)) {
				DataOutputStream out = new DataOutputStream(httpConn.getOutputStream());
				out.write(requestBody.getBytes(CoreConst.DEFAULT_CODETYPE));
				out.flush();
				out.close();
			}
			int status = httpConn.getResponseCode();
			if (status != HttpURLConnection.HTTP_OK) {
				logger.error("发送HTTP请求失败，状态码：[" + status + "] 返回信息：" + httpConn.getResponseMessage());
				return null;
			}
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(httpConn.getInputStream(), CoreConst.DEFAULT_CODETYPE));
			StringBuffer responseSb = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				responseSb.append(line.trim());
			}
			reader.close();
			return responseSb.toString().trim();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("发送请求异常:" + ex.getMessage());
			return null;
		} finally {
			if (httpConn != null) {
				httpConn.disconnect();
			}
		}
	}
}
