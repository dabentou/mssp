package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.vo.Paging;

public interface PannelService {

	Paging<Pannel> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public Pannel save(Pannel pannel);
	public Pannel getPannelById(Integer id);
	public Pannel getPannelByName(String name);
	
	List<Pannel> listAll(boolean isDelete);
}
