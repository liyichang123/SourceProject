/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.pig4cloud.pigx.admin.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpStatistics;
import com.pig4cloud.pigx.admin.service.QpStatisticsService;
import com.pig4cloud.pigx.admin.service.SysDeptRelationService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 数据统计
 *
 * @author fll
 * @date 2020-04-15 00:00:00
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpstatistics")
@Api(value = "qpstatistics", tags = "数据统计管理")
public class QpStatisticsController {

	private final QpStatisticsService qpStatisticsService;

	@ApiOperation(value = "查询气瓶数量统计", notes = "查询气瓶数量统计")
		@GetMapping("/getQpGascylinderStatistics")
	public R getQpGascylinderStatistics(QpStatistics qpStatistics) {
		Map<String, Object> map = new HashMap<>();
		qpStatistics.setTenantId(TenantContextHolder.getTenantId());
		List<Integer> depts = new ArrayList<>();
		if (qpStatistics.getDeptId() == null) {
			depts = qpStatisticsService.getDeptsByDeptId(SecurityUtils.getUser().getDeptId());
		} else {
			depts = qpStatisticsService.getDeptsByDeptId(qpStatistics.getDeptId());
		}
		int totalNumber = qpStatisticsService.getQpGascylinderNumberTotal(qpStatistics, depts);
		map.put("totalNumber", totalNumber);
		List<Map<String, Object>> dataList = new ArrayList<>();
		if (qpStatistics.getAnalyzeType().trim().equals("1")) {
			dataList = qpStatisticsService.getQpGascylinderNumberM(qpStatistics, depts);
		} else if (qpStatistics.getAnalyzeType().trim().equals("2")) {
			dataList = qpStatisticsService.getQpGascylinderNumberY(qpStatistics, depts);
		}
		List<String> xList = new ArrayList<>();
		List<Integer> yList = new ArrayList<>();
		if (dataList.size() != 0) {
			dataList.forEach(e -> {
				BigDecimal percentage = new BigDecimal(e.get("number").toString())
						.divide(new BigDecimal(totalNumber), 4, BigDecimal.ROUND_HALF_UP);
				xList.add(e.get("date").toString() + " " + percentage.multiply(new BigDecimal(100)).setScale(2) + "%");
				yList.add(Integer.parseInt(e.get("number").toString()));
			});
		}
		map.put("xList", xList);
		map.put("yList", yList);
		return R.ok(map);
	}

	@ApiOperation(value = "查询气瓶类型统计", notes = "查询气瓶类型统计")
	@GetMapping("/getQpGascylinderTypeStatistics")
	public R getQpGascylinderTypeStatistics(QpStatistics qpStatistics) {
		Map<String, Object> map = new HashMap<>();
		qpStatistics.setTenantId(TenantContextHolder.getTenantId());
		List<Integer> depts = new ArrayList<>();
		if (qpStatistics.getDeptId() == null) {
			depts = qpStatisticsService.getDeptsByDeptId(SecurityUtils.getUser().getDeptId());
		} else {
			depts = qpStatisticsService.getDeptsByDeptId(qpStatistics.getDeptId());
		}
		List<Map<String, Object>> sList = qpStatisticsService.getQpGascylinderTypeStatistics(qpStatistics, depts);
		List<String> lList = new ArrayList<>();
		if (sList.size() != 0) {
			sList.forEach(e -> {
				lList.add(e.get("name").toString());
			});
		}
		map.put("lList", lList);
		map.put("sList", sList);
		return R.ok(map);
	}

	/**
	 * 气瓶预警分页查询
	 *
	 * @param page         分页对象
	 * @param qpStatistics
	 * @return
	 */
	@ApiOperation(value = "气瓶预警分页查询", notes = "气瓶预警分页查询")
	@GetMapping("/getQpGascylinderWarningPage")
	public R getQpGascylinderWarningPage(Page page, QpStatistics qpStatistics) {
		qpStatistics.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpStatisticsService.getQpGascylinderWarningPage(page, qpStatistics));
	}

	/**
	 * 查询气瓶数量分析
	 *
	 * @param qpStatistics
	 * @return
	 */
	@ApiOperation(value = "查询气瓶数量分析", notes = "查询气瓶数量分析")
	@GetMapping("/getQpGascylinderAnalyze")
	public R getQpGascylinderAnalyze(QpStatistics qpStatistics) {
		Map<String, Object> map = new HashMap<>();
		qpStatistics.setTenantId(TenantContextHolder.getTenantId());
		List<Integer> depts;
		if (qpStatistics.getDeptId() == null) {
			depts = qpStatisticsService.getDeptsByDeptId(SecurityUtils.getUser().getDeptId());
		} else {
			depts = qpStatisticsService.getDeptsByDeptId(qpStatistics.getDeptId());
		}
		int totalNumber = qpStatisticsService.getQpGascylinderAnalyzeTotal(qpStatistics, depts);
		map.put("totalNumber", totalNumber);
		List<Map<String, Object>> sList = null;
		if (qpStatistics.getAnalyzeType().trim().equals("1")) {
			sList = qpStatisticsService.getQpGascylinderAnalyzeQPLX(qpStatistics, depts);
		} else if (qpStatistics.getAnalyzeType().trim().equals("2")) {
			sList = qpStatisticsService.getQpGascylinderAnalyzeCZJZ(qpStatistics, depts);
		}else if (qpStatistics.getAnalyzeType().trim().equals("3")){
			sList = qpStatisticsService.getQpGascylinderAnalyzeQPZT(qpStatistics, depts);
		}else if (qpStatistics.getAnalyzeType().trim().equals("4")){
			//inOutType 0 出库   1 入库
			int outStoreCount = qpStatisticsService.getQpInOutStoreCount(0, depts, TenantContextHolder.getTenantId());
			Map<String, Object> inStore = new HashMap<>();
			inStore.put("name","在库");
			inStore.put("value",totalNumber - outStoreCount);
			Map<String, Object> outStore = new HashMap<>();
			outStore.put("name","不在库");
			outStore.put("value",outStoreCount);
			sList = new ArrayList<>();
			sList.add(inStore);
			sList.add(outStore);
		}
		System.out.println(JSON.toJSONString(sList));
		sList = sList == null ? new ArrayList<>() : sList;
		List<String> lList = new ArrayList<>();
		if (sList.size() != 0) {
			sList.forEach(e -> {
				lList.add(e.get("name").toString());
			});
		}
		map.put("lList", lList);
		map.put("sList", sList);
		return R.ok(map);
	}

}
