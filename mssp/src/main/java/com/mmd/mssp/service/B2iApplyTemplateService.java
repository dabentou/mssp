package com.mmd.mssp.service;



import java.util.List;

import com.mmd.mssp.domain.B2iApplyTemplate;
import com.mmd.mssp.domain.vo.Paging;

public interface B2iApplyTemplateService {

	/**
	 * 根据id查询
	 * @return
	 */
	public B2iApplyTemplate findById(Integer id,Boolean isDelete);
	
	/**
	 * 申请模板保存
	 * @param applyTemplate
	 * @return
	 */
	public B2iApplyTemplate save(B2iApplyTemplate applyTemplate);
	
	
	/**
	 * 列出未删除项
	 * @param isDelete
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<B2iApplyTemplate> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	/**
	 * 列出所有未删除的申请模板
	 * @param isDelete
	 * @return
	 */
	List<B2iApplyTemplate > listByIsDelete(boolean isDelete);
	
}
