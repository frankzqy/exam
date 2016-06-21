package org.inspur.exam.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.inspur.exam.data.Bonus;
import org.inspur.exam.data.Staff;
import org.inspur.exam.service.IBonusService;
import org.inspur.exam.service.IStaffTaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/download/xsl")
public class DownloadController {

	private static Log log = LogFactory.getLog(DownloadController.class);

	private static String templateDownloadFilepath = "/WEB-INF/download/exam/";
	private static String templateMonthFile = "MonthWages.xls";
	private static String templateMonthFileName = "员工月实发工资";
	private static String templateYearFile = "YearBonus.xls";
	private static String templateYearFileName = "年终奖最优避税方案";

	private static String importSalaryTemplateFile = "SalaryTemplate.xls";
	private static String importBonusTemplateFile = "BonusTemplate.xls";

	@Autowired
	IStaffTaxService staffTaxService;
	@Autowired
	IBonusService bonusService;

	@RequestMapping("/wages")
	private void downloadMonthWages(HttpServletRequest request,
			HttpServletResponse response) {
		String filename = templateMonthFile;
		String filetype = filename.substring(filename.lastIndexOf(".") + 1)
				.trim();
		try {
			// 读模板文件
			String templatefilepath = request.getRealPath("")
					+ templateDownloadFilepath + filename;
			InputStream inStream = new FileInputStream(templatefilepath);
			Workbook workbook = null;
			if ("xlsx".equals(filetype)) {// Excel 2007
				workbook = new XSSFWorkbook(inStream);
			} else if ("xls".equals(filetype)) {// Excel 2003
				workbook = (Workbook) new HSSFWorkbook(inStream);
			}

			List<Staff> wageslist = staffTaxService.getStaffList(null);
			if (wageslist != null && !wageslist.isEmpty()) {
				// 取第一个sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 每个单元格添加边框
				CellStyle cellstyle = workbook.createCellStyle();
				cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				// 已有总行数
				int addIdx = sheet.getPhysicalNumberOfRows();
				// 将DB中的员工信息写入excel
				Row row = null;
				Cell cell = null;
				int cellIdx = 0;
				for (int i = 0; i < wageslist.size(); i++) {
					Staff tmpStaff = (Staff) wageslist.get(i);
					row = sheet.createRow(addIdx);
					cellIdx = 0;
					// 工号
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpStaff.getStaffId());
					// 姓名
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpStaff.getName());
					// 月工资标准
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpStaff.getWages());
					// 应纳税额
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpStaff.getWages()
							- tmpStaff.getAfterTax());
					// 实发工资
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpStaff.getAfterTax());
					addIdx++;
				}
			}

			String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
					.getInstance().getTime());
			String realfilename = templateMonthFileName + "_" + curDate;
			if ("xlsx".equals(filetype)) {// Excel2007
				realfilename = realfilename + ".xlsx";
			} else if ("xls".equals(filetype)) {// Excel2003
				realfilename = realfilename + ".xls";
			}

			String realfilepath = request.getRealPath("")
					+ templateDownloadFilepath + realfilename;
			OutputStream outputStream = new FileOutputStream(realfilepath);
			workbook.write(outputStream);
			outputStream.close();

			InputStream fis = new BufferedInputStream(new FileInputStream(
					realfilepath));
			byte[] fileContent = new byte[fis.available()];
			fis.read(fileContent);
			fis.close();

			// 写文件
			writeToClient(realfilename, fileContent, response);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/bonus")
	private void downloadYearBonus(HttpServletRequest request,
			HttpServletResponse response) {
		String filename = templateYearFile;
		String filetype = filename.substring(filename.lastIndexOf(".") + 1)
				.trim();
		try {
			// 读模板文件
			String templatefilepath = request.getRealPath("")
					+ templateDownloadFilepath + filename;
			InputStream inStream = new FileInputStream(templatefilepath);
			Workbook workbook = null;
			if ("xlsx".equals(filetype)) {// Excel 2007
				workbook = new XSSFWorkbook(inStream);
			} else if ("xls".equals(filetype)) {// Excel 2003
				workbook = (Workbook) new HSSFWorkbook(inStream);
			}
			Map map = new HashMap();
			map.put("orderSql", "staffId");
			List<Bonus> bonuslist = bonusService.findByCondition(map);
			if (bonuslist != null && !bonuslist.isEmpty()) {
				// 取第一个sheet
				Sheet sheet = workbook.getSheetAt(0);
				// 每个单元格添加边框
				CellStyle cellstyle = workbook.createCellStyle();
				cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				// 已有总行数
				int startRow = sheet.getPhysicalNumberOfRows();
				// 将DB中的员工信息写入excel
				Row row = null;
				Cell cell = null;
				int cellIdx = 0;
				int addIdx = startRow;
				int endRow = bonuslist.size();
				for (int i = 0; i < endRow; i++) {
					Bonus tmpbonus = (Bonus) bonuslist.get(i);
					row = sheet.createRow(addIdx);
					cellIdx = 0;
					// 工号
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getStaffId());
					// 姓名
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getStaffName());
					// 月工资标准
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getWages());
					// 年终奖
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getTotalBonus());
					// 应纳税额
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getTotalTax());
					// 应发年终奖
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getAfterTaxBonus());

					// 最优避税
					// 综合计税应发年终奖
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getBonus1());
					// 第1个月随工资发放奖金
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getBonus2());
					// 第2个月随工资发放奖金
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getBonus3());
					// 避税金额
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getEscapeTax());
					// 实发年终奖
					cell = row.createCell(cellIdx++);
					cell.setCellStyle(cellstyle);
					cell.setCellValue(tmpbonus.getAfterEscapeBonus());

					addIdx++;
				}

				CellStyle greyStyle = workbook.createCellStyle();
				greyStyle
						.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
				greyStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
				greyStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				greyStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				greyStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				greyStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);

				// 合计行
				row = sheet.createRow(addIdx);
				cellIdx = 0;
				// 工号
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("合计");
				// 姓名
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("-");
				// 月工资标准
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("-");
				// 年终奖
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellFormula("SUM(D" + startRow + ":D"
						+ (startRow + endRow) + ")");
				// 应纳税额
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellFormula("SUM(E" + startRow + ":E"
						+ (startRow + endRow) + ")");
				// 应发年终奖
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellFormula("SUM(F" + startRow + ":F"
						+ (startRow + endRow) + ")");

				// 最优避税
				// 综合计税应发年终奖
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("-");
				// 第1个月随工资发放奖金
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("-");
				// 第2个月随工资发放奖金
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellValue("-");
				// 避税金额
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellFormula("SUM(J" + startRow + ":J"
						+ (startRow + endRow) + ")");
				// 实发年终奖
				cell = row.createCell(cellIdx++);
				cell.setCellStyle(greyStyle);
				cell.setCellFormula("SUM(K" + startRow + ":K"
						+ (startRow + endRow) + ")");
			}

			String curDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar
					.getInstance().getTime());
			String realfilename = templateYearFileName + "_" + curDate;
			if ("xlsx".equals(filetype)) {// Excel2007
				realfilename = realfilename + ".xlsx";
			} else if ("xls".equals(filetype)) {// Excel2003
				realfilename = realfilename + ".xls";
			}

			String realfilepath = request.getRealPath("")
					+ templateDownloadFilepath + realfilename;
			OutputStream outputStream = new FileOutputStream(realfilepath);
			workbook.write(outputStream);
			outputStream.close();

			InputStream fis = new BufferedInputStream(new FileInputStream(
					realfilepath));
			byte[] fileContent = new byte[fis.available()];
			fis.read(fileContent);
			fis.close();

			// 写文件
			writeToClient(realfilename, fileContent, response);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping("/bonusTemplate")
	private void downloadYearBonusTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String realfilepath = request.getRealPath("")
					+ templateDownloadFilepath + importBonusTemplateFile;
			// path是指欲下载的文件的路径。
			File file = new File(realfilepath);
			// 取得文件名。
			String filename = "员工年终奖.xls";

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					realfilepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			
			// 写文件
			writeToClient(filename, buffer, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping("/wagesTemplate")
	private void downloadWagesTemplate(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String realfilepath = request.getRealPath("")
					+ templateDownloadFilepath + importSalaryTemplateFile;
			// path是指欲下载的文件的路径。
			File file = new File(realfilepath);
			// 取得文件名。
			String filename = "员工月基本工资.xls";

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(
					realfilepath));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 写文件
			writeToClient(filename, buffer, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void writeToClient(String filename, byte[] fileContent,
			HttpServletResponse response) {
		// 清空response
		response.reset();
		// 设置response的Header
		try {
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(filename.getBytes("utf-8"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			response.setHeader("Content-Disposition", "attachment; filename="
					+ filename);
		}
		response.addHeader("Content-Length", "" + fileContent.length);
		try {
			OutputStream os = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			os.write(fileContent);
			os.flush();
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}