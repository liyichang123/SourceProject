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
 * 开封万通上传充装记录失败日志表
 *
 * @author guomingxi
 * @date 2020-09-14 14:54:33
 */
@Data
@TableName("kfwt_upload_record_fail")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "开封万通上传充装记录失败日志表")
public class KfwtUploadRecordFail extends Model<KfwtUploadRecordFail> {
private static final long serialVersionUID = 1L;

    /**
     * 
     */
    @TableId
    @ApiModelProperty(value="主键id")
    private Integer id;
    /**
     * 
     */
    @ApiModelProperty(value="充装记录id")
    private Integer recordId;
    /**
     * 
     */
    @ApiModelProperty(value="开封万通气瓶档案id")
    private String kfwtId;
    /**
     * 
     */
    @ApiModelProperty(value="失败原因")
    private String failMsg;
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
	 * 气瓶钢印编号（瓶身编号）
	 */
	@TableField(exist = false)
	private String gascylindercode;
}
