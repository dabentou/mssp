package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;

public interface CommRoleService {

	Paging<CommRole> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public CommRole save(CommRole commRole);
	public CommRole getCommRoleById(int id);
	
	List<CommRole> listAll(boolean isDelete);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param roleName
	* @param @return   
	* @return CommRole   
	* @throws
	*/
	CommRole findByRoleName(String roleName);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param b
	* @param @return   
	* @return List<CommRole>   
	* @throws
	*/
	List<CommRole> listAllNoAgent(boolean b);
}
