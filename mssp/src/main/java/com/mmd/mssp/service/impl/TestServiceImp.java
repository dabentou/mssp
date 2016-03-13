package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.repository.TestRepository;
import com.mmd.mssp.service.TestService;

/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
@Service
public class TestServiceImp implements TestService  {
	
	@Resource
	TestRepository testRepository;
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.TestService#findById(int)
	 */
	@Override
	public CommArea findTestAreaById(int i) {
		return testRepository.findTestAreaById(i);
	}

	public TestRepository getTestRepository() {
		return testRepository;
	}

	public void setTestRepository(TestRepository testRepository) {
		this.testRepository = testRepository;
	}
	
}
