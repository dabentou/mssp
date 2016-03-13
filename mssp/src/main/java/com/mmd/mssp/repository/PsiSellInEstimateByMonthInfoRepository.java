package com.mmd.mssp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.PsiSellInEstimateByMonthInfo;

@Repository
public interface PsiSellInEstimateByMonthInfoRepository extends
		CrudRepository<PsiSellInEstimateByMonthInfo, Integer> {
	
	PsiSellInEstimateByMonthInfo getById(Integer id);
	
	@Query("select a from PsiSellInEstimateByMonthInfo as a order by  a.createtime desc,a.name asc")
	Page<PsiSellInEstimateByMonthInfo> findAll(Pageable page);
}