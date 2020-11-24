package com.pig4cloud.pigx.admin.util.excel;

import com.pig4cloud.pigx.admin.entity.QpGascylinderrecord;
import com.pig4cloud.pigx.common.core.constant.enums.QpStatusEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <Description> <br>
 *
 * @author guomingxi<br>
 * @date 2020/09/14 16:27 <br>
 */
public class ExcelWriter {

	private static List<String> CELL_HEADS; //列头

	static {
		// 类装载时就载入指定好的列头信息，如有需要，可以考虑做成动态生成的列头
		CELL_HEADS = new ArrayList<>();
		CELL_HEADS.add("登记证编号");
		CELL_HEADS.add("设备品种");
		CELL_HEADS.add("单位内编号");
		CELL_HEADS.add("产品编号");
		CELL_HEADS.add("标签号");
		CELL_HEADS.add("二维码号");
		CELL_HEADS.add("制造单位");
		CELL_HEADS.add("制造日期");
		CELL_HEADS.add("检验日期");
		CELL_HEADS.add("下次检验日期");
		CELL_HEADS.add("充装介质");
		CELL_HEADS.add("容积(L)");
		CELL_HEADS.add("公称压力(MPa)");
		CELL_HEADS.add("气瓶状态");
		CELL_HEADS.add("是否打印");
		CELL_HEADS.add("气瓶制造许可证编号");
		CELL_HEADS.add("丙酮规定充装量（kg）");
		CELL_HEADS.add("最大乙炔量（kg）");
	}

	/**
	 * 生成Excel并写入数据信息
	 *
	 * @param dataList 数据列表
	 * @return 写入数据后的工作簿对象
	 */
	public static Workbook exportData(List<QpGascylinderrecord> dataList) {
		// 生成xlsx的Excel
		//Workbook workbook = new SXSSFWorkbook();

		// 如需生成xls的Excel，请使用下面的工作簿对象，注意后续输出时文件后缀名也需更改为xls
		Workbook workbook = new HSSFWorkbook();

		// 生成Sheet表，写入第一,二行的列头并合并
		Sheet sheet = buildDataSheet(workbook);
		//构建每行的数据内容
		int rowNum = 3;
		for (QpGascylinderrecord data : dataList) {
			if (data == null) {
				continue;
			}
			//输出行数据
			Row row = sheet.createRow(rowNum++);
			convertDataToRow(data, row);
		}
		return workbook;
	}

	/**
	 * 生成sheet表，并写入第一行数据（列头）
	 *
	 * @param workbook 工作簿对象
	 * @return 已经写入列头的Sheet
	 */
	private static Sheet buildDataSheet(Workbook workbook) {
		Sheet sheet = workbook.createSheet();
		// 设置列头宽度
		for (int i = 0; i < CELL_HEADS.size(); i++) {
			sheet.setColumnWidth(i, 4000);
		}
		// 设置默认行高
		sheet.setDefaultRowHeight((short) 400);
		// 构建头单元格样式
		CellStyle cellStyle = buildHeadCellStyle(sheet.getWorkbook());
		//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, CELL_HEADS.size() - 1));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, CELL_HEADS.size() - 1));
		//第一行数据
		Row head0 = sheet.createRow(0);
		Cell cell0 = head0.createCell(0);
		cell0.setCellValue("导入气瓶档案模板");
		cell0.setCellStyle(cellStyle);
		//第二行数据
		Row head1 = sheet.createRow(1);
		Cell cell1 = head1.createCell(0);
		cell1.setCellValue("导入人：");
		// 写入第三行各列的数据
		Row head = sheet.createRow(2);
		for (int i = 0; i < CELL_HEADS.size(); i++) {
			Cell cell = head.createCell(i);
			cell.setCellValue(CELL_HEADS.get(i));
			cell.setCellStyle(cellStyle);
		}
		return sheet;
	}

	/**
	 * 设置第一行列头的样式
	 *
	 * @param workbook 工作簿对象
	 * @return 单元格样式对象
	 */
	private static CellStyle buildHeadCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		//对齐方式设置
		style.setAlignment(HorizontalAlignment.CENTER);
		//边框颜色和宽度设置
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 下边框
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边框
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex()); // 右边框
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex()); // 上边框
		//设置背景颜色
		style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		//粗体字设置
		Font font = workbook.createFont();
		font.setBold(true);
		style.setFont(font);
		return style;
	}

	/**
	 * 将数据转换成行
	 *
	 * @param data 源数据
	 * @param row  行对象
	 */
	private static void convertDataToRow(QpGascylinderrecord data, Row row) {
		Cell cell;
		// 登记证编号 (登记证标识编号)
		cell = row.createCell(0);
		cell.setCellValue(StringUtils.isNotBlank(data.getRegistrationMarkNo()) ? data.getRegistrationMarkNo() : "");
		// 设备品种 (气瓶类型)
		cell = row.createCell(1);
		cell.setCellValue(StringUtils.isNotBlank(data.getGascylindertypeName()) ? data.getGascylindertypeName() : "");
		// 单位内编号 (追溯系统中的气瓶id)
		cell = row.createCell(2);
		cell.setCellValue(null != data.getId() ? data.getId().toString() : "");
		// 产品编号  (瓶身编号)
		cell = row.createCell(3);
		cell.setCellValue(StringUtils.isNotBlank(data.getGascylindercode()) ? data.getGascylindercode() : "");
		String bq = "";
		String ewm = "";
		if (StringUtils.isNotBlank(data.getElectroniclabeltype())) {
			if ("1".equals(data.getElectroniclabeltype())) {
				bq = StringUtils.isNotBlank(data.getElectroniclabel()) ? data.getElectroniclabel() : "";
			} else {
				ewm = StringUtils.isNotBlank(data.getElectroniclabel()) ? data.getElectroniclabel() : "";
			}
		}
		// 标签号 (标签)
		cell = row.createCell(4);
		cell.setCellValue(bq);
		// 二维码 (二维码)
		cell = row.createCell(5);
		cell.setCellValue(ewm);
		// 制造单位 (制造厂家)
		cell = row.createCell(6);
		cell.setCellValue(StringUtils.isNotBlank(data.getFactoryName()) ? data.getFactoryName() : "");
		// 制造日期 (制造日期)
		cell = row.createCell(7);
		String facturedate = "";
		if (null != data.getManufacturedate()) {
			facturedate = dateFmt(data.getManufacturedate());
		}
		cell.setCellValue(facturedate);
		// 检验日期 (上检日期)
		cell = row.createCell(8);
		String checkDate = "";
		if (null != data.getManufacturedate()) {
			checkDate = dateFmt(data.getCheckdate());
		}
		cell.setCellValue(checkDate);
		// 下次检验日期 (下次检验日期)
		cell = row.createCell(9);
		String nextcheckDate = "";
		if (null != data.getManufacturedate()) {
			nextcheckDate = dateFmt(data.getNextcheckdate());
		}
		cell.setCellValue(nextcheckDate);
		// 充装介质 (充装介质)
		cell = row.createCell(10);
		cell.setCellValue(StringUtils.isNotBlank(data.getMaterialName()) ? data.getMaterialName() : "");
		// 容积(L) (设计容积)
		cell = row.createCell(11);
		cell.setCellValue(null != data.getCapacity() ? data.getCapacity().toString() : "0");
		// 公称压力(MPa) (公称压力)
		cell = row.createCell(12);
		cell.setCellValue(null != data.getNominalPressure() ? data.getNominalPressure().toString() : "0");
		// 气瓶状态 (当前状态)
		cell = row.createCell(13);
		cell.setCellValue(null != data.getStatus() ? QpStatusEnum.getQpStatus(data.getStatus()) : "");
		// 是否打印
		cell = row.createCell(14);
		cell.setCellValue("");
		// 气瓶制造许可证编号 (制造许可)
		cell = row.createCell(15);
		cell.setCellValue(StringUtils.isNotBlank(data.getFactoryPermissionName()) ? data.getFactoryPermissionName() : "");
		// 丙酮规定充装量（kg） ()
		cell = row.createCell(16);
		cell.setCellValue("");
		// 最大乙炔量（kg） ()
		cell = row.createCell(17);
		cell.setCellValue("");
	}

	private static String dateFmt(Date date) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
}
