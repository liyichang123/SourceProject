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
import com.pig4cloud.pigx.admin.entity.QpJqz;
import com.pig4cloud.pigx.admin.mapper.QpJqzMapper;
import com.pig4cloud.pigx.admin.service.QpJqzService;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 加气站表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@AllArgsConstructor
@Slf4j
@Service
public class QpJqzServiceImpl extends ServiceImpl<QpJqzMapper, QpJqz> implements QpJqzService {

	private QpJqzMapper qpJqzMapper;

	/**
	 * 根据部门id获取加气站信息 <br>
	 *
	 * @param deptId : 部门id
	 * @Return: List<QpJqz>
	 * @Author: gmx
	 * @Date: 2020/5/9 10:00
	 */
	@Override
	public List<QpJqz> getJqzByDeptId(Integer deptId) {
		QueryWrapper<QpJqz> queryWrapper = new QueryWrapper<QpJqz>();
		queryWrapper.eq("del_flag", "0");
		queryWrapper.eq("dept_id", deptId);
		return qpJqzMapper.selectList(queryWrapper);
	}
}
