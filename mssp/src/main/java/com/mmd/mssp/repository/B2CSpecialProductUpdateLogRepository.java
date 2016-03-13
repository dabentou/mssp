package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialProductUpdateLog;

@Repository
public interface B2CSpecialProductUpdateLogRepository extends
		CrudRepository<B2CSpecialProductUpdateLog, Integer> {
	@Query("select a from B2CSpecialProductUpdateLog as a where a.b2CSpecialApplyProduct = ?1 order by a.updatetime desc")
	List<B2CSpecialProductUpdateLog> findByB2CSpecialApplyProduct(B2CSpecialApplyProduct b2CSpecialApplyProduct);
}
