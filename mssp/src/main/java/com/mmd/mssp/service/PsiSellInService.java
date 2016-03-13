package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellInDatasource;


public interface PsiSellInService {



	/**
	* @author: sheng.tian
	* @Description: 批量导入Sell in
	* @param @param datalist
	* @param @return   
	* @return int   
	* @throws
	*/
	void addBatchPsiSellIn(List<PsiSellInDatasource> datalist);
	Integer sumSellInDatas(String thisMonth,  Product product,CommAgent agent,Integer channelType);
	
	/**
	 * 上月累计sell in
	 * @param commAgent 代理商
	 * @param product 型号
	 * @param channelType 类型(飞生、越海)
	 * @param firtDay 上月第一天
	 * @param lastDay 上月最后一天
	 * @return
	 */
	Integer sumSellInLastMonth(Date firtDay,Date lastDay, CommAgent commAgent,Product product,Integer channelType);
	
	Integer sellInCurrent(String actiontime,Product product,CommAgent commAgent,Integer channelType);
	
	Integer sumSellInCurrent(Date firstDay,Date currentDate,Product product,CommAgent commAgent);
}
