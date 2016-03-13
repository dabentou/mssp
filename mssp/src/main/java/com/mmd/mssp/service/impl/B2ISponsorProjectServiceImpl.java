package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2ISponsorApproveLogRepository;
import com.mmd.mssp.repository.B2ISponsorProjectRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.B2ISponsorProjectService;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class B2ISponsorProjectServiceImpl implements B2ISponsorProjectService {
	
	@Resource
	B2ISponsorProjectRepository b2iSponsorProjectRepository;
	
	@Resource
	B2ISponsorApproveLogRepository b2iSponsorApproveLogRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	CommCityRepository cityRepository;
	
	@Resource
	ProcessStepRepository processStepRepository;

	@Override
	public B2ISponsorProject save(B2ISponsorProject b2iSponsorProject) {
		return b2iSponsorProjectRepository.save(b2iSponsorProject);
	}

	@Override
	public Paging<B2ISponsorProject> listAll(Integer startStep,
			Integer endStep, List<CommAgent> agentList, Integer page,
			Integer size) {
		Paging<B2ISponsorProject> paging = new Paging<B2ISponsorProject>(page, size);
		Page<B2ISponsorProject> result = b2iSponsorProjectRepository.listAll(agentList,startStep,endStep, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public B2ISponsorProject findById(Integer id) {
		return b2iSponsorProjectRepository.findOne(id);
	}

	@Override
	public List<B2ISponsorApproveLog> findApproveLogList(
			B2ISponsorProject sponsorProject) {
		return b2iSponsorApproveLogRepository.findByB2iSponsorProject(sponsorProject);
	}

	@Override
	public void saveProjectAndApproveLog(B2ISponsorProject sponsorProject,
			B2ISponsorApproveLog log) {
		ProcessStep step = processStepService.findById(sponsorProject.getStep().getId());
		if(step.getPnextId()==null){
			sponsorProject.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		b2iSponsorProjectRepository.save(sponsorProject);
		b2iSponsorApproveLogRepository.save(log);
	}

	@Override
	public Paging<B2ISponsorProject> listAllBySearch(List<CommAgent> agentList,
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
		Paging<B2ISponsorProject> paging = new Paging<B2ISponsorProject>(page, size);
		Page<B2ISponsorProject> result = b2iSponsorProjectRepository.listAllBySearch(agentList, processStepList, processStepFlag, cityList, cityFlag, "%"+pCode+"%", pCodeFlag, "%"+pName+"%", pNameFlag, applyTimeStart, applyTimeEnd, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<B2ISponsorProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents) {
		return b2iSponsorProjectRepository.findProjectListByRoleAndAgent(commRole,agents);
	}
}
