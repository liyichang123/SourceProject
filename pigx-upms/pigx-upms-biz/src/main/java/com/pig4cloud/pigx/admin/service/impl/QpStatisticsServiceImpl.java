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
import com.pig4cloud.pigx.admin.entity.QpStatistics;
import com.pig4cloud.pigx.admin.mapper.QpStatisticsMapper;
import com.pig4cloud.pigx.admin.service.QpStatisticsService;
import com.pig4cloud.pigx.common.data.datascope.DataScope;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据统计
 *
 * @author fll
 * @date 2020-04-15 00:00:00
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpStatisticsServiceImpl implements QpStatisticsService {

	private final QpStatisticsMapper qpStatisticsMapper;

	/**
	 * 气瓶总数量
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public int getQpGascylinderNumberTotal(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderNumberTotal(qpStatistics, depts);
	}

	/**
	 * 气瓶数量统计（按月）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderNumberM(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderNumberM(qpStatistics, depts);
	}

	/**
	 * 气瓶数量统计（按年）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderNumberY(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderNumberY(qpStatistics, depts);
	}

	/**
	 * 气瓶类型统计
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderTypeStatistics(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderTypeStatistics(qpStatistics, depts);
	}

	/**
	 * 气瓶预警分页查询
	 * @param page
	 * @param QpStatistics
	 * @return
	 */
	@Override
	public IPage getQpGascylinderWarningPage(Page page, QpStatistics QpStatistics) {
		return qpStatisticsMapper.getQpGascylinderWarningPage(page, QpStatistics, new DataScope());
	}

	/**
	 * 根据父节点id查询子节点集合
	 * @param deptId
	 * @return
	 */
	@Override
	public List<Integer> getDeptsByDeptId(Integer deptId) {
		return qpStatisticsMapper.getDeptsByDeptId(deptId);
	}

	/**
	 * 气瓶分析总数量
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public int getQpGascylinderAnalyzeTotal(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderAnalyzeTotal(qpStatistics, depts);
	}

	/**
	 * 气瓶分析统计（按气瓶类型）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderAnalyzeQPLX(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderAnalyzeQPLX(qpStatistics, depts);
	}

	/**
	 * 气瓶分析统计（按充装介质）
	 * @param qpStatistics
	 * @param depts
	 * @return
	 */
	@Override
	public List<Map<String, Object>> getQpGascylinderAnalyzeCZJZ(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderAnalyzeCZJZ(qpStatistics, depts);
	}

	@Override
	public List<Map<String, Object>> getQpGascylinderAnalyzeQPZT(QpStatistics qpStatistics, List<Integer> depts) {
		return qpStatisticsMapper.getQpGascylinderAnalyzeQPZT(qpStatistics, depts);
	}

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
	@Override
	public int getQpInOutStoreCount(Integer inOutType, List<Integer> depts, Integer tenantId) {
		return qpStatisticsMapper.getQpInOutStoreCount(inOutType, depts, tenantId);
	}

}
