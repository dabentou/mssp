package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;


public class PsiReportVo {

	private Product product;
	
	private CommAgent agent;
	
	private Integer currentInventory;
	
	private Integer totalSellIn;
	
	private Integer totalSellOut;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CommAgent getAgent() {
		return agent;
	}

	public void setAgent(CommAgent agent) {
		this.agent = agent;
	}

	public Integer getCurrentInventory() {
		return currentInventory;
	}

	public Integer getTotalSellIn() {
		return totalSellIn;
	}

	public void setTotalSellIn(Integer totalSellIn) {
		this.totalSellIn = totalSellIn;
	}

	public Integer getTotalSellOut() {
		return totalSellOut;
	}

	public void setTotalSellOut(Integer totalSellOut) {
		this.totalSellOut = totalSellOut;
	}

	public void setCurrentInventory(Integer currentInventory) {
		this.currentInventory = currentInventory;
	}
}
