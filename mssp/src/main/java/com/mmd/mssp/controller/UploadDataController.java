package com.mmd.mssp.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.openjpa.jdbc.sql.InformixDictionary;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.PsiSellInEstimateByMonth;
import com.mmd.mssp.domain.PsiSellInEstimateByMonthInfo;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;
import com.mmd.mssp.domain.PsiSellInEstimateTemplateInfo;
import com.mmd.mssp.domain.vo.ApiWarp;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.repository.PsiSellInEstimateByMonthRepository;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateInfoRepository;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateRepository;
import com.mmd.mssp.service.CommAreaService;
import com.mmd.mssp.service.CommCityService;
import com.mmd.mssp.service.CommSeriesService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.PannelService;
import com.mmd.mssp.service.ProductPriceService;
import com.mmd.mssp.service.ProductSeriesService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiSellInEstimateByMonthInfoService;
import com.mmd.mssp.service.PsiSellInEstimateTemplateInfoService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class UploadDataController {

	private static final int IS_NOT_DELETE = 0;
	private static final int PAGE_SIZE = 50;

	@Resource
	ExcelService excelService;
	@Resource
	CommAreaService commAreaService;
	@Resource
	CommCityService commCityService;
	@Resource
	PannelService pannelService;
	@Resource
	CommSeriesService commSeriesService;
	@Resource
	ProductSeriesService productSeriesService;
	@Resource
	ProductService productService;
	@Resource
	PsiSellInEstimateTemplateRepository sellInEstimateTemplateRepository;
	@Resource
	ProductPriceService productPriceService;
	@Resource
	CommService commService;
	@Resource
	PsiSellInEstimateByMonthRepository sellInEstimateByMonthRepository;
	@Resource
	PsiSellInEstimateTemplateInfoService infoService;
	@Resource
	PsiSellInEstimateByMonthInfoService estimateByMonthInfoService;
	
	@RequestMapping(value="/admin/comm/uploaddata.html")
	public String saleCodeSearch(HttpServletRequest request){
		
		return "/admin/comm/uploaddata.jsp";

	}
	
	@RequestMapping(value="/admin/comm/upload/pannel",method=RequestMethod.POST)
	@ResponseBody
	public String uploadPannel(HttpServletRequest request,@RequestParam MultipartFile pannelFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getPannelByExcel(pannelFile.getInputStream());
			for(Map map :mapList){
				Pannel pannel = new Pannel();
				Date createtime = new Date();
				
				String pannelName = String.valueOf(map.get("pannelName"));
				pannel.setName(pannelName);
				pannel.setCreatetime(createtime);
				pannel.setIsDelete(IS_NOT_DELETE);
				
				pannelService.save(pannel);
			}
			
			//apiWarp.putData("codeList", codeList);
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/upload/series",method=RequestMethod.POST)
	@ResponseBody
	public String uploadSeries(HttpServletRequest request,@RequestParam MultipartFile seriesFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getSeriesByExcel(seriesFile.getInputStream());
			for(Map map :mapList){
				CommSeries commSeries = new CommSeries();
				Date createtime = new Date();
				
				String pannelName = String.valueOf(map.get("seriesName"));
				commSeries.setName(pannelName);
				commSeries.setCreatetime(createtime);
				commSeries.setIsDelete(IS_NOT_DELETE);
				
				commSeriesService.save(commSeries);
			}
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/upload/productSeries",method=RequestMethod.POST)
	@ResponseBody
	public String uploadProductSeries(HttpServletRequest request,@RequestParam MultipartFile productSeriesFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getProductSeriesByExcel(productSeriesFile.getInputStream());
			for(Map map :mapList){
				ProductSeries productSeries = new ProductSeries();
				Date createtime = new Date();
				
				String name = String.valueOf(map.get("name"));
				productSeries.setName(name);
				productSeries.setCreatetime(createtime);
				productSeries.setIsDelete(IS_NOT_DELETE);
				
				productSeriesService.save(productSeries);
			}
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/upload/product",method=RequestMethod.POST)
	@ResponseBody
	public String uploadProduct(HttpServletRequest request,@RequestParam MultipartFile productFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getProductByExcel(productFile.getInputStream());
			for(Map map :mapList){
				Product product = new Product();
				Date createtime = new Date();
				
				Integer status;
				String name = String.valueOf(map.get("name"));
				String statusName = String.valueOf(map.get("statusName"));
				String seriesName = String.valueOf(map.get("seriesName"));
				String pannelName = String.valueOf(map.get("pannelName"));
				String productSeriesName = String.valueOf(map.get("productSeriesName"));
				String size1 = String.valueOf(map.get("size1"));
				String size2 = String.valueOf(map.get("size2"));
				
				if("EOL".equals(statusName)){
					status = 0;
				}else{
					status = 1;
				}
				
				Pannel pannel = pannelService.getPannelByName(pannelName);
				CommSeries commSeries = commSeriesService.getCommSeriesByName(seriesName);
				ProductSeries productSeries = productSeriesService.getProductSeriesByName(productSeriesName);
				
				product.setName(name);
				product.setStatus(status);
				product.setPannel(pannel);
				product.setProductSeries(productSeries);
				product.setCommSeries(commSeries);
				product.setSize1(size1);
				product.setSize2(size2);
				product.setCreatetime(createtime);
				product.setIsDelete(IS_NOT_DELETE);
				
				productService.save(product);
			}
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/upload/area",method=RequestMethod.POST)
	@ResponseBody
	public String uploadArea(HttpServletRequest request,@RequestParam MultipartFile areaFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getAreaByExcel(areaFile.getInputStream());
			for(Map map :mapList){
				CommArea commArea = new CommArea();
				Date createtime = new Date();
				
				String name = String.valueOf(map.get("name"));
				commArea.setAreaName(name);
				commArea.setCreatetime(createtime);
				commArea.setIsDelete(IS_NOT_DELETE);
				
				commAreaService.save(commArea);
			}
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/comm/upload/city",method=RequestMethod.POST)
	@ResponseBody
	public String uploadCity(HttpServletRequest request,@RequestParam MultipartFile cityFile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		
		try {
			List<Map> mapList = excelService.getCityByExcel(cityFile.getInputStream());
			for(Map map :mapList){
				CommCity commCity = new CommCity();
				Date createtime = new Date();
				
				String cityName = String.valueOf(map.get("cityName"));
				String areaName = String.valueOf(map.get("areaName"));
				
				CommArea commArea = commAreaService.getCommAreaByAreaName(areaName);
				
				commCity.setCityName(cityName);
				commCity.setCommArea(commArea);
				commCity.setCreatetime(createtime);
				commCity.setIsDelete(IS_NOT_DELETE);
				
				commCityService.save(commCity);
			}
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/psi/uploadsell.html")
	public String uploadsell(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<PsiSellInEstimateTemplateInfo> paging = infoService.findAll(page, PAGE_SIZE);
		boolean isSubmit = false;
		if(sellInEstimateTemplateRepository.getCountByDateMonth(DateUtils.getCurrentMonthFisrtDay())>0){
			isSubmit = true;
		}
		request.setAttribute("pagelist", paging);
		request.setAttribute("isSubmit", isSubmit);
		return "/admin/psi/psi_sell_estimate_template.jsp";

	}
	@RequestMapping("/admin/psi/einfo/view-{id}")
	public String areaEdit(HttpServletRequest request,@PathVariable Integer id){
		PsiSellInEstimateTemplateInfo info = infoService.getInfoById(id);
		List<PsiSellInEstimateTemplate> list = sellInEstimateTemplateRepository.findByInfo(info);
		request.setAttribute("list", list);
		return "/admin/psi/est_list.jsp";
	}
	/**
	 * 管理员上传统一销售预估魔板（每个月15日由管理员上传
	 * @param request
	 * @param cityFile
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/admin/comm/upload/sell",method=RequestMethod.POST)
	@ResponseBody
	public String uploadSellEstimateTemplate(HttpServletRequest request,@RequestParam MultipartFile sellFile,HttpServletResponse response) throws Exception{
		ApiWarp apiWarp = new ApiWarp();
		SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
		//Date dateMonth = sdfMonth.parse(DateUtils.getCurrentMonth());
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		String error="";
		try {
			List<Map> mapList = excelService.getSellEstimateTemplateByExcel(sellFile.getInputStream());
			for(Map map :mapList){
				String name = String.valueOf(map.get("productName"));
				if(productPriceService.findProductByNameAndDateMonth(name.trim(), dateMonth)==null){
					error = error + name + ",";
				}
			}
			if(!"".equals(error)){
				apiWarp.addError(error);
			}else{
				PsiSellInEstimateTemplateInfo info = new PsiSellInEstimateTemplateInfo();
				info.setName(DateUtils.getCurrentMonth()+"销售预估模板");
				info.setCreatetime(new Date());
				info.setUser(commService.findCurrentUserByRequest(request));
				List<PsiSellInEstimateTemplate> tempList = new ArrayList<PsiSellInEstimateTemplate>();
				for(Map map :mapList){
					PsiSellInEstimateTemplate sellEstimateTemplate = new PsiSellInEstimateTemplate();
					String productName = String.valueOf(map.get("productName")).trim();
					Product product = productPriceService.findProductByNameAndDateMonth(productName,dateMonth);
					String comment = String.valueOf(map.get("comment")).equals("null")?"":String.valueOf(map.get("comment"));
					sellEstimateTemplate.setProduct(product);
					sellEstimateTemplate.setComment(comment);
					sellEstimateTemplate.setDateMonth(dateMonth);
					sellEstimateTemplate.setInfo(info);
					tempList.add(sellEstimateTemplate);
					//sellInEstimateTemplateRepository.save(sellEstimateTemplate);
				}
				infoService.save(info);
				sellInEstimateTemplateRepository.save(tempList);
			}
			
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		return apiWarp.toJsonString();
	}
	
	@RequestMapping(value="/admin/psi/selltarget.html")
	public String uploadsellTarget(HttpServletRequest request, @RequestParam(required=false) Integer page){
		Paging<PsiSellInEstimateByMonthInfo> paging = estimateByMonthInfoService.findAll(page, PAGE_SIZE);
		boolean isSubmit = false;
		if(sellInEstimateByMonthRepository.getCountByDateMonth(DateUtils.getCurrentMonthFisrtDay())>0){
			isSubmit = true;
		}
		request.setAttribute("pagelist", paging);
		request.setAttribute("isSubmit", isSubmit);
		return "/admin/psi/selltarget.jsp";

	}
	
	@RequestMapping("/admin/psi/einfotarget/view-{id}")
	public String viewTarget(HttpServletRequest request,@PathVariable Integer id){
		PsiSellInEstimateByMonthInfo info = estimateByMonthInfoService.getInfoById(id);
		List<PsiSellInEstimateByMonth> list = sellInEstimateByMonthRepository.findByInfo(info);
		request.setAttribute("list", list);
		return "/admin/psi/est_bymonth_list.jsp";
	}
	
	@RequestMapping(value="/admin/comm/upload/selltarget",method=RequestMethod.POST)
	@ResponseBody
	public String uploadSellTargetTemplate(HttpServletRequest request,@RequestParam MultipartFile sellTargetFile,HttpServletResponse response) throws Exception{
		ApiWarp apiWarp = new ApiWarp();
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		Date createtime = new Date();
		String error="";
		try {
			List<Map> mapList = excelService.getSellTargetTemplateByExcel(sellTargetFile.getInputStream());
			for(Map map :mapList){
				String name = String.valueOf(map.get("cityName"));
				if(commCityService.getCommCityByCityName(name.trim())==null){
					error = error + name + ",";
				}
			}
			if(!"".equals(error)){
				apiWarp.addError(error);
			}else{
				PsiSellInEstimateByMonthInfo info = new PsiSellInEstimateByMonthInfo();
				info.setName(DateUtils.getCurrentMonth()+"的销售目标模板");
				info.setUser(commService.findCurrentUserByRequest(request));
				info.setCreatetime(new Date());
				List<PsiSellInEstimateByMonth> list = new ArrayList<PsiSellInEstimateByMonth>();
				for(Map map :mapList){
					PsiSellInEstimateByMonth sellInEstimateByMonth = new PsiSellInEstimateByMonth();
					String cityName = String.valueOf(map.get("cityName")).trim();
					Integer sellInVolume = Integer.parseInt(String.valueOf(map.get("sellInVolume")).trim().replace(".0", ""));
					Integer sellOutVolume = Integer.parseInt(String.valueOf(map.get("sellOutVolume")).trim().replace(".0", ""));
					CommAgent agent = commService.findAgentByCity(cityName);
					
					sellInEstimateByMonth.setAgent(agent);
					sellInEstimateByMonth.setDateMonth(dateMonth);
					sellInEstimateByMonth.setCreatetime(createtime);
					sellInEstimateByMonth.setSellInVolume(sellInVolume);
					sellInEstimateByMonth.setSellOutVolume(sellOutVolume);
					sellInEstimateByMonth.setInfo(info);
					list.add(sellInEstimateByMonth);
				}
				estimateByMonthInfoService.save(info);
				sellInEstimateByMonthRepository.save(list);
			}
			
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		return apiWarp.toJsonString();
	}
}
