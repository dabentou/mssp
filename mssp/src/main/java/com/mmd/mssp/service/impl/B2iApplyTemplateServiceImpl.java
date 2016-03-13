package com.mmd.mssp.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2iApplyTemplate;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2iApplyTemplateRepository;
import com.mmd.mssp.service.B2iApplyTemplateService;

@Service
public class B2iApplyTemplateServiceImpl implements B2iApplyTemplateService {
	
	@Resource
	B2iApplyTemplateRepository b2iApplyTemplateRepository;

	@Override
	public B2iApplyTemplate findById(Integer id,Boolean isDelete) {
		return b2iApplyTemplateRepository.findByIdAndIsDelete(id, isDelete?1:0);
	}

	@Override
	public B2iApplyTemplate save(B2iApplyTemplate applyTemplate) {
		return b2iApplyTemplateRepository.save(applyTemplate);
	}

	@Override
	public Paging<B2iApplyTemplate> findByIsDelete(boolean isDelete,
			Integer page, Integer size) {
		Paging<B2iApplyTemplate> paging = new Paging<B2iApplyTemplate>(page, size);
		Page<B2iApplyTemplate> result = b2iApplyTemplateRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		return paging; 
	}

	@Override
	public List<B2iApplyTemplate> listByIsDelete(boolean isDelete) {
		return b2iApplyTemplateRepository.listByIsDelete(isDelete?1:0);
	}

}
