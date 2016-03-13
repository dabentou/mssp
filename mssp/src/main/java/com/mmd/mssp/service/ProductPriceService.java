package com.mmd.mssp.service;

import java.util.Date;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.vo.Paging;

public interface ProductPriceService {
	
	Paging<ProductPrice> findByProduct(Product product,Integer page, Integer size);
	
	public ProductPrice save(ProductPrice productPrice);
	
	public ProductPrice getById(Integer id);
	
	/**
	 * 根据型号名称和日期查询型号
	 * @param name
	 * @param dateMonth
	 * @return
	 */
	Product findProductByNameAndDateMonth(String name,Date dateMonth);
	
	/**
	 * 根据型号和日期查询型号价格
	 * @param product
	 * @param dateMonth
	 * @return
	 */
	ProductPrice findByProductAndDateMonth(Product product,Date dateMonth);
	
	/**
	 * 判断当月产品的各个价格是否提交
	 * @param dateMonth
	 * @return
	 */
	boolean isSubmitCurrentMonth(Date dateMonth);
}
