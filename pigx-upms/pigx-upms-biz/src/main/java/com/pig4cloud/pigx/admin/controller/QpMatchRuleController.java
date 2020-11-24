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
import com.pig4cloud.pigx.admin.entity.QpMaterial;
import com.pig4cloud.pigx.admin.service.QpMaterialService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpMatchRule;
import com.pig4cloud.pigx.admin.service.QpMatchRuleService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 气瓶类型和介质匹配规则表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpmatchrule")
@Api(value = "qpmatchrule", tags = "气瓶类型和介质匹配规则表管理")
public class QpMatchRuleController {

	private final QpMatchRuleService qpMatchRuleService;
	private final QpMaterialService qpMaterialService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param qpMatchRule 气瓶类型和介质匹配规则表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpMatchRulePage(Page page, QpMatchRule qpMatchRule) {
		qpMatchRule.setTenantId(TenantContextHolder.getTenantId());
		qpMatchRule.setDeptId(SecurityUtils.getUser().getDeptId());
		return R.ok(qpMatchRuleService.getQpMatchRulePage(page, qpMatchRule));
	}

	@ApiOperation(value = "根据气瓶类型获取对应介质", notes = "根据气瓶类型获取对应介质")
	@GetMapping("/getMaterialByRule/{code}")
	public R getMaterialByRule(@PathVariable("code") Integer code) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpMatchRule qpMatchRule = new QpMatchRule();
		qpMatchRule.setGadscyclindetypeId(code);
		qpMatchRule.setDeptId(pigxUser.getDeptId());
		qpMatchRule.setTenantId(TenantContextHolder.getTenantId());
		List<QpMaterial> materials = qpMatchRuleService.getMaterialByRule(qpMatchRule);
		/*List<QpMatchRule> qpMatchRuleList = qpMatchRuleService.getMaterialByRule(qpMatchRule);
		List<QpMaterial> materials = null;
			QueryWrapper<QpMaterial> queryWrapper = new QueryWrapper<QpMaterial>();
			queryWrapper.eq("del_flag", "0");
			List<QpMaterial> list = qpMaterialService.list(queryWrapper);
		if (CollectionUtils.isNotEmpty(qpMatchRuleList)) {
			materials = list.stream()
					.filter(a -> ArrayUtils.contains(qpMatchRuleList.stream().map(QpMatchRule::getMaterialId).toArray(Integer[]::new), a.getId()))
					.collect(Collectors.toList());
		}*/
		materials = CollectionUtils.isNotEmpty(materials) ? materials : new ArrayList<>();
		return R.ok(materials);
	}

	/**
	 * 通过id查询气瓶类型和介质匹配规则表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpMatchRuleService.getById(id));
	}

	/**
	 * 新增气瓶类型和介质匹配规则表
	 *
	 * @param qpMatchRule 气瓶类型和介质匹配规则表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶类型和介质匹配规则表", notes = "新增气瓶类型和介质匹配规则表")
	@SysLog("新增气瓶类型和介质匹配规则表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpmatchrule_add')")
	public R save(@RequestBody QpMatchRule qpMatchRule) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpMatchRule qpmatchrule = getOneMatchRule(pigxUser, qpMatchRule);
		if (qpmatchrule == null) {
			qpMatchRule.setCreateat(pigxUser.getUsername());
			qpMatchRule.setCreatedate(LocalDateTime.now());
			qpMatchRule.setModifyat(null);
			qpMatchRule.setDelFlag("0");
			qpMatchRule.setDeptId(pigxUser.getDeptId());
			qpMatchRule.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpMatchRuleService.save(qpMatchRule));
		} else {
			return R.failed().setMsg("规则匹配有重复");
		}
	}

	/**
	 * 修改气瓶类型和介质匹配规则表
	 *
	 * @param qpMatchRule 气瓶类型和介质匹配规则表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶类型和介质匹配规则表", notes = "修改气瓶类型和介质匹配规则表")
	@SysLog("修改气瓶类型和介质匹配规则表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpmatchrule_edit')")
	public R updateById(@RequestBody QpMatchRule qpMatchRule) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpMatchRule qpmatchrule = getOneMatchRule(pigxUser, qpMatchRule);
		if (qpmatchrule == null || qpmatchrule.getId().equals(qpMatchRule.getId())) {
			qpMatchRule.setModifyat(pigxUser.getUsername());
			qpMatchRule.setModifydate(LocalDateTime.now());
			return R.ok(qpMatchRuleService.updateById(qpMatchRule));
		} else {
			return R.failed().setMsg("规则匹配有重复");
		}
	}

	/**
	 * 通过id删除气瓶类型和介质匹配规则表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶类型和介质匹配规则表", notes = "通过id删除气瓶类型和介质匹配规则表")
	@SysLog("通过id删除气瓶类型和介质匹配规则表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpmatchrule_del')")
	public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpMatchRule> queryWrapper = new QueryWrapper<QpMatchRule>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpMatchRule qpmatchrule = qpMatchRuleService.getOne(queryWrapper);
		if (qpmatchrule != null) {
			qpmatchrule.setDelFlag("1");
			qpmatchrule.setModifyat(SecurityUtils.getUser().getUsername());
			qpmatchrule.setModifydate(LocalDateTime.now());
			return R.ok(qpMatchRuleService.updateById(qpmatchrule));
		} else {
			return R.ok();
		}
		//return R.ok(qpMatchRuleService.removeById(id));
	}

	private QpMatchRule getOneMatchRule(PigxUser pigxUser, QpMatchRule qpMatchRule) {
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(pigxUser.getDeptId());
		QueryWrapper<QpMatchRule> queryWrapper = new QueryWrapper<QpMatchRule>();
		queryWrapper.eq("gadscyclindetype_id", qpMatchRule.getGadscyclindetypeId());
		queryWrapper.eq("material_id", qpMatchRule.getMaterialId());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.in("dept_id", depts);
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		return qpMatchRuleService.getOne(queryWrapper);
	}
}
