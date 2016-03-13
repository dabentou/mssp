package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.ProductSeriesRepository;
import com.mmd.mssp.repository.CommSeriesRepository;
import com.mmd.mssp.service.CommSeriesService;

@Service
public class CommSeriesServiceImpl implements CommSeriesService {

	@Resource
	CommSeriesRepository seriesRepository;
	
	@Override
	public Paging<CommSeries> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		
		Paging<CommSeries> paging = new Paging<CommSeries>(page, size);
		Page<CommSeries> result = seriesRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommSeries save(CommSeries series) {
		return seriesRepository.save(series);
	}

	@Override
	public CommSeries getCommSeriesById(Integer id) {
		return seriesRepository.getById(id);
	}

	@Override
	public List<CommSeries> listAll(boolean isDelete) {
		return seriesRepository.listAll(isDelete?1:0);
	}

	@Override
	public CommSeries getCommSeriesByName(String name) {
		return seriesRepository.getByName(name);
	}

}
