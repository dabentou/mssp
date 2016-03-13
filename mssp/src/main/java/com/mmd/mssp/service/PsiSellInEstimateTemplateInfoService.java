package com.mmd.mssp.service;

import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;
import com.mmd.mssp.domain.vo.Paging;

public interface PsiSellInEstimateTemplateInfoService {

	PsiSellInEstimateTemplateInfo getInfoById(Integer id);
	Paging<PsiSellInEstimateTemplateInfo> findAll(Integer page, Integer size);
	public PsiSellInEstimateTemplateInfo save(PsiSellInEstimateTemplateInfo info);
}
