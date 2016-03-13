package com.mmd.mssp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommSeries;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellIn;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.repository.CommAgentRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.PsiSellInRepository;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.PsiSellInService;

@Service
@Transactional
public class PsiSellInServiceImpl implements PsiSellInService {

	@Resource
	PsiSellInRepository psiSellInRepository;
	
	@Resource
	ProductRepository productRepository;
	
	@Resource
	CommAgentRepository commAgentRepository;
	
	@Resource
	CommService commService;
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInService#addBatchPsiSellIn(java.util.List)
	 */
	@Override
	public void addBatchPsiSellIn(List<PsiSellInDatasource> datalist) {
		List<PsiSellIn> sellInList  = new ArrayList<PsiSellIn>();
		for (int i = 0; i < datalist.size(); i++) {
			PsiSellIn sellIn  = new PsiSellIn();
			sellIn.setProduct(productRepository.findProductByName(datalist.get(i).getMaterial(),Product.NOT_DELETE));
			sellIn.setSellInVolume(StringUtils.isBlank(datalist.get(i).getDlvQty())?0:Integer.parseInt(datalist.get(i).getDlvQty()));
			sellIn.setCreatetime(new Date());
			sellIn.setChannelType(datalist.get(i).getChannelType());
			sellIn.setActiontime(datalist.get(i).getCreatedon());
			sellIn.setAgent(commService.findAgentByCity(datalist.get(i).getCity()));
			sellInList.add(sellIn);
		}
		psiSellInRepository.save(sellInList);
	}

	@Override
	public Integer sumSellInDatas(String thisMonth,  Product product,CommAgent agent, Integer channelType) {
		return psiSellInRepository.sumSellInDatas(thisMonth, product, agent, channelType);
	}
	
	public Integer sumSellInLastMonth(Date firstDay, Date lastDay, CommAgent commAgent, Product product,
			Integer channelType) {
		return psiSellInRepository.sumSellInLastMonth(firstDay, lastDay, product, commAgent, channelType);
	}

	@Override
	public Integer sellInCurrent(String actiontime, Product product,
			CommAgent commAgent, Integer channelType) {
		if(psiSellInRepository.sellInData(actiontime, product, commAgent, channelType)==null){
			return 0;
		}else{
			return psiSellInRepository.sellInData(actiontime, product, commAgent, channelType);
		}
	}

	@Override
	public Integer sumSellInCurrent(Date firstDay, Date currentDate,
			Product product, CommAgent commAgent) {
		return psiSellInRepository.sumSellInData(firstDay, currentDate, product, commAgent);
	}


}
