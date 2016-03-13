package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ProductSeriesRepository;
import com.mmd.mssp.service.ProductSeriesService;

@Service
public class ProductSeriesServiceImpl implements ProductSeriesService {

	@Resource
	ProductSeriesRepository productSeriesRepository;

	@Override
	public Paging<ProductSeries> findByIsDelete(boolean isDelete,Integer page, Integer size) {
		Paging<ProductSeries> paging = new Paging<ProductSeries>(page, size);
		Page<ProductSeries> result = productSeriesRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public ProductSeries save(ProductSeries productSeries) {
		return productSeriesRepository.save(productSeries);
	}

	@Override
	public ProductSeries getProductSeriesById(Integer id) {
		return productSeriesRepository.getById(id);
	}

	@Override
	public List<ProductSeries> listAll(boolean isDelete) {
		return productSeriesRepository.listAll(isDelete?1:0);
	}

	@Override
	public ProductSeries getProductSeriesByName(String name) {
		return productSeriesRepository.getByName(name);
	}

}
