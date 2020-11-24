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
import com.pig4cloud.pigx.admin.entity.QpGascylinderlabelchanges;
import com.pig4cloud.pigx.admin.mapper.QpGascylinderlabelchangesMapper;
import com.pig4cloud.pigx.admin.service.QpGascylinderlabelchangesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 气瓶标签变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpGascylinderlabelchangesServiceImpl extends ServiceImpl<QpGascylinderlabelchangesMapper, QpGascylinderlabelchanges> implements QpGascylinderlabelchangesService {

	private final QpGascylinderlabelchangesMapper qpGascylinderlabelchangesMapper;

	/**
	 * 通过电子标签气瓶档案记录
	 * @param electroniclabel
	 * @return
	 */
	@Override
	public Map<String, Object> getQpgascylinderrecord(String electroniclabel, String codeType) {
		return qpGascylinderlabelchangesMapper.getQpgascylinderrecord(electroniclabel, codeType);
	}

}
