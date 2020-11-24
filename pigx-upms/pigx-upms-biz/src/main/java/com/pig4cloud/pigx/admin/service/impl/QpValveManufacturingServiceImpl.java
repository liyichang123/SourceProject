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

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pigx.admin.entity.QpValveManufacturing;
import com.pig4cloud.pigx.admin.mapper.QpValveManufacturingMapper;
import com.pig4cloud.pigx.admin.service.QpValveManufacturingService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 气瓶阀门制造信息
 *
 * @author wh
 * @date 2020-04-22 22:51:43
 */
@Service
@AllArgsConstructor
@Slf4j
public class QpValveManufacturingServiceImpl extends ServiceImpl<QpValveManufacturingMapper, QpValveManufacturing> implements QpValveManufacturingService {
	private final  QpValveManufacturingMapper qpValveManufacturingMapper;
}
