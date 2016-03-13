package com.mmd.mssp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2IApproveLog;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommCustomer;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.repository.B2IApplyProductRepository;
import com.mmd.mssp.repository.B2IApproveLogRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.service.B2IApplyProductService;

@Service
public class B2IApplyProductServiceImpl implements B2IApplyProductService {

	@Resource
	B2IApplyProductRepository b2IApplyProductRepository;
	@Resource
	ProcessStepRepository processStepRepository;
	@Resource
	CommCityRepository cityRepository;
	@Resource
	B2IApproveLogRepository b2IApproveLogRepository;
	
	@Override
	public B2IApplyProduct save(B2IApplyProduct applyProduct) {
		return b2IApplyProductRepository.save(applyProduct);
	}

	@Override
	public List<B2IApplyProduct> findSAPListByProject(B2IProject project) {
		return b2IApplyProductRepository.findByB2IProject(project);
	}

	@Override
	public List<B2IApplyProduct> listToExport(List<CommAgent> agentList,
			Date applyTimeStart, Date applyTimeEnd, Date approveTimeStart,
			Date approveTimeEnd, Integer[] processStepId, Integer[] cityId,
			String pCode, String pName) {
		int processStepFlag = 1;
		int cityFlag = 1;
		int pCodeFlag = 1;
		int pNameFlag = 1;
		
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommCity> cityList = new ArrayList<CommCity>();
		
		if(processStepId!=null){
			processStepFlag = 2;
			for(int i=0; i<processStepId.length; i++){
				processStepList.add(processStepRepository.getById(processStepId[i]));
			}
		}else{
			processStepList.add(new ProcessStep());
		}
		if(cityId!=null){
			cityFlag = 2;
			for(int i=0; i<cityId.length; i++){
				cityList.add(cityRepository.getById(cityId[i]));
			}
		}else{
			cityList.add(new CommCity());
		}
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(!StringUtils.isBlank(pName)){
			pNameFlag = 2;
		}
		return b2IApplyProductRepository.listToExport(agentList, processStepList, processStepFlag, cityList, cityFlag, "%"+pCode+"%", pCodeFlag, "%"+pName+"%", pNameFlag, applyTimeStart, applyTimeEnd);
	}

	@Override
	public List<Map> getMapBySearch(List<B2IApplyProduct> list) {
		List<Map> mapList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(B2IApplyProduct applyProduct :list){
			Map map = new HashMap();
			CommCity city = applyProduct.getB2IProject().getAgent().getCommCity();
			B2IProject project = applyProduct.getB2IProject();
			CommAgent si = project.getCommSi();
			CommCustomer customer = project.getCustomer();
			Product product = applyProduct.getProductPrice().getProduct();
			ProcessStep step = project.getStep().getPnextId();
			List<B2IApproveLog> logList = b2IApproveLogRepository.findByProject(project);
			
			map.put("city", city==null?"":city.getCityName());
			map.put("pCode", project.getpCode());
			map.put("pName", project.getpName());
			
			if(step.getStatusValue() > 0 && step.getStatusValue() < 10){
				map.put("approveStatus", step.getOperateStatus());
				map.put("hxStatus", "");
				map.put("flStatus", "");
			}else if(step.getStatusValue() < 100){
				map.put("approveStatus", "");
				map.put("hxStatus", step.getOperateStatus());
				map.put("flStatus", "");
			}else{
				map.put("approveStatus", "");
				map.put("hxStatus", "");
				map.put("flStatus", step.getOperateStatus());
			}
			
			map.put("approveMsg", "");
			map.put("approveTime", "");
			for (B2IApproveLog approveLog : logList) {
				if(approveLog.getUser().getCommRole().getId()==CommRole.HANGYEJINGLI){//全国经理改为行业经理
					map.put("approveMsg", approveLog.getApproveMsg());
					map.put("approveTime", sdf.format(approveLog.getApproveTime()));
					map.put("approvePrice", applyProduct.getApplyPrice());
				}
			}
			
			map.put("SIName", si.getIrName());
			map.put("SIPhone", si.getPhone());
			map.put("SIAddress", si.getAddress());
			map.put("product", product.getName());
			map.put("number", applyProduct.getApplyNumber());
			map.put("size1", product.getSize1());
			map.put("applyPrice", applyProduct.getApplyPrice());
			map.put("applyTime", sdf.format(project.getApplytime()));
			map.put("customerName", customer.getName());
			map.put("customerAddress", customer.getAddress()==null?"":customer.getAddress());
			map.put("customerLinkman", customer.getPerson()==null?"":customer.getPerson());
			map.put("customerPhone", customer.getMobile()==null?"":customer.getMobile());
			
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public List<B2IApplyProduct> findByProductAndAgent(Product product,
			CommAgent agent) {
		return b2IApplyProductRepository.findByProductAndAgent(product, agent);
	}

	@Override
	public B2IApplyProduct findById(Integer id) {
		return b2IApplyProductRepository.findOne(id);
	}
}
