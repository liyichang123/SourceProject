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
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpMatchRule;
import com.pig4cloud.pigx.admin.service.QpMatchRuleService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpGascylindertype;
import com.pig4cloud.pigx.admin.service.QpGascylindertypeService;
import com.pig4cloud.pigx.common.security.annotation.Inner;
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


/**
 * 气瓶类型表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Inner(value = false)
@RestController
@AllArgsConstructor
@RequestMapping("/qpgascylindertype" )
@Api(value = "qpgascylindertype", tags = "气瓶类型表管理")
public class QpGascylindertypeController {

    private final  QpGascylindertypeService qpGascylindertypeService;

	private final  QpMatchRuleService qpMatchRuleService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpGascylindertype 气瓶类型表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpGascylindertypePage(Page page, QpGascylindertype qpGascylindertype) {
		qpGascylindertype.setTenantId(TenantContextHolder.getTenantId());
		qpGascylindertype.setDeptId(SecurityUtils.getUser().getDeptId());
        return R.ok(qpGascylindertypeService.getQpGascylindertypePage(page, qpGascylindertype));
    }

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpGascylindertypeList() {
		QueryWrapper<QpGascylindertype> queryWrapper = new QueryWrapper<QpGascylindertype>();
		queryWrapper.select("id", "name", "limitation", "regularlycheckcycle");
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(SecurityUtils.getUser().getDeptId());
		queryWrapper.in("dept_id", depts);
		queryWrapper.orderByDesc("createDate");
		return R.ok(qpGascylindertypeService.list(queryWrapper));
	}

    /**
     * 通过id查询气瓶类型表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpGascylindertypeService.getById(id));
    }

    /**
     * 新增气瓶类型表
     * @param qpGascylindertype 气瓶类型表
     * @return R
     */
    @ApiOperation(value = "新增气瓶类型表", notes = "新增气瓶类型表")
    @SysLog("新增气瓶类型表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpgascylindertype_add')" )
    public R save(@RequestBody QpGascylindertype qpGascylindertype) {
		QueryWrapper<QpGascylindertype> queryWrapper = new QueryWrapper<QpGascylindertype>();
		PigxUser pigxUser = SecurityUtils.getUser();
		queryWrapper.eq("name", qpGascylindertype.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		queryWrapper.in("dept_id", getDepts(pigxUser));
		QpGascylindertype qpgascylindertype = qpGascylindertypeService.getOne(queryWrapper);
		if(qpgascylindertype == null) {
			qpGascylindertype.setCreateat(pigxUser.getUsername());
			qpGascylindertype.setCreatedate(LocalDateTime.now());
			qpGascylindertype.setModifyat(null);
			qpGascylindertype.setDelFlag("0");
			qpGascylindertype.setDeptId(pigxUser.getDeptId());
			qpGascylindertype.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpGascylindertypeService.save(qpGascylindertype));
		} else {
			return R.failed().setMsg("气瓶类型名称有重复");
		}
    }

    /**
     * 修改气瓶类型表
     * @param qpGascylindertype 气瓶类型表
     * @return R
     */
    @ApiOperation(value = "修改气瓶类型表", notes = "修改气瓶类型表")
    @SysLog("修改气瓶类型表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpgascylindertype_edit')" )
    public R updateById(@RequestBody QpGascylindertype qpGascylindertype) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpGascylindertype> queryWrapper = new QueryWrapper<QpGascylindertype>();
		queryWrapper.eq("name", qpGascylindertype.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		queryWrapper.in("dept_id", getDepts(pigxUser));
		QpGascylindertype qpgascylindertype = qpGascylindertypeService.getOne(queryWrapper);
		if(qpgascylindertype == null || qpgascylindertype.getId().equals(qpGascylindertype.getId())) {
			QpGascylindertype qpgascylindertype2 = qpGascylindertypeService.getById(qpGascylindertype.getId());
			qpgascylindertype2.setDelFlag("1");
			qpGascylindertypeService.updateById(qpgascylindertype2);
			qpGascylindertype.setCreateat(qpgascylindertype2.getCreateat());
			qpGascylindertype.setCreatedate(qpgascylindertype2.getCreatedate());
			qpGascylindertype.setModifyat(pigxUser.getUsername());
			qpGascylindertype.setModifydate(LocalDateTime.now());
			qpGascylindertype.setDelFlag("0");
			qpGascylindertype.setDeptId(pigxUser.getDeptId());
			qpGascylindertype.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpGascylindertypeService.save(qpGascylindertype));
		} else {
			return R.failed().setMsg("气瓶类型名称有重复");
		}
    }

    /**
     * 通过id删除气瓶类型表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除气瓶类型表", notes = "通过id删除气瓶类型表")
    @SysLog("通过id删除气瓶类型表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpgascylindertype_del')" )
    public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpGascylindertype> queryWrapper = new QueryWrapper<QpGascylindertype>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpGascylindertype qpgascylindertype = qpGascylindertypeService.getOne(queryWrapper);
		if(qpgascylindertype != null) {
			QueryWrapper<QpMatchRule> queryWrapper2 = new QueryWrapper<QpMatchRule>();
			queryWrapper2.eq("gadscyclindetype_id", id);
			queryWrapper2.eq("del_flag", "0");
			int count = qpMatchRuleService.count(queryWrapper2);
			if(count == 0) {
				qpgascylindertype.setDelFlag("1");
				qpgascylindertype.setModifyat(SecurityUtils.getUser().getUsername());
				qpgascylindertype.setModifydate(LocalDateTime.now());
				return R.ok(qpGascylindertypeService.updateById(qpgascylindertype));
			} else {
				return R.failed().setMsg("已存在匹配规则中，请先删除与之相关的匹配规则");
			}
		} else {
			return R.ok();
		}
        //return R.ok(qpGascylindertypeService.removeById(id));
    }

    private List<Integer> getDepts(PigxUser pigxUser){
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(pigxUser.getDeptId());
		return depts;
	}

}
