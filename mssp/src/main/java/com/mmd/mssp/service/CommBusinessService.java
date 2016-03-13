package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.vo.Paging;

public interface CommBusinessService {
	
	CommBusiness getCommBusinessById(Integer id);
	CommBusiness getCommBusinessByName(String name);
	CommBusiness save(CommBusiness commBusiness);
	void delete(CommBusiness commBusiness);
	
	Paging<CommBusiness> findAll(Integer page, Integer size);
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return List<CommBusiness>   
	* @throws
	*/
	List<CommBusiness> findBusinessList();
	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param parseInt
	* @param @return   
	* @return CommBusiness   
	* @throws
	*/
	CommBusiness findById(Integer bId);
}
