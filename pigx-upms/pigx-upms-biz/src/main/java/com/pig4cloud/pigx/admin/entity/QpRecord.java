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
 * 气瓶充装记录表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Data
@TableName("qp_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶充装记录表")
public class QpRecord extends Model<QpRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * 自增id
	 */
	@TableId
	@ApiModelProperty(value = "自增id")
	private Integer id;
	/**
	 * 气瓶档案记录id
	 */
	@ApiModelProperty(value = "气瓶档案记录id")
	private Integer gascylinderrecordId;
	/**
	 * 气瓶钢印编号
	 */
	@TableField(exist = false)
	private String gascylindercode;
	/**
	 * 充装量
	 */
	@ApiModelProperty(value = "充装量")
	private String fillingquantity;
	/**
	 * 充装压力
	 */
	@ApiModelProperty(value = "充装压力")
	private String fillingpressure;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String createat;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private LocalDateTime createdate;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String modifyat;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private LocalDateTime modifydate;
	/**
	 * 0：否 1：是
	 */
	@ApiModelProperty(value = "0：否 1：是")
	private String delFlag;
	/**
	 * 部门ID
	 */
	@ApiModelProperty(value = "部门ID")
	private Integer deptId;
	/**
	 * 所属租户
	 */
	@ApiModelProperty(value = "所属租户", hidden = true)
	private Integer tenantId;
	/**
	 * 标记
	 */
	@ApiModelProperty(value = "标记")
	private String sigil;
	/**
	 * 是否执行过万通的上传
	 */
	@ApiModelProperty(value = "是否执行过万通的上传")
	private String isPerform;
	/**
	 * 充装单位（kg、m³、MPa）
	 */
	@ApiModelProperty(value = "充装单位（kg、m³、MPa）")
	private String fillingUnit;
	/**
	 * 充装前检查结果
	 */
	@TableField(exist = false)
	private String beforeRes;
	/**
	 * 充装后检查结果
	 */
	@TableField(exist = false)
	private String afterRes;

	/**
	 * 充装前 检查人
	 */
	@TableField(exist = false)
	private String beforePeople;

	/**
	 * 充装前 检查时间
	 */
	@TableField(exist = false)
	private LocalDateTime beforeDate;

	/**
	 * 充装后检查人
	 */
	@TableField(exist = false)
	private String afterPeople;

	/**
	 * 充装后检查时间
	 */
	@TableField(exist = false)
	private LocalDateTime afterDate;

	/**
	 * 加气站名称
	 */
	@TableField(exist = false)
	private String jqzName;

	/**
	 * 充装介质
	 */
	@TableField(exist = false)
	private String materialName;
}
