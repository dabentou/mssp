package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mmd.mssp.domain.B2BProject;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.SpecialApplyProduct;


/**
 * @ClassName: TestService
 * @Package com.mmd.mssp.service
 * @Description: TODO
 * @author sheng.tian
 * @date 2015-11-25
 * @version V1.1 
 */
public interface  SpecialApplyProductService {

	/**
	* @author: sheng.tian
	* @Description: 根据B2B项目查询特殊单型号list
	* @param @param projectId
	* @param @return   
	* @return List<SpecialApplyProduct>   
	* @throws
	*/
	List<SpecialApplyProduct> findSAPListByProject(B2BProject project);

	List<SpecialApplyProduct> listToExport(CommAgent agent,Date applyTimeStart,Date applyTimeEnd,Date approveTimeStart,Date approveTimeEnd,Integer[] processStepId,Integer[] cityId,Integer[] businessId,String pCode,String pName,Integer[] pTypeId);

	List<Map> getMapBySearch(List<SpecialApplyProduct> list);
	
	SpecialApplyProduct findSAPListById(Integer id);
	
	void save(SpecialApplyProduct specialApplyProduct);
}
