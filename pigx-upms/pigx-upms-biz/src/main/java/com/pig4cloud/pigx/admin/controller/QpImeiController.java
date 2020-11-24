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
import com.pig4cloud.pigx.admin.entity.QpImei;
import com.pig4cloud.pigx.admin.service.QpImeiService;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * IMEI设备表
 *
 * @author fll
 * @date 2020-05-22 15:08:34
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpimei" )
@Api(value = "qpimei", tags = "IMEI设备表管理")
public class QpImeiController {

    private final  QpImeiService qpImeiService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpImei IMEI设备表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpImeiPage(Page page, QpImei qpImei) {
		qpImei.setTenantId(TenantContextHolder.getTenantId());
        return R.ok(qpImeiService.getQpImeiPage(page, qpImei));
    }

    /**
     * 通过id查询IMEI设备表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpImeiService.getById(id));
    }

	/**
	 * 通过IMEI号查询IMEI设备表
	 * @param imei imei
	 * @return R
	 */
	@Inner(value = false)
	@ApiOperation(value = "通过IMEI号查询IMEI设备表", notes = "通过IMEI号查询IMEI设备表")
	@GetMapping("/getByImei/{imei}" )
	public R getByImei(@PathVariable("imei" ) String imei) {
		QueryWrapper<QpImei> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("imei", imei);
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpImei qpImei = qpImeiService.getOne(queryWrapper);
		if(qpImei != null) {
			return R.ok().setData(qpImei);
		} else {
			QpImei qpimei = new QpImei();
			qpimei.setImei(imei);
			qpimei.setIsavailable("0");
			qpimei.setCreateat("qpadmin");
			qpimei.setCreatedate(LocalDateTime.now());
			qpimei.setDelFlag("0");
			qpimei.setDeptId(10);
			qpimei.setTenantId(TenantContextHolder.getTenantId());
			qpImeiService.save(qpimei);
			return R.ok().setData(qpimei);
		}
	}

    /**
     * 新增IMEI设备表
     * @param qpImei IMEI设备表
     * @return R
     */
    @ApiOperation(value = "新增IMEI设备表", notes = "新增IMEI设备表")
    @SysLog("新增IMEI设备表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpimei_add')" )
    public R save(@RequestBody QpImei qpImei) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpImei> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("imei", qpImei.getImei());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpImei qpimei = qpImeiService.getOne(queryWrapper);
		if(qpimei == null ) {
			qpImei.setCreateat(pigxUser.getUsername());
			qpImei.setCreatedate(LocalDateTime.now());
			qpImei.setModifyat(null);
			qpImei.setDelFlag("0");
			qpImei.setDeptId(pigxUser.getDeptId());
			qpImei.setTenantId(TenantContextHolder.getTenantId());
			return R.ok(qpImeiService.save(qpImei));
		} else {
			return R.failed().setMsg("IMEI设备号有重复");
		}
    }

    /**
     * 修改IMEI设备表
     * @param qpImei IMEI设备表
     * @return R
     */
    @ApiOperation(value = "修改IMEI设备表", notes = "修改IMEI设备表")
    @SysLog("修改IMEI设备表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpimei_edit')" )
    public R updateById(@RequestBody QpImei qpImei) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpImei> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("imei", qpImei.getImei());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpImei qpimei = qpImeiService.getOne(queryWrapper);
		if(qpimei == null || qpimei.getId().equals(qpImei.getId())) {
			qpImei.setModifyat(pigxUser.getUsername());
			qpImei.setModifydate(LocalDateTime.now());
			return R.ok(qpImeiService.updateById(qpImei));
		} else {
			return R.failed().setMsg("IMEI设备号有重复");
		}
    }

    /**
     * 通过id删除IMEI设备表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除IMEI设备表", notes = "通过id删除IMEI设备表")
    @SysLog("通过id删除IMEI设备表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpimei_del')" )
    public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpImei> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpImei qpImei = qpImeiService.getOne(queryWrapper);
		if(qpImei != null) {
			qpImei.setDelFlag("1");
			qpImei.setModifyat(SecurityUtils.getUser().getUsername());
			qpImei.setModifydate(LocalDateTime.now());
			return R.ok(qpImeiService.updateById(qpImei));
		} else {
			return R.ok();
		}
    }

}
