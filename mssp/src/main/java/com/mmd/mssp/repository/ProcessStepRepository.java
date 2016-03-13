package com.mmd.mssp.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.ProcessStep;

@Repository
public interface ProcessStepRepository extends CrudRepository<ProcessStep, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param temp
	* @param @return   
	* @return ProcessStep   
	* @throws
	*/
	@Query("select a from ProcessStep a where a.temp=?1 and a.isFirst=1")
	ProcessStep findFisrtStep(ApproveTemplate temp);
	
	@Query("select a from ProcessStep a where a.temp = ?1 order by a.id")
	List<ProcessStep> findByTemp(ApproveTemplate approveTemplate);
	
	ProcessStep getById(Integer id);
}