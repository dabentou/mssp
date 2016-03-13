package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.B2CSpecialApplyProductReplyLog;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.repository.B2CSpecialApplyProductRepository;
import com.mmd.mssp.service.B2CSpecialApplyProductService;
import com.mmd.mssp.service.CommService;

@Service
public class B2CSpecialApplyProductServiceImpl implements
		B2CSpecialApplyProductService {

	@Resource
	B2CSpecialApplyProductRepository b2CSpecialApplyProductRepository;
	
	@Override
	public B2CSpecialApplyProduct save(
			B2CSpecialApplyProduct b2cSpecialApplyProduct) {
		return b2CSpecialApplyProductRepository.save(b2cSpecialApplyProduct);
	}

	@Override
	public List<B2CSpecialApplyProduct> getByB2CProject(B2CProject b2CProject) {
		return b2CSpecialApplyProductRepository.findByB2CProject(b2CProject);
	}

	@Override
	public void delete(B2CSpecialApplyProduct b2CSpecialApplyProduct) {
		b2CSpecialApplyProductRepository.delete(b2CSpecialApplyProduct);
	}

	@Override
	public List<B2CSpecialApplyProduct> searchProjectToExportByAgent(Date startDate,
			Date endDate, String pCode,CommAgent agent) {
		int pCodeFlag = 1;
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		return b2CSpecialApplyProductRepository.searchProjectToExportByAgent(startDate, endDate, pCode, pCodeFlag, agent);
	}

	@Override
	public List<Map> getMapBySearch(List<B2CSpecialApplyProduct> list,CommUser user) {
		List<Map> mapList = new ArrayList();
		for (B2CSpecialApplyProduct applyProduct : list) {
			Map map = new HashMap();
			CommArea area = applyProduct.getProject().getAgent().getCommCity().getCommArea();
			CommAgent agent = applyProduct.getProject().getAgent();
			//最终同意的
			B2CSpecialApplyProductReplyLog replyLog = applyProduct.getReplyLog();
			
			map.put("area", area==null?"":area.getAreaName());
			map.put("agent", agent==null?"":agent.getIrName());
			map.put("type", "特殊资源申请");
			map.put("pCode", applyProduct.getProject().getpCode());
			map.put("condition", replyLog.getConditions());
			map.put("content", replyLog.getContent());
			map.put("applyPrice", replyLog.getReplyPrice());
			map.put("number", replyLog.getNumber());
			map.put("isPicking", applyProduct.getIsPicking()==0?"否":"是");
			map.put("poPrice", applyProduct.getPoPrice());
			map.put("netPrice", applyProduct.getNetPrice());
			if(user.getCommRole().getId()==CommRole.XIAOFUZHUGUAN || user.getCommRole().getId()==CommRole.XIAOFUZONGJINGLI){
				map.put("financePrice", applyProduct.getFinancePrice());
				map.put("syPrice", applyProduct.getSyPrice());
			}
			map.put("isGrowth", applyProduct.getIsGrowth()==0?"否":"是");
			map.put("growthRate", applyProduct.getGrowthRate());
			map.put("applyTotalPrice", replyLog.getApplyTotalPrice());
			map.put("compProduct", applyProduct.getCompPrice());
			map.put("compPrice", applyProduct.getCompProduct());
			map.put("remark", applyProduct.getRemark());
			mapList.add(map);
		}
		return mapList;
	}

	@Override
	public B2CSpecialApplyProduct getByApplyProductId(Integer id) {
		return b2CSpecialApplyProductRepository.getById(id);
	}

	@Override
	public List<B2CSpecialApplyProduct> searchProjectToExport(Date startDate,Date endDate,String pCode,List<CommAgent> agentList,List<Integer> projectIds) {
		int pCodeFlag = 1;
		int startDateFlag = 1;
		int endDateFlag = 1;
		int projectIdFlag = 1;
		if(!StringUtils.isBlank(pCode)){
			pCodeFlag = 2;
		}
		if(startDate!=null){
			startDateFlag =2;
		}
		if(endDate!=null){
			endDateFlag =2;
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DATE, 1);
			endDate = cal.getTime();
		}
		if(projectIds.size()>0){
			pCodeFlag = 2;
		}else{
			projectIds.add(-1);
		}
		return b2CSpecialApplyProductRepository.searchProjectToExport(startDate, startDateFlag, endDate, endDateFlag, pCode, pCodeFlag, agentList, projectIds, projectIdFlag);
	}
}
