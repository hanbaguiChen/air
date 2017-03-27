package com.cnmts.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper;

	public static ObjectMapper getMapper() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		DeserializationConfig deserializationConfig = mapper.getDeserializationConfig();
		// 反序列化时， 忽略不存在的字段
		deserializationConfig.with(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		return mapper;
	}

	public static String toJson(Object obj) {
		String str = null;
		try {
			str = getMapper().writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	public static <T> T toObj(String json, Class<T> clazz) {
		try {
			T obj = getMapper().readValue(json, clazz);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T toObj(String json, TypeReference<T> reference) {
		try {
			T obj = getMapper().readValue(json, reference);
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 得到格式化json数据 退格用\t 换行用\r
	 */
	public static String format(String jsonStr) {
		int level = 0;
		StringBuffer jsonForMatStr = new StringBuffer();
		for (int i = 0; i < jsonStr.length(); i++) {
			char c = jsonStr.charAt(i);
			if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
				jsonForMatStr.append(getLevelStr(level));
			}
			switch (c) {
			case '{':
			case '[':
				jsonForMatStr.append(c + "\n");
				level++;
				break;
			case ',':
				jsonForMatStr.append(c + "\n");
				break;
			case '}':
			case ']':
				jsonForMatStr.append("\n");
				level--;
				jsonForMatStr.append(getLevelStr(level));
				jsonForMatStr.append(c);
				break;
			default:
				jsonForMatStr.append(c);
				break;
			}
		}

		return jsonForMatStr.toString();

	}

	private static String getLevelStr(int level) {
		StringBuffer levelStr = new StringBuffer();
		for (int levelI = 0; levelI < level; levelI++) {
			levelStr.append("\t");
		}
		return levelStr.toString();
	}

}