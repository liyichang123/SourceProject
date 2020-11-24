/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pigx.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.api.entity.SysFile;
import com.pig4cloud.pigx.admin.dto.AppFileDto;
import com.pig4cloud.pigx.admin.mapper.SysFileMapper;
import com.pig4cloud.pigx.admin.service.SysFileService;
import com.pig4cloud.pigx.common.core.constant.CommonConstants;
import com.pig4cloud.pigx.common.core.util.R;
import com.pig4cloud.pigx.common.minio.service.MinioTemplate;
import com.pig4cloud.pigx.common.security.util.SecurityUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.pig4cloud.pigx.common.core.constant.CommonConstants.STATUS_NORMAL;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {
	private final MinioTemplate minioTemplate;
	private final SysFileMapper sysFileMapper;


	/**
	 * 上传文件
	 *
	 * @param file
	 * @return
	 */
	@Override
	public R uploadFile(MultipartFile file) {
		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", CommonConstants.BUCKET_NAME);
		resultMap.put("fileName", fileName);
		resultMap.put("url", String.format("/admin/sys-file/%s/%s", CommonConstants.BUCKET_NAME, fileName));

		try {
			minioTemplate.putObject(CommonConstants.BUCKET_NAME, fileName, file.getInputStream());
			//文件管理数据记录,收集管理追踪文件
			fileLog(file, fileName, null);
		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	/**
	 * 读取文件
	 *
	 * @param bucket
	 * @param fileName
	 * @param response
	 */
	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (InputStream inputStream = minioTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(inputStream, response.getOutputStream());
		} catch (Exception e) {
			log.error("文件读取异常", e);
		}
	}


	/**
	 * 删除文件
	 *
	 * @param id
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFile(Long id) {
		SysFile file = this.getById(id);
		minioTemplate.removeObject(CommonConstants.BUCKET_NAME, file.getFileName());
		return this.removeById(id);
	}

	/**
	 * 根据版本号查询 apk上传记录 ，若版本号为空则查全部，若不为空，则查版本号匹配数据 <br>
	 *
	 * @param version : 版本号
	 * @Return: List<SysFile>
	 * @Author: gmx
	 * @Date: 2020/5/11 14:52
	 */
	@Override
	public R getApkList(String version) {
		if (StringUtils.isBlank(version)) {
			QueryWrapper<SysFile> wrapper = Wrappers.query();
			wrapper.eq("create_user", "qpadmin")
					.eq("type", "apk")
					.orderByAsc("del_flag")
					.orderByDesc("create_time");
			List<SysFile> sysFiles = sysFileMapper.selectList(wrapper);
			if (CollectionUtils.isNotEmpty(sysFiles)) {
				return R.ok(sysFiles.stream().map(this::convertorToAppFile).collect(Collectors.toList()));
			}
		} else {
			QueryWrapper<SysFile> wrapper = Wrappers.query();
			wrapper.eq("create_user", "qpadmin")
					.eq("del_flag", Integer.parseInt(STATUS_NORMAL))
					.like("original", version);
			List<SysFile> sysFiles = sysFileMapper.selectList(wrapper);
			if (CollectionUtils.isNotEmpty(sysFiles)) {
				List<AppFileDto> list = new ArrayList<>();
				list.add(convertorToAppFile(sysFiles.get(0)));
				return R.ok(list);
			}
		}
		return R.ok("暂无数据");
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 *
	 * @param file     上传文件格式
	 * @param fileName 文件名
	 * @param content  文件描述，备注（目前上传apk所用）
	 */
	private void fileLog(MultipartFile file, String fileName, String content) {
		SysFile sysFile = new SysFile();
		//原文件名
		String original = file.getOriginalFilename();
		sysFile.setFileName(fileName);
		sysFile.setOriginal(original);
		sysFile.setFileSize(file.getSize());
		sysFile.setType(FileUtil.extName(original));
		sysFile.setBucketName(CommonConstants.BUCKET_NAME);
		sysFile.setCreateUser(SecurityUtils.getUser().getUsername());
		sysFile.setRemark(content);
		this.save(sysFile);
	}

	private AppFileDto convertorToAppFile(SysFile sysFile) {
		AppFileDto appFileDto = new AppFileDto();
		BeanUtils.copyProperties(sysFile, appFileDto);
		String original = sysFile.getOriginal();
		try {
			String version = original.substring(8, original.lastIndexOf("."));
			appFileDto.setVersion(version);
		} catch (Exception e) {
			log.error("获取版本号异常：", e);
		}
		String url = String.format("/admin/sys-file/%s/%s", CommonConstants.BUCKET_NAME, sysFile.getFileName());
		appFileDto.setDownUrl(url);
		return appFileDto;
	}
}
