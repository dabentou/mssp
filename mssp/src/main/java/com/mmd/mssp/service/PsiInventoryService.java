package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;


import java.util.Date;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;


/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
public interface  PsiInventoryService {


	PsiInventory findLastInventorys(String lastMonthLastDay, Product product,CommAgent agent,Integer channelType);
	
	public PsiInventory save(PsiInventory inventory);
	public void save(List<PsiInventory> inventory);
	/**
	 * 根据代理商、型号和类型获取期初库存
	 * @param product 型号
	 * @param commAgent 代理商
	 * @param channelType 类型(飞生、越海)
	 * @return
	 */
	PsiInventory findByProductAndCommAgentAndChannelType(Product product,CommAgent commAgent,Integer channelType,Date time);
	
	PsiInventory getByid(long id);
}
