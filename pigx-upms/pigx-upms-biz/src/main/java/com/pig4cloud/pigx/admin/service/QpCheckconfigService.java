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

package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.QpCheckconfig;

import java.util.List;

/**
 * 气瓶检验项配置表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
public interface QpCheckconfigService extends IService<QpCheckconfig> {

	/**
	 * 分页查询
	 * @param page
	 * @param qpCheckconfig
	 * @return
	 */
	IPage getQpCheckconfigPage(Page page, QpCheckconfig qpCheckconfig);

	/**
	 * 列表查询
	 * @param qpCheckconfig
	 * @return
	 */
	List<QpCheckconfig> getQpCheckconfigList(QpCheckconfig qpCheckconfig);

	/**
	 * 通过气瓶充装介质id和检查类型查询气瓶检验项配置表
	 * @param qpCheckconfig
	 * @return
	 */
	QpCheckconfig getQpCheckconfigMapById(QpCheckconfig qpCheckconfig);
}
