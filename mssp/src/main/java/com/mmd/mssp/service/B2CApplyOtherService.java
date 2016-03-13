package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2CApplyOther;
import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;

public interface B2CApplyOtherService {

	B2CApplyOther getB2CApplyOtherById(Integer id);
	
	public B2CApplyOther save(B2CApplyOther applyOther);
	
	List<B2CApplyOther> findByProjectAndType(B2CProject project,Integer type);
	
	List<B2CApplyOther> findByProject(B2CProject project);
	
	public List<B2CApplyOther> searchProjectToExport(Date startDate,Date endDate,String pCode,List<CommAgent> agentList,List<Integer> projectIds);
	
	List<Map> getMapBySearch(List<B2CApplyOther> list,CommUser user);
	
	void delete(B2CApplyOther applyOther);
}
