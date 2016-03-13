package com.mmd.mssp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.Vacation;
import com.mmd.mssp.repository.VacationRepository;
import com.mmd.mssp.service.VacationService;

@Service
public class VacationServiceImpl implements VacationService{

	@Resource
	VacationRepository vacationRepository;
	
	@Override
	public Vacation getVacation(String dates) {//是节假日的，要放假的
		Vacation vacation = vacationRepository.findVacationById(dates);
		return vacation;
	}
	
	@Override
	public Vacation getWorkday(String dates) {//是周末，但是调休需要上班的
		Vacation vacation = vacationRepository.findWorkdayById(dates);
		return vacation;
	}
}
