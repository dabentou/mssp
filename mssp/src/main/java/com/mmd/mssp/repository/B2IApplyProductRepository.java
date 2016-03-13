package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;

@Repository
public interface B2IApplyProductRepository extends CrudRepository<B2IApplyProduct, Integer> {

	List<B2IApplyProduct> findByB2IProject(B2IProject project);
	
	@Query("select b from B2IApplyProduct as b, B2IProject as a where a.id=b.b2IProject.id and a.agent in ?1 and (a.step.pnextId in ?2 or 1=?3) and (a.agent.commCity in ?4 or 1=?5) and (a.pCode like ?6 or 1=?7) and (a.pName like ?8 or 1=?9) and a.applytime>?10 and a.applytime<?11 and a.isDelete=0")
	List<B2IApplyProduct> listToExport(List<CommAgent> agentList,List<ProcessStep> processStepList, int processStepFlag,List<CommCity> cityList, int cityFlag,String pCode,int pCodeFlag,String pName,int pNameFlag,Date applyTimeStart,Date applyTimeEnd);

	
	@Query("select a from B2IApplyProduct as a where a.productPrice.product = ?1 and a.b2IProject.agent = ?2")
	List<B2IApplyProduct> findByProductAndAgent(Product product,CommAgent agent);
}
