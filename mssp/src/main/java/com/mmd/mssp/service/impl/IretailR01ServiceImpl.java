package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.IretailR01;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.IretailR01Repository;
import com.mmd.mssp.service.IretailR01Service;

@Service
public class IretailR01ServiceImpl implements IretailR01Service {

	@Resource
	IretailR01Repository iretailR01Repository;
	
	
	@Override
	public IretailR01 save(IretailR01 iretailR01) {
		return iretailR01Repository.save(iretailR01);
	}


	@Override
	public Paging<IretailR01> findByAgent(CommAgent commAgent,Integer page,Integer size) {
		Paging<IretailR01> paging = new Paging<IretailR01>(page, size);
		Page<IretailR01> result = iretailR01Repository.findByAgent(commAgent,paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public IretailR01 findById(Integer id) {
		return iretailR01Repository.findOne(id);
	}
	
	

}
