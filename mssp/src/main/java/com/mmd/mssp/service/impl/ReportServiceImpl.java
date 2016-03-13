package com.mmd.mssp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.B2CSpecialApplyProduct;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Pannel;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.ProductSeries;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;
import com.mmd.mssp.repository.CommAgentRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.PsiInventoryRepository;
import com.mmd.mssp.repository.PsiSellInDataSrouceRepository;
import com.mmd.mssp.repository.PsiSellInRepository;
import com.mmd.mssp.repository.PsiSellOutRepository;
import com.mmd.mssp.service.PsiReportService;
import com.mmd.mssp.service.PsiSellOutService;
import com.mmd.mssp.util.DateUtils;

@Service
public class ReportServiceImpl implements PsiReportService {

	@Resource
	PsiSellOutRepository psiSellOutRepository;
	@Resource
	PsiSellInRepository psiSellInRepository;
	@Resource
	PsiInventoryRepository psiInventoryRepository;
	@Resource
	PsiSellInDataSrouceRepository psiSellInDataSrouceRepository;
	@Resource
	ProductRepository productRepository;
	@Resource
	PsiSellOutService psiSellOutService;
	@Resource
	CommAgentRepository agentRepository;
	
	@Override
	public Integer sumSellIn(Date firstDay, Date currentDay, Product product,
			CommAgent agent, Integer channelType) {
		return psiSellInRepository.sumSellInData(firstDay, currentDay, product, agent, channelType);
	}

	@Override
	public Integer sumSellOut(Date firstDay, Date currentDay, Product product,
			CommAgent agent, Integer channelType) {
		return psiSellOutRepository.sumSellOutData(firstDay, currentDay, product, agent, channelType);
	}

	@Override
	public Integer inventory(Product product, CommAgent agent,
			Integer channelType, Date lastDayOfLastMonth) {
		if(psiInventoryRepository.findByProductAndCommAgentAndChannelType(product, agent, channelType, lastDayOfLastMonth)==null){
			return 0;
		}else{
			return psiInventoryRepository.findByProductAndCommAgentAndChannelType(product, agent, channelType, lastDayOfLastMonth).getInventoryVolume();
		}
	}

	@Override
	public PsiSellInDatasource findByPacc(String material, String city,
			Integer channelType, Date date) {
		return psiSellInDataSrouceRepository.findByPacc(material, city, channelType, date);
	}

	@Override
	public Paging<PsiReportVo> findByDateChannelTypeProduct(Date startDate,
			Date endDate, Integer channelType, Integer[] productId,Integer[] agentId,Integer page) {
		int productFlag = 1;
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        Date firstDate = calendar.getTime();
        
		Page<Product> products = null;
		Paging<PsiReportVo> paging = new Paging(page, 50);
		List<Integer> idList = new ArrayList<Integer>();
		List<CommAgent> agentList = new ArrayList<CommAgent>();
		if(agentId!=null){
			for(int i=0; i<agentId.length; i++){
				agentList.add(agentRepository.getById(agentId[i]));
			}
		}else{
			agentList.add(new CommAgent());
		}
		
		if(productId[0]!=-1){
			productFlag = 2;
			for(int i=0; i<productId.length; i++){
				idList.add(productId[i]);
			}
		}else{
			idList.add(-2);
		}
		products = productRepository.findByIdAndChannelTypeList(idList, productFlag,Product.getSellTypeByChannlType(channelType),paging.toPage());
		if(products!=null){
			List<PsiReportVo> reportList = new ArrayList<PsiReportVo>();
			for (Product product : products.getContent()) {
				for (CommAgent agent : agentList){
					PsiReportVo reportVo = new PsiReportVo();
					reportVo.setAgent(agent);
					reportVo.setProduct(product);
					Integer totalSellIn = psiSellInRepository.sumSellInData(startDate, endDate, product, agent, channelType);
					Integer totalSellOut = psiSellOutRepository.sumSellOutData(startDate, endDate, product, agent, channelType);
					Integer currentInventory = psiSellOutService.getCurrentInventory(firstDate, endDate, product, agent, channelType);
					reportVo.setCurrentInventory(currentInventory);
					reportVo.setTotalSellIn(totalSellIn);
					reportVo.setTotalSellOut(totalSellOut);
					reportList.add(reportVo);
				}
			}
			Page pageImpl = new PageImpl(reportList,paging.toPage(),products.getTotalElements()*agentList.size());
			paging.setResult(pageImpl);
		}
		return paging;
	}

	@Override
	public List<PsiReportVo> findByDateChannelTypeProductToExport(
			Date startDate, Date endDate, Integer channelType,
			Integer[] productId, List<CommAgent> agentList) {
		int productFlag = 1;
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        Date firstDate = calendar.getTime();
        
		List<Integer> idList = new ArrayList<Integer>();
		if(productId[0]!=-1){
			productFlag = 2;
			for(int i=0; i<productId.length; i++){
				idList.add(productId[i]);
			}
		}else{
			idList.add(-2);
		}
		List<Product> products = productRepository.findByIdListToExport(idList, productFlag);
		List<PsiReportVo> reportList = new ArrayList<PsiReportVo>();
		if(products!=null){
			for (Product product : products) {
				for (CommAgent agent : agentList){
					PsiReportVo reportVo = new PsiReportVo();
					reportVo.setAgent(agent);
					reportVo.setProduct(product);
					Integer totalSellIn = psiSellInRepository.sumSellInData(startDate, endDate, product, agent, channelType);
					Integer totalSellOut = psiSellOutRepository.sumSellOutData(startDate, endDate, product, agent, channelType);
					Integer currentInventory = psiSellOutService.getCurrentInventory(firstDate, endDate, product, agent, channelType);
					reportVo.setCurrentInventory(currentInventory);
					reportVo.setTotalSellIn(totalSellIn);
					reportVo.setTotalSellOut(totalSellOut);
					reportList.add(reportVo);
				}
			}
		}
		return reportList;
	}

	@Override
	public List<Map> getMapBySearch(List<PsiReportVo> list) {
		List<Map> mapList = new ArrayList();
		for (PsiReportVo vo : list) {
			Map map = new HashMap();
			CommCity city = vo.getAgent().getCommCity();
			Product product = vo.getProduct();
			
			map.put("city", city==null?"":city.getCityName());
			map.put("productName", product.getName());
			map.put("totalSellIn", vo.getTotalSellIn());
			map.put("totalSellOut", vo.getTotalSellOut());
			map.put("currentInventory", vo.getCurrentInventory());
			map.put("size1", product.getSize1());
			map.put("size2", product.getSize2());
			map.put("pannel", product.getPannel()==null?"":product.getName());
			if(product.getChannelType()==0){
				map.put("channelType", "飞生/越海");
			}else if(product.getChannelType()==1){
				map.put("channelType", "飞生");
			}else{
				map.put("channelType", "越海");
			}
			mapList.add(map);
		}
		
		return mapList;
	}
}
