package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

/**
 * 气瓶当前状态枚举 <br>
 *
 * @author guomingxi<br>
 * @date 2020/04/26 9:26 <br>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum QpStatusEnum {
	/**
	 * 气瓶当前状态枚举
	 *
	 * 在用 1：(1)空瓶待检验、(2)空瓶已检验、(3)空瓶待充装、(4)重瓶待检验、(5)重瓶已检验、(6)已配送出库、（7）空瓶检验异常、
	 * 		（8）重瓶检验异常、（9）气瓶待送修、（11）空瓶已送检、（12）气瓶已送修、
	 * 停用 2：（14）已停用
	 * 超期未检 3：（10）气瓶超期未检
	 * 待报废 4：（15）气瓶待报废
	 * 已报废 5：（13）气瓶已报废
	 */
	EMPTY_WAIT_CHECK(1, "空瓶待检查"),
	EMPTY_FINISH_CHECK(2, "空瓶已检查"),
	EMPTY_WAIT_AERATE(3, "空瓶待充装"),
	WEIGHT_WAIT_CHECK(4, "重瓶待检查"),
	WEIGHT_FINISH_CHECK(5, "重瓶已检查"),
	DISPATCHING(6, "配送出库"),
	EMPTY_CHECK_ABNORMAL(7, "空瓶检查异常"),
	WEIGHT_CHECK_ABNORMAL(8, "重瓶检验异常"),
	WAIT_SEND_REPAIR(9, "气瓶待送修"),
	CHECK_OUT_DATE(10, "气瓶超期未检"),
	EMPTY_FINISH_SEND_CHECK(11, "空瓶已送检"),
	FINISH_SEND_REPAIR(12, "气瓶已送修"),
	SCRAP(13, "气瓶已报废"),
	DISUSE(14, "已停用"),
	WAIT_SCRAP(15, "气瓶待报废"),
	;

	/**
	 * 操作员表type
	 */
	private Integer status;
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 判断status是否属于枚举类的值
	 *
	 * @param status 枚举的key
	 * @return true/false
	 */
	public static boolean isInclude(Integer status) {
		boolean include = false;
		for (QpStatusEnum e : QpStatusEnum.values()) {
			if (e.getStatus().equals(status)) {
				include = true;
				break;
			}
		}
		return include;
	}

	/**
	 * 根据枚举status获取相应的枚举对象
	 *
	 * @param status 枚举的key
	 * @return 枚举对象
	 */
	public static QpStatusEnum getEnum(Integer status) {
		QpStatusEnum resultEnum = null;
		QpStatusEnum[] enumAry = QpStatusEnum.values();
		for (QpStatusEnum mapEnum : enumAry) {
			if (mapEnum.getStatus().equals(status)) {
				resultEnum = mapEnum;
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 气瓶充装前检查（是否不可操作） <br>
	 *
	 * @param status : 接受检查的状态
	 * @Return: true 不可操作 / false 可以操作
	 */
	public static boolean qpAerateCheck(Integer status){
		//不可操作的情况：过期（超期未检）、待报废（超出使用年限）、已报废、停用状态
		Integer[] nonOperational = {
				CHECK_OUT_DATE.getStatus(),
				WAIT_SCRAP.getStatus(),
				SCRAP.getStatus(),
				DISUSE.getStatus()
		};
		return Arrays.asList(nonOperational).contains(status);
	}

	/**
	 * 气瓶状态业务状态转换 <br>
	 *
	 * @param status : 前台展示状态
	 * @Return: 描述
	 * @Author: gmx
	 * @Date: 2020/5/8 19:33
	 */
	public static String getQpStatus(Integer status) {
		String statusArry;
		switch (status) {
			case 1:
				statusArry = "在用";
				break;
			case 2:
				statusArry = "停用";
				break;
			case 3:
				statusArry = "超期未检";
				break;
			case 4:
				statusArry = "待报废";
				break;
			case 5:
				statusArry = "已报废";
				break;
			default:
				return "未知状态";
		}
		return statusArry;
	}
}
