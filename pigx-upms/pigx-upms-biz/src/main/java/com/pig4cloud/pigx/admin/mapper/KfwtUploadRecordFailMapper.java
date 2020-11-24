package com.pig4cloud.pigx.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pigx.admin.entity.KfwtUploadRecordFail;
import org.apache.ibatis.annotations.Param;

/**
 * 开封万通上传充装记录失败日志表
 *
 * @author guomingxi
 * @date 2020-09-14 14:54:33
 */
public interface KfwtUploadRecordFailMapper extends BaseMapper<KfwtUploadRecordFail> {

	IPage<KfwtUploadRecordFail> getPage(Page<KfwtUploadRecordFail> page, @Param("query") KfwtUploadRecordFail kfwtUploadRecordFail);
}
