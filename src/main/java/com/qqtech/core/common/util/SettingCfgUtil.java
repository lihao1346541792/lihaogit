package com.qqtech.core.common.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qqtech.core.common.constant.CoreConst;

/**
 * 加载全局配置属性文件
 * 
 * @author andy
 * 
 */
public class SettingCfgUtil {
	public static final Logger logger = LoggerFactory.getLogger(SettingCfgUtil.class);
	private static SettingCfgUtil conf;
	private Properties props = null;

	/**
	 * 单例
	 */
	private SettingCfgUtil(String settingFile) {
		try {
			props = new Properties();
			// 配置文件如："/setting.properties"
			InputStream reader = getClass().getClassLoader().getResourceAsStream(settingFile);
			InputStreamReader in = new InputStreamReader(reader, CoreConst.DEFAULT_CODETYPE);
			props.load(in);
		} catch (Exception e) {
			logger.error("加载属性文件失败", e);
		}
	}

	/**
	 * 获取加载对象
	 * 
	 * @return
	 */
	public static SettingCfgUtil getConfigHelper(String settingFile) {
		if (conf == null) {
			conf = new SettingCfgUtil(settingFile);
		}
		return conf;
	}

	/**
	 * 获取值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return props.getProperty(key);
	}

	/**
	 * 获取整型值
	 * 
	 * @param key
	 * @return
	 */
	public Integer getIntValue(String key) {
		return new Integer(this.getValue(key));
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key
	 * @param defaultVal
	 * @return
	 */
	public String getValue(String key, String defaultVal) {
		String val = props.getProperty(key);
		if (null == val)
			return defaultVal;
		return val;
	}

}
