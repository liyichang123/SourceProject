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
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpCheckconfig;
import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.admin.entity.QpGascylinderstatechanges;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpCheckrecord;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import static com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum.*;

import java.time.LocalDateTime;


/**
 * 气瓶检验记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpcheckrecord")
@Api(value = "qpcheckrecord", tags = "气瓶检验记录表管理")
public class QpCheckrecordController {

	private final QpCheckrecordService qpCheckrecordService;

	private final QpCheckconfigService qpCheckconfigService;

	private final QpGascylinderstatechangesService qpGascylinderstatechangesService;

	private final QpGascylinderrecordService qpGascylinderrecordService;

	private final QpRecordService qpRecordService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param qpCheckrecord 气瓶检验记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpCheckrecordPage(Page page, QpCheckrecord qpCheckrecord) {
		qpCheckrecord.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpCheckrecordService.getQpCheckrecordPage(page, qpCheckrecord));
	}

	/**
	 * 根据气瓶检验配置项名称查询气瓶检验配置项列表
	 *
	 * @param checkname
	 * @return
	 */
	@ApiOperation(value = "根据气瓶检验配置项名称查询气瓶检验配置项列表", notes = "根据气瓶检验配置项名称查询气瓶检验配置项列表")
	@GetMapping(value = {"/getQpCheckconfigList/{checkname}", "/getQpCheckconfigList"})
	public R getQpCheckconfigList(@PathVariable(value = "checkname", required = false) String checkname) {
		QpCheckrecord qpCheckrecord = new QpCheckrecord();
		qpCheckrecord.setCheckname(checkname);
		qpCheckrecord.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpCheckrecordService.getQpCheckconfigList(qpCheckrecord));
	}

	/**
	 * 通过id查询气瓶检验记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpCheckrecordService.getById(id));
	}

	/**
	 * 新增气瓶检验记录表
	 *
	 * @param qpCheckrecord 气瓶检验记录表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶检验记录表", notes = "新增气瓶检验记录表")
	@SysLog("新增气瓶检验记录表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpcheckrecord_add')")
	public R save(@RequestBody QpCheckrecord qpCheckrecord) {
		//判断是否是黑名单
		Integer gascylinderrecordId = qpCheckrecord.getGascylinderrecordId();
		int count = qpRecordService.queryIsitablacklist(gascylinderrecordId);
		if (count > 0) {
			return R.failed().setMsg("请先定检气瓶");
		}
		QpGascylinderrecord qpGascylinder = qpGascylinderrecordService.getById(gascylinderrecordId);
		if (QpStatusEnum.qpAerateCheck(qpGascylinder.getInstate())) {
			return R.failed().setMsg("不可操作, 气瓶当前状态为：" + QpStatusEnum.getEnum(qpGascylinder.getInstate()).getDesc());
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		qpCheckrecord.setCreateat(pigxUser.getId().toString());
		qpCheckrecord.setCreatedate(LocalDateTime.now());
		qpCheckrecord.setModifyat(null);
		qpCheckrecord.setDelFlag("0");
		qpCheckrecord.setDeptId(pigxUser.getDeptId());
		qpCheckrecord.setTenantId(TenantContextHolder.getTenantId());
		qpCheckrecordService.save(qpCheckrecord);
		//新增气瓶状态变更记录
		QpCheckconfig qpCheckconfig = qpCheckconfigService.getById(qpCheckrecord.getCheckconfigId());
		QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
		/*String checktype = "";
		if (qpCheckconfig.getChecktype() == 1) {
			checktype = "充装前检查";
		} else if (qpCheckconfig.getChecktype() == 2) {
			checktype = "充装前后检查";
		}
		String result = "";
		if (qpCheckrecord.getResult() == 1) {
			result = "通过";
		} else if (qpCheckrecord.getResult() == 2) {
			result = "不通过";
		}
		String remarks = checktype + result;*/
		qpGascylinderstatechanges.setRemarks( qpCheckrecord.getRemark());
		qpGascylinderstatechanges.setRecordType(1);
		qpGascylinderstatechanges.setRecordId(qpCheckrecord.getId());
		qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
		qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
		qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
		qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderstatechanges.setDelFlag("0");
		qpGascylinderstatechangesService.save(qpGascylinderstatechanges);
		//修改气瓶档案记录状态
		QpGascylinderrecord qpGascylinderrecord = new QpGascylinderrecord();
		qpGascylinderrecord.setId(qpCheckrecord.getGascylinderrecordId());
		//空瓶已检查
		Integer status = null;
		if(qpCheckrecord.getResult()==1){

			if (qpCheckconfig.getChecktype() == 1) {
				status = EMPTY_WAIT_AERATE.getStatus();
			} else if (qpCheckconfig.getChecktype() == 2) {
				status = WEIGHT_FINISH_CHECK.getStatus();
			}
		}else  if (qpCheckrecord.getResult()==2){
			if (qpCheckconfig.getChecktype() == 1) {
				status = EMPTY_CHECK_ABNORMAL.getStatus();
			} else if (qpCheckconfig.getChecktype() == 2) {
				status = WEIGHT_CHECK_ABNORMAL.getStatus();
			}
		}
		qpGascylinderrecord.setInstate(status);
		return R.ok(qpGascylinderrecordService.updateById(qpGascylinderrecord));
	}

	/**
	 * 修改气瓶检验记录表
	 *
	 * @param qpCheckrecord 气瓶检验记录表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶检验记录表", notes = "修改气瓶检验记录表")
	@SysLog("修改气瓶检验记录表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpcheckrecord_edit')")
	public R updateById(@RequestBody QpCheckrecord qpCheckrecord) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpCheckrecord.setModifyat(pigxUser.getId().toString());
		qpCheckrecord.setModifydate(LocalDateTime.now());
		return R.ok(qpCheckrecordService.updateById(qpCheckrecord));
	}

	/**
	 * 通过id删除气瓶检验记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶检验记录表", notes = "通过id删除气瓶检验记录表")
	@SysLog("通过id删除气瓶检验记录表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpcheckrecord_del')")
	public R removeById(@PathVariable Integer id) {
		QpCheckrecord qpCheckrecord = qpCheckrecordService.getById(id);
		qpCheckrecord.setDelFlag("1");
		qpCheckrecord.setModifydate(LocalDateTime.now());
		return R.ok(qpCheckrecordService.updateById(qpCheckrecord));
	}

}
