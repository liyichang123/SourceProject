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
 * 气瓶检验记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_checkrecord")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶检验记录表")
public class QpCheckrecord extends Model<QpCheckrecord> {
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
	 * 气瓶检验配置项id
	 */
	@ApiModelProperty(value = "气瓶检验配置项id")
	private Integer checkconfigId;
	/**
	 * 气瓶检验配置名称
	 */
	@TableField(exist = false)
	private String checkname;
	/**
	 * 结果（1：通过，2：不通过）
	 */
	@ApiModelProperty(value = "结果（1：通过，2：不通过）")
	private Integer result;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;
	/**
	 * 气体量
	 */
	@ApiModelProperty(value = "气体量")
	private String gasamount;
	/**
	 * 检查项1
	 */
	@ApiModelProperty(value = "检查项1")
	private String result1;
	/**
	 * 检查项2
	 */
	@ApiModelProperty(value = "检查项2")
	private String result2;
	/**
	 * 检查项3
	 */
	@ApiModelProperty(value = "检查项3")
	private String result3;
	/**
	 * 检查项4
	 */
	@ApiModelProperty(value = "检查项4")
	private String result4;
	/**
	 * 检查项5
	 */
	@ApiModelProperty(value = "检查项5")
	private String result5;
	/**
	 * 检查项6
	 */
	@ApiModelProperty(value = "检查项6")
	private String result6;
	/**
	 * 检查项7
	 */
	@ApiModelProperty(value = "检查项7")
	private String result7;
	/**
	 * 检查项8
	 */
	@ApiModelProperty(value = "检查项8")
	private String result8;
	/**
	 * 检查项9
	 */
	@ApiModelProperty(value = "检查项9")
	private String result9;
	/**
	 * 检查项10
	 */
	@ApiModelProperty(value = "检查项10")
	private String result10;
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

	@TableField(exist = false)
	private Integer checktypeValue;

	@TableField(exist = false)
	private Integer resultValue;

	@TableField(exist = false)
	private String gascylinderrecordCode;

	/**
	 * 加气站名称
	 */
	@TableField(exist = false)
	private String jqzName;
}
