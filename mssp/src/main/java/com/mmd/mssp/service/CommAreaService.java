package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.vo.Paging;

public interface CommAreaService {

	Paging<CommArea> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public CommArea save(CommArea area);
	public CommArea getCommAreaById(Integer id);
	public CommArea getCommAreaByAreaName(String areaName);
	
	List<CommArea> listAll(boolean isDelete);
}
