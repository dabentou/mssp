package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;

public interface B2ISponsorProjectService {
	
	public B2ISponsorProject save(B2ISponsorProject b2iSponsorProject);
	
	Paging<B2ISponsorProject> listAll(Integer startStep,Integer endStep,List<CommAgent> agentList,Integer page, Integer size);
	
	public B2ISponsorProject findById(Integer id);
	
	List<B2ISponsorApproveLog> findApproveLogList(B2ISponsorProject sponsorProject);
	
	void saveProjectAndApproveLog(B2ISponsorProject sponsorProject, B2ISponsorApproveLog log);
	
	Paging<B2ISponsorProject> listAllBySearch(List<CommAgent> agentList,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,String pCode,String pName,Integer page,Integer size);

	/**
	 * 不同角色的代办事宜
	 * @param commRole
	 * @return
	 */
	List<B2ISponsorProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
}
