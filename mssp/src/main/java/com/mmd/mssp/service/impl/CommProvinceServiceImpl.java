package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommProvinceRepository;
import com.mmd.mssp.service.CommProvinceService;

@Service
public class CommProvinceServiceImpl implements CommProvinceService{

	@Resource
	CommProvinceRepository commProvinceRepository;
	
	@Override
	public CommProvince save(CommProvince commProvince) {
		return commProvinceRepository.save(commProvince);
	}
	
	@Override
	public Paging<CommProvince> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<CommProvince> paging = new Paging<CommProvince>(page, size);
		Page<CommProvince> result = commProvinceRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		return paging;
	}
	
	@Override
	public CommProvince getCommProvinceById(Integer id) {
		return commProvinceRepository.getById(id);
	}
	
	@Override
	public CommProvince getCommProvinceByProvinceName(String provinceName) {
		return commProvinceRepository.getByProvinceName(provinceName);
	}

	@Override
	public List<CommProvince> listAll() {
		return commProvinceRepository.listAll();
	}
	
}
