package com.pig4cloud.pigx.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.admin.util.Result;
import com.pig4cloud.pigx.admin.util.kfwt.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.Test;

import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/08 16:35 <br>
 */
@Slf4j
public class ThisTest {

	@Test
	public void test() {
		Map<String, Object> jsonObj = new HashMap<>();
		/*jsonObj.put("gasId", "1303966055305707522");
		jsonObj.put("czTime","2020-09-10 16:30:24");
		jsonObj.put("personId", "test");
		jsonObj.put("personName","test");
		jsonObj.put("deviceId", "1");
		jsonObj.put("gunId","1");
		jsonObj.put("czl","23.0");
		jsonObj.put("MEDIA", "氮气");*/
		jsonObj.put("Sydw", "1bf0166d49714314ac25599b0a35727c");
		jsonObj.put("FACTORY_NUMBER", "893207");
		String nameSpace = "http://itelian.cn";
		//String methodName = "getGasInfoJsonList";
		String methodName = "getGasJson";
		/*String methodName = "getAddGasRecord";//充装记录上传
		String sing = SignUtil.getSingn(jsonObj);
		System.out.println("sign : ------: " + sing);
		jsonObj.put("Sign", sing);*/
		System.out.println("all request : " + JSON.toJSONString(jsonObj));
		Object[] service = getService(JSON.toJSONString(jsonObj), nameSpace, methodName);
		assert service != null;
		System.out.println(service[0].toString());
		Result result = JSON.parseObject(service[0].toString(), Result.class);
		JSONObject data = new JSONObject();
		if (result != null) {
			data = JSON.parseObject(result.getData().toString());
		}
		System.out.println("result: data: " + data.toJSONString());
	}

	public static Object[] getService(String parameters, String namespace, String methodName) {
		try {
			String newUrl = "http://qp.kf96333.com/ws/interface?wsdl";
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(newUrl);
			QName name = new QName(namespace, methodName);
			Object[] params = new Object[]{parameters};
			return client.invoke(name, params);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

}
