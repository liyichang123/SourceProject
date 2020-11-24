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
import com.pig4cloud.pigx.admin.entity.QpRqqy;
import com.pig4cloud.pigx.admin.service.QpRqqyService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 燃气企业表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qprqqy" )
@Api(value = "qprqqy", tags = "燃气企业表管理")
public class QpRqqyController {

    private final  QpRqqyService qpRqqyService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpRqqy 燃气企业表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpRqqyPage(Page page, QpRqqy qpRqqy) {
        return R.ok(qpRqqyService.page(page, Wrappers.query(qpRqqy)));
    }


    /**
     * 通过id查询燃气企业表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpRqqyService.getById(id));
    }

    /**
     * 新增燃气企业表
     * @param qpRqqy 燃气企业表
     * @return R
     */
    @ApiOperation(value = "新增燃气企业表", notes = "新增燃气企业表")
    @SysLog("新增燃气企业表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qprqqy_add')" )
    public R save(@RequestBody QpRqqy qpRqqy) {
        return R.ok(qpRqqyService.save(qpRqqy));
    }

    /**
     * 修改燃气企业表
     * @param qpRqqy 燃气企业表
     * @return R
     */
    @ApiOperation(value = "修改燃气企业表", notes = "修改燃气企业表")
    @SysLog("修改燃气企业表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qprqqy_edit')" )
    public R updateById(@RequestBody QpRqqy qpRqqy) {
        return R.ok(qpRqqyService.updateById(qpRqqy));
    }

    /**
     * 通过id删除燃气企业表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除燃气企业表", notes = "通过id删除燃气企业表")
    @SysLog("通过id删除燃气企业表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qprqqy_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpRqqyService.removeById(id));
    }

}
