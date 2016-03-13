package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;

public interface CommProvinceRepository extends CrudRepository<CommProvince, Long> {

	CommProvince getById(Integer id);
	
	@Query("select a from CommProvince as a where a.province=?1 ")
	CommProvince getByProvinceName(String provinceName);
	
	@Query("select a from CommProvince as a where a.isDelete = (?1) order by  a.createTime desc")
	Page<CommProvince> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommProvince as a where a.isDelete = 0 order by  a.createTime desc")
	List<CommProvince> listAll();
}
