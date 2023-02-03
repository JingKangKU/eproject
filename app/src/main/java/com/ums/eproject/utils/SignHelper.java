package com.ums.eproject.utils;



import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;


public class SignHelper {

	/**
	 * 获取加签后的值 <br>
	 * <b>作者： ryan</b> <br>
	 * 创建时间：2023年1月5日 下午4:41:11
	 * 
	 * @since 1.0
	 * @param signKey
	 * @return
	 */
	public static String getSignValue(String jsonStr, String signKey) {

		Map<String, String> valueMap = JSON.parseObject(jsonStr, new TypeReference<Map<String, String>>() {});

		Map<String, String> treeMap = new TreeMap<String, String>();
		
		for (String key : valueMap.keySet()) {
			// 如果参数中包含了非加签字段则去掉
			if ("serialVersionUID".equals(key) || "sign".equals(key)) {
				continue;
			}
			
			// 不处理空值
			if (StrUtil.isEmpty(valueMap.get(key))) {
				continue;
			}
			treeMap.put(key, valueMap.get(key));

		}

		// 参数值组装
		StringBuffer sb = new StringBuffer();
		for (String value : treeMap.values()) {
			sb.append(value);
		}
		sb.append("&key=").append(signKey);

		Log.d("jingk",sb.toString());
		System.out.println(sb.toString());

		// 加密后报文
		return MD5Util.md5(sb.toString()).toLowerCase();

	}

	/**
	 * 按ASCII码给json对象排序（规定：升序）

	 * @return
	 */
	public static String getSignStr(JSONObject jsonObject, String signKey){
		String res = "";
		try {

			ArrayList<String> nameList = new ArrayList<>();
			Iterator keys = jsonObject.keys();
			while(keys.hasNext()) {
				String key = keys.next().toString();
				nameList.add(key);
			}
			//key排序，升序
			Collections.sort(nameList);

			StringBuffer sb = new StringBuffer();
			sb.append("{");
			for (int i = 0; i < nameList.size(); i++) {
				String name = nameList.get(i);
				String value = jsonObject.getString(name);
				if(i != 0)sb.append(",");
				//添加键值对，区分字符串与json对象
				if(value.startsWith("{")||value.startsWith("[")){
					sb.append(String.format("\"%s\":%s",name,value));
				}else{
					sb.append(String.format("\"%s\":\"%s\"",name,value));
				}
			}
			sb.append("}");
			sb.append("&key=").append(signKey);
			res = sb.toString();

		} catch (JSONException e) {
			e.printStackTrace();
			return "";
		}

		return res;

	}


}
