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
import com.pig4cloud.pigx.admin.entity.QpStore;
import com.pig4cloud.pigx.admin.mapper.QpStoreMapper;
import com.pig4cloud.pigx.admin.service.QpStoreService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

/**
 * 气瓶仓库表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpStoreServiceImpl extends ServiceImpl<QpStoreMapper, QpStore> implements QpStoreService {

	private final QpStoreMapper qpStoreMapper;

	/**
	 * 分页查询
	 * @param page
	 * @param qpStore
	 * @return
	 */
	@Override
	public IPage getQpStorePage(Page page, QpStore qpStore) {
		return qpStoreMapper.getQpStorePage(page, qpStore, new DataScope());
	}

	/**
	 * 列表查询
	 * @param qpStore
	 * @return
	 */
	@Override
	public List<QpStore> getQpStoreList(QpStore qpStore) {
		return qpStoreMapper.getQpStoreList(qpStore, new DataScope());
	}

}
