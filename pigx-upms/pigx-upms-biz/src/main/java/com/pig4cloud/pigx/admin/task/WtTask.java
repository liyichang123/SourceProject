package com.pig4cloud.pigx.admin.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pig4cloud.pigx.admin.entity.QpRecord;
import com.pig4cloud.pigx.admin.service.QpRecordService;
import com.pig4cloud.pigx.admin.service.WtService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/22 15:53 <br>
 */
@AllArgsConstructor
@Component
@Slf4j
public class WtTask {

	private final WtService wtService;
	private final QpRecordService qpRecordService;

	@Scheduled(cron = "0 0 2 * * ?")
	public void addGasRecord() {
		log.info("addGasRecord 任务开始 -- - -");
		LocalDate localDate = LocalDate.now().minusDays(1);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String yesterday = localDate.format(formatters);
		QueryWrapper<QpRecord> query = new QueryWrapper<>();
		query.eq("del_flag", "0");
		query.eq("is_perform", "0");
		query.eq("dept_id", 29);
		query.ge("createDate", yesterday + " 00:00:00");
		query.le("createDate", yesterday + " 59:59:59");
		List<QpRecord> list = qpRecordService.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			wtService.addWtRecord(list);
		}
		log.info("addGasRecord 任务结束 -- - -");
	}
}
