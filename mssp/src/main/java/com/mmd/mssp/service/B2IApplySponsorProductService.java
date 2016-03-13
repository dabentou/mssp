package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2IApplyProduct;
import com.mmd.mssp.domain.B2IProject;
import com.mmd.mssp.domain.B2ISponsorApplyProduct;
import com.mmd.mssp.domain.B2ISponsorProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;

public interface B2IApplySponsorProductService {

	
	public List<B2ISponsorApplyProduct> findByProductAndAgent(Product product,CommAgent agent);
	
	public B2ISponsorApplyProduct save(B2ISponsorApplyProduct sponsorApplyProduct);
	
	public List<B2ISponsorApplyProduct> findByProject(B2ISponsorProject sponsorProject);
	
	public B2ISponsorApplyProduct findById(Integer id);
}
