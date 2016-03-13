package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2BCasualDetail;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.vo.B2BCasualDetailDomain;
import com.mmd.mssp.repository.B2BCasualDetailRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.SpecialApplyProductRepository;
import com.mmd.mssp.service.B2BCasualDetailService;

@Service
public class B2BCasualDetailServiceImpl implements B2BCasualDetailService {

	@Resource
	B2BCasualDetailRepository b2BCasualDetailRepository;
	
	@Resource
	ProductRepository productRepository;
	
	@Resource
	SpecialApplyProductRepository specialApplyProductRepository;
	
	@Override
	public void save(List<B2BCasualDetail> list) {
		b2BCasualDetailRepository.save(list);
	}
	
	@Override
	public List<Map<String, Map<String, String>>> getCasualDetail(
			B2BProject project) {
		int projectId = project.getId();
		int[] productId = b2BCasualDetailRepository.findProductByProject(projectId);
		List<Map<String, Map<String, String>>> lists = new ArrayList<Map<String, Map<String, String>>>();
		for(int p:productId){
			Map<String, Map<String, String>> map = new TreeMap<String, Map<String, String>>();//一个型号一个map，key不可以重复的
			
			List<B2BCasualDetail> list = b2BCasualDetailRepository.findValByProjectAndProduct(projectId,p);
			Map<String, String> maps = new TreeMap<String,String>();//同一个型号，要有十二个月份的数据
			for(int i=0;i<list.size();i++){
				maps.put(Integer.toString(list.get(i).getMonth()), Integer.toString(list.get(i).getVolumn()));
			}
			
			map.put(productRepository.getById(p).getName() , maps);
			lists.add(map);
		}
		return lists;
	}
	
	@Override
	public List<B2BCasualDetailDomain> getCasualDetails(B2BProject project) {
		List<B2BCasualDetailDomain> list = new ArrayList<B2BCasualDetailDomain>();
		int projectId = project.getId();
		int[] productId = b2BCasualDetailRepository.findProductByProject(projectId);
		for(int p:productId){
			B2BCasualDetailDomain domain = new B2BCasualDetailDomain();
			domain.setName(productRepository.getById(p).getName());
			domain.setTotal(specialApplyProductRepository.findByB2bProjectIdAndProductId(projectId, p).getNumber());
			List<B2BCasualDetail> lists = b2BCasualDetailRepository.findValByProjectAndProduct(projectId,p);
			domain.setOne(lists.get(0).getVolumn());
			domain.setTwo(lists.get(1).getVolumn());
			domain.setThree(lists.get(2).getVolumn());
			domain.setFour(lists.get(3).getVolumn());
			domain.setFive(lists.get(4).getVolumn());
			domain.setSix(lists.get(5).getVolumn());
			domain.setSeven(lists.get(6).getVolumn());
			domain.setEight(lists.get(7).getVolumn());
			domain.setNine(lists.get(8).getVolumn());
			domain.setTen(lists.get(9).getVolumn());
			domain.setEleven(lists.get(10).getVolumn());
			domain.setTwelve(lists.get(11).getVolumn());
			list.add(domain);
		}
		return list;
	}
}
