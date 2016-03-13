package com.mmd.mssp.service;

import java.util.List;

import com.mmd.mssp.domain.PsiReason;
import com.mmd.mssp.domain.vo.Paging;

public interface PsiReasonService {

	Paging<PsiReason> findByIsDelete(boolean isDelete,Integer page, Integer size);
	
	public PsiReason save(PsiReason psiReason);
	public PsiReason getPsiReasonById(Integer id);
	
	List<PsiReason> listAll(boolean isDelete);
}
