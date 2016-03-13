package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.IretailBudgetLog;


public interface IretailBudgetLogService {

	public void save(List<IretailBudgetLog> list);
	
	List<IretailBudgetLog> findByYearAndQuarterAndCommProvince(Integer year,Integer quarter,CommProvince province);
}
