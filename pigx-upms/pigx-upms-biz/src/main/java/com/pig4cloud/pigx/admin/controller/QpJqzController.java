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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpJqz;
import com.pig4cloud.pigx.admin.service.QpJqzService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 加气站表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpjqz")
@Api(value = "qpjqz", tags = "加气站表管理")
public class QpJqzController {

	private final QpJqzService qpJqzService;

	/**
	 * 分页查询
	 *
	 * @param page  分页对象
	 * @param qpJqz 加气站表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpJqzPage(Page page, QpJqz qpJqz) {
		PigxUser pigxUser = SecurityUtils.getUser();
		Integer deptId = null;
		if(!"qpadmin".equals(pigxUser.getUsername())){
			deptId = pigxUser.getDeptId();
		}
		qpJqz.setDeptId(deptId);
		qpJqz.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpJqzService.page(page, Wrappers.query(qpJqz)));
	}


	/**
	 * 通过id查询加气站表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpJqzService.getById(id));
	}

	/**
	 * 新增加气站表
	 *
	 * @param qpJqz 加气站表
	 * @return R
	 */
	@ApiOperation(value = "新增加气站表", notes = "新增加气站表")
	@SysLog("新增加气站表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpjqz_add')")
	public R save(@RequestBody QpJqz qpJqz) {
		if (qpJqz.getDeptId() == null) {
			return R.failed("查询参数缺少部门id");
		}
		List<QpJqz> list = qpJqzService.getJqzByDeptId(qpJqz.getDeptId());
		if (CollectionUtils.isNotEmpty(list)) {
			return R.failed("该部门已有所属加气站，不可添加");
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		qpJqz.setCreateat(pigxUser.getUsername());
		qpJqz.setCreatedate(LocalDateTime.now());
		qpJqz.setModifyat(null);
		qpJqz.setDelFlag("0");
		qpJqz.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpJqzService.save(qpJqz));
	}

	/**
	 * 修改加气站表
	 *
	 * @param qpJqz 加气站表
	 * @return R
	 */
	@ApiOperation(value = "修改加气站表", notes = "修改加气站表")
	@SysLog("修改加气站表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpjqz_edit')")
	public R updateById(@RequestBody QpJqz qpJqz) {
		return R.ok(qpJqzService.updateById(qpJqz));
	}

	/**
	 * 通过id删除加气站表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除加气站表", notes = "通过id删除加气站表")
	@SysLog("通过id删除加气站表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpjqz_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(qpJqzService.removeById(id));
	}

	/**
	 * 通过部门id查询加气站信息 <br>
	 *
	 * @param deptId : 部门id
	 * @Return: R
	 * @Author: gmx
	 * @Date: 2020/5/9 10:20
	 */
	@ApiOperation(value = "通过部门id查询加气站信息", notes = "通过部门id查询加气站信息")
	@SysLog("通过部门id查询加气站信息")
	@GetMapping("/getJqzByDeptId/{deptId}")
	public R getJqzByDeptId(@PathVariable Integer deptId) {
		List<QpJqz> list = qpJqzService.getJqzByDeptId(deptId);
		return R.ok(CollectionUtils.isNotEmpty(list) ? list.get(0) : new QpJqz());
	}
}
