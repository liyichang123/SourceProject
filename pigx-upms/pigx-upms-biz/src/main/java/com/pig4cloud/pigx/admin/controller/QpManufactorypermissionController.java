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
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpManufactorypermission;
import com.pig4cloud.pigx.admin.service.QpManufactorypermissionService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * 制造许可证表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpmanufactorypermission" )
@Api(value = "qpmanufactorypermission", tags = "制造许可证表管理")
public class QpManufactorypermissionController {

    private final  QpManufactorypermissionService qpManufactorypermissionService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpManufactorypermission 制造许可证表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpManufactorypermissionPage(Page page, QpManufactorypermission qpManufactorypermission) {
        return R.ok(qpManufactorypermissionService.page(page, Wrappers.query(qpManufactorypermission)));
    }

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpManufactorypermissionList() {
		QueryWrapper<QpManufactorypermission> queryWrapper = new QueryWrapper<QpManufactorypermission>();
		queryWrapper.select("id", "permission");
		return R.ok(qpManufactorypermissionService.list(queryWrapper));
	}

	/**
	 * 通过生产厂商id查询生产厂商许可列表
	 * @param qpmanufactoryId qpmanufactoryId
	 * @return R
	 */
	@ApiOperation(value = "通过部门id查询生产厂商列表", notes = "通过部门id查询生产厂商列表")
	@GetMapping("/getPermissionListByQpmanufactoryId/{qpmanufactoryId}" )
	public R  getPermissionListByQpmanufactoryId(@PathVariable("qpmanufactoryId" ) Integer qpmanufactoryId) {
		QueryWrapper<QpManufactorypermission> queryWrapper = new QueryWrapper<QpManufactorypermission>();
		queryWrapper.select("id value, permission label");
		queryWrapper.eq("manufactory_id", qpmanufactoryId);
		queryWrapper.eq("del_flag", "0");
		List<Map<String, Object>> qpmanufactorypermissionList = qpManufactorypermissionService.listMaps(queryWrapper);
		return R.ok(qpmanufactorypermissionList);
	}

	/**
	 * 获取制造许可
	 * @param code 制造许可证表
	 * @return
	 */
	@ApiOperation(value = "获取制造许可", notes = "获取制造许可")
	@GetMapping("/code/{code}" )
	public R getQpManufactorypermissionListById(@PathVariable("code" ) Integer code) {
		QpManufactorypermission qpManufactorypermission = new QpManufactorypermission();
		qpManufactorypermission.setManufactoryId(code);
		return R.ok(qpManufactorypermissionService.getQpManufactorypermissionListById(qpManufactorypermission));
	}
    /**
     * 通过id查询制造许可证表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpManufactorypermissionService.getById(id));
    }

    /**
     * 新增制造许可证表
     * @param qpManufactorypermission 制造许可证表
     * @return R
     */
    @ApiOperation(value = "新增制造许可证表", notes = "新增制造许可证表")
    @SysLog("新增制造许可证表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactorypermission_add')" )
    public R save(@RequestBody QpManufactorypermission qpManufactorypermission) {
        return R.ok(qpManufactorypermissionService.save(qpManufactorypermission));
    }

    /**
     * 修改制造许可证表
     * @param qpManufactorypermission 制造许可证表
     * @return R
     */
    @ApiOperation(value = "修改制造许可证表", notes = "修改制造许可证表")
    @SysLog("修改制造许可证表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactorypermission_edit')" )
    public R updateById(@RequestBody QpManufactorypermission qpManufactorypermission) {
        return R.ok(qpManufactorypermissionService.updateById(qpManufactorypermission));
    }

    /**
     * 通过id删除制造许可证表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除制造许可证表", notes = "通过id删除制造许可证表")
    @SysLog("通过id删除制造许可证表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactorypermission_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpManufactorypermissionService.removeById(id));
    }

}
