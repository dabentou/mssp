package com.mmd.mssp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCustomer;

@Repository
public interface CommCustomerRepository extends CrudRepository<CommCustomer, Integer> {
	/**
	 * 查找改代理商的所有客户
	 * @param agent
	 * @return
	 */
	@Query("select a from CommCustomer as a where a.agent = (?1) and a.type=?2 order by a.id desc,a.name asc")
	List<CommCustomer> findByAgentAndType(CommAgent agent,int type);
	
	CommCustomer getById(Integer id);

}
