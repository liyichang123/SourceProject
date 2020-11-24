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
import com.pig4cloud.pigx.admin.entity.QpZjj;
import com.pig4cloud.pigx.admin.service.QpZjjService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 质监局表
 *
 * @author wh
 * @date 2020-04-03 14:04:22
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpzjj" )
@Api(value = "qpzjj", tags = "质监局表管理")
public class QpZjjController {

    private final  QpZjjService qpZjjService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param qpZjj 质监局表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpZjjPage(Page page, QpZjj qpZjj) {
        return R.ok(qpZjjService.page(page, Wrappers.query(qpZjj)));
    }


    /**
     * 通过id查询质监局表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(qpZjjService.getById(id));
    }

    /**
     * 新增质监局表
     * @param qpZjj 质监局表
     * @return R
     */
    @ApiOperation(value = "新增质监局表", notes = "新增质监局表")
    @SysLog("新增质监局表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpzjj_add')" )
    public R save(@RequestBody QpZjj qpZjj) {
        return R.ok(qpZjjService.save(qpZjj));
    }

    /**
     * 修改质监局表
     * @param qpZjj 质监局表
     * @return R
     */
    @ApiOperation(value = "修改质监局表", notes = "修改质监局表")
    @SysLog("修改质监局表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpzjj_edit')" )
    public R updateById(@RequestBody QpZjj qpZjj) {
        return R.ok(qpZjjService.updateById(qpZjj));
    }

    /**
     * 通过id删除质监局表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除质监局表", notes = "通过id删除质监局表")
    @SysLog("通过id删除质监局表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpzjj_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(qpZjjService.removeById(id));
    }

}
