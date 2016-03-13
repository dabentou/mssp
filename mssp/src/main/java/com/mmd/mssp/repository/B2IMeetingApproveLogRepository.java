package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mmd.mssp.domain.B2IMeetingApproveLog;
import com.mmd.mssp.domain.B2IMeetingProject;

public interface B2IMeetingApproveLogRepository extends CrudRepository<B2IMeetingApproveLog, Integer> {

	List<B2IMeetingApproveLog> findByProject(B2IMeetingProject project);
}
