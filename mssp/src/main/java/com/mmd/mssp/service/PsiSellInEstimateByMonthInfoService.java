package com.mmd.mssp.service;

import com.mmd.mssp.domain.PsiSellInEstimateByMonthInfo;
import com.mmd.mssp.domain.vo.Paging;

public interface PsiSellInEstimateByMonthInfoService {

	PsiSellInEstimateByMonthInfo getInfoById(Integer id);
	Paging<PsiSellInEstimateByMonthInfo> findAll(Integer page, Integer size);
	public PsiSellInEstimateByMonthInfo save(PsiSellInEstimateByMonthInfo info);
}
