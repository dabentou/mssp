package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.IretailBudget;

@Repository
public interface IretailBudgetRepository extends CrudRepository<IretailBudget,Long>{

	
	@Query(nativeQuery=true,value="SELECT DISTINCT years,months FROM iretail_budget;")
	public List<Object[]> findByYearAndQuarter();
	
	@Query("select a from IretailBudget as a where a.year=?1 and a.months=?2 order by a.id desc,a.months asc")
	List<IretailBudget> findByYearsAndQuarter(Integer year ,Integer quarter);
	
	@Query("select a from IretailBudget as a where a.year=?1 and a.months<=?2 order by a.id desc,a.months asc")
	List<IretailBudget> findByYearsAndQuarters(Integer year ,Integer quarter);
	
//	@Query(nativeQuery=true,value="SELECT sum(?4) from iretail_budget d  where d.years=?1 and d.months<=?2 and d.comm_province_id=?3;")
//	@Query("select sum(a.?4)  from  IretailBudget as a where a.year=?1 and a.months<=?2 and a.commonProvince=?3")
//	public BigDecimal findSumByYearsAndQuarterAndProvince(Integer year ,Integer months,CommProvince province,String type);
	
//	@Query("select sum(a.r02),sum(a.r03),sum(a.r04),sum(a.r05),sum(a.r06),sum(a.r07),sum(a.r08),sum(a.r09),sum(a.r10),sum(a.r11),sum(a.r12) from  IretailBudget as a where a.year=?1 and a.months<=?2 and a.commonProvince=?3")
//	public BudgetTotal findSumByYearsAndQuarterAndProvince(Integer year ,Integer months,CommProvince province);
	
	@Query("select a from IretailBudget as a where a.year=?1 and a.months=?2 and a.commonProvince=?3")
	IretailBudget findByYearsAndQuarterAndProvince(Integer year ,Integer quarter,CommProvince province);
	
	@Query("select a from IretailBudget as a where a.year=?1 and a.commonProvince=?2")
	List<IretailBudget> listByYearsAndMonthAndProvince(Integer year,CommProvince province);
}

