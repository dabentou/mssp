package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.service.CommCityService;

@Service
public class CommCityServiceImpl implements CommCityService {

	@Resource
	CommCityRepository commCityRepository;
	
	@Override
	public Paging<CommCity> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommCity> paging = new Paging<CommCity>(page, size);
		Page<CommCity> result = commCityRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommCity save(CommCity commCity) {
		return commCityRepository.save(commCity);
	}

	@Override
	public CommCity getCommCityById(Integer id) {
		return commCityRepository.getById(id);
	}

	@Override
	public CommCity getCommCityByCityName(String cityName) {
		return commCityRepository.getByCityName(cityName);
	}

	@Override
	public List<CommCity> listAllCity(boolean isDelete) {
		return commCityRepository.queryAllCity(isDelete?1:0);
	}

}
