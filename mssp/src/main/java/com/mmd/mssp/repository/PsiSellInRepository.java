package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellIn;

@Repository
public interface PsiSellInRepository extends CrudRepository<PsiSellIn, Long> {

	/**
	* @author: sheng.tian
	* @Description: 查询这个月累计sell in数据
	* @param @param currentMonthFisrtDay 当前月的第一天
	* @param @param date 当前日期
	* @param @param product 产品型号
	* @param @param agent代理商
	* @param @param channelType数据类型
	* @param @return   
	* @return int   
	* @throws
	*/
	@Query("select sum(a.sellInVolume)  from PsiSellIn a where a.actiontime>=?1 and a.actiontime<=?2 and a.product=?3 and a.agent=?4 and a.channelType=?5")
	Integer sumSellInData(Date currentMonthFisrtDay, Date date, Product product,CommAgent agent,Integer channelType);
	
	@Query("select sum(a.sellInVolume)  from PsiSellIn a where a.actiontime like ?1 and a.product=?2 and a.agent=?3 and a.channelType=?4")
	Integer sumSellInDatas(String thisMonth, Product product,CommAgent agent,Integer channelType);
	/**
	 * 上月累计sell in
	 * @param firstDay
	 * @param lastDay
	 * @param product
	 * @param commAgent
	 * @param channelType
	 * @return
	 */
	@Query("select sum(a.sellInVolume)  from PsiSellIn a where a.actiontime>=?1 and a.actiontime<=?2 and a.product=?3 and a.agent=?4 and a.channelType=?5")
	Integer sumSellInLastMonth(Date firstDay, Date lastDay, Product product, CommAgent commAgent, Integer channelType);
	
	@Query("select a.sellInVolume from PsiSellIn a where a.actiontime like ?1 and a.product=?2 and a.agent=?3 and a.channelType=?4")
	Integer sellInData(String actiontime, Product product,CommAgent agent,Integer channelType);
	/**
	 * 查询这个月该代理这个型号的累计sellIn
	 * @param currentMonthFisrtDay
	 * @param date
	 * @param product
	 * @param agent
	 * @return
	 */
	@Query("select sum(a.sellInVolume)  from PsiSellIn a where a.actiontime>=?1 and a.actiontime<=?2 and a.product=?3 and a.agent=?4")
	Integer sumSellInData(Date currentMonthFisrtDay, Date date, Product product,CommAgent agent);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param currentMonthFisrtDay
	* @param @param date
	* @param @return   
	* @return List<PsiSellIn>   
	* @throws
	*/
	@Query("select a from PsiSellIn a where a.actiontime>=?1 and a.actiontime<?2 and a.channelType=?3")
	List<PsiSellIn> findCurrentMonthSellInList(Date currentMonthFisrtDay,Date date,Integer channelType);
}