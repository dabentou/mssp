package com.mmd.mssp.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.SampleInOutLog;
import com.mmd.mssp.domain.SpecialApplyProduct;

public interface ExcelService {

	public String convert2XlsAndSave2Disk(List<Map> list);
	
	public String convert2Cvs(Map<String,String> titleMap,List<Map> list);
	
	public Workbook convert2Excel(Workbook wb,Sheet mySheet, Integer rowIndex,String sheetName,Map<String,String> titleMapper,List<Map> list);
	
	public <T> String convert2CvsByObject(Map<String,String> titleMap,List<T> list);
	
	public List<Map> getPannelByExcel(InputStream input);
	
	public List<Map> getSeriesByExcel(InputStream input);
	
	public List<Map> getProductSeriesByExcel(InputStream input);
	
	public List<Map> getProductByExcel(InputStream input);
	
	public List<Map> getAreaByExcel(InputStream input);
	
	public List<Map> getCityByExcel(InputStream input);
	
	public List<Map> getSellEstimateTemplateByExcel(InputStream input);
	
	public List<Map> getSellTargetTemplateByExcel(InputStream input);
	
	public void B2BProjectExcel(Integer id);
	
	public List<List<String>> getProjectListMap(B2BProject b2bProject);
	
	public List<List<String>> getCustomerListMap(B2BProject b2bProject);
	
	public List<List<String>> getSAPListMap(List<SpecialApplyProduct> list);
	
	public List<List<String>> getAgentListMap(B2BProject b2bProject);
	
	public List<List<String>> getReceiveListMap(B2BProject b2bProject);
	
	public List<List<String>> getSellListMap(B2BProject b2bProject);
	
	public List<List<String>> getRecordListMap(B2BProject b2bProject);
	
	/**
	 * 样机申请列表
	 * @param list
	 * @return
	 */
	public List<Map> getSampleListMap(List<SampleInOutLog> list);
	
	public List<List<String>> getApproListMap(List<B2BApproveLog> list);
	
	
}
