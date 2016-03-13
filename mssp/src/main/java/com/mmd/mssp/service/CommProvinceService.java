package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.vo.Paging;

public interface CommProvinceService {

	Paging<CommProvince> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public CommProvince getCommProvinceById(Integer id);
	
	public CommProvince getCommProvinceByProvinceName(String provinceName);
	
	public CommProvince save(CommProvince commProvince);
	
	public List<CommProvince> listAll();
	
}
