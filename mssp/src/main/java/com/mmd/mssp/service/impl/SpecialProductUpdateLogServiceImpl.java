package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.SpecialProductUpdateLog;
import com.mmd.mssp.repository.SpecialProductUpdateLogRepository;
import com.mmd.mssp.service.SpecialProductUpdateLogService;

@Service
public class SpecialProductUpdateLogServiceImpl implements SpecialProductUpdateLogService{

	@Resource
	SpecialProductUpdateLogRepository specialProductUpdateLogRepository;
	
	@Override
	public List<SpecialProductUpdateLog> findBySpecialApplyProduct(
			SpecialApplyProduct specialApplyProduct) {
		return specialProductUpdateLogRepository.findBySpecialApplyProduct(specialApplyProduct);
	}
	
	@Override
	public SpecialProductUpdateLog save(
			SpecialProductUpdateLog specialProductUpdateLog) {
		return specialProductUpdateLogRepository.save(specialProductUpdateLog);
	}
}
