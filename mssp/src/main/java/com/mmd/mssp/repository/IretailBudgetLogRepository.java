package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.IretailBudgetLog;

@Repository
public interface IretailBudgetLogRepository extends CrudRepository<IretailBudgetLog,Long>{
	
	@Query("select a from IretailBudgetLog as a where a.year = ?1 and a.quarter = ?2 and a.province = ?3 order by a.id desc")
	List<IretailBudgetLog> findByYearAndQuarterAndCommCity(Integer year,Integer quarter, CommProvince province);
}
