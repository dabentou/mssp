package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.B2IMeetingApproveLog;
import com.mmd.mssp.domain.B2IMeetingProject;
import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.B2IMeetingApproveLogRepository;
import com.mmd.mssp.repository.B2IMeetingProjectRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.B2IMeetingProjectService;
import com.mmd.mssp.service.ProcessStepService;

@Service
public class B2IMeetingProjectServiceImpl implements B2IMeetingProjectService {
	
	@Resource
	B2IMeetingProjectRepository b2iMeetingProjectRepository;
	
	@Resource
	B2IMeetingApproveLogRepository b2iMeetingApproveLogRepository;
	
	@Resource
	ProcessStepService processStepService;
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Resource
	CommCityRepository cityRepository;

	@Override
	public B2IMeetingProject save(B2IMeetingProject meetingProject) {
		return b2iMeetingProjectRepository.save(meetingProject);
	}

	@Override
	public Paging<B2IMeetingProject> listAll(Integer startStep,
			Integer endStep, List<CommAgent> agentList, Integer page,
			Integer size) {
		Paging<B2IMeetingProject> paging = new Paging<B2IMeetingProject>(page, size);
		Page<B2IMeetingProject> result = b2iMeetingProjectRepository.listAll(agentList,startStep,endStep, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public B2IMeetingProject findById(Integer id) {
		return b2iMeetingProjectRepository.findOne(id);
	}

	@Override
	public List<B2IMeetingApproveLog> findApproveLogList(
			B2IMeetingProject meetingProject) {
		return b2iMeetingApproveLogRepository.findByProject(meetingProject);
	}

	@Override
	public void saveProjectAndApproveLog(B2IMeetingProject meetingProject,
			B2IMeetingApproveLog log) {
		ProcessStep step = processStepService.findById(meetingProject.getStep().getId());
		if(step.getPnextId()==null){
			meetingProject.setStep(processStepService.findById(Constants.APPROVE_FINSHED));
		}
		b2iMeetingProjectRepository.save(meetingProject);
		b2iMeetingApproveLogRepository.save(log);
	}

	@Override
	public List<B2IMeetingProject> findProjectListByRoleAndAgent(
			CommRole commRole, List<CommAgent> agents) {
		return b2iMeetingProjectRepository.findProjectListByRoleAndAgent(commRole, agents);
	}

	@Override
	public Paging<B2IMeetingProject> listAllBySearch(List<CommAgent> agentList,
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
		Paging<B2IMeetingProject> paging = new Paging<B2IMeetingProject>(page, size);
		Page<B2IMeetingProject> result = b2iMeetingProjectRepository.listAllBySearch(agentList, processStepList, processStepFlag, cityList, cityFlag, "%"+pCode+"%", pCodeFlag, "%"+pName+"%", pNameFlag, applyTimeStart, applyTimeEnd, paging.toPage());
		paging.setResult(result);
		return paging;
	}
}
