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
@Table(name="b2i_apply_product")
public class B2IApplyProduct {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_price_id")
	private ProductPrice productPrice;
	
	@Basic
	@Column(name="psi_sell_in")
	private Integer psiSellIn;
	
	@Basic
	@Column(name="apply_number")
	private Integer applyNumber;
	
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
	
	@Basic
	@Column(name="apply_price")
	private BigDecimal applyPrice;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="b2i_project_id")
	private B2IProject b2IProject;
	
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

	public Integer getPsiSellIn() {
		return psiSellIn;
	}

	public void setPsiSellIn(Integer psiSellIn) {
		this.psiSellIn = psiSellIn;
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

	public BigDecimal getApplyPrice() {
		return applyPrice;
	}

	public void setApplyPrice(BigDecimal applyPrice) {
		this.applyPrice = applyPrice;
	}

	public B2IProject getB2IProject() {
		return b2IProject;
	}

	public void setB2IProject(B2IProject b2iProject) {
		b2IProject = b2iProject;
	}

	public BigDecimal getRebate() {
		return rebate;
	}

	public void setRebate(BigDecimal rebate) {
		this.rebate = rebate;
	}
	
	
}
