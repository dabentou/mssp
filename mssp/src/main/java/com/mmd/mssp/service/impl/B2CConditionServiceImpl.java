package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2CCondition;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2CConditionRepository;
import com.mmd.mssp.service.B2CConditionService;

@Service
public class B2CConditionServiceImpl implements B2CConditionService {

	@Resource
	B2CConditionRepository conditionRepository;
	
	@Override
	public List<B2CCondition> findByType(Integer type) {
		return conditionRepository.findByType(type);
	}

	@Override
	public B2CCondition getConditionById(Integer id) {
		return conditionRepository.getById(id);
	}

	@Override
	public Paging<B2CCondition> findAll(Integer page, Integer size) {
		Paging<B2CCondition> paging = new Paging<B2CCondition>(page, size);
		Page<B2CCondition> result = conditionRepository.findAll(paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public B2CCondition save(B2CCondition condition) {
		return conditionRepository.save(condition);
	}

	@Override
	public void delete(B2CCondition condition) {
		conditionRepository.delete(condition);
	}

}