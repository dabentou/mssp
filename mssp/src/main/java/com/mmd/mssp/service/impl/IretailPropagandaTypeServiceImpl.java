package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.IretailPropagandaType;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.IretailPropagandaTypeRepository;
import com.mmd.mssp.service.IretailPropagandaTypeService;

@Service
public class IretailPropagandaTypeServiceImpl implements IretailPropagandaTypeService {
	
	@Resource
	IretailPropagandaTypeRepository iretailPropagandaTypeRepository;

	@Override
	public IretailPropagandaType findByIdAndIsDelete(Integer id, Boolean isDelete) {
		return iretailPropagandaTypeRepository.findByIdAndIsDelete(id, isDelete?1:0);
	}

	@Override
	public IretailPropagandaType save(IretailPropagandaType itPropagandaType) {
		return iretailPropagandaTypeRepository.save(itPropagandaType);
	}

	@Override
	public Paging<IretailPropagandaType> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<IretailPropagandaType> paging = new Paging<IretailPropagandaType>(page, size);
		Page<IretailPropagandaType> result = iretailPropagandaTypeRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public void delete(IretailPropagandaType itPropagandaType) {
		iretailPropagandaTypeRepository.delete(itPropagandaType);
	}
	
}
