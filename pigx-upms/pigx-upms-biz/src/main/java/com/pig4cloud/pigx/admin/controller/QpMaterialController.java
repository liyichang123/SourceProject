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
import com.pig4cloud.pigx.admin.entity.QpGascylindertype;
import com.pig4cloud.pigx.admin.entity.QpMatchRule;
import com.pig4cloud.pigx.admin.service.QpMatchRuleService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpMaterial;
import com.pig4cloud.pigx.admin.service.QpMaterialService;
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
 * 充装介质表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpmaterial" )
@Api(value = "qpmaterial", tags = "充装介质表管理")
public class QpMaterialController {

    private final  QpMaterialService qpMaterialService;

	private final  QpMatchRuleService qpMatchRuleService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpMaterial 充装介质表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpMaterialPage(Page page, QpMaterial qpMaterial) {
		qpMaterial.setTenantId(TenantContextHolder.getTenantId());
		qpMaterial.setDeptId(SecurityUtils.getUser().getDeptId());
        return R.ok(qpMaterialService.getQpMaterialPage(page, qpMaterial));
    }

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpMaterialList() {
		QueryWrapper<QpMaterial> queryWrapper = new QueryWrapper<QpMaterial>();
		queryWrapper.select("id", "name");
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		queryWrapper.orderByDesc("createDate");
		return R.ok(qpMaterialService.list(queryWrapper));
	}

    /**
     * 通过id查询充装介质表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpMaterialService.getById(id));
    }

    /**
     * 新增充装介质表
     * @param qpMaterial 充装介质表
     * @return R
     */
    @ApiOperation(value = "新增充装介质表", notes = "新增充装介质表")
    @SysLog("新增充装介质表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmaterial_add')" )
    public R save(@RequestBody QpMaterial qpMaterial) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpMaterial qpmaterial = getOneQpMaterial(pigxUser, qpMaterial);
		if(qpmaterial == null) {
			qpMaterial.setCreateat(pigxUser.getUsername());
			qpMaterial.setCreatedate(LocalDateTime.now());
			qpMaterial.setModifyat(null);
			qpMaterial.setDelFlag("0");
			qpMaterial.setDeptId(pigxUser.getDeptId());
			qpMaterial.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpMaterialService.save(qpMaterial));
		} else {
			return R.failed().setMsg("充装介质名称有重复");
		}
    }

    /**
     * 修改充装介质表
     * @param qpMaterial 充装介质表
     * @return R
     */
    @ApiOperation(value = "修改充装介质表", notes = "修改充装介质表")
    @SysLog("修改充装介质表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmaterial_edit')" )
    public R updateById(@RequestBody QpMaterial qpMaterial) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QpMaterial qpmaterial = getOneQpMaterial(pigxUser, qpMaterial);
		if(qpmaterial == null || qpmaterial.getId().equals(qpMaterial.getId())) {
			qpMaterial.setModifyat(pigxUser.getUsername());
			qpMaterial.setModifydate(LocalDateTime.now());
			return R.ok(qpMaterialService.updateById(qpMaterial));
			/*QpMaterial qpmaterial2 = qpMaterialService.getById(qpMaterial.getId());
			qpmaterial2.setDelFlag("1");
			qpMaterialService.updateById(qpmaterial2);
			PigxUser pigxUser = SecurityUtils.getUser();
			qpMaterial.setCreateat(qpmaterial2.getCreateat());
			qpMaterial.setCreatedate(qpmaterial2.getCreatedate());
			qpMaterial.setModifyat(pigxUser.getId().toString());
			qpMaterial.setModifydate(LocalDateTime.now());
			qpMaterial.setDelFlag("0");
			qpMaterial.setDeptId(pigxUser.getDeptId());
			qpMaterial.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpMaterialService.save(qpMaterial));*/
		} else {
			return R.failed().setMsg("充装介质名称有重复");
		}
    }

    /**
     * 通过id删除充装介质表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除充装介质表", notes = "通过id删除充装介质表")
    @SysLog("通过id删除充装介质表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpmaterial_del')" )
    public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpMaterial> queryWrapper = new QueryWrapper<QpMaterial>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpMaterial qpmaterial = qpMaterialService.getOne(queryWrapper);
		if(qpmaterial != null) {
			QueryWrapper<QpMatchRule> queryWrapper2 = new QueryWrapper<QpMatchRule>();
			queryWrapper2.eq("material_id", id);
			queryWrapper2.eq("del_flag", "0");
			int count = qpMatchRuleService.count(queryWrapper2);
			if(count == 0) {
				qpmaterial.setDelFlag("1");
				qpmaterial.setModifyat(SecurityUtils.getUser().getUsername());
				qpmaterial.setModifydate(LocalDateTime.now());
				return R.ok(qpMaterialService.updateById(qpmaterial));
			} else {
				return R.failed().setMsg("已存在匹配规则中，请先删除与之相关的匹配规则");
			}
		} else {
			return R.ok();
		}
        //return R.ok(qpMaterialService.removeById(id));
    }

    private QpMaterial getOneQpMaterial(PigxUser pigxUser, QpMaterial qpMaterial){
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(pigxUser.getDeptId());
		QueryWrapper<QpMaterial> queryWrapper = new QueryWrapper<QpMaterial>();
		queryWrapper.eq("name", qpMaterial.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.in("dept_id", depts);
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		return qpMaterialService.getOne(queryWrapper);
	}
}
