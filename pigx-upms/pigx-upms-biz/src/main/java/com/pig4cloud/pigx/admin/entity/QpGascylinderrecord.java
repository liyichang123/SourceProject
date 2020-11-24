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
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 气瓶档案记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Data
@TableName("qp_gascylinderrecord")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "气瓶档案记录表")
public class 	QpGascylinderrecord extends Model<QpGascylinderrecord> {
private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    @TableId
    @ApiModelProperty(value="自增id")
    private Integer id;
    /**
     * 气瓶钢印编号（瓶身编号）
     */
    @ApiModelProperty(value="气瓶钢印编号（瓶身编号）")
    private String gascylindercode;
    /**
     * 气瓶钢印标识截图地址
     */
    @ApiModelProperty(value="气瓶钢印标识截图地址")
    private String gascylinderurl;
    /**
     * 制造日期
     */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="制造日期")
    private Date manufacturedate;
    /**
     * 制造厂家id
     */
    @ApiModelProperty(value="制造厂家id")
    private Integer manufactoryId;
    /**
     * 制造许可id
     */
    @ApiModelProperty(value="制造许可id")
    private Integer manufactorypermissionId;
    /**
     * 气瓶类型id
     */
    @ApiModelProperty(value="气瓶类型id")
    private Integer gascylindertypeId;
    /**
     * 充装介质id
     */
    @ApiModelProperty(value="充装介质id")
    private Integer materialId;
    /**
     * 使用年限
     */
    @ApiModelProperty(value="使用年限")
    private Integer usagelimitation;
    /**
     * 上次审验日期
     */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="上次审验日期")
    private Date checkdate;
    /**
     * 下次审验日期
     */
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    @ApiModelProperty(value="下次审验日期")
    private Date nextcheckdate;
    /**
     * 设计容积
     */
    @ApiModelProperty(value="设计容积")
    private BigDecimal capacity;
    /**
     * 质量
     */
    @ApiModelProperty(value="质量")
    private BigDecimal weight;
    /**
     * 定检周期（单位：年）
     */
    @ApiModelProperty(value="定检周期（单位：年）")
    private Integer regularlycheckcycle;
    /**
     * 当前状态
     */
    @ApiModelProperty(value="当前状态")
    private Integer instate;
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
	 * 阀门制造单位名称
	 */
	@ApiModelProperty(value="阀门制造单位名称")
	private String valveManufacturingName;
	/**
	 * 阀门型号
	 */
	@ApiModelProperty(value="阀门型号")
	private String valveManufacturingModel;
	/**
	 * 登记证标识编号
	 */
	@ApiModelProperty(value="登记证标识编号")
	private String registrationMarkNo;
	/**
	 * 登记证标识图片
	 */
	@ApiModelProperty(value="登记证标识图片")
	private String registrationMarkPhoto;
	/**
	 * 检验合格标志图片
	 */
	@ApiModelProperty(value="检验合格标志图片")
	private String inspectionMarkPhoto;
	/**
	 * 是否外采 1外采  2非外采
	 */
	@ApiModelProperty(value="是否外采")
	private String purchaseType;

	/**
	 * 外采备注
	 */
	@ApiModelProperty(value="外采备注")
	private String purchaseTypeRemark;
	/**
	 * 公称压力
	 */
	@ApiModelProperty(value="公称压力")
	private BigDecimal nominalPressure;
	/**
	 * 设计壁厚
	 */
	@ApiModelProperty(value="设计壁厚")
	private BigDecimal designWallThickness;
	/**
	 * 报废日期
	 */
	@ApiModelProperty(value="报废日期")
	private LocalDateTime scrapdate;

	/**
	 * 气瓶型号
	 */
	@ApiModelProperty(value="气瓶型号")
	private String modelNumber;

	/**
	 * 检验结果  0：不通过  1：通过
	 */
	@ApiModelProperty(value="检验结果")
	private String inspectionResult;

	/**
	 * 检验报告图片
	 */
	@ApiModelProperty(value="检验报告图片")
	private String inspectionResultPhoto;

	/**
	 * 检验员用户id
	 */
	@ApiModelProperty(value="检验员用户id")
	private Integer inspectionUserId;

	@TableField(exist = false)
	private String electroniclabeltype;

	@TableField(exist = false)
	private String electroniclabel;


	@TableField(exist = false)
	private String factoryName;
	@TableField(exist = false)
	private String factoryPermissionName;
	@TableField(exist = false)
	private String gascylindertypeName;
	@TableField(exist = false)
	private String materialName;
	/**
	 * 充装单位
	 */
	@TableField(exist = false)
	private String fillingUnit;
	/**
	 * 充装压力
	 */
	@TableField(exist = false)
	private BigDecimal fillingPressure;
	/**
	 * 气瓶展示状态
	 * 在用 1：(1)空瓶待检验、(2)空瓶已检验、(3)空瓶待充装、(4)重瓶待检验、(5)重瓶已检验、(6)已配送出库、（7）空瓶检验异常、
	 * 		（8）重瓶检验异常、（9）气瓶待送修、（11）空瓶已送检、（12）气瓶已送修、
	 * 停用 2：（14）已停用
	 * 超期未检 3：（10）气瓶超期未检
	 * 待报废 4：（15）气瓶待报废
	 * 已报废 5：（13）气瓶已报废
	 */
	@TableField(exist = false)
	private Integer status;
	/**
	 * 经度
	 */
	@TableField(exist = false)
	private String longitude;
	/**
	 * 维度
	 */
	@TableField(exist = false)
	private String dimension;

	/**
	 * 搜索条件 月份
	 */
	@TableField(exist = false)
	private String searchMonth;

	/**
	 * 前端搜索条件 状态 转换为业务状态数组供sql使用
	 */
	@TableField(exist = false)
	private Integer[] statusArray;

	/**
	 * 用途 1:修改状态， 2：修改标签  3：检验员操作  4:其他， 5：nfc标签
	 */
	@TableField(exist = false)
	private Integer useType;

	/**
	 * 加气站名称
	 */
	@TableField(exist = false)
	private String jqzName;

	/**
	 * 开始时间
	 */
	@TableField(exist = false)
	private String startTime;
	/**
	 * 结束时间
	 */
	@TableField(exist = false)
	private String endTime;

	/**
	 * nfc的编码
	 */
	@TableField(exist = false)
	private String nfcId;
}
