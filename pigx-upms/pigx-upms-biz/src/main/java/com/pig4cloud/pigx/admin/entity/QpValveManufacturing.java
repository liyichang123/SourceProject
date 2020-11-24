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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 气瓶阀门制造信息
 *
 * @author wh
 * @date 2020-04-22 22:51:43
 */
@Data
@TableName("qp_valve_manufacturing")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶阀门制造信息")
public class QpValveManufacturing extends Model<QpValveManufacturing> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="")
    private Integer id;
	/**
	 * 生产厂商id
	 */
	@ApiModelProperty(value="生产厂商id")
	private Integer manufactoryId;
    /**
     * 阀门制造单位名称
     */
    @ApiModelProperty(value="阀门制造单位名称")
    private String name;
    /**
     * 阀门型号
     */
    @ApiModelProperty(value="阀门型号")
    private String model;
    /**
     * 型式试验证书
     */
    @ApiModelProperty(value="型式试验证书")
    private String ceTypeCertificate;

	/**
	 *
	 */
	@ApiModelProperty(value="")
	private String createat;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private LocalDateTime createdate;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private String modifyat;
	/**
	 *
	 */
	@ApiModelProperty(value="")
	private LocalDateTime modifydate;
	/**
	 * 0：否 1：是
	 */
	@ApiModelProperty(value="0：否 1：是")
	private String delFlag;
	/**
	 * 部门ID
	 */
	@ApiModelProperty(value="部门ID")
	private Integer deptId;
	/**
	 * 所属租户
	 */
	@ApiModelProperty(value="所属租户",hidden=true)
	private Integer tenantId;
    }
