package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.domain.SpecialProductUpdateLog;

@Repository
public interface SpecialProductUpdateLogRepository extends CrudRepository<SpecialProductUpdateLog,Integer>{

	@Query("select a from SpecialProductUpdateLog as a where a.SpecialApplyProduct = ?1 order by a.updatetime desc")
	List<SpecialProductUpdateLog> findBySpecialApplyProduct(SpecialApplyProduct specialApplyProduct);
}

