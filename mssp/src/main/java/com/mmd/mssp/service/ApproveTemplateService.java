package com.mmd.mssp.service;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.vo.Paging;


public interface ApproveTemplateService {
	
	ApproveTemplate save(ApproveTemplate approveTemplate);
	
	ApproveTemplate fandById(Integer id);
	
	Paging<ApproveTemplate> findAll(Integer page, Integer size);
	
	void delete(ApproveTemplate approveTemplate);
	
	Paging<ApproveTemplate> findByTypeName(String typeName,Integer page, Integer size);
	
	ApproveTemplate findTempByType(String tempType);
}
