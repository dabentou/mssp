package com.mmd.mssp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mmd.mssp.domain.CommBusiness;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductPrice;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.vo.ApiWarp;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.service.CommBusinessService;
import com.mmd.mssp.service.CommSeriesService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.PannelService;
import com.mmd.mssp.service.ProductPriceService;
import com.mmd.mssp.service.ProductSeriesService;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class PsiProductController {

	private static final int IS_NOT_DELETE = 0;
	private static final int IS_DELETE = 1;
	private static final int PAGE_SIZE = 50;

	@Resource
	ProductService productService;
	@Resource
	CommSeriesService commSeriesService;
	@Resource
	ProductSeriesService productSeriesService;
	@Resource
	PannelService pannelService;
	
	@Resource
	CommService commService;
	
	@Resource
	CommBusinessService commBusinessService;
	
	@Resource
	ProductPriceService productPriceService;
	
	@Resource
	ExcelService excelService;
	
	@RequestMapping("/admin/psi/product/list.html")
	public String list(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		List<CommSeries> commSeriesList = commSeriesService.listAll(false);
		List<ProductSeries> productSeriesList = productSeriesService.listAll(false);
		List<Pannel> pannelList = pannelService.listAll(false);
		boolean isSubmit = productPriceService.isSubmitCurrentMonth(DateUtils.getCurrentMonthFisrtDay());
		
		request.setAttribute("commSeriesList", commSeriesList);
		request.setAttribute("productSeriesList", productSeriesList);
		request.setAttribute("pannelList", pannelList);
		Paging<Product> paging = productService.findByIsDelete(false, page,PAGE_SIZE);
		request.setAttribute("pagelist", paging);
		request.setAttribute("isSubmit", isSubmit);
		return "/admin/psi/product/product.jsp";
	}
	
	@RequestMapping("/admin/psi/product/new.html")
	public String newproduct(HttpServletRequest request, @RequestParam(required=false) Integer page){
		
		List<CommSeries> commSeriesList = commSeriesService.listAll(false);
		List<ProductSeries> productSeriesList = productSeriesService.listAll(false);
		List<Pannel> pannelList = pannelService.listAll(false);
		List<CommBusiness> businesses = commBusinessService.findBusinessList();
		
		request.setAttribute("businesses", businesses);
		request.setAttribute("commSeriesList", commSeriesList);
		request.setAttribute("productSeriesList", productSeriesList);
		request.setAttribute("pannelList", pannelList);
		
		return "/admin/psi/product/product_new.jsp";
		
	}
	
	@RequestMapping(value="/admin/psi/product/newSave.html",method=RequestMethod.POST)
	public String newSave(Product product,ProductPrice productPrice,Integer seriesId,Integer productSeriesId,Integer pannelId){
		CommSeries commSeries = commSeriesService.getCommSeriesById(seriesId);
		ProductSeries productSeries = productSeriesService.getProductSeriesById(productSeriesId);
		Pannel pannel = pannelService.getPannelById(pannelId);
		product.setPannel(pannel);
		product.setProductSeries(productSeries);
		product.setCommSeries(commSeries);
		product.setIsDelete(IS_NOT_DELETE);
		product.setCreatetime(new Date());
		Product newEntity = productService.save(product);
		productPrice.setDateMonth(commService.getCurrentMonthFisrtDay());
		productPrice.setProduct(newEntity);
		productPriceService.save(productPrice);
		return "redirect:/admin/psi/product/list.html";
	}
	
	@RequestMapping("/admin/psi/product/edit-{id}")
	public String productEdit(HttpServletRequest request,@PathVariable Integer id){
		
		List<CommSeries> commSeriesList = commSeriesService.listAll(false);
		List<ProductSeries> productSeriesList = productSeriesService.listAll(false);
		List<Pannel> pannelList = pannelService.listAll(false);
		Product product = productService.getProductById(id);
		List<CommBusiness> businesses = commBusinessService.findBusinessList();
		
		request.setAttribute("businesses", businesses);
		request.setAttribute("commSeriesList", commSeriesList);
		request.setAttribute("productSeriesList", productSeriesList);
		request.setAttribute("pannelList", pannelList);
		request.setAttribute("product", product);
				
		return "/admin/psi/product/product_edit.jsp";
	}
	
	/**
	 * 编辑型号
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/admin/psi/product/price/edit-{id}")
	public String productPriceEdit(HttpServletRequest request,@PathVariable Integer id){
		ProductPrice productPrice = productPriceService.getById(id);
		request.setAttribute("productPrice", productPrice);
		return "/admin/psi/product/product_price_edit.jsp";
	}
	
	
	@RequestMapping("/admin/psi/product/listPrice")
	public String listProductPrice(HttpServletRequest request,Integer id,@RequestParam(required=false) Integer page){
		Product product = productService.getProductById(id);
		Paging<ProductPrice> pagelist = productPriceService.findByProduct(product, page, PAGE_SIZE);
		request.setAttribute("pagelist", pagelist);
		return "/admin/psi/product/product_price.jsp";
	}
	
	@RequestMapping("/admin/psi/product/delete")
	public String delete(Integer id){
		
		Product product = productService.getProductById(id);
		
		product.setIsDelete(IS_DELETE);
		productService.save(product);
	
		return "redirect:/admin/psi/product/list.html";
		
	}
	
	@RequestMapping(value="/admin/psi/product/editSave.html",method=RequestMethod.POST)
	public String editSave(Integer id,String name,String prodectFormat,String size1,String size2,Integer series_id,Integer product_series_id,Integer pannel_id,String material,Integer status,Integer channelType,Integer sellType){
		
		Product product = productService.getProductById(id);
		CommSeries commSeries = commSeriesService.getCommSeriesById(series_id);
		ProductSeries productSeries = productSeriesService.getProductSeriesById(product_series_id);
		Pannel pannel = pannelService.getPannelById(pannel_id);
		product.setName(name);
		product.setProdectFormat(prodectFormat);
		product.setSize1(size1);
		product.setSize2(size2);
		product.setCommSeries(commSeries);
		product.setProductSeries(productSeries);
		product.setPannel(pannel);
		product.setMaterial(material);
		product.setStatus(status);
		product.setChannelType(channelType);
		product.setSellType(sellType);
		productService.save(product);
		return "redirect:/admin/psi/product/list.html";
	}
	
	@RequestMapping(value="/admin/psi/product/price/edit",method=RequestMethod.POST)
	public String editPirceSave(ProductPrice productPrice){
		ProductPrice orgin = productPriceService.getById(productPrice.getId());
		BeanUtils.copyProperties(productPrice, orgin, "product","dateMonth");
		productPriceService.save(orgin);
		return "redirect:/admin/psi/product/listPrice?id="+orgin.getProduct().getId();
	}
	
	@RequestMapping("/admin/psi/product/search")
	public String search(HttpServletRequest request, @RequestParam(required=false) Integer page,String name,Integer seriesId,Integer productSeriesId,Integer pannelId){
		
		List<CommSeries> commSeriesList = commSeriesService.listAll(false);
		List<ProductSeries> productSeriesList = productSeriesService.listAll(false);
		List<Pannel> pannelList = pannelService.listAll(false);
		
		Paging<Product> paging = productService.findBySearch(name, seriesId, productSeriesId, pannelId, page,PAGE_SIZE);
		
		request.setAttribute("commSeriesList", commSeriesList);
		request.setAttribute("productSeriesList", productSeriesList);
		request.setAttribute("pannelList", pannelList);
		request.setAttribute("pagelist", paging);
		
		return "/admin/psi/product/product.jsp";
	}
	
	@RequestMapping(value="/admin/psi/upload/product",method=RequestMethod.POST)
	@ResponseBody
	public String uploadProduct(HttpServletRequest request,@RequestParam MultipartFile pfile,HttpServletResponse response){
		ApiWarp apiWarp = new ApiWarp();
		Date dateMonth = DateUtils.getCurrentMonthFisrtDay();
		try {
			List<Map> mapList = excelService.getProductByExcel(pfile.getInputStream());
			for(Map map :mapList){
				String productName = String.valueOf(map.get("productName")).trim();
				
				String financePrice = String.valueOf(map.get("financePrice")==null?0:map.get("financePrice")).trim();
				String netPrice = String.valueOf(map.get("netPrice")==null?0:map.get("netPrice")).trim();
				String poPrice = String.valueOf(map.get("poPrice")==null?0:map.get("poPrice")).trim();
				String productSeriesName = String.valueOf(map.get("productSeriesName")).trim();
				String pannelName = String.valueOf(map.get("pannelName")).trim();
				String seriesName = String.valueOf(map.get("seriesName")).trim();
				String size1 = String.valueOf(map.get("size1")).trim();
				String size2 = String.valueOf(map.get("size2")).trim();
				String channelType = String.valueOf(map.get("channelType")).trim();
				String sellType = String.valueOf(map.get("sellType")).trim();
				String interPublicPrice = String.valueOf(map.get("interPublicPrice")==null?0:map.get("interPublicPrice")).trim();
				String b2bPublicPrice = String.valueOf(map.get("b2bPublicPrice")==null?0:map.get("b2bPublicPrice")).trim();
				String syPrice = String.valueOf(map.get("syPrice")==null?0:map.get("syPrice")).trim();
				String status = String.valueOf(map.get("status")).trim();
				String date = String.valueOf(map.get("date")).trim();
				String productFormat = String.valueOf(map.get("productFormat")).trim().equals("null")?"":String.valueOf(map.get("productFormat")).trim();
				
				if(productService.findByName(productName)!=null){
					Product product = productService.findByName(productName);
					ProductPrice pPrice = new ProductPrice();
					pPrice.setFinancePrice(new BigDecimal(financePrice));
					pPrice.setNetPrice(new BigDecimal(netPrice));
					pPrice.setInterPublicPrice(new BigDecimal(interPublicPrice));
					pPrice.setB2bPublicPrice(new BigDecimal(b2bPublicPrice));
					pPrice.setSyPrice(new BigDecimal(syPrice));
					pPrice.setPoPrice(new BigDecimal(poPrice));
					pPrice.setProduct(product);
					pPrice.setDateMonth(dateMonth);
					productPriceService.save(pPrice);
				}else{
					Product product = new Product();
					ProductSeries productSeries = productSeriesService.getProductSeriesByName(productSeriesName);
					if(!productSeriesName.equals("null") && productSeries==null){
						productSeries = new ProductSeries();
						productSeries.setName(productSeriesName);
						productSeries.setIsDelete(IS_NOT_DELETE);
						productSeries.setCreatetime(new Date());
						productSeriesService.save(productSeries);
					}
					CommSeries series = commSeriesService.getCommSeriesByName(seriesName);
					if(!seriesName.equals("null") && series==null){
						series = new CommSeries();
						series.setName(seriesName);
						series.setIsDelete(IS_NOT_DELETE);
						series.setCreatetime(new Date());
						commSeriesService.save(series);
					}
					Pannel pannel = pannelService.getPannelByName(pannelName);
					if(!pannelName.equals("null") && pannel==null){
						pannel = new Pannel();
						pannel.setName(pannelName);
						pannel.setIsDelete(IS_NOT_DELETE);
						pannel.setCreatetime(new Date());
						pannelService.save(pannel);
					}
					int stat = 0;//停产
					if(status.equals("激活")){
						stat = 1;
					}
					int cType = 0;//没填设为0
					if(channelType.equals("飞生")){
						cType = 1;
					}else if(channelType.equals("越海")){
						cType = 2;
					}else if(channelType.equals("飞生&越海")){
						cType = 3;//飞生&越海
					}
					/*"飞生 ".equals(channelType);
					channelType.equals("飞f生");*/
					int sType = 0;
					if(sellType.equals("网吧型号")){
						sType = 1;
					}else if(sellType.equals("行业型号")){
						sType = 2;
					}else if(sellType.equals("零售型号")){
						sType = 3;//零售型号
					}else if(sellType.equals("电商型号")){
						sType = 4;
					}else if(sellType.equals("通路机型")){
						sType = 5;
					}
					product.setName(productName);
					product.setSize1(size1);
					product.setSize2(size2);
					product.setCreatetime(new Date());
					product.setStatus(stat);
					product.setIsDelete(IS_NOT_DELETE);
					product.setPannel(pannel);
					product.setProductSeries(productSeries);
					product.setCommSeries(series);
					product.setChannelType(cType);
					product.setSellType(sType);
					product.setProdectFormat(productFormat);
					productService.save(product);
					
					ProductPrice pPrice = new ProductPrice();
					pPrice.setFinancePrice(new BigDecimal(financePrice));
					pPrice.setNetPrice(new BigDecimal(netPrice));
					pPrice.setInterPublicPrice(new BigDecimal(interPublicPrice));
					pPrice.setB2bPublicPrice(new BigDecimal(b2bPublicPrice));
					pPrice.setPoPrice(new BigDecimal(poPrice));
					pPrice.setSyPrice(new BigDecimal(syPrice));
					pPrice.setProduct(product);
					pPrice.setDateMonth(dateMonth);
					productPriceService.save(pPrice);
				}
			}
			//apiWarp.putData("codeList", codeList);
		}catch (IOException e) {
			apiWarp.addError("导入xls非法，导入失败");
			e.printStackTrace();
		}
		return apiWarp.toJsonString();
	}
}
