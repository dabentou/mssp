package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommRoleRepository;
import com.mmd.mssp.service.CommRoleService;

@Service
public class CommRoleServiceImpl implements CommRoleService {

	@Resource
	CommRoleRepository commRoleRepository;
	
	@Override
	public Paging<CommRole> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommRole> paging = new Paging<CommRole>(page, size);
		Page<CommRole> result = commRoleRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommRole save(CommRole commRole) {
		return commRoleRepository.save(commRole);
	}

	@Override
	public CommRole getCommRoleById(int id) {
		return commRoleRepository.getById(id);
	}

	@Override
	public List<CommRole> listAll(boolean isDelete) {
		return commRoleRepository.listAll(isDelete?1:0);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRoleService#findByRoleName(java.lang.String)
	 */
	@Override
	public CommRole findByRoleName(String roleName) {
		return commRoleRepository.findByRoleName(roleName);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommRoleService#listAllNoAgent(boolean)
	 */
	@Override
	public List<CommRole> listAllNoAgent(boolean isDelete) {
		return commRoleRepository.listAllNoAgent(isDelete?1:0);
	}
}
