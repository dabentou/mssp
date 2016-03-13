package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.PsiReason;

@Repository
public interface PsiReasonRepository extends CrudRepository<PsiReason, Long> {

	PsiReason getById(Integer id);
	
	@Query("select a from PsiReason as a where a.isDelete = (?1) order by  a.createtime desc,a.reason asc")
	Page<PsiReason> findByIsDelete(int i, Pageable page);
	
	@Query("select a from PsiReason as a where a.isDelete = (?1) order by  a.createtime desc,a.reason asc")
	List<PsiReason> listAll(int i);
}
