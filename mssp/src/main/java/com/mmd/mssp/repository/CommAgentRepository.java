package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;

@Repository
public interface CommAgentRepository extends CrudRepository<CommAgent, Integer> {

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param bean
	* @param @param iR_LEVEL_AGENT
	* @param @return   
	* @return CommAgent   
	* @throws
	*/
	@Query("select a from CommAgent a where a.commCity=?1 and  a.irLevel=?2 and a.irStatus=0 and a.verifyStatus=1")
	CommAgent findAgentByCityAndIRlevel(CommCity bean, int iR_LEVEL_AGENT);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param currentUser
	* @param @return   
	* @return CommAgent   
	* @throws
	*/
	//CommAgent findByCommUser(CommUser currentUser);
	
	//CommAgent getByCommUser(CommUser commUser);
	
	@Query("select a from CommAgent a where a.isDelete =0 and a.irLevel=5 and a.irStatus=0 and a.verifyStatus=1")
	List<CommAgent> listAllAgent();//5是代理商6是零售店获取所有的总代
	
	@Query("select a from CommAgent as a where a.isDelete = ?1 and a.irLevel=5 order by  a.createtime desc")
	List<CommAgent> fandAll(int isDelete);//5是代理商6是零售店
	
	CommAgent getById(Integer id);

	@Query("select a from CommAgent as a where a.isDelete = (?1) and (a.irLevel=5 or a.irLevel=7) order by  a.createtime desc")
	Page<CommAgent> findByIsDelete(int i, Pageable page);
	
	@Query("select a from CommAgent as a where a.isDelete = 0 and (a.irLevel=5 or a.irLevel=7) and (a.irName like ?1 or 1=?2) and (a.province in ?3 or 1=?4) and (a.commCity in ?5 or 1=?6) and (a.verifyStatus in ?7 or 1=?8) and (a.irStatus in ?9 or 1=?10) order by  a.createtime desc")
	Page<CommAgent> findBySearch(String irName,int nameFlag,List<CommProvince> provinceList,int provinceFlag, List<CommCity> cityList, int cityFlag,List<Integer> verifyStatusList,int verifyStatusFlag,List<Integer> irStatusList,int irStatusFlag, Pageable page);
	
	@Query("select a from CommAgent as a where a.isDelete = (?1) and a.irLevel=6 order by  a.createtime desc")
	Page<CommAgent> findIretailStore(int i, Pageable page);
	
	@Query("select a from CommAgent as a where a.isDelete = 0 and a.irLevel=6 and (a.irName = ?1 or ?2 = 2) and (?4 = 2 or a.id = ?3) order by  a.createtime desc")
	Page<CommAgent> searchIretailStoreByNameOrId(String name,int nameFlag,int id,int idFlag, Pageable page);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param agent
	* @param @return   
	* @return List<CommAgent>   
	* @throws
	*/
	@Query("select a from CommAgent a where a.pId=?1 and a.irLevel=7 and a.irStatus=0 and a.isDelete=0 and a.verifyStatus=1")
	List<CommAgent> findSiByAgent(Integer agentId);
	
	@Query("select a from CommAgent a where a.irLevel=?1 and a.isDelete=?2 order by a.id desc")
	List<CommAgent> findByIrLevelAndIsDelete(Integer irLevel,Integer isDelete);
	
	@Query("select a from CommAgent a where a.irLevel=5 and a.isDelete=0 and a.province=?1 order by a.id desc")
	CommAgent findAgentByProvince(CommProvince commProvince);
	
	@Query("select a from CommAgent a where a.irLevel=6 and a.isDelete=0 and a.pId=?1 order by a.id desc")
	List<CommAgent> findStoreByAgent(Integer agentId);
	
	@Query("select a from CommAgent a where a.irLevel=6 and a.isDelete=0 and a.province=?1 order by a.id desc")
	List<CommAgent> findStoreByProvince(CommProvince province);
}
