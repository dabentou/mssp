package com.mmd.mssp.controller;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.service.PsiSellInDataSourceService;
import com.mmd.mssp.service.PsiSellInService;

@Controller
public class PsiSellInDataSourceController {
	
	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	PsiSellInDataSourceService psiSellInDataSourceService;
	
	@Resource
	PsiSellInService psiSellInService;
	
	@RequestMapping("/psi/sellin.html")
	public String order(HttpServletRequest request){
		return "/psi/sellin_datasource.jsp";
	}
	
	@RequestMapping(value="/psi/sellin/input",method=RequestMethod.POST)
	public String SellInInput(HttpServletRequest request,HttpServletResponse response,@RequestParam(value = "file", required = false) MultipartFile file){
		String errorMsg =  null;
		String channelType=request.getParameter("channelType");
		List<PsiSellInDatasource> datalist=null;
		try {
			datalist= psiSellInDataSourceService.sellInInput(file.getInputStream(),channelType);
			if(CollectionUtils.isEmpty(datalist)){
				errorMsg="导入类型选择错误或Excel解析数据失败，请重试！";
			}else{
				//验证导入的数据
				errorMsg = psiSellInDataSourceService.validatedata(datalist);
			}
		} catch (IOException e) {
			errorMsg="导入Sell in数据发生异常";
			e.printStackTrace();
		}
		if(StringUtils.isNotBlank(errorMsg)){
			request.setAttribute("errorMsg", errorMsg);
		}else{
			psiSellInDataSourceService.addBatchData(datalist,channelType);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(channelType.equals("1")){
				String datefei =sdf.format(new Date())+channelType;
				request.getSession().setAttribute("fei", datefei);
//				request.getServletContext().setAttribute("fei", datefei);
			}
			if(channelType.equals("2")){
				String dateyue =sdf.format(new Date())+channelType;
				request.getSession().setAttribute("yue", dateyue);
//				request.getServletContext().setAttribute("yue", dateyue);
			}
			request.setAttribute("list", datalist);
		}
		request.setAttribute("channelType", channelType);
		return "/psi/sellin_datasource.jsp";
	}
}
