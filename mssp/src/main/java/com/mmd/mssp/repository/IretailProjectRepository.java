package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.ProcessStep;

@Repository
public interface IretailProjectRepository extends CrudRepository<IretailProject, Integer>{
	
/*	@Query("select a from IretailProject as a  where a.iType=?1 and a.store=?2 order by a.id desc")
	List<IretailProject> findIretailProjectByTypeAndAgent(String type,CommAgent commAgent);*/
	@Query("select a from IretailProject as a  where a.iType=?1 and a.province in ?2 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectByTypeAndAgent(String type,List<CommProvince> currentProvinces,Pageable page);
	
	@Query("select a from IretailProject as a  where a.iType=?1 and a.province in ?2 and a.processStep.statusValue>=?3 and a.processStep.statusValue<?4 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectByTypeAndAgent(String type,List<CommProvince> currentProvinces,Integer startStep,Integer endStep,Pageable page);
	
//	@Query("select a from B2IProject as a where (a.agent=?1 or 1=?2) and (a.step.pnextId in ?3 or 1=?4) and (a.agent.commCity in ?5 or 1=?6) and (a.pCode like ?7 or 1=?8) and (a.pName like ?9 or 1=?10) and a.applytime>?11 and a.applytime<?12 and a.isDelete=0")
	@Query("select a from IretailProject as a  where (a.processStep=?1 or 1=?2) and (a.province=?3 or (1=?4 and a.province in ?5)) and (a.ppn=?6 or 1=?7) and a.iType=?8 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectBySearch(ProcessStep processStepId,Integer processStepIdFlag, CommProvince province,Integer provinceIdFlag,List<CommProvince> currentProvinces,String ppn,Integer ppnFlag,String type,Pageable page);
	
	@Query("select a from IretailProject as a  where a.processStep.statusValue > 9 and (a.province=?1 or (1=?2 and a.province in ?3)) and (a.ppn=?4 or 1=?5) and a.iType=?6 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectBySearchByApply(CommProvince province,Integer provinceIdFlag,List<CommProvince> currentProvinces,String ppn,Integer ppnFlag,String type,Pageable page);

	@Query("select a from IretailProject as a  where a.processStep.statusValue > 0 and a.processStep.statusValue < 10 and (a.province=?1 or (1=?2 and a.province in ?3)) and (a.ppn=?4 or 1=?5) and a.iType=?6 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectBySearchByApply1(CommProvince province,Integer provinceIdFlag,List<CommProvince> currentProvinces,String ppn,Integer ppnFlag,String type,Pageable page);
	
	@Query("select a from IretailProject as a  where a.processStep.statusValue > 9 and (a.province=?1 or (1=?2 and a.province in ?3)) and (a.ppn=?4 or 1=?5) and a.iType=?6 and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProjectBySearchByApply2(CommProvince province,Integer provinceIdFlag,List<CommProvince> currentProvinces,String ppn,Integer ppnFlag,String type,Pageable page);
	
	@Query("select a from IretailProject as a  where a.isDelete=0 order by a.id desc")
	Page<IretailProject> findAllIretailProject(Pageable page);

	IretailProject getById(Integer id);
	
	@Query("select a from IretailProject as a where a.ppn = ?1 and (a.iType = ?2 or 1=?3)  and a.isDelete=0 ")
	IretailProject findByPPNAndIType(String ppn,String iType,Integer iTypeFlag);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return Object   
	* @throws
	*/
	@Query("select count(0) from IretailProject as a where a.processStep.commRole=?1 and a.province in ?2  and a.isDelete=0 ")
	Integer countIretailToDo(CommRole commRole,List<CommProvince>  provinces);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   /////
	* @return List<IretailProject>   
	* @throws
	*/	
	@Query("select a from IretailProject as a where a.processStep.commRole=?1 and a.province in ?2 and a.isDelete=0   order by a.id desc")
	List<IretailProject> findProjectListByRole(CommRole commRole,List<CommProvince>  provinces);
	
	@Query("select a from IretailProject as a  where a.iType=?1  and a.isDelete=0 order by a.id desc")
	List<IretailProject> findIretailProjectByType(String type);
	
	/**
	 * 根据各个搜索条件查询（区域、状态可多选）
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param type
	 * @param areaList
	 * @param areaFlag
	 * @param processStepList
	 * @param processStepFlag
	 * @param ppn
	 * @param ppnFlag
	 * @param page
	 * @return
	 */
	@Query("select a from IretailProject as a  where a.applyDate>=?1 and a.applyDate<=?2 and a.iType=?3 and (a.province in ?4 or 1=?5) and (a.processStep in ?6 or 1=?7) and (a.ppn =?8 or 1=?9) and a.isDelete=0  order by a.id desc")
	Page<IretailProject> findIretailProjectBySearch(Date applyTimeStart,Date applyTimeEnd,String type,List<CommProvince> provinceList,int areaFlag,List<ProcessStep> processStepList,int processStepFlag,String ppn,int ppnFlag,Pageable page);
	
	
	@Query("select a from IretailProject as a  where a.applyDate>=?1 and a.applyDate<=?2 and (a.iType=?3 or 1=?4) and (a.province in ?5 or 1=?6) and (a.processStep in ?7 or 1=?8) and (a.ppn =?9 or 1=?10) and a.isDelete=0 order by a.id desc")
	Page<IretailProject> findIretailProject(Date applyTimeStart,Date applyTimeEnd,String type,int typeFlag,List<CommProvince> provinceList,int areaFlag,List<ProcessStep> processStepList,int processStepFlag,String ppn,int ppnFlag,Pageable page);
	/**
	 * 查询出一个list后用以导出excel
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param type
	 * @param areaList
	 * @param areaFlag
	 * @param processStepList
	 * @param processStepFlag
	 * @param ppn
	 * @param ppnFlag
	 * @return
	 */
	@Query("select a from IretailProject as a  where a.applyDate>=?1 and a.applyDate<=?2 and a.iType=?3 and (a.province in ?4 or 1=?5) and (a.processStep in ?6 or 1=?7) and (a.ppn =?8 or 1=?9) and a.isDelete=0  order by a.id desc")
	List<IretailProject> findIretailProjectBySearchToExport(Date applyTimeStart,Date applyTimeEnd,String type,List<CommProvince> provinceList,int areaFlag,List<ProcessStep> processStepList,int processStepFlag,String ppn,int ppnFlag);

	@Query("select a from IretailProject as a  where a.iType =?1 and a.applyDate>=?2 and a.applyDate<=?3")
	List<IretailProject> listByTypeAndApplyDate(String type,Date applyDate,Date endDate);
}
