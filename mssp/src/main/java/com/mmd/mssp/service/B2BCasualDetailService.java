package com.mmd.mssp.service;

import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2BCasualDetail;
import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.vo.B2BCasualDetailDomain;

public interface B2BCasualDetailService {
	
	void save(List<B2BCasualDetail> list);
	
	List<Map<String,Map<String,String>>> getCasualDetail(B2BProject project);
	
	List<B2BCasualDetailDomain> getCasualDetails(B2BProject project);
}
