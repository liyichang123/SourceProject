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

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 气瓶出入库记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_in_out_store")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶出入库记录表")
public class QpInOutStore extends Model<QpInOutStore> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 气瓶档案记录id
     */
    @ApiModelProperty(value="气瓶档案记录id")
    private Integer gascylinderrecordId;
	/**
	 * 气瓶钢印编号
	 */
	@TableField(exist = false)
	private String gascylindercode;
    /**
     * 气瓶仓库id
     */
    @ApiModelProperty(value="气瓶仓库id")
    private Integer qpStoreId;
    /**
     * 气瓶空重状态（1：空瓶，2：重瓶）
     */
    @ApiModelProperty(value="气瓶空重状态（1：空瓶，2：重瓶）")
    private Integer emptyWeightState;
    /**
     * 气瓶出入库类型（0：出库，1：入库）
     */
    @ApiModelProperty(value="气瓶出入库类型（0：出库，1：入库）")
    private Integer inOutType;
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
	/**
	 * 出入库原因
	 */
	@ApiModelProperty(value="出入库原因")
	private String reason;
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarks;
	/**
	 * 经度
	 */
	@ApiModelProperty(value="经度")
	private String longitude;
	/**
	 * 维度
	 */
	@ApiModelProperty(value="维度")
	private String dimension;

	/**
	 * 加气站名称
	 */
	@TableField(exist = false)
	private String jqzName;

	/**
	 * 标签类型
	 */
	@TableField(exist = false)
	private String electroniclabeltype;

	/**
	 * 标签码
	 */
	@TableField(exist = false)
	private String electroniclabel;
}
