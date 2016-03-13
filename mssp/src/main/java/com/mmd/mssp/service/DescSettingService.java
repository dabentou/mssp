package com.mmd.mssp.service;


import java.util.List;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.DescSetting;

public interface DescSettingService {
	
	public void save(List<DescSetting> descSettings);
	
	public List<DescSetting> listAll();
	
	public void deleteAll();
	
	public DescSetting findById(Integer id);
	
	public DescSetting findByProvince(CommProvince province);
}
