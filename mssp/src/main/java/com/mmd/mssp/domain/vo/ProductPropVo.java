package com.mmd.mssp.domain.vo;

/**
 * @ClassName: ProductPropVo
 * @Package com.mmd.mssp.domain.vo
 * @Description: TODO
 * @author sheng.tian
 * @date 2016-1-7
 * @version V1.1 
 */
public class ProductPropVo {
	
	private String cpt;//竞品型号
	
	private String cpp;//竞品价格
	
	private String cpn;//竞品数量

	public String getCpt() {
		return cpt;
	}

	public void setCpt(String cpt) {
		this.cpt = cpt;
	}

	public String getCpp() {
		return cpp;
	}

	public void setCpp(String cpp) {
		this.cpp = cpp;
	}

	public String getCpn() {
		return cpn;
	}

	public void setCpn(String cpn) {
		this.cpn = cpn;
	}
}
