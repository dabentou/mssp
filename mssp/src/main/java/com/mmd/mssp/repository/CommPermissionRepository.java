package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommPermission;


@Repository
public interface CommPermissionRepository extends CrudRepository<CommPermission, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return int   
	* @throws
	*/
	@Query("select count(a) from CommPermission as a")
	int getCounts();

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param list
	* @param @return   
	* @return List<CommPermission>   
	* @throws
	*/
	@Query("select a from CommPermission as a where a.id in (?1)")
	List<CommPermission> findByIds(List<Integer> list);

}
