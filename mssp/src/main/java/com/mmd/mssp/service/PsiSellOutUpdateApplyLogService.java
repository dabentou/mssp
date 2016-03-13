package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.PsiSellOutUpdateApproveLog;
import com.mmd.mssp.domain.vo.Paging;


public interface PsiSellOutUpdateApplyLogService {

	/**
	 * 申请记录保存
	 * @param entity
	 * @return
	 */
	PsiSellOutUpdateApplyLog save(PsiSellOutUpdateApplyLog entity);
	
	List<PsiSellOutUpdateApplyLog> findByCommAgent(CommAgent agent);
	
	List<PsiSellOutUpdateApplyLog> findByApplyDate(Date applyDate,CommAgent commAgent);
	
	/**
	 * 获取单个sell out申请记录(分页为了兼容admin修改所有)
	 * @param id
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<PsiSellOutUpdateApplyLog> fandAll(Integer applyLogId,Integer page,int size);
	
	Paging<PsiSellOutUpdateApplyLog> fandByAgentAndStatusAndProduc(Integer agentid,Integer approveStatus,String productName,Integer page,int size);

	PsiSellOutUpdateApplyLog getById(Integer id);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param psiSellOutUpdateApplyLog
	* @param @return   
	* @return List<PsiSellOutUpdateApproveLog>   
	* @throws
	*/
	List<PsiSellOutUpdateApproveLog> findApproveList(PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param log
	* @param @param approveLog   
	* @return void   
	* @throws
	*/
	void saveLogAndApproveLog(PsiSellOutUpdateApplyLog log,PsiSellOutUpdateApproveLog approveLog);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<PsiSellOutUpdateApplyLog>   
	* @throws
	*/
	List<PsiSellOutUpdateApplyLog> findSelloutUpdateByRole(CommRole commRole);
}
