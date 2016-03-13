package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommApprovalLog;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiEstimateByProduct;
import com.mmd.mssp.domain.PsiInventory;
import com.mmd.mssp.domain.PsiSellInEstimateTemplate;
import com.mmd.mssp.domain.PsiSellOut;
import com.mmd.mssp.domain.PsiSellOutUpdateApplyLog;
import com.mmd.mssp.domain.UpdateLogByAdmin;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.PsiReportVo;
import com.mmd.mssp.domain.vo.SellOutVo;
import com.mmd.mssp.repository.CommAgentRepository;
import com.mmd.mssp.repository.CommApprovalLogRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.PsiEstimateByProductRepository;
import com.mmd.mssp.repository.PsiInventoryRepository;
import com.mmd.mssp.repository.PsiSellInEstimateTemplateRepository;
import com.mmd.mssp.repository.PsiSellInRepository;
import com.mmd.mssp.repository.PsiSellOutRepository;
import com.mmd.mssp.repository.PsiSellOutUpdateApplyLogRepository;
import com.mmd.mssp.repository.UpdateLogByAdminRepository;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.PsiSellOutService;
import com.mmd.mssp.util.DateUtils;

@Service
@Transactional
public class PsiSellOutServiceImpl implements PsiSellOutService {

	@Resource
	PsiSellOutRepository psiSellOutRepository;
	
	@Resource
	CommService commService;
	
	@Resource
	PsiEstimateByProductRepository psiEstimateByProductRepository;
	
	@Resource
	PsiSellInRepository psiSellInRepository;
	
	@Resource
	PsiInventoryRepository  psiInventoryRepository;
	
	@Resource
	PsiSellOutUpdateApplyLogRepository psiSellOutUpdateApplyLogRepository;
	
	@Resource
	CommApprovalLogRepository commApprovalLogRepository;
	
	@Resource
	UpdateLogByAdminRepository updateLogByAdminRepository;
	
	@Resource
	PsiSellInEstimateTemplateRepository psiSellInEstimateTemplateRepository;
	
	@Resource
	CommAgentRepository agentRepository;
	
	@Resource
	ProductRepository productRepository;
	
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInService#addBatchPsiSellIn(java.util.List)
	 */
	@Override
	public void addBatchPsiSellOut(List<PsiSellOut> selloutList) {
		psiSellOutRepository.save(selloutList);
	}

	@Override
	public PsiSellOut findByProductAndAgentAndCreatetimeAndChannelType(Product productName,CommAgent commAgent, Date date,Integer channelType) {
		return psiSellOutRepository.findByProductAndAgentAndCreatetimeAndChannelType(productName,commAgent, date, channelType);
	}

	
	@Override
	public Integer sumSellOutDatas(String thisMonth,  Product product,CommAgent agent,
			Integer channelType) {
		return psiSellOutRepository.sumSellOutDatas(thisMonth, product, agent, channelType);
	}
	


	@Override
	public Integer sumSellOutLastMonth(Date firstDay, Date lastDay,
			Product product, CommAgent commAgent, Integer channelType) {
		return psiSellOutRepository.sumSellOutLastMonth(firstDay, lastDay, product, commAgent, channelType);
	}



	@Override
	public PsiSellOut save(PsiSellOut psiSellOut) {
		return psiSellOutRepository.save(psiSellOut);
	}



	@Override
	public Boolean saveApprovalLog(PsiSellOutUpdateApplyLog applyLog,
			CommApprovalLog approvalLog, PsiSellOut psiSellOut,
			UpdateLogByAdmin aByAdmin,PsiInventory currentInventory) {
		if(applyLog!=null){
			psiSellOutUpdateApplyLogRepository.save(applyLog);
		}
		if(applyLog!=null){
			commApprovalLogRepository.save(approvalLog);
		}
		if(psiSellOut!=null){
			psiSellOutRepository.save(psiSellOut);
		}
		if(aByAdmin!=null){
			updateLogByAdminRepository.save(aByAdmin);
		}
		if(currentInventory!=null){
			psiInventoryRepository.save(currentInventory);
		}
		return true;
	}



	@Override
	public Integer sellOutCurrent(String actiontime, Product product,
			CommAgent commAgent, Integer channelType) {
		if(psiSellOutRepository.sellOutData(actiontime, product, commAgent, channelType)==null){
			return 0;
		}else{
			return psiSellOutRepository.sellOutData(actiontime, product, commAgent, channelType);
		}
	}

	/**
	* @author: sheng.tian
	* @Description: 获取当前库存  公式：上个月的月末库存 + 累计Sell in - 累计Sell out = 当前库存
	* @param @param currentMonthFisrtDay
	* @param @param date
	* @param @param product
	* @param @param agent
	* @param @param channelType
	* @param @return   
	* @return int   
	* @throws
	*/
	@Override
	public Integer getCurrentInventory(Date currentMonthFisrtDay, Date date,
			Product product, CommAgent agent, Integer channelType) {
		PsiInventory pi = psiInventoryRepository.findLastInventory(commService.getLastMonthLastDay(),product,channelType,agent);
		Integer totalSellIn = psiSellInRepository.sumSellInData(commService.getCurrentMonthFisrtDay(),new Date(),product,agent,channelType);
		Integer totalSellOut = psiSellOutRepository.sumSellOutData(commService.getCurrentMonthFisrtDay(),new Date(),product,agent,channelType);
		return (pi==null?0:pi.getInventoryVolume())+(totalSellIn==null?0:totalSellIn)-(totalSellOut==null?0:totalSellOut);
	}
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellOutService#getSellOutList()
	 */
	@Override
	public List<SellOutVo> getSellOutList(String channelType,CommAgent agent) {
		List<Product> pList = commService.findProductByChannelType(Product.getSellTypeByChannlType(Integer.parseInt(channelType)));
		//List<Product> pList1 = commService.findProductListByChannelType(channelType,Product.NOT_DELETE);
		List<SellOutVo> voList = new ArrayList<SellOutVo>();
		for (int i = 0; i < pList.size(); i++) {
			SellOutVo vo = new SellOutVo();
			vo.setProduct(pList.get(i));
			PsiSellInEstimateTemplate template =psiSellInEstimateTemplateRepository.findTempByMouthAndProduct(DateUtils.getLastMonthFirstDay(),pList.get(i));
			PsiEstimateByProduct ep = psiEstimateByProductRepository.findTotalVolumeByProductAndDataMonthAndAgent(template,DateUtils.getLastMonthFirstDay(),agent);
			vo.setEstimateVolume(ep==null?0:ep.getNextMonthEstimateVolume());
			vo.setTotalSellin(psiSellInRepository.sumSellInData(commService.getCurrentMonthFisrtDay(),new Date(),pList.get(i),agent,Integer.parseInt(channelType)));
			vo.setTotalSellout(psiSellOutRepository.sumSellOutData(commService.getCurrentMonthFisrtDay(), new Date(), pList.get(i), agent, Integer.parseInt(channelType)));
			vo.setCurrentInventory(this.getCurrentInventory(commService.getCurrentMonthFisrtDay(),new Date(),pList.get(i),agent,Integer.parseInt(channelType)));
			PsiSellOut po =psiSellOutRepository.findByProductAndCreatetimeAndChannelType(pList.get(i), DateUtils.getDate(new Date()),Integer.parseInt(channelType));
			vo.setSelloutVolume(po==null?0:po.getSellInOutlvolume());
			voList.add(vo);
		}
		return voList;
	}

	@Override
	public Paging<PsiReportVo> findByDateChannelTypeProduct(Date startDate,
			Date endDate, Integer channelType, Integer[] productId,
			Integer[] agentId, String page) {
		int productFlag = 1;
		int pageNo = 1;
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        int index = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.DATE, (1 - index));
        Date firstDate = calendar.getTime();
        
		Page<Product> products = null;
		Paging<PsiReportVo> paging = new Paging(pageNo, 50);
		if(!StringUtils.isBlank(page)){
			try{
				pageNo = Integer.parseInt(page);
			}catch(Exception e){
				
			}
		}
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
		products = productRepository.findByIdList(idList, productFlag, paging.toPage());
		if(products!=null){
			List<PsiReportVo> reportList = new ArrayList<PsiReportVo>();
			for (Product product : products.getContent()) {
				for (CommAgent agent : agentList){
					PsiReportVo reportVo = new PsiReportVo();
					reportVo.setAgent(agent);
					reportVo.setProduct(product);
					Integer totalSellIn = psiSellInRepository.sumSellInData(startDate, endDate, product, agent, channelType);
					Integer totalSellOut = psiSellOutRepository.sumSellOutData(startDate, endDate, product, agent, channelType);
					Integer currentInventory = getCurrentInventory(firstDate, endDate, product, agent, channelType);
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
	public void saveSellOutAndPsiInventory(PsiSellOut sellOut,
			PsiInventory psiInventory) {
		psiSellOutRepository.save(sellOut);
		psiInventoryRepository.save(psiInventory);
	}
}
