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

import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.B2BApproveLog;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.SpecialApplyProduct;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.repository.B2BApproveLogRepository;
import com.mmd.mssp.repository.CommBusinessRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.repository.SpecialApplyProductRepository;
import com.mmd.mssp.service.SpecialApplyProductService;

/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
@Service
public class SpecialApplyProductServiceImp implements SpecialApplyProductService  {
	
	@Resource
	SpecialApplyProductRepository  specialApplyProductRepository;
	
	@Resource
	CommCityRepository cityRepository;
	
	@Resource
	CommBusinessRepository businessRepository;
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Resource
	B2BApproveLogRepository b2BApproveLogRepository;

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.SpecialApplyProductService#findSAPListByProject(java.lang.Integer)
	 */
	@Override
	public List<SpecialApplyProduct> findSAPListByProject(B2BProject project) {
		return specialApplyProductRepository.findByB2bProject(project);
	}

	@Override
	public List<SpecialApplyProduct> listToExport(CommAgent agent,
			Date applyTimeStart, Date applyTimeEnd, Date approveTimeStart,
			Date approveTimeEnd, Integer[] processStepId, Integer[] cityId,
			Integer[] businessId, String pCode, String pName, Integer[] pTypeId) {
		int agentFlag = 1;
		int processStepFlag = 1;
		int cityFlag = 1;
		int businessFlag = 1;
		int pCodeFlag = 1;
		int pNameFlag = 1;
		int pTypeFlag = 1;
		
		List<ProcessStep> processStepList = new ArrayList<ProcessStep>();
		List<CommCity> cityList = new ArrayList<CommCity>();
		List<CommBusiness> businessList = new ArrayList<CommBusiness>();
		List<Integer> pTypeList = new ArrayList<Integer>();
		
		if(agent!=null){
			agentFlag = 2;
		}
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
		if(businessId!=null){
			businessFlag = 2;
			for(int i=0; i<businessId.length; i++){
				businessList.add(businessRepository.getById(businessId[i]));
			}
		}else{
			businessList.add(new CommBusiness());
		}
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(!StringUtils.isBlank(pName)){
			pNameFlag = 2;
		}
		if(pTypeId!=null){
			pTypeFlag = 2;
			for(int i=0; i<pTypeId.length; i++){
				pTypeList.add(pTypeId[i]);
			}
		}else{
			pTypeList.add(5);
		}
		
		return specialApplyProductRepository.listToExport(agent, agentFlag, processStepList, processStepFlag, cityList, cityFlag, businessList, businessFlag, pCode, pCodeFlag, pName, pNameFlag, pTypeList, pTypeFlag);
	}

	@Override
	public List<Map> getMapBySearch(List<SpecialApplyProduct> list) {
		List<Map> mapList = new ArrayList();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(SpecialApplyProduct applyProduct :list){
			Map map = new HashMap();
			CommCity city = applyProduct.getB2bProject().getAgent().getCommCity();
			B2BProject b2bProject = applyProduct.getB2bProject();
			CommAgent si = b2bProject.getCommSi();
			Product product = applyProduct.getProductPrice().getProduct();
			ProcessStep step = b2bProject.getStep().getPnextId();
			List<B2BApproveLog> logList = b2BApproveLogRepository.findByProject(b2bProject);
			
			map.put("city", city==null?"":city.getCityName());
			map.put("pCode", b2bProject.getpCode());
			map.put("pName", b2bProject.getpName());
			
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
			for (B2BApproveLog approveLog : logList) {
				if(approveLog.getUser().getCommRole().getId()==CommRole.HANGYEJINGLI){//全国经理改为行业经理
					map.put("approveMsg", approveLog.getApproveMsg());
					map.put("approveTime", sdf.format(approveLog.getApproveTime()));
					map.put("approvePrice", applyProduct.getApplyPrice());
				}
			}
			
			map.put("SIName", si.getIrName());
			map.put("SIPhone", si.getPhone());
			map.put("SIAddress", si.getAddress());
			map.put("customerName", b2bProject.getCustomer().getName());
			map.put("product", product.getName());
			map.put("number", applyProduct.getNumber());
			map.put("size1", product.getSize1());
			map.put("applyPrice", applyProduct.getApplyPrice());
			map.put("applyTime", sdf.format(b2bProject.getApplyTime()));
			map.put("customerAddress", b2bProject.getCustomer().getAddress());
			map.put("customerLinkman", b2bProject.getCustomer().getPerson());
			map.put("customerPhone", b2bProject.getCustomer().getPhone());

			mapList.add(map);
			
		}
		return mapList;
	}

	@Override
	public SpecialApplyProduct findSAPListById(Integer id) {
		return specialApplyProductRepository.findById(id);
	}
	
	@Override
	public void save(SpecialApplyProduct specialApplyProduct) {
		specialApplyProductRepository.save(specialApplyProduct);
	}
}
