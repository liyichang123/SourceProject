package com.pig4cloud.pigx.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.KfwtUploadRecordFail;
import com.pig4cloud.pigx.admin.mapper.KfwtUploadRecordFailMapper;
import com.pig4cloud.pigx.admin.service.KfwtUploadRecordFailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 开封万通上传充装记录失败日志表
 *
 * @author guomingxi
 * @date 2020-09-14 14:54:33
 */
@Service
@AllArgsConstructor
public class KfwtUploadRecordFailServiceImpl extends ServiceImpl<KfwtUploadRecordFailMapper, KfwtUploadRecordFail> implements KfwtUploadRecordFailService {

	private final KfwtUploadRecordFailMapper kfwtUploadRecordFailMapper;
	@Override
	public IPage<KfwtUploadRecordFail> getPage(Page<KfwtUploadRecordFail> page, KfwtUploadRecordFail kfwtUploadRecordFail) {
		return kfwtUploadRecordFailMapper.getPage(page, kfwtUploadRecordFail);
	}
}
