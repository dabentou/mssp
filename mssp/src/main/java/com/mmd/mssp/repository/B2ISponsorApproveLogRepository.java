package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;

public interface B2ISponsorApproveLogRepository extends CrudRepository<B2ISponsorApproveLog, Integer> {

	List<B2ISponsorApproveLog> findByB2iSponsorProject(B2ISponsorProject sponsorProject);
}
