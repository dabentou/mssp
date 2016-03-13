package com.mmd.mssp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellOut;

@Repository
public interface PsiSellOutRepository extends CrudRepository<PsiSellOut, Long> {

	/**
	* @author: sheng.tian
	* @Description: 当前月的累计Sell out
	* @param @param currentMonthFisrtDay当前月的第一天
	* @param @param date 当前时间
	* @param @param product 型号
	* @param @param agent 代理商
	* @param @param channelType 数据类型
	* @param @return   
	* @return int   
	* @throws
	*/
	@Query("select sum(a.sellInOutlvolume)  from PsiSellOut a where a.actiontime>=?1 and a.actiontime<=?2 and a.product=?3 and a.agent=?4 and a.channelType=?5")
	Integer sumSellOutData(Date currentMonthFisrtDay, Date date, Product product,CommAgent agent, Integer channelType);
	
	@Query("select sum(a.sellInOutlvolume)  from PsiSellOut a where a.actiontime like ?1 and a.product=?2 and a.agent=?3 and a.channelType=?4")
	Integer sumSellOutDatas(String thisMonth, Product product,CommAgent agent, Integer channelType);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param productName
	* @param @param date
	* @param @return   
	* @return PsiSellOut   
	* @throws
	*/
	PsiSellOut findByProductAndAgentAndCreatetimeAndChannelType(Product productName,CommAgent commAgent, Date date,Integer channelType);
	
	
	/**
	 * 上月累计sell out
	 * @param firstDay 上月第一天
	 * @param lastDay 上月最后一天
	 * @param product
	 * @param commAgent
	 * @param channelType
	 * @return
	 */
	@Query("select sum(a.sellInOutlvolume)  from PsiSellOut a where a.actiontime>=?1 and a.actiontime<=?2 and a.product=?3 and a.agent=?4 and a.channelType=?5")
	Integer sumSellOutLastMonth(Date firstDay, Date lastDay, Product product, CommAgent commAgent,Integer channelType);
	
	@Query("select a.sellInOutlvolume from PsiSellOut a where a.actiontime like ?1 and a.product=?2 and a.agent=?3 and a.channelType=?4")
	Integer sellOutData(String actiontime, Product product,CommAgent agent, Integer channelType);
	
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param productName
	* @param @param date
	* @param @return   
	* @return PsiSellOut   
	* @throws
	*/
	PsiSellOut findByProductAndCreatetimeAndChannelType(Product productName, Date date,Integer channelType);
}