package com.mmd.mssp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.mmd.mssp.comm.Constants;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommRole;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.UserCity;
import com.mmd.mssp.domain.UserProvince;
import com.mmd.mssp.domain.Vacation;
import com.mmd.mssp.repository.ApproveTemplateRepository;
import com.mmd.mssp.repository.B2BProjectRepository;
import com.mmd.mssp.repository.B2CProjectRepository;
import com.mmd.mssp.repository.B2IMeetingProjectRepository;
import com.mmd.mssp.repository.B2IProjectRepository;
import com.mmd.mssp.repository.B2ISponsorProjectRepository;
import com.mmd.mssp.repository.CommAgentRepository;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.IretailProjectRepository;
import com.mmd.mssp.repository.ProcessStepRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.PsiInventoryRepository;
import com.mmd.mssp.repository.PsiSellOutUpdateApplyLogRepository;
import com.mmd.mssp.repository.UserCityRepository;
import com.mmd.mssp.repository.UserProvinceRepository;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.util.DateUtils;

@Service
public class CommServiceImpl implements CommService {
	
	@Resource
	CommAgentRepository commAgentRepository;
	
	@Resource
	CommCityRepository  commCityRepository;
	
	@Resource
	ProductRepository productRepository;
	
	@Resource
	PsiInventoryRepository psiInventoryRepository;
	
	@Resource 
	VacationServiceImpl vacationServiceImpl;
	
	@Resource
	B2BProjectRepository b2BProjectRepository;
	
	@Resource
	B2CProjectRepository b2CProjectRepository;
	
	@Resource
	B2IProjectRepository b2IProjectRepository;
	
	@Resource
	B2ISponsorProjectRepository b2iSponsorProjectRepository;
	
	@Resource
	ApproveTemplateRepository approveTemplateRepository;
	
	@Resource
	ProcessStepRepository processStepRepository;
	
	@Resource
	PsiSellOutUpdateApplyLogRepository psiSellOutUpdateApplyLogRepository;
	
	@Resource
	IretailProjectRepository iretailProjectRepository;
	
	@Resource
	UserCityRepository userCityRepository;
	
	@Resource
	UserProvinceRepository userProvinceRepository;
	
	@Resource
	B2IMeetingProjectRepository b2iMeetingProjectRepository;
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findAgentByCity(java.lang.String)
	 */
	@Override
	public CommAgent findAgentByCity(String cityName) {
		CommCity  bean = commCityRepository.findCityByName(cityName,CommCity.NOT_DELETE);
		return commAgentRepository.findAgentByCityAndIRlevel(bean,CommAgent.IR_LEVEL_AGENT);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#queryProductList()
	 */
	@Override
	public List<Product> findProductList(int isDelete) {
		return productRepository.findAllProduct(isDelete);
	}

	@Override
	public List<Product> findSampleProductList() {
		// TODO Auto-generated method stub
		return productRepository.findSampleProduct();
	}
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#queryCityList(int)
	 */
	@Override
	public List<Product> findCityList(int isDelele) {
		return commCityRepository.findAllCity(isDelele);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#getCurrentAgent(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public CommAgent findCurrentAgent(HttpServletRequest request) {
		CommUser currentUser = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		return currentUser.getCommAgent();
	}
	
	@Override
	public List<CommAgent> listCurrentAgent(HttpServletRequest request) {
		List<CommCity> commCities = listCurrentCity(request);
		List<CommAgent> commAgents = new ArrayList<CommAgent>();
		for (CommCity commCity : commCities) {
			CommAgent commAgent = commAgentRepository.findAgentByCityAndIRlevel(commCity, CommAgent.IR_LEVEL_AGENT);
			if(commAgent!=null)
				commAgents.add(commAgent);
		}
		return commAgents;
	}

	@Override
	public List<CommCity> listCurrentCity(HttpServletRequest request) {
		CommUser currentUser = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		List<UserCity> userCities = userCityRepository.getUserCityByUser(currentUser);
		List<CommCity> commCities = new ArrayList<CommCity>();
		for (UserCity userCity : userCities) {
			commCities.add(userCity.getCity());
		}
		return commCities;
	}

	@Override
	public List<CommProvince> listCurrentProvince(HttpServletRequest request) {
		CommUser currentUser = (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
		List<UserProvince> userProvinces = userProvinceRepository.getUserProvinceByUser(currentUser);
		List<CommProvince> provinces = new ArrayList<CommProvince>();
		for (UserProvince userProvince : userProvinces) {
			provinces.add(userProvince.getProvince());
		}
		return provinces;
	}


	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findProductbyProductName(java.lang.String)
	 */
	@Override
	public Product findProductbyProductName(String key) {
		return productRepository.findProductByName(key, Product.NOT_DELETE);
	}
	
	@Override
	public boolean isWorkday(Date date) {//判断是不是工作日,工作日返回true
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance();
		Vacation vacation = vacationServiceImpl.getVacation(sdf.format(date));//判断是不是节假日
		if(vacation == null){ //vacation == null说明没有记录不是节假日
			ca.setTime(date);
			int weeks = ca.get(Calendar.DAY_OF_WEEK);
			if(weeks != 1&&weeks != 7){//也不是周六周日，说明是工作日
				return true;
			}else{//如果是周六周日的话，看有没有可能是周末调休的，即便周末也要上班，也是工作日
				Vacation vacations = vacationServiceImpl.getWorkday(sdf.format(date));
				if(vacations != null)
					return true;
			else
				return false;
			}
		}else{//有记录说明是节假日，则不是工作日
			return false;//
		}
	}
	
	//从此日期以后的第一个工作日
	@Override
	public Date getFisrtDayAfter(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		for(int i = 1;;i++){
			ca.add(Calendar.DAY_OF_MONTH, +1);
			if(isWorkday(ca.getTime())){
				return ca.getTime();
			}else{
				continue;
			}
		}
	}
	
	//从此日期之前的第一个工作日
	@Override
	public Date getFisrtDayBefore(Date date) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		for(int i = 1;;i++){
			ca.add(Calendar.DAY_OF_MONTH, -1);
			if(isWorkday(ca.getTime())){
				return ca.getTime();
			}else{
				continue;
			}
		}
	}
	
	@Override
	public Date getCurrentMonthFisrtDay() {//获取当月的第一天
		Date date = DateUtils.getCurrentMonthFisrtDay();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期加1
			return getFisrtDayAfter(date);
		}
	}
	
	@Override
	public Date getCurrentMonthLastDay() {
		Date date = DateUtils.getCurrentMonthLastDay();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期加1
			return getFisrtDayBefore(date);
		}
	}
	
	@Override
	public Date getLastMonthLastDay() {
		Date date = DateUtils.getLastMonthLastDay();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期减1
			return getFisrtDayBefore(date);
		}
	}
	
	@Override
	public Date getLastMonthFirstDay() {
		Date date = DateUtils.getLastMonthFirstDay();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期减1
			return getFisrtDayAfter(date);
		}
	}
	
	@Override
	public Date getLastLastMonthLastDay() {
		Date date = DateUtils.getLastLastMonthLastDay();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期减1
			return getFisrtDayBefore(date);
		}
	}
	
	@Override
	public Date getYestday() {
		Date date = DateUtils.getYestday();
		if(isWorkday(date)){//是工作日
			return date;
		}else{//不是工作日的话，日期减1
			return getFisrtDayBefore(date);
		}
	}

	@Override
	public boolean isDateBefore(int days) {
		Date date = getCurrentMonthFisrtDay();
		Calendar calendar=Calendar.getInstance();   
		calendar.setTime(date); 
		calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+days);
		return new Date().before(calendar.getTime());
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#getProjectCode(com.mmd.mssp.domain.CommAgent)
	 */
	@Override
	public String getProjectCode(CommAgent agent,String codeType){ 
		String projectCode = "";
		agent.getCommCity().getCityCode();
		projectCode+=agent.getCommCity().getCityCode();
		projectCode+="_";
		if(codeType.equalsIgnoreCase("b2c")){
			projectCode+=DateUtils.getCurrentMonthNo_b2c();
		}else{
			projectCode+=DateUtils.getCurrentMonthNo_();
		}
		projectCode+="_";
		int sum =0;
		if(codeType.equalsIgnoreCase("b2b")){//如果是B2B项目申请，就查b2b_project表
			sum=b2BProjectRepository.sumByAgent(agent.getId(),DateUtils.getCurrentMonthFisrtDay(),new Date());
			sum++;
		}
		if(codeType.equalsIgnoreCase("b2c")){//如果是B2C项目申请，就查b2c_project表
			sum=b2CProjectRepository.sumByAgent(agent.getId(),DateUtils.getCurrentMonthFisrtDay(),new Date());
			sum++;
		}
		if(codeType.equalsIgnoreCase("b2i")){//如果是B2I项目申请，就查b2i_project表
			sum=b2IProjectRepository.sumByAgent(agent.getId(),DateUtils.getCurrentMonthFisrtDay(),new Date());
			sum++;
		}
		
		if(sum<10){
			projectCode+="0"+sum;
		}else{
			projectCode+=""+sum;
		}
		return projectCode;
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findSiByAgent(com.mmd.mssp.domain.CommAgent)
	 */
	@Override
	public List<CommAgent> findSiByAgent(CommAgent agent) {
		// TODO Auto-generated method stub
		return commAgentRepository.findSiByAgent(agent.getId());
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findProductById(java.lang.Integer)
	 */
	@Override
	public Product findProductById(Integer pId) {
		return productRepository.findById(pId);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findCurrentUserByRequest(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public CommUser findCurrentUserByRequest(HttpServletRequest request) {
		return (CommUser)request.getSession().getAttribute(Constants.USER_KEY);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findTmpeType(java.lang.Integer)
	 */
	@Override
	public ApproveTemplate findTmpeType(String temp_type) {
		return approveTemplateRepository.findTempByType(temp_type);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findFisrtStep(com.mmd.mssp.domain.ApproveTemplate)
	 */
	@Override
	public ProcessStep findFisrtStep(ApproveTemplate temp) {
		return processStepRepository.findFisrtStep(temp);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#sumPsiToDo(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public Integer countPsiToDo(CommRole commRole) {
		return 0;//psi暂时不需要待办事宜
		/*return psiSellOutUpdateApplyLogRepository.countPsiToDo(commRole);*/
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#sumb2bToDo(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public Integer countb2bToDo(List<CommAgent> commAgents,CommRole commRole) {
		return b2BProjectRepository.countB2BToDo(commAgents,commRole);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#sumb2cToDo(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public Integer countb2cToDo(CommRole commRole,List<CommAgent> agents) {
		// TODO Auto-generated method stub
		return b2CProjectRepository.countb2cToDo(commRole,agents);
	}

	@Override
	public Integer countb2iToDo(CommRole commRole,List<CommAgent> agents) {
		Integer bi2ToDoCount=b2IProjectRepository.countb2iToDo(commRole,agents)+b2iSponsorProjectRepository.countb2iToDo(commRole,agents)+b2iMeetingProjectRepository.countb2iToDo(commRole, agents);
		return bi2ToDoCount;
	}
	
	@Override
	public String getPName(String type) {
		String name =type+String.valueOf((int)(Math.random()*10));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		name += sdf.format(new Date()); 
		name += String.valueOf((char)((int)(Math.random ()*26)+'A'));
		name += String.valueOf((char)((int)(Math.random ()*26)+'A'));
		for(int i=1;i<7;i++){
			name += String.valueOf((int)(Math.random()*10));
		}
		return name;
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#countIretailToDo(com.mmd.mssp.domain.CommRole)
	 */
	@Override
	public Integer countIretailToDo(CommRole commRole,List<CommProvince>  provinces) {
		return iretailProjectRepository.countIretailToDo(commRole,provinces);
	}
	
	@Override
	public CommAgent findAgentByProvince(CommProvince commProvince) {
		return commAgentRepository.findAgentByProvince(commProvince);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.CommService#findProductListByChannelType(java.lang.String, int)
	 */
	@Override
	public List<Product> findProductListByChannelType(String channelType,int nOT_DELETE) {
		/*return commAgentRepository.findProductByChannelType(Product.getSellTypeByChannlType(channelType),);*/
		return null;
	}

	@Override
	public List<Product> findProductByChannelType(Integer[] channelType) {
		// TODO Auto-generated method stub
		return productRepository.findProductByChannelType(channelType);
	}
	
	

}
