package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialProductUpdateLog;

public interface B2CSpecialProductUpdateLogService {
	public List<B2CSpecialProductUpdateLog> findByB2CSpecialApplyProduct(B2CSpecialApplyProduct b2CSpecialApplyProduct);
	public B2CSpecialProductUpdateLog save(B2CSpecialProductUpdateLog b2CSpecialProductUpdateLog);
}
