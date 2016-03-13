package com.mmd.mssp.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;


public interface CommService {

	/**
	* @author: sheng.tian
	* @Description: 通过城市查询代理商
	* @param @param city
	* @param @return
	* @return CommAgent
	* @throws
	*/
	CommAgent findAgentByCity(String city);

	/**
	* @author: sheng.tian
	* @Description: 产寻所有产品
	* @param @return
	* @return List<Product>
	* @throws
	*/
	List<Product> findProductList(int isDelele);
	
	List<Product> findProductByChannelType(Integer[] channelType);
	
	List<Product> findSampleProductList();
	
	/**
	* @author: sheng.tian
	* @Description: 产寻所有城市
	* @param @return
	* @return List<Product>
	* @throws
	*/
	List<Product> findCityList(int isDelele);

	/**
	* @author: sheng.tian
	* @Description: 获取当前代理商
	* @param @param request
	* @param @return
	* @return CommAgent
	* @throws
	*/
	CommAgent findCurrentAgent(HttpServletRequest request);
	
	/**
	 * 获取当前代理商列表
	 * @param request
	 * @return
	 */
	List<CommAgent> listCurrentAgent(HttpServletRequest request);
	
	/**
	 * 获取当前城市列表
	 * @param request
	 * @return
	 */
	List<CommCity> listCurrentCity(HttpServletRequest request);
	
	/**
	 * 获取当前省份列表
	 * @param request
	 * @return
	 */
	List<CommProvince> listCurrentProvince(HttpServletRequest request);

	/**
	* @author: sheng.tian
	* @Description: 通过型号名称查询型号
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Product findProductbyProductName(String key);
	/**
	* @author: sheng.tian
	* @Description: 给定一个日期，判断一个日期是不是工作日
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	
	CommAgent findAgentByProvince(CommProvince commProvince);
	
	boolean isWorkday(Date date);
	Date getFisrtDayAfter(Date date);//从此日期之后的第一个工作日
	Date getFisrtDayBefore(Date date);//从此日期之前的第一个工作日
	/**
	* @author: sheng.tian
	* @Description: 获取当月的第一天，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getCurrentMonthFisrtDay();
	/**
	* @author: sheng.tian
	* @Description: 获取当月的最后一天，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getCurrentMonthLastDay();
	/**
	* @author: sheng.tian
	* @Description: 获取上个月的最后一天，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getLastMonthLastDay();
	/**
	* @author: sheng.tian
	* @Description: 获取上个月的第一天，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getLastMonthFirstDay();
	/**
	* @author: sheng.tian
	* @Description: 获取上上个月的最后一天，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getLastLastMonthLastDay();
	/**
	* @author: sheng.tian
	* @Description: 获取上个工作日，考虑到了节假日和调休
	* @param @param key
	* @param @return
	* @return Product
	* @throws
	*/
	Date getYestday();
	
	/**
	* @author: xinyi.zhang
	* @Description: 判断当天是否在指定日期范围之内
	* @param days 从当月第一天到指定日期的天数
	* @return
	*/
	boolean isDateBefore(int days);

	/**
	* @author: sheng.tian
	* @Description:组装项目编号
	* @param @param findCurrentAgent
	* @param @return   
	* @return String   
	* @throws
	*/
	String getProjectCode(CommAgent findCurrentAgent,String code_type);

	/**
	* @author: sheng.tian
	* @Description:查询总代下的二级代理商
	* @param @param agent
	* @param @return   
	* @return List<CommAgent>   
	* @throws
	*/
	List<CommAgent> findSiByAgent(CommAgent agent);

	/**
	* @author: sheng.tian
	* @Description: 通过型号Id查询型号
	* @param @return   
	* @return Product   
	* @throws
	*/
	Product findProductById(Integer pId);
	
	/**
	* @author: sheng.tian
	* @Description: 获取当前用户
	* @param @return   
	* @return Product   
	* @throws
	*/
	CommUser findCurrentUserByRequest(HttpServletRequest request);

	/**
	* @author: sheng.tian
	* @Description: 查找模板类型
	* @param @param temp_B2B_PROJECT
	* @param @return   
	* @return ApproveTemplate   
	* @throws
	*/
	ApproveTemplate findTmpeType(String temp_type);

	/**
	* @author: sheng.tian
	* @Description: 查找一个模板下的第一步
	* @param @param temp
	* @param @return   
	* @return ProcessStep   
	* @throws
	*/
	ProcessStep findFisrtStep(ApproveTemplate temp);

	/**
	* @author: sheng.tian
	* @Description: Psi待办事宜总量
	* @param @param commRole
	* @param @return   
	* @return Object   
	* @throws
	*/
	Integer countPsiToDo(CommRole commRole);

	/**
	* @author: sheng.tian
	* @Description: b2b待办事宜总量
	* @param @param commAgents
	* @param @return   
	* @return Object   
	* @throws
	*/
	Integer countb2bToDo(List<CommAgent> commAgents,CommRole commRole);

	/**
	* @author: sheng.tian
	* @Description: b2待办事宜总量
	* @param @param commRole
	* @param @return   
	* @return Object   
	* @throws
	*/
	Integer countb2cToDo(CommRole commRole,List<CommAgent> agents);
	/**
	 * @author:咖喱信
	 * @Description: b2i待办事宜总量
	 * @param commRole
	 * @return
	 */
	Integer countb2iToDo(CommRole commRole,List<CommAgent> agents);
	
	String getPName(String type);//iretail中，获得项目申请的编号

	/**
	* @author: sheng.tian
	* @Description: Iretail待办事宜总量
	* @param @param commRole
	* @param @return   
	* @return Object   
	* @throws
	*/
	Integer countIretailToDo(CommRole commRole,List<CommProvince>  provinces);

	/**
	* @author: sheng.tian
	* @Description: TODO
	* @param @param channelType
	* @param @param nOT_DELETE
	* @param @return   
	* @return List<Product>   
	* @throws
	*/
	List<Product> findProductListByChannelType(String channelType,int nOT_DELETE);
}
