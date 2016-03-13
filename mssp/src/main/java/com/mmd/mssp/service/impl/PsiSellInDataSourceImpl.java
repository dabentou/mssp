package com.mmd.mssp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mmd.mssp.comm.TimeTool;
import com.mmd.mssp.domain.CommCity;
import com.mmd.mssp.domain.Product;
import com.mmd.mssp.domain.PsiSellIn;
import com.mmd.mssp.domain.PsiSellInDatasource;
import com.mmd.mssp.repository.CommCityRepository;
import com.mmd.mssp.repository.ProductRepository;
import com.mmd.mssp.repository.ProductSeriesRepository;
import com.mmd.mssp.repository.PsiSellInDataSrouceRepository;
import com.mmd.mssp.repository.PsiSellInRepository;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.PsiSellInDataSourceService;
import com.mmd.mssp.service.PsiSellInService;
import com.mmd.mssp.util.StringUtil;

@Service
@Transactional
public class PsiSellInDataSourceImpl implements PsiSellInDataSourceService {
	
	private static final Log logger = LogFactory.getLog(PsiSellInDataSourceImpl.class);

	@Resource
	ProductSeriesRepository productSeriesRepository;
	
	@Resource
	PsiSellInDataSrouceRepository psiSellInDataSrouceRepository;
	
	@Resource
	ProductRepository productRepository;
	
	@Resource
	CommCityRepository commCityRepository;
	
	@Resource
	PsiSellInService psiSellInService;
	
	@Resource
	PsiSellInRepository psiSellInRepository;
	
	@Resource
	CommService commService;
	
	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInDataSourceService#sellInInput(java.io.InputStream)
	 */
	@Override
	public List<PsiSellInDatasource> sellInInput(InputStream inputStream,String channelType) {
		List<PsiSellInDatasource> list = new ArrayList<PsiSellInDatasource>();
		try {
			Workbook wb = WorkbookFactory.create(inputStream);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			String firstCell = sheet.getRow(0).getCell(0).toString();
			//如果是飞生类型导入，判断导入的excel是否是飞生的模板
			if(channelType.equals("1")){
				if(!"Delivery".equals(firstCell))
					return null;
			}
			//如果是越海类型导入，判断导入的excel是否是越海的模板
			if(channelType.equals("2")){
				if(!"序号".equals(firstCell))
					return null;
			}
			for (int i = 1; i < rowNum+1; i++) {
				PsiSellInDatasource bean = new PsiSellInDatasource(); 
				if(Integer.parseInt(channelType)==PsiSellInDatasource.CHANNEL_TYPE_MMD){
					bean.setDelivery(sheet.getRow(i).getCell(0)==null?"":sheet.getRow(i).getCell(0).toString());
					bean.setItem(sheet.getRow(i).getCell(1)==null?"":sheet.getRow(i).getCell(1).toString());
					bean.setMaterial(sheet.getRow(i).getCell(2)==null?"":sheet.getRow(i).getCell(2).toString());
					bean.setDlvQty(StringUtil.formatDlvqtyString(sheet.getRow(i).getCell(3).toString()));
					bean.setSu(sheet.getRow(i).getCell(4)==null?"":sheet.getRow(i).getCell(4).toString());
					bean.setCreatedon(TimeTool.shortstrToDateReplace_dian(sheet.getRow(i).getCell(5).toString()));
					bean.setVolume(sheet.getRow(i).getCell(6)==null?"":sheet.getRow(i).getCell(6).toString());
					bean.setVun(sheet.getRow(i).getCell(7)==null?"":sheet.getRow(i).getCell(7).toString());
					bean.setSoldTo(sheet.getRow(i).getCell(8)==null?"":sheet.getRow(i).getCell(8).toString());
					bean.setNameofsoldToparty(sheet.getRow(i).getCell(9)==null?"":sheet.getRow(i).getCell(9).toString());
					bean.setShipTo(sheet.getRow(i).getCell(10)==null?"":sheet.getRow(i).getCell(10).toString());
					bean.setNameoftheshipToparty(sheet.getRow(i).getCell(11)==null?"":sheet.getRow(i).getCell(11).toString());
					bean.setShipToL(sheet.getRow(i).getCell(12)==null?"":sheet.getRow(i).getCell(12).toString());
					bean.setDlvTy(sheet.getRow(i).getCell(13)==null?"":sheet.getRow(i).getCell(13).toString());
					bean.setShPt(sheet.getRow(i).getCell(14)==null?"":sheet.getRow(i).getCell(14).toString());
					bean.setArea(sheet.getRow(i).getCell(15)==null?"":sheet.getRow(i).getCell(15).toString());
					bean.setCity(sheet.getRow(i).getCell(16)==null?"":sheet.getRow(i).getCell(16).toString());
					bean.setProvince(sheet.getRow(i).getCell(17)==null?"":sheet.getRow(i).getCell(17).toString());
					bean.setSize(sheet.getRow(i).getCell(18)==null?"":sheet.getRow(i).getCell(18).toString());
					bean.setAhips(sheet.getRow(i).getCell(19)==null?"":sheet.getRow(i).getCell(19).toString());
					bean.setCreatetime(new Date());
				}else{
					bean.setSeqNumber(StringUtil.formatDlvqtyString(sheet.getRow(i).getCell(0).toString()));
					bean.setInputNumber(sheet.getRow(i).getCell(1)==null?"":sheet.getRow(i).getCell(1).toString());
					bean.setRepositoryType(sheet.getRow(i).getCell(2)==null?"":sheet.getRow(i).getCell(2).toString());
					bean.setCity(sheet.getRow(i).getCell(3)==null?"":sheet.getRow(i).getCell(3).toString());
					bean.setNameoftheshipToparty(sheet.getRow(i).getCell(4)==null?"":sheet.getRow(i).getCell(4).toString());
					if(sheet.getRow(i).getCell(5)!=null){
						bean.setCreatedon(sheet.getRow(i).getCell(5).getDateCellValue());
					}
					bean.setProduct(sheet.getRow(i).getCell(6)==null?"":sheet.getRow(i).getCell(6).toString());
					bean.setMaterial(sheet.getRow(i).getCell(7)==null?"":sheet.getRow(i).getCell(7).toString());
					bean.setDlvQty(StringUtil.formatDlvqtyString(sheet.getRow(i).getCell(8).toString()));
					bean.setSize(sheet.getRow(i).getCell(9)==null?"":sheet.getRow(i).getCell(9).toString());
					bean.setAhips(sheet.getRow(i).getCell(10)==null?"":sheet.getRow(i).getCell(10).toString());
				}
				bean.setChannelType(Integer.parseInt(channelType));
				list.add(bean);
			}
		} catch (InvalidFormatException e) {
			logger.debug("读取Excell 出错！");
			e.printStackTrace();
		} catch (IOException e) {
			logger.debug("读取Excell 发生异常！");
			e.printStackTrace();
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInDataSourceService#validateProduct(java.util.List)
	 */
	@Override
	public String validatedata(List<PsiSellInDatasource> datalist) {
		List<String> pnamelist = productRepository.queryAllProductName(Product.NOT_DELETE);
		List<String> cnameList  = commCityRepository.queryAllCityName(CommCity.NOT_DELETE);
		for (int i = 0; i < datalist.size(); i++) {
			if(StringUtils.isBlank(datalist.get(i).getMaterial())){
				return "第"+(i+2)+"行型号为空，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
			if(datalist.get(i).getCreatedon()==null){
				return "第"+(i+2)+"行日期为空，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
			if(StringUtils.isBlank(datalist.get(i).getCity())){
				return "第"+(i+2)+"行城市为空，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
			if(!pnamelist.contains(datalist.get(i).getMaterial())){
				return "第"+(i+2)+"行的型号 "+datalist.get(i).getMaterial()+" 在系统内不存在，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
			if(!cnameList.contains(datalist.get(i).getCity())){
				return "第"+(i+2)+"行的城市 "+datalist.get(i).getCity()+" 在系统内不存在，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
			if(commService.findAgentByCity(datalist.get(i).getCity())==null){
				return "第"+(i+2)+"行的城市 "+datalist.get(i).getCity()+" 在系统中没有找到对应的代理商，导入失败，请仔细检查数据确认无误后再进行导入！";
			}
		}
		return null;
	}


	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInDataSourceService#addBatchSellIn(java.util.List)
	 */
	@Override
	public void addBatchSellInDataSource(List<PsiSellInDatasource> datalist) {
		 psiSellInDataSrouceRepository.save(datalist);
	}

	/* (non-Javadoc)
	 * @see com.mmd.mssp.service.PsiSellInDataSourceService#addBatchData()
	 */
	@Override
	public void addBatchData(List<PsiSellInDatasource> datalist,String channelType) {
		List<PsiSellInDatasource> datasourcesList  = psiSellInDataSrouceRepository.findCurrentMonthDataList(commService.getCurrentMonthFisrtDay(),new Date(),Integer.valueOf(channelType));
		List<PsiSellIn> sellInList = psiSellInRepository.findCurrentMonthSellInList(commService.getCurrentMonthFisrtDay(),new Date(),Integer.valueOf(channelType));
		//先删除当前月到今天的所有数据，在批量将导入的数据插入数据库
		psiSellInDataSrouceRepository.delete(datasourcesList);
		psiSellInRepository.delete(sellInList);
		
		//批量添加sell in源数据
		this.addBatchSellInDataSource(datalist);
		//批量添加sell in数据
		psiSellInService.addBatchPsiSellIn(datalist); 
		
	}

}
