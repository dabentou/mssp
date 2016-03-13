package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.vo.Paging;

public interface ProductService {
	
	Paging<Product> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public Product save(Product product);
	public Product getProductById(Integer id);
	
	List<Product> listAll(boolean isDelete);
	List<String> listAllProductName(boolean isDelete);
	
	/**
	 * 活动型号名称的字符串数组
	 * @return
	 */
	public String getProductNameArr(List<Product> products);
	
	Paging<Product> findBySearch(String name,Integer seriesId,Integer productSeriesId,Integer pannelId,Integer page, int size);
	
	/**
	 * 根据销售类型列出型号
	 * @param sellType
	 * @return
	 */
	List<Product> listBySellType(Integer sellType,boolean isDelete);
	
	/**
	 * 根据型号名称查型号
	 * @param name
	 * @return
	 */
	public Product findByName(String name);
}
