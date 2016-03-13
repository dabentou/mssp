package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.B2CCondition;
import com.mmd.mssp.domain.vo.Paging;

public interface B2CConditionService {
	
	List<B2CCondition> findByType(Integer type);
	
	B2CCondition getConditionById(Integer id);
	
	Paging<B2CCondition> findAll(Integer page, Integer size);
	
	public B2CCondition save(B2CCondition condition);
	
	public void delete(B2CCondition condition);
}
