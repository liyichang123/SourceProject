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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpStore;
import com.pig4cloud.pigx.admin.service.QpStoreService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 气瓶仓库表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpstore" )
@Api(value = "qpstore", tags = "气瓶仓库表管理")
public class QpStoreController {

    private final  QpStoreService qpStoreService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpStore 气瓶仓库表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpStorePage(Page page, QpStore qpStore) {
		qpStore.setTenantId(TenantContextHolder.getTenantId());
        return R.ok(qpStoreService.getQpStorePage(page, qpStore));
    }

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpStoreList() {
		QpStore qpStore = new QpStore();
		qpStore.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpStoreService.getQpStoreList(qpStore));
	}

    /**
     * 通过id查询气瓶仓库表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpStoreService.getById(id));
    }

    /**
     * 新增气瓶仓库表
     * @param qpStore 气瓶仓库表
     * @return R
     */
    @ApiOperation(value = "新增气瓶仓库表", notes = "新增气瓶仓库表")
    @SysLog("新增气瓶仓库表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpstore_add')" )
    public R save(@RequestBody QpStore qpStore) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpStore> queryWrapper = new QueryWrapper<QpStore>();
		queryWrapper.eq("name", qpStore.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpStore qpstore = qpStoreService.getOne(queryWrapper);
		if(qpstore == null) {
			qpStore.setCreateat(pigxUser.getUsername());
			qpStore.setCreatedate(LocalDateTime.now());
			qpStore.setModifyat(null);
			qpStore.setDelFlag("0");
			qpStore.setDeptId(pigxUser.getDeptId());
			qpStore.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpStoreService.save(qpStore));
		} else {
			return R.failed().setMsg("气瓶仓库名称有重复");
		}
    }

    /**
     * 修改气瓶仓库表
     * @param qpStore 气瓶仓库表
     * @return R
     */
    @ApiOperation(value = "修改气瓶仓库表", notes = "修改气瓶仓库表")
    @SysLog("修改气瓶仓库表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpstore_edit')" )
    public R updateById(@RequestBody QpStore qpStore) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpStore> queryWrapper = new QueryWrapper<QpStore>();
		queryWrapper.eq("name", qpStore.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpStore qpstore = qpStoreService.getOne(queryWrapper);
		if(qpstore == null || (qpstore != null && qpstore.getId().equals(qpStore.getId()))) {
			qpStore.setModifyat(pigxUser.getUsername());
			qpStore.setModifydate(LocalDateTime.now());
			return R.ok(qpStoreService.updateById(qpStore));
		} else {
			return R.failed().setMsg("气瓶仓库名称有重复");
		}
    }

    /**
     * 通过id删除气瓶仓库表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除气瓶仓库表", notes = "通过id删除气瓶仓库表")
    @SysLog("通过id删除气瓶仓库表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpstore_del')" )
    public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpStore> queryWrapper = new QueryWrapper<QpStore>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpStore qpstore = qpStoreService.getOne(queryWrapper);
		if(qpstore != null) {
			qpstore.setDelFlag("1");
			return R.ok(qpStoreService.updateById(qpstore));
		} else {
			return R.ok();
		}
        //return R.ok(qpStoreService.removeById(id));
    }

}
