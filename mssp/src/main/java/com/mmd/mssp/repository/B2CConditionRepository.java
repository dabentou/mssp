package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.B2CCondition;
import com.mmd.mssp.domain.CommArea;

@Repository
public interface B2CConditionRepository extends CrudRepository<B2CCondition,Integer> {

	B2CCondition getById(Integer id);
	
	@Query("select a from B2CCondition as a where a.type = ?1")
	List<B2CCondition> findByType(Integer type);
	
	@Query("select a from B2CCondition as a order by a.createtime desc")
	Page<B2CCondition> findAll(Pageable page);
}
