package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.CommAgent;

@Repository
public interface B2CSpecialApplyProductRepository extends CrudRepository<B2CSpecialApplyProduct, Integer> {

	B2CSpecialApplyProduct getById(Integer id);
	
	@Query("select a from B2CSpecialApplyProduct as a where a.project = (?1) order by a.isAgree desc")
	List<B2CSpecialApplyProduct> findByB2CProject(B2CProject project);
	/**
	 * 代理商导出excel数据报表
	 * @param startDate
	 * @param endDate
	 * @param pCode
	 * @param pCodeFlag
	 * @param agent
	 * @return
	 */
	@Query("select a from B2CSpecialApplyProduct as a,B2CProject as b where a.project.id = b.id and b.applytime>=?1 and b.applytime<=?2 and (b.pCode like ?3 or 1=?4) and b.agent = ?5 order by b.applytime desc,b.theme asc")
	List<B2CSpecialApplyProduct> searchProjectToExportByAgent(Date startDate,Date endDate,String pCode,int pCodeFlag,CommAgent agent);

	@Query("select b from B2CSpecialApplyProduct as b,B2CProject as a where b.project.id = a.id and (a.applytime>=?1 or 1=?2) and (a.applytime<=?3 or 1=?4) and (a.pCode like ?5 or 1=?6) and a.agent in ?7 and a.processStep.id = -1 and (a.id in ?8 or 1=?9) order by a.id asc")
	List<B2CSpecialApplyProduct> searchProjectToExport(Date startDate,int startDateFlag,Date endDate,int endDateFlag,String pCode,int pCodeFlag,List<CommAgent> agentList,List<Integer> projectIds,int projectIdFlag);
}
