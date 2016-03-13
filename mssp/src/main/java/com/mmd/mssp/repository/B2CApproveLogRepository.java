package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommRole;


@Repository
public interface B2CApproveLogRepository extends CrudRepository<B2CApproveLog, Integer> {

	List<B2CApproveLog> findByProject(B2CProject b2cProject);

}
