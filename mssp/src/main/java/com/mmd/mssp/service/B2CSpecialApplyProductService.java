package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2CProject;
import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommUser;

public interface B2CSpecialApplyProductService {

	public B2CSpecialApplyProduct save(B2CSpecialApplyProduct b2CSpecialApplyProduct);
	
	public List<B2CSpecialApplyProduct> getByB2CProject(B2CProject b2CProject);
	
	void delete(B2CSpecialApplyProduct b2CSpecialApplyProduct);
	
	public List<B2CSpecialApplyProduct> searchProjectToExportByAgent(Date startDate,Date endDate,String pCode,CommAgent agent);
	
	public List<B2CSpecialApplyProduct> searchProjectToExport(Date startDate,Date endDate,String pCode,List<CommAgent> agentList,List<Integer> projectIds);
	
	List<Map> getMapBySearch(List<B2CSpecialApplyProduct> list,CommUser user);
	
	public B2CSpecialApplyProduct getByApplyProductId(Integer id);
}
