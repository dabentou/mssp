package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.IretailMarket;

@Repository
public interface IretailMarketRepository extends CrudRepository<IretailMarket, Integer> {

	IretailMarket getById(Integer id);
	
	@Query("select a from IretailMarket as a order by a.id desc,a.marketName asc")
	Page<IretailMarket> listAll(Pageable page);
	
	@Query("select a from IretailMarket as a order by a.id desc,a.marketName asc")
	List<IretailMarket> findAll();
}
