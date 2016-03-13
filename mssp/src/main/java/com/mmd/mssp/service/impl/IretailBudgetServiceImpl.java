package com.mmd.mssp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.mmd.mssp.domain.CommProvince;
import com.mmd.mssp.domain.CommUser;
import com.mmd.mssp.domain.IretailBudget;
import com.mmd.mssp.domain.vo.IretailBudgetYears;
import com.mmd.mssp.repository.IretailBudgetRepository;
import com.mmd.mssp.service.CommProvinceService;
import com.mmd.mssp.service.IretailBudgetLogService;
import com.mmd.mssp.service.IretailBudgetService;

@Service
public class IretailBudgetServiceImpl implements IretailBudgetService {

	private static final Log logger = LogFactory.getLog(IretailBudgetServiceImpl.class);
	
	@Resource
	IretailBudgetRepository iretailBudgetRepository;
	
	@Resource
	CommProvinceService commProvinceService;
	
	@Resource
	IretailBudgetLogService iretailBudgetLogService;
	
	@Override
	public List<IretailBudgetYears> selectYearAndQuarter() {
		List<IretailBudgetYears> list = new ArrayList<IretailBudgetYears>();
		List<Object[]> lists = iretailBudgetRepository.findByYearAndQuarter();
		if(lists.size() > 0){
			for(int i = 1;i<=lists.size();i++){
				IretailBudgetYears domain = new IretailBudgetYears();
				domain.setId(i);
				domain.setYear(Integer.parseInt(lists.get(i-1)[0].toString()));
				domain.setQuarter(Integer.parseInt(lists.get(i-1)[1].toString()));
				list.add(domain);
			}
		}
		return list;
	}
	
	@Override
	public List<IretailBudget> findByYearsAndQuarter(Integer year,
			Integer quarter) {
		List<IretailBudget> list = iretailBudgetRepository.findByYearsAndQuarter(year,quarter);
		return list;
	}
	
	@Override
	public List<IretailBudget> findByYearsAndQuarters(Integer year,
			Integer quarter) {
		List<IretailBudget> list = iretailBudgetRepository.findByYearsAndQuarters(year,quarter);
		return list;
	}
	
//	@Override
//	public BudgetTotal findSumByYearsAndQuarterAndProvince(Integer year,
//			Integer quarter, CommProvince province) {
//		return iretailBudgetRepository.findSumByYearsAndQuarterAndProvince(year,quarter,province);
//	}
	
	@Override
	public IretailBudget findByYearsAndQuarterAndProvince(Integer year,
			Integer quarter, CommProvince province) {
		return iretailBudgetRepository.findByYearsAndQuarterAndProvince(year,quarter,province);
	}
	
	@Override
	public String budgetInput(InputStream inputStream,CommUser user,int years,int months) throws Exception{
		List<IretailBudget> list = new ArrayList<IretailBudget>();
		try {
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			
			for (int j = 1; j < rowNum+1; j++) {//模板中的省份
				String  p = sheet.getRow(j).getCell(0).toString();
				CommProvince pro = commProvinceService.getCommProvinceByProvinceName(p);
				if(pro == null){//说明导入的excle中有的省份不存在
					return p+"不存在";
				}
			}
			
			
			for (int i = 1; i < rowNum+1; i++) {
				IretailBudget bean = new IretailBudget();
				bean.setYear(years);//年
				bean.setMonths(months);//月
				CommProvince province = commProvinceService.getCommProvinceByProvinceName(sheet.getRow(i).getCell(0).toString());//省份
				bean.setCommonProvince(province);
				
				for(int k=2;k<13;k++){//R02到R12的值
					String nums = Integer.toString(k);
					if(k < 10){
						nums = "0" + nums;
					}
					if(sheet.getRow(i).getCell(k).toString() == null || sheet.getRow(i).getCell(k).toString().equals("") ||sheet.getRow(i).getCell(k).equals("-")){
						BeanUtils.setProperty(bean, "r"+nums, new BigDecimal("0").setScale(1, BigDecimal.ROUND_HALF_UP));
					}else{
						System.out.println(k);
						System.out.println(sheet.getRow(i).getCell(k).toString());
						BeanUtils.setProperty(bean, "r"+nums, new BigDecimal(sheet.getRow(i).getCell(k).toString()).setScale(1, BigDecimal.ROUND_HALF_UP));
					}
				}
				list.add(bean);
			}
			save(list);
		}catch (InvalidFormatException e) {
			logger.debug("读取Excell 出错！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("读取Excell 发生异常！");
			e.printStackTrace();
		}	
		return null;
	}
	
	@Override
	public void save(List<IretailBudget> list) {
		iretailBudgetRepository.save(list);
	}
	
	@Override
	public void save(IretailBudget iretailBudget) {
		iretailBudgetRepository.save(iretailBudget);
	}

	@Override
	public List<IretailBudget> listByYearsAndMonthAndProvince(Integer year, CommProvince province) {
		return iretailBudgetRepository.listByYearsAndMonthAndProvince(year, province);
	}
	
	
}
