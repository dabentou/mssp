package com.mmd.mssp.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mmd.mssp.service.impl.ExcelServiceImpl;


public class ExcelUtils {

	public static byte[] exportExl(String sheetName,String[] headers, List<Map<Integer,Object>> content ){
		Workbook workbook = doExcel(null, sheetName, headers, content);
		return getExcelByteArray(workbook);
	}
	public static void exportExl(String sheetName,String[] headers, List<Map<Integer,Object>> content,OutputStream output ){
		Workbook workbook = doExcel(null, sheetName, headers, content);
		getExcelByteArray(workbook,output);
	}
	
	public static Workbook doExcel(Workbook wb, String sheetName, String[] headers, List<Map<Integer,Object>> content){
		// 创建excel工作簿
		Workbook workbook = null;
		if(null == wb){
			workbook = new XSSFWorkbook();// ".xlsx":2007以上  (// "xls":97-2003  workbook = new HSSFWorkbook();)
		}else{
			workbook = wb;
		}
		
		// 创建第一个sheet（页），并命名
		Sheet sheet = null;
		if(null == sheetName){
			sheet = workbook.createSheet("sheet");
		}else{
			sheet = workbook.createSheet(sheetName);
		}
		
		//产生表格标题行
		Row headerRow = sheet.createRow(0);
		
		if(headers != null){
			//表头样式
			CellStyle headerStyle = getStyle(workbook,true);
			for (short i = 0; i < headers.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(headerStyle);
			}
		}
		
		//填充内容
		fillContent(workbook, sheet, 1, false, content);
		
		//调整列宽
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		return workbook;
	}
	
	public static Workbook doExcel(Workbook wb, String sheetName, List<Map<Integer,Object>> headers, List<Map<Integer,Object>> content){
		// 创建excel工作簿
		Workbook workbook = null;
		if(null == wb){
			workbook = new XSSFWorkbook();// ".xlsx":2007以上  (// "xls":97-2003  workbook = new HSSFWorkbook();)
		}else{
			workbook = wb;
		}
		
		// 创建第一个sheet（页），并命名
		Sheet sheet = null;
		if(null == sheetName){
			sheet = workbook.createSheet("sheet");
		}else{
			sheet = workbook.createSheet(sheetName);
		}
		
		//填充表头
		fillContent(workbook, sheet, 0, true, headers);
		int beginRow = headers==null?0:headers.size();
		//填充内容
		fillContent(workbook, sheet, beginRow, false, content);
		
		int length = 0;
		if(null != headers && !headers.isEmpty()){
			length = headers.get(0).size();
			try {
				String str = (String)headers.get(0).get(length-1);
				if(str.contains("图片")){
					--length;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//调整列宽
		for (int i = 0; i < length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		return workbook;
	}
	
	public static void fillContent(Workbook workbook,Sheet sheet,Integer begingRow, boolean isBold,List<Map<Integer,Object>> content){
		if(content != null){
			//填充内容
			Row row = null;
			//内容样式
			CellStyle contentStyle = getStyle(workbook,isBold);
			for (int rowNum = 0; rowNum < content.size(); rowNum++) {
				//创建行
				int lastRowNum = sheet.getLastRowNum();
				row=sheet.createRow(lastRowNum+1);
				Map<Integer,Object> rowValue = content.get(rowNum);
				if(null != rowValue){
					Object value = null;
					Cell cell = null;
					for (int columnNum = 0; columnNum < rowValue.size(); columnNum++) {
						value = rowValue.get(columnNum);//获取值
						cell = row.createCell((short)columnNum);//创建单元格
						if(value instanceof List){
							ExcelServiceImpl.setList(workbook, sheet, value, columnNum, cell, ExcelServiceImpl.getCellStyle(workbook, workbook.getCreationHelper()));
						}else{
							writeValueToCell(value, cell);//给单元格赋值
							cell.setCellStyle(contentStyle);//设置样式
						}
					}
				}  
			}
		}
	}
	
	public static byte[] getExcelByteArray(Workbook workbook){
		ByteArrayOutputStream out = null;
		byte[] byteArray = null;
		try {
			out = new ByteArrayOutputStream();
			workbook.write(out);
			out.flush();
			byteArray = out.toByteArray();
			return byteArray;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return byteArray;
	}
	public static void getExcelByteArray(Workbook workbook,OutputStream  output){
		OutputStream out = null;
		try {
			//out = new ByteArrayOutputStream();
			out = output;
			workbook.write(out);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static CellStyle getStyle(Workbook workbook,boolean isBold){
		CellStyle style = workbook.createCellStyle();
		style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);   
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font font = workbook.createFont();
		font.setFontName("宋体");
		if(isBold){
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		style.setFont(font);
		
		return style;
	}
	
	
	public static void writeValueToCell(Object value, Cell cell){
		if(null == value){
			cell.setCellValue("");
		}else{
			if("class java.lang.String".equalsIgnoreCase(value.getClass().toString())){
				cell.setCellValue((String) value);
			}else if("class java.lang.Short".equalsIgnoreCase(value.getClass().toString()) || "short".equalsIgnoreCase(value.getClass().toString())){
				cell.setCellValue((Short) value);
			}else if("class java.lang.Integer".equalsIgnoreCase(value.getClass().toString()) || "int".equalsIgnoreCase(value.getClass().toString())){
				cell.setCellValue((Integer) value);
			}else if("class java.lang.Double".equalsIgnoreCase(value.getClass().toString()) || "double".equalsIgnoreCase(value.getClass().toString())){
				cell.setCellValue((Double) value);
			}else if("class java.lang.Long".equalsIgnoreCase(value.getClass().toString()) || "long".equalsIgnoreCase(value.getClass().toString())){
				cell.setCellValue((Long) value);
			}else if("class java.util.Date".equalsIgnoreCase(value.getClass().toString())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				cell.setCellValue(sdf.format((Date) value));
			}else if("class org.apache.openjpa.util.java$util$Date$proxy".equalsIgnoreCase(value.getClass().toString())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				cell.setCellValue(sdf.format((Date) value));
			}
		}
	}
	
}
