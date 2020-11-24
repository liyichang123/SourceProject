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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.QpMaterial;
import com.pig4cloud.pigx.admin.mapper.QpMaterialMapper;
import com.pig4cloud.pigx.admin.service.QpMaterialService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 充装介质表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpMaterialServiceImpl extends ServiceImpl<QpMaterialMapper, QpMaterial> implements QpMaterialService {

	private final QpMaterialMapper qpMaterialMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpMaterial
	 * @return
	 */
	@Override
	public IPage getQpMaterialPage(Page page, QpMaterial qpMaterial) {
		DataScope dataScope = new DataScope();
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(qpMaterial.getDeptId());
		dataScope.setDeptIds(depts);
		return qpMaterialMapper.getQpMaterialPage(page, qpMaterial, dataScope);
	}

}
