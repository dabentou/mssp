package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;

@Repository
public interface PsiSellOutUpdateApplyLogRepository extends CrudRepository<PsiSellOutUpdateApplyLog, Integer> {
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.commAgent = ?1 order by  a.id desc")
	List<PsiSellOutUpdateApplyLog> findByCommAgent(CommAgent agent);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandAll(Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.product.name = ?1 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByProduct(String producntName,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.status = ?1 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByStatus(Integer approvalStatus,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.status = ?1 and a.product.name = ?2 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByStatusAndProduct(Integer approvalStatus,String productName,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.commAgent.id = ?1 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByAgent(Integer agentId,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.commAgent.id = ?1 and a.product.name = ?2 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByAgentAndPorduct(Integer agentId,String producntName,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.commAgent.id = ?1 and a.status = ?2 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByAgentAndStatud(Integer agentId,Integer approvalStatus,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.commAgent.id = ?1 and a.status = ?2 and a.product.name = ?3 order by a.id desc")
	Page<PsiSellOutUpdateApplyLog> fandByAgentAndStatudAndProduct(Integer agentId,Integer approvalStatus,String producntName,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.id = ?1")
	Page<PsiSellOutUpdateApplyLog> pageById(Integer applyLogId,Pageable page);
	
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.applyTime >= ?1 and a.applyTime < ?2 and a.commAgent = ?3 order by  a.id desc")
	List<PsiSellOutUpdateApplyLog> findByApplyDate(Date startTime,Date endTime,CommAgent commAgent);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return Integer   
	* @throws
	*/
	@Query("select count(0) from PsiSellOutUpdateApplyLog as a where a.step.pnextId.commRole=?1 and a.step.pnextId.pnextId is not null")
	Integer countPsiToDo(CommRole commRole);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<PsiSellOutUpdateApplyLog>   
	* @throws
	*/
	@Query("select a from PsiSellOutUpdateApplyLog as a where a.step.pnextId.commRole=?1")
	List<PsiSellOutUpdateApplyLog> findSelloutUpdateByRole(CommRole commRole);
	
}