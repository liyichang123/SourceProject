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

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.admin.entity.QpGascylinderstatechanges;
import com.pig4cloud.pigx.admin.service.QpGascylinderrecordService;
import com.pig4cloud.pigx.admin.service.QpGascylinderstatechangesService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.QpInOutStore;
import com.pig4cloud.pigx.admin.service.QpInOutStoreService;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



import static com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 气瓶出入库记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@RestController
@AllArgsConstructor
@RequestMapping("/qpinoutstore")
@Api(value = "qpinoutstore", tags = "气瓶出入库记录表管理")
public class QpInOutStoreController {

	private final QpInOutStoreService qpInOutStoreService;

	private final QpGascylinderstatechangesService qpGascylinderstatechangesService;

	private final QpGascylinderrecordService qpGascylinderrecordService;

	/**
	 * 分页查询
	 *
	 * @param page         分页对象
	 * @param qpInOutStore 气瓶出入库记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpInOutStorePage(Page page, QpInOutStore qpInOutStore) {
		qpInOutStore.setTenantId(TenantContextHolder.getTenantId());
		return R.ok(qpInOutStoreService.getQpInOutStorePage(page, qpInOutStore));
	}


	/**
	 * 通过id查询气瓶出入库记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpInOutStoreService.getById(id));
	}

	/**
	 * 新增气瓶出入库记录表
	 *
	 * @param qpInOutStore 气瓶出入库记录表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶出入库记录表", notes = "新增气瓶出入库记录表")
	@SysLog("新增气瓶出入库记录表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_qpinoutstore_add')")
	public R save(@RequestBody QpInOutStore qpInOutStore) {
		PigxUser pigxUser = SecurityUtils.getUser();


		//修改气瓶档案记录状态
		QpGascylinderrecord qpGascylinderrecord = new QpGascylinderrecord();
		qpGascylinderrecord.setId(qpInOutStore.getGascylinderrecordId());

		//出入库原因（1配送、2送检、3送修、4其他）
		//qpInOutStore.getInOutType()     （0：出库，1：入库）
		//qpInOutStore.getEmptyWeightState（1：空瓶，2：重瓶）
		//新增气瓶状态变更记录
		QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();

		//入库类型
		if(qpInOutStore.getInOutType() ==1 ){
			//空瓶待检测
			qpGascylinderrecord.setInstate(EMPTY_WAIT_CHECK.getStatus());
			qpGascylinderstatechanges.setState(EMPTY_WAIT_CHECK.getStatus().toString());

		}
		//出库类型  配送
		else if (qpInOutStore.getInOutType() == 0 && "1".equals(qpInOutStore.getReason())){
			//配送已出库
			qpGascylinderrecord.setInstate(DISPATCHING.getStatus());
			qpGascylinderstatechanges.setState(DISPATCHING.getStatus().toString());
		}
		//出库类型  送检
		else if (qpInOutStore.getInOutType() == 0 && "2".equals(qpInOutStore.getReason())){
			//空瓶已送检
			qpGascylinderrecord.setInstate(EMPTY_FINISH_SEND_CHECK.getStatus());
			qpGascylinderstatechanges.setState(EMPTY_FINISH_SEND_CHECK.getStatus().toString());
		}
		//出库类型  送修
		else if (qpInOutStore.getInOutType() == 0 && "3".equals(qpInOutStore.getReason())){
			//空瓶已送检
			qpGascylinderrecord.setInstate(FINISH_SEND_REPAIR.getStatus());
			qpGascylinderstatechanges.setState(FINISH_SEND_REPAIR.getStatus().toString());
		}

		//入库类型  配送
		else if (qpInOutStore.getInOutType() == 1 && "1".equals(qpInOutStore.getReason())){
			//配送已出库
			qpGascylinderrecord.setInstate(EMPTY_WAIT_CHECK.getStatus());
			qpGascylinderstatechanges.setState(EMPTY_WAIT_CHECK.getStatus().toString());
		}
		//入库类型  送检
		else if (qpInOutStore.getInOutType() == 1 && "2".equals(qpInOutStore.getReason())){
			//空瓶已送检
			qpGascylinderrecord.setInstate(EMPTY_WAIT_CHECK.getStatus());
			qpGascylinderstatechanges.setState(EMPTY_WAIT_CHECK.getStatus().toString());
		}
		//入库类型  送修
		else if (qpInOutStore.getInOutType() == 1 && "3".equals(qpInOutStore.getReason())){
			//空瓶已送检
			qpGascylinderrecord.setInstate(EMPTY_WAIT_CHECK.getStatus());
			qpGascylinderstatechanges.setState(EMPTY_WAIT_CHECK.getStatus().toString());
		}

		/*List<Integer> roles = SecurityUtils.getRoles();
		if (CollectionUtils.isNotEmpty(roles) && roles.size() == 1) {
			Integer roleId = roles.get(0);
			//加气工角色id 5，配送员角色id 7，检验员角色id 8， 当逻辑所需角色过多时，可以考虑优化定义枚举
			String reason = "";
			if (roleId == 5 && qpInOutStore.getInOutType() == 1) {
				reason = "处理";
			} else if (roleId == 7) {
				reason = "配送";
			}else if (roleId == 8) {
				reason = "送检、送修";
			}*/
			/*qpInOutStore.setReason(reason);*/
		/*}*/
		qpInOutStore.setCreateat(pigxUser.getId().toString());
		qpInOutStore.setCreatedate(LocalDateTime.now());
		qpInOutStore.setModifyat(null);
		qpInOutStore.setDelFlag("0");
		qpInOutStore.setDeptId(pigxUser.getDeptId());
		qpInOutStore.setTenantId(TenantContextHolder.getTenantId());
		qpInOutStoreService.save(qpInOutStore);


		/*String empty_weight_state = "";
		if (qpInOutStore.getEmptyWeightState() == 1) {
			empty_weight_state = "空瓶";
		} else if (qpInOutStore.getEmptyWeightState() == 2) {
			empty_weight_state = "重瓶";
		}
		String in_out_type = "";
		if (qpInOutStore.getInOutType() == 0) {
			in_out_type = "出库";
		} else if (qpInOutStore.getInOutType() == 1) {
			in_out_type = "入库";
		}
		String remarks = empty_weight_state + in_out_type;
		qpGascylinderstatechanges.setRemarks(remarks);*/
		qpGascylinderstatechanges.setRecordType(2);
		qpGascylinderstatechanges.setRecordId(qpInOutStore.getId());
		qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
		qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
		qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
		qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderstatechanges.setDelFlag("0");
		qpGascylinderstatechangesService.save(qpGascylinderstatechanges);

		return R.ok(qpGascylinderrecordService.updateById(qpGascylinderrecord));
	}

	/**
	 * 修改气瓶出入库记录表
	 *
	 * @param qpInOutStore 气瓶出入库记录表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶出入库记录表", notes = "修改气瓶出入库记录表")
	@SysLog("修改气瓶出入库记录表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_qpinoutstore_edit')")
	public R updateById(@RequestBody QpInOutStore qpInOutStore) {
		PigxUser pigxUser = SecurityUtils.getUser();
		qpInOutStore.setModifyat(pigxUser.getId().toString());
		qpInOutStore.setModifydate(LocalDateTime.now());
		return R.ok(qpInOutStoreService.updateById(qpInOutStore));
	}

	/**
	 * 通过id删除气瓶出入库记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶出入库记录表", notes = "通过id删除气瓶出入库记录表")
	@SysLog("通过id删除气瓶出入库记录表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpinoutstore_del')")
	public R removeById(@PathVariable Integer id) {
		QpInOutStore qpInOutStore = qpInOutStoreService.getById(id);
		qpInOutStore.setDelFlag("1");
		qpInOutStore.setModifydate(LocalDateTime.now());
		return R.ok(qpInOutStoreService.updateById(qpInOutStore));
	}

}
