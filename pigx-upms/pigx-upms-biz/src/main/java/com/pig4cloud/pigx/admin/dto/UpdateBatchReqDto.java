package com.pig4cloud.pigx.admin.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/05/29 16:43 <br>
 */
@Data
public class UpdateBatchReqDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private String[] codes;

	private Integer[] ids;

	/**
	 * 上次审验日期
	 */
	private String checkdate;
	/**
	 * 下次审验日期
	 */
	private String nextcheckdate;

	/**
	 * 检验合格标志图片
	 */
	private String inspectionMarkPhoto;
}
