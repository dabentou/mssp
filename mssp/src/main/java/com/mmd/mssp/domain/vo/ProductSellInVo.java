package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.Product;

public class ProductSellInVo {
	
	private Product product;
	private int sellIn; //当月改代理改型号的sellIn
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getSellIn() {
		return sellIn;
	}
	public void setSellIn(int sellIn) {
		this.sellIn = sellIn;
	}
	
}
