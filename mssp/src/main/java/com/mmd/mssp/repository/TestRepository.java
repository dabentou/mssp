package com.mmd.mssp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommArea;


@Repository
public interface TestRepository extends CrudRepository<CommArea, Integer> {

	
	@Query("select a from CommArea a where a.id=?1")
	CommArea findTestAreaById(int i);


}
