package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.SpecialApplyProduct;


@Repository
public interface SpecialApplyProductRepository extends CrudRepository<SpecialApplyProduct, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @return   
	* @return List<SpecialApplyProduct>   
	* @throws
	*/
	List<SpecialApplyProduct> findByB2bProject(B2BProject project);
	
	SpecialApplyProduct findById(Integer id);
	
	@Query("select a from SpecialApplyProduct as a where a.b2bProject.id = (?1) and a.productPrice.product.id = (?2)")
	SpecialApplyProduct findByB2bProjectIdAndProductId(int projectId,int productId);

	@Query("select b from SpecialApplyProduct b,B2BProject a where a.id = b.b2bProject.id and a.temp = a.step.temp and (a.agent=?1 or 1=?2) and (a.step in ?3 or 1=?4) and (a.agent.commCity in ?5 or 1=?6) and (a.business in ?7 or 1=?8) and (a.pCode like ?9 or 1=?10) and (a.pName like ?11 or 1=?12) and (a.pType in ?13 or 1=?14) order by a.id desc ")
	List<SpecialApplyProduct> listToExport(CommAgent agent,int agentFlag,List<ProcessStep> processStepList, int processStepFlag,List<CommCity> cityList, int cityFlag, List<CommBusiness> businessList,int businessFlag,String pCode,int pCodeFlag,String pName,int pNameFlag,List<Integer> pTypeList,int pTypeFlag);

}
