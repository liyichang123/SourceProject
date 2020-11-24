package com.pig4cloud.pigx.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.QpRecord;
import com.pig4cloud.pigx.admin.service.QpRecordService;
import com.pig4cloud.pigx.admin.service.WtService;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.log.annotation.SysLog;
import com.pig4cloud.pigx.admin.entity.KfwtUploadRecordFail;
import com.pig4cloud.pigx.admin.service.KfwtUploadRecordFailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 开封万通上传充装记录失败日志表
 *
 * @author guomingxi
 * @date 2020-09-14 14:54:33
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/kfwtuploadrecordfail")
@Api(value = "kfwtuploadrecordfail", tags = "开封万通上传充装记录失败日志表管理")
public class KfwtUploadRecordFailController {

	private final KfwtUploadRecordFailService kfwtUploadRecordFailService;
	private final QpRecordService qpRecordService;
	private final WtService wtService;

	/**
	 * 分页查询
	 *
	 * @param page                 分页对象
	 * @param kfwtUploadRecordFail 开封万通上传充装记录失败日志表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<Object> getKfwtUploadRecordFailPage(Page page, KfwtUploadRecordFail kfwtUploadRecordFail) {
		return R.ok(kfwtUploadRecordFailService.getPage(page, kfwtUploadRecordFail));
	}


	/**
	 * 通过id查询开封万通上传充装记录失败日志表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{id}")
	public R getById(@PathVariable("id") Integer id) {
		return R.ok(kfwtUploadRecordFailService.getById(id));
	}

	/**
	 * 新增开封万通上传充装记录失败日志表
	 *
	 * @param kfwtUploadRecordFail 开封万通上传充装记录失败日志表
	 * @return R
	 */
	@ApiOperation(value = "新增开封万通上传充装记录失败日志表", notes = "新增开封万通上传充装记录失败日志表")
	@SysLog("新增开封万通上传充装记录失败日志表")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('admin_kfwtuploadrecordfail_add')")
	public R save(@RequestBody KfwtUploadRecordFail kfwtUploadRecordFail) {
		return R.ok(kfwtUploadRecordFailService.save(kfwtUploadRecordFail));
	}

	/**
	 * 修改开封万通上传充装记录失败日志表
	 *
	 * @param kfwtUploadRecordFail 开封万通上传充装记录失败日志表
	 * @return R
	 */
	@ApiOperation(value = "修改开封万通上传充装记录失败日志表", notes = "修改开封万通上传充装记录失败日志表")
	@SysLog("修改开封万通上传充装记录失败日志表")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('admin_kfwtuploadrecordfail_edit')")
	public R updateById(@RequestBody KfwtUploadRecordFail kfwtUploadRecordFail) {
		return R.ok(kfwtUploadRecordFailService.updateById(kfwtUploadRecordFail));
	}

	/**
	 * 通过id删除开封万通上传充装记录失败日志表
	 *
	 * @param id id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除开封万通上传充装记录失败日志表", notes = "通过id删除开封万通上传充装记录失败日志表")
	@SysLog("通过id删除开封万通上传充装记录失败日志表")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('admin_kfwtuploadrecordfail_del')")
	public R removeById(@PathVariable Integer id) {
		return R.ok(kfwtUploadRecordFailService.removeById(id));
	}

	/**
	 * 通过ids再次上传充装记录
	 *
	 * @param ids ids
	 * @return R
	 */
	@ApiOperation(value = "通过ids再次上传充装记录", notes = "通过ids再次上传充装记录")
	@GetMapping("/againUpload")
	public R<Object> againUpload(String ids) {
		if (StringUtils.isBlank(ids)) {
			return R.failed("请选择要操作的数据");
		}
		log.info("againUpload ids :" + ids);
		String[] idss = ids.split(",");
		List<String> idlist = Arrays.asList(idss);
		List<KfwtUploadRecordFail> failLogs = kfwtUploadRecordFailService.listByIds(idlist);
		if (CollectionUtils.isEmpty(failLogs)) {
			return R.failed("所选数据有误");
		}
		List<Integer> recordIds = failLogs.stream().map(KfwtUploadRecordFail::getRecordId).collect(Collectors.toList());
		List<QpRecord> qpRecords = qpRecordService.listByIds(recordIds);
		wtService.addWtRecord(qpRecords);
		//删除原失败日志
		kfwtUploadRecordFailService.removeByIds(idlist);
		return R.ok();
	}

	/**
	 * 处理至今为止的历史数据
	 *
	 * @return R
	 */
	@ApiOperation(value = "处理至今为止的历史数据", notes = "处理至今为止的历史数据")
	@GetMapping("/allRecordUpload")
	public R<Object> allRecordUpload() {
		QueryWrapper<QpRecord> query = new QueryWrapper<>();
		query.ne("is_perform", "1");
		query.eq("del_flag", "0");
		query.eq("dept_id", 29);
		List<QpRecord> qpRecords = qpRecordService.list(query);
		wtService.addWtRecord(qpRecords);
		return R.ok();
	}
}
