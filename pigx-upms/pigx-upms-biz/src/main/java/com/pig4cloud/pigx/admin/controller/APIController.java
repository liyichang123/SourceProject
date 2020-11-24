package com.pig4cloud.pigx.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.admin.service.SysFileService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

@Inner(value = false)
@RestController
@AllArgsConstructor
@RequestMapping("/api")
@Api(value = "api", tags = "API管理")
public class APIController {

	private final SysFileService sysFileService;


	/**
	 * idis获取订单
	 *
	 * @param        httpRequest
	 * @return
	 */
	@ApiOperation(value = "idis获取订单", notes = "idis获取订单")
	@GetMapping("/getOrderInfo")
	public R getOrderInfo(HttpServletRequest httpRequest) {
		JSONObject jsonObject = new JSONObject();

		return R.ok(jsonObject);
	}


	/**
	 * 生成二维码
	 *
	 * @param
	 * @return
	 */
	@ApiOperation(value = "生成二维码", notes = "生成二维码")
	@GetMapping("/genQrCode")
	public R genQrCode(HttpServletRequest httpRequest) {
		JSONObject jsonObject=new JSONObject();


		return R.ok(jsonObject);
	}

	/**
	 * 根据版本号获取 apk信息（不传版本号，则获取apkList）
	 *
	 * @Return: com.pig4cloud.pigx.common.core.util.R
	 * @Author: gmx
	 * @Date: 2020/5/11 14:40
	 */
	@ApiOperation(value = "根据版本号查询Apk信息", notes = "根据版本号查询Apk信息（不传版本号，则获取apkList）")
	@GetMapping("/getApkList")
	public R getApkList(@RequestParam(value = "version", required = false) String version){
		return sysFileService.getApkList(version);
	}
}
