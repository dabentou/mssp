package com.mmd.mssp.controller;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmd.mssp.comm.CommonConfig;
import com.mmd.mssp.domain.ApproveTemplate;
import com.mmd.mssp.domain.CommAgent;
import com.mmd.mssp.domain.CommArea;
import com.mmd.mssp.domain.IretailProject;
import com.mmd.mssp.domain.IretailStoreLevel;
import com.mmd.mssp.domain.ProcessStep;
import com.mmd.mssp.domain.vo.Paging;
import com.mmd.mssp.domain.vo.TitleMapper;
import com.mmd.mssp.service.CommAreaService;
import com.mmd.mssp.service.CommService;
import com.mmd.mssp.service.ExcelService;
import com.mmd.mssp.service.IretailProjectService;
import com.mmd.mssp.service.IretailStoreLevelService;
import com.mmd.mssp.service.PDFService;
import com.mmd.mssp.service.ProcessStepService;
import com.mmd.mssp.util.DateUtils;

@Controller
public class IretailReportController {

	private static final int PAGE_SIZE = 50;
	
	@Resource
	ExcelService excelService;
	@Resource
	CommService commService;
	@Resource
	IretailProjectService iretailProjectService;
	@Resource
	ProcessStepService processStepService;
	@Resource
	CommAreaService commAreaService;
	@Resource
	IretailStoreLevelService iretailStoreLevelService;
	
	@Resource
	PDFService pdfService;
	
	@RequestMapping("/admin/iretail/report/list.html")
	public String listAll(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		Paging<IretailProject> paging = iretailProjectService.findAllIretailProject(page, PAGE_SIZE);
		List<CommArea> areaList = commAreaService.listAll(Boolean.FALSE);
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		List<ProcessStep> processStepList = processStepService.findByTemp(temp);//默认是iretail的模板审批步骤
		request.setAttribute("paging", paging);
		request.setAttribute("areaList", areaList);
		request.setAttribute("processStepList", processStepList);
		return "/admin/iretail/report/list.jsp";
	}
	
	@RequestMapping("/admin/iretail/report/search.html")
	public String searchProject(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,String type,Integer[] areaId,Integer[] processStepId) throws Exception {
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		List<IretailStoreLevel> storeLevels = iretailStoreLevelService.listByIsDelete(Boolean.FALSE);
		request.setAttribute("storeLevels", storeLevels);
		request.setAttribute("enabled", false);
		return "/admin/iretail/report/list.jsp";
	}
	
	@RequestMapping("/admin/iretail/report/delete-{id}")
	public String deleteProject(HttpServletRequest request,@PathVariable Integer id){
		IretailProject project = iretailProjectService.findProjectById(id);
		project.setIsDelete(1);
		iretailProjectService.save(project);
		return "redirect:/admin/iretail/report/list.html";
	}
	
	@RequestMapping("/admin/iretail/report/r01.html")
	public String r01list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R01";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		//if("R01".equals(type)){
			List<IretailStoreLevel> storeLevels = iretailStoreLevelService.listByIsDelete(Boolean.FALSE);
			request.setAttribute("storeLevels", storeLevels);
			request.setAttribute("enabled", false);
		//}
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r02.html")
	public String r02list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R02";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r03.html")
	public String r03list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R03";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r04.html")
	public String r04list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R04";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r05.html")
	public String r05list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R05";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r06.html")
	public String r06list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R06";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r07.html")
	public String r07list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R07";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r08.html")
	public String r08list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R08";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r09.html")
	public String r09list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R09";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r10.html")
	public String r10list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R10";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r11.html")
	public String r11list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R11";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	@RequestMapping("/admin/iretail/report/r12.html")
	public String r12list(HttpServletRequest request,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId) throws Exception {
		String type = "R12";
		list(request,type,page,applyTimeStart,applyTimeEnd,ppn,areaId,processStepId);
		return "/admin/iretail/report/list.jsp";
	}
	/*public void list(HttpServletRequest request,String type,@RequestParam(required=false) Integer page){
		CommAgent commAgent = commService.findCurrentAgent(request);
		Paging<IretailProject> paging = iretailProjectService.findIretailProjectByTypeAndAgent(type,commAgent,page, PAGE_SIZE);
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		List<ProcessStep> processSteps = processStepService.findByTemp(temp);
		List<CommArea> areas = commAreaService.listAll(Boolean.FALSE);
		request.setAttribute("enabled", true);
		request.setAttribute("paging", paging);
		request.setAttribute("type", type);
		request.setAttribute("areas", areas);
		request.setAttribute("processSteps", processSteps);
	}*/
	public void list(HttpServletRequest request,String type,@RequestParam(required=false) Integer page,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId)  throws Exception{
		CommAgent commAgent = commService.findCurrentAgent(request);
		
		ApproveTemplate temp= commService.findTmpeType(CommonConfig.getTEMP_IRETAIL_PROJECT());
		List<ProcessStep> processStepList = processStepService.findByTemp(temp);
		List<CommArea> areaList = commAreaService.listAll(Boolean.FALSE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDate1;
		Date ayDate2;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		if(!(applyTimeStart.equals("")) && applyTimeStart!=null){
			ayDate1 = sdf.parse(applyTimeStart);
		}else{
			ayDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(!(applyTimeEnd.equals("")) && applyTimeEnd!=null){
			ayDate2 = sdf.parse(applyTimeEnd);
		}else{
			ayDate2 = calendar.getTime();
		}
		Paging<IretailProject> paging = iretailProjectService.findIretailProjectBySearch(ayDate1, ayDate2, type, ppn, areaId, processStepId, page, PAGE_SIZE);
		request.setAttribute("applyTimeStart", ayDate1);
		request.setAttribute("applyTimeEnd", ayDate2);
		request.setAttribute("enabled", true);
		request.setAttribute("paging", paging);
		request.setAttribute("type", type);
		request.setAttribute("ppn", ppn);
		request.setAttribute("areaList", areaList);
		request.setAttribute("areaId", areaId);
		request.setAttribute("processStepId", processStepId);
		request.setAttribute("processStepList", processStepList);
	}
	@RequestMapping(value="/admin/iretail/report/excel")
	public String export(HttpServletRequest request,String type,String applyTimeStart,String applyTimeEnd,String ppn,Integer[] areaId,Integer[] processStepId,HttpServletResponse response) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date ayDate1;
		Date ayDate2;
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH),0,0,0);
		if(!(applyTimeStart.equals("")) && applyTimeStart!=null){
			ayDate1 = sdf.parse(applyTimeStart);
		}else{
			ayDate1 = DateUtils.getCurrentMonthFisrtDay();
		}
		if(!(applyTimeEnd.equals("")) && applyTimeEnd!=null){
			ayDate2 = sdf.parse(applyTimeEnd);
		}else{
			ayDate2 = calendar.getTime();
		}
		List<IretailProject> iretailProjectList = iretailProjectService.findIretailProjectByType(type);
		List<Map> mapList = iretailProjectService.getMap(iretailProjectList,type);
		
		response.setContentType("application/vnd.ms-excel;charset=GBK");  
        response.setHeader("Pragma", "no-cache");  
        response.setHeader("Cache-Control", "no-cache");  
        response.setDateHeader("Expires", 0);  
        response.setHeader("Content-Disposition", "attachment;filename=test.csv");  
        response.setCharacterEncoding("GBK");
        OutputStreamWriter osw = null;
        try {
        	osw = new OutputStreamWriter(response.getOutputStream(), "GBK");
        	if("R01".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r01ReportMapper, mapList));
        	}else if("R02".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r02ReportMapper, mapList));
        	}else if("R03".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r03ReportMapper, mapList));
        	}else if("R04".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r04ReportMapper, mapList));
        	}else if("R05".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r05ReportMapper, mapList));
        	}else if("R06".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r06ReportMapper, mapList));
        	}else if("R07".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r07ReportMapper, mapList));
        	}else if("R08".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r08ReportMapper, mapList));
        	}else if("R09".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r09ReportMapper, mapList));
        	}else if("R10".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r10ReportMapper, mapList));
        	}else if("R11".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r11ReportMapper, mapList));
        	}else if("R12".equals(type)){
        		osw.write(this.excelService.convert2Cvs(TitleMapper.r12ReportMapper, mapList));
        	}
		} catch (IOException e) {
			e.printStackTrace();
		}  finally{
			try {
				osw.flush();
				osw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
