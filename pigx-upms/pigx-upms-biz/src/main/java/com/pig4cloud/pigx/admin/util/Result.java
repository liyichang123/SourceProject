package com.pig4cloud.pigx.admin.util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;
import java.rmi.MarshalledObject;
import java.util.Map;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/16 14:54 <br>
 */
@Data
public class Result implements Serializable {

	private Integer code;

	private Object data;

	private String message;
}
