package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.SpecialProductUpdateLog;


public interface SpecialProductUpdateLogService {
	public List<SpecialProductUpdateLog> findBySpecialApplyProduct(SpecialApplyProduct specialApplyProduct);
	public SpecialProductUpdateLog save(SpecialProductUpdateLog specialProductUpdateLog);
}
