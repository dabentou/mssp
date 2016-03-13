package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.IretailStoreLevel;

@Repository
public interface IretailStoreLevelRepository extends CrudRepository<IretailStoreLevel, Integer> {
	
	@Query("select a from IretailStoreLevel as a where a.id = ?1 and a.isDelete = ?2")
	public IretailStoreLevel findByIdAndIsDelete(Integer id,Integer isDelete);
	
	@Query("select a from IretailStoreLevel as a where a.isDelete = ?1 order by  a.id desc")
	Page<IretailStoreLevel> findByIsDelete(Integer i, Pageable page);
	
	@Query("select a from IretailStoreLevel as a where a.isDelete = ?1 order by  a.targetVolume desc")
	List<IretailStoreLevel> listByIsDelete(Integer isDelete);
}
