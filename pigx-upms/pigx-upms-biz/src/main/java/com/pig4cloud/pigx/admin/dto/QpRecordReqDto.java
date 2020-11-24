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

package com.pig4cloud.pigx.admin.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 气瓶档案记录相关请求dto
 *
 * @author gmx
 * @date 2020-04-27 19:52:19
 */
@Data
@ApiModel(value = "气瓶档案相关请求dto")
public class QpRecordReqDto {
private static final long serialVersionUID = 1L;

    /**
     * 气瓶钢印编号（瓶身编号）
     */
    private String gascylindercode;

    /**
     * 制造厂家id
     */
    private Integer manufactoryId;
    /**
     * 制造许可id
     */
    private Integer manufactorypermissionId;
    /**
     * 气瓶类型id
     */
    private Integer gascylindertypeId;
    /**
     * 充装介质id
     */
    private Integer materialId;
    /**
     * 部门ID
     */
    private Integer deptId;
    /**
     * 所属租户
     */
    private Integer tenantId;
	/**
	 * 黑名单原因类型：  1：超期未检   2：超过使用年限
	 */
	@TableField(exist = false)
    private String blackReason;

	@TableField(exist = false)
	private String warningType;

}
