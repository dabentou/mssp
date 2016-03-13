package com.mmd.mssp.domain;

import java.math.BigDecimal;

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
import javax.persistence.Transient;

@Entity
@Table(name="b2i_sponsor_apply_product")
public class B2ISponsorApplyProduct {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_price_id")
	private ProductPrice productPrice;
	
	@Basic
	@Column(name="apply_number")
	private Integer applyNumber;
	
	@Basic
	@Column(name="apply_price")
	private BigDecimal applyPrice;
	
	@Basic
	@Column(name="reply_price")
	private BigDecimal replyPrice;
	
	@Basic
	@Column(name="support_money")
	private BigDecimal supportMoney;
	
	@Basic
	@Column(name="compete_product")
	private String competeProduct;
	
	@Basic
	@Column(name="compete_price")
	private BigDecimal competePrice;
	
	@Basic
	@Column(name="area_mange_rice")
	private BigDecimal areaMangePrice;
	
	@Basic
	@Column(name="sell_mange_price")
	private BigDecimal sellMangePrice;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2i_sponsor_project_id")
	private B2ISponsorProject b2iSponsorProject ;
	
	@Transient
	private BigDecimal rebate;//返利

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public ProductPrice getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(ProductPrice productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getApplyNumber() {
		return applyNumber;
	}

	public void setApplyNumber(Integer applyNumber) {
		this.applyNumber = applyNumber;
	}

	public String getCompeteProduct() {
		return competeProduct;
	}

	public void setCompeteProduct(String competeProduct) {
		this.competeProduct = competeProduct;
	}

	public BigDecimal getCompetePrice() {
		return competePrice;
	}

	public void setCompetePrice(BigDecimal competePrice) {
		this.competePrice = competePrice;
	}

	public BigDecimal getApplyPrice() {
		return applyPrice;
	}

	public void setApplyPrice(BigDecimal applyPrice) {
		this.applyPrice = applyPrice;
	}

	public BigDecimal getReplyPrice() {
		return replyPrice;
	}

	public void setReplyPrice(BigDecimal replyPrice) {
		this.replyPrice = replyPrice;
	}

	public BigDecimal getSupportMoney() {
		return supportMoney;
	}

	public void setSupportMoney(BigDecimal supportMoney) {
		this.supportMoney = supportMoney;
	}

	public B2ISponsorProject getB2iSponsorProject() {
		return b2iSponsorProject;
	}

	public void setB2iSponsorProject(B2ISponsorProject b2iSponsorProject) {
		this.b2iSponsorProject = b2iSponsorProject;
	}

	public BigDecimal getAreaMangePrice() {
		return areaMangePrice;
	}

	public void setAreaMangePrice(BigDecimal areaMangePrice) {
		this.areaMangePrice = areaMangePrice;
	}

	public BigDecimal getSellMangePrice() {
		return sellMangePrice;
	}

	public void setSellMangePrice(BigDecimal sellMangePrice) {
		this.sellMangePrice = sellMangePrice;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}

}
