package com.pig4cloud.pigx.admin.vo;

import lombok.Data;

/**
 * 万通大数据气瓶档案vo
 *
 * @author guomingxi<br>
 * @date 2020/09/14 17:32 <br>
 */
@Data
public class WtQpArchivesVo {

	/**
	 * 登记证编号 (登记证标识编号)
	 */
	private String registrationMarkNo;
	/**
	 * 设备品种 (气瓶类型)
	 */
	private String gascylindertype;
	/**
	 * 单位内编号 (追溯系统中的气瓶id)
	 */
	private String gascylinderId;
	/**
	 * 产品编号  (瓶身编号)
	 */
	private String gascylindercode;
	/**
	 * 标签号 (标签)
	 */
	private String gascylinderLabel;
	/**
	 * 二维码 (二维码)
	 */
	private String gascylinderQRcode;
	/**
	 * 制造单位 (制造厂家)
	 */
	private String factoryName;
	/**
	 * 制造日期 (制造日期)
	 */
	private String manufacturedate;
	/**
	 * 检验日期 (上检日期)
	 */
	private String checkdate;
	/**
	 * 下次检验日期 (下次检验日期)
	 */
	private String nextcheckdate;
	/**
	 * 充装介质 (充装介质)
	 */
	private String material;
	/**
	 * 容积(L) (设计容积)
	 */
	private String capacity;
	/**
	 * 公称压力(MPa) (公称压力)
	 */
	private String nominalPressure;
	/**
	 * 气瓶状态 (当前状态)
	 */
	private String instate;
	/**
	 * 是否打印
	 */
	private String isPrint;
	/**
	 * 气瓶制造许可证编号 (制造许可)
	 */
	private String factoryPermissionName;
	/**
	 * 丙酮规定充装量（kg） ()
	 */
	private String acetone;
	/**
	 * 最大乙炔量（kg） ()
	 */
	private String maxBY;

}
