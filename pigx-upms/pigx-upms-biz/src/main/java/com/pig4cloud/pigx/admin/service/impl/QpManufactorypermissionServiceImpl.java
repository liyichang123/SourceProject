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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.QpManufactorypermission;
import com.pig4cloud.pigx.admin.mapper.QpManufactorypermissionMapper;
import com.pig4cloud.pigx.admin.service.QpManufactorypermissionService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 制造许可证表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpManufactorypermissionServiceImpl extends ServiceImpl<QpManufactorypermissionMapper, QpManufactorypermission> implements QpManufactorypermissionService {

	private final QpManufactorypermissionMapper qpManufactorypermissionMapper;

	@Override
	public List<QpManufactorypermission> getQpManufactorypermissionListById(QpManufactorypermission qpManufactorypermission) {
		return qpManufactorypermissionMapper.getQpManufactorypermissionListById(qpManufactorypermission);
	}
}
