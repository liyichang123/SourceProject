package com.pig4cloud.pigx.admin.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/05/11 15:34 <br>
 */
@Data
@ApiModel(value = "客户端")
public class AppFileDto implements Serializable {
	/**
	 * 编号
	 */
	private Long id;

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 原文件名
	 */
	private String original;

	/**
	 * 文件类型
	 */
	private String type;

	/**
	 * 文件大小
	 */
	private Long fileSize;

	/**
	 * 上传时间
	 */
	private LocalDateTime createTime;

	/**
	 * 描述备注
	 */
	private String remark;

	/**
	 * 版本号
	 */
	private String version;

	/**
	 * 下载地址
	 */
	private String downUrl;
}
