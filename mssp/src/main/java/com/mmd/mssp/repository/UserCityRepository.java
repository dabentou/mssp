package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.UserCity;

@Repository
public interface UserCityRepository extends CrudRepository<UserCity, Integer> {
	List<UserCity> getUserCityByUser(CommUser user);
}
