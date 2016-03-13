package com.mmd.mssp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.IretailR01;

@Repository
public interface IretailR01Repository extends CrudRepository<IretailR01, Integer>{

	@Query("select a from IretailR01 as a where a.retailProject.store = ?1 order by  a.id desc")
	Page<IretailR01> findByAgent(CommAgent commAgent,Pageable page);
}
