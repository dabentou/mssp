package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.PsiInventory;

/**
 * @ClassName: SellOutUpdateVo
 * @Package com.mmd.mssp.domain.vo
 * @Description: TODO
 * @author xinyi.zhang
 * @date 2015-12-30
 * @version V1.1 
 */
public class SellOutUpdateVo {
	
	private PsiInventory psiInventory;
	private Integer sumSellInLastMonth;
	private Integer sumSellOutLastMonth;
	private PsiInventory currentInventory;
	
	public PsiInventory getPsiInventory() {
		return psiInventory;
	}
	public void setPsiInventory(PsiInventory psiInventory) {
		this.psiInventory = psiInventory;
	}
	public Integer getSumSellInLastMonth() {
		return sumSellInLastMonth;
	}
	public void setSumSellInLastMonth(Integer sumSellInLastMonth) {
		this.sumSellInLastMonth = sumSellInLastMonth;
	}
	public Integer getSumSellOutLastMonth() {
		return sumSellOutLastMonth;
	}
	public void setSumSellOutLastMonth(Integer sumSellOutLastMonth) {
		this.sumSellOutLastMonth = sumSellOutLastMonth;
	}
	public PsiInventory getCurrentInventory() {
		return currentInventory;
	}
	public void setCurrentInventory(PsiInventory currentInventory) {
		this.currentInventory = currentInventory;
	}
}
