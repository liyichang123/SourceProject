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
import com.pig4cloud.pigx.admin.entity.QpRecord;
import com.pig4cloud.pigx.admin.mapper.QpRecordMapper;
import com.pig4cloud.pigx.admin.service.QpRecordService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 气瓶充装记录表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpRecordServiceImpl extends ServiceImpl<QpRecordMapper, QpRecord> implements QpRecordService {

	private final QpRecordMapper qpRecordMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpRecord
	 * @return
	 */
	@Override
	public IPage getQpRecordPage(Page page, QpRecord qpRecord) {
		return qpRecordMapper.getQpRecordPage(page, qpRecord, new DataScope());
	}

	/**
	 * 根据气瓶钢印编号查询气瓶档案记录列表
	 * @param qpRecord
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderrecordList(QpRecord qpRecord) {
		return qpRecordMapper.getQpGascylinderrecordList(qpRecord, new DataScope());
	}

	@Override
	public List<QpRecord> getQprrecordListByCode(QpRecord qpRecord) {
		return qpRecordMapper.getQprrecordListByCode(qpRecord);
	}

	/**
	 * 根据气瓶id查询是否是黑名单
	 * @param gascylinderrecordId
	 * @return
	 */
	@Override
	public int queryIsitablacklist(int gascylinderrecordId) {
		return qpRecordMapper.queryIsitablacklist(gascylinderrecordId);
	}

}
