package com.mmd.mssp.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiInventory;
import com.mmd.mssp.service.ProductService;
import com.mmd.mssp.service.PsiInventoryService;
import com.mmd.mssp.service.PsiSellInService;
import com.mmd.mssp.service.PsiSellOutService;
import com.mmd.mssp.service.impl.CommAgentServiceImpl;

@Component("MonthInventory")
public class MonthInventory {

	@Resource 
	PsiInventoryService psiInventoryService;
	@Resource 
	ProductService productService;
	@Resource
	CommAgentServiceImpl commAgentServiceImpl;
	@Resource
	PsiSellInService psiSellInService;
	@Resource
	PsiSellOutService psiSellOutService;
	
	public void job1(){
		System.out.println(new Date());
		
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(year, month, 1);
        calendar.add(Calendar.DATE, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String lastMonthLastDay = "%"+sdf.format(calendar.getTime())+"%";//1.获得上个月的最后一天
		SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM");
		String thisMonth ="%" + sdff.format(new Date()) + "%";//1.获得这个月
		List<Product> products = productService.listAll(false);//查询所有的产品
		List<CommAgent> commAgents =  commAgentServiceImpl.listAll();//查询所有的代理商
		List <PsiInventory> list = new ArrayList<PsiInventory>();
		for(Product p : products){
			Product product = p;//2.获得产品
			for(CommAgent a : commAgents){
				CommAgent agent = a;//3.获得代理商
				for(int i = 1;i<=2;i++){
					PsiInventory psiInventory = psiInventoryService.findLastInventorys(lastMonthLastDay, product, agent, i);
					//1.获得上个月的剩余库存
					int lastTotal;
					if(psiInventory != null){
						 lastTotal = psiInventory.getInventoryVolume();
					}else{
						lastTotal = 0;
					}
					//获得上个月所有sell in的数据
					int monthSellIn = psiSellInService.sumSellInDatas(thisMonth,  product, agent, i);
					//获得上个月所有sell out的数据
					int monthSellOut = psiSellOutService.sumSellOutDatas(thisMonth, product, agent, i);
					int newData = lastTotal + monthSellIn - monthSellOut;
					PsiInventory bean = new PsiInventory();
					bean.setInventoryVolume(newData);
					bean.setProduct(product);
					bean.setAgent(agent);
					bean.setCreatetime(new Date());
					bean.setChannelType(i);
					list.add(bean);
				}
			}
			psiInventoryService.save(list);
		}
		System.out.println(new Date());
	}
}
