package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.vo.Paging;

public interface CommSeriesService {

	Paging<CommSeries> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public CommSeries save(CommSeries series);
	public CommSeries getCommSeriesById(Integer id);
	public CommSeries getCommSeriesByName(String name);
	
	List<CommSeries> listAll(boolean isDelete);
}
