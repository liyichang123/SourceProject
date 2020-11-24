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

package com.pig4cloud.pigx.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 数据统计
 *
 * @author fll
 * @date 2020-04-15 00:00:00
 */
@Data
public class QpStatistics {
private static final long serialVersionUID = 1L;
	/**
	 * 分析类型
	 */
	private String analyzeType;
    /**
     * 开始时间
     */
    private String startTime;
    /**
     * 结束时间
     */
    private String endTime;
	/**
	 * 电子标签类型（1：芯片,2：二维码）
	 */
	private String electroniclabelType;
	/**
	 * 制造厂家id
	 */
	private Integer manufactoryId;
	/**
	 * 制造许可id
	 */
	private Integer manufactorypermissionId;
    /**
     * 部门ID
     */
    private Integer deptId;
    /**
     * 所属租户
     */
    private Integer tenantId;
    /**
     * 预警类型 1：临近超期，  2：临近报废
     */
    private String warningType = "1";
	/**
	 * 分析天数 （只能为整数天）
	 */
    private Integer analyzeDays = 30;
    }
