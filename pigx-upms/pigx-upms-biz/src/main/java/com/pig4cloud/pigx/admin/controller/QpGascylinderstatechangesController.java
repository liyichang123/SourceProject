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
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpGascylinderstatechanges;
import com.pig4cloud.pigx.admin.service.QpGascylinderstatechangesService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 气瓶状态变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpgascylinderstatechanges" )
@Api(value = "qpgascylinderstatechanges", tags = "气瓶状态变更记录表管理")
public class QpGascylinderstatechangesController {

    private final  QpGascylinderstatechangesService qpGascylinderstatechangesService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpGascylinderstatechanges 气瓶状态变更记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpGascylinderstatechangesPage(Page page, QpGascylinderstatechanges qpGascylinderstatechanges) {
        return R.ok(qpGascylinderstatechangesService.page(page, Wrappers.query(qpGascylinderstatechanges)));
    }


    /**
     * 通过id查询气瓶状态变更记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpGascylinderstatechangesService.getById(id));
    }

    /**
     * 新增气瓶状态变更记录表
     * @param qpGascylinderstatechanges 气瓶状态变更记录表
     * @return R
     */
    @ApiOperation(value = "新增气瓶状态变更记录表", notes = "新增气瓶状态变更记录表")
    @SysLog("新增气瓶状态变更记录表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpgascylinderstatechanges_add')" )
    public R save(@RequestBody QpGascylinderstatechanges qpGascylinderstatechanges) {
        return R.ok(qpGascylinderstatechangesService.save(qpGascylinderstatechanges));
    }

    /**
     * 修改气瓶状态变更记录表
     * @param qpGascylinderstatechanges 气瓶状态变更记录表
     * @return R
     */
    @ApiOperation(value = "修改气瓶状态变更记录表", notes = "修改气瓶状态变更记录表")
    @SysLog("修改气瓶状态变更记录表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpgascylinderstatechanges_edit')" )
    public R updateById(@RequestBody QpGascylinderstatechanges qpGascylinderstatechanges) {
        return R.ok(qpGascylinderstatechangesService.updateById(qpGascylinderstatechanges));
    }

    /**
     * 通过id删除气瓶状态变更记录表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除气瓶状态变更记录表", notes = "通过id删除气瓶状态变更记录表")
    @SysLog("通过id删除气瓶状态变更记录表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpgascylinderstatechanges_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpGascylinderstatechangesService.removeById(id));
    }

}
