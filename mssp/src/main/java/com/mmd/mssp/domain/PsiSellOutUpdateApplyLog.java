package com.mmd.mssp.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * 
 */
@Entity
@Table(name="psi_sell_out_update_apply_log")
public class PsiSellOutUpdateApplyLog {
	
	public static int  CHECK_WAINTING = 0;//待审批
	public static int AGREE=1;//同意
	public static int NOT_AGREE=-1;//不同意

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Basic
	@Column(name="channel_type")
	private Integer channelType;
	
	
	@Basic
	@Column(name="ll_inventory")
	private Integer llInventory;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_id")
	private Product product;
	
	@Basic
	@Column(name="sell_out_valume")
	private Integer sellOutlVolume;
	
	@Basic
	@Column(name="sell_in_volume")
	private Integer sellInlVolume;
	
	@Basic
	@Column(name="l_inventory")
	private Integer lInventory;
	
	@Basic
	@Column(name="real_sell_out")
	private Integer realSellOut;
	
	@Basic
	@Column(name="sell_out_gap")
	private Integer sellOutGap;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="reason_id")
	private PsiReason psiReason;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="apply_id")
	private CommAgent commAgent;
	
	@Basic
	@Column(name = "apply_time", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date applyTime;
	
	@Basic
	@Column(name="status")
	private Integer status;

	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="approve_template_id")
	private ApproveTemplate temp;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="current_process_step")
	private ProcessStep step;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getSellOutlVolume() {
		return sellOutlVolume;
	}

	public void setSellOutlVolume(Integer sellOutlVolume) {
		this.sellOutlVolume = sellOutlVolume;
	}

	public Integer getSellInlVolume() {
		return sellInlVolume;
	}

	public void setSellInlVolume(Integer sellInlVolume) {
		this.sellInlVolume = sellInlVolume;
	}


	public Integer getRealSellOut() {
		return realSellOut;
	}

	public void setRealSellOut(Integer realSellOut) {
		this.realSellOut = realSellOut;
	}

	public Integer getSellOutGap() {
		return sellOutGap;
	}

	public void setSellOutGap(Integer sellOutGap) {
		this.sellOutGap = sellOutGap;
	}

	public PsiReason getPsiReason() {
		return psiReason;
	}

	public void setPsiReason(PsiReason psiReason) {
		this.psiReason = psiReason;
	}


	public CommAgent getCommAgent() {
		return commAgent;
	}

	public void setCommAgent(CommAgent commAgent) {
		this.commAgent = commAgent;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLlInventory() {
		return llInventory;
	}

	public void setLlInventory(Integer llInventory) {
		this.llInventory = llInventory;
	}

	public Integer getlInventory() {
		return lInventory;
	}

	public void setlInventory(Integer lInventory) {
		this.lInventory = lInventory;
	}

	public ApproveTemplate getTemp() {
		return temp;
	}

	public void setTemp(ApproveTemplate temp) {
		this.temp = temp;
	}

	public ProcessStep getStep() {
		return step;
	}

	public void setStep(ProcessStep step) {
		this.step = step;
	}
}