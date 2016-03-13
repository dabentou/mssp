package com.mmd.mssp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.ApplyCloseProject;
import com.mmd.mssp.domain.B2BProject;

@Repository
public interface ApplyCloseProjectRepository extends CrudRepository<ApplyCloseProject,Integer>{
	
	public ApplyCloseProject findByb2bProject(B2BProject b2bProject);

}
