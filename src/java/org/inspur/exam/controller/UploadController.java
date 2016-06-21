package org.inspur.exam.controller;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.inspur.exam.data.Bonus;
import org.inspur.exam.data.Staff;
import org.inspur.exam.service.IBonusService;
import org.inspur.exam.service.IStaffTaxService;
import org.inspur.exam.util.TaxCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {

	@Autowired
	IStaffTaxService staffTaxService;
	@Autowired
	IBonusService bonusService;
	
	@RequestMapping(value = "/staff")
	public String upload(HttpServletRequest request) {			        		        
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
			Map<String,MultipartFile> files =multipartRequest.getFileMap();
			Iterator<String> fileNames=multipartRequest.getFileNames();
			while(fileNames.hasNext()) {
				InputStream  is=null;
				try{
					String name = new String(fileNames.next().getBytes("gb2312"),"ISO-8859-1");       
					CommonsMultipartFile file = (CommonsMultipartFile) files.get(name);  
					is=file.getInputStream();
					String filename =file.getFileItem().getName();	
					String type=filename.substring(filename.lastIndexOf(".")+1).trim();
					Workbook wb = null;
					if("xlsx".equals(type)){
    					wb = new XSSFWorkbook(is);//Excel 2007
    				}else if("xls".equals(type)){
    					wb = (Workbook) new HSSFWorkbook(is);//Excel 2003
    				}					
					int totalSheets = wb.getNumberOfSheets();
					Sheet sheet = wb.getSheetAt(0);//一页工作表
					int totalRows = sheet.getPhysicalNumberOfRows();// 总行数					
					for (int i = 5; i < totalRows; i++) {
						Row row = sheet.getRow(i);//取一行
						Staff staff=new Staff();
						int totalCells = row.getPhysicalNumberOfCells();// 总列数	
						String staffId=getCellValue(row.getCell(0));
						if(staffTaxService.findOneStaff(staffId)!=null){
							staffTaxService.deleteStaff(staffId);
						}
						staff.setStaffId(getCellValue(row.getCell(0)));
						staff.setName(getCellValue(row.getCell(1)));
						double wage=Double.valueOf(getCellValue(row.getCell(2)));
						staff.setWages(wage);
						staff.setAfterTax(wage-Double.valueOf(String.format("%.2f",TaxCalculator.getTax(wage))));
						staffTaxService.addStaff(staff);
					}
					
				}catch (Exception e) {
					System.out.println(e);
				}finally{
					if(is!=null){
						is.close();
					}
				}
			}
			
		}catch(Throwable t){
			//logger.error("", t);
			//status=0;
			System.out.println(t);
		}		
		return null;
		//return JSONUtil.bean2Json(userdata);
	}
	
	@RequestMapping(value = "/bonus")
	public String uploadBonus(HttpServletRequest request) {			        		        
		try{
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
			Map<String,MultipartFile> files =multipartRequest.getFileMap();
			Iterator<String> fileNames=multipartRequest.getFileNames();
			while(fileNames.hasNext()) {
				InputStream  is=null;
				try{
					String name = new String(fileNames.next().getBytes("gb2312"),"ISO-8859-1");       
					CommonsMultipartFile file = (CommonsMultipartFile) files.get(name);  
					is=file.getInputStream();
					String filename =file.getFileItem().getName();	
					String type=filename.substring(filename.lastIndexOf(".")+1).trim();
					Workbook wb = null;
					if("xlsx".equals(type)){
    					wb = new XSSFWorkbook(is);//Excel 2007
    				}else if("xls".equals(type)){
    					wb = (Workbook) new HSSFWorkbook(is);//Excel 2003
    				}					
					int totalSheets = wb.getNumberOfSheets();
					Sheet sheet = wb.getSheetAt(0);//一页工作表
					int totalRows = sheet.getPhysicalNumberOfRows();// 总行数
					
					for (int i = 5; i < totalRows; i++) {
						Row row = sheet.getRow(i);//取一行
						Bonus bonus=new Bonus();
						int totalCells = row.getPhysicalNumberOfCells();// 总列数						
						String staffId=getCellValue(row.getCell(0));
						double wages=Double.valueOf(getCellValue(row.getCell(2)));
						double totalBonus=Double.valueOf(getCellValue(row.getCell(3)));
						double[] result=TaxCalculator.getOptimalSolution(wages, totalBonus, true);
						List staffList=bonusService.findByStaffId(staffId);
						if(staffList.size()>0){
							bonusService.deleteBonusByStaffId(staffId);
						}
						bonus.setStaffId(staffId);
						bonus.setStaffName(getCellValue(row.getCell(1)));
						bonus.setWages(wages);
						bonus.setTotalBonus(totalBonus);			
						bonus.setBonus1(result[0]);
						bonus.setBonus2(result[1]);
						bonus.setBonus3(result[2]);
						bonus.setEscapeTax(result[5]);
						bonus.setAfterEscapeBonus(result[3]);
						bonus.setTotalTax(Double.parseDouble(String.format("%.2f",TaxCalculator.computeBonusTax(wages, totalBonus))));
						bonus.setAfterTaxBonus(Double.parseDouble(String.format("%.2f",totalBonus-TaxCalculator.computeBonusTax(wages,totalBonus))));
						bonusService.save(bonus);
					}
					
				}catch (Exception e) {
					System.out.println(e);
				}finally{
					if(is!=null){
						is.close();
					}
				}
			}
			
		}catch(Throwable t){
			//logger.error("", t);
			//status=0;
			System.out.println(t);
		}		
		return null;
		//return JSONUtil.bean2Json(userdata);
	}
	
	
	
	public String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				value = cell.getNumericCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getNumericCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
			}
		}

		return value;
	}
}
