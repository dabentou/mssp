package com.mmd.mssp.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellInDatasource;

@Repository
public interface PsiSellInDataSrouceRepository extends CrudRepository<PsiSellInDatasource, Long> {
	/**
	 * 根据型号、城市（代理商）、渠道、导入时间查询
	 * @param material
	 * @param city
	 * @param channelType
	 * @param date
	 * @return
	 */
	@Query("select a from PsiSellInDatasource a where a.material=?1 and a.city=?2 and a.channelType=?3 and a.createdon=?4")
	PsiSellInDatasource findByPacc(String material,String city,Integer channelType,Date date);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param currentMonthFisrtDay
	* @param @param date
	* @param @return   
	* @return List<PsiSellInDatasource>   
	* @throws
	*/
	@Query("select a from PsiSellInDatasource a where a.createdon>=?1 and a.createdon<?2 and a.channelType=?3")
	List<PsiSellInDatasource> findCurrentMonthDataList(Date currentMonthFisrtDay, Date date,Integer channelType);
}