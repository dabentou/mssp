package com.mmd.mssp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.Vacation;

@Repository
public interface VacationRepository extends CrudRepository<Vacation, Long>{

	@Query("select a from Vacation a where a.dates = ?1 and a.model=1")
	Vacation findVacationById(String dates);//是节假日的，要放假的
	
	@Query("select a from Vacation a where a.dates = ?1 and a.model=2")
	Vacation findWorkdayById(String dates);//是周末，但是调休需要上班的
}
