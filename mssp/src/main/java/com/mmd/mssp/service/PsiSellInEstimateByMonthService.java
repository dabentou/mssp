package com.mmd.mssp.service;

import java.util.Date;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;

public interface PsiSellInEstimateByMonthService {

	PsiSellInEstimateByMonth getsellInVolumeByDateAndCommAgent(Date dateBegin,Date dateEnd,CommAgent commAgent);
	
	public PsiSellInEstimateByMonth save(PsiSellInEstimateByMonth psiSellInEstimateByMonth);
	
	PsiSellInEstimateByMonth findByAgentAndDateMonth(CommAgent agent,Date dateMonth);
}
