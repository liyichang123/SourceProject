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
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpValveManufacturing;
import com.pig4cloud.pigx.admin.service.QpValveManufacturingService;
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
 * 气瓶阀门制造信息
 *
 * @author wh
 * @date 2020-04-22 22:51:43
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpvalvemanufacturing" )
@Api(value = "qpvalvemanufacturing", tags = "气瓶阀门制造信息管理")
public class QpValveManufacturingController {

    private final  QpValveManufacturingService qpValveManufacturingService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpValveManufacturing 气瓶阀门制造信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpValveManufacturingPage(Page page, QpValveManufacturing qpValveManufacturing) {
        return R.ok(qpValveManufacturingService.page(page, Wrappers.query(qpValveManufacturing)));
    }


    /**
     * 通过id查询气瓶阀门制造信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpValveManufacturingService.getById(id));
    }

    /**
     * 新增气瓶阀门制造信息
     * @param qpValveManufacturing 气瓶阀门制造信息
     * @return R
     */
    @ApiOperation(value = "新增气瓶阀门制造信息", notes = "新增气瓶阀门制造信息")
    @SysLog("新增气瓶阀门制造信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpvalvemanufacturing_add')" )
    public R save(@RequestBody QpValveManufacturing qpValveManufacturing) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpValveManufacturing.setCreateat(pigxUser.getUsername());
		qpValveManufacturing.setCreatedate(LocalDateTime.now());
		qpValveManufacturing.setModifyat(null);
		qpValveManufacturing.setDelFlag("0");
		qpValveManufacturing.setDeptId(pigxUser.getDeptId());
		qpValveManufacturing.setTenantId(TenantContextHolder.getTenantId());
        return R.ok(qpValveManufacturingService.save(qpValveManufacturing));
    }

    /**
     * 修改气瓶阀门制造信息
     * @param qpValveManufacturing 气瓶阀门制造信息
     * @return R
     */
    @ApiOperation(value = "修改气瓶阀门制造信息", notes = "修改气瓶阀门制造信息")
    @SysLog("修改气瓶阀门制造信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpvalvemanufacturing_edit')" )
    public R updateById(@RequestBody QpValveManufacturing qpValveManufacturing) {
		PigxUser pigxUser = SecurityUtils.getUser();
        return R.ok(qpValveManufacturingService.updateById(qpValveManufacturing));
    }

    /**
     * 通过id删除气瓶阀门制造信息
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除气瓶阀门制造信息", notes = "通过id删除气瓶阀门制造信息")
    @SysLog("通过id删除气瓶阀门制造信息" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpvalvemanufacturing_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpValveManufacturingService.removeById(id));
    }

    /**
     * 查询所有阀门信息列表: <br>
     *
     * @Return: R
     * @Author: gmx
     * @Date: 2020/4/24 16:17
     */
	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpValveManufacturingList() {
		QueryWrapper<QpValveManufacturing> queryWrapper = new QueryWrapper<QpValveManufacturing>();
		queryWrapper.select("id", "name", "model", "ceTypeCertificate");
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		queryWrapper.orderByDesc("createDate");
		return R.ok(qpValveManufacturingService.list());
	}


	/**
	 * 通过生产厂商id查询生产厂商许可列表
	 * @param qpmanufactoryId qpmanufactoryId
	 * @return R
	 */
	@ApiOperation(value = "通过部门id查询生产厂商列表", notes = "通过部门id查询生产厂商列表")
	@GetMapping("/getListByqpmanufactoryId/{qpmanufactoryId}" )
	public R  getListByqpmanufactoryId(@PathVariable("qpmanufactoryId" ) Integer qpmanufactoryId) {
		QueryWrapper<QpValveManufacturing> queryWrapper = new QueryWrapper<QpValveManufacturing>();
		queryWrapper.select("id,name,model,ce_type_certificate");
		queryWrapper.eq("manufactory_id", qpmanufactoryId);
		queryWrapper.eq("del_flag", "0");
		List<Map<String, Object>> qpmanufactorypermissionList = qpValveManufacturingService.listMaps(queryWrapper);
		return R.ok(qpmanufactorypermissionList);
	}
}
