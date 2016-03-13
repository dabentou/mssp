package com.mmd.mssp.service;


import com.mmd.mssp.domain.IretailPropagandaType;
import com.mmd.mssp.domain.vo.Paging;

public interface IretailPropagandaTypeService {

	/**
	 * 根据id查询未被删除的物料
	 * @param id
	 * @param isDelete
	 * @return
	 */
	public IretailPropagandaType findByIdAndIsDelete(Integer id,Boolean isDelete);
	
	public IretailPropagandaType save(IretailPropagandaType itPropagandaType);
	
	Paging<IretailPropagandaType> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public void delete(IretailPropagandaType itPropagandaType);
}
