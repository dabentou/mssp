package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.IretailApproveLog;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.ProcessStep;


@Repository
public interface IretailApproveLogRepository extends CrudRepository<IretailApproveLog, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @return   
	* @return List<b2bApproveLog>   
	* @throws
	*/
	List<IretailApproveLog> findByProject(IretailProject project);
	
	@Query("select a from IretailApproveLog as a where a.project = ?1 and a.step = ?2")
	IretailApproveLog findByProjectAndProcessStep(IretailProject project,ProcessStep step);

	

}
