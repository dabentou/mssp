package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.SampleInOutLog;
import com.mmd.mssp.repository.SampleInOutLogRepository;
import com.mmd.mssp.service.SampleInOutLogService;

@Service
public class SampleInOutLogServiceImpl implements SampleInOutLogService {

	@Resource
	SampleInOutLogRepository sampleInOutLogRepository;
	
	@Override
	public void save(List<SampleInOutLog> sampleInOutLog) {
		sampleInOutLogRepository.save(sampleInOutLog);
	}
	
	@Override
	public List<SampleInOutLog> findByProjectId(int id) {
		List<SampleInOutLog>  list = sampleInOutLogRepository.findByProjectId(id);
		return list;
	}

	@Override
	public SampleInOutLog save(SampleInOutLog sampleInOutLog) {
		return sampleInOutLogRepository.save(sampleInOutLog);
	}
}
