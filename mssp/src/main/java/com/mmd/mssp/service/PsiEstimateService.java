package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.PsiEstimateByProduct;

public interface PsiEstimateService {

	public PsiEstimateByProduct save(PsiEstimateByProduct psiEstimateByProduct);
	
	boolean isSubmitByAgent(CommAgent agent,Date dateMonth);
	
	/**
	 * 判断该大区经理该月是否提交过该代理商的月度销售预估
	 * @param user
	 * @param agent
	 * @param dateMonth
	 * @return
	 */
	boolean isSubmitByManager(CommUser user,CommAgent agent,Date dateMonth);
	
	List<PsiEstimateByProduct> listByAgentAndDateMonth(CommAgent agent,Date dateMonth);
	
	List<Object[]> sumByDateMonth(Date dateMonth);
	
	List<PsiEstimateByProduct> listByAgent(CommAgent agent);
	
	PsiEstimateByProduct getById(Integer id);
}
