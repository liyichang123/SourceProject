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
import com.pig4cloud.pigx.admin.entity.QpOperator;

import java.util.List;

/**
 * 操作人员管理表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
public interface QpOperatorService extends IService<QpOperator> {

	/**
	 * 通过用户id,操作员类型 获取操作员信息 <br>
	 *
	 * @param userId : 当前登录用户id
	 * @param type   : 要查询的操作员类型  1：加气工  2:配送员，3检验员
	 * @Return: List<QpOperator>
	 * @Author: gmx
	 * @Date: 2020/4/21 17:28
	 */
	List<QpOperator> getQpOperatorByUserId(Integer userId, Integer type);

	/**
	 * 新增操作员 <br>
	 *
	 * @param qpOperator : 操作员信息
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/23 17:00
	 */
	boolean saveQpOperator(QpOperator qpOperator);

	/**
	 * 通过id 逻辑删除操作员，并同步逻辑删除sys_user表用户信息 <br>
	 *
	 * @param id : 操作员id
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/25 11:15
	 */
	boolean deleteOperator(Integer id);

	/**
	 * 修改操作员 <br>
	 *
	 * @param qpOperator : 操作员信息
	 * @Return: boolean
	 * @Author: gmx
	 * @Date: 2020/4/29 10:47
	 */
	boolean updateOperator(QpOperator qpOperator);
}
