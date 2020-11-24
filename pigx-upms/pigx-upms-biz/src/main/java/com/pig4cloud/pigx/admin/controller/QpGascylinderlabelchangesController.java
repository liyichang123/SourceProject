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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpGascylinderlabelchanges;
import com.pig4cloud.pigx.admin.service.QpGascylinderlabelchangesService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


/**
 * 气瓶标签变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpgascylinderlabelchanges")
@Api(value = "qpgascylinderlabelchanges", tags = "气瓶标签变更记录表管理")
public class QpGascylinderlabelchangesController {

	private final QpGascylinderlabelchangesService qpGascylinderlabelchangesService;

	/**
	 * 分页查询
	 *
	 * @param page                      分页对象
	 * @param qpGascylinderlabelchanges 气瓶标签变更记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpGascylinderlabelchangesPage(Page page, QpGascylinderlabelchanges qpGascylinderlabelchanges) {
		return R.ok(qpGascylinderlabelchangesService.page(page, Wrappers.query(qpGascylinderlabelchanges)));
	}


	/**
	 * 通过id查询气瓶标签变更记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpGascylinderlabelchangesService.getById(id));
	}

	/**
	 * 通过电子标签气瓶档案记录
	 *
	 * @param electroniclabel electroniclabel
	 * @return R
	 */
	@ApiOperation(value = "通过电子标签气瓶档案记录", notes = "通过电子标签气瓶档案记录")
	@GetMapping("/getQpgascylinderrecord/{electroniclabel}")
	public R getQpgascylinderrecord(@PathVariable("electroniclabel") String electroniclabel, @RequestParam(value = "codeType", required = false) String codeType) {
		if (StringUtils.isBlank(electroniclabel)){
			return R.ok(null, "缺少请求参数");
		}
		Map<String, Object> qpgascylinderrecord = qpGascylinderlabelchangesService.getQpgascylinderrecord(electroniclabel, codeType);
		if (CollectionUtils.isNotEmpty(qpgascylinderrecord)) {
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.SIMPLIFIED_CHINESE);
			qpgascylinderrecord.put("sysTime", now.format(dateTimeFormatter));
		}
		return R.ok(qpgascylinderrecord);
	}

	/**
	 * 新增气瓶标签变更记录表
	 *
	 * @param qpGascylinderlabelchanges 气瓶标签变更记录表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶标签变更记录表", notes = "新增气瓶标签变更记录表")
	@SysLog("新增气瓶标签变更记录表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpgascylinderlabelchanges_add')")
	public R save(@RequestBody QpGascylinderlabelchanges qpGascylinderlabelchanges) {
		return R.ok(qpGascylinderlabelchangesService.save(qpGascylinderlabelchanges));
	}

	/**
	 * 修改气瓶标签变更记录表
	 *
	 * @param qpGascylinderlabelchanges 气瓶标签变更记录表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶标签变更记录表", notes = "修改气瓶标签变更记录表")
	@SysLog("修改气瓶标签变更记录表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpgascylinderlabelchanges_edit')")
	public R updateById(@RequestBody QpGascylinderlabelchanges qpGascylinderlabelchanges) {
		return R.ok(qpGascylinderlabelchangesService.updateById(qpGascylinderlabelchanges));
	}

	/**
	 * 通过id删除气瓶标签变更记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶标签变更记录表", notes = "通过id删除气瓶标签变更记录表")
	@SysLog("通过id删除气瓶标签变更记录表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpgascylinderlabelchanges_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(qpGascylinderlabelchangesService.removeById(id));
	}

}
