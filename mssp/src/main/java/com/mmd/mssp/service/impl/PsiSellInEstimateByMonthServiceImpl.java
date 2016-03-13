package com.mmd.mssp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;
import com.mmd.mssp.repository.PsiSellInEstimateByMonthRepository;
import com.mmd.mssp.service.PsiSellInEstimateByMonthService;

@Service
public class PsiSellInEstimateByMonthServiceImpl implements
		PsiSellInEstimateByMonthService {

	@Resource
	PsiSellInEstimateByMonthRepository psiSellInEstimateByMonthRepository;
	
	@Override
	public PsiSellInEstimateByMonth getsellInVolumeByDateAndCommAgent(
			Date dateBegin, Date dateEnd, CommAgent commAgent) {
		return psiSellInEstimateByMonthRepository.findSellInVolumeByDateAndAgent(dateBegin, dateEnd, commAgent);
	}

	@Override
	public PsiSellInEstimateByMonth save(
			PsiSellInEstimateByMonth psiSellInEstimateByMonth) {
		return psiSellInEstimateByMonthRepository.save(psiSellInEstimateByMonth);
	}

	@Override
	public PsiSellInEstimateByMonth findByAgentAndDateMonth(CommAgent agent,
			Date dateMonth) {
		return psiSellInEstimateByMonthRepository.findByAgentAndDateMonth(agent, dateMonth);
	}

}
