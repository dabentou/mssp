package com.mmd.mssp.service.impl;


import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.service.ApproveTemplateService;

@Service
@Transactional
public class ApproveTemplateServiceImpl implements ApproveTemplateService {
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;

	@Override
	public ApproveTemplate save(ApproveTemplate approveTemplate) {
		return approveTemplateRepository.save(approveTemplate);
	}

	@Override
	public ApproveTemplate fandById(Integer id) {
		return approveTemplateRepository.findOne(id);
	}

	@Override
	public Paging<ApproveTemplate> findAll(Integer page, Integer size) {
		Paging<ApproveTemplate> paging = new Paging<ApproveTemplate>(page, size);
		Page<ApproveTemplate> result = approveTemplateRepository.findAll(paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public void delete(ApproveTemplate approveTemplate) {
		approveTemplateRepository.delete(approveTemplate);
	}

	@Override
	public Paging<ApproveTemplate> findByTypeName(String typeName,Integer page, Integer size) {
		Paging<ApproveTemplate> paging = new Paging<ApproveTemplate>(page, size);
		Page<ApproveTemplate> result = approveTemplateRepository.findByTypeName(typeName, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public ApproveTemplate findTempByType(String tempType) {
		return approveTemplateRepository.findTempByType(tempType);
	}
	
}
