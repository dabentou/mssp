package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommArea;

@Repository
public interface CommAreaRepository extends CrudRepository<CommArea, Integer> {

	CommArea getById(Integer id);
	CommArea getByAreaName(String areaName);
	
	@Query("select a from CommArea as a where a.isDelete = (?1) order by  a.createtime desc,a.areaName asc")
	Page<CommArea> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommArea as a where a.isDelete = (?1) order by  a.createtime desc,a.areaName asc")
	List<CommArea> listAll(int i);
}
