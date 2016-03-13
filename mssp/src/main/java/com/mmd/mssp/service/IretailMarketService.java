package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.IretailMarket;
import com.mmd.mssp.domain.vo.Paging;

public interface IretailMarketService {

	Paging<IretailMarket> listAll(Integer page, Integer size);
	
	List<IretailMarket> findAll();
	
	IretailMarket getByMarketId(Integer id);
	
	IretailMarket save(IretailMarket market);
	
	void delete(IretailMarket market);
}
