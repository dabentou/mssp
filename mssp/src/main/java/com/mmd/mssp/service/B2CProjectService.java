package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import com.mmd.mssp.domain.B2CApproveLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;

public interface B2CProjectService {

	public B2CProject save(B2CProject b2CProject);
	
	public B2CProject getB2CProjectById(Integer id);
	/**
	 * 代理商的项目列表
	 * @param agent
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> listAllProjectByAgentAndStep(CommAgent agent,Integer page, Integer size);
	
	/**
	 * 根据项目id列表查询，
	 * @param ids
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> listAllProjectByIds(List<Integer> ids,Integer page, Integer size);
	
	List<B2CProject> listAllPorjectByAgentsAndStep(List<CommAgent> agents);
	
	/**
	 * 代办事宜列表
	 * @param agents
	 * @param role
	 * @return
	 */
	Paging<B2CProject> listAllProjectByAgentAndRole(List<CommAgent> agents,CommRole role,Integer page, Integer size);
	
	Paging<B2CProject> listAllProjectByStep(List<CommAgent> agents,Integer page, Integer size);

	void delete(B2CProject b2CProject);
	
	/**
	 * 代理商 已完成项目查询列表
	 * @param startDate
	 * @param endDate
	 * @param pCode
	 * @param agent
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> searchProjectByAgentAndStep(Date startDate,Date endDate,String pCode,CommAgent agent,List<Integer> projectIds,Integer page, Integer size);
	
	/**
	 * 代理商之外角色 已完成项目查询列表
	 * @param startDate
	 * @param endDate
	 * @param pCode
	 * @param agents
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> searchProjectAndStep(Date startDate,Date endDate,String pCode,List<CommAgent> agents,List<Integer> projectIds,Integer page, Integer size);
	
	/**
	 * 项目列表页筛选
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param agents
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> searchProjectByDateAndStatusAndStep(Date startDate,Date endDate,Integer status,List<CommAgent> agents,Integer page, Integer size);
	
	/**
	 * 代办事宜筛选查询
	 * @param startDate
	 * @param endDate
	 * @param status
	 * @param agents
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2CProject> searchProjectByDateAndRole(Date startDate,Date endDate,List<CommAgent> agents,CommRole role,Integer page, Integer size);

	public List<B2CApproveLog> findApproveLogList(B2CProject b2cProject);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @param log   
	* @return void   
	* @throws
	*/
	public void saveProjectAndApproveLog(B2CProject project, B2CApproveLog log);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<B2CProject>   
	* @throws
	*/
	public List<B2CProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
}
