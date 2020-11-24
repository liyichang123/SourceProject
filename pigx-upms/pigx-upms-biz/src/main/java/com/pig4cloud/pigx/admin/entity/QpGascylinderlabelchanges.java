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
 * 气瓶标签变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_gascylinderlabelchanges")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶标签变更记录表")
public class QpGascylinderlabelchanges extends Model<QpGascylinderlabelchanges> {
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
	 * 电子标签类型（1：芯片,2：二维码）
	 */
	@ApiModelProperty(value = "电子标签类型（1：芯片,2：二维码）")
	private String electroniclabeltype;
	/**
	 * 电子标签编码
	 */
	@ApiModelProperty(value = "电子标签编码")
	private String electroniclabel;
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
	 * nfc编号
	 */
	@ApiModelProperty(value="nfc编号")
	private String nfcId;
}
