package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;
import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;

@Repository
public interface PsiSellInEstimateTemplateRepository extends CrudRepository<PsiSellInEstimateTemplate, Integer> {
	
	PsiSellInEstimateTemplate getById(Integer id);
	
	@Query("select a from PsiSellInEstimateTemplate as a where a.dateMonth = (?1) order by a.dateMonth desc")
	List<PsiSellInEstimateTemplate> listByDateMonth(Date dateMonth);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param lastMonthFirstDay
	* @param @param product
	* @param @return   
	* @return PsiSellInEstimateTemplate   
	* @throws
	*/
	@Query("select a from PsiSellInEstimateTemplate as a where a.dateMonth = (?1) and a.product=?2")
	PsiSellInEstimateTemplate findTempByMouthAndProduct(Date lastMonthFirstDay,Product product);
	
	@Query("select a from PsiSellInEstimateTemplate as a where a.info = (?1)")
	List<PsiSellInEstimateTemplate> findByInfo(PsiSellInEstimateTemplateInfo info);
	
	@Query("select count(a) from PsiSellInEstimateTemplate as a where a.dateMonth = (?1)")
	int getCountByDateMonth(Date dateMonth);
}
