package com.mmd.mssp.service;

import com.mmd.mssp.domain.Vacation;

public interface VacationService {
	
	Vacation getVacation(String dates);
	
	Vacation getWorkday(String dates);
	
}
