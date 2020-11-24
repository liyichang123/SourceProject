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
import com.pig4cloud.pigx.admin.entity.QpGascylinderlabelchanges;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * 气瓶标签变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
public interface QpGascylinderlabelchangesMapper extends BaseMapper<QpGascylinderlabelchanges> {

	/**
	 * 通过电子标签气瓶档案记录
	 * @param electroniclabel
	 * @return
	 */
	Map<String, Object> getQpgascylinderrecord(@Param("electroniclabel") String electroniclabel, @Param("codeType") String codeType);

}
