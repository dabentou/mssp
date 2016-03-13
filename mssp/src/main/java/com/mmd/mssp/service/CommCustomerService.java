package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCustomer;

public interface CommCustomerService {

	public CommCustomer save(CommCustomer customer);
	
	void delete(CommCustomer customer);
	
	List<CommCustomer> listAllByAgentAndType(CommAgent agent,int type);
	
	CommCustomer getCustomerById(Integer id);

}
