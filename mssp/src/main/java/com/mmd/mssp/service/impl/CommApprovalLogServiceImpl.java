package com.mmd.mssp.service.impl;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommApprovalLog;
import com.mmd.mssp.repository.CommApprovalLogRepository;
import com.mmd.mssp.service.CommApprovalLogService;


@Service
public class CommApprovalLogServiceImpl implements CommApprovalLogService {

	@Resource
	CommApprovalLogRepository commApprovalLogRepository;

	@Override
	public CommApprovalLog save(CommApprovalLog entity) {
		return commApprovalLogRepository.save(entity);
	}

	@Override
	public CommApprovalLog getByDataId(Integer id) {
		return commApprovalLogRepository.getByDataId(id);
	}
}
