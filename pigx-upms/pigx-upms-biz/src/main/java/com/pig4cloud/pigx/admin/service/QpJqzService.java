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

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.QpJqz;

import java.util.List;

/**
 * 加气站表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
public interface QpJqzService extends IService<QpJqz> {

	/**
	 * 根据部门id获取加气站信息 <br>
	 *
	 * @param deptId : 部门id
	 * @Return: List<QpJqz>
	 * @Author: gmx
	 * @Date: 2020/5/9 10:00
	 */
	List<QpJqz> getJqzByDeptId(Integer deptId);
}
