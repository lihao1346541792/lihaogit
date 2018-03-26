package com.qqtech.core.common.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {
	private static final Logger LOG = LoggerFactory.getLogger(JsonUtil.class);

	private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * JSON串转换为Java泛型对象
	 *
	 * @param <T>
	 * @param jsonString
	 *            JSON字符串
	 * @param tr
	 *            TypeReference,例如: new TypeReference< List<FamousUser> >(){}
	 * @return List对象列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> T json2GenericObject(String jsonString, TypeReference<T> tr) {
		if (StringUtil.isNotBlank(jsonString)) {
			try {
				return (T) OBJECT_MAPPER.readValue(jsonString, tr);
			} catch (Exception e) {
				LOG.error("json error:" + e.getMessage());
			}
		}
		return null;
	}

	/**
	 * Java对象转Json字符串
	 *
	 * @param object
	 *            Java对象，可以是对象，数组，List,Map等
	 * @return json 字符串
	 */
	public static String toJson(Object object) {
		String jsonString = "";
		try {
			if (object != null) {
				jsonString = OBJECT_MAPPER.writeValueAsString(object);
			}
		} catch (Exception e) {
			LOG.error("json error:" + e.getMessage());
		}
		return jsonString;

	}

	/**
	 * Json字符串转Java对象
	 *
	 * @param jsonString
	 * @param c
	 * @return
	 */
	public static Object toObj(String jsonString, Class<?> c) {
		if (StringUtil.isNotBlank(jsonString)) {
			try {
				return OBJECT_MAPPER.readValue(jsonString, c);
			} catch (Exception e) {
				LOG.error("json error:" + e.getMessage());
			}
		}
		return null;
	}
}
