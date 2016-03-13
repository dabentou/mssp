package com.mmd.mssp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mmd.mssp.comm.ApiWarp;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.DescSetting;
import com.mmd.mssp.domain.IretailBudget;
import com.mmd.mssp.domain.IretailBudgetLog;
import com.mmd.mssp.domain.IretailMarket;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.IretailPropagandaType;
import com.mmd.mssp.domain.IretailStoreLevel;
import com.mmd.mssp.domain.Material;
import com.mmd.mssp.domain.vo.IretailBudgetLogDomain;
import com.mmd.mssp.domain.vo.IretailBudgetYears;
import com.mmd.mssp.domain.vo.ItetailBudgetTotal;
import com.mmd.mssp.domain.vo.PDFFormat;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommAgentService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommProvinceService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.DescSettingService;
import com.mmd.mssp.service.IretailBudgetLogService;
import com.mmd.mssp.service.IretailBudgetService;
import com.mmd.mssp.service.IretailMarketService;
import com.mmd.mssp.service.IretailProjectService;
import com.mmd.mssp.service.IretailPropagandaTypeService;
import com.mmd.mssp.service.IretailStoreLevelService;
import com.mmd.mssp.service.MaterialService;
import com.mmd.mssp.service.PDFService;

@Controller
public class IretailAdminController {

	private static final int PAGE_SIZE = 50;
	private static final int IS_DESC = 1;//可以装修
	private static final int IS_NOT_DESC = 0;//不可装修
	
	@Resource
	IretailMarketService iretailMarketService;
	@Resource
	CommCityService cityService;
	@Resource
	IretailBudgetService iretailBudgetService;
	
	@Resource
	IretailBudgetLogService iretailBudgetLogService;
	
	@Resource
	CommService  commService;
	
	@Resource
	CommProvinceService commProvinceService;
	
	@Resource
	MaterialService materialService;
	
	@Resource
	IretailPropagandaTypeService iretailPropagandaTypeService;
	
	@Resource
	IretailStoreLevelService iretailStoreLevelService;
	
	@Resource
	IretailProjectService iretailProjectService;
	
	@Resource
	CommAgentService commAgentService;
	
	@Resource
	DescSettingService descSettingService;
	
	@Resource
	PDFService pdfService;
	
	@Resource(name = "comentNotePDFFormat")
	private PDFFormat comentNotePDFFormat;
	
	/**
	 * 卖场列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/market/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<IretailMarket> paging = iretailMarketService.listAll(page, PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/iretail/market/market.jsp";
	}
	
	/**
	 * 物料列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/material/list.html")
	public String materialList(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<Material> paging = materialService.findByIsDelete(Boolean.FALSE, page, PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/iretail/material/material.jsp";
	}
	
	/**
	 * 宣传品类型列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/propagandatype/list.html")
	public String propagandaTypeList(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<IretailPropagandaType> paging = iretailPropagandaTypeService.findByIsDelete(Boolean.FALSE, page, PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/iretail/propagandatype/propaganda_type.jsp";
	}
	
	/**
	 * 店面级别列表
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/storeLevel/list.html")
	public String storeLevelList(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<IretailStoreLevel> paging = iretailStoreLevelService.findByIsDelete(Boolean.FALSE, page, PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		return "/admin/iretail/storelevel/store_level.jsp";
	}
	
	/**
	 * PPN单据字段详情
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/iretail/ppn/detail.html")
	public String SearchPPN(HttpServletRequest request){
		List<Material> listMaterials = materialService.listByIsDelete(Boolean.FALSE);
		List<CommAgent> storeList = commAgentService.findByIrLevelAndIsDelete(6, Boolean.FALSE);
		request.setAttribute("storeList", storeList);
		request.setAttribute("listMaterials", listMaterials);
		return "/admin/iretail/ppn/detail.jsp";
	}
	
	/**
	 * 装修设置维护
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/descsetting/list.html")
	public String descSettingList(HttpServletRequest request){
		List<CommProvince> provinces = commProvinceService.listAll();
		List<DescSetting> descSettings = descSettingService.listAll();
		request.setAttribute("descSettings", descSettings);
		request.setAttribute("provinces", provinces);
		return "/admin/iretail/descsetting/list.jsp";
	}
	
	/**
	 * 卖场新增
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/market/new.html")
	public String newarea(HttpServletRequest request, @RequestParam(required=false) Integer page){
		boolean isNew = true;
		List<CommCity> cityList = cityService.listAllCity(false);
		request.setAttribute("isNew", isNew);
		request.setAttribute("cityList", cityList);
		return "/admin/iretail/market/market_detail.jsp";
		
	}
	
	/**
	 * 物料新增
	 * @param request
	 * @param page
	 * @return
	 */
	@RequestMapping("/admin/iretail/material/new.html")
	public String addMaterial(HttpServletRequest request, @RequestParam(required=false) Integer page){
		boolean isNew = true;
		request.setAttribute("isNew", isNew);
		return "/admin/iretail/material/material_detail.jsp";
		
	}
	
	/**
	 * 宣传品类型新增
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/iretail/propagandatype/new.html")
	public String addPropagandaType(HttpServletRequest request){
		boolean isNew = true;
		request.setAttribute("isNew", isNew);
		return "/admin/iretail/propagandatype/propaganda_type_detail.jsp";
		
	}
	
	/**
	 * 店面级别新增
	 * @param request
	 * @return
	 */
	@RequestMapping("/admin/iretail/storeLevel/new.html")
	public String addstoreLevel(HttpServletRequest request){
		boolean isNew = true;
		request.setAttribute("isNew", isNew);
		return "/admin/iretail/storelevel/store_level_detail.jsp";
		
	}
	
	/**
	 * 卖场保存
	 * @param id
	 * @param marketName
	 * @param cityId
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/market/save",method=RequestMethod.POST)
	public String save(Integer id,String marketName,Integer cityId){
		IretailMarket market = new IretailMarket();
		CommCity city = cityService.getCommCityById(cityId);
		if(id!=null){
			market = iretailMarketService.getByMarketId(id);
		}
		market.setMarketName(marketName);
		market.setCity(city);
		iretailMarketService.save(market);
		
		return "redirect:/admin/iretail/market/list.html";
	}
	
	
	/**
	 * 物料保存
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/material/save",method=RequestMethod.POST)
	public String saveMaterial(Integer id,String name){
		Material material = materialService.findByIdAndIsDelete(id, Boolean.FALSE);
		if(material==null){
			material = new Material();
			material.setCreatetime(new Date());
			material.setIsDelete(0);
		}
		material.setName(name);
		materialService.save(material);
		return "redirect:/admin/iretail/material/list.html";
	}
	
	/**
	 * 宣传品类型保存
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/propagandatype/save",method=RequestMethod.POST)
	public String savePropagandaType(Integer id,String type){
		IretailPropagandaType iretailPropagandaType = iretailPropagandaTypeService.findByIdAndIsDelete(id, Boolean.FALSE);
		if(iretailPropagandaType==null){
			iretailPropagandaType = new IretailPropagandaType();
			iretailPropagandaType.setCreatetime(new Date());
			iretailPropagandaType.setIsDelete(0);
		}
		iretailPropagandaType.setType(type);
		iretailPropagandaTypeService.save(iretailPropagandaType);
		return "redirect:/admin/iretail/propagandatype/list.html";
	}
	
	/**
	 * 店面级别保存
	 * @param id
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/storeLevel/save",method=RequestMethod.POST)
	public String savestoreLevel(IretailStoreLevel iretailStoreLevel){
		IretailStoreLevel orgin = iretailStoreLevelService.findByIdAndIsDelete(iretailStoreLevel.getId(), Boolean.FALSE);
		if(orgin==null){
			orgin = new IretailStoreLevel();
			orgin.setCreatetime(new Date());
			orgin.setIsDelete(0);
		}
		org.springframework.beans.BeanUtils.copyProperties(iretailStoreLevel, orgin, "createtime","isDelete");
//		orgin.setCreatetime(iretailStoreLevel.getCreatetime());
//		orgin.setIsDelete(iretailStoreLevel.getIsDelete());
		iretailStoreLevelService.save(orgin);
		return "redirect:/admin/iretail/storeLevel/list.html";
	}
	
	@RequestMapping(value="/admin/iretail/descsetting/save",method=RequestMethod.POST)
	@ResponseBody
	public String saveDescSetting(HttpServletRequest request,String provinceIds){
		ApiWarp apiWarp = new ApiWarp();
		if("".equals(provinceIds)){
			descSettingService.deleteAll();
			return apiWarp.putData("保存成功").toJsonString();
		}
		String[] provinceArr = provinceIds.split(",");
		List<DescSetting> descSettings = new ArrayList<DescSetting>();
		for(int i=0; i<provinceArr.length; i++){
			DescSetting dSetting = new DescSetting();
			CommProvince province = commProvinceService.getCommProvinceById(Integer.parseInt(provinceArr[i]));
			dSetting.setProvince(province);
			dSetting.setIs_desc(IretailAdminController.IS_DESC);
			descSettings.add(dSetting);
		}
		descSettingService.save(descSettings);
		return apiWarp.putData("保存成功").toJsonString();
	}
	
	/**
	 * 卖场修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/market/edit-{id}")
	public String areaEdit(HttpServletRequest request,@PathVariable Integer id){
		boolean isNew = false;
		List<CommCity> cityList = cityService.listAllCity(false);
		IretailMarket market = iretailMarketService.getByMarketId(id);
		request.setAttribute("isNew", isNew);
		request.setAttribute("cityList", cityList);
		request.setAttribute("market", market);
		return "/admin/iretail/market/market_detail.jsp";
	}
	
	/**
	 * 物料修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/material/edit-{id}")
	public String materialEdit(HttpServletRequest request,@PathVariable Integer id){
		boolean isNew = false;
		Material material = materialService.findByIdAndIsDelete(id, Boolean.FALSE);
		request.setAttribute("isNew", isNew);
		request.setAttribute("material", material);
		return "/admin/iretail/material/material_detail.jsp";
	}
	
	/**
	 * 宣传品类型修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/propagandatype/edit-{id}")
	public String propagandaTypeEdit(HttpServletRequest request,@PathVariable Integer id){
		boolean isNew = false;
		IretailPropagandaType iretailPropagandaType = iretailPropagandaTypeService.findByIdAndIsDelete(id, Boolean.FALSE);
		request.setAttribute("isNew", isNew);
		request.setAttribute("iretailPropagandaType", iretailPropagandaType);
		return "/admin/iretail/propagandatype/propaganda_type_detail.jsp";
	}
	
	/**
	 * 店面级别修改
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/storeLevel/edit-{id}")
	public String storeLevelEdit(HttpServletRequest request,@PathVariable Integer id){
		boolean isNew = false;
		IretailStoreLevel iretailStoreLevel = iretailStoreLevelService.findByIdAndIsDelete(id, Boolean.FALSE);
		request.setAttribute("isNew", isNew);
		request.setAttribute("iretailStoreLevel", iretailStoreLevel);
		return "/admin/iretail/storelevel/store_level_detail.jsp";
	}
	
	/**
	 * R01申请信息修改
	 * @param request
	 * @param ppn
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/ppn/projectEdit",method=RequestMethod.POST)
	public String projectEdit(HttpServletRequest request,String ppn){
		IretailProject iretailProject = iretailProjectService.findByPPNAndIType(ppn, "R01");
		String applyTheme = request.getParameter("applyTheme");
		BigDecimal applyPrice = request.getParameter("applyPrice")==null?new BigDecimal(0):new BigDecimal(request.getParameter("applyPrice"));
		String approveEmail = request.getParameter("approveEmail");
		String applyContent = getIretailParam(request);
		iretailProject.setApplyTheme(applyTheme);
		iretailProject.setApplyPrice(applyPrice);
		iretailProject.setApproveEmail(approveEmail);
		iretailProject.setApplyContent(applyContent);
		iretailProjectService.save(iretailProject);
		request.setAttribute("ppn", ppn);
		return "redirect:/admin/iretail/ppn/detail.html";
	}
	
	/**
	 * 卖场删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/market/delete")
	public String delete(Integer id){
		IretailMarket market = iretailMarketService.getByMarketId(id);
		iretailMarketService.delete(market);
	
		return "redirect:/admin/iretail/market/list.html";
	}
	
	@RequestMapping("/admin/iretail/budget/maintain.html")
	public String budgetMaintain(HttpServletRequest request,String errorMsg){
		List<IretailBudgetYears> list = iretailBudgetService.selectYearAndQuarter();
		List<Integer> years = new ArrayList<Integer>();
		List<Integer> months = new ArrayList<Integer>();
		/*for(IretailBudgetYears iretailBudgetYears:list){
			Integer a = iretailBudgetYears.getYear();
			if(!(years.contains(a))){
				years.add(a);
			}
		}*/
		for(int i=2015;i<2021;i++){
			years.add(i);
		}
		for(int j=1;j<13;j++){
			months.add(j);
		}
		request.setAttribute("list", list);
		request.setAttribute("years", years);
		request.setAttribute("months", months);
		request.setAttribute("errorMsg", errorMsg);
		return "/admin/iretail/budget/budget_maintain.jsp";
	}
	
	@RequestMapping("/admin/iretail/budget/search-{year}-{quarter}")
	public String budgetSearch(HttpServletRequest request,@PathVariable Integer year,@PathVariable Integer quarter){
		List<IretailBudgetYears> lists = iretailBudgetService.selectYearAndQuarter();
		List<IretailBudgetYears> list = new ArrayList<IretailBudgetYears>();
		List<Integer> years = new ArrayList<Integer>();
		for(IretailBudgetYears iretailBudgetYears:lists){
			Integer a = iretailBudgetYears.getYear();
			if(!(years.contains(a))){
				years.add(a);
			}
		}
		for(IretailBudgetYears iretailBudgetYears:lists){
			if(iretailBudgetYears.getYear() == year && iretailBudgetYears.getQuarter() == quarter){
				list.add(iretailBudgetYears);
			}
		}
		request.setAttribute("list", list);
		request.setAttribute("years", years);
		return "/admin/iretail/budget/budget_maintain.jsp";
	}
	
	@RequestMapping("/admin/iretail/budget/view-{year}-{quarter}")
	public String budgetView(HttpServletRequest request,@PathVariable Integer year,@PathVariable Integer quarter){
		List<IretailBudget> list = iretailBudgetService.findByYearsAndQuarter(year,quarter);
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("quarter", quarter);
		return "/admin/iretail/budget/budget_view.jsp";
	}
	
	@RequestMapping("/admin/iretail/budget/viewTotal-{year}-{quarter}")
	public String budgetViewTotal(HttpServletRequest request,@PathVariable Integer year,@PathVariable Integer quarter) throws Exception{
//		List<IretailBudget> list = iretailBudgetService.findByYearsAndQuarter(year,quarter);
//		for(IretailBudget domain : list){
//			CommProvince  province = domain.getCommonProvince();
//			BudgetTotal big= iretailBudgetService.findSumByYearsAndQuarterAndProvince(year,quarter,province);
//			for(int i=2;i<13;i++){
//				String nums = Integer.toString(i);
//				if(i < 10){
//					nums = "0" + nums;
//				}
//				BeanUtils.setProperty(domain,"r"+nums,BeanUtils.getProperty(big, "r"+nums+"Total"));
//			}
//		}
//		request.setAttribute("list", list);
//		request.setAttribute("year", year);
//		request.setAttribute("quarter", quarter);
		
		
		
		
		
		
		/*List<IretailBudget> list = iretailBudgetService.findByYearsAndQuarter(year,quarter);
		newDemo de
		int sumR2=0;
		for(int i = 0 ;i<list.size();i++){
			sumR2+=list.get(i).getR02();
		}
		de.set(sumR2
				
				
				
		List<newDemo> d*/
		List<ItetailBudgetTotal> list = new ArrayList<ItetailBudgetTotal>();
		List<IretailBudget> lists = iretailBudgetService.findByYearsAndQuarters(year,quarter);
		List<CommProvince> pro = new ArrayList<CommProvince>();
		for(IretailBudget i : lists){
			if(!(pro.contains(i.getCommonProvince()))){
				pro.add(i.getCommonProvince());
			}
		}
		for(CommProvince p: pro){
			ItetailBudgetTotal total = new ItetailBudgetTotal();
			total.setYear(year);
			total.setMonth(quarter);
			total.setCommonProvince(p);
			for(int i=0;i<lists.size();i++){
				if(lists.get(i).getCommonProvince() == p){
					total.setR02((total.getR02()).add(lists.get(i).getR02()));
					total.setR03(total.getR03().add(lists.get(i).getR03()));
					total.setR04(total.getR04().add(lists.get(i).getR04()));
					total.setR05(total.getR05().add(lists.get(i).getR05()));
					total.setR06(total.getR06().add(lists.get(i).getR06()));
					total.setR07(total.getR07().add(lists.get(i).getR07()));
					total.setR08(total.getR08().add(lists.get(i).getR08()));
					total.setR09(total.getR09().add(lists.get(i).getR09()));
					total.setR10(total.getR10().add(lists.get(i).getR10()));
					total.setR11(total.getR11().add(lists.get(i).getR11()));
					total.setR12(total.getR12().add(lists.get(i).getR12()));
				}
			}
			list.add(total);
		}
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("quarter", quarter);
		
		return "/admin/iretail/budget/budget_view.jsp";
	}
	
	@RequestMapping(value="/admin/iretail/budget/addBudget",method=RequestMethod.POST)
	@ResponseBody
	public String addBudget(HttpServletRequest request,int year,int quarter, IretailBudgetLogDomain iretailBudgetLogDomain) throws Exception{
		ApiWarp apiWarp = new ApiWarp();	
		
		CommProvince province = commProvinceService.getCommProvinceById(Integer.parseInt(BeanUtils.getProperty(iretailBudgetLogDomain, "provinceID")));
		IretailBudget iretailBudget = iretailBudgetService.findByYearsAndQuarterAndProvince(year, quarter,province);
		CommUser user = commService.findCurrentUserByRequest(request);
		List<IretailBudgetLog> list = new ArrayList<IretailBudgetLog>();
		for(int i=2;i<13;i++)
		{
			String num;
			if(i<10){
				num ="0"+Integer.toString(i);
			}else{
				num =Integer.toString(i);
			}
			if(BeanUtils.getProperty(iretailBudgetLogDomain, "r"+num+"oldVal") != null){
				BeanUtils.setProperty(iretailBudget, "r"+num, new BigDecimal(BeanUtils.getProperty(iretailBudgetLogDomain, "r"+num+"newVal")).setScale(1, BigDecimal.ROUND_HALF_UP));
				IretailBudgetLog  domain= new IretailBudgetLog();
				domain.setYear(year);
				domain.setQuarter(quarter);
				domain.setProvince(province);
				domain.setNewVolum(new BigDecimal(BeanUtils.getProperty(iretailBudgetLogDomain, "r"+num+"newVal")).setScale(1, BigDecimal.ROUND_HALF_UP));
				domain.setOldVolum(new BigDecimal(BeanUtils.getProperty(iretailBudgetLogDomain, "r"+num+"oldVal")).setScale(1, BigDecimal.ROUND_HALF_UP));
				domain.setType("R" + num);
				domain.setUser(user);
				list.add(domain);
			}
		}
		iretailBudgetService.save(iretailBudget);
		iretailBudgetLogService.save(list);
		return apiWarp.toJsonString();
	}
	
	@RequestMapping("/admin/iretail/budget/logView-{year}-{quarter}-{provinceId}")
	public String logView(HttpServletRequest request,@PathVariable Integer year,@PathVariable Integer quarter,@PathVariable Integer provinceId){
		CommProvince province = commProvinceService.getCommProvinceById(provinceId);
		List<IretailBudgetLog> list = iretailBudgetLogService.findByYearAndQuarterAndCommProvince(year, quarter, province);
		request.setAttribute("list", list);
		request.setAttribute("year", year);
		request.setAttribute("quarter", quarter);
		return "/admin/iretail/budget/log_view.jsp";
	}
	
	@RequestMapping(value="/admin/iretail/budget/import.html",method=RequestMethod.POST)
	public String SellInInput(HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr
				,@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		String errorMsg =  null;
		CommUser user = commService.findCurrentUserByRequest(request);
		int years = Integer.parseInt(request.getParameter("years"));
		int months = Integer.parseInt(request.getParameter("months"));
		try {
			String message=iretailBudgetService.budgetInput(file.getInputStream(),user,years,months);
//			iretailBudgetService.save(list);
			if(message==null || message.equals("")){
				errorMsg="IMPORT SUCCESS!";
			}else{
				errorMsg=message;
			}
			
		} catch (IOException e) {
			errorMsg="IMPORT ERROR";
			e.printStackTrace();
		}
		attr.addAttribute("errorMsg", errorMsg);
		return "redirect:/admin/iretail/budget/maintain.html";
	}
	/**
	 * 物料删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/material/delete")
	public String deleteMaterial(Integer id){
		Material material = materialService.findByIdAndIsDelete(id, Boolean.FALSE);
		if(material!=null){
			material.setIsDelete(1);
			materialService.save(material);
		}
		return "redirect:/admin/iretail/material/list.html";
	}
	
	/**
	 * 宣传品类型删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/propagandatype/delete")
	public String deletePropagandatype(Integer id){
		IretailPropagandaType iretailPropagandaType = iretailPropagandaTypeService.findByIdAndIsDelete(id, Boolean.FALSE);
		if(iretailPropagandaType!=null){
			iretailPropagandaType.setIsDelete(1);
			iretailPropagandaTypeService.save(iretailPropagandaType);
		}
		return "redirect:/admin/iretail/propagandatype/list.html";
	}
	
	/**
	 * 店面级别删除
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/iretail/storeLevel/delete")
	public String deleteStoreLevel(Integer id){
		IretailStoreLevel iretailStoreLevel = iretailStoreLevelService.findByIdAndIsDelete(id, Boolean.FALSE);
		if(iretailStoreLevel!=null){
			iretailStoreLevel.setIsDelete(1);
			iretailStoreLevelService.save(iretailStoreLevel);
		}
		return "redirect:/admin/iretail/storeLevel/list.html";
	}
	
	
	/**
	 * 根据ppn申请编号查询项目信息
	 * @param request
	 * @param ppn
	 * @return
	 */
	@RequestMapping(value="/admin/iretail/ppn/search",method=RequestMethod.POST)
	@ResponseBody
	public String getILevel(HttpServletRequest request,String ppn){
		ApiWarp apiWarp = new ApiWarp();
		IretailProject iretailProject = iretailProjectService.findByPPNAndIType(ppn, "R01");
		request.setAttribute("ppn", ppn);
		if(iretailProject==null){
			return apiWarp.addError("未查到匹配项目,项目编号为："+ppn).toJsonString();
		}
		return apiWarp.putData(iretailProject).toJsonString();
	}
	
	/**
	 * 获取R01零售店装修申请参数
	 * @param request
	 * @param chargesDetail
	 * @return
	 */
	private String getIretailParam(HttpServletRequest request){
		JSONObject applyContJs = new JSONObject();
		applyContJs.put("iName", request.getParameter("iName"));
		applyContJs.put("decorateLevel", request.getParameter("decorateLevel"));
		applyContJs.put("cityLevel", request.getParameter("cityLevel"));
		applyContJs.put("cityName", request.getParameter("cityName"));
		applyContJs.put("shopPlace", request.getParameter("shopPlace"));
		applyContJs.put("iLevel", request.getParameter("iLevel"));
		applyContJs.put("iLocation", request.getParameter("iLocation"));
		applyContJs.put("yqVolume", request.getParameter("yqVolume"));
		applyContJs.put("iAcreage", request.getParameter("iAcreage"));
		applyContJs.put("lsVolume", request.getParameter("lsVolume"));
		applyContJs.put("iPrincipal", request.getParameter("iPrincipal"));
		applyContJs.put("lsRate", request.getParameter("lsRate"));
		applyContJs.put("phone", request.getParameter("phone"));
		applyContJs.put("iTargetVolume", request.getParameter("iTargetVolume"));
		applyContJs.put("remark", request.getParameter("remark"));
		applyContJs.put("iRealPic", request.getParameter("iRealPic"));
		applyContJs.put("iResultPic", request.getParameter("iResultPic"));
		applyContJs.put("chargesDetail", request.getParameter("chargesDetail"));
		String[] materialNameArr = request.getParameterValues("materialName");
		String[] materialCountArr = request.getParameterValues("materialCount");
		JSONArray jsArray = new JSONArray();
		for(int i=0; i<materialNameArr.length; i++){
			if(materialCountArr[i]!=null){
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("material", materialNameArr[i]);
				jsonObject.put("count", materialCountArr[i]);
				jsArray.add(jsonObject);
			}
		}
		applyContJs.put("materialDetail", jsArray.toJSONString());
		return applyContJs.toJSONString();
	}
	
}
	



