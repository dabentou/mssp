package com.mmd.mssp.service.impl;

import java.util.List;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;
import com.mmd.mssp.repository.PsiInventoryRepository;
import com.mmd.mssp.service.PsiInventoryService;

/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
@Service
public class PsiInventoryServiceImp implements PsiInventoryService  {
	
	@Resource
	 PsiInventoryRepository psiInventoryRepository;
	
	@Override
	public PsiInventory findLastInventorys(String lastMonthLastDay,
			Product product, CommAgent agent, Integer channelType) {
		// TODO Auto-generated method stub
		return psiInventoryRepository.findLastInventorys(lastMonthLastDay, product, agent, channelType);
	}
	
	@Override
	public PsiInventory save(PsiInventory inventory) {
		return psiInventoryRepository.save(inventory);
	}
	
	@Override
	public void save(List<PsiInventory> inventory){
		 psiInventoryRepository.save(inventory);
	}
	

	@Override
	public PsiInventory findByProductAndCommAgentAndChannelType(Product product,CommAgent commAgent,Integer channelType,Date time) {
		return psiInventoryRepository.findByProductAndCommAgentAndChannelType(product, commAgent, channelType, time);
	}

	@Override
	public PsiInventory getByid(long id) {
		return psiInventoryRepository.findOne(id);
	}
}
