package com.mmd.mssp.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2ISponsorApplyProduct;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.repository.B2IApplySponsorProductRepository;
import com.mmd.mssp.service.B2IApplySponsorProductService;

@Service
public class B2IApplySponsorProductServiceImpl implements B2IApplySponsorProductService {
	
	@Resource
	B2IApplySponsorProductRepository b2iApplySponsorProductRepository;

	@Override
	public List<B2ISponsorApplyProduct> findByProductAndAgent(Product product,
			CommAgent agent) {
		return b2iApplySponsorProductRepository.findByProductAndAgent(product, agent);
	}

	@Override
	public B2ISponsorApplyProduct save(
			B2ISponsorApplyProduct sponsorApplyProduct) {
		return b2iApplySponsorProductRepository.save(sponsorApplyProduct);
	}

	@Override
	public List<B2ISponsorApplyProduct> findByProject(
			B2ISponsorProject sponsorProject) {
		return b2iApplySponsorProductRepository.findByProject(sponsorProject);
	}

	@Override
	public B2ISponsorApplyProduct findById(Integer id) {
		return b2iApplySponsorProductRepository.findOne(id);
	}
}
