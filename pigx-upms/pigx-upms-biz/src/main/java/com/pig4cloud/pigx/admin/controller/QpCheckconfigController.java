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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.service.QpMaterialService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpCheckconfig;
import com.pig4cloud.pigx.admin.service.QpCheckconfigService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * 气瓶检验项配置表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpcheckconfig")
@Api(value = "qpcheckconfig", tags = "气瓶检验项配置表管理")
@Slf4j
public class QpCheckconfigController {

	private final QpCheckconfigService qpCheckconfigService;

	private final QpMaterialService QpMaterialService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param qpCheckconfig 气瓶检验项配置表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpCheckconfigPage(Page page, QpCheckconfig qpCheckconfig) {
		PigxUser pigxUser = SecurityUtils.getUser();
		if (pigxUser.getDeptId() == 10){
			qpCheckconfig.setDeptId(pigxUser.getDeptId());
		}
		qpCheckconfig.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpCheckconfigService.getQpCheckconfigPage(page, qpCheckconfig));
	}

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list")
	public R getQpCheckconfigList() {
		QpCheckconfig qpCheckconfig = new QpCheckconfig();
		qpCheckconfig.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpCheckconfigService.getQpCheckconfigList(qpCheckconfig));
	}

	/**
	 * 通过id查询气瓶检验项配置表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpCheckconfigService.getById(id));
	}

	/**
	 * 通过气瓶充装介质id和检查类型查询气瓶检验项配置表
	 *
	 * @param qpmaterialId qpmaterialId
	 * @return R
	 */
	@ApiOperation(value = "通过气瓶充装介质id和检查类型查询气瓶检验项配置表", notes = "通过气瓶充装介质id和检查类型查询气瓶检验项配置表")
	@GetMapping("/getQpCheckconfigMapById/{qpmaterialId}/{checktype}")
	public R getQpCheckconfigMapById(@PathVariable("qpmaterialId") Integer qpmaterialId, @PathVariable("checktype") Integer checktype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpCheckconfig qpCheckconfig = new QpCheckconfig();
		qpCheckconfig.setDeptId(pigxUser.getDeptId());
		qpCheckconfig.setTenantId(TenantContextHolder.getTenantId());
		qpCheckconfig.setChecktype(checktype);
		qpCheckconfig.setMaterialId(qpmaterialId);
		return R.ok(qpCheckconfigService.getQpCheckconfigMapById(qpCheckconfig));
	}

	/**
	 * 新增气瓶检验项配置表
	 *
	 * @param qpCheckconfig 气瓶检验项配置表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶检验项配置表", notes = "新增气瓶检验项配置表")
	@SysLog("新增气瓶检验项配置表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpcheckconfig_add')")
	public R save(@RequestBody QpCheckconfig qpCheckconfig) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpCheckconfig> queryWrapper = new QueryWrapper<QpCheckconfig>();
		queryWrapper.eq("material_id", qpCheckconfig.getMaterialId());
		queryWrapper.eq("checktype", qpCheckconfig.getChecktype());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpCheckconfig qpcheckconfig = qpCheckconfigService.getOne(queryWrapper);
		if (qpcheckconfig == null) {
			String materialName = QpMaterialService.getById(qpCheckconfig.getMaterialId()).getName();
			String checktype = "";
			if (qpCheckconfig.getChecktype() == 1) {
				checktype = "充装前检查";
			} else if (qpCheckconfig.getChecktype() == 2) {
				checktype = "充装后检查";
			}
			qpCheckconfig.setCheckname(materialName + "的" + checktype + "的检查配置");
			qpCheckconfig.setCreateat(pigxUser.getUsername());
			qpCheckconfig.setCreatedate(LocalDateTime.now());
			qpCheckconfig.setModifyat(null);
			qpCheckconfig.setDelFlag("0");
			qpCheckconfig.setDeptId(pigxUser.getDeptId());
			qpCheckconfig.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpCheckconfigService.save(qpCheckconfig));
		} else {
			return R.failed().setMsg("检查配置有重复");
		}
	}

	/**
	 * 修改气瓶检验项配置表
	 *
	 * @param qpCheckconfig 气瓶检验项配置表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶检验项配置表", notes = "修改气瓶检验项配置表")
	@SysLog("修改气瓶检验项配置表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpcheckconfig_edit')")
	public R updateById(@RequestBody QpCheckconfig qpCheckconfig) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpCheckconfig> queryWrapper = new QueryWrapper<QpCheckconfig>();
		queryWrapper.eq("material_id", qpCheckconfig.getMaterialId());
		queryWrapper.eq("checktype", qpCheckconfig.getChecktype());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpCheckconfig qpcheckconfig = qpCheckconfigService.getOne(queryWrapper);
		if (qpcheckconfig == null || (qpcheckconfig != null && qpcheckconfig.getId().equals(qpCheckconfig.getId()))) {
			QpCheckconfig qpcheckconfig2 = qpCheckconfigService.getById(qpCheckconfig.getId());
			qpcheckconfig2.setDelFlag("1");
			qpCheckconfigService.updateById(qpcheckconfig2);
			qpCheckconfig.setCreateat(qpcheckconfig2.getCreateat());
			qpCheckconfig.setCreatedate(qpcheckconfig2.getCreatedate());
			qpCheckconfig.setModifyat(pigxUser.getUsername());
			qpCheckconfig.setModifydate(LocalDateTime.now());
			qpCheckconfig.setDelFlag("0");
			qpCheckconfig.setDeptId(pigxUser.getDeptId());
			qpCheckconfig.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpCheckconfigService.save(qpCheckconfig));
		} else {
			return R.failed().setMsg("检验配置有重复");
		}
	}

	/**
	 * 通过id删除气瓶检验项配置表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶检验项配置表", notes = "通过id删除气瓶检验项配置表")
	@SysLog("通过id删除气瓶检验项配置表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpcheckconfig_del')")
	public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpCheckconfig> queryWrapper = new QueryWrapper<QpCheckconfig>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpCheckconfig qpcheckconfig = qpCheckconfigService.getOne(queryWrapper);
		if (qpcheckconfig != null) {
			qpcheckconfig.setDelFlag("1");
			return R.ok(qpCheckconfigService.updateById(qpcheckconfig));
		} else {
			return R.ok();
		}
		//return R.ok(qpCheckconfigService.removeById(id));
	}

	@ApiOperation(value = "应用气瓶检验规则模板配置", notes = "应用气瓶检验规则模板配置")
	@GetMapping("/useTemplate")
	public R useTemplate() {
		PigxUser user = SecurityUtils.getUser();
		QueryWrapper<QpCheckconfig> tmpWrapper = new QueryWrapper<>();
		tmpWrapper.eq("del_flag", "0");
		tmpWrapper.eq("dept_id", 10);
		tmpWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		List<QpCheckconfig> tmpList = qpCheckconfigService.list(tmpWrapper);
		QueryWrapper<QpCheckconfig> deptWrapper = new QueryWrapper<>();
		deptWrapper.eq("del_flag", "0");
		deptWrapper.eq("dept_id", user.getDeptId());
		deptWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		try {
			List<QpCheckconfig> deptlist = qpCheckconfigService.list(deptWrapper);
			tmpList.stream().filter(a -> isHave(a, deptlist))
					.map(a -> {
						a.setId(null);
						a.setDeptId(user.getDeptId());
						a.setCreateat(user.getUsername());
						a.setCreatedate(LocalDateTime.now());
						a.setModifyat(null);
						a.setModifydate(null);
						return a;
					}).forEach(qpCheckconfigService::save);
		}catch (Exception e){
			log.error("应用模板替换异常：", e);
			return R.failed("应用模板使用失败");
		}
		return R.ok();
	}

	private boolean isHave(QpCheckconfig tmp, List<QpCheckconfig> deptlist) {
		if (CollectionUtils.isNotEmpty(deptlist)) {
			for (QpCheckconfig a : deptlist) {
				if (tmp.getMaterialId().equals(a.getMaterialId()) && tmp.getChecktype().equals(a.getChecktype())) {
					return false;
				}
			}
		}
		return true;
	}
}
