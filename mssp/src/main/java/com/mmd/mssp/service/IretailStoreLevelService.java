package com.mmd.mssp.service;


import java.util.List;

import com.mmd.mssp.domain.IretailStoreLevel;
import com.mmd.mssp.domain.vo.Paging;

public interface IretailStoreLevelService {

	/**
	 * 根据id查询未被删除的店面级别
	 * @param id
	 * @param isDelete
	 * @return
	 */
	public IretailStoreLevel findByIdAndIsDelete(Integer id,Boolean isDelete);
	
	public IretailStoreLevel save(IretailStoreLevel iretailStoreLevel);
	
	Paging<IretailStoreLevel> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public List<IretailStoreLevel> listByIsDelete(Boolean isDelete);
}
