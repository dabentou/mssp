package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;

@Repository
public interface B2CProjectRepository extends CrudRepository<B2CProject, Integer> {

	B2CProject getById(Integer id);
	
	@Query(nativeQuery=true, value="SELECT count(*) FROM b2c_project AS b  WHERE  b.agent_id=?1 AND b.apply_time>=?2 AND b.apply_time<=?3;")
	Integer sumByAgent(Integer agentId, Date currentMonthFisrtDay, Date date);
	
	/**
	 * 代理商的项目列表
	 * @param agent
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where a.agent = ?1 and a.processStep.id <> -1 order by a.id asc")
	Page<B2CProject> listAllProjectByAgentAndStep(CommAgent agent,Pageable page);
	
	
	/**
	 * 代理商的已完成的项目查询列表
	 * @param startDate
	 * @param endDate
	 * @param pCode
	 * @param pCodeFlag
	 * @param agent
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent = ?7 and a.processStep.id = -1  order by a.id asc")
	Page<B2CProject> searchProjectByAgentAndStep(Date startDate,int startDateFlag ,Date endDate,int endDateFlag,String pCode,int pCodeFlag,CommAgent agent,Pageable page);
	
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent = ?7 and a.processStep.id = -1 and a.id in ?8 order by a.id asc")
	Page<B2CProject> searchProjectByAgentAndStepAndIds(Date startDate,int startDateFlag,Date endDate,int endDateFlag,String pCode,int pCodeFlag,CommAgent agent,List<Integer> projectIds,Pageable page);
	
	
	/**
	 * 除了代理商，其他审核人看到的项目列表
	 */
	@Query("select a from B2CProject as a where a.agent in ?1 and a.processStep.id <> -1 order by a.id asc")
	Page<B2CProject> listAllProjectByStep(List<CommAgent> agents,Pageable page);
	
	/**
	 * 代办事宜列表
	 * @param agents
	 * @param role
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where a.agent in ?1 and a.processStep.pnextId.commRole=?2 order by a.id asc")
	Page<B2CProject> listAllProjectByAgentAndRole(List<CommAgent> agents,CommRole role,Pageable page);
	
	@Query("select a from B2CProject as a where a.agent in ?1 and a.processStep.id=-1 order by a.id asc")
	List<B2CProject> listAllPorjectByAgentsAndStep(List<CommAgent> agents);
	
	
	@Query("select a from B2CProject as a where a.id in ?1 order by a.id asc")
	Page<B2CProject> listAllProjectByIds(List<Integer> ids,Pageable page);
	
	/**
	 * 代理商之外角色 已完成项目查询列表
	 * @param startDate
	 * @param endDate
	 * @param pCode
	 * @param pCodeFlag
	 * @param agents
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent in ?7 and a.processStep.id = -1  order by a.id asc")
	Page<B2CProject> searchProjectAndStep(Date startDate,int startDateFlag,Date endDate,int endDateFlag,String pCode,int pCodeFlag,List<CommAgent> agents,Pageable page);
	
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent in ?7 and a.processStep.id = -1 and a.id in ?8 order by a.id asc")
	Page<B2CProject> searchProjectAndStepAndIds(Date startDate,int startDateFlag,Date endDate,int endDateFlag,String pCode,int pCodeFlag,List<CommAgent> agents,List<Integer> projectIds,Pageable page);
	
	/**
	 * 项目列表页筛选查询
	 * @param startDate
	 * @param startDateFlag
	 * @param endDate
	 * @param endDateFlag
	 * @param status
	 * @param statusFlag
	 * @param agents
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.processStep.id=?5 or 1=?6) and a.agent in ?7 and a.processStep.id <> -1 order by a.id asc")
	Page<B2CProject> searchProjectByDateAndStatusAndStep(Date startDate,int startDateFlag,Date endDate,int endDateFlag,Integer status,int statusFlag,List<CommAgent> agents,Pageable page);
	
	/**
	 * 代办事宜筛选查询
	 * @param startDate
	 * @param startDateFlag
	 * @param endDate
	 * @param endDateFlag
	 * @param role
	 * @param agents
	 * @param page
	 * @return
	 */
	@Query("select a from B2CProject as a where (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and a.processStep.pnextId.commRole=?5 and a.agent in ?6 order by a.id asc")
	Page<B2CProject> searchProjectByDateAndRole(Date startDate,int startDateFlag,Date endDate,int endDateFlag,CommRole role,List<CommAgent> agents,Pageable page);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return Integer   
	* @throws
	*/
	@Query("select count(0) from B2CProject as a where a.processStep.pnextId.commRole=?1 and a.agent in ?2")
	Integer countb2cToDo(CommRole commRole,List<CommAgent> agents);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<B2CProject>   
	* @throws
	*/
	@Query("select a from B2CProject as a where a.processStep.pnextId.commRole=?1 and a.agent in ?2")
	List<B2CProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
}
