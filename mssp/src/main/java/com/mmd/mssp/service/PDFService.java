package com.mmd.mssp.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmd.mssp.domain.vo.PDFFormat;

public interface PDFService {

	/**
	 * html转pdf
	 * @param request 
	 * @param pdfFormat pdf格式(静态html)。其中html必须满足 
	 * 		1、所有标签要以 /正确结束，如<p><p/>。
	 * 		2、中午字体设置为 font-family: SimSun;。
	 * 		3、html页面须有：
	 * 			<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">  
	 *			<html xmlns="http://www.w3.org/1999/xhtml">
	 * @param paramMap pdf中的动态参数
	 * @return
	 */
	public String htmlToPDF(HttpServletRequest request,PDFFormat pdfFormat,Map<String, String> paramMap);
	
	/**
	 * pdf下载
	 * @param response
	 * @param filePath 包含文件名的完整路径，如：C:\test.pdf
	 */
	public void downloadPDF(HttpServletResponse response,String filePath);
}
