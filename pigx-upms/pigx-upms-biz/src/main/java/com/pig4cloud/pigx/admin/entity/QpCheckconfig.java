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
import java.time.LocalDateTime;

/**
 * 气瓶检验项配置表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_checkconfig")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶检验项配置表")
public class QpCheckconfig extends Model<QpCheckconfig> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 检查配置名称（自生成）
     */
    @ApiModelProperty(value="检查配置名称（自生成）")
    private String checkname;
    /**
     * 检查类型（1：充装前检查，2：充装后检查，3：定检前检查，4：定检后检查，5：报废检验）
     */
    @ApiModelProperty(value="检查类型（1：充装前检查，2：充装后检查，3：定检前检查，4：定检后检查，5：报废检验）")
    private Integer checktype;
    /**
     * 充装介质id
     */
    @ApiModelProperty(value="充装介质id")
    private Integer materialId;

	/**
	 * 充装介质
	 */
	@TableField(exist = false)
	private String materialName;
    /**
     * 检查项1
     */
    @ApiModelProperty(value="检查项1")
    private String option1;
    /**
     * 检查项2
     */
    @ApiModelProperty(value="检查项2")
    private String option2;
    /**
     * 检查项3
     */
    @ApiModelProperty(value="检查项3")
    private String option3;
    /**
     * 检查项4
     */
    @ApiModelProperty(value="检查项4")
    private String option4;
    /**
     * 检查项5
     */
    @ApiModelProperty(value="检查项5")
    private String option5;
    /**
     * 检查项6
     */
    @ApiModelProperty(value="检查项6")
    private String option6;
    /**
     * 检查项7
     */
    @ApiModelProperty(value="检查项7")
    private String option7;
    /**
     * 检查项8
     */
    @ApiModelProperty(value="检查项8")
    private String option8;
    /**
     * 检查项9
     */
    @ApiModelProperty(value="检查项9")
    private String option9;
    /**
     * 检查项10
     */
    @ApiModelProperty(value="检查项10")
    private String option10;
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
