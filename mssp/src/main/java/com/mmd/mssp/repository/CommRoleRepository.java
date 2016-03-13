package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommRole;

@Repository
public interface CommRoleRepository extends CrudRepository<CommRole, Integer> {

	CommRole getById(int id);
	
	@Query("select a from CommRole as a where a.isDelete = (?1) order by  a.createtime desc,a.roleName asc")
	Page<CommRole> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommRole as a where a.isDelete = (?1) order by  a.createtime desc,a.roleName asc")
	List<CommRole> listAll(int i);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param roleName
	* @param @return   
	* @return CommRole   
	* @throws
	*/
	CommRole findByRoleName(String roleName);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param i
	* @param @return   
	* @return List<CommRole>   
	* @throws
	*/
	@Query("select a from CommRole as a where a.isDelete = (?1) and a.id <> 5  order by  a.createtime desc,a.roleName asc")
	List<CommRole> listAllNoAgent(int i);
}
