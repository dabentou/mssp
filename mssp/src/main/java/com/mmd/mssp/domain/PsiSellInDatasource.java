package com.mmd.mssp.domain;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 * @ClassName: PsiSellInDatasource
 * @Package com.mmd.mssp.domain.vo
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-12-21
 * @version V1.1 
 */
@Entity
@Table(name="psi_sell_in_datasource")
public class PsiSellInDatasource {
	
	public static int CHANNEL_TYPE_MMD=1;//飞生
	
	public static int CHANNEL_TYPE_YH=2;//越海
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Basic
	@Column(name="delivery")
	private String delivery;
	
	@Basic
	@Column(name="item")
	private String item;
	
	@Basic
	@Column(name="material")
	private String material;//型号
	
	@Basic
	@Column(name="dlv_qty")
	private String  dlvQty;//销量

	@Basic
	@Column(name="su")
	private String su;
	
	@Basic
	@Column(name = "createdon", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createdon;//数据日期
	
	@Basic
	@Column(name="volume")
	private String volume;
	
	@Basic
	@Column(name="vun")
	private String vun;
	
	@Basic
	@Column(name="sold_to")
	private String soldTo;
	
	@Basic
	@Column(name="nameofsold_toparty")
	private String nameofsoldToparty;
	
	@Basic
	@Column(name="ship_to")
	private String shipTo;

	@Basic
	@Column(name="nameoftheship_toparty")
	private String nameoftheshipToparty;
	
	@Basic
	@Column(name="ship_tol")
	private String shipToL;
	
	@Basic
	@Column(name="dlvty")
	private String dlvTy;
	
	@Basic
	@Column(name="shpt")
	private String shPt;
	
	@Basic
	@Column(name="area")
	private String area;//区域
	
	@Basic
	@Column(name="city")
	private String city;//城市
	
	@Basic
	@Column(name="province")
	private String province;
	
	@Basic
	@Column(name="size")
	private String size;//寸别
	
	@Basic
	@Column(name="prop")
	private String ahips;
	
	@Basic
	@Column(name="channel_type")
	private int channelType;
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;//数据导入时间
	
	@Basic
	@Column(name="seq_number")
	private String seqNumber;
	
	@Basic
	@Column(name="input_number")
	private String inputNumber;
	
	@Basic
	@Column(name="repository_type")
	private String repositoryType;
	
	@Basic
	@Column(name="product")
	private String product;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDelivery() {
		return delivery;
	}

	public void setDelivery(String delivery) {
		this.delivery = delivery;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSu() {
		return su;
	}

	public void setSu(String su) {
		this.su = su;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getVun() {
		return vun;
	}

	public void setVun(String vun) {
		this.vun = vun;
	}

	public String getSoldTo() {
		return soldTo;
	}

	public void setSoldTo(String soldTo) {
		this.soldTo = soldTo;
	}

	public String getNameofsoldToparty() {
		return nameofsoldToparty;
	}

	public void setNameofsoldToparty(String nameofsoldToparty) {
		this.nameofsoldToparty = nameofsoldToparty;
	}

	public String getShipTo() {
		return shipTo;
	}

	public void setShipTo(String shipTo) {
		this.shipTo = shipTo;
	}

	public String getNameoftheshipToparty() {
		return nameoftheshipToparty;
	}

	public void setNameoftheshipToparty(String nameoftheshipToparty) {
		this.nameoftheshipToparty = nameoftheshipToparty;
	}

	public String getShipToL() {
		return shipToL;
	}

	public void setShipToL(String shipToL) {
		this.shipToL = shipToL;
	}

	public String getDlvTy() {
		return dlvTy;
	}

	public void setDlvTy(String dlvTy) {
		this.dlvTy = dlvTy;
	}

	public String getShPt() {
		return shPt;
	}

	public void setShPt(String shPt) {
		this.shPt = shPt;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAhips() {
		return ahips;
	}

	public void setAhips(String ahips) {
		this.ahips = ahips;
	}

	public String getDlvQty() {
		return dlvQty;
	}

	public void setDlvQty(String dlvQty) {
		this.dlvQty = dlvQty;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setCreatedon(Date createdon) {
		this.createdon = createdon;
	}

	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(String seqNumber) {
		this.seqNumber = seqNumber;
	}

	public String getInputNumber() {
		return inputNumber;
	}

	public void setInputNumber(String inputNumber) {
		this.inputNumber = inputNumber;
	}

	public String getRepositoryType() {
		return repositoryType;
	}

	public void setRepositoryType(String repositoryType) {
		this.repositoryType = repositoryType;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
	
}
