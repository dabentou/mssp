package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;


@Repository
public interface PsiEstimateByProductRepository extends CrudRepository<PsiEstimateByProduct, Integer> {
	
	@Query("select a from PsiEstimateByProduct a where a.agent=?1 and a.dateMonth=?2")
	List<PsiEstimateByProduct> findPsiEstimateByProductByAgentAndDateMonth(CommAgent agent, Date dateMonth);
	
	@Query("select a from PsiEstimateByProduct a where a.agent=?1")
	List<PsiEstimateByProduct> findPsiEstimateByProductByAgent(CommAgent agent);
	
	@Query("select a from PsiEstimateByProduct a where a.user=?1 and a.agent = ?2 and a.dateMonth=?3")
	List<PsiEstimateByProduct> findPsiEstimateByProductByManagerAndDateMonth(CommUser user, CommAgent agent, Date dateMonth);
	
	@Query("select a.temp,sum(a.nextMonthEstimateVolume),sum(a.nnextMonthEstimateVolume) from PsiEstimateByProduct a where a.dateMonth=?1 GROUP BY a.temp")
	List<Object[]> sumPsiEstimateByProductByDateMonth(Date dateMonth);
	
	PsiEstimateByProduct getById(Integer id);
	
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param template
	* @param @param lastMonthFirstDay
	* @param @param agent
	* @param @return   
	* @return PsiEstimateByProduct   
	* @throws
	*/
	@Query("select a from PsiEstimateByProduct a where a.temp=?1 and a.dateMonth=?2 and a.agent=?3")
	PsiEstimateByProduct findTotalVolumeByProductAndDataMonthAndAgent(PsiSellInEstimateTemplate template, Date lastMonthFirstDay,CommAgent agent);
}
