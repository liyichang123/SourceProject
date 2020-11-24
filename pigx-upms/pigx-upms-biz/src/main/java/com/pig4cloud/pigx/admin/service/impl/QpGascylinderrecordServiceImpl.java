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
import com.pig4cloud.pigx.admin.dto.QpRecordReqDto;
import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.admin.mapper.QpGascylinderrecordMapper;
import com.pig4cloud.pigx.admin.service.QpGascylinderrecordService;
import com.pig4cloud.pigx.admin.vo.WtQpArchivesVo;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 气瓶档案记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Service
@Slf4j
@AllArgsConstructor
public class QpGascylinderrecordServiceImpl extends ServiceImpl<QpGascylinderrecordMapper, QpGascylinderrecord> implements QpGascylinderrecordService {
	private final QpGascylinderrecordMapper qpGascylinderrecordMapper;

	@Override
	public IPage getPage(Page page, QpGascylinderrecord qpGascylinderrecord) {
		return qpGascylinderrecordMapper.getPage(page,qpGascylinderrecord,new DataScope());
	}

	@Override
	public QpGascylinderrecord getGascylinderrecordByCode(String code, String codeType) {
		if (StringUtils.isBlank(code)){
			return null;
		}
		return qpGascylinderrecordMapper.getGascylinderrecordByCode(code, codeType);
	}

	@Override
	public IPage warninglist(Page page,QpGascylinderrecord qpGascylinderrecord) {
		return qpGascylinderrecordMapper.warninglist(page,qpGascylinderrecord,new DataScope());
	}

	@Override
	public IPage<Map<String, Object>> blacklist(Page page, QpRecordReqDto qpRecordReqDto) {
		return qpGascylinderrecordMapper.blacklist(page,qpRecordReqDto,new DataScope());
	}

	@Override
	public IPage sleeplist(Page page, QpGascylinderrecord qpGascylinderrecord) {
		return qpGascylinderrecordMapper.sleeplist(page,qpGascylinderrecord,new DataScope());
	}

	@Override
	public Integer saveRecord(QpGascylinderrecord qpGascylinderrecord){
		qpGascylinderrecordMapper.insert(qpGascylinderrecord);
		return qpGascylinderrecord.getId();
	}

	@Override
	public List<QpGascylinderrecord> getByCodes(String[] codes) {
		return qpGascylinderrecordMapper.getByCodes(codes);
	}

	@Override
	public List<QpGascylinderrecord> getExcelRecordVoByIds(String ids) {
		String[] id = ids.split(",");
		return qpGascylinderrecordMapper.getExcelRecordVoByIds(id);
	}
}
