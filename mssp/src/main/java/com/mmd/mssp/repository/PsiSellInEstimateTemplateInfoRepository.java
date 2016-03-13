package com.mmd.mssp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;

@Repository
public interface PsiSellInEstimateTemplateInfoRepository extends
		CrudRepository<PsiSellInEstimateTemplateInfo, Integer> {

	PsiSellInEstimateTemplateInfo getById(Integer id);
	
	@Query("select a from PsiSellInEstimateTemplateInfo as a order by  a.createtime desc,a.name asc")
	Page<PsiSellInEstimateTemplateInfo> findAll(Pageable page);
}
