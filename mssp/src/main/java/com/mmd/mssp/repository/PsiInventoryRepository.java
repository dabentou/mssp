package com.mmd.mssp.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;

@Repository
public interface PsiInventoryRepository extends CrudRepository<PsiInventory, Long> {

	/**
	* @author: sheng.tian
	* @Description: 查询上个月的月末库存
	* @param @param lastMonthLastDay
	* @param @param product
	* @param @return   
	* @return int   
	* @throws
	*/
	@Query("select a from PsiInventory a where a.createtime=?1 and a.product=?2 and a.channelType=?3 and a.agent=?4")
	PsiInventory findLastInventory(Date lastMonthLastDay, Product product,Integer channelType,CommAgent agent);
	
	@Query("select a from PsiInventory a where a.createtime like ?1 and a.product=?2 and a.agent=?3 and a.channelType=?4")
	PsiInventory findLastInventorys(String lastMonthLastDay, Product product,CommAgent agent,Integer channelType);

	/**
	 * 根据型号、代理商、类型 和时间 获取上月末库存
	 * @param product
	 * @param commAgent
	 * @param channelType
	 * @param time
	 * @return
	 */
	@Query("select a from PsiInventory a where a.product=?1 and a.agent=?2 and a.channelType=?3 and a.createtime=?4")
	PsiInventory findByProductAndCommAgentAndChannelType(Product product, CommAgent commAgent, Integer channelType,Date time);
}
