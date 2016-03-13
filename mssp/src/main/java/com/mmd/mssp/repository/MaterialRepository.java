package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.Material;

@Repository
public interface MaterialRepository extends CrudRepository<Material, Integer> {
	
	@Query("select a from Material as a where a.id = ?1 and a.isDelete = ?2")
	public Material findByIdAndIsDelete(Integer id,Integer isDelete);
	
	@Query("select a from Material as a where a.isDelete = ?1 order by  a.id desc")
	Page<Material> findByIsDelete(Integer i, Pageable page);
	
	@Query("select a from Material as a where a.isDelete = ?1 order by  a.id desc")
	List<Material> listByIsDelete(Integer isDelete);
}
