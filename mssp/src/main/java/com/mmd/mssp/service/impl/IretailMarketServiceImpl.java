package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.IretailMarket;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.IretailMarketRepository;
import com.mmd.mssp.service.IretailMarketService;

@Service
public class IretailMarketServiceImpl implements IretailMarketService {

	@Resource
	IretailMarketRepository iretailMarketRepository;

	@Override
	public Paging<IretailMarket> listAll(Integer page, Integer size) {
		Paging<IretailMarket> paging = new Paging<IretailMarket>(page, size);
		Page<IretailMarket> result = iretailMarketRepository.listAll(paging.toPage());
		paging.setResult(result);
		
		return paging;
	}
	
	@Override
	public List<IretailMarket> findAll() {
		// TODO Auto-generated method stub
		return iretailMarketRepository.findAll();
	}

	@Override
	public IretailMarket getByMarketId(Integer id) {
		return iretailMarketRepository.getById(id);
	}

	@Override
	public IretailMarket save(IretailMarket market) {
		return iretailMarketRepository.save(market);
	}

	@Override
	public void delete(IretailMarket market) {
		iretailMarketRepository.delete(market);
	}
	

}
