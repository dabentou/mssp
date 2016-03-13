package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.vo.Paging;

public interface CommAgentService {

	//CommAgent getByCommUser(CommUser commUser);
	
	List<CommAgent> listAll();//所有审核通过未冻结未删除的总代
	
	List<CommAgent> fandAll(boolean isDelete);
	
	CommAgent getById(Integer id);
	
	Paging<CommAgent> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	Paging<CommAgent> findIretailStore(boolean isDelete,Integer page, Integer size);
	
	Paging<CommAgent> searchIretailStoreByNameOrId(String name,Integer id,Integer page, Integer size);
	
	CommAgent save(CommAgent commAgent);
	
	List<CommAgent> findSiByAgent(Integer agentId);
	
	List<CommAgent> findByIrLevelAndIsDelete(Integer irLevel,boolean isDelete);
	
	List<CommAgent> findStoreByAgent(Integer agentId);
	
	List<CommAgent> findStoreByProvince(CommProvince province);
	
	Paging<CommAgent> findBySearch(String irName,Integer[] provinceId, Integer[] cityId, Integer[] verifyStatus,Integer[] irStatus,Integer page, Integer size);
}
