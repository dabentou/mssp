package com.mmd.mssp.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.repository.B2CApprovalLogRepository;
import com.mmd.mssp.service.B2CApprovalLogService;

@Service
public class B2CApprovalLogServiceImpl implements B2CApprovalLogService {

	@Resource
	B2CApprovalLogRepository b2cApprovalLogRepository;

	@Override
	public B2CApproveLog save(B2CApproveLog approveLog) {
		return b2cApprovalLogRepository.save(approveLog);
	}

	@Override
	public List<B2CApproveLog> listByProject(B2CProject project) {
		return b2cApprovalLogRepository.findByProject(project);
	}

	@Override
	public List<B2CApproveLog> listByApprovDateAndStep(Date approvStartDate,
			Date approvEndDate,List<CommAgent> agents) {
		int startFlag = 1;
		int endFlag = 1;
		if(approvStartDate!=null){
			startFlag = 2;
		}
		if(approvEndDate!=null){
			endFlag = 2;
		}
		return b2cApprovalLogRepository.listByApprovDateAndStep(approvStartDate, startFlag, approvEndDate, endFlag,agents);
	}
	

}
