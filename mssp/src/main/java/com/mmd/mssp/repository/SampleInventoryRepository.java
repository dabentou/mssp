package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.SampleInventory;

@Repository
public interface SampleInventoryRepository extends CrudRepository<SampleInventory,Long>{

	SampleInventory getSampleInventoryById(Integer id);
	
	@Query("select a from SampleInventory  as a order by a.id")
	List<SampleInventory> listAll();
	
	@Query("select a from SampleInventory  as a where a.product.id=?1")
	SampleInventory getSampleInventoryByProductId(Integer productId);
	
	@Query("select a from SampleInventory as a order by a.id")
	Page<SampleInventory> findAll(Pageable page);
	
	
}
