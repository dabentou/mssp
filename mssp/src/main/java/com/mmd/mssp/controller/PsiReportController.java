package com.mmd.mssp.controller;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;
import com.mmd.mssp.domain.vo.TitleMapper;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiEstimateService;
import com.mmd.mssp.service.PsiSellInService;
import com.mmd.mssp.service.PsiSellOutService;
import com.mmd.mssp.service.PsiReportService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class PsiReportController {
	
	private static final int PAGE_SIZE = 50;

	@Resource
	ExcelService excelService;
	@Resource
	CommService commService;
	@Resource
	CommAgentService commAgentService;
	@Resource
	ProductService productService;
	@Resource
	PsiSellOutService sellOutService;
	@Resource
	PsiReportService reportService;
	@Resource
	PsiEstimateService psiEstimateService;
	@Resource
	PsiSellInService psiSellInService;
	@Resource
	PsiSellOutService psiSellOutService;
	
	@RequestMapping("/psi/report.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Date startDate = new Date();
		Date endDate = startDate;
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Product> productList = productService.listAll(false);
		
		request.setAttribute("productList", productList);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("agentList", agentList);
		return "/psi/report.jsp";
	}
	
	@RequestMapping(value="/psi/report/search.html",method=RequestMethod.POST)
	public String searchPost(HttpServletRequest request, String startDate,String endDate,Integer channelType,Integer[] productId,Integer[] agentId) throws Exception{
		Map map = new HashMap();
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		map.put("channelType", channelType);
		map.put("productId", productId);
		map.put("agentId", agentId);
		String reqid = "psi_report_search" + UUID.randomUUID().toString();
		request.getSession(true).setAttribute(reqid, map);
		return "redirect:/psi/report/search.html?reqid=" + reqid;
	}

		
		@RequestMapping(value="/psi/report/search.html",method=RequestMethod.GET)
		public String search(HttpServletRequest request, String reqid, Integer page) throws Exception{
		Map map = (Map) request.getSession(true).getAttribute(reqid);
		String startDate = (String) map.get("startDate");
		String endDate = (String) map.get("endDate");
		Integer channelType = (Integer) map.get("channelType");
		Integer[] productId = (Integer[]) map.get("productId");
		Integer[] agentId = (Integer[]) map.get("agentId");
		
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Product> productList = productService.listAll(false);
		//Paging<Product> paging = productService.findByIsDelete(false, page, PAGE_SIZE);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date date1 = sdf.parse(startDate);
		Date date2 = sdf.parse(endDate);
		
		Paging<PsiReportVo> paging = reportService.findByDateChannelTypeProduct(date1, date2, channelType, productId, agentId,page);
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date1);
		
		request.setAttribute("pagelist", paging);
		request.setAttribute("productList", productList);
		request.setAttribute("productId", productId);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("channelType", channelType);
		request.setAttribute("agentList", agentList);
		request.setAttribute("agentId", agentId);
		return "/psi/report.jsp";
	}
	
	@RequestMapping(value="/psi/report/excel")
	public String export(HttpServletRequest request,HttpServletResponse response,String startDate,String endDate,Integer channelType,Integer[] productId) throws Exception{
		
		List<CommAgent> agentList = commService.listCurrentAgent(request);
		List<Product> productList = productService.listAll(false);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date date1 = sdf.parse(startDate);
		Date date2 = sdf.parse(endDate);
		
		List<PsiReportVo> reportVoList = reportService.findByDateChannelTypeProductToExport(date1, date2, channelType, productId, agentList);
		List<Map> mapList = reportService.getMapBySearch(reportVoList);
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.csv");  
        response.setCharacterEncoding("GBK");
        OutputStreamWriter osw = null;
        try {
        	osw = new OutputStreamWriter(response.getOutputStream(), "GBK");  
        	osw.write(this.excelService.convert2Cvs(TitleMapper.psiReportMapper, mapList));
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try {
				osw.flush();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	@RequestMapping("/admin/psi/report.html")
	public String listByAdmin(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		Date startDate = new Date();
		Date endDate = startDate;
		
		List<CommAgent> agentList = commAgentService.listAll();
		List<Product> productList = productService.listAll(false);
		
		request.setAttribute("agentList", agentList);
		request.setAttribute("productList", productList);
		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		return "/admin/psi/report.jsp";
	}
	
	@RequestMapping(value="/admin/psi/report/search",method=RequestMethod.POST)
	public String searchByAdmin(HttpServletRequest request,String startDate,String endDate,Integer channelType,Integer[] agentId,Integer[] productId) throws Exception{
		
		List<PsiReportVo> reportVoList = new ArrayList<PsiReportVo>();
		List<Product> productList = productService.listAll(false);
		List<CommAgent> agentList = commAgentService.listAll();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		Date date1 = sdf.parse(startDate);
		Date date2 = sdf.parse(endDate);
		
		Calendar ca = Calendar.getInstance();
		ca.setTime(date1);
		request.setAttribute("agentList", agentList);
		request.setAttribute("productList", productList);
		request.setAttribute("startDate", date1);
		request.setAttribute("endDate", date2);
		request.setAttribute("channelType", channelType);
		request.setAttribute("reportVoList", reportVoList);
		return "/admin/psi/report.jsp";
	}
	
	@RequestMapping(value="/admin/psi/report/excel")
	@ResponseBody
	public void exportAdmin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.reset();
		response.setContentType("application/vnd.ms-excel");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");  
		
		List<CommAgent> agentList = commAgentService.listAll();
		
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
            // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        try {
        	for (CommAgent agent : agentList) {
    			List<PsiEstimateByProduct> list = psiEstimateService.listByAgentAndDateMonth(agent, dateMonth);
    		    /** Excel 文件要存放的位置，假定在D盘下*/
    			HSSFSheet sheet = workbook.createSheet(agent.getIrName());
                {
    	            HSSFRow row = sheet.createRow((short)0);
    	            //在索引0的位置创建单元格（左上端）
    	            HSSFCell cell0 = row.createCell((short)0);
    	            // 定义单元格为字符串类型
    	            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            // 在单元格中输入一些内容
    	            cell0.setCellValue("寸别");
    	            
    	            HSSFCell cell1 = row.createCell((short)1);
    	            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell1.setCellValue("系列");
    	            
    	            HSSFCell cell2 = row.createCell((short)2);
    	            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell2.setCellValue("型号");
    	            
    	            HSSFCell cell3 = row.createCell((short)3);
    	            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell3.setCellValue("备注");
    	            
    	            HSSFCell cell4 = row.createCell((short)4);
    	            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell4.setCellValue("下月需求 ");
    	            
    	            HSSFCell cell5 = row.createCell((short)5);
    	            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell5.setCellValue("下下月需求");
    	            
                }
                for(int i=1; i<=list.size();i++){
                	PsiEstimateByProduct estimateByProduct = list.get(i-1);
                	// 在索引0的位置创建行（最顶端的行）
    	            HSSFRow row = sheet.createRow((short)i);
    	            //在索引0的位置创建单元格（左上端）
    	            HSSFCell cell1 = row.createCell((short)0);
    	            // 定义单元格为字符串类型
    	            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            // 在单元格中输入一些内容
    	            cell1.setCellValue(estimateByProduct.getTemp().getProduct().getSize1());
    	            
    	            CommSeries series = estimateByProduct.getTemp().getProduct().getCommSeries();
    	            
    	            HSSFCell cell2 = row.createCell((short)1);
    	            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell2.setCellValue(series==null?"":series.getName());
    	            
    	            HSSFCell cell3 = row.createCell((short)2);
    	            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell3.setCellValue(estimateByProduct.getTemp().getProduct().getName());
    	            
    	            HSSFCell cell4 = row.createCell((short)3);
    	            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell4.setCellValue(estimateByProduct.getTemp().getComment());
    	            
    	            HSSFCell cell5 = row.createCell((short)4);
    	            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell5.setCellValue(estimateByProduct.getNextMonthEstimateVolume());
    	            
    	            HSSFCell cell6 = row.createCell((short)5);
    	            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
    	            cell6.setCellValue(estimateByProduct.getNnextMonthEstimateVolume());
                }
    		}
        	HSSFSheet sheet = workbook.createSheet("汇总");
            {
	            HSSFRow row = sheet.createRow((short)0);
	            //在索引0的位置创建单元格（左上端）
	            HSSFCell cell0 = row.createCell((short)0);
	            // 定义单元格为字符串类型
	            cell0.setCellType(HSSFCell.CELL_TYPE_STRING);
	            // 在单元格中输入一些内容
	            cell0.setCellValue("寸别");
	            
	            HSSFCell cell1 = row.createCell((short)1);
	            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell1.setCellValue("系列");
	            
	            HSSFCell cell2 = row.createCell((short)2);
	            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell2.setCellValue("型号");
	            
	            HSSFCell cell3 = row.createCell((short)3);
	            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell3.setCellValue("备注");
	            
	            HSSFCell cell4 = row.createCell((short)4);
	            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell4.setCellValue("下月需求 ");
	            
	            HSSFCell cell5 = row.createCell((short)5);
	            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell5.setCellValue("下下月需求");
            }
            List<Object[]> objList = psiEstimateService.sumByDateMonth(dateMonth);
        	for(int i=1; i<=objList.size(); i++){
        		PsiSellInEstimateTemplate temp = (PsiSellInEstimateTemplate) (objList.get(i-1)[0]);
        		HSSFRow row = sheet.createRow((short)i);
	            HSSFCell cell1 = row.createCell((short)0);
	            cell1.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell1.setCellValue(temp.getProduct().getSize1());
	            
	            CommSeries series = temp.getProduct().getCommSeries();
	            
	            HSSFCell cell2 = row.createCell((short)1);
	            cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell2.setCellValue(series==null?"":series.getName());
	            
	            HSSFCell cell3 = row.createCell((short)2);
	            cell3.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell3.setCellValue(temp.getProduct().getName());
	            
	            HSSFCell cell4 = row.createCell((short)3);
	            cell4.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell4.setCellValue(temp.getComment());
	            
	            HSSFCell cell5 = row.createCell((short)4);
	            cell5.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell5.setCellValue(Integer.parseInt(objList.get(i-1)[1].toString()));
	            
	            HSSFCell cell6 = row.createCell((short)5);
	            cell6.setCellType(HSSFCell.CELL_TYPE_STRING);
	            cell6.setCellValue(Integer.parseInt(objList.get(i-1)[2].toString()));
        	}
            // 在Excel工作簿中建一工作表，其名为缺省值
            // 如要新建一名为"效益指标"的工作表，其语句为：
            // HSSFSheet sheet = workbook.createSheet("效益指标");
            
            // 把相应的Excel 工作簿存盘
            
            
            //FileOutputStream fOut = new FileOutputStream(outputFile);
            // 把相应的Excel 工作簿存盘
        } catch (Exception e) {
            System.out.println("已运行 xlCreate() : " + e);
        }
		String file = "d:/test.xls";
		FileOutputStream out = new FileOutputStream(file);
		workbook.write(out);
		workbook.close();
		out.close();
		FileInputStream in = new FileInputStream(file);
		IOUtils.copy(in, response.getOutputStream());
		in.close();
		System.out.println("文件生成...");
	}
	
	@RequestMapping("/admin/psi/estreport.html")
	public String estReport(HttpServletRequest request, @RequestParam(required=false) Integer page){
		return "/admin/psi/est_report.jsp";
	}
}
