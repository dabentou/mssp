package com.mmd.mssp.service;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.IretailR01;
import com.mmd.mssp.domain.vo.Paging;

public interface IretailR01Service {

	public IretailR01 save(IretailR01 iretailR01);
	
	/**
	 * 根据代理商列出r01申请记录
	 * @param commAgent
	 * @return
	 */
	public Paging<IretailR01> findByAgent(CommAgent commAgent,Integer page,Integer size);
	
	/**
	 * 根据id查询R01申请信息
	 * @param id
	 * @return
	 */
	public IretailR01 findById(Integer id);
}
