package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.vo.Paging;

public interface CommUserService {

	Paging<CommUser> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	Paging<CommUser> findBySearch(String loginName,Integer[] roleId,Integer page, Integer size);
	
	public CommUser save(CommUser commUser);
	public CommUser getCommUserById(Integer id);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param loginName
	* @param @param pwdMd5
	* @param @return   
	* @return CommUser   
	* @throws
	*/
	CommUser findByLoginNameAndPassword(String loginName, String pwdMd5);
	
	CommUser findByCommAgent(CommAgent commAgent);
	
	void delete(CommUser commUser);
	
	List<CommUser> listAll(boolean isDelete);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param username
	* @param @return   
	* @return CommUser   
	* @throws
	*/
	CommUser findByLoginName(String username);
}
