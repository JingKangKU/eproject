package com.ums.eproject.utils;



import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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

	public static String getSignValue4Bean(Object obj, String signKey) {
		StringBuilder sb = new StringBuilder();
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			Map<String, String> map = new HashMap<String, String>();
			for (Field f : fields) {
				String propertyName = f.getName();
				Log.d("chendong", "the propertyName is " + propertyName);
				if ("serialVersionUID".equals(propertyName)) {
					continue;
				}
				String propertyValue = getFieldValue(f, obj);
				if (propertyValue == null || "".equals(propertyValue)) {
//						Log.w(TAG, "propertyName:"+propertyName+" value is null!");
					continue;
				}
				map.put(propertyName, propertyValue);
			}
			Field[] superFields = obj.getClass().getSuperclass().getDeclaredFields();
			for (Field f : superFields) {
				String propertyName = f.getName();
				Log.d("chendong", "the propertyName is " + propertyName);
				if ("serialVersionUID".equals(propertyName)) {
					continue;
				}
				String propertyValue = getFieldValue(f, obj);
				if (propertyValue == null || "".equals(propertyValue)) {
//						Log.w(TAG, "propertyName:"+propertyName+" value is null!");
					continue;
				}
				map.put(propertyName, propertyValue);
			}
			List<String> keys = new ArrayList<String>(map.keySet());
			//字段排序
			Collections.sort(keys);

			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				String value = map.get(key);
				sb.append(value);
//                if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
//                    sb.append(key).append("=").append(value);
//                } else {
//                    sb.append(key).append("=").append(value).append("&");
//                }

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sb.append("&key=").append(signKey);
		Log.d("chendong", "the signdata is " + sb.toString());
		return MD5Util.md5(sb.toString()).toLowerCase();
	}

	//获取字段值
	private static String getFieldValue(Field f, Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class clazz = f.getType();
		f.setAccessible(true);
		if (clazz == String.class) {
			return (String) f.get(obj);
		}
		if (clazz == Long.class) {
			try {
				return String.valueOf(f.getLong(obj));
			}catch (Exception e){
				return null;
			}
		} else if (clazz == int.class) {
			return String.valueOf(f.getInt(obj));
		} else if (clazz == List.class) {
			//todo 针对集合类型的数据进行处理
//            StringBuilder sb = new StringBuilder();
//            List<Object> lists = (List<Object>) f.get(obj);
//            if (null != lists && lists.size() > 0) {
//                sb.append("[");
//                if (lists != null) {
//                    for (int i = 0; i < lists.size(); i++) {
//                        Object o = lists.get(i);
//                        sb.append("{" + getSignData(o) + "}");
//                        if (i != lists.size() - 1) {
//                            sb.append("&");
//                        }
//                    }
//                }
//                sb.append("]");
//            }
			return "list";
		} else {
			//todo 针对对象类型的数据进行处理
//            Object o = f.get(obj);
//            System.out.println(o);
			return "object";
		}
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
