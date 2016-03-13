package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommBusiness;

@Repository
public interface CommBusinessRepository extends CrudRepository<CommBusiness,Integer> {

	CommBusiness getById(Integer id);
	CommBusiness getByName(String name);
	
	@Query("select a from CommBusiness as a order by a.createtime desc,a.name asc")
	Page<CommBusiness> findAll(Pageable page);
	
	@Query("select a from CommBusiness as a order by a.createtime desc,a.name asc")
	List<CommBusiness> listAll();
}
