package com.mmd.mssp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.ApproveTemplate;

@Repository
public interface ApproveTemplateRepository extends CrudRepository<ApproveTemplate, Integer> {
	
	@Query("select a from ApproveTemplate as a order by a.id desc")
	Page<ApproveTemplate> findAll(Pageable page);
	
	@Query("select a from ApproveTemplate as a where a.type like ?1 order by a.id desc")
	Page<ApproveTemplate> findByType(String type,Pageable page);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param temp_B2B_PROJECT
	* @param @return   
	* @return ApproveTemplate   
	* @throws
	*/
	@Query("select a from ApproveTemplate as a where a.type= ?1")
	ApproveTemplate findTempByType(String tempType);
	
	@Query("select a from ApproveTemplate as a where a.tempName like ?1 order by a.id desc")
	Page<ApproveTemplate> findByTypeName(String typeName,Pageable page);
}