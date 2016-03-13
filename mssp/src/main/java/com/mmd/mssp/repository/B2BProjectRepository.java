package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;

@Repository
public interface B2BProjectRepository extends CrudRepository<B2BProject, Long> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param projectId
	* @param @return   
	* @return B2BProject   
	* @throws
	*/
	@Query("select a from B2BProject as a where a.id=?1 and a.isDelete=0")
	B2BProject findById(Integer projectId);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param agent
	* @param @param currentMonthFisrtDay
	* @param @param date
	* @param @return   
	* @return int   
	* @throws
	*/
	@Query(nativeQuery=true, value="SELECT count(*) FROM b2b_project AS b  WHERE  b.agent_id=?1 AND b.apply_time>=?2 AND b.apply_time<?3 AND b.is_delete=0;")
	Integer sumByAgent(Integer agentId, Date currentMonthFisrtDay, Date date);
	
	@Query("select a from B2BProject as a where (a.agent in ?1 or 1=?2) and a.step.statusValue>=?3 and a.step.statusValue<?4 and a.isDelete=0 order by a.id desc")
	Page<B2BProject> listAll(List<CommAgent> agent,int agentFlag ,Integer startStep,Integer endStep,Pageable page);
	
	@Query("select a from B2BProject as a where (a.agent in ?1 or 1=?2) and ((a.step.statusValue>=?3 and a.step.statusValue<?4) or a.step=?5 ) and a.isDelete=0 order by a.id desc")
	Page<B2BProject> listAll(List<CommAgent> agent,int agentFlag ,Integer startStep,Integer endStep,ProcessStep lastStep,Pageable page);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param id
	* @param @return   
	* @return Integer   
	* @throws
	*/
	@Query("select count(0) from B2BProject as a where (a.step.pnextId.commRole=?2 and a.pType is not null and a.isDelete=0 and a.agent in ?1) or (a.step.commRole=?2 and a.step.statusValue=1 and a.agent in ?1 and ((a.pIsBid=1 and a.commitmentNote is null) or (a.proto=1 and a.sampleDeal is null) or (a.entrust=1 and a.entrustPic is null) or (a.pType is null) ))")
	Integer countB2BToDo(List<CommAgent> commAgents,CommRole commRole);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<B2BProject>   
	* @throws
	*/
	@Query("select a from B2BProject as a where (a.step.pnextId.commRole=?2 and a.pType is not null and a.isDelete=0 and a.agent in ?1) or (a.step.commRole=?2 and a.step.statusValue=1 and a.agent in ?1 and ((a.pIsBid=1 and a.commitmentNote is null) or (a.proto=1 and a.sampleDeal is null) or (a.entrust=1 and a.entrustPic is null) or (a.pType is null) )) order by a.id desc")
	List<B2BProject> todot(List<CommAgent> commAgents,CommRole commRole);
	
	@Query("select a from B2BProject as a where a.step.pnextId.commRole=?1 and a.isDelete=0 order by a.id desc")
	List<B2BProject> findProjectListByRole(CommRole commRole);
	
	@Query("select distinct  a from B2BProject as a where a.agent in ?1 and a.isDelete=0 order by a.id desc")
	List<B2BProject> listProjectByAgents(List<CommAgent> agents);
	
	@Query("select a from B2BProject as a where a.temp = a.step.temp and (a.agent=?1 or 1=?2) and (a.step in ?3 or 1=?4) and (a.agent.commCity in ?5 or 1=?6) and (a.business in ?7 or 1=?8) and (a.pCode like ?9 or 1=?10) and (a.pName like ?11 or 1=?12) and (a.pType in ?13 or 1=?14) and a.isDelete=0 order by a.id desc")
	Page<B2BProject> listAllBySearch(CommAgent agent,int agentFlag,List<ProcessStep> processStepList, int processStepFlag,List<CommCity> cityList, int cityFlag, List<CommBusiness> businessList,int businessFlag,String pCode,int pCodeFlag,String pName,int pNameFlag,List<Integer> pTypeList,int pTypeFlag,Pageable page);

	@Query("select a from B2BProject as a where (2=?2 or a.pCode=?1) and (2=?4 or a.applyTime >= ?3) and (2=?6 or a.applyTime <= ?5) and (2=?8 or a.step.id=?7) and a.agent in ?9 order by a.id desc")
	Page<B2BProject> search(String ppn,int ppnFlag,Date startTime,int startDateFlag,Date endDate,int endDateFlag,int statusId,int statusFlag,List<CommAgent> agents,Pageable page);
}
