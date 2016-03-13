package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommAreaRepository;
import com.mmd.mssp.service.CommAreaService;

@Service
public class CommAreaServiceImpl implements CommAreaService {

	@Resource
	CommAreaRepository commAreaRepository;
	
	@Override
	public Paging<CommArea> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {

		Paging<CommArea> paging = new Paging<CommArea>(page, size);
		Page<CommArea> result = commAreaRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommArea save(CommArea area) {
		return commAreaRepository.save(area);
	}

	@Override
	public CommArea getCommAreaById(Integer id) {
		return commAreaRepository.getById(id);
	}

	@Override
	public List<CommArea> listAll(boolean isDelete) {
		return commAreaRepository.listAll(isDelete?1:0);
	}

	@Override
	public CommArea getCommAreaByAreaName(String areaName) {
		return commAreaRepository.getByAreaName(areaName);
	}

}
