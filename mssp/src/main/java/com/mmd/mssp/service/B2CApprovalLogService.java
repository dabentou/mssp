package com.mmd.mssp.service;


import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;

public interface B2CApprovalLogService {
	
	B2CApproveLog save(B2CApproveLog approveLog);
	
	List<B2CApproveLog> listByProject(B2CProject project);
	
	
	List<B2CApproveLog> listByApprovDateAndStep(Date approvStartDate,Date approvEndDate,List<CommAgent> agents);
}
