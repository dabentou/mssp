package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;

@Repository
public interface CommUserRepository extends CrudRepository<CommUser, Long> {

	CommUser getById(Integer id);
	
	@Query("select a from CommUser as a where a.isDelete = (?1) order by  a.createtime desc,a.loginName asc")
	Page<CommUser> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommUser as a where a.isDelete = 0 and (a.loginName like ?1 or 1=?2) and (a.commRole in ?3 or 1=?4) order by  a.createtime desc,a.loginName asc")
	Page<CommUser> findBySearch(String loginName,int loginNameFlag,List<CommRole> roleList, int roleFlag, Pageable page);
	
	@Query("select a from CommUser as a where a.isDelete = (?1) order by  a.createtime desc,a.loginName asc")
	List<CommUser> listAll(int i);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param loginName
	* @param @param pwdMd5
	* @param @return   
	* @return CommUser   
	* @throws
	*/
	CommUser findByLoginNameAndPasswordAndIsDelete(String loginName, String pwdMd5,Integer isDelete);

	CommUser getByCommAgent(CommAgent commAgent);

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
