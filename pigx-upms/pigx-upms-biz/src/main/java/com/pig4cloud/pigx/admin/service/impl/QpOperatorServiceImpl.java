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
package com.pig4cloud.pigx.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.dto.UserDTO;
import com.pig4cloud.pigx.admin.api.entity.SysUser;
import com.pig4cloud.pigx.admin.entity.QpOperator;
import com.pig4cloud.pigx.admin.mapper.QpOperatorMapper;
import com.pig4cloud.pigx.admin.service.QpOperatorService;
import com.pig4cloud.pigx.admin.service.SysUserService;
import com.pig4cloud.pigx.common.core.constant.enums.OperatorTypeEnum;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_DEL;
import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_NORMAL;
import static com.pig4cloud.pigx.common.core.constant.SecurityConstants.DEFAULT_PASSWORD;

/**
 * 操作人员管理表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Slf4j
@Service
@AllArgsConstructor
public class QpOperatorServiceImpl extends ServiceImpl<QpOperatorMapper, QpOperator> implements QpOperatorService {

	private final QpOperatorMapper qpOperatorMapper;
	private final SysUserService sysUserService;

	/**
	 * 通过用户id,操作员类型 获取操作员信息 <br>
	 *
	 * @param userId : 当前登录用户id
	 * @param type   : 要查询的操作员类型
	 * @Return: List<QpOperator>
	 * @Author: gmx
	 * @Date: 2020/4/21 17:28
	 */
	@Override
	public List<QpOperator> getQpOperatorByUserId(Integer userId, Integer type) {
		QueryWrapper<QpOperator> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_id", userId).eq("type", type);
		return qpOperatorMapper.selectList(queryWrapper);
	}

	/**
	 * 新增操作员 <br>
	 *
	 * @param qpOperator : 操作员信息
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/23 17:00
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveQpOperator(QpOperator qpOperator) {
		qpOperatorMapper.insert(qpOperator);
		//操作人员添加成功，同步去注册被添加的这个操作员
		List<Integer> role = new ArrayList<>();
		role.add(OperatorTypeEnum.getEnum(qpOperator.getType()).getRoleId());
		UserDTO user = new UserDTO();
		user.setRole(role);
		user.setUsername(qpOperator.getPhone());
		user.setPassword(DEFAULT_PASSWORD);
		user.setPhone(qpOperator.getPhone());
		user.setDeptId(qpOperator.getDeptId());
		user.setCreateTime(qpOperator.getCreatedate());
		Integer userId = sysUserService.saveUser(user);
		//注册完成回填userId
		qpOperator.setUserId(userId);
		int result = qpOperatorMapper.updateById(qpOperator);
		return result == 1;
	}

	/**
	 * 通过id 逻辑删除操作员，并同步逻辑删除sys_user表用户信息 <br>
	 *
	 * @param id : 操作员id
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/25 11:15
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean deleteOperator(Integer id) {
		QpOperator qpOperator = qpOperatorMapper.selectById(id);
		if (Objects.isNull(qpOperator)){
			return Boolean.FALSE;
		}
		Integer userId = qpOperator.getUserId();
		qpOperator.setDelFlag(STATUS_DEL);
		qpOperator.setModifyat(SecurityUtils.getUser().getUsername());
		qpOperator.setModifydate(LocalDateTime.now());
		qpOperatorMapper.updateById(qpOperator);
		SysUser user = sysUserService.getById(userId);
		if (Objects.isNull(user)){
			return Boolean.FALSE;
		}
		return sysUserService.deleteUserById(user);
	}

	/**
	 * 修改操作员 <br>
	 *
	 * @param qpOperator : 操作员信息
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/29 10:47
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateOperator(QpOperator qpOperator) {
		String phone = qpOperator.getPhone();
		List<Integer> role = new ArrayList<>();
		role.add(OperatorTypeEnum.getEnum(qpOperator.getType()).getRoleId());
		UserDTO user = new UserDTO();
		user.setUserId(qpOperator.getUserId());
		user.setRole(role);
		user.setUsername(phone);
		user.setPhone(phone);
		user.setPassword(qpOperator.getNewPassword());
		sysUserService.updateUser(user);
		qpOperatorMapper.updateById(qpOperator);
		return Boolean.TRUE;
	}
}
