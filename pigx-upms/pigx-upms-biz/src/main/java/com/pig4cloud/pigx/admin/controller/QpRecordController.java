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
import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.admin.entity.QpGascylinderstatechanges;
import com.pig4cloud.pigx.admin.service.QpGascylinderrecordService;
import com.pig4cloud.pigx.admin.service.QpGascylinderstatechangesService;
import com.pig4cloud.pigx.admin.service.WtService;
import com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpRecord;
import com.pig4cloud.pigx.admin.service.QpRecordService;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum.WEIGHT_WAIT_CHECK;


/**
 * 气瓶充装记录表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qprecord")
@Slf4j
@Api(value = "qprecord", tags = "气瓶充装记录表管理")
public class QpRecordController {

	private final QpRecordService qpRecordService;

	private final QpGascylinderstatechangesService qpGascylinderstatechangesService;

	private final QpGascylinderrecordService qpGascylinderrecordService;

	private final WtService wtService;

	/**
	 * 分页查询
	 *
	 * @param page     分页对象
	 * @param qpRecord 气瓶充装记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpRecordPage(Page page, QpRecord qpRecord) {
		qpRecord.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpRecordService.getQpRecordPage(page, qpRecord));
	}

	/**
	 * 根据气瓶钢印编号查询气瓶档案记录列表
	 *
	 * @param gascylindercode
	 * @return
	 */
	@ApiOperation(value = "根据气瓶钢印编号查询气瓶档案记录列表", notes = "根据气瓶钢印编号查询气瓶档案记录列表")
	@GetMapping(value = {"/getQpGascylinderrecordList/{gascylindercode}", "/getQpGascylinderrecordList"})
	public R getQpGascylinderrecordList(@PathVariable(value = "gascylindercode", required = false) String gascylindercode) {
		QpRecord qpRecord = new QpRecord();
		qpRecord.setGascylindercode(gascylindercode);
		qpRecord.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpRecordService.getQpGascylinderrecordList(qpRecord));
	}

	/**
	 * 通过id查询气瓶充装记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpRecordService.getById(id));
	}

	/**
	 * 新增气瓶充装记录表
	 *
	 * @param qpRecord 气瓶充装记录表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶充装记录表", notes = "新增气瓶充装记录表")
	@SysLog("新增气瓶充装记录表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qprecord_add')")
	public R save(@RequestBody QpRecord qpRecord) {
		//判断是否是黑名单
		Integer gascylinderrecordId = qpRecord.getGascylinderrecordId();
		int count = qpRecordService.queryIsitablacklist(gascylinderrecordId);
		if (count > 0) {
			return R.failed().setMsg("请先定检气瓶");
		}
		QpGascylinderrecord qpGascylinder = qpGascylinderrecordService.getById(gascylinderrecordId);
		if (QpStatusEnum.qpAerateCheck(qpGascylinder.getInstate())) {
			return R.failed().setMsg("不可操作, 气瓶当前状态为：" + QpStatusEnum.getEnum(qpGascylinder.getInstate()).getDesc());
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		qpRecord.setCreateat(pigxUser.getId().toString());
		qpRecord.setCreatedate(LocalDateTime.now());
		qpRecord.setModifyat(null);
		qpRecord.setDelFlag("0");
		qpRecord.setDeptId(pigxUser.getDeptId());
		qpRecord.setTenantId(TenantContextHolder.getTenantId());
		qpRecordService.save(qpRecord);

		//将充装记录上传万通
		if (pigxUser.getDeptId() == 29) {
			List<QpRecord> list = new ArrayList<>();
			list.add(qpRecord);
			wtService.addWtRecord(list);
		}
		//新增气瓶状态变更记录
		QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
		qpGascylinderstatechanges.setState(WEIGHT_WAIT_CHECK.getStatus().toString());
		qpGascylinderstatechanges.setRecordType(3);
		qpGascylinderstatechanges.setRecordId(qpRecord.getId());
		qpGascylinderstatechanges.setCreateat(pigxUser.getId().toString());
		qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
		qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
		qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderstatechanges.setDelFlag("0");
		qpGascylinderstatechangesService.save(qpGascylinderstatechanges);
		//修改气瓶档案记录状态
		QpGascylinderrecord qpGascylinderrecord = new QpGascylinderrecord();
		qpGascylinderrecord.setId(qpRecord.getGascylinderrecordId());
		qpGascylinderrecord.setInstate(WEIGHT_WAIT_CHECK.getStatus());
		return R.ok(qpGascylinderrecordService.updateById(qpGascylinderrecord));
	}

	/**
	 * 修改气瓶充装记录表
	 *
	 * @param qpRecord 气瓶充装记录表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶充装记录表", notes = "修改气瓶充装记录表")
	@SysLog("修改气瓶充装记录表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qprecord_edit')")
	public R updateById(@RequestBody QpRecord qpRecord) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpRecord.setModifyat(pigxUser.getId().toString());
		qpRecord.setModifydate(LocalDateTime.now());
		qpRecord.setDeptId(pigxUser.getDeptId());
		qpRecord.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpRecordService.updateById(qpRecord));
	}

	/**
	 * 通过id删除气瓶充装记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶充装记录表", notes = "通过id删除气瓶充装记录表")
	@SysLog("通过id删除气瓶充装记录表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qprecord_del')")
	public R removeById(@PathVariable Integer id) {
		QpRecord qpRecord = qpRecordService.getById(id);
		qpRecord.setDelFlag("1");
		return R.ok(qpRecordService.updateById(qpRecord));
	}

	/**
	 * 处理历史充装记录数据
	 *
	 * @Return: void
	 * @Author: gmx
	 * @Date: 2020/9/22 16:34
	 */
	@Inner(value = false)
	@GetMapping("/addGasRecordAll")
	public R<Object> addGasRecord() {
		log.info("addGasRecord_all 处理历史数据任务开始 -- - -");
		QueryWrapper<QpRecord> query = new QueryWrapper<>();
		query.eq("del_flag", "0");
		query.eq("is_perform", "0");
		query.eq("dept_id", 29);
		List<QpRecord> list = qpRecordService.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			wtService.addWtRecord(list);
		}
		log.info("addGasRecord_all 处理历史数据任务结束 -- - -");
		return R.ok();
	}
}
