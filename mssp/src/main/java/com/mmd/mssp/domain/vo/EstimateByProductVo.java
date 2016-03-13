package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.Product;

public class EstimateByProductVo {

	private Product product;
	
	private int estimateVolume;//型号预估销量-对应psi_estimate_by_product表中total_volume
	
	private int isEstimate;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getEstimateVolume() {
		return estimateVolume;
	}

	public void setEstimateVolume(int estimateVolume) {
		this.estimateVolume = estimateVolume;
	}

	public int getIsEstimate() {
		return isEstimate;
	}

	public void setIsEstimate(int isEstimate) {
		this.isEstimate = isEstimate;
	}
	
}
