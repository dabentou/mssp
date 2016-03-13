package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CApplyOther;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.CommAgent;

@Repository
public interface B2CApplyOtherRepository extends CrudRepository<B2CApplyOther, Integer> {

	B2CApplyOther getById(Integer id);
	
	@Query("select a from B2CApplyOther as a where a.b2CProject = (?1) and a.type=?2 order by a.isAgree desc")
	List<B2CApplyOther> findByProjectAndType(B2CProject project,Integer type);
	
	@Query("select a from B2CApplyOther as a where a.b2CProject = (?1) order by a.isAgree desc")
	List<B2CApplyOther> findByProject(B2CProject project);
	
	@Query("select b from B2CApplyOther as b,B2CProject as a where b.b2CProject.id = a.id and (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent in ?7 and a.processStep.id = -1 and (a.id in ?8 or 1=?9) order by a.id asc")
	List<B2CApplyOther> searchProjectToExport(Date startDate,int startDateFlag,Date endDate,int endDateFlag,String pCode,int pCodeFlag,List<CommAgent> agentList,List<Integer> projectIds,int projectIdFlag);
}
