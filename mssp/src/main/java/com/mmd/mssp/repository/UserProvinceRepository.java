package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.UserProvince;

@Repository
public interface UserProvinceRepository extends CrudRepository<UserProvince, Integer> {

	List<UserProvince> getUserProvinceByUser(CommUser user);
}
