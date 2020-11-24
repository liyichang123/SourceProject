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

package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpCheckconfig;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 气瓶检验项配置表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
public interface QpCheckconfigMapper extends BaseMapper<QpCheckconfig> {

	/**
	 * 分页查询
	 * @param page
	 * @param qpCheckconfig
	 * @param dataScope
	 * @return
	 */
	IPage getQpCheckconfigPage(Page page, @Param("query") QpCheckconfig qpCheckconfig, DataScope dataScope);

	/**
	 * 列表查询
	 * @param qpCheckconfig
	 * @param dataScope
	 * @return
	 */
	List<QpCheckconfig> getQpCheckconfigList(@Param("query") QpCheckconfig qpCheckconfig, DataScope dataScope);

	/**
	 * 通过气瓶充装介质id和检查类型查询气瓶检验项配置表
	 * @param qpCheckconfig
	 * @return
	 */
	QpCheckconfig getQpCheckconfigMapById(@Param("query") QpCheckconfig qpCheckconfig);

}
