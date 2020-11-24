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

import com.baomidou.mybatisplus.annotation.IdType;
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
import java.util.List;

/**
 * 生产厂商表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Data
@TableName("qp_manufactory")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "生产厂商表")
public class QpManufactory extends Model<QpManufactory> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 气瓶制造商名称
     */
    @ApiModelProperty(value="气瓶制造商名称")
    private String name;
    /**
     * 组织机构码
     */
    @ApiModelProperty(value="组织机构码")
    private String organizationcode;
    /**
     * 负责人
     */
    @ApiModelProperty(value="负责人")
    private String administrator;
    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String telephone;
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
	 * 营业执照（唯一）照片
	 */
	@ApiModelProperty(value="营业执照（唯一）照片")
	private String businessLicensePhoto;

    @TableField(exist = false)
	private List<QpManufactorypermission> qpmanufactorypermissionList;

	@TableField(exist = false)
	private List<QpValveManufacturing> qpvalvemanufacturingList;
    }
