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
 * 气瓶状态变更记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_gascylinderstatechanges")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶状态变更记录表")
public class QpGascylinderstatechanges extends Model<QpGascylinderstatechanges> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 气瓶状态名称
     */
    @ApiModelProperty(value="气瓶状态名称")
    private String state;
	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remarks;
    /**
     * 关联的记录类型：（1.检验记录 2.出入库记录 3.充装记录）
     */
    @ApiModelProperty(value="关联的记录类型：（1.检验记录 2.出入库记录 3.充装记录）")
    private Integer recordType;
    /**
     * 关联的记录表id
     */
    @ApiModelProperty(value="关联的记录表id")
    private Integer recordId;
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
