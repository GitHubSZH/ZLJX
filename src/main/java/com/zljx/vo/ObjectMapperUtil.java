package com.zljx.vo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static String  toJSON(Object object) {
		
		String json = null;
		try {
			json = mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return json;
	}
	@SuppressWarnings("unchecked")
	public static <T> T  toObejct(String json,Class<T> targetClass) {
		
		T t = null;
		try {
			t = mapper.readValue(json, targetClass);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return t;
	}
	
}
