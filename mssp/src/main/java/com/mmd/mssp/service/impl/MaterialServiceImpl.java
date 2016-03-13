package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.Material;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.MaterialRepository;
import com.mmd.mssp.service.MaterialService;

@Service
public class MaterialServiceImpl implements MaterialService {
	
	@Resource
	MaterialRepository materialRepository;

	@Override
	public Material findByIdAndIsDelete(Integer id, Boolean isDelete) {
		return materialRepository.findByIdAndIsDelete(id, isDelete?1:0);
	}

	@Override
	public Material save(Material material) {
		return materialRepository.save(material);
	}

	@Override
	public Paging<Material> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<Material> paging = new Paging<Material>(page, size);
		Page<Material> result = materialRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		return paging;
	}

	@Override
	public void delete(Material material) {
		materialRepository.delete(material);
	}

	@Override
	public List<Material> listByIsDelete(boolean isDelete) {
		return materialRepository.listByIsDelete(isDelete?1:0);
	}
	
}
