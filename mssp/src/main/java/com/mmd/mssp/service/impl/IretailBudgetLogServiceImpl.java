package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.IretailBudgetLog;
import com.mmd.mssp.repository.IretailBudgetLogRepository;
import com.mmd.mssp.service.IretailBudgetLogService;

@Service
public class IretailBudgetLogServiceImpl implements IretailBudgetLogService{

	@Resource
	IretailBudgetLogRepository iretailBudgetLogRepository;
	
	@Override
	public void save(List<IretailBudgetLog> list) {
		iretailBudgetLogRepository.save(list);
	}
	
	@Override
	public List<IretailBudgetLog> findByYearAndQuarterAndCommProvince(Integer year,
			Integer quarter, CommProvince province) {
		return iretailBudgetLogRepository.findByYearAndQuarterAndCommCity(year,quarter,province);
	}
}
