package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.PsiSellOutUpdateApproveLog;


@Repository
public interface PsiSellOutUpdateApproveLogRepository extends CrudRepository<PsiSellOutUpdateApproveLog, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param psiSellOutUpdateApplyLog
	* @param @return   
	* @return List<PsiSellOutUpdateApproveLog>   
	* @throws
	*/
	List<PsiSellOutUpdateApproveLog> findByUpdateApply(PsiSellOutUpdateApplyLog psiSellOutUpdateApplyLog);


}
