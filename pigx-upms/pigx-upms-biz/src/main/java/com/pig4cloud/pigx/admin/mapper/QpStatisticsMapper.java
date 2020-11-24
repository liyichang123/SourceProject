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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpStatistics;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据统计
 *
 * @author fll
 * @date 2020-04-15 00:00:00
 */
public interface QpStatisticsMapper {

	/**
	 * 气瓶总数量
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	int getQpGascylinderNumberTotal(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶数量统计（按月）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderNumberM(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶数量统计（按年）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderNumberY(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶类型统计
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderTypeStatistics(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶预警分页查询
	 * @param page
	 * @param QpStatistics
	 * @param dataScope
	 * @return
	 */
	IPage getQpGascylinderWarningPage(Page page, @Param("query") QpStatistics QpStatistics, DataScope dataScope);
	/**
	 * 根据父节点id查询子节点集合
	 * @param deptId
	 * @return
	 */
	List<Integer> getDeptsByDeptId(Integer deptId);

	/**
	 * 气瓶分析总数量
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	int getQpGascylinderAnalyzeTotal(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶分析统计（按气瓶类型）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderAnalyzeQPLX(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶分析统计（按充装介质）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderAnalyzeCZJZ(@Param("query") QpStatistics qpStatistics, @Param("depts") List<Integer> depts);

	/**
	 * 气瓶分析统计（按气瓶状态）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	List<Map<String, Object>> getQpGascylinderAnalyzeQPZT(@Param("query") QpStatistics qpStatistics,  @Param("depts") List<Integer> depts);

	/**
	 * 气瓶分析统计（气瓶出入库状态）
	 *
	 * @param inOutType : 0 出库，1 入库
	 * @param depts : 部门id集合
	 * @param tenantId :
	 * @Return: int
	 * @Author: gmx
	 * @Date: 2020/5/9 11:28
	 */
	int getQpInOutStoreCount(@Param("inOutType") Integer inOutType, @Param("depts") List<Integer> depts, @Param("tenantId") Integer tenantId);
}
