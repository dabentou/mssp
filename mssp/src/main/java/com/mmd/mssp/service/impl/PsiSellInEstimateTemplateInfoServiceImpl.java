package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateInfoRepository;
import com.mmd.mssp.service.PsiSellInEstimateTemplateInfoService;

@Service
public class PsiSellInEstimateTemplateInfoServiceImpl implements
		PsiSellInEstimateTemplateInfoService {

	@Resource
	PsiSellInEstimateTemplateInfoRepository infoRepository;
	
	@Override
	public Paging<PsiSellInEstimateTemplateInfo> findAll(Integer page,
			Integer size) {
		Paging<PsiSellInEstimateTemplateInfo> paging = new Paging<PsiSellInEstimateTemplateInfo>(page, size);
		Page<PsiSellInEstimateTemplateInfo> result = infoRepository.findAll(paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public PsiSellInEstimateTemplateInfo save(PsiSellInEstimateTemplateInfo info) {
		return infoRepository.save(info);
	}

	@Override
	public PsiSellInEstimateTemplateInfo getInfoById(Integer id) {
		return infoRepository.getById(id);
	}

}
