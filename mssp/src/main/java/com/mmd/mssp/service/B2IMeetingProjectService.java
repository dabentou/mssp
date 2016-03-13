package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IMeetingApproveLog;
import com.mmd.mssp.domain.B2IMeetingProject;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.B2ISponsorApproveLog;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;

public interface B2IMeetingProjectService {
	
	B2IMeetingProject save(B2IMeetingProject meetingProject);
	
	Paging<B2IMeetingProject> listAll(Integer startStep,Integer endStep,List<CommAgent> agentList,Integer page, Integer size);
	
	public B2IMeetingProject findById(Integer id);
	
	List<B2IMeetingApproveLog> findApproveLogList(B2IMeetingProject meetingProject);
	
	void saveProjectAndApproveLog(B2IMeetingProject meetingProject, B2IMeetingApproveLog log);
	
	List<B2IMeetingProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
	
	Paging<B2IMeetingProject> listAllBySearch(List<CommAgent> agentList,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,String pCode,String pName,Integer page,Integer size);

}
