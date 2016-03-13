package com.mmd.mssp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.PsiReason;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PsiReasonRepository;
import com.mmd.mssp.service.PsiReasonService;

@Service
public class PsiReasonServiceImpl implements PsiReasonService {

	@Resource
	PsiReasonRepository psiReasonRepository;
	
	@Override
	public Paging<PsiReason> findByIsDelete(boolean isDelete, Integer page,
			Integer size) {
		Paging<PsiReason> paging = new Paging<PsiReason>(page, size);
		Page<PsiReason> result = psiReasonRepository.findByIsDelete(isDelete?1:0, paging.toPage());
		paging.setResult(result);
		
		return paging;
	}

	@Override
	public PsiReason save(PsiReason psiReason) {
		return psiReasonRepository.save(psiReason);
	}

	@Override
	public PsiReason getPsiReasonById(Integer id) {
		return psiReasonRepository.getById(id);
	}

	@Override
	public List<PsiReason> listAll(boolean isDelete) {
		return psiReasonRepository.listAll(isDelete?1:0);
	}

}
