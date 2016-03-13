package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.SampleInventory;
import com.mmd.mssp.domain.vo.Paging;

public interface SampleInventoryService {

	List<SampleInventory> listAll();
	
	Paging<SampleInventory> findAll(Integer page, Integer size);
	
	SampleInventory getSampleInventoryById(Integer id);
	
	SampleInventory getSampleInventoryByProductId(Integer  pdoductId);
	
	void delete(SampleInventory sampleInventory);
	
	void save(SampleInventory sampleInventory);
}
