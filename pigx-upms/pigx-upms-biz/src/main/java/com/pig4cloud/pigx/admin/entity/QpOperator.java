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
 * 操作人员管理表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Data
@TableName("qp_operator")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "操作人员管理表")
public class QpOperator extends Model<QpOperator> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;
    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;
    /**
     * 1：加气工，2：配送员，3：检验员
     */
    @ApiModelProperty(value="1：加气工，2：配送员，3：检验员")
    private Integer type;
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
	 * 身份证号
	 */
	@ApiModelProperty(value="身份证号")
	private String idNumber;
	/**
	 * 充装证编号
	 */
	@ApiModelProperty(value="充装证编号")
	private String fillingCertificateNo;
	/**
	 * 充装证照片
	 */
	@ApiModelProperty(value="充装证照片")
	private String fillingCertificatePhoto;
	/**
	 * 检验人的信息
	 */
	@ApiModelProperty(value="检验人的信息")
	private String inspectorInformation;
	/**
	 * 检验员单位
	 */
	@ApiModelProperty(value="检验员单位")
	private String inspectorCompany;
	/**
	 * 检验员姓名
	 */
	@ApiModelProperty(value="检验员姓名")
	private String inspectorName;
	/**
	 * 操作员电话号码
	 */
	@ApiModelProperty(value="操作员电话号码")
	private String phone;

	/**
	 * 新密码
	 */
	@TableField(exist = false)
	private String newPassword;
    }
