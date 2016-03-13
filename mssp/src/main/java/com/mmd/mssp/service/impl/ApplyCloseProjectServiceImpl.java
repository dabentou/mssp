package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.ApplyCloseProject;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.repository.ApplyCloseProjectRepository;
import com.mmd.mssp.service.ApplyCloseProjectService;

@Service
public class ApplyCloseProjectServiceImpl implements ApplyCloseProjectService{

	@Resource 
	ApplyCloseProjectRepository applyCloseProjectRepository;
	
	@Override
	public void save(ApplyCloseProject applyCloseProject) {
		applyCloseProjectRepository.save(applyCloseProject);
	}

	@Override
	public ApplyCloseProject findByb2bProject(B2BProject b2bProject) {
		return applyCloseProjectRepository.findByb2bProject(b2bProject);
	}
}
