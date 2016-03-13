package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.SpecialApplyProduct;


public interface B2BProjectService {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param projectId
	* @param @return   
	* @return B2BProject   
	* @throws
	*/
	B2BProject findB2BProjectById(Integer projectId);

	/**
	* @author: sheng.tian
	* @Description: 保存项目报备和特殊申请单中的型号申请列表
	* @param @param bProject
	* @param @param pronumids
	* @param @param pronum
	* @param @param cptf
	* @param @param cppf
	* @param @param cpnf   
	* @return void   
	* @throws
	*/
	void saveProjectAndSpecial(B2BProject bProject, String[] pronumids,String[] pronum, String[] cptf,String[] cppf, String[] cpnf);
	/**
	* @author: sheng.tian
	* @Description: 列出所有项目的信息
	* @param @param bProject
	* @param @param pronumids
	* @param @param pronum
	* @param @param cptf
	* @param @param cppf
	* @param @param cpnf   
	* @return void   
	* @throws
	*/

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @param sapList   
	* @return void   
	* @throws
	*/
	void saveSAPListAndProject(B2BProject project,List<SpecialApplyProduct> sapList);
	
	Paging<B2BProject> listAll(Integer startStep,Integer endStep,List<CommAgent> agents,boolean isLastStep,Integer page,Integer size);
	
	void delete(B2BProject b2BProject,Boolean idDel);
	
	void save(B2BProject b2BProject);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @return   
	* @return List<b2bApproveLog>   
	* @throws
	*/
	List<B2BApproveLog> findApproveLogList(B2BProject project);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @param log   
	* @return void   
	* @throws
	*/
	void saveProjectAndApproveLog(B2BProject project, B2BApproveLog log);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<B2BProject>   
	* @throws
	*/
	List<B2BProject> findProjectListByRole(CommRole commRole);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @param parameter   
	* @return void   
	* @throws
	*/
	void saveColseProject(B2BProject project, String projectInfo);
	/**
	 * 各种条件查询B2B项目列表
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param approveTimeStart
	 * @param approveTimeEnd
	 * @param productId
	 * @param cityId
	 * @param businessId
	 * @param pCode
	 * @param pName
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2BProject> listAllBySearch(CommAgent agent,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,Integer[] businessId,String pCode,String pName,Integer[] pTypeId,Integer page,Integer size);
	
	/**
	 * 根据代理商列出项目
	 * @return
	 */
	List<B2BProject> listProjectByAgents(List<CommAgent> commAgents);
	
	List<B2BProject> todo(List<CommAgent> commAgents,CommRole role);
	
	Paging<B2BProject> search(String ppn,String startTime,String endTime,String status,String agentId,List<CommAgent> commAgents,Integer page,Integer size) throws Exception;
}
