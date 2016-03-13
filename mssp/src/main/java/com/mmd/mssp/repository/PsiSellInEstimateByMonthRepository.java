package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;
import com.mmd.mssp.domain.PsiSellInEstimateByMonthInfo;


@Repository
public interface PsiSellInEstimateByMonthRepository extends CrudRepository<PsiSellInEstimateByMonth, Integer> {

	@Query("select a from PsiSellInEstimateByMonth a where a.createtime>=?1 and a.createtime<=?2 and a.agent=?3")
	PsiSellInEstimateByMonth findSellInVolumeByDateAndAgent(Date dateBegin,Date dateEnd,CommAgent commAgent);
	
	@Query("select a from PsiSellInEstimateByMonth a where a.agent=?1 and a.dateMonth=?2")
	PsiSellInEstimateByMonth findByAgentAndDateMonth(CommAgent agent,Date dateMonth);
	
	@Query("select count(a) from PsiSellInEstimateByMonth a where a.dateMonth=?1")
	int getCountByDateMonth(Date dateMonth);
	
	@Query("select a from PsiSellInEstimateByMonth a where a.info=?1")
	List<PsiSellInEstimateByMonth> findByInfo(PsiSellInEstimateByMonthInfo info);
}
