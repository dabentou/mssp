package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.vo.Paging;

public interface CommCityService {
	
	Paging<CommCity> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public CommCity save(CommCity commCity);
	public CommCity getCommCityById(Integer id);
	public CommCity getCommCityByCityName(String cityName);
	
	List<CommCity> listAllCity(boolean isDelete);
}
