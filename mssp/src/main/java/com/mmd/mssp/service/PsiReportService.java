package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;

public interface PsiReportService {
	/**
	 * 此月第一天开始按型号代理录入渠道计算累计sell in
	 * @param firstDay
	 * @param currentDay
	 * @param product
	 * @param agent
	 * @param channelType
	 * @return
	 */
	Integer sumSellIn(Date firstDay, Date currentDay, Product product,CommAgent agent, Integer channelType);
	
	/**
	 * 此月第一天开始按型号代理录入渠道计算累计sell out
	 * @param firstDay
	 * @param currentDay
	 * @param product
	 * @param agent
	 * @param channelType
	 * @return
	 */
	Integer sumSellOut(Date firstDay, Date currentDay, Product product,CommAgent agent, Integer channelType);

	/**
	 * 此月第一天开始+上月期末库存按型号代理录入渠道得出当日库存
	 * @param firstDay
	 * @param currentDay
	 * @param product
	 * @param agent
	 * @param channelType
	 * @return
	 */
	Integer inventory(Product product,CommAgent agent,Integer channelType,Date lastDayOfLastMonth);
	/**
	 * 根据型号、城市（代理商）、渠道、导入时间查询
	 * @param material
	 * @param city
	 * @param channelType
	 * @param date
	 * @return
	 */
	PsiSellInDatasource findByPacc(String material,String city,Integer channelType,Date date);
	
	Paging<PsiReportVo> findByDateChannelTypeProduct(Date startDate,Date endDate,Integer channelType,Integer[] productId,Integer[] agentId,Integer page);
	
	List<PsiReportVo> findByDateChannelTypeProductToExport(Date startDate,Date endDate,Integer channelType,Integer[] productId,List<CommAgent> agentList);
	
	List<Map> getMapBySearch(List<PsiReportVo> list);
}
