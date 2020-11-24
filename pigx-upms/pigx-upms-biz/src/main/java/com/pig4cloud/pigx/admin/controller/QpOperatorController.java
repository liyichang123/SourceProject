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
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.constant.enums.OperatorTypeEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpOperator;
import com.pig4cloud.pigx.admin.service.QpOperatorService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_NORMAL;
import static com.pig4cloud.pigx.common.core.constant.SecurityConstants.DEFAULT_PASSWORD;


/**
 * 操作人员管理表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpoperator")
@Api(value = "qpoperator", tags = "操作人员管理表管理")
public class QpOperatorController {

	private final QpOperatorService qpOperatorService;
	private final SysUserService sysUserService;

	/**
	 * 分页查询
	 *
	 * @param page       分页对象
	 * @param qpOperator 操作人员管理表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpOperatorPage(Page page, QpOperator qpOperator) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpOperator.setDeptId(pigxUser.getDeptId());
		qpOperator.setTenantId(TenantContextHolder.getTenantId());
		qpOperator.setDelFlag("0");
		return R.ok(qpOperatorService.page(page, Wrappers.query(qpOperator)));
	}


	/**
	 * 通过id查询操作人员管理表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpOperatorService.getById(id));
	}

	/**
	 * 新增操作人员管理表
	 *
	 * @param qpOperator 操作人员管理表
	 * @return R
	 */
	@ApiOperation(value = "新增操作人员管理表", notes = "新增操作人员管理表")
	@SysLog("新增操作人员管理表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpoperator_add')")
	public R save(@RequestBody QpOperator qpOperator) {
		//先检查电话号是否存已注册，若存在则返回 已存在，不可重复添加
		SysUser user = sysUserService.getUserByPhone(qpOperator.getPhone());
		if (Objects.nonNull(user)) {
			return R.failed("该电话号已注册，不可重复添加");
		}
		//新增，注册
		PigxUser pigxUser = SecurityUtils.getUser();
		qpOperator.setCreateat(pigxUser.getUsername());
		qpOperator.setCreatedate(LocalDateTime.now());
		qpOperator.setModifyat(null);
		qpOperator.setDelFlag("0");
		qpOperator.setDeptId(pigxUser.getDeptId());
		qpOperator.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpOperatorService.saveQpOperator(qpOperator));
	}

	/**
	 * 修改操作人员管理表
	 *
	 * @param qpOperator 操作人员管理表
	 * @return R
	 */
	@ApiOperation(value = "修改操作人员管理表", notes = "修改操作人员管理表")
	@SysLog("修改操作人员管理表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpoperator_edit')")
	public R updateById(@RequestBody QpOperator qpOperator) {
		QpOperator oldRecord = qpOperatorService.getById(qpOperator.getId());
		String phone = qpOperator.getPhone();
		if (!oldRecord.getPhone().equals(phone)) {
			QueryWrapper<QpOperator> queryWrapper = new QueryWrapper<>();
			queryWrapper.eq("phone", phone).eq("del_flag", STATUS_NORMAL);
			try {
				qpOperatorService.getOne(queryWrapper);
			} catch (Exception e) {
				return R.failed("该电话号已被添加为操作员，不可使用该号码");
			}
			SysUser user = sysUserService.getUserByPhone(phone);
			if (Objects.nonNull(user)) {
				return R.failed("该电话号已被添加为操作员，不可使用该号码");
			}
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		qpOperator.setModifyat(pigxUser.getUsername());
		qpOperator.setModifydate(LocalDateTime.now());
		return R.ok(qpOperatorService.updateOperator(qpOperator));
	}

	/**
	 * 通过id删除操作人员管理表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除操作人员管理表", notes = "通过id删除操作人员管理表")
	@SysLog("通过id删除操作人员管理表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpoperator_del')")
	public R removeById(@PathVariable Integer id) {
		QpOperator oldRecord = qpOperatorService.getById(id);
		if (ObjectUtils.isEmpty(oldRecord)){
			return R.failed("所选用户不存在");
		}
		QpOperator upd = new QpOperator();
		upd.setId(id);
		upd.setDelFlag(CommonConstants.STATUS_DEL);
		PigxUser pigxUser = SecurityUtils.getUser();
		upd.setModifyat(pigxUser.getUsername());
		upd.setModifydate(LocalDateTime.now());
		//先将user表用户信息删除
		SysUser user = sysUserService.getUserByPhone(oldRecord.getPhone());
		if (ObjectUtils.isNotEmpty(user)){
			sysUserService.deleteUserById(user);
		}
		return R.ok(qpOperatorService.updateById(upd));
	}

	/**
	 * 通过当前登录人userId查询操作人员信息
	 *
	 * @return R
	 */
	@ApiOperation(value = "通过当前登录用户id获取操作人员信息", notes = "通过当前登录用户id获取操作人员信息")
	@SysLog("通过id删除操作人员管理表")
	@GetMapping("/getQpOperatorByUserId")
	public R getQpOperatorByUserId() {
		Integer userId = SecurityUtils.getUser().getId();
		//查询当前登录操作员的角色，进行业务转换
		Integer role = SecurityUtils.getRoles().get(0);
		List<QpOperator> qpOperators = qpOperatorService.getQpOperatorByUserId(userId, OperatorTypeEnum.getEnumByRoleId(role).getType());
		if (!CollectionUtils.isEmpty(qpOperators)) {
			return R.ok(qpOperators.get(0));
		}
		return R.ok(null, "暂无操作员信息");
	}
}
