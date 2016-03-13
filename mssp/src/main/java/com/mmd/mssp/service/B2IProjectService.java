package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;

public interface B2IProjectService {

	public B2IProject save(B2IProject b2IProject);
	
//	Paging<B2IProject> listAll(Integer startStep,Integer endStep,CommAgent agent,Integer page, Integer size);
	
	Paging<B2IProject> listAll(Integer startStep,Integer endStep,List<CommAgent> agentList,Integer page, Integer size);
	
	B2IProject findB2IProjectById(Integer projectId);
	
	void saveProjectAndApproveLog(B2IProject project, B2IApproveLog log);
	
	/**
	 * 不同角色的代办事宜
	 * @param commRole
	 * @return
	 */
	List<B2IProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
	
	List<B2IApproveLog> findApproveLogList(B2IProject project);
	/**
	 * 各种条件查询B2I项目列表
	 * @param agent
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param approveTimeStart
	 * @param approveTimeEnd
	 * @param processStepId
	 * @param cityId
	 * @param pCode
	 * @param pName
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2IProject> listAllBySearch(List<CommAgent> agentList,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,String pCode,String pName,Integer page,Integer size);
}
