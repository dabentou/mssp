package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommRolePermission;


/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
public interface  CommRolePermissionService {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param id
	* @param @return   
	* @return List<CommRolePermission>   
	* @throws
	*/
	List<CommRolePermission> findByCommRoleId(CommRole roleId);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @return   
	* @return int   
	* @throws
	*/
	int getCounts();

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRolePermission   
	* @return void   
	* @throws
	*/
	void save(CommRolePermission commRolePermission);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param findByCommRoleId   
	* @return void   
	* @throws
	*/
	void delete(List<CommRolePermission> findByCommRoleId);


}
