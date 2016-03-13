package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.SampleInventory;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.SampleInventoryRepository;
import com.mmd.mssp.service.SampleInventoryService;

@Service
public class SampleInventoryImpl implements SampleInventoryService{
	
	@Resource
	SampleInventoryRepository sampleInventoryRepository;
	
	@Override
	public List<SampleInventory> listAll() {
		return sampleInventoryRepository.listAll();
	}
	
	@Override
	public Paging<SampleInventory> findAll(Integer page, Integer size) {
		Paging<SampleInventory> paging = new Paging<SampleInventory>(page, size);
		Page<SampleInventory> result = sampleInventoryRepository.findAll(paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public SampleInventory getSampleInventoryById(Integer id) {
		SampleInventory sampleInventory = sampleInventoryRepository.getSampleInventoryById(id);
		return sampleInventory;
	}
	
	@Override
	public SampleInventory getSampleInventoryByProductId(Integer pdoductId) {
		return sampleInventoryRepository.getSampleInventoryByProductId(pdoductId);
	}

	@Override
	public void delete(SampleInventory sampleInventory) {
		sampleInventoryRepository.delete(sampleInventory);
		
	}
	
	@Override
	public void save(SampleInventory sampleInventory) {
		sampleInventoryRepository.save(sampleInventory);
	}
}
