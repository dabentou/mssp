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

@Entity
@Table(name="comm_product")
public class Product {
	
	public static int  DELETE = 1;//已删除
	public static int NOT_DELETE=0;//未删除
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	private String name;
	
	@Basic
	private String size1;

	@Basic
	private String size2;
	
	@Basic
	private String material;
	
	@Basic
	@Column(name="prodect_format")
	private String prodectFormat;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="product_series_id")
	private ProductSeries productSeries;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="series_id")
	private CommSeries commSeries;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="pannel_id")
	private Pannel pannel;
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;
	
	@Basic
	@Column(name="status", columnDefinition="INT")
	private Integer status;
	
	@Basic
	@Column(name="channel_type", columnDefinition="INT")
	private Integer channelType;
	
	@Basic
	@Column(name="sell_type", columnDefinition="INT")
	private Integer sellType;

	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSize1() {
		return size1;
	}

	public void setSize1(String size1) {
		this.size1 = size1;
	}

	public String getSize2() {
		return size2;
	}

	public void setSize2(String size2) {
		this.size2 = size2;
	}

	public String getMaterial() {
		return material;
	}


	public String getProdectFormat() {
		return prodectFormat;
	}

	public void setProdectFormat(String prodectFormat) {
		this.prodectFormat = prodectFormat;
	}


	public Integer getSellType() {
		return sellType;
	}

	public void setSellType(Integer sellType) {
		this.sellType = sellType;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public ProductSeries getProductSeries() {
		return productSeries;
	}

	public void setProductSeries(ProductSeries productSeries) {
		this.productSeries = productSeries;
	}

	public CommSeries getCommSeries() {
		return commSeries;
	}

	public void setCommSeries(CommSeries commSeries) {
		this.commSeries = commSeries;
	}

	public Pannel getPannel() {
		return pannel;
	}

	public void setPannel(Pannel pannel) {
		this.pannel = pannel;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	
	public static Integer[] getSellTypeByChannlType(Integer channelType){
		if(channelType==0){
			return new Integer[]{1,2,3};
		}else if(channelType==1){
			return new Integer[]{1,3};
		}else if(channelType==2){
			return new Integer[]{2,3};
		}
		return new Integer[0];
	}
}
