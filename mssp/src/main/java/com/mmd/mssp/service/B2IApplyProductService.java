package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;

public interface B2IApplyProductService {

	public B2IApplyProduct save(B2IApplyProduct applyProduct);
	
	List<B2IApplyProduct> findSAPListByProject(B2IProject project);
	
	List<B2IApplyProduct> listToExport(List<CommAgent> agentList,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,String pCode,String pName);
	
	List<Map> getMapBySearch(List<B2IApplyProduct> list);
	
	public List<B2IApplyProduct> findByProductAndAgent(Product product,CommAgent agent);
	
	public B2IApplyProduct findById(Integer id);
}
