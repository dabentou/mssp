package com.mmd.mssp.service;

import java.io.InputStream;
import java.util.List;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.IretailBudget;
import com.mmd.mssp.domain.vo.IretailBudgetYears;

public interface IretailBudgetService {
	
	public List<IretailBudgetYears> selectYearAndQuarter();
	
	public List<IretailBudget> findByYearsAndQuarter(Integer year,Integer quarter);
	
	public List<IretailBudget> findByYearsAndQuarters(Integer year,Integer quarter);
	
//	public BudgetTotal findSumByYearsAndQuarterAndProvince(Integer year,Integer quarter,CommProvince  province);
	
	List<IretailBudget> listByYearsAndMonthAndProvince(Integer year,CommProvince province);
	
	public IretailBudget findByYearsAndQuarterAndProvince(Integer year,Integer quarter,CommProvince province);
	
	String budgetInput(InputStream inputStream,CommUser user,int years,int months)  throws Exception;
	void save(List<IretailBudget> list);
	void save(IretailBudget iretailBudget);

}
