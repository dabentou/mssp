package com.mmd.mssp.service;



import com.mmd.mssp.domain.CommApprovalLog;


public interface CommApprovalLogService {
	
	CommApprovalLog save(CommApprovalLog entity);
	
	CommApprovalLog getByDataId(Integer id);
}
