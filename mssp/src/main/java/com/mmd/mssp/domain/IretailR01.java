package com.mmd.mssp.domain;

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

@Entity
@Table(name="iretail_r01")
public class IretailR01 {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="i_name")
	private String iName;
	
	@Basic
	@Column(name="decorate_level")
	private String decorateLevel;
	
	@Basic
	@Column(name="city_level")
	private String cityLevel;
	
	@Basic
	@Column(name="city_name")
	private String cityName;
	
	@Basic
	@Column(name="shop_place")
	private String shopPlace;
	
	@Basic
	@Column(name="i_level")
	private String iLevel;
	
	@Basic
	@Column(name="i_location")
	private String iLocation;
	
	@Basic
	@Column(name="yq_volume")
	private int yqVolume;
	
	@Basic
	@Column(name="ls_volume")
	private int lsVolume;
	
	@Basic
	@Column(name="i_acreage")
	private String iAcreage;
	
	@Basic
	@Column(name="ls_rate")
	private String lsRate;
	
	@Basic
	@Column(name="i_principal")
	private String iPrincipal;
	
	@Basic
	@Column(name="phone")
	private String phone;
	
	@Basic
	@Column(name="i_target_volume")
	private int iTargetVolume;
	
	@Basic
	@Column(name="remark")
	private String remark;
	
	@Basic
	@Column(name="i_real_pic")
	private String iRealPic;
	
	@Basic
	@Column(name="i_result_pic")
	private String iResultPic;

	@Basic
	@Column(name="charges_detail")
	private String chargesDetail;
	
	@Basic
	@Column(name="material_detail")
	private String materialDetail;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinColumn(name="iretail_project_id")
	private IretailProject retailProject;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getiName() {
		return iName;
	}

	public void setiName(String iName) {
		this.iName = iName;
	}

	public String getCityLevel() {
		return cityLevel;
	}

	public void setCityLevel(String cityLevel) {
		this.cityLevel = cityLevel;
	}

	public String getShopPlace() {
		return shopPlace;
	}

	public void setShopPlace(String shopPlace) {
		this.shopPlace = shopPlace;
	}

	public String getiLevel() {
		return iLevel;
	}

	public void setiLevel(String iLevel) {
		this.iLevel = iLevel;
	}

	public String getiLocation() {
		return iLocation;
	}

	public void setiLocation(String iLocation) {
		this.iLocation = iLocation;
	}

	public int getYqVolume() {
		return yqVolume;
	}

	public void setYqVolume(int yqVolume) {
		this.yqVolume = yqVolume;
	}

	public int getLsVolume() {
		return lsVolume;
	}

	public void setLsVolume(int lsVolume) {
		this.lsVolume = lsVolume;
	}

	public String getiAcreage() {
		return iAcreage;
	}

	public void setiAcreage(String iAcreage) {
		this.iAcreage = iAcreage;
	}

	public String getLsRate() {
		return lsRate;
	}

	public void setLsRate(String lsRate) {
		this.lsRate = lsRate;
	}

	public String getiPrincipal() {
		return iPrincipal;
	}

	public void setiPrincipal(String iPrincipal) {
		this.iPrincipal = iPrincipal;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getiTargetVolume() {
		return iTargetVolume;
	}

	public void setiTargetVolume(int iTargetVolume) {
		this.iTargetVolume = iTargetVolume;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getiRealPic() {
		return iRealPic;
	}

	public void setiRealPic(String iRealPic) {
		this.iRealPic = iRealPic;
	}

	public String getiResultPic() {
		return iResultPic;
	}

	public void setiResultPic(String iResultPic) {
		this.iResultPic = iResultPic;
	}

	public String getChargesDetail() {
		return chargesDetail;
	}

	public void setChargesDetail(String chargesDetail) {
		this.chargesDetail = chargesDetail;
	}

	public String getMaterialDetail() {
		return materialDetail;
	}

	public void setMaterialDetail(String materialDetail) {
		this.materialDetail = materialDetail;
	}

	public IretailProject getRetailProject() {
		return retailProject;
	}

	public void setRetailProject(IretailProject retailProject) {
		this.retailProject = retailProject;
	}
	
	public String getDecorateLevel() {
		return decorateLevel;
	}

	public void setDecorateLevel(String decorateLevel) {
		this.decorateLevel = decorateLevel;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
