package com.pig4cloud.pigx.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 操作员角色类型转换枚举 <br>
 *
 * @author guomingxi<br>
 * @date 2020/04/21 15:55 <br>
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum OperatorTypeEnum {
	/**
	 * 操作员角色类型转换枚举
	 */
	JQG(1, 5, "加气工"),
	PSY(2, 7, "配送员"),
	JYY(3, 8, "检验员"),
	;

	/**
	 * 操作员表type
	 */
	private Integer type;
	/**
	 * 原角色id
	 */
	private Integer roleId;
	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 判断type是否属于枚举类的值
	 *
	 * @param type 枚举的type
	 * @return true/false
	 */
	public static boolean isInclude(Integer type) {
		boolean include = false;
		for (OperatorTypeEnum e : OperatorTypeEnum.values()) {
			if (e.getType().equals(type)) {
				include = true;
				break;
			}
		}
		return include;
	}

	/**
	 * 根据枚举type获取相应的枚举对象
	 *
	 * @param type 枚举的type
	 * @return 枚举对象
	 */
	public static OperatorTypeEnum getEnum(Integer type) {
		OperatorTypeEnum resultEnum = null;
		OperatorTypeEnum[] enumAry = OperatorTypeEnum.values();
		for (OperatorTypeEnum mapEnum : enumAry) {
			if (mapEnum.getType().equals(type)) {
				resultEnum = mapEnum;
				break;
			}
		}
		return resultEnum;
	}

	/**
	 * 根据枚举roleId获取相应的枚举对象
	 *
	 * @param roleId 枚举的type
	 * @return 枚举对象
	 */
	public static OperatorTypeEnum getEnumByRoleId(Integer roleId) {
		OperatorTypeEnum resultEnum = null;
		OperatorTypeEnum[] enumAry = OperatorTypeEnum.values();
		for (OperatorTypeEnum mapEnum : enumAry) {
			if (mapEnum.getRoleId().equals(roleId)) {
				resultEnum = mapEnum;
				break;
			}
		}
		return resultEnum;
	}
}
