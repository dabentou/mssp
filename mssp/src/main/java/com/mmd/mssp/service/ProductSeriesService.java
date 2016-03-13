package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;

public interface ProductSeriesService {
	
	Paging<ProductSeries> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public ProductSeries save(ProductSeries productSeries);
	public ProductSeries getProductSeriesById(Integer id);
	public ProductSeries getProductSeriesByName(String name);
	
	List<ProductSeries> listAll(boolean isDelete);
}
