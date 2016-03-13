package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.Pannel;

@Repository
public interface PannelRepository extends CrudRepository<Pannel, Long> {
	
	Pannel getById(Integer id);
	
	Pannel getByName(String Name);
	
	@Query("select a from Pannel as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	Page<Pannel> findByIsDelete(int i, Pageable page);
	
	@Query("select a from Pannel as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	List<Pannel> ListAll(int i);
}
