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
import com.pig4cloud.pigx.admin.entity.QpCheckconfig;
import com.pig4cloud.pigx.admin.mapper.QpCheckconfigMapper;
import com.pig4cloud.pigx.admin.service.QpCheckconfigService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 气瓶检验项配置表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpCheckconfigServiceImpl extends ServiceImpl<QpCheckconfigMapper, QpCheckconfig> implements QpCheckconfigService {

	private final QpCheckconfigMapper qpCheckconfigMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpCheckconfig
	 * @return
	 */
	@Override
	public IPage getQpCheckconfigPage(Page page, QpCheckconfig qpCheckconfig) {
		return qpCheckconfigMapper.getQpCheckconfigPage(page, qpCheckconfig, new DataScope());
	}

	/**
	 * 列表查询
	 * @param qpCheckconfig
	 * @return
	 */
	@Override
	public List<QpCheckconfig> getQpCheckconfigList(QpCheckconfig qpCheckconfig) {
		return qpCheckconfigMapper.getQpCheckconfigList(qpCheckconfig, new DataScope());
	}

	/**
	 * 通过气瓶充装介质id和检查类型查询气瓶检验项配置表
	 * @param qpCheckconfig
	 * @return
	 */
	@Override
	public QpCheckconfig getQpCheckconfigMapById(QpCheckconfig qpCheckconfig) {
		return qpCheckconfigMapper.getQpCheckconfigMapById(qpCheckconfig);
	}

}
