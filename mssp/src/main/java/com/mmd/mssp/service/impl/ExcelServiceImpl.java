package com.mmd.mssp.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.SampleInOutLog;
import com.mmd.mssp.domain.SampleInventory;
import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.vo.AttachmentVo;
import com.mmd.mssp.service.B2BProjectService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.SampleInOutLogService;
import com.mmd.mssp.service.SpecialApplyProductService;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Resource
	B2BProjectService b2BProjectService;
	
	@Resource
	SpecialApplyProductService specialApplyProductService;
	
	@Resource
	SampleInOutLogService sampleInOutLogService;
	
	@Override
	public String convert2XlsAndSave2Disk(List<Map> list) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convert2Cvs(Map<String,String> titleMapper,List<Map> list) {
		StringBuffer sb = new StringBuffer();
		Set<String> set = titleMapper.keySet();
		for(String str:set){
			String value =(String) titleMapper.get(str);
			sb.append(value+",");
		}
		sb.append("\r\n");
		if(list.size()>0){
			for(Map<String, String> oneMap:list){
				for(String str:set){
					String value = String.valueOf(oneMap.get(str));
					if(value==null){
						value ="";
					}
					sb.append(value+",");
				}
				sb.append("\r\n");
			}
		}
		return sb.toString();
	}

	@Override
	public <T> String convert2CvsByObject(Map<String, String> titleMapper,
			List<T> list) {
		StringBuffer sb = new StringBuffer();
		Set<String> set = titleMapper.keySet();
		for(String str:set){
			String value =(String) titleMapper.get(str);
			sb.append(value+",");
		}
		sb.append("\n");
		if(list.size()>0){
			for(T t:list){
				for(String str:set){
					Field field;
					String value ="";
					try {
						field = t.getClass().getDeclaredField(str);
					if(field==null){
						value ="";
					}else{
						value = String.valueOf(field.get(t));
					}
					} catch (NoSuchFieldException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sb.append(value+",");
				}
				sb.append("\n");
;
			}
		}
		return null;
	}
	
	public Workbook convert2Excel(Workbook workb,Sheet mySheet, Integer rowIndex,String sheetName,Map<String,String> titleMapper,List<Map> list){
		Workbook wb= workb;
		if(workb==null){
			wb = new XSSFWorkbook();
		}
		Sheet sheet = mySheet;
		CreationHelper createHelper = wb.getCreationHelper();
		/*if(mySheet==null){
			sheet = wb.createSheet("sheet1");
		}*/
		if(StringUtils.isBlank(sheetName)){
			sheet = wb.createSheet("sheet1");
		}else{
			sheet = wb.createSheet(sheetName);
		}
		//创建一个样式                                            
		CellStyle style = getCellStyle(wb, createHelper);
		
		 Row row=sheet.createRow(rowIndex==null?0:rowIndex);     
		 //row.setHeightInPoints((sheet.getDefaultRowHeightInPoints()));
		 Set<String> set = titleMapper.keySet();
		 int i = 0;
		 for(String str:set){
				String value =(String) titleMapper.get(str);
				Cell cell=row.createCell(i);
				cell.setCellValue(value);
				CellStyle cellStyle = wb.createCellStyle();
				cellStyle.cloneStyleFrom(style);
				Font font=wb.createFont();  
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				cellStyle.setFont(font);
				sheet.autoSizeColumn(i);
				cell.setCellStyle(cellStyle);
				i++;
		 }
		 for(int j=0;j<list.size();j++){
			int lastRowNum = sheet.getLastRowNum();
			row=sheet.createRow(lastRowNum+1); 
			Map map = list.get(j);
			int k = 0;
			for(String str:set){
				Cell cell=row.createCell(k);
				Object obj = map.get(str);
				if(obj instanceof Date){
					Date date = (Date)obj;
					cell.setCellValue(date);
					CellStyle cellStyle = wb.createCellStyle();
					cellStyle.cloneStyleFrom(style);
					cell.setCellStyle(cellStyle);
					sheet.autoSizeColumn(k);
				}else if(obj instanceof List){
					setList(wb, sheet, obj, k, cell, style);
				}else{
					if(obj!=null){
						cell.setCellValue(String.valueOf(obj));
						CellStyle cellStyle = wb.createCellStyle();
						cellStyle.cloneStyleFrom(style);
						sheet.autoSizeColumn(k);
						cell.setCellStyle(cellStyle);
					}
				}
				k++;
			}
		 }
		return wb;
		
	}
	
	public static CellStyle getCellStyle(Workbook wb,CreationHelper createHelper){
		 CellStyle style=wb.createCellStyle();                
		//设置这些样式                                          
		 style.setFillForegroundColor(IndexedColors.WHITE.index);
		 style.setFillBackgroundColor(IndexedColors.WHITE.index);  
		 style.setFillPattern(CellStyle.SOLID_FOREGROUND);    
		 style.setBorderBottom(CellStyle.BORDER_THIN);        
		 style.setBorderLeft(CellStyle.BORDER_THIN);          
		 style.setBorderRight(CellStyle.BORDER_THIN);         
		 style.setBorderTop(CellStyle.BORDER_THIN);           
		 style.setAlignment(CellStyle.ALIGN_CENTER);   
		 style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		//创建一个字体                                            
		 //Font font=wb.createFont();                           
		 //font.setColor(new XSSFColor(Color.BLACK));                   
		 //font.setFontHeightInPoints((short)16);                   
		 //font.setBoldweight((short)2);
		//把字体应用到当前的样式                                  
		 //style.setFont(font);
		 style.setIndention((short)3);
		 //style.setWrapText(true);
		 style.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		 
		 return style;
	}
	
	public static void setImage(Workbook wb, Sheet sheet, Object obj, int k, Cell cell, CellStyle style){
		List l = (List)obj;
		int index = 0;
		//声明一个画图的顶级管理器                                
		 Drawing patriarch = sheet.createDrawingPatriarch();
		for(Object o :l){
			if(o instanceof AttachmentVo){
				AttachmentVo attachment = (AttachmentVo)o;
				String url = attachment.getUrl();
				BufferedImage img = null;
				try {
					if(url.contains("59.60.10.222:8008")){
						int ind= url.lastIndexOf("/");
						url = url.substring(ind+1,url.length());
						url = "file:///D:\\apache-tomcat-7.0.57\\webapps\\fileupload\\"+url;
					}
					img = ImageIO.read(new URL(url));
					insertImage(wb,patriarch,img,cell,index);
					CellStyle cellStyle = wb.createCellStyle();
					cellStyle.cloneStyleFrom(style);
					cell.setCellStyle(cellStyle);
					sheet.setColumnWidth(k+index, 1023*6);
					index++;
				} catch (MalformedURLException e) {
					e.printStackTrace();
					System.out.println(url);
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println(url);
				} catch(Exception e){
					e.printStackTrace();
					System.out.println(url);

				}
				
			}
		}
		sheet.setColumnWidth(k, 1023*6);
	}
	public static void setList(Workbook wb, Sheet sheet, Object obj, int k, Cell cell, CellStyle style){
		List l = (List)obj;
		if(l!=null&& l.size()>0){
			Object o  = l.get(0);
			if(o instanceof AttachmentVo){
				setImage(wb,sheet,obj,k,cell,style);
			}else if(o instanceof String){
				int index = 0;
				for(Object ob :l){
					String str = (String)ob;
					if(index>0){
						int i = cell.getSheet().getLastRowNum();
						Row r = cell.getSheet().createRow(i+1);
						Cell c  = r.createCell(k);
						CellStyle cellStyle = wb.createCellStyle();
						cellStyle.cloneStyleFrom(style);
						c.setCellStyle(cellStyle);
						c.setCellValue(str);
					}else{
						CellStyle cellStyle = wb.createCellStyle();
						cellStyle.cloneStyleFrom(style);
						style.setWrapText(true);
						cell.setCellStyle(style);
						cell.setCellValue(str);
					}
					index++;
				}
				sheet.autoSizeColumn(k);
			}
		}
	}
	   //自定义的方法,插入某个图片到指定索引的位置                                                                
	   private static void insertImage(Workbook wb,Drawing pa,BufferedImage bi,Cell cell,int index){
	       byte[] data = null;
	       try{                                                                                                   
	            ByteArrayOutputStream bout=new ByteArrayOutputStream();    
	            if(bi!=null){
	            	ImageIO.write(bi,"jpg",bout);                                                                     
	            	data =  bout.toByteArray();
	            }
	        }catch(Exception exe){                                                                                
	            exe.printStackTrace();                                                                            
	        }
	       Row row = cell.getRow();	
	       row.setHeight((short)2048);
	       XSSFClientAnchor anchor = new XSSFClientAnchor();
	       anchor.setCol1(cell.getColumnIndex()+index);
	       anchor.setCol2(cell.getColumnIndex()+1+index);
	       anchor.setRow1(row.getRowNum());
	       anchor.setRow2(row.getRowNum()+1);
	       anchor.setDx1(0);
	       anchor.setDy1(0);
	       anchor.setDx2(1023);
	       anchor.setDy2(512);
	       anchor.setAnchorType(2);   
	       if(data !=null){
	    	   Picture pict = pa.createPicture(anchor , wb.addPicture(data,Workbook.PICTURE_TYPE_JPEG));   
	       }
	       //pict.resize();
	    }

	@Override
	public List<Map> getPannelByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.getRichStringCellValue().getString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("pannelName", cellString);
		        	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getSeriesByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.getRichStringCellValue().getString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("seriesName", cellString);
		        	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getProductSeriesByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.getRichStringCellValue().getString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("name", cellString);
		        	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getProductByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 //String cellString =  cell.getRichStringCellValue().getString();
	        			 String cellString = cell.toString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("productName", cellString);
		        	         }else if(cell.getColumnIndex()==1){
		           	        	 map.put("financePrice", cellString);
		           	         }else if(cell.getColumnIndex()==2){
		           	        	 map.put("netPrice", cellString);
		           	         }else if(cell.getColumnIndex()==3){
		           	        	 map.put("poPrice", cellString);
		           	         }else if(cell.getColumnIndex()==4){
		           	        	 map.put("syPrice", cellString);
		           	         }else if(cell.getColumnIndex()==5){
		           	        	 map.put("seriesName", cellString);
		           	         }else if(cell.getColumnIndex()==6){
		           	        	 map.put("pannelName", cellString);
		           	         }else if(cell.getColumnIndex()==7){
		           	        	 map.put("productSeriesName", cellString);
		           	         }else if(cell.getColumnIndex()==8){
		           	        	 map.put("size1", cellString);
		           	         }else if(cell.getColumnIndex()==9){
		           	        	 map.put("size2", cellString);
		           	         }else if(cell.getColumnIndex()==10){
		           	        	 map.put("channelType", cellString);
		           	         }else if(cell.getColumnIndex()==11){
		           	        	 map.put("sellType", cellString);
		           	         }else if(cell.getColumnIndex()==12){
		           	        	 map.put("interPublicPrice", cellString);
		           	         }else if(cell.getColumnIndex()==13){
		           	        	 map.put("b2bPublicPrice", cellString);
		           	         }else if(cell.getColumnIndex()==14){
		           	        	 map.put("status", cellString);
		           	         }else if(cell.getColumnIndex()==15){
		           	        	 map.put("date", cellString);
		           	         }else if(cell.getColumnIndex()==16){
		           	        	 map.put("productFormat", cellString);
		           	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getAreaByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.getRichStringCellValue().getString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("name", cellString);
		        	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getCityByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.getRichStringCellValue().getString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("areaName", cellString);
		        	         }else if(cell.getColumnIndex()==1){
		           	        	 map.put("cityName", cellString);
		           	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void B2BProjectExcel(Integer id) {
		B2BProject project = b2BProjectService.findB2BProjectById(id);
		List<SpecialApplyProduct> SAPlist = specialApplyProductService.findSAPListByProject(project);
		List<SampleInOutLog> lists = sampleInOutLogService.findByProjectId(project.getId());
		List<B2BApproveLog> logList  = b2BProjectService.findApproveLogList(project);
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("项目报备");
		setTitle(wb, sheet, 0, "项目报备信息", 0, 0, 0, 4);
	    //项目信息
	    List<List<String>>  projectList = this.getProjectListMap(project);
	    setListList(wb, sheet, projectList, 1, projectList.size(), 0, 0);
	    //客户信息
	    List<List<String>> customerList = this.getCustomerListMap(project);
	    setListList(wb, sheet, customerList, 3, customerList.size()+2, 0, 0);
	    //销售信息
	    List<List<String>> spaList = this.getSAPListMap(SAPlist);
	    setListList(wb, sheet, spaList, 5, spaList.size()*2, 0, 0);
	    //代理商信息
	    List<List<String>> agentList = this.getAgentListMap(project);
	    setListList(wb, sheet, agentList, 9, 8+agentList.size(), 0, 0);
	    //收发货信息
	    List<List<String>> receiveList = this.getReceiveListMap(project);
	    setListList(wb, sheet, receiveList, 11, 10+receiveList.size(), 0, 0);
	    //销售信息
	    List<List<String>> sellList = this.getSellListMap(project);
	    setListList(wb, sheet, sellList, 23, 22+sellList.size(), 0, 0);
	    //审批记录
	    List<List<String>> approList = this.getApproListMap(logList);
	    
		int lastRow = sheet.getLastRowNum();
		//备注
		List<List<String>> remList = this.getRecordListMap(project);
		for(int i=0; i<remList.size(); i++){
			List<String> listValue = remList.get(i);
			Row row = sheet.createRow(lastRow+1);
			for(int j=0; j<listValue.size(); j++){
				Cell cell = row.createCell(j);
				cell.setCellValue(listValue.get(j));
				if(j==1){//合并单元格
					sheet.addMergedRegion(new CellRangeAddress(25, 25, 1, 4));
				    CellStyle cellStyleMeun = wb.createCellStyle();//获取样式对象
				    cellStyleMeun.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中 
				    cell.setCellStyle(cellStyleMeun);
				    sheet.autoSizeColumn(j);
				}
			}
			lastRow++;
		}
		lastRow = sheet.getLastRowNum();
		
		//设置样机申请标题
		setTitle(wb, sheet, lastRow+2, "样机申请", 27, 27, 0, 3);
		
	    Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("样机型号", "样机型号");
		map.put("样机总量", "样机总量");
		map.put("当前可使用数量", "当前可使用数量");
		map.put("申请数量", "申请数量");
		List<Map> sampleList = this.getSampleListMap(lists);
		wb = this.convert2Excel(wb,sheet, lastRow+3, "new sheet", map, sampleList);
		
		//设置样机借用协议
		lastRow = sheet.getLastRowNum();
		String Imageurl1 = project.getSampleDeal();
		setImage(wb, sheet, lastRow+2, "样机借用协议：", Imageurl1, 32, 32, 1, 3);
	    
		//设置授权申请标题
		lastRow = sheet.getLastRowNum();
		setTitle(wb, sheet, lastRow+3, "授权申请", 35, 35, 0, 3);
	    
	    lastRow = sheet.getLastRowNum();
	    String url = project.getCommitmentNote();
	    setImage(wb, sheet, lastRow+1, "代理商承诺函：", url, 36,36, 1, 3);
		//审批记录/操作
		lastRow = sheet.getLastRowNum();
		setTitle(wb, sheet, lastRow+2, "审批记录/操作", 38, 38, 0, 4);
	    
	    lastRow = sheet.getLastRowNum();
	    for(int i=0; i<approList.size(); i++){
	    	Row row = sheet.createRow(lastRow+1);
	    	List<String> list = approList.get(i);
	    	for(int j=0; j<list.size(); j++){
	    		Cell cell = row.createCell(j);
	    		cell.setCellValue(list.get(j));
	    	}
	    	lastRow++;
	    }
//	    String path = request.getSession().getServletContext().getRealPath("/")+"WEB-INF\\upload\\"+fileName;
	   	File file = new File("F:\\test.xls");
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 往指定位置插入List<List<String>>形式数据
	 * @param wb
	 * @param sheet
	 * @param list
	 * @param lastRowIndex 最后一行的索引
	 * @param firstRow 合并单元格 起始行
	 * @param lastRow 合并单元格 终止行
	 * @param firstCol 合并单元格 起始列
	 * @param lastCol 合并单元格 终止列
	 */
	private static void setListList(Workbook wb,Sheet sheet,List<List<String>> list,int firstRow, int lastRow, int firstCol, int lastCol){
		int lastRowIndex = sheet.getLastRowNum();
		for(int i=0; i<list.size(); i++){
			List<String> listValue = list.get(i);
			Row row = sheet.createRow(lastRowIndex+1);
			for(int j=0; j<listValue.size(); j++){
				Cell cell = row.createCell(j);
				cell.setCellValue(listValue.get(j));
				if(i==0 && j==0){//合并单元格
					sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
				    CellStyle cellStyleMeun = wb.createCellStyle();//获取样式对象
				    cellStyleMeun.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中 
				    cell.setCellStyle(cellStyleMeun);
				    sheet.autoSizeColumn(j);
				}
			}
			lastRowIndex++;
		}
	}
	
	/**
	 * 往指定位置插入标题
	 * @param wb 
	 * @param sheet
	 * @param rowIndex 标题行索引
	 * @param title 标题
	 * @param firstRow 合并单元格 起始行
	 * @param lastRow 合并单元格 终止行
	 * @param firstCol 合并单元格 起始列
	 * @param lastCol 合并单元格 终止列
	 */
	private static void setTitle(Workbook wb,Sheet sheet,int rowIndex,String title,int firstRow, int lastRow, int firstCol, int lastCol){
	    Row rowTypeTitle = sheet.createRow(rowIndex);
	    Cell cellTypeTitle = rowTypeTitle.createCell((short) 0);
	    cellTypeTitle.setCellValue(title);
	    sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol)); //纵向合并不能填0,0否则 ，横向合并也无效
	    CellStyle cellTypeStyle = wb.createCellStyle();//获取样式对象
	    cellTypeStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中        
	    Font fontType = wb.createFont();
	    fontType.setFontHeightInPoints((short) 15);//字号         
	    fontType.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//加粗 字体
	    cellTypeStyle.setFont(fontType);
	    cellTypeTitle.setCellStyle(cellTypeStyle);//将样式应用到行上(此处将样式应用到列上时，始终不起作用)
	}
	
	/**
	 * 往指定位置插入图片
	 * @param wb
	 * @param sheet
	 * @param firstCellVal 第一列值
	 * @param url 图片地址
	 * @param firstRow 合并单元格  起始行
	 * @param lastRow 合并单元格  终止行
	 * @param firstCol 合并单元格  起始列
	 * @param lastCol 合并单元格  终止列
	 */
	private static void setImage(Workbook wb,Sheet sheet,int rowIndex,String firstCellVal,String url,int firstRow, int lastRow, int firstCol, int lastCol){
	    Row grantImp = sheet.createRow(rowIndex);
	    grantImp.setHeight((short) 75);
	    for(int i=0; i<2; i++){
	    	Cell cell = grantImp.createCell(i);
	    	if(i==0){
	    		cell.setCellValue(firstCellVal);
	    	}else{
	    		Drawing patriarch = sheet.createDrawingPatriarch();
	    		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	    	    CellStyle grantImpStyle = wb.createCellStyle();//获取样式对象
	    	    grantImpStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中     
	    	    cell.setCellStyle(grantImpStyle);//将样式应用到行上(此处将样式应用到列上时，始终不起作用)
	    		BufferedImage img = null;
				try {
					if(url.contains(" "))
						url = url.replace(" ", "");
					img = ImageIO.read(new URL(url));
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    		insertImage(wb, patriarch, img, cell, 1);
	    	}
	    }
	}

	@Override
	public List<List<String>> getProjectListMap(B2BProject b2bProject) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("项目信息");
		listPro1.add("项目名称：");
		listPro1.add(b2bProject.getpName());
		listPro1.add("项目编号：");
		listPro1.add(b2bProject.getpCode());
		list.add(listPro1);
		
		List<String> listPro2 = new ArrayList<String>();
		listPro2.add("");
		listPro2.add("是否招投标：");
		listPro2.add(b2bProject.getpIsBid()==0?"否":"是");
		listPro2.add("投标时间：");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		listPro2.add(sdf.format(b2bProject.getpBidTime()));
		list.add(listPro2);
 		return list;
	}

	@Override
	public List<List<String>> getCustomerListMap(B2BProject b2bProject) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("客户信息：");
		listPro1.add("公司名称：");
		listPro1.add(b2bProject.getCustomer().getName());
		listPro1.add("联系人/电话：");
		listPro1.add(b2bProject.getCustomer().getPerson()+"/"+b2bProject.getCustomer().getPhone());
		list.add(listPro1);
		List<String> listPro2 = new ArrayList<String>();
		listPro2.add("");
		listPro2.add("涉及行业：");
		listPro2.add(b2bProject.getBusiness().getName());
		listPro2.add("客户地址：");
		listPro2.add(b2bProject.getCustomer().getAddress());
		list.add(listPro2);
		return list;
	}

	@Override
	public List<List<String>> getSAPListMap(List<SpecialApplyProduct> list) {
		List<List<String>> listSAP = new ArrayList<List<String>>();
		for(int i=0; i<list.size(); i++){
			List<String> listValue = new ArrayList<String>();
			listValue.add("销售信息");
			listValue.add("型号：");
			listValue.add(list.get(i).getProductPrice().getProduct().getName());
			listValue.add("销售数量：");
			listValue.add(list.get(i).getNumber().toString());
			listSAP.add(listValue);
		}
		for(int i=0; i<list.size(); i++){
			List<String> listValue = new ArrayList<String>();
			listValue.add("销售信息");
			listValue.add("竞品型号/价格/尺寸：");
			listValue.add(list.get(i).getProductPrice().getProduct().getName()+"/"+list.get(i).getCompeteProduct()+"/"+list.get(i).getCompetePrice());
			listValue.add("样机提供：");
			listValue.add(list.get(i).getB2bProject().getProto()==1?"是":"否");
			listSAP.add(listValue);
		}
		return listSAP;
	}

	@Override
	public List<List<String>> getAgentListMap(B2BProject b2bProject) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("代理信息");
		listPro1.add("总代名称：");
		listPro1.add(b2bProject.getAgent().getIrName());
		listPro1.add("联系人/电话：");
		listPro1.add("");//暂时未定
		list.add(listPro1);
		
		List<String> listPro2 = new ArrayList<String>();
		listPro2.add("");
		listPro2.add("二级代理商：");
		listPro2.add(b2bProject.getCommSi().getIrName());
		listPro2.add("联系人/电话：");
		listPro2.add("");//暂时未定
		list.add(listPro2);
		return list;
	}

	@Override
	public List<List<String>> getReceiveListMap(B2BProject b2bProject) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("收发货信息");
		listPro1.add("公司名称：");
		listPro1.add(b2bProject.getSendName());
		listPro1.add("公司名称：");
		listPro1.add(b2bProject.getReceiveCompany());
		list.add(listPro1);
		
		List<String> listPro2 = new ArrayList<String>();
		listPro2.add("");
		listPro2.add("发件人：");
		listPro2.add(b2bProject.getSendName());
		listPro2.add("收件人：");
		listPro2.add(b2bProject.getReceiveName());
		list.add(listPro2);
		
		List<String> listPro3 = new ArrayList<String>();
		listPro3.add("");
		listPro3.add("地址：");
		listPro3.add(b2bProject.getSendAddress());
		listPro3.add("地址：");
		listPro3.add(b2bProject.getReceiveAddress());
		list.add(listPro3);
		
		List<String> listPro4 = new ArrayList<String>();
		listPro4.add("");
		listPro4.add("地址1：");
		listPro4.add(b2bProject.getSendAddress1());
		listPro4.add("地址1：");
		listPro4.add(b2bProject.getReceiveAddress1());
		list.add(listPro4);
		
		List<String> listPro5 = new ArrayList<String>();
		listPro5.add("");
		listPro5.add("地址2：");
		listPro5.add(b2bProject.getSendAddress2());
		listPro5.add("地址2：");
		listPro5.add(b2bProject.getReceiveAddress2());
		list.add(listPro5);
		
		List<String> listPro6 = new ArrayList<String>();
		listPro6.add("");
		listPro6.add("邮政编码：");
		listPro6.add(b2bProject.getSendZipcode());
		listPro6.add("邮政编码：");
		listPro6.add(b2bProject.getReceiveZipcode());
		list.add(listPro6);
		
		List<String> listPro7 = new ArrayList<String>();
		listPro7.add("");
		listPro7.add("市：");
		listPro7.add(b2bProject.getSendCity());
		listPro7.add("市：");
		listPro7.add(b2bProject.getReceiveCity());
		list.add(listPro7);
		
		List<String> listPro8 = new ArrayList<String>();
		listPro8.add("");
		listPro8.add("省/市/自治区：");
		listPro8.add(b2bProject.getSendProvince());
		listPro8.add("省/市/自治区：");
		listPro8.add(b2bProject.getReceiveProvince());
		list.add(listPro8);
		
		List<String> listPro9 = new ArrayList<String>();
		listPro9.add("");
		listPro9.add("国家或地区：");
		listPro9.add(b2bProject.getSendCountry());
		listPro9.add("国家或地区：");
		listPro9.add(b2bProject.getReceiveCountry());
		list.add(listPro9);
		
		List<String> listPro10 = new ArrayList<String>();
		listPro10.add("");
		listPro10.add("电话号码：");
		listPro10.add(b2bProject.getSendPhone());
		listPro10.add("电话号码：");
		listPro10.add(b2bProject.getReceivePhone());
		list.add(listPro10);
		
		List<String> listPro11 = new ArrayList<String>();
		listPro11.add("");
		listPro11.add("传真号码：");
		listPro11.add(b2bProject.getSendFax());
		listPro11.add("传真号码：");
		listPro11.add(b2bProject.getReceiveFax());
		list.add(listPro11);
		
		List<String> listPro12 = new ArrayList<String>();
		listPro12.add("");
		listPro12.add("电子邮件地址：");
		listPro12.add(b2bProject.getSendEmail());
		listPro12.add("电子邮件地址：");
		listPro12.add(b2bProject.getReceiveEmail());
		list.add(listPro12);
		return list;
	}

	@Override
	public List<List<String>> getSellListMap(B2BProject b2bProject) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("销售信息");
		listPro1.add("代理发货时间：");
		String sendTime = b2bProject.getAgentSendTime()==null?"":sdf.format(b2bProject.getAgentSendTime());
		listPro1.add(sendTime);
		listPro1.add("客户收货时间：");
		String recTime = b2bProject.getCustomerReceiveTime()==null?"":sdf.format(b2bProject.getCustomerReceiveTime());
		listPro1.add(recTime);
		list.add(listPro1);
		
		List<String> listPro2 = new ArrayList<String>();
		listPro2.add("");
		listPro2.add("产品安装情况：");
		listPro2.add(b2bProject.getProductUseInfo());
		listPro2.add("售后服务形式：");
		listPro2.add(b2bProject.getAfterSalesService());
		list.add(listPro2);
		return list;
	}

	@Override
	public List<List<String>> getRecordListMap(B2BProject b2bProject) {
		List<List<String>> list = new ArrayList<List<String>>();
		List<String> listPro1 = new ArrayList<String>();
		listPro1.add("备注：");
		listPro1.add(b2bProject.getRemark());
		list.add(listPro1);
		return list;
	}

	@Override
	public List<Map> getSampleListMap(List<SampleInOutLog> list) {
		List<Map> listSample = new ArrayList<Map>();
		for(int i=0; i<list.size(); i++){
			Map map = new LinkedHashMap();
			map.put("样机型号", list.get(i).getSampleInventory().getProduct().getName());
			map.put("样机总量", list.get(i).getSampleInventory().getTotal());
			map.put("当前可使用数量", list.get(i).getSampleInventory().getBalance());
			map.put("申请数量", list.get(i).getChangeVolue());
			listSample.add(map);
		}
		return listSample;
	}

	@Override
	public List<List<String>> getApproListMap(List<B2BApproveLog> list) {
		List<List<String>> listAppro = new ArrayList<List<String>>();
		for(int i=0; i<list.size(); i++){
			List<String> listlog = new ArrayList<String>();
			listlog.add("审核人："+list.get(i).getUser().getRealName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			listlog.add("审核时间："+sdf.format(list.get(i).getApproveTime()));
			listlog.add("步骤："+list.get(i).getStep().getOperateStatus());
			listlog.add("是否通过："+(list.get(i).getIsPass()==0?"不通过":"通过"));
			listlog.add("审批备注："+list.get(i).getApproveMsg());
			listAppro.add(listlog);
		}
		return listAppro;
	}

	@Override
	public List<Map> getSellEstimateTemplateByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.toString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("size", cellString);
		        	         }else if(cell.getColumnIndex()==1){
		           	        	 map.put("series", cellString);
		           	         }else if(cell.getColumnIndex()==2){
		           	        	 map.put("productName", cellString);
		           	         }else if(cell.getColumnIndex()==3){
		           	        	 map.put("comment", cellString);
		           	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map> getSellTargetTemplateByExcel(InputStream input) {
		List<Map> list  = new ArrayList<Map>();
		try {
			Workbook wb = WorkbookFactory.create(input);
			Sheet sheet = wb.getSheetAt(0);
			int rowStart = Math.min(1, sheet.getFirstRowNum());
		    int rowEnd = Math.max(1000, sheet.getLastRowNum());
		    
		    for (int rowNum = rowStart+1; rowNum < rowEnd; rowNum++) {
		        Row r = sheet.getRow(rowNum);
		        if(r!=null){
		        	Map map = new HashMap();
		        	int i = 0;
	        		for (Cell cell : r) {
	        			 CellReference cellRef = new CellReference(r.getRowNum(), cell.getColumnIndex());
	        			 String cellString =  cell.toString();
	        			 if(cellString.length()==0){
	        				 continue;
	        			 }else{
	        				 if(cell.getColumnIndex() == 0){
		        	        	 map.put("cityName", cellString);
		        	         }else if(cell.getColumnIndex()==1){
		           	        	 map.put("sellInVolume", cellString);
		           	         }else if(cell.getColumnIndex()==2){
		           	        	 map.put("sellOutVolume", cellString);
		           	         }
	        			 }
	        		}
			        if(map.size()>0){
			        	list.add(map);
			        }
		        }
		     }
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}
}
