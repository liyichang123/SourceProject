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
import com.pig4cloud.pigx.admin.entity.QpGascylindertype;
import com.pig4cloud.pigx.admin.mapper.QpGascylindertypeMapper;
import com.pig4cloud.pigx.admin.service.QpGascylindertypeService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 气瓶类型表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpGascylindertypeServiceImpl extends ServiceImpl<QpGascylindertypeMapper, QpGascylindertype> implements QpGascylindertypeService {

	private final QpGascylindertypeMapper qpGascylindertypeMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpGascylindertype
	 * @return
	 */
	@Override
	public IPage getQpGascylindertypePage(Page page, QpGascylindertype qpGascylindertype) {
		DataScope dataScope = new DataScope();
		List<Integer> depts = new ArrayList<>();
		depts.add(10);
		depts.add(qpGascylindertype.getDeptId());
		dataScope.setDeptIds(depts);
		return qpGascylindertypeMapper.getQpGascylindertypePage(page, qpGascylindertype, dataScope);
	}

}
