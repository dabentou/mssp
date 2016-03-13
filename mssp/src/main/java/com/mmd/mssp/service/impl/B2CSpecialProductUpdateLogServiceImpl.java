package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialProductUpdateLog;
import com.mmd.mssp.repository.B2CSpecialProductUpdateLogRepository;
import com.mmd.mssp.service.B2CSpecialProductUpdateLogService;

@Service
public class B2CSpecialProductUpdateLogServiceImpl implements
		B2CSpecialProductUpdateLogService {

	@Resource
	B2CSpecialProductUpdateLogRepository b2CSpecialProductUpdateLogRepository;

	@Override
	public List<B2CSpecialProductUpdateLog> findByB2CSpecialApplyProduct(B2CSpecialApplyProduct b2CSpecialApplyProduct) {
		return b2CSpecialProductUpdateLogRepository.findByB2CSpecialApplyProduct(b2CSpecialApplyProduct);
	}

	@Override
	public B2CSpecialProductUpdateLog save(
			B2CSpecialProductUpdateLog b2CSpecialProductUpdateLog) {
		return b2CSpecialProductUpdateLogRepository.save(b2CSpecialProductUpdateLog);
	}

}
