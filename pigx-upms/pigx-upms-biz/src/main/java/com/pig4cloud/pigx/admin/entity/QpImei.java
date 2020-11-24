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
 * IMEI设备表
 *
 * @author fll
 * @date 2020-05-22 15:08:34
 */
@Data
@TableName("qp_imei")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "IMEI设备表")
public class QpImei extends Model<QpImei> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * IMEI设备号
     */
    @ApiModelProperty(value="IMEI设备号")
    private String imei;
    /**
     * 是否可用: 0否 1是
     */
    @ApiModelProperty(value="是否可用: 0否 1是")
    private String isavailable;
    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;
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
