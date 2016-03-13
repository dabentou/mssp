package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;

@Repository
public interface ProductPriceRepository extends CrudRepository<ProductPrice, Integer> {
	
	ProductPrice getById(Integer id);
	
	@Query("select a from ProductPrice as a where a.product = (?1) order by  a.id asc")
	Page<ProductPrice> findByProduct(Product product, Pageable page);
	
	@Query("select a.product from ProductPrice a where a.product.name=?1 and a.dateMonth = ?2 and  a.product.isDelete = 0")
	Product findByNameAndDateMonth(String name,Date dateMonth);
	
/*	@Query("select a from ProductPrice a where a.product=?1 and a.dateMonth=?2")
	ProductPrice findByProductAndDateMonth(Product product,Date dateMonth);*/
	@Query("select a from ProductPrice a where a.product=?1")
	ProductPrice findByProductAndDateMonth(Product product);
	
	@Query("select count(a) from ProductPrice a where a.dateMonth=?1")
	int getCountByDateMonth(Date dateMonth);
}