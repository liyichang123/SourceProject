package com.pig4cloud.pigx.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.dto.QpRecordReqDto;
import com.pig4cloud.pigx.admin.dto.UpdateBatchReqDto;
import com.pig4cloud.pigx.admin.entity.*;
import com.pig4cloud.pigx.admin.service.*;
import com.pig4cloud.pigx.admin.util.excel.ExcelWriter;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.data.tenant.TenantContextHolder;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.common.security.annotation.Inner;
import com.pig4cloud.pigx.common.security.service.PigxUser;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_DEL;
import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_NORMAL;
import static com.pig4cloud.pigx.common.core.constant.enums.OperatorTypeEnum.JYY;
import static com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum.*;


/**
 * 气瓶档案记录表
 *
 * @author wh
 * @date 2020-04-03 14:05:09
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/qpgascylinderrecord")
@Api(value = "qpgascylinderrecord", tags = "气瓶档案记录表管理")
public class QpGascylinderrecordController {

	private final QpGascylinderrecordService qpGascylinderrecordService;
	private final QpGascylinderlabelchangesService qpGascylinderlabelchangesService;
	private final QpGascylinderstatechangesService qpGascylinderstatechangesService;

	private final QpInOutStoreService qpInOutStoreService;
	private final QpRecordService qpRecordService;
	private final QpJqzService qpJqzService;
	private final QpOperatorService qpOperatorService;

	private final WtService wtService;

	/**
	 * 分页查询
	 *
	 * @param page                分页对象
	 * @param qpGascylinderrecord 气瓶档案记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getQpGascylinderrecordPage(Page page, QpGascylinderrecord qpGascylinderrecord) {
		qpGascylinderrecord.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderrecord.setDelFlag("0");
		if (qpGascylinderrecord.getStatus() != null) {
			qpGascylinderrecord.setStatusArray(getStatusArray(qpGascylinderrecord.getStatus()));
		}
		return R.ok(qpGascylinderrecordService.getPage(page, qpGascylinderrecord));
	}


	/**
	 * 预警
	 *
	 * @param page                分页对象
	 * @param qpGascylinderrecord 气瓶档案记录表黑名单
	 * @return
	 */
	@ApiOperation(value = "黑名单查询", notes = "黑名单查询")
	@GetMapping("/warninglist")
	public R warninglist(Page page, QpGascylinderrecord qpGascylinderrecord) {
		qpGascylinderrecord.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderrecord.setDelFlag("0");
		return R.ok(qpGascylinderrecordService.warninglist(page, qpGascylinderrecord));
	}

	/**
	 * 黑名单
	 *
	 * @param page           分页对象
	 * @param qpRecordReqDto 气瓶档案黑名单查询dto
	 * @return
	 */
	@ApiOperation(value = "黑名单查询", notes = "黑名单查询")
	@GetMapping("/blacklist")
	public R blacklist(Page page, QpRecordReqDto qpRecordReqDto) {
		qpRecordReqDto.setTenantId(TenantContextHolder.getTenantId());
		IPage<Map<String, Object>> blackPage = qpGascylinderrecordService.blacklist(page, qpRecordReqDto);
		List<Map<String, Object>> records = blackPage.getRecords();
		String blackReason;
		if ("1".equals(qpRecordReqDto.getBlackReason())) {
			blackReason = "超期未检";
		} else {
			blackReason = "超过使用年限";
		}
		records.forEach(a -> {
			a.put("blackReason", blackReason);
		});
		blackPage.setRecords(records);
		return R.ok(blackPage);
	}

	/**
	 * 沉睡钢瓶
	 *
	 * @param page                分页对象
	 * @param qpGascylinderrecord 气瓶档案记录表沉睡钢瓶
	 * @return
	 */
	@ApiOperation(value = "沉睡钢瓶查询", notes = "沉睡钢瓶查询")
	@GetMapping("/sleeplist")
	public R sleeplist(Page page, QpGascylinderrecord qpGascylinderrecord) {
		qpGascylinderrecord.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderrecord.setDelFlag("0");
		return R.ok(qpGascylinderrecordService.sleeplist(page, qpGascylinderrecord));
	}


	/**
	 * 通过id查询气瓶档案记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(qpGascylinderrecordService.getById(id));
	}


	/**
	 * 通过id查询气瓶档案记录表
	 *
	 * @param code
	 * @return R
	 */
	@Inner(value = false)
	@ApiOperation(value = "通过标签或者气瓶编号", notes = "通过标签或者气瓶编号")
	@GetMapping("/getGascylinderrecordByCode/{code}")
	public R getGascylinderrecordByCode(@PathVariable("code") String code, @RequestParam(value = "codeType", required = false) String codeType) {
		if (StringUtils.isBlank(code)) {
			return R.ok(null, "缺少请求参数");
		}
		if (StringUtils.isNotBlank(codeType)) {
			codeType = codeType.toLowerCase();
		}
		JSONObject jsonObject = new JSONObject();
		//气瓶档案
		QpGascylinderrecord qpGascylinderrecord = qpGascylinderrecordService.getGascylinderrecordByCode(code, codeType);
		jsonObject.put("qpGascylinderrecordMap", qpGascylinderrecord);
		if (qpGascylinderrecord == null) {
			return R.ok(null, "暂无气瓶档案记录");
		}

		//最后检验信息
		Map<String, String> lastCheckInfo = new HashMap<>();
		lastCheckInfo.put("inspectionMarkPhoto", qpGascylinderrecord.getInspectionMarkPhoto());
		lastCheckInfo.put("inspectionResult", qpGascylinderrecord.getInspectionResult());
		lastCheckInfo.put("inspectionResultPhoto", qpGascylinderrecord.getInspectionResultPhoto());
		List<QpOperator> jyy = qpOperatorService.getQpOperatorByUserId(qpGascylinderrecord.getInspectionUserId(), JYY.getType());
		if (CollectionUtils.isNotEmpty(jyy)) {
			lastCheckInfo.put("inspectorCompany", jyy.get(0).getInspectorCompany());
			lastCheckInfo.put("inspectorName", jyy.get(0).getInspectorName());
			lastCheckInfo.put("inspectorInformation", jyy.get(0).getInspectorInformation());
		}
		jsonObject.put("QpLastCheckInfo", lastCheckInfo);

		//气瓶检验记录
		/*QpCheckrecord qpCheckrecord = new QpCheckrecord();
		qpCheckrecord.setDeptId(pigxUser.getDeptId());
		qpCheckrecord.setTenantId(TenantContextHolder.getTenantId());
		qpCheckrecord.setGascylinderrecordId(qpGascylinderrecord.getId());
		List<Map<String, Object>> qpCheckrecordList = qpCheckrecordService.getQpCheckRecordList(qpCheckrecord);
		jsonObject.put("qpCheckrecordList", qpCheckrecordList);*/

		//气瓶出入库记录
		QpInOutStore qpInOutStore = new QpInOutStore();
		//qpInOutStore.setDeptId(pigxUser.getDeptId());
		//qpInOutStore.setTenantId(TenantContextHolder.getTenantId());
		qpInOutStore.setGascylinderrecordId(qpGascylinderrecord.getId());
		List<Map<String, Object>> qpInOutStoreList = qpInOutStoreService.getQpInOutStoreListByCode(qpInOutStore);
		jsonObject.put("qpInOutStoreList", qpInOutStoreList);

		//气瓶坐标map
		Map<String, String> map = new HashMap<>();
		String longitude = null;
		String dimension = null;
		//增加气瓶坐标经纬度
		if (CollectionUtils.isNotEmpty(qpInOutStoreList)) {
			Map<String, Object> inOutStore = qpInOutStoreList.get(0);
			if ((Integer) inOutStore.get("inOutType") == 1) {
				QueryWrapper<QpJqz> queryWrapper = new QueryWrapper<QpJqz>();
				queryWrapper.select("id", "name", "longitude", "dimension");
				queryWrapper.eq("del_flag", "0");
				queryWrapper.eq("tenant_id", inOutStore.get("tenant_id"));
				queryWrapper.eq("dept_id", inOutStore.get("dept_id"));
				queryWrapper.orderByDesc("createDate");
				List<QpJqz> qpJpzList = qpJqzService.list(queryWrapper);
				if (qpJpzList.size() > 0) {
					longitude = qpJpzList.get(0).getLongitude();
					dimension = qpJpzList.get(0).getDimension();
				}
			} else {
				longitude = (String) inOutStore.get("longitude");
				dimension = (String) inOutStore.get("dimension");
			}
			qpGascylinderrecord.setLongitude(longitude);
			qpGascylinderrecord.setDimension(dimension);
			map.put("longitude", longitude);
			map.put("dimension", dimension);
		}
		jsonObject.put("map", map);

		//气瓶充装记录
		QpRecord qpRecord = new QpRecord();
		//qpRecord.setDeptId(pigxUser.getDeptId());
		//qpRecord.setTenantId(TenantContextHolder.getTenantId());
		qpRecord.setGascylinderrecordId(qpGascylinderrecord.getId());
		List<QpRecord> qpRecordList = qpRecordService.getQprrecordListByCode(qpRecord);
		jsonObject.put("qpRecordList", qpRecordList);
		return R.ok(jsonObject);
	}

	/**
	 * 新增气瓶档案记录表
	 *
	 * @param qpGascylinderrecord 气瓶档案记录表
	 * @return R
	 */
	@ApiOperation(value = "新增气瓶档案记录表", notes = "新增气瓶档案记录表")
	@SysLog("新增气瓶档案记录表")
	@PostMapping
	public R save(@RequestBody QpGascylinderrecord qpGascylinderrecord) {
		QueryWrapper<QpGascylinderlabelchanges> queryWrapper1 = new QueryWrapper<>();
		queryWrapper1.eq("electroniclabel", qpGascylinderrecord.getElectroniclabel());
		QpGascylinderlabelchanges qpGascylinderlabelchanges1 = qpGascylinderlabelchangesService.getOne(queryWrapper1);
		if (qpGascylinderlabelchanges1 != null) {
			return R.failed("当前芯片或二维码已存在，请联系管理员。");
		}
		String nfcId = qpGascylinderrecord.getNfcId();
		QpGascylinderlabelchanges qpGascylinderlabelchanges = new QpGascylinderlabelchanges();
		if (StringUtils.isNotBlank(nfcId)) {
			QueryWrapper<QpGascylinderlabelchanges> queryWrapper2 = new QueryWrapper<>();
			queryWrapper2.eq("nfc_id", qpGascylinderrecord.getNfcId());
			QpGascylinderlabelchanges qpGascylinderlabelchanges2 = qpGascylinderlabelchangesService.getOne(queryWrapper2);
			if (qpGascylinderlabelchanges2 != null) {
				return R.failed("当前芯片或二维码已存在，请联系管理员。");
			}
			qpGascylinderlabelchanges.setNfcId(nfcId);
		}
		PigxUser pigxUser = SecurityUtils.getUser();
		qpGascylinderrecord.setCreateat(pigxUser.getId().toString());
		qpGascylinderrecord.setCreatedate(LocalDateTime.now());
		qpGascylinderrecord.setDeptId(pigxUser.getDeptId());
		qpGascylinderrecord.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderrecord.setInstate(1);
		qpGascylinderrecord.setDelFlag("0");
		//气瓶钢印判重
		QpGascylinderrecord qpGascylinderrecord1 = new QpGascylinderrecord();
		qpGascylinderrecord1.setGascylindercode(qpGascylinderrecord.getGascylindercode());
		qpGascylinderrecord1.setDelFlag("0");
		qpGascylinderrecord1 = qpGascylinderrecordService.getOne(Wrappers.query(qpGascylinderrecord1));
		if (qpGascylinderrecord1 != null) {
			return R.failed("当前钢瓶编号已建档，请查证。");
		}
		Integer qpGascylinderrecordId = qpGascylinderrecordService.saveRecord(qpGascylinderrecord);

		qpGascylinderlabelchanges.setDeptId(pigxUser.getDeptId());
		qpGascylinderlabelchanges.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderlabelchanges.setDelFlag("0");
		qpGascylinderlabelchanges.setElectroniclabel(qpGascylinderrecord.getElectroniclabel());
		qpGascylinderlabelchanges.setElectroniclabeltype(qpGascylinderrecord.getElectroniclabeltype());
		qpGascylinderlabelchanges.setGascylinderrecordId(qpGascylinderrecordId);

		return R.ok(qpGascylinderlabelchangesService.save(qpGascylinderlabelchanges));
	}

	/**
	 * 修改气瓶档案记录表
	 *
	 * @param qpGascylinderrecord 气瓶档案记录表
	 * @return R
	 */
	@ApiOperation(value = "修改气瓶档案记录表", notes = "修改气瓶档案记录表")
	@SysLog("修改气瓶档案记录表")
	@PutMapping
	@Transactional
	public R updateById(@RequestBody QpGascylinderrecord qpGascylinderrecord) {
		if (qpGascylinderrecord.getUseType() == null || qpGascylinderrecord.getId() == null) {
			return R.failed("缺少用途类型或气瓶档案id");
		}
		QpGascylinderrecord gascylinderrecord = new QpGascylinderrecord();
		PigxUser pigxUser = SecurityUtils.getUser();
		gascylinderrecord.setId(qpGascylinderrecord.getId());
		gascylinderrecord.setModifyat(pigxUser.getId().toString());
		gascylinderrecord.setModifydate(LocalDateTime.now());
		if (qpGascylinderrecord.getUseType() == 1) {
			gascylinderrecord.setInstate(qpGascylinderrecord.getInstate());
			//报废
			if (SCRAP.getStatus().equals(qpGascylinderrecord.getInstate())) {
				QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
				qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
				qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
				qpGascylinderstatechanges.setRecordType(4);
				qpGascylinderstatechanges.setState(SCRAP.getStatus().toString());
				qpGascylinderstatechanges.setRecordId(0);
				qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
				qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
				qpGascylinderstatechangesService.save(qpGascylinderstatechanges);
			}
			//送修
			if (WAIT_SEND_REPAIR.getStatus().equals(qpGascylinderrecord.getInstate())) {
				QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
				qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
				qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
				qpGascylinderstatechanges.setRecordType(6);
				qpGascylinderstatechanges.setState(WAIT_SEND_REPAIR.getStatus().toString());
				qpGascylinderstatechanges.setRecordId(0);
				qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
				qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
				qpGascylinderstatechangesService.save(qpGascylinderstatechanges);
			}
			//送检
			if (EMPTY_FINISH_SEND_CHECK.getStatus().equals(qpGascylinderrecord.getInstate())) {
				QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
				qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
				qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
				qpGascylinderstatechanges.setRecordType(5);
				qpGascylinderstatechanges.setState(EMPTY_FINISH_SEND_CHECK.getStatus().toString());
				qpGascylinderstatechanges.setRecordId(0);
				qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
				qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
				qpGascylinderstatechangesService.save(qpGascylinderstatechanges);
			}
		} else if (qpGascylinderrecord.getUseType() == 2) {
			QpGascylinderrecord byId = qpGascylinderrecordService.getById(qpGascylinderrecord.getId());
			if (!byId.getDeptId().equals(pigxUser.getDeptId())) {
				return R.failed("不能更改其他气站的电子标签或二维码");
			}
			//入参标签类型或标签编号为空则不补发，不为空 气瓶标签补发，先查询是否存在气瓶标签，若有，则逻辑删除， 后重新补发。 若无，也进行补发
			if (StringUtils.isNotBlank(qpGascylinderrecord.getElectroniclabel()) && StringUtils.isNotBlank(qpGascylinderrecord.getElectroniclabeltype())) {
				deleteGascylinderlabelchanges(qpGascylinderrecord.getId(), pigxUser);
				QpGascylinderlabelchanges qpGascylinderlabelchanges = packageGascylinderlabelchanges(qpGascylinderrecord, pigxUser);
				qpGascylinderlabelchangesService.save(qpGascylinderlabelchanges);
				if (pigxUser.getDeptId() == 29) {
					wtService.gasUpdataLabel(qpGascylinderrecord.getGascylindercode(), qpGascylinderrecord.getElectroniclabeltype(), qpGascylinderrecord.getElectroniclabel());
				}
			} else {
				return R.failed("缺少标签类型或标签编码");
			}
		} else if (qpGascylinderrecord.getUseType() == 3) {
			gascylinderrecord.setInspectionUserId(pigxUser.getId());
			gascylinderrecord.setUsagelimitation(qpGascylinderrecord.getUsagelimitation());
			gascylinderrecord.setRegularlycheckcycle(qpGascylinderrecord.getRegularlycheckcycle());
			gascylinderrecord.setScrapdate(qpGascylinderrecord.getScrapdate());
			gascylinderrecord.setCheckdate(qpGascylinderrecord.getCheckdate());
			gascylinderrecord.setNextcheckdate(qpGascylinderrecord.getNextcheckdate());
			gascylinderrecord.setInspectionResult(qpGascylinderrecord.getInspectionResult());
			gascylinderrecord.setInspectionResultPhoto(qpGascylinderrecord.getInspectionResultPhoto());
			gascylinderrecord.setInspectionMarkPhoto(qpGascylinderrecord.getInspectionMarkPhoto());
			gascylinderrecord.setValveManufacturingName(qpGascylinderrecord.getValveManufacturingName());
			gascylinderrecord.setValveManufacturingModel(qpGascylinderrecord.getValveManufacturingModel());
			//检验不通过，则置为报废
			if ("0".equals(qpGascylinderrecord.getInspectionResult())) {
				QpGascylinderstatechanges qpGascylinderstatechanges = new QpGascylinderstatechanges();
				qpGascylinderstatechanges.setCreatedate(LocalDateTime.now());
				qpGascylinderstatechanges.setCreateat(pigxUser.getUsername());
				qpGascylinderstatechanges.setRecordType(4);
				qpGascylinderstatechanges.setState(SCRAP.getStatus().toString());
				qpGascylinderstatechanges.setRecordId(0);
				qpGascylinderstatechanges.setDeptId(pigxUser.getDeptId());
				qpGascylinderstatechanges.setTenantId(TenantContextHolder.getTenantId());
				qpGascylinderstatechangesService.save(qpGascylinderstatechanges);

				gascylinderrecord.setInstate(SCRAP.getStatus());
			}
		} else if (qpGascylinderrecord.getUseType() == 4) {
			BeanUtils.copyProperties(qpGascylinderrecord, gascylinderrecord);
			gascylinderrecord.setCreateat(null);
		} else if (qpGascylinderrecord.getUseType() == 5) {
			//补发nfc标签
			if (StringUtils.isNotBlank(qpGascylinderrecord.getNfcId())) {
				QueryWrapper<QpGascylinderlabelchanges> queryNfc = new QueryWrapper<>();
				queryNfc.eq("nfc_id", qpGascylinderrecord.getNfcId());
				List<QpGascylinderlabelchanges> nfcList = qpGascylinderlabelchangesService.list(queryNfc);
				QueryWrapper<QpGascylinderlabelchanges> queryLabel = new QueryWrapper<>();
				queryLabel.eq("electroniclabel", qpGascylinderrecord.getElectroniclabel());
				List<QpGascylinderlabelchanges> labelList = qpGascylinderlabelchangesService.list(queryLabel);
				if (CollectionUtils.isNotEmpty(nfcList) || CollectionUtils.isNotEmpty(labelList)) {
					return R.failed("当前芯片或二维码已存在，请联系管理员。");
				}
				QueryWrapper<QpGascylinderlabelchanges> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("gascylinderrecord_id", qpGascylinderrecord.getId());
				queryWrapper.eq("del_flag", "0");
				QpGascylinderlabelchanges qpGascylinderlabelchanges = qpGascylinderlabelchangesService.getOne(queryWrapper);
				if (qpGascylinderlabelchanges != null) {
					deleteGascylinderlabelchanges(qpGascylinderrecord.getId(), pigxUser);
				}
				QpGascylinderlabelchanges newLabelchanges = packageGascylinderlabelchanges(qpGascylinderrecord, pigxUser);
				qpGascylinderlabelchangesService.save(newLabelchanges);
			}
		}
		return R.ok(qpGascylinderrecordService.updateById(gascylinderrecord));
	}


	/**
	 * 通过id删除气瓶档案记录表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除气瓶档案记录表", notes = "通过id删除气瓶档案记录表")
	@SysLog("通过id删除气瓶档案记录表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_qpgascylinderrecord_del')")
	public R removeById(@PathVariable Integer id) {
		PigxUser pigxUser = SecurityUtils.getUser();
		//先将标签记录表delflag修改为删除
		deleteGascylinderlabelchanges(id, pigxUser);

		QpGascylinderrecord gascylinderrecord = new QpGascylinderrecord();
		gascylinderrecord.setId(id);
		gascylinderrecord.setModifyat(pigxUser.getId().toString());
		gascylinderrecord.setModifydate(LocalDateTime.now());
		gascylinderrecord.setDelFlag(STATUS_DEL);
		return R.ok(qpGascylinderrecordService.updateById(gascylinderrecord));
	}

	/**
	 * 通过气瓶编号数组查询气瓶档案list
	 *
	 * @param reqDto 气瓶编号拼接字符串 （例：12345,abc3456,34567）
	 * @return R
	 * @Author: gmx
	 */
	@ApiOperation(value = "通过气瓶编号数组查询list", notes = "通过气瓶编号数组查询list")
	@PostMapping("/getByCodes")
	public R getByCodes(@RequestBody UpdateBatchReqDto reqDto) {
		if (reqDto.getCodes() == null || reqDto.getCodes().length == 0) {
			return R.ok(null, "气瓶编号数组为空");
		}
		return R.ok(qpGascylinderrecordService.getByCodes(reqDto.getCodes()));
	}

	/**
	 * 批量修改检查的气瓶
	 *
	 * @param reqDto 入参
	 * @return R
	 * @Author: gmx
	 */
	@ApiOperation(value = "批量修改检查的气瓶", notes = "批量修改检查的气瓶")
	@PostMapping("/updateBatchCheck")
	@Transactional(rollbackFor = Exception.class)
	public R updateBatchCheck(@RequestBody UpdateBatchReqDto reqDto) {
		List<Integer> ids = Arrays.asList(reqDto.getIds());
		PigxUser pigxUser = SecurityUtils.getUser();
		LocalDateTime now = LocalDateTime.now();
		List<QpGascylinderrecord> updateList = ids.stream().map(id -> {
			QpGascylinderrecord gascylinderrecord = new QpGascylinderrecord();
			gascylinderrecord.setId(id);
			gascylinderrecord.setModifyat(pigxUser.getId().toString());
			gascylinderrecord.setModifydate(now);
			gascylinderrecord.setInspectionUserId(pigxUser.getId());
			try {
				gascylinderrecord.setCheckdate(DateUtils.parseDate(reqDto.getCheckdate(), "yyyy-MM-dd"));
				gascylinderrecord.setNextcheckdate(DateUtils.parseDate(reqDto.getNextcheckdate(), "yyyy-MM-dd"));
			} catch (ParseException e) {
				log.error("Checkdate or Nextcheckdate parseDate error : ", e);
			}
			gascylinderrecord.setInspectionResult("1");
			gascylinderrecord.setInspectionResultPhoto(reqDto.getInspectionMarkPhoto());
			return gascylinderrecord;
		}).collect(Collectors.toList());
		return R.ok(qpGascylinderrecordService.updateBatchById(updateList));
	}

	/**
	 * 导出开封万通气瓶档案excel
	 *
	 * @param ids 所选气瓶ids
	 * @return R
	 * @Author: gmx
	 */
	@ApiOperation(value = "导出开封万通气瓶档案excel", notes = "导出开封万通气瓶档案excel")
	@GetMapping("/exportKfwtQpExcel")
	public R<Object> exportKfwtQpExcel(String ids, HttpServletResponse response) {
		Workbook workbook = null;
		OutputStream out = null;
		if (StringUtils.isBlank(ids)) {
			return R.ok(null, "请选择要生成excel的数据");
		}
		List<QpGascylinderrecord> dataList = qpGascylinderrecordService.getExcelRecordVoByIds(ids);
		try {
			// 生成Excel工作簿对象并写入数据
			workbook = ExcelWriter.exportData(dataList);
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS");
			// 写入Excel文件到前端
			String fileName = "开封气瓶信息" + LocalDateTime.now().format(fmt) + ".xls";
			//fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), "iso8859-1");
			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Pargam", "no-cache");
			response.addHeader("Cache-Control", "no-cache");
			response.flushBuffer();
			out = response.getOutputStream();
			workbook.write(out);
			out.flush();
		} catch (Exception e) {
			log.error("写入Excel过程出错！错误原因：" + e.getMessage());
		} finally {
			try {
				if (null != workbook) {
					workbook.close();
				}
				if (null != out) {
					out.close();
				}
			} catch (Exception e) {
				log.error("关闭workbook或outputStream出错！");
			}
		}
		return null;
	}

	/**
	 * 封装气瓶标签: <br>
	 *
	 * @param qpGascylinderrecord : 气瓶档案数据
	 * @param pigxUser            : 当前登录用户
	 * @Return: QpGascylinderlabelchanges
	 * @Author: gmx
	 * @Date: 2020/4/26 14:11
	 */
	private QpGascylinderlabelchanges packageGascylinderlabelchanges(QpGascylinderrecord qpGascylinderrecord, PigxUser pigxUser) {
		QpGascylinderlabelchanges qpGascylinderlabelchanges = new QpGascylinderlabelchanges();
		qpGascylinderlabelchanges.setTenantId(TenantContextHolder.getTenantId());
		qpGascylinderlabelchanges.setDelFlag(STATUS_NORMAL);
		qpGascylinderlabelchanges.setElectroniclabel(qpGascylinderrecord.getElectroniclabel());
		qpGascylinderlabelchanges.setElectroniclabeltype(qpGascylinderrecord.getElectroniclabeltype());
		qpGascylinderlabelchanges.setGascylinderrecordId(qpGascylinderrecord.getId());
		qpGascylinderlabelchanges.setDeptId(pigxUser.getDeptId());
		qpGascylinderlabelchanges.setCreateat(pigxUser.getUsername());
		qpGascylinderlabelchanges.setCreatedate(LocalDateTime.now());
		qpGascylinderlabelchanges.setNfcId(qpGascylinderrecord.getNfcId());
		return qpGascylinderlabelchanges;
	}

	/**
	 * 气瓶状态业务状态转换 <br>
	 *
	 * @param status : 前台展示状态
	 * @Return: Integer[] 业务状态数组
	 * @Author: gmx
	 * @Date: 2020/5/8 19:33
	 */
	private Integer[] getStatusArray(Integer status) {
		Integer[] statusArry;
		switch (status) {
			case 1:
				statusArry = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12};
				break;
			case 2:
				statusArry = new Integer[]{14};
				break;
			case 3:
				statusArry = new Integer[]{10};
				break;
			case 4:
				statusArry = new Integer[]{15};
				break;
			case 5:
				statusArry = new Integer[]{13};
				break;
			default:
				return null;
		}
		return statusArry;
	}

	private void deleteGascylinderlabelchanges(Integer gascylinderrecordId, PigxUser pigxUser) {
		QueryWrapper<QpGascylinderlabelchanges> queryWrapper = new QueryWrapper<QpGascylinderlabelchanges>();
		queryWrapper.eq("gascylinderrecord_id", gascylinderrecordId);
		queryWrapper.eq("del_flag", "0");
		QpGascylinderlabelchanges qpGascylinderlabelchanges = qpGascylinderlabelchangesService.getOne(queryWrapper);
		if (qpGascylinderlabelchanges != null) {
			qpGascylinderlabelchanges.setModifyat(pigxUser.getUsername());
			qpGascylinderlabelchanges.setModifydate(LocalDateTime.now());
			qpGascylinderlabelchanges.setDelFlag(STATUS_DEL);
			qpGascylinderlabelchangesService.updateById(qpGascylinderlabelchanges);
		}
	}

}
