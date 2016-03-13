package com.mmd.mssp.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.DescSetting;
import com.mmd.mssp.repository.DescSettingRepository;
import com.mmd.mssp.service.DescSettingService;

@Service
public class DescSettingServiceImpl implements DescSettingService{
	
	@Resource
	DescSettingRepository descSettingRepository;

	@Override
	public void save(List<DescSetting> descSettings) {
		deleteAll();
		descSettingRepository.save(descSettings);
	}

	@Override
	public List<DescSetting> listAll() {
		return descSettingRepository.listAll();
	}

	@Override
	public void deleteAll() {
		descSettingRepository.deleteAll();
	}

	@Override
	public DescSetting findById(Integer id) {
		return descSettingRepository.findOne(id);
	}

	@Override
	public DescSetting findByProvince(CommProvince province) {
		return descSettingRepository.findByProvince(province);
	}

}
