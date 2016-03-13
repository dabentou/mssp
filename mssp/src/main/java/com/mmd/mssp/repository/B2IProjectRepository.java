package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;

@Repository
public interface B2IProjectRepository extends CrudRepository<B2IProject, Integer> {

	@Query(nativeQuery=true, value="SELECT count(*) FROM b2i_project AS b  WHERE  b.agent_id=?1 AND b.apply_time>=?2 AND b.apply_time<=?3;")
	Integer sumByAgent(Integer agentId, Date currentMonthFisrtDay, Date date);
	
	/*@Query("select a from B2IProject as a where (a.agent=?1 or 1=?2) and a.step.statusValue>=?3 and a.step.statusValue<?4 and a.isDelete=0 order by a.id desc")
	Page<B2IProject> listAll(CommAgent agent,int agentFlag ,Integer startStep,Integer endStep,Pageable page);*/

	@Query("select a from B2IProject as a where a.id=?1 and a.isDelete=0")
	B2IProject findById(Integer projectId);
	
	@Query("select a from B2IProject as a where a.step.pnextId.commRole=?1 and a.agent in ?2  and a.isDelete=0 order by a.id desc")
	List<B2IProject> findProjectListByRoleAndAgent(CommRole commRole,List<CommAgent> agents);
	
	@Query("select count(0) from B2IProject as a where a.step.pnextId.commRole=?1 and a.agent in ?2")
	Integer countb2iToDo(CommRole commRole,List<CommAgent> agents);
	
	@Query("select a from B2IProject as a where a.agent in ?1 and (a.step.pnextId in ?2 or 1=?3) and (a.agent.commCity in ?4 or 1=?5) and (a.pCode like ?6 or 1=?7) and (a.pName like ?8 or 1=?9) and a.applytime>?10 and a.applytime<?11 and a.isDelete=0")
	Page<B2IProject> listAllBySearch(List<CommAgent> agent,List<ProcessStep> processStepList, int processStepFlag,List<CommCity> cityList, int cityFlag,String pCode,int pCodeFlag,String pName,int pNameFlag,Date applyTimeStart,Date applyTimeEnd,Pageable page);
	
	/**
	 * 
	 * @param agentList
	 * @param startStep
	 * @param endStep
	 * @param page
	 * @return
	 */
	@Query("select a from B2IProject as a where a.agent in ?1 and (a.step.statusValue>=?2 and a.step.statusValue<?3 or a.step.statusValue=-1) and a.isDelete=0 order by a.id desc")
	Page<B2IProject> listAll(List<CommAgent> agentList,Integer startStep,Integer endStep,Pageable page);
}
