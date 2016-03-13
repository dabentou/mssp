package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.SampleInOutLog;

@Repository
public interface SampleInOutLogRepository extends CrudRepository<SampleInOutLog,Long> {

	@Query("select a from SampleInOutLog  as a where a.b2bProject.id=?1")
	List<SampleInOutLog> findByProjectId(Integer id);
}
