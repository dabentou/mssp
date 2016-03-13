package com.mmd.mssp.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="comm_role")
public class CommRole {
	
	/**
	 * 总经理
	 */
    public static final int ZONGJINGLI=1;
    
    /**
     * 副总
     */
    public static final int FUZONG=2;
    
  /**
   * 大区经理
   */
    public static final int DAQUJINGLI=3;
    
  /**
   * 零售专员
   */
    public static final int LINGSHOUZHUANYUAN=4;
    
  /**
   * 代理商
   */
    public static final int DAILISHANG=5;
    
  /**
   * 零售店
   */
    public static final int LINGSHOUDIAN=6;
    
  /**
   * 系统管理员
   */
    public static final int XITONGGUANLIYUAN=7;
    
  /**
   * 销服主管
   */
    public static final int XIAOFUZHUGUAN=8;
    
  /**
   * 销服总经理
   */
    public static final int XIAOFUZONGJINGLI=9;
    
  /**
   * 销售总经理
   */
    public static final int XIAOSHOUZONGJINGLI=10;
    
  /**
   * 供应链
   */
    public static final int GONGYINGLIAN=11;
    
  /**
   * 销服总监
   */
    public static final int XIAOFUZONGJIAN=12;
    
  /**
   * 财务
   */
    public static final int CAIWU=13;
    
  /**
   * 网吧总监
   */
    public static final int WANGBAZONGJIAN=14;
    
  /**
   * 工厂
   */
    public static final int GONGCHANG=15;
    
  /**
   * 零售主管
   */
    public static final int LINGSHOUZHUGUAN=16;
    
  /**
   * 市场部
   */
    public static final int SHICHANGBU=17;
    
  /**
   * 行业经理
   */
    public static final int HANGYEJINGLI=18;
    
  /**
   * 门店装修专员
   */
    public static final int MENDIANZHUANGXIUYUAN=19;
    
  /**
   * 装修供应商
   */
    public static final int ZHUANGXIUGONGYINGSHANG=20;
    
  /**
   * 培训主管
   */
    public static final int PEIXUNZHUGUAN=21;
    
  /**
   * 精英网管理员
   */
    public static final int JINGYINGWANGGUANLIYUAN=22;
    
  /**
   * PSI管理员
   */
    public static final int PSIGUANLIYUAN=27;
    
  /**
   * B2C管理员
   */
    public static final int B2CGUANLIYUAN=28;
    
  /**
   * B2I管理员
   */
    public static final int B2IGUANLIYUAN=29;
    
  /**
   * B2B管理员
   */
    public static final int B2BGUANLIYUAN=30;
    
  /**
   * Iretail管理员
   */
    public static final int IRETAILGUANLIYUAN=31;
    
  /**
   * 财务总监
   */
    public static final int CAIWUZONGJIAN=32;

	
	@OneToMany(targetEntity=com.mmd.mssp.domain.CommRolePermission.class, mappedBy="commRole", cascade=CascadeType.MERGE)
	private Set commRolePermission = new HashSet();

	@OneToMany(targetEntity=com.mmd.mssp.domain.CommUser.class, mappedBy="commRole", cascade=CascadeType.MERGE)
	private Set commUser = new HashSet();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Basic
	@Column(name="is_delete", columnDefinition="INT")
	private Integer isDelete;

	@Basic
	@Column(name="role_name")
	private String roleName;
	
	@Basic
	@Column(name = "createtime", columnDefinition = "TIMESTAMP")
	@Temporal(javax.persistence.TemporalType.TIMESTAMP)
	private Date createtime;


	public CommRole() {
	}

	public CommRole(Integer id) {
		this.id = id;
	}

	public Set getCommRolePermission() {
		return commRolePermission;
	}

	public void setCommRolePermission(Set commRolePermission) {
		this.commRolePermission = commRolePermission;
	}

	public Set getCommUser() {
		return commUser;
	}

	public void setCommUser(Set commUser) {
		this.commUser = commUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
}
