package com.pig4cloud.pigx.admin.service;

import com.pig4cloud.pigx.admin.entity.QpRecord;

import java.util.List;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/16 10:21 <br>
 */
public interface WtService {
	/**
	 * 充装企业信息接口、通过密钥获取，只获取//读表 企业信息
	 */
	String CompanyJsonList = "getCompanyJsonList";
	/**
	 * 气瓶基础数据接口、通过密钥获取，只获取企业下所有气瓶档案
	 */
	String GasInfoJsonList = "getGasInfoJsonList";
	/**
	 * 通过出厂编号查询气瓶基础数据接口、通过密钥获取
	 */
	String GasJson = "getGasJson";
	/**
	 * 气瓶充装数据接口、通过密钥上传企业充装记录，每天限制调用5000次
	 */
	String AddGasRecord = "getAddGasRecord";
	/**
	 * 气瓶标签数据查询接口
	 */
	String GasTabJson = "getGasTabJson";
	/**
	 * 气瓶标签数据接口、通过密钥获取企业下属气瓶主键id 上传标签uid，每天限制调用5000次
	 */
	String GasUpdataTab = "getGasUpdataTab";

	void addWtRecord(List<QpRecord> records);

	void gasUpdataLabel(String gascylindercode, String labeltype, String label);
}
