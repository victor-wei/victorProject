package com.wh.victorwei.test;

import com.alibaba.fastjson.JSONObject;

public class TestJson {

	public static void main(String[] args) {
		JSONObject jsonObj = new JSONObject();
		String[] array = new String[]{"李四","张三"};
		jsonObj.put("array", array);
		System.out.println(jsonObj);
	}
}
