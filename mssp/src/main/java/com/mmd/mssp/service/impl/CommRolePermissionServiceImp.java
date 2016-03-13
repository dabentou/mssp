package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommRolePermission;
import com.mmd.mssp.repository.CommPermissionRepository;
import com.mmd.mssp.repository.CommRolePermissionRepository;
import com.mmd.mssp.service.CommRolePermissionService;

/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
@Service
public class CommRolePermissionServiceImp implements CommRolePermissionService  {
	
	@Resource
	CommRolePermissionRepository commRolePermissionRepository;
	
	@Resource
	CommPermissionRepository commPermissionRepository;

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRolePermissionService#findByCommRoleId(java.lang.Integer)
	 */
	@Override
	public List<CommRolePermission> findByCommRoleId(CommRole role) {
		return commRolePermissionRepository.findByCommRole(role);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRolePermissionService#getCounts()
	 */
	@Override
	public int getCounts() {
		return commPermissionRepository.getCounts();
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRolePermissionService#save(com.mmd.mssp.domain.CommRolePermission)
	 */
	@Override
	public void save(CommRolePermission commRolePermission) {
		// TODO Auto-generated method stub
		commRolePermissionRepository.save(commRolePermission);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRolePermissionService#delete(java.util.List)
	 */
	@Override
	public void delete(List<CommRolePermission> list) {
		// TODO Auto-generated method stub
		commRolePermissionRepository.delete(list);
	}
	
}
