package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PannelRepository;
import com.mmd.mssp.service.PannelService;

@Service
public class PannelServiceImpl implements PannelService {

	@Resource
	PannelRepository pannelRepository;
	
	@Override
	public Paging<Pannel> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		
		Paging<Pannel> paging = new Paging<Pannel>(page, size);
		Page<Pannel> result = pannelRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public Pannel save(Pannel pannel) {
		return pannelRepository.save(pannel);
	}

	@Override
	public Pannel getPannelById(Integer id) {
		return pannelRepository.getById(id);
	}

	@Override
	public List<Pannel> listAll(boolean isDelete) {
		return pannelRepository.ListAll(isDelete?1:0);
	}

	@Override
	public Pannel getPannelByName(String name) {
		return pannelRepository.getByName(name);
	}

}
