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
import com.pig4cloud.pigx.admin.entity.QpCheckrecord;
import com.pig4cloud.pigx.admin.mapper.QpCheckrecordMapper;
import com.pig4cloud.pigx.admin.service.QpCheckrecordService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 气瓶检验记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpCheckrecordServiceImpl extends ServiceImpl<QpCheckrecordMapper, QpCheckrecord> implements QpCheckrecordService {

	private final QpCheckrecordMapper qpCheckrecordMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpCheckrecord
	 * @return
	 */
	@Override
	public IPage getQpCheckrecordPage(Page page, QpCheckrecord qpCheckrecord) {
		return qpCheckrecordMapper.getQpCheckrecordPage(page, qpCheckrecord, new DataScope());
	}

	/**
	 * 根据气瓶检验配置项名称查询气瓶检验配置项列表
	 * @param qpCheckrecord
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpCheckconfigList(QpCheckrecord qpCheckrecord) {
		return qpCheckrecordMapper.getQpCheckconfigList(qpCheckrecord, new DataScope());
	}

	@Override
	public List<Map<String, Object>> getQpCheckRecordList(QpCheckrecord qpCheckrecord) {
		return qpCheckrecordMapper.getQpCheckRecordList(qpCheckrecord, new DataScope());
	}

}
