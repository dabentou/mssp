package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.SampleInOutLog;

public interface SampleInOutLogService {
	public void save(List<SampleInOutLog> sampleInOutLog);
	
	List<SampleInOutLog> findByProjectId(int id);
	
	public SampleInOutLog save(SampleInOutLog sampleInOutLog);
}
