package com.mmd.mssp.service;

import java.io.InputStream;
import java.util.List;

import com.mmd.mssp.domain.PsiSellInDatasource;


public interface PsiSellInDataSourceService {

	/**
	* @author: sheng.tian
	* @Description: 导入sell in
	* @param @param inputStream
	* @param @return   
	* @return List<PsiSellInDatasource>   
	* @throws
	*/
	List<PsiSellInDatasource> sellInInput(InputStream inputStream,String channelType);

	/**
	* @author: sheng.tian
	* @Description: 验证型号
	* @param @param datalist
	* @param @return   
	* @return String   
	* @throws
	*/
	String validatedata(List<PsiSellInDatasource> datalist);


	/**
	* @author: sheng.tian
	* @Description: 批量将导入的sell in数据保存到数据库中
	* @param @param datalist
	* @param @return   
	* @return int   
	* @throws
	*/
	void addBatchSellInDataSource(List<PsiSellInDatasource> datalist);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param    
	* @return void   
	* @throws
	*/
	void addBatchData(List<PsiSellInDatasource> datalist,String channelType);
}
