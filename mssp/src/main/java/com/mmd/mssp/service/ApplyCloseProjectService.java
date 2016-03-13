package com.mmd.mssp.service;

import com.mmd.mssp.domain.ApplyCloseProject;
import com.mmd.mssp.domain.B2BProject;

public interface ApplyCloseProjectService {
	void save(ApplyCloseProject applyCloseProject);
	
	/**
	 * 根据b2b项目获取项目结案申请信息
	 * @param b2bProject
	 * @return
	 */
	ApplyCloseProject findByb2bProject(B2BProject b2bProject);
}
