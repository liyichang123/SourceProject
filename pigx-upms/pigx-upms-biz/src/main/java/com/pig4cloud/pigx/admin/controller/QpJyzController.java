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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpJyz;
import com.pig4cloud.pigx.admin.service.QpJyzService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


/**
 * 检验站表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpjyz" )
@Api(value = "qpjyz", tags = "检验站表管理")
public class QpJyzController {

    private final  QpJyzService qpJyzService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpJyz 检验站表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpJyzPage(Page page, QpJyz qpJyz) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpJyz.setDeptId(pigxUser.getDeptId());
		qpJyz.setTenantId(TenantContextHolder.getTenantId());
    	return R.ok(qpJyzService.page(page, Wrappers.query(qpJyz)));
    }


    /**
     * 通过id查询检验站表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpJyzService.getById(id));
    }

    /**
     * 新增检验站表
     * @param qpJyz 检验站表
     * @return R
     */
    @ApiOperation(value = "新增检验站表", notes = "新增检验站表")
    @SysLog("新增检验站表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpjyz_add')" )
    public R save(@RequestBody QpJyz qpJyz) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpJyz.setCreateat(pigxUser.getUsername());
		qpJyz.setCreatedate(LocalDateTime.now());
		qpJyz.setModifyat(null);
		qpJyz.setDelFlag("0");
		qpJyz.setDeptId(pigxUser.getDeptId());
		qpJyz.setTenantId(TenantContextHolder.getTenantId());
        return R.ok(qpJyzService.save(qpJyz));
    }

    /**
     * 修改检验站表
     * @param qpJyz 检验站表
     * @return R
     */
    @ApiOperation(value = "修改检验站表", notes = "修改检验站表")
    @SysLog("修改检验站表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpjyz_edit')" )
    public R updateById(@RequestBody QpJyz qpJyz) {
        return R.ok(qpJyzService.updateById(qpJyz));
    }

    /**
     * 通过id删除检验站表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除检验站表", notes = "通过id删除检验站表")
    @SysLog("通过id删除检验站表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpjyz_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpJyzService.removeById(id));
    }

}
