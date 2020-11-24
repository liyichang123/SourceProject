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
import com.pig4cloud.pigx.admin.entity.QpManufactorypermission;
import com.pig4cloud.pigx.admin.entity.QpValveManufacturing;
import com.pig4cloud.pigx.admin.service.QpManufactorypermissionService;
import com.pig4cloud.pigx.admin.service.QpValveManufacturingService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpManufactory;
import com.pig4cloud.pigx.admin.service.QpManufactoryService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 生产厂商表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpmanufactory" )
@Api(value = "qpmanufactory", tags = "生产厂商表管理")
public class QpManufactoryController {

    private final  QpManufactoryService qpManufactoryService;

	private final  QpManufactorypermissionService qpManufactorypermissionService;
	private final QpValveManufacturingService qpValveManufacturingService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param qpManufactory 生产厂商表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getQpManufactoryPage(Page page, QpManufactory qpManufactory) {
		qpManufactory.setTenantId(TenantContextHolder.getTenantId());
        return R.ok(qpManufactoryService.getQpManufactoryPage(page, qpManufactory));
    }

	@ApiOperation(value = "查询所有列表", notes = "查询所有列表")
	@GetMapping("/list" )
	public R getQpManufactoryList() {
		QpManufactory qpManufactory = new QpManufactory();
		qpManufactory.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpManufactoryService.getQpManufactoryList(qpManufactory));
	}

    /**
     * 通过id查询生产厂商表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
		QpManufactory qpmanufactory = qpManufactoryService.getById(id);
		QueryWrapper<QpManufactorypermission> queryWrapper = new QueryWrapper<QpManufactorypermission>();
		queryWrapper.eq("manufactory_id", id);
		queryWrapper.eq("del_flag", "0");
		List<QpManufactorypermission> qpmanufactorypermissionList = qpManufactorypermissionService.list(queryWrapper);
		qpmanufactory.setQpmanufactorypermissionList(qpmanufactorypermissionList);


		QueryWrapper<QpValveManufacturing> queryWrapper2 = new QueryWrapper<QpValveManufacturing>();
		queryWrapper2.eq("manufactory_id", id);
		queryWrapper2.eq("del_flag", "0");
		List<QpValveManufacturing> qpvalvemanufacturings = qpValveManufacturingService.list(queryWrapper2);
		qpmanufactory.setQpvalvemanufacturingList(qpvalvemanufacturings);

		return R.ok(qpmanufactory);
    }

	/**
	 * 通过部门id查询生产厂商列表
	 * @param deptId deptId
	 * @return R
	 */
	@ApiOperation(value = "通过部门id查询生产厂商列表", notes = "通过部门id查询生产厂商列表")
	@GetMapping("/getQpmanufactoryListByDeptId/{deptId}" )
	public R getQpmanufactoryListByDeptId(@PathVariable("deptId" ) Integer deptId) {
		QueryWrapper<QpManufactory> queryWrapper = new QueryWrapper<QpManufactory>();
		queryWrapper.select("id value, name label");
		queryWrapper.eq("dept_id", deptId);
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		queryWrapper.orderByDesc("createDate");
		List<Map<String, Object>> qpmanufactoryList = qpManufactoryService.listMaps(queryWrapper);
		return R.ok(qpmanufactoryList);
	}

    /**
     * 新增生产厂商表
     * @param qpManufactory 生产厂商表
     * @return R
     */
    @ApiOperation(value = "新增生产厂商表", notes = "新增生产厂商表")
    @SysLog("新增生产厂商表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactory_add')" )
    public R save(@RequestBody QpManufactory qpManufactory) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpManufactory> queryWrapper = new QueryWrapper<QpManufactory>();
		queryWrapper.eq("name", qpManufactory.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpManufactory qpmanufactory = qpManufactoryService.getOne(queryWrapper);
		if(qpmanufactory == null) {
			qpManufactory.setCreateat(pigxUser.getUsername());
			qpManufactory.setCreatedate(LocalDateTime.now());
			qpManufactory.setDeptId(pigxUser.getDeptId());
			qpManufactory.setTenantId(TenantContextHolder.getTenantId());
			qpManufactoryService.save(qpManufactory);

			List<QpManufactorypermission> qpmanufactorypermissionList = qpManufactory.getQpmanufactorypermissionList();
			qpmanufactorypermissionList.stream().forEach(e -> { e.setId(null); e.setManufactoryId(qpManufactory.getId()); });
			qpManufactorypermissionService.saveBatch(qpmanufactorypermissionList);

			List<QpValveManufacturing> qpvalvemanufacturingList = qpManufactory.getQpvalvemanufacturingList();
			qpvalvemanufacturingList.stream().forEach(e -> { e.setId(null); e.setManufactoryId(qpManufactory.getId()); });
			qpValveManufacturingService.saveBatch(qpvalvemanufacturingList);
			return R.ok(true);
		} else {
			return R.failed().setMsg("气瓶制造商名称有重复");
		}

    }

    /**
     * 修改生产厂商表
     * @param qpManufactory 生产厂商表
     * @return R
     */
    @ApiOperation(value = "修改生产厂商表", notes = "修改生产厂商表")
    @SysLog("修改生产厂商表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactory_edit')" )
    public R updateById(@RequestBody QpManufactory qpManufactory) {
		PigxUser pigxUser = SecurityUtils.getUser();
		QueryWrapper<QpManufactory> queryWrapper = new QueryWrapper<QpManufactory>();
		queryWrapper.eq("name", qpManufactory.getName());
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", pigxUser.getDeptId());
		queryWrapper.eq("tenant_id", TenantContextHolder.getTenantId());
		QpManufactory qpmanufactory = qpManufactoryService.getOne(queryWrapper);
		if(qpmanufactory == null || (qpmanufactory != null && qpmanufactory.getId().equals(qpManufactory.getId()))) {
			qpManufactory.setModifyat(pigxUser.getUsername());
			qpManufactory.setModifydate(LocalDateTime.now());
			qpManufactoryService.updateById(qpManufactory);
			List<QpManufactorypermission> qpmanufactorypermissionList = qpManufactory.getQpmanufactorypermissionList();

			List<QpValveManufacturing> qpvalvemanufacturingList = qpManufactory.getQpvalvemanufacturingList();


		/*	//原集
			QueryWrapper<QpManufactorypermission> queryWrapper2 = new QueryWrapper<QpManufactorypermission>();
			queryWrapper2.select("id", "manufactory_id", "permission");
			queryWrapper2.eq("manufactory_id", qpManufactory.getId());
			queryWrapper2.eq("del_flag", "0");
			List<QpManufactorypermission> yqpmanufactorypermissionList = qpManufactorypermissionService.list(queryWrapper2);*/
		if(qpmanufactorypermissionList.size() > 0){

					qpmanufactorypermissionList.stream().forEach(e -> { e.setManufactoryId(qpManufactory.getId()); });
			qpManufactorypermissionService.saveOrUpdateBatch(qpmanufactorypermissionList);
		}
		if(qpvalvemanufacturingList.size() > 0) {
			qpvalvemanufacturingList.stream().forEach(e -> {e.setManufactoryId(qpManufactory.getId()); });
			qpValveManufacturingService.saveOrUpdateBatch(qpvalvemanufacturingList);
		}

			/*//交集
			List<QpManufactorypermission> intersection = qpmanufactorypermissionList.stream()
					.filter(e1 -> yqpmanufactorypermissionList.stream().map(e2 -> e2.getId()).collect(Collectors.toList())
							.contains(e1.getId())).collect(Collectors.toList());
			if(intersection.size() != 0) {
				//需修改的交集
				List<QpManufactorypermission> needModifyIntersection = new ArrayList<QpManufactorypermission>();
				intersection.stream().forEach(e -> {
					yqpmanufactorypermissionList.stream().forEach(ye -> {
						if(e.getId() == ye.getId() && !e.getPermission().equals(ye.getPermission())) {
							needModifyIntersection.add(e);
						}
					});
				});
				if(needModifyIntersection.size() != 0) {
					qpManufactorypermissionService.updateBatchById(needModifyIntersection);//删除
					needModifyIntersection.stream().forEach(e -> { e.setId(null); e.setManufactoryId(qpManufactory.getId()); });
					qpManufactorypermissionService.saveBatch(needModifyIntersection);//新增
				}
			}
			//差集 qpmanufactorypermissionList - intersection
			List<QpManufactorypermission> reduce = qpmanufactorypermissionList.stream()
					.filter(e1 -> !intersection.stream().map(e2 -> e2.getId()).collect(Collectors.toList())
							.contains(e1.getId())).collect(Collectors.toList());
			if(reduce.size() != 0) {
				reduce.stream().forEach(e -> { e.setId(null); e.setManufactoryId(qpManufactory.getId()); });
				qpManufactorypermissionService.saveBatch(reduce);//新增
			}
			//差集 yqpmanufactorypermissionList - intersection
			List<QpManufactorypermission> yreduce = yqpmanufactorypermissionList.stream()
					.filter(e1 -> !intersection.stream().map(e2 -> e2.getId()).collect(Collectors.toList())
							.contains(e1.getId())).collect(Collectors.toList());
			if(yreduce.size() != 0) {
				yreduce.stream().forEach(e -> e.setDelFlag("1"));
				qpManufactorypermissionService.updateBatchById(yreduce);//删除
			}*/
			return R.ok();
		} else {
			return R.failed().setMsg("气瓶制造商名称有重复");
		}
    }

    /**
     * 通过id删除生产厂商表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除生产厂商表", notes = "通过id删除生产厂商表")
    @SysLog("通过id删除生产厂商表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_qpmanufactory_del')" )
    public R removeById(@PathVariable Integer id) {
		QueryWrapper<QpManufactory> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("id", id);
		queryWrapper.eq("del_flag", "0");
		QpManufactory qpmanufactory = qpManufactoryService.getOne(queryWrapper);
		if(qpmanufactory != null) {
			QueryWrapper<QpManufactorypermission> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("manufactory_id", id);
			queryWrapper2.eq("del_flag", "0");
			List<QpManufactorypermission> qpmanufactorypermissionList = qpManufactorypermissionService.list(queryWrapper2);
			if (CollectionUtils.isNotEmpty(qpmanufactorypermissionList)) {
				qpmanufactorypermissionList.forEach(e -> e.setDelFlag("1"));
				qpManufactorypermissionService.updateBatchById(qpmanufactorypermissionList);
			}
			qpmanufactory.setDelFlag("1");

			QueryWrapper<QpValveManufacturing> queryWrapper3 = new QueryWrapper<QpValveManufacturing>();
			queryWrapper3.eq("manufactory_id", id);
			queryWrapper3.eq("del_flag", "0");
			List<QpValveManufacturing> qpvalvemanufacturingList = qpValveManufacturingService.list(queryWrapper3);
			if (CollectionUtils.isNotEmpty(qpvalvemanufacturingList)) {
				qpvalvemanufacturingList.forEach(e -> e.setDelFlag("1"));
				qpValveManufacturingService.updateBatchById(qpvalvemanufacturingList);
			}
			return R.ok(qpManufactoryService.updateById(qpmanufactory));
		} else {
			return R.ok();
		}
		//return R.ok(qpManufactoryService.removeById(id));
    }

}
