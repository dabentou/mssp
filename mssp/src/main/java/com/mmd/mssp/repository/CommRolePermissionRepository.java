package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommRolePermission;


@Repository
public interface CommRolePermissionRepository extends CrudRepository<CommRolePermission, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param role
	* @param @return   
	* @return List<CommRolePermission>   
	* @throws
	*/
	List<CommRolePermission> findByCommRole(CommRole role);


}
