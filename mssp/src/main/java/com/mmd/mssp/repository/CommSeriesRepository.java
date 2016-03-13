package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommSeries;


@Repository
public interface CommSeriesRepository extends CrudRepository<CommSeries, Long> {

	CommSeries getById(Integer id);
	CommSeries getByName(String name);
	
	@Query("select a from CommSeries as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	Page<CommSeries> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommSeries as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	List<CommSeries> listAll(int i);
}
