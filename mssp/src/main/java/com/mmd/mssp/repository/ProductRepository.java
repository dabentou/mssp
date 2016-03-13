package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
	
	Product getById(Integer id);
	
	@Query("select a from Product as a where a.isDelete = (?1) order by  a.createtime desc,a.name asc")
	Page<Product> findByIsDelete(int i, Pageable page);

	/**
	* @author: sheng.tian
	* @Description: 查询所有型号名称
	* @param @return   
	* @return List<Product>   
	* @throws
	*/
	@Query("select a.name from Product  a where a.isDelete = (?1)")
	List<String> queryAllProductName(int isDelete);

	/**
	* @author: sheng.tian
	* @Description: 通过型号名称查询型号
	* @param @param material
	* @param @return   
	* @return Product   
	* @throws
	*/
	@Query("select a from Product  a where a.name=?1 and  a.isDelete = ?2")
	Product findProductByName(String name,int isDelete);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param nOT_DELETE
	* @param @return   
	* @return List<Product>   
	* @throws
	*/
	@Query("select a from Product  a where a.isDelete = ?1")
	List<Product> findAllProduct(int nOT_DELETE);
	
	@Query("select a from Product  a where a.isDelete = 0 and a.sellType=2")
	List<Product> findSampleProduct();
	
	/**后台查询产品型号**/
	@Query("select a from Product as a where a.isDelete = 0 and (a.name like ?1 or 1=?2) and (a.commSeries.id = ?3 or 1=?4) and (a.productSeries.id = ?5 or 1=?6) and (a.pannel.id = ?7 or 1=?8) order by  a.createtime desc,a.name asc")
	Page<Product> findBySearch(String name,int nameFlag, Integer commSeriesId , int commSeriesFlag, Integer productSeriesId , int productSeriesFlag,Integer pannelId , int pannelFlag, Pageable page);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return Product   
	* @throws
	*/
	Product findById(Integer pid);
	
	@Query("select a from Product as a where (a.id in ?1 or 1=?2) and a.isDelete = 0 order by  a.createtime desc,a.name asc")
	Page<Product> findByIdList(List<Integer> idList,int productFlag,Pageable page);
	
	@Query("select a from Product as a where (a.id in ?1 or 1=?2) and a.isDelete = 0 and a.channelType in ?3 order by  a.createtime desc,a.name asc")
	Page<Product> findByIdAndChannelTypeList(List<Integer> idList,int productFlag,Integer[] channelType,Pageable page);
	
	@Query("select a from Product as a where (a.id in ?1 or 1=?2) and a.isDelete = 0 order by  a.createtime desc,a.name asc")
	List<Product> findByIdListToExport(List<Integer> idList,int productFlag);
	
	
	@Query("select a from Product a where a.sellType = ?1 and a.isDelete = ?2")
	List<Product> listBySellType(Integer sellType,Integer isDetete);
	
	@Query("select a from Product  a where a.name=?1 and  a.isDelete = 0")
	Product findByName(String name);
	
	@Query("select a from Product  a where a.isDelete = 0 and a.channelType in ?1")
	List<Product> findProductByChannelType(Integer[] channelType);
}
