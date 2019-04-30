package com.loujie.www.dwz.service.impl;

import com.loujie.www.util.json.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PojoToObjectServiceImpl {

	public String toString(Object pojo) {
		if (pojo instanceof String) {
			return (String) pojo;
		}
		return JsonUtils.toJson(pojo);
	}

	public <T> T parseObject(String pojoStr, Class<T> cla) {
		return JsonUtils.parseObject(pojoStr, cla);
	}

	public Map<String, Object> parseMap(String pojoStr) {
		return JsonUtils.parseMap(pojoStr);
	}

	public <T> List<T> parseList(String pojoStr, Class<T> cla) {
		return JsonUtils.parseList(pojoStr, cla);
	}

}
