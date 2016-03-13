package com.mmd.mssp.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ProductPriceRepository;
import com.mmd.mssp.service.ProductPriceService;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

	@Resource
	ProductPriceRepository productPriceRepository;
	
	@Override
	public Paging<ProductPrice> findByProduct(Product product, Integer page,
			Integer size) {
		
		Paging<ProductPrice> paging = new Paging<ProductPrice>(page, size);
		Page<ProductPrice> result = productPriceRepository.findByProduct(product, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public ProductPrice save(ProductPrice productPrice) {
		return productPriceRepository.save(productPrice);
	}


	@Override
	public Product findProductByNameAndDateMonth(String name, Date dateMonth) {
		return productPriceRepository.findByNameAndDateMonth(name, dateMonth);
	}

	@Override
	public ProductPrice getById(Integer id) {
		return productPriceRepository.findOne(id);
	}

	@Override
	public ProductPrice findByProductAndDateMonth(Product product,
			Date dateMonth) {
		return productPriceRepository.findByProductAndDateMonth(product);
	}

	@Override
	public boolean isSubmitCurrentMonth(Date dateMonth) {
		if(productPriceRepository.getCountByDateMonth(dateMonth)>0){
			return true;
		}
		return false;
	}

}
