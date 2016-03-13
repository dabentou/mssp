package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommRoleRepository;
import com.mmd.mssp.repository.CommUserRepository;
import com.mmd.mssp.service.CommUserService;

@Service
public class CommUserServiceImpl implements CommUserService {

	@Resource
	CommUserRepository commUserRepository;
	@Resource
	CommRoleRepository roleRepository;
	
	@Override
	public Paging<CommUser> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommUser> paging = new Paging<CommUser>(page, size);
		Page<CommUser> result = commUserRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommUser save(CommUser commUser) {
		return commUserRepository.save(commUser);
	}

	@Override
	public CommUser getCommUserById(Integer id) {
		return commUserRepository.getById(id);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommUserService#findByLoginNameAndPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public CommUser findByLoginNameAndPassword(String loginName, String pwdMd5) {
		return commUserRepository.findByLoginNameAndPasswordAndIsDelete(loginName,pwdMd5,CommUser.NOT_DELETE);
	}

	@Override
	public CommUser findByCommAgent(CommAgent commAgent) {
		return commUserRepository.getByCommAgent(commAgent);
	}

	@Override
	public void delete(CommUser commUser) {
		commUserRepository.delete(commUser);
	}

	@Override
	public List<CommUser> listAll(boolean isDelete) {
		return commUserRepository.listAll(isDelete?1:0);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommUserService#findByLoginName(java.lang.String)
	 */
	@Override
	public CommUser findByLoginName(String username) {
		return commUserRepository.findByLoginName(username);
	}

	@Override
	public Paging<CommUser> findBySearch(String loginName, Integer[] roleId,
			Integer page, Integer size) {
		int loginNameFlag = 1;
		int roleFlag = 1;
		List<CommRole> roleList = new ArrayList<CommRole>();
		if(loginName != null && !"".equals(loginName)){
			loginNameFlag = 2;
		}
		if(roleId!=null){
			roleFlag = 2;
			for(int i=0; i<roleId.length; i++){
				roleList.add(roleRepository.getById(roleId[i]));
			}
		}else{
			roleList.add(new CommRole());
		}
		Paging<CommUser> paging = new Paging<CommUser>(page, size);
		Page<CommUser> result = commUserRepository.findBySearch("%"+loginName+"%", loginNameFlag, roleList, roleFlag, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}
}
