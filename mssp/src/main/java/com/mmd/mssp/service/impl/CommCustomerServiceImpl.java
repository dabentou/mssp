package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCustomer;
import com.mmd.mssp.repository.CommCustomerRepository;
import com.mmd.mssp.service.CommCustomerService;

@Service
public class CommCustomerServiceImpl implements CommCustomerService {

	@Resource
	CommCustomerRepository customerRepository;
	
	@Override
	public CommCustomer save(CommCustomer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public void delete(CommCustomer customer) {

		customerRepository.delete(customer);		
	}

	@Override
	public List<CommCustomer> listAllByAgentAndType(CommAgent agent,int type) {
		return customerRepository.findByAgentAndType(agent, type);
	}

	@Override
	public CommCustomer getCustomerById(Integer id) {
		return customerRepository.getById(id);
	}
}
