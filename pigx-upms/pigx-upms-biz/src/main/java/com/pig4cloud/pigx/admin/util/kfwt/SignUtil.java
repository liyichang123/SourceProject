package com.pig4cloud.pigx.admin.util.kfwt;

import com.alibaba.fastjson.JSON;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/10 10:58 <br>
 */
public class SignUtil {
	/**
	 * @param source 明文
	 * @return 排序明文
	 */
	public static Map<String, Object> sortMapByKey(Map<String, Object> source) {
		if (source == null || source.isEmpty()) {
			return new HashMap<>();
		}
		Map<String, Object> dest = new TreeMap<>(new Comparator<String>() {
			@Override
			public int compare(String key1, String key2) {
				return key1.compareTo(key2);
			}
		});
		dest.putAll(source);
		return dest;
	}

	/**
	 * @param values 明文
	 * @return 密文
	 */
	public static String getSingn(Map<String, Object> values) {
		StringBuilder content = new StringBuilder();
		for (String k : values.keySet()) {
			String value = "";
			if (values.get(k) != null) {
				value = values.get(k).toString();
			}

			if (value != null && !"".equals(value) && !"sign".equals(k)) {
				content.append(k).append("=").append(values.get(k)).append("&");
			}
		}
		//秘钥C9882B4574F610E1074FEE9011B9F387
		content.append("key=").append("C9882B4574F610E1074FEE9011B9F387");
		System.out.println("sorts obj: " + JSON.toJSONString(content.toString()));
		return new StringBuffer(hash("MD5", content.toString()).toUpperCase()).reverse().toString();
		//return new StringBuffer(HashKit.md5(content.toString()).toUpperCase()).reverse().toString();
	}

	public static String hash(String algorithm, String srcStr) {
		try {
			StringBuilder result = new StringBuilder();
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] bytes = md.digest(srcStr.getBytes(StandardCharsets.UTF_8));
			for (byte b : bytes) {
				String hex = Integer.toHexString(b & 0xFF);
				if (hex.length() == 1)
					result.append("0");
				result.append(hex);
			}
			return result.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
