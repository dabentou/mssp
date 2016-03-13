package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.Product;

@Repository
public interface CommCityRepository extends CrudRepository<CommCity, Long> {

	CommCity getById(Integer id);
	
	@Query("select a from CommCity as a where a.isDelete = (?1) order by  a.createtime desc,a.cityName asc")
	Page<CommCity> findByIsDelete(int i, Pageable page);

	/**
	* @author: sheng.tian
	* @Description: 查询所有的城市名称
	* @param @param i
	* @param @return   
	* @return List<String>   
	* @throws
	*/
	@Query("select a.cityName from CommCity as a where a.isDelete = (?1)")
	List<String> queryAllCityName(int isDelete);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param cityName
	* @param @param nOT_DELETE
	* @param @return   
	* @return CommCity   
	* @throws
	*/
	@Query("select a from CommCity as a where a.cityName=?1 and  a.isDelete = (?2)")
	CommCity findCityByName(String cityName, int nOT_DELETE);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param isDelele
	* @param @return   
	* @return List<Product>   
	* @throws
	*/
	@Query("select a from CommCity as a where a.isDelete = (?1)")
	List<Product> findAllCity(int isDelele);
	
	CommCity getByCityName(String name);
	
	/**查找所有城市**/
	@Query("select a from CommCity as a where a.isDelete = (?1)")
	List<CommCity> queryAllCity(int isDelete);
}
