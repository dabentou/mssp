package com.mmd.mssp.service.impl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;
import com.mmd.mssp.domain.vo.PDFFormat;
import com.mmd.mssp.service.PDFService;


@Service
public class PDFServiceImpl implements PDFService {
	
	@Override
	public String htmlToPDF(HttpServletRequest request,PDFFormat pdfFormat,Map<String, String> paramMap) {
		OutputStream os = null;
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String fileName = pdfFormat.getTitle()+"_"+df.format(new Date())+".pdf";
		String pubPath = request.getSession().getServletContext().getRealPath("/");
		String outputFile =  pubPath+"WEB-INF"+File.separator+"upload"+File.separator+"pdf"+File.separator+fileName;
		try {
			File file = new File(pubPath+"WEB-INF"+File.separator+"upload"+File.separator+"pdf");
			if(!file.exists()){
				file.mkdirs();
			}
			os = new FileOutputStream(outputFile);
			ITextRenderer renderer = new ITextRenderer();
			String str = pdfFormat.getContent();
			for (Map.Entry<String, String> param : paramMap.entrySet()) {
				String key = param.getKey();
				key = "${" + key + "}";
				str = str.replace(key, param.getValue()==null?"":param.getValue());
			}
			renderer.setDocumentFromString(str);
			ITextFontResolver fontResolver = renderer.getFontResolver();
			String fontPath = pubPath + "WEB-INF"+File.separator+"fonts"+File.separator+"SIMSUN.TTC";
			fontResolver.addFont(fontPath,BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
			renderer.layout();      
			renderer.createPDF(os);    
			os.flush();  
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if(os!=null){os.close();}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return outputFile;
	}
	
	@Override
	public void downloadPDF(HttpServletResponse response,String filePath) {
		if(filePath!=null){
			try {
				String fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1, filePath.length());
				fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
				response.setContentType("application/vnd.ms-pdf;charset=GBK");  
		        response.setHeader("Pragma", "no-cache");  
		        response.setHeader("Cache-Control", "no-cache");  
		        response.setDateHeader("Expires", 0);  
		        response.setHeader("Content-Disposition", "attachment;filename="+fileName);  
		        response.setCharacterEncoding("GBK");
		        //读取文件  
		        InputStream in;
				in = new FileInputStream(filePath);
		        OutputStream out = response.getOutputStream();  
		        int b;  
		        while((b=in.read())!= -1){  
		            out.write(b);  
		        }  
		        in.close();  
		        out.close(); 
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
 
	}
}
