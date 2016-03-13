package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.B2BApproveLog;


@Repository
public interface B2BApproveLogRepository extends CrudRepository<B2BApproveLog, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @return   
	* @return List<b2bApproveLog>   
	* @throws
	*/
	List<B2BApproveLog> findByProject(B2BProject project);

	

}
