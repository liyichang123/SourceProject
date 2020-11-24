package com.pig4cloud.pigx.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pigx.admin.entity.KfwtUploadRecordFail;

/**
 * 开封万通上传充装记录失败日志表
 *
 * @author guomingxi
 * @date 2020-09-14 14:54:33
 */
public interface KfwtUploadRecordFailService extends IService<KfwtUploadRecordFail> {

	 IPage<KfwtUploadRecordFail> getPage(Page<KfwtUploadRecordFail> page, KfwtUploadRecordFail kfwtUploadRecordFail);
}
