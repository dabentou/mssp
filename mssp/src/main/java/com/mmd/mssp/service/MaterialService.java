package com.mmd.mssp.service;


import java.util.List;

import com.mmd.mssp.domain.Material;
import com.mmd.mssp.domain.vo.Paging;

public interface MaterialService {

	/**
	 * 根据id查询未被删除的物料
	 * @param id
	 * @param isDelete
	 * @return
	 */
	public Material findByIdAndIsDelete(Integer id,Boolean isDelete);
	
	public Material save(Material material);
	
	Paging<Material> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public void delete(Material material);
	
	public List<Material> listByIsDelete(boolean isDelete);
}
