package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.IretailApproveLog;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;

public interface IretailProjectService {

	public IretailProject save(IretailProject iretailProject);
	
	Paging<IretailProject> findIretailProjectByTypeAndAgent(String type,List<CommProvince> currentProvinces,Integer page, Integer size);//列表界面
	
	Paging<IretailProject> findIretailProjectByTypeAndAgent(String type,List<CommProvince> currentProvinces,Integer startStep,Integer endStep,Integer page, Integer size);//列表界面
	
	Paging<IretailProject> findIretailProjectBySearch(Integer processStepId, Integer provinceId,List<CommProvince> currentProvinces,String ppn,String type,Integer page, Integer size);//列表界面
	
	Paging<IretailProject> findIretailProjectBySearchByApply(Integer provinceId,List<CommProvince> currentProvinces,String ppn,String type,Integer page, Integer size);//列表界面
	
	Paging<IretailProject> findIretailProjectBySearchByApply1(Integer provinceId,List<CommProvince> currentProvinces,String ppn,String type,Integer page, Integer size);//po申请的请选择
	
	Paging<IretailProject> findIretailProjectBySearchByApply2(Integer provinceId,List<CommProvince> currentProvinces,String ppn,String type,Integer page, Integer size);//po核销的请选择
	
	Paging<IretailProject> findAllIretailProject(Integer page, Integer size);//列表界面
	
	IretailProject findProjectById(Integer id);//查看某个申请

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @return   
	* @return List<IretailApproveLog>   
	* @throws
	*/
	public List<IretailApproveLog> findApproveLogListByProject(IretailProject project);
	
	public IretailApproveLog findApproveLogListByProjectAndProcessStep(IretailProject project,ProcessStep step);


	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param project
	* @param @param log   
	* @return void   
	* @throws
	*/
	public void saveProjectAndApproveLog(IretailProject project,IretailApproveLog log);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param commRole
	* @param @return   
	* @return List<IretailProject>   
	* @throws
	*/
	public List<IretailProject> findProjectListByRole(CommRole commRole,List<CommProvince>  provinces);
	
	List<IretailProject> findIretailProjectByType(String type);
	
	List<Map> getMap(List<IretailProject> list,String type);
	
	/**
	 * 根据类型、申请日期、区域、状态（可多选）来查询项目
	 * @param type
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @param areaList
	 * @param processStepList
	 * @param page
	 * @param size
	 * @return
	 */
	Paging<IretailProject> findIretailProjectBySearch(Date applyTimeStart,Date applyTimeEnd,String type,String ppn,Integer[] areaId,Integer[] processStepId,Integer page, Integer size);
	
	List<IretailProject> findIretailProjectBySearchToExport(Date applyTimeStart,Date applyTimeEnd,String type,String ppn,Integer[] areaId,Integer[] processStepId);
	
	/**
	 * 根据申请类型和申请编号查询项目信息
	 * @param ppn
	 * @param iType(可为null)
	 * @return
	 */
	public IretailProject findByPPNAndIType(String ppn,String iType);
	
	/**
	 * 生成pdf
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	public String exportPDF(HttpServletRequest request,HttpServletResponse response,Integer id);
	
	/**
	 * 根据类型 和申请时间列出项目
	 * @param type
	 * @param applyDate
	 * @return
	 */
	List<IretailProject> listByTypeAndApplyDate(String type,Date applyDate,Date endDate);
}
