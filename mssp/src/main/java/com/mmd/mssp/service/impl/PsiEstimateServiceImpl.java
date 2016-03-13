package com.mmd.mssp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.repository.PsiEstimateByProductRepository;
import com.mmd.mssp.service.PsiEstimateService;

@Service
public class PsiEstimateServiceImpl implements PsiEstimateService {

	@Resource
	PsiEstimateByProductRepository psiEstimateByProductRepository;
	
	@Override
	public PsiEstimateByProduct save(PsiEstimateByProduct psiEstimateByProduct) {
		return psiEstimateByProductRepository.save(psiEstimateByProduct);
	}

	@Override
	public boolean isSubmitByAgent(CommAgent agent, Date dateMonth) {
		return psiEstimateByProductRepository.findPsiEstimateByProductByAgentAndDateMonth(agent, dateMonth).isEmpty()?false:true;
	}

	@Override
	public List<PsiEstimateByProduct> listByAgentAndDateMonth(CommAgent agent,
			Date dateMonth) {
		return psiEstimateByProductRepository.findPsiEstimateByProductByAgentAndDateMonth(agent, dateMonth);
	}

	@Override
	public boolean isSubmitByManager(CommUser user, CommAgent agent, Date dateMonth) {
		return psiEstimateByProductRepository.findPsiEstimateByProductByManagerAndDateMonth(user, agent, dateMonth).isEmpty()?false:true;
	}

	@Override
	public PsiEstimateByProduct getById(Integer id) {
		return psiEstimateByProductRepository.getById(id);
	}

	@Override
	public List<PsiEstimateByProduct> listByAgent(CommAgent agent) {
		return psiEstimateByProductRepository.findPsiEstimateByProductByAgent(agent);
	}

	@Override
	public List<Object[]> sumByDateMonth(Date dateMonth) {
		return psiEstimateByProductRepository.sumPsiEstimateByProductByDateMonth(dateMonth);
	}
}
