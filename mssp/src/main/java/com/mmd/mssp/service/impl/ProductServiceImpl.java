package com.mmd.mssp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Resource
	ProductRepository productRepository;
	
	@Override
	public Paging<Product> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		
		Paging<Product> paging = new Paging<Product>(page, size);
		Page<Product> result = productRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}

	@Override
	public Product getProductById(Integer id) {
		return productRepository.getById(id);
	}

	@Override
	public List<Product> listAll(boolean isDelete) {
		return productRepository.findAllProduct(isDelete?1:0);
	}

	@Override
	public List<String> listAllProductName(boolean isDelete) {
		return productRepository.queryAllProductName(isDelete?1:0);
	}

	@Override
	public Paging<Product> findBySearch(String name, Integer seriesId,
			Integer productSeriesId, Integer pannelId, Integer page,
			int size) {
		
		int nameFlag = 1;
		int commSeriesFlag = 1;
		int productSeriesFlag = 1;
		int pannelFlag = 1;
		
		if(!StringUtils.isBlank(name)){
			nameFlag = 2;
		}
		if(seriesId != null){
			commSeriesFlag = 2;
		}
		if(productSeriesId != null){
			productSeriesFlag = 2;
		}
		if(pannelId != null){
			pannelFlag = 2;
		}
		
		Paging<Product> paging = new Paging<Product>(page, size);
		Page<Product> result = productRepository.findBySearch("%"+name+"%", nameFlag, seriesId, commSeriesFlag, productSeriesId, productSeriesFlag, pannelId, pannelFlag, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	public List<Product> listBySellType(Integer sellType,boolean isDelete) {
		return productRepository.listBySellType(sellType,isDelete?1:0);
	}

	public String getProductNameArr(List<Product> products) {
		StringBuilder sb = new StringBuilder();
		if(null != products && !products.isEmpty()){
			for (Product product : products) {
				sb.append("\""+product.getName()+"\",");
			}
			return sb.substring(0, sb.length()-1);
		}
		return "";
	}

	@Override
	public Product findByName(String name) {
		return productRepository.findByName(name);
	}
}
