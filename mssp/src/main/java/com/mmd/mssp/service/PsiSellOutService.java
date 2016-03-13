package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommApprovalLog;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.domain.PsiSellOut;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;
import com.mmd.mssp.domain.vo.SellOutVo;


public interface PsiSellOutService {



	/**
	* @author: sheng.tian
	* @Description: 批量新增Sell out
	* @param @param datalist
	* @param @return   
	* @return int   
	* @throws
	*/
	void addBatchPsiSellOut(List<PsiSellOut> selloutList);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return List<SellOutVo>   
	* @throws
	*/
	List<SellOutVo> getSellOutList(String channelType,CommAgent city);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param findProductbyProductName
	* @param @param date
	* @param @return   
	* @return boolean   
	* @throws
	*/
	PsiSellOut findByProductAndAgentAndCreatetimeAndChannelType(Product product,CommAgent commAgent,Date date,Integer channelType);
	
	Integer sumSellOutDatas(String thisMonth, Product product,CommAgent agent, Integer channelType);
	/**
	 * 上月累计sell  out
	 * @param firstDay 上月第一天
	 * @param lastDay 上月最后一天
	 * @param product
	 * @param commAgent
	 * @param channelType
	 * @return
	 */
	Integer sumSellOutLastMonth(Date firstDay, Date lastDay, Product product, CommAgent commAgent, Integer channelType);
	
	PsiSellOut save(PsiSellOut psiSellOut);
	
	/**
	 * 作为一个事务保存sellOut修改后的相关数据
	 * @return
	 */
	Boolean saveApprovalLog(PsiSellOutUpdateApplyLog applyLog,CommApprovalLog approvalLog,PsiSellOut psiSellOut,UpdateLogByAdmin aByAdmin,PsiInventory currentInventory);

	Integer sellOutCurrent(String actiontime, Product product, CommAgent commAgent, Integer channelType);
	
	Integer getCurrentInventory(Date currentMonthFisrtDay, Date date,Product product, CommAgent agent, Integer channelType);
	
	Paging<PsiReportVo> findByDateChannelTypeProduct(Date startDate,Date endDate,Integer channelType,Integer[] productId,Integer[] agentId,String page);
	
	void saveSellOutAndPsiInventory(PsiSellOut sellOut ,PsiInventory psiInventory);
}
