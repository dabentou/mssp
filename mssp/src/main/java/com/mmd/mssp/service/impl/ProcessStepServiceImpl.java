package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class ProcessStepServiceImpl implements ProcessStepService {
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Override
	public ProcessStep findById(Integer id) {
		return processStepRepository.findOne(id);
	}

	@Override
	public ProcessStep save(ProcessStep processStep) {
		return processStepRepository.save(processStep);
	}

	@Override
	public List<ProcessStep> findByTemp(ApproveTemplate approveTemplate) {
		return processStepRepository.findByTemp(approveTemplate);
	}

	@Override
	public void delete(ProcessStep processStep) {
		processStepRepository.delete(processStep);
	}

	@Override
	public void deleteList(List<ProcessStep> list) {
		processStepRepository.delete(list);
	}
 
	@Override
	public ProcessStep isDelProcessStep(ApproveTemplate approveTemplate,ProcessStep processStep) {
		ProcessStep notDelPStep = null;
		List<ProcessStep> list = this.findByTemp(approveTemplate);
		for(int i=0; i<list.size(); i++){
			int pnaxtId = list.get(i).getPnextId()==null?0:list.get(i).getPnextId().getId();
			int rnextId = list.get(i).getRnextId()==null?0:list.get(i).getRnextId().getId();
			int pStepId = processStep.getId();
			if(pnaxtId == pStepId){
				notDelPStep = processStep;
				return notDelPStep;
			}else if(rnextId == pStepId){
				notDelPStep = processStep;
				return notDelPStep;
			}
		}
		return notDelPStep;
	}
}
