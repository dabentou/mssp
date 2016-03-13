package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.Product;

/**
 * @ClassName: SellOutVo
 * @Package com.mmd.mssp.domain.vo
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-12-25
 * @version V1.1 
 */
public class SellOutVo {
	
	private Product product;
	
	private int estimateVolume;//型号预估销量
	
	private int totalSellin;//累计Sell in
	
	private int totalSellout;//累计Sell in
	
	private int currentInventory;//当前库存
	
	private int selloutVolume;

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

	public int getTotalSellin() {
		return totalSellin;
	}

	public void setTotalSellin(int totalSellin) {
		this.totalSellin = totalSellin;
	}

	public int getTotalSellout() {
		return totalSellout;
	}

	public void setTotalSellout(int totalSellout) {
		this.totalSellout = totalSellout;
	}

	public int getCurrentInventory() {
		return currentInventory;
	}

	public void setCurrentInventory(int currentInventory) {
		this.currentInventory = currentInventory;
	}

	public int getSelloutVolume() {
		return selloutVolume;
	}

	public void setSelloutVolume(int selloutVolume) {
		this.selloutVolume = selloutVolume;
	}
}
