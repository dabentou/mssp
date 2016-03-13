package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;

@Repository
public interface B2CApprovalLogRepository extends CrudRepository<B2CApproveLog, Integer> {
	
	List<B2CApproveLog> findByProject(B2CProject b2cProject);
	
	@Query("select a from B2CApproveLog as a where (a.approveTime>=?1 or 1=?2) and (a.approveTime<=?3 or 1=?4) and a.project.processStep.id = -1 and a.project.agent in ?5 order by a.id asc")
	List<B2CApproveLog> listByApprovDateAndStep(Date approvStartDate,int startFlag, Date approvEndDate, int endFlag,List<CommAgent> agents);

}
