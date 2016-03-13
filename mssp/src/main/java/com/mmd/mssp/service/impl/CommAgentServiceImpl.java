package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommAgentRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.CommProvinceRepository;
import com.mmd.mssp.service.CommAgentService;

@Service
public class CommAgentServiceImpl implements CommAgentService {

	
	@Resource
	CommAgentRepository commAgentRepository;
	@Resource
	CommProvinceRepository provinceRepository;
	@Resource
	CommCityRepository cityRepository;
	
	@Override
	public List<CommAgent> listAll() {
		return commAgentRepository.listAllAgent();
	}

	@Override
	public CommAgent getById(Integer id) {
		return commAgentRepository.getById(id);
	}

	@Override
	public Paging<CommAgent> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommAgent> paging = new Paging<CommAgent>(page, size);
		Page<CommAgent> result = commAgentRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}
	
	@Override
	public Paging<CommAgent> findIretailStore(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommAgent> paging = new Paging<CommAgent>(page, size);
		Page<CommAgent> result = commAgentRepository.findIretailStore(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public Paging<CommAgent> searchIretailStoreByNameOrId(String name,
			Integer ids, Integer page, Integer size) {
		Paging<CommAgent> paging = new Paging<CommAgent>(page, size);
		int nameFlag = 1;
		int idFlag = 1;
		int id=-1;
		if(name.equals("") || name == null){
			nameFlag = 2;
		}
		if(ids == null){
			idFlag = 2;
		}else{
			id = ids;
		}
		Page<CommAgent> result = commAgentRepository.searchIretailStoreByNameOrId(name,nameFlag,id,idFlag,paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public CommAgent save(CommAgent commAgent) {
		return commAgentRepository.save(commAgent);
	}

	@Override
	public List<CommAgent> fandAll(boolean isDelete) {
		return commAgentRepository.fandAll(isDelete?1:0);
	}

	@Override
	public List<CommAgent> findSiByAgent(Integer agentId) {
		return commAgentRepository.findSiByAgent(agentId);
	}

	@Override
	public List<CommAgent> findByIrLevelAndIsDelete(Integer irLevel,
			boolean isDelete) {
		return commAgentRepository.findByIrLevelAndIsDelete(irLevel, isDelete?1:0);
	}

	@Override
	public List<CommAgent> findStoreByAgent(Integer agentId) {
		return commAgentRepository.findStoreByAgent(agentId);
	}
	
	@Override
	public List<CommAgent> findStoreByProvince(CommProvince province) {
		return commAgentRepository.findStoreByProvince(province);
	}

	@Override
	public Paging<CommAgent> findBySearch(String irName, Integer[] provinceId,
			Integer[] cityId, Integer[] verifyStatus, Integer[] irStatus,
			Integer page, Integer size) {
		int nameFlag = 1;
		int provinceFlag = 1;
		int cityFlag = 1;
		int verifyStatusFlag = 1;
		int irStatusFlag = 1;
		if(!"".equals(irName)){
			nameFlag = 2;
		}
		List<CommProvince> provinceList = new ArrayList<CommProvince>();
		List<CommCity> cityList = new ArrayList<CommCity>();
		List<Integer> verifyStatusList = new ArrayList<Integer>();
		List<Integer> irStatusList = new ArrayList<Integer>();
		if(provinceId!=null && provinceId.length>0){
			provinceFlag = 2;
			for (Integer pId : provinceId) {
				CommProvince province = provinceRepository.getById(pId);
				provinceList.add(province);
			}
		}else{
			provinceList.add(new CommProvince());
		}
		if(cityId!=null && cityId.length>0){
			cityFlag = 2;
			for (Integer cId : cityId) {
				CommCity city = cityRepository.getById(cId);
				cityList.add(city);
			}
		}else{
			cityList.add(new CommCity());
		}
		if(verifyStatus!=null && verifyStatus.length>0){
			verifyStatusFlag = 2;
			for (Integer vs : verifyStatus) {
				verifyStatusList.add(vs);
			}
		}else{
			verifyStatusList.add(-1);
		}
		if(irStatus!=null && irStatus.length>0){
			irStatusFlag = 2;
			for (Integer s : irStatus) {
				irStatusList.add(s);
			}
		}else{
			irStatusList.add(-1);
		}
		Paging<CommAgent> paging = new Paging<CommAgent>(page, size);
		Page<CommAgent> result = commAgentRepository.findBySearch("%"+irName+"%", nameFlag, provinceList, provinceFlag, cityList, cityFlag, verifyStatusList, verifyStatusFlag, irStatusList, irStatusFlag, paging.toPage());
		paging.setResult(result);
		return paging;
	}
}
