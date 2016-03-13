package com.mmd.mssp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.IretailPropagandaType;

@Repository
public interface IretailPropagandaTypeRepository extends CrudRepository<IretailPropagandaType, Integer> {
	
	@Query("select a from IretailPropagandaType as a where a.id = ?1 and a.isDelete = ?2")
	public IretailPropagandaType findByIdAndIsDelete(Integer id,Integer isDelete);
	
	@Query("select a from IretailPropagandaType as a where a.isDelete = ?1 order by  a.id desc")
	Page<IretailPropagandaType> findByIsDelete(Integer i, Pageable page);
}
