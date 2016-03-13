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

import com.mmd.mssp.domain.B2CApplyOther;
import com.mmd.mssp.domain.B2CApplyOtherReplyLog;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.repository.B2CApplyOtherRepository;
import com.mmd.mssp.service.B2CApplyOtherService;

@Service
public class B2CApplyOtherServiceImpl implements B2CApplyOtherService {
	
	@Resource
	B2CApplyOtherRepository applyOtherRepository;
	
	@Override
	public B2CApplyOther save(B2CApplyOther applyOther) {
		return applyOtherRepository.save(applyOther);
	}

	@Override
	public List<B2CApplyOther> findByProjectAndType(B2CProject project,Integer type) {
		return applyOtherRepository.findByProjectAndType(project, type);
	}

	@Override
	public B2CApplyOther getB2CApplyOtherById(Integer id) {
		return applyOtherRepository.getById(id);
	}

	@Override
	public List<B2CApplyOther> findByProject(B2CProject project) {
		return applyOtherRepository.findByProject(project);
	}

	@Override
	public void delete(B2CApplyOther applyOther) {
		applyOtherRepository.delete(applyOther);
	}

	@Override
	public List<B2CApplyOther> searchProjectToExport(Date startDate,Date endDate,String pCode,List<CommAgent> agentList,List<Integer> projectIds) {
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
		return applyOtherRepository.searchProjectToExport(startDate, startDateFlag, endDate, endDateFlag, "%"+pCode+"%", pCodeFlag, agentList, projectIds, projectIdFlag);
	}

	@Override
	public List<Map> getMapBySearch(List<B2CApplyOther> list,CommUser user) {
		List<Map> mapList = new ArrayList();
		for (B2CApplyOther applyOther : list) {
			Map map = new HashMap();
			CommArea area = applyOther.getB2CProject().getAgent().getCommCity().getCommArea();
			CommAgent agent = applyOther.getB2CProject().getAgent();
			Integer type = applyOther.getType();
			B2CApplyOtherReplyLog replyLog = applyOther.getReplyLog();
			map.put("area", area==null?"":area.getAreaName());
			map.put("agent", agent==null?"":agent.getIrName());
			if(type==2){
				map.put("type", "先进达成特批");
			}else if(type==3){
				map.put("type", "月度各项考核指标特批");
			}else if(type==4){
				map.put("type", "季度各项考核指标特批");
			}else{
				map.put("type", "NET外奖励特批");
			}
			map.put("pCode", applyOther.getB2CProject().getpCode());
			map.put("condition", replyLog.getCondition().getName());
			map.put("content", replyLog.getContent());
			map.put("applyPrice", "");
			map.put("number", "");
			map.put("isPicking", "");
			map.put("poPrice", "");
			map.put("netPrice", "");
			if(user.getCommRole().getId()==CommRole.XIAOFUZHUGUAN || user.getCommRole().getId()==CommRole.XIAOFUZONGJINGLI){
				map.put("financePrice", "");
				map.put("syPrice", "");
			}
			map.put("isGrowth", "");
			map.put("growthRate", "");
			map.put("applyTotalPrice", "");
			map.put("compProduct", "");
			map.put("compPrice", "");
			map.put("remark", "无");
			mapList.add(map);
		}
		return mapList;
	}

}
