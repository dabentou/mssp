package com.mmd.mssp.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommApprovalLog;

@Repository
public interface CommApprovalLogRepository extends CrudRepository<CommApprovalLog, Integer> {
	
	@Query("select a from CommApprovalLog as a where a.dataId = ?1")
	CommApprovalLog getByDataId(Integer id);
}
