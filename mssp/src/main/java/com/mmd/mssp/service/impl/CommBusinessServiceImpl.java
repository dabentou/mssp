package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.CommAreaRepository;
import com.mmd.mssp.repository.CommBusinessRepository;
import com.mmd.mssp.service.CommBusinessService;

@Service
public class CommBusinessServiceImpl implements CommBusinessService {

	@Resource
	CommBusinessRepository businessRepository;
	
	@Override
	public Paging<CommBusiness> findAll(Integer page, Integer size) {
		Paging<CommBusiness> paging = new Paging<CommBusiness>(page, size);
		Page<CommBusiness> result = businessRepository.findAll(paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public CommBusiness getCommBusinessById(Integer id) {
		return businessRepository.getById(id);
	}

	@Override
	public CommBusiness save(CommBusiness commBusiness) {
		return businessRepository.save(commBusiness);
	}

	@Override
	public void delete(CommBusiness commBusiness) {
		businessRepository.delete(commBusiness);
	}

	@Override
	public CommBusiness getCommBusinessByName(String name) {
		return businessRepository.getByName(name);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommBusinessService#findBusinessList()
	 */
	@Override
	public List<CommBusiness> findBusinessList() {
		return (List<CommBusiness>) businessRepository.findAll();
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommBusinessService#findById(java.lang.Integer)
	 */
	@Override
	public CommBusiness findById(Integer bId) {
		// TODO Auto-generated method stub
		return businessRepository.findOne(bId);
	}

}
