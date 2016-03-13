package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2IApproveLogRepository;
import com.mmd.mssp.repository.B2IProjectRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.B2IProjectService;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class B2IProjectServiceImpl implements B2IProjectService {

	@Resource
	B2IProjectRepository b2IProjectRepository;
	@Resource
	B2IApproveLogRepository b2IApproveLogRepository;
	@Resource
	ProcessStepRepository processStepRepository;
	@Resource
	CommCityRepository cityRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Override
	public B2IProject save(B2IProject b2iProject) {
		return b2IProjectRepository.save(b2iProject);
	}
	/*@Override
	public Paging<B2IProject> listAll(Integer startStep,Integer endStep,CommAgent agent,Integer page, Integer size) {
	int agentFlag = 1;
		
		if(agent != null){
			agentFlag = 2;
		}
		Paging<B2IProject> paging = new Paging<B2IProject>(page, size);
		Page<B2IProject> result = b2IProjectRepository.listAll(agent,agentFlag,startStep,endStep, paging.toPage());
		paging.setResult(result);
		return paging;
	}*/
	
	@Override
	public B2IProject findB2IProjectById(Integer projectId) {
		return b2IProjectRepository.findById(projectId);
	}

	@Override
	public void saveProjectAndApproveLog(B2IProject project, B2IApproveLog log) {
		ProcessStep step = processStepService.findById(project.getStep().getId());
		if(step.getPnextId()==null){
			project.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		b2IProjectRepository.save(project);
		b2IApproveLogRepository.save(log);
	}

	@Override
	public List<B2IProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents) {
		return b2IProjectRepository.findProjectListByRoleAndAgent(commRole,agents);
	}

	@Override
	public List<B2IApproveLog> findApproveLogList(B2IProject project) {
		return b2IApproveLogRepository.findByProject(project);
	}

	@Override
	public Paging<B2IProject> listAllBySearch(List<CommAgent> agentList,
			Date applyTimeStart, Date applyTimeEnd, Date approveTimeStart,
			Date approveTimeEnd, Integer[] processStepId, Integer[] cityId,
			String pCode, String pName, Integer page, Integer size) {
		int processStepFlag = 1;
		int cityFlag = 1;
		int pCodeFlag = 1;
		int pNameFlag = 1;
		
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommCity> cityList = new ArrayList<CommCity>();

		if(processStepId!=null){
			processStepFlag = 2;
			for(int i=0; i<processStepId.length; i++){
				processStepList.add(processStepRepository.getById(processStepId[i]));
			}
		}else{
			processStepList.add(new ProcessStep());
		}
		if(cityId!=null){
			cityFlag = 2;
			for(int i=0; i<cityId.length; i++){
				cityList.add(cityRepository.getById(cityId[i]));
			}
		}else{
			cityList.add(new CommCity());
		}
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(!StringUtils.isBlank(pName)){
			pNameFlag = 2;
		}
		Paging<B2IProject> paging = new Paging<B2IProject>(page, size);
		Page<B2IProject> result = b2IProjectRepository.listAllBySearch(agentList, processStepList, processStepFlag, cityList, cityFlag, "%"+pCode+"%", pCodeFlag, "%"+pName+"%", pNameFlag, applyTimeStart, applyTimeEnd, paging.toPage());
		paging.setResult(result);
		return paging;
	}
	@Override
	public Paging<B2IProject> listAll(Integer startStep, Integer endStep,
			List<CommAgent> agentList, Integer page, Integer size) {
		Paging<B2IProject> paging = new Paging<B2IProject>(page, size);
		Page<B2IProject> result = b2IProjectRepository.listAll(agentList,startStep,endStep, paging.toPage());
		paging.setResult(result);
		return paging;
	}

}
