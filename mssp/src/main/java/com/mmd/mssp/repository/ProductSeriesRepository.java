package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.ProductSeries;

@Repository
public interface ProductSeriesRepository extends CrudRepository<ProductSeries, Long> {
	
	ProductSeries getById(Integer id);
	ProductSeries getByName(String name);
	
	@Query("select a from ProductSeries as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	Page<ProductSeries> findByIsDelete(int i, Pageable page);
	
	@Query("select a from ProductSeries as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	List<ProductSeries> listAll(int i);
	
}