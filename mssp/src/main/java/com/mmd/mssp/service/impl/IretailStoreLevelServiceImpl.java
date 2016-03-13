package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.IretailStoreLevel;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.IretailStoreLevelRepository;
import com.mmd.mssp.service.IretailStoreLevelService;

@Service
public class IretailStoreLevelServiceImpl implements IretailStoreLevelService {
	
	@Resource
	IretailStoreLevelRepository iretailStoreLevelRepository;

	@Override
	public IretailStoreLevel findByIdAndIsDelete(Integer id, Boolean isDelete) {
		return iretailStoreLevelRepository.findByIdAndIsDelete(id, isDelete?1:0);
	}

	@Override
	public IretailStoreLevel save(IretailStoreLevel iretailStoreLevel) {
		return iretailStoreLevelRepository.save(iretailStoreLevel);
	}

	@Override
	public Paging<IretailStoreLevel> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<IretailStoreLevel> paging = new Paging<IretailStoreLevel>(page, size);
		Page<IretailStoreLevel> result = iretailStoreLevelRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public List<IretailStoreLevel> listByIsDelete(Boolean isDelete) {
		return iretailStoreLevelRepository.listByIsDelete(isDelete?1:0);
	}
}
