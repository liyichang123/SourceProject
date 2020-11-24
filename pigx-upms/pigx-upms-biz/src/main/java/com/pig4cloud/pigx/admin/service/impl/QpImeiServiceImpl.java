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
import com.pig4cloud.pigx.admin.entity.QpImei;
import com.pig4cloud.pigx.admin.mapper.QpImeiMapper;
import com.pig4cloud.pigx.admin.service.QpImeiService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * IMEI设备表
 *
 * @author fll
 * @date 2020-05-22 15:08:34
 */
@Service
@AllArgsConstructor
public class QpImeiServiceImpl extends ServiceImpl<QpImeiMapper, QpImei> implements QpImeiService {

	private final QpImeiMapper qpImeiMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpImei
	 * @return
	 */
	@Override
	public IPage getQpImeiPage(Page page, QpImei qpImei) {
		return qpImeiMapper.getQpImeiPage(page, qpImei, new DataScope());
	}

}
