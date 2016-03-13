package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.ProcessStep;


public interface ProcessStepService {
	ProcessStep findById(Integer id);
	
	ProcessStep save(ProcessStep processStep);
	
	List<ProcessStep> findByTemp(ApproveTemplate approveTemplate);
	
	void delete(ProcessStep processStep);
	
	void deleteList(List<ProcessStep> list);
	
	ProcessStep isDelProcessStep(ApproveTemplate approveTemplate,ProcessStep processStep);
}
