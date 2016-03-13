package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.PsiSellInEstimateByMonthInfo;
import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PsiSellInEstimateByMonthInfoRepository;
import com.mmd.mssp.repository.PsiSellInEstimateByMonthRepository;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateInfoRepository;
import com.mmd.mssp.service.PsiSellInEstimateByMonthInfoService;

@Service
public class PsiSellInEstimateByMonthInfoServiceImpl implements
		PsiSellInEstimateByMonthInfoService {

	@Resource
	PsiSellInEstimateByMonthInfoRepository infoRepository;
	
	@Override
	public PsiSellInEstimateByMonthInfo getInfoById(Integer id) {
		return infoRepository.getById(id);
	}

	@Override
	public Paging<PsiSellInEstimateByMonthInfo> findAll(Integer page,
			Integer size) {
		Paging<PsiSellInEstimateByMonthInfo> paging = new Paging<PsiSellInEstimateByMonthInfo>(page, size);
		Page<PsiSellInEstimateByMonthInfo> result = infoRepository.findAll(paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public PsiSellInEstimateByMonthInfo save(PsiSellInEstimateByMonthInfo info) {
		return infoRepository.save(info);
	}

}
