package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.DescSetting;

@Repository
public interface DescSettingRepository extends CrudRepository<DescSetting, Integer>{

	@Query("select a from DescSetting as a order by a.id desc")
	List<DescSetting> listAll();
	
	DescSetting findByProvince(CommProvince province);
}
