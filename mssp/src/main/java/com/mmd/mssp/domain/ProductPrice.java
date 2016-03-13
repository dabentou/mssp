package com.mmd.mssp.domain;

import java.math.BigDecimal;
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

@Entity
@Table(name="comm_product_price")
public class ProductPrice {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_id")
	private Product product;
	
	@Basic
	@Column(name="finance_price", columnDefinition="Decimal")
	private BigDecimal financePrice;
	
	@Basic
	@Column(name="net_price", columnDefinition="Decimal")
	private BigDecimal netPrice;
	
	@Basic
	@Column(name="inter_public_price", columnDefinition="Decimal")
	private BigDecimal interPublicPrice;
	
	@Basic
	@Column(name="b2b_public_price", columnDefinition="Decimal")
	private BigDecimal b2bPublicPrice;
	
	@Basic
	@Column(name="po_price", columnDefinition="Decimal")
	private BigDecimal poPrice;
	
	@Basic
	@Column(name="sy_price", columnDefinition="Decimal")
	private BigDecimal syPrice;
	
	@Basic
	@Column(name = "date_month", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date dateMonth;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public BigDecimal getFinancePrice() {
		return financePrice;
	}

	public void setFinancePrice(BigDecimal financePrice) {
		this.financePrice = financePrice;
	}

	public BigDecimal getNetPrice() {
		return netPrice;
	}

	public void setNetPrice(BigDecimal netPrice) {
		this.netPrice = netPrice;
	}

	public BigDecimal getInterPublicPrice() {
		return interPublicPrice;
	}

	public void setInterPublicPrice(BigDecimal interPublicPrice) {
		this.interPublicPrice = interPublicPrice;
	}

	public BigDecimal getB2bPublicPrice() {
		return b2bPublicPrice;
	}

	public void setB2bPublicPrice(BigDecimal b2bPublicPrice) {
		this.b2bPublicPrice = b2bPublicPrice;
	}

	public BigDecimal getPoPrice() {
		return poPrice;
	}

	public void setPoPrice(BigDecimal poPrice) {
		this.poPrice = poPrice;
	}

	public BigDecimal getSyPrice() {
		return syPrice;
	}

	public void setSyPrice(BigDecimal syPrice) {
		this.syPrice = syPrice;
	}

	public Date getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(Date dateMonth) {
		this.dateMonth = dateMonth;
	}
}
