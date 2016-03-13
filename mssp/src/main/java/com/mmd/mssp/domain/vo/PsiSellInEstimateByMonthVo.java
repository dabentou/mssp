package com.mmd.mssp.domain.vo;

import com.mmd.mssp.domain.CommAgent;

public class PsiSellInEstimateByMonthVo {
	
	private CommAgent commAgent;
	private int estimateVolume;//型号预估销量-对应psi_sell_in_estimate_by_month表中sell_in_volume
	private int isEstimate;
	public CommAgent getCommAgent() {
		return commAgent;
	}
	public void setCommAgent(CommAgent commAgent) {
		this.commAgent = commAgent;
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
