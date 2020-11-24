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
 * 制造许可证表
 *
 * @author wh
 * @date 2020-04-03 14:04:23
 */
@Data
@TableName("qp_manufactorypermission")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "制造许可证表")
public class QpManufactorypermission extends Model<QpManufactorypermission> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 生产厂商id
     */
    @ApiModelProperty(value="生产厂商id")
    private Integer manufactoryId;
    /**
     * 生产许可证号
     */
    @ApiModelProperty(value="生产许可证号")
    private String permission;
	/**
	 * 0：否 1：是
	 */
	@ApiModelProperty(value="0：否 1：是")
	private String delFlag;
	/**
	 * 到期日期
	 */
	@ApiModelProperty(value="到期日期")
	private LocalDateTime duedate;
	/**
	 * 生产许可证照片
	 */
	@ApiModelProperty(value="生产许可证照片")
	private String productionLicensePhoto;
	/**
	 * 有效状态
	 */
	@ApiModelProperty(value="有效状态")
	private String status;
    }
