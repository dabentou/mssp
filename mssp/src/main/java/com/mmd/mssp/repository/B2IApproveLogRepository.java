package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IProject;

public interface B2IApproveLogRepository extends CrudRepository<B2IApproveLog, Integer> {

	List<B2IApproveLog> findByProject(B2IProject project);
}
