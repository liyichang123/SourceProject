package com.pig4cloud.pigx.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pig4cloud.pigx.admin.entity.KfwtUploadRecordFail;
import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.admin.entity.QpOperator;
import com.pig4cloud.pigx.admin.entity.QpRecord;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.Result;
import com.pig4cloud.pigx.admin.util.kfwt.SignUtil;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.xml.namespace.QName;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/16 10:21 <br>
 */
@Slf4j
@Service
@AllArgsConstructor
public class WtServiceImpl implements WtService {
	private final String SYDW = "1bf0166d49714314ac25599b0a35727c";

	private final QpOperatorService qpOperatorService;
	private final QpGascylinderrecordService qpGascylinderrecordService;
	private final KfwtUploadRecordFailService kfwtUploadRecordFailService;
	private final QpRecordService qpRecordService;

	@Async
	@Override
	public void addWtRecord(List<QpRecord> records) {
		if (CollectionUtils.isEmpty(records)) {
			return;
		}
		records.forEach(record -> {
			QpGascylinderrecord qpGascylinder = qpGascylinderrecordService.getById(record.getGascylinderrecordId());
			if (null != qpGascylinder) {
				Result gasJson = getGasJson(qpGascylinder.getGascylindercode());
				if (gasJson == null) {
					addkfwtUploadRecord(record, null, "万通气瓶档案查询接口调用异常");
				} else if (gasJson.getCode() != 0) {
					addkfwtUploadRecord(record, null, gasJson.getMessage());
				} else {
					JSONObject gasJsonData = JSON.parseObject(gasJson.getData().toString());
					String wtId = gasJsonData.getString("ID");
					Result result = addGasRecord(record, gasJsonData.getString("MEDIA"), wtId);
					if (result == null) {
						addkfwtUploadRecord(record, wtId, "上传充装记录接口调用异常");
					} else if (result.getCode() != 0) {
						addkfwtUploadRecord(record, wtId, gasJson.getMessage());
					}
				}
			}
			//修改上传标记
			if ("0".equals(record.getIsPerform())){
				record.setIsPerform("1");
				record.setModifyat("system");
				record.setModifydate(LocalDateTime.now());
				qpRecordService.updateById(record);
			}
		});
	}

	@Async
	@Override
	public void gasUpdataLabel(String gascylindercode, String labeltype, String label) {
		Result json = getGasJson(gascylindercode);
		if (json != null && json.getCode() == 0) {
			JSONObject jsonData = JSON.parseObject(json.getData().toString());
			String wtId = jsonData.getString("ID");
			Result result = gasUpdataTab(jsonData.getString("MEDIA"), wtId, label, labeltype);
			if (result == null) {
				log.error("气瓶标签数据接口调用返回异常");
			} else if (result.getCode() != 0) {
				log.error("气瓶标签数据接口调用异常: " + result.getMessage());
			}
		} else {
			log.error("万通气瓶档案查询接口调用异常: " + (null == json ? "" :json.getMessage()));
		}
	}

	private Result getGasJson(String gascylindercode) {
		JSONObject params = new JSONObject();
		params.put("Sydw", SYDW);
		params.put("FACTORY_NUMBER", gascylindercode);
		return getServiceResult(params.toJSONString(), GasJson);
	}

	private Result getServiceResult(String parameters, String methodName) {
		String nameSpace = "http://itelian.cn";
		String newUrl = "http://qp.kf96333.com/ws/interface?wsdl";
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient(newUrl);
			QName name = new QName(nameSpace, methodName);
			Object[] params = new Object[]{parameters};
			Object[] service = client.invoke(name, params);
			System.out.println("原result : "+JSON.toJSONString(service));
			System.out.println("service[0] ->>> : "+service[0].toString());
			return JSON.parseObject(service[0].toString(), Result.class);
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}

	private void addkfwtUploadRecord(QpRecord record, String kfwtId, String message) {
		LocalDateTime now = LocalDateTime.now();
		//新增失败记录
		KfwtUploadRecordFail failRecord = new KfwtUploadRecordFail();
		failRecord.setKfwtId(kfwtId);
		failRecord.setRecordId(record.getId());
		failRecord.setFailMsg(message);
		failRecord.setCreateat("system");
		failRecord.setCreatedate(now);
		failRecord.setDelFlag(CommonConstants.STATUS_NORMAL);
		kfwtUploadRecordFailService.save(failRecord);
	}

	private Result addGasRecord(QpRecord record, String media, String gasId) {
		JSONObject params = new JSONObject();
		params.put("gasId", gasId);
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
		params.put("czTime", record.getCreatedate().format(fmt));
		List<QpOperator> operator = qpOperatorService.getQpOperatorByUserId(Integer.valueOf(record.getCreateat()), 1);
		params.put("personId", CollectionUtils.isEmpty(operator) ? "" : operator.get(0).getId());
		params.put("personName", CollectionUtils.isEmpty(operator) ? "" : operator.get(0).getName());
		params.put("deviceId", "1");
		params.put("gunId", "1");
		params.put("czl", record.getFillingquantity());
		params.put("MEDIA", media);
		params.put("Sydw", SYDW);
		String sing = SignUtil.getSingn(params);
		System.out.println("sign : ------: " + sing);
		params.put("Sign", sing);
		return getServiceResult(params.toJSONString(), AddGasRecord);
	}

	private Result gasUpdataTab(String media, String gasId, String label, String labelType) {
		JSONObject params = new JSONObject();
		params.put("gasId", gasId);
		if ("1".equals(labelType)) {
			params.put("tagID", label);
		} else {
			params.put("qrCode", label);
		}
		params.put("MEDIA", media);
		params.put("Sydw", SYDW);
		String sing = SignUtil.getSingn(params);
		params.put("Sign", sing);
		System.out.println("sign : ------: " + sing);
		return getServiceResult(params.toJSONString(), AddGasRecord);
	}

}
