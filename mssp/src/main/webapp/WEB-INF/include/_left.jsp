<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
	<div class="sidebar">
       <div class="sidebar-collapse">
          <div class="sidebar-header">
             <img src="${ROOT}/images/logo.png" class="img-responsive" alt="">
         </div>
         <div class="sidebar-menu" id="sidebar-menu">
             <nav id="menu" role="navigation">
                <ul class="nav nav-sidebar" id="first-menu">
                   <li class="nav-parent">
                   <shiro:hasPermission name="comm"> 
                      <a href="#basicdata" data-toggle="collapse" aria-expanded="false"><i class="icon icon01"></i><span>基础资料维护</span></a>
                      <ul class="nav nav-children collapse" id="basicdata">
                      	<shiro:hasPermission name="city:*">      
                         	<li><a href="${ROOT}/admin/comm/city/list.html"><i class="icon icon08"></i>城市维护</a></li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="area:*">      
                        	 <li><a href="${ROOT}/admin/comm/area/list.html"><i class="icon icon08"></i>区域维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="province:*">      
                        	  <li><a href="${ROOT}/admin/comm/province/list.html"><i class="icon icon08"></i>省份维护</a></li>
                          </shiro:hasPermission>
                          <shiro:hasPermission name="pannel:*">      
                        	 <li><a href="${ROOT}/admin/comm/pannel/list.html"><i class="icon icon08"></i>面板维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="series:*">      
                       		  <li><a href="${ROOT}/admin/comm/series/list.html"><i class="icon icon08"></i>产品系列维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="proSeries:*">      
                     	    <li><a href="${ROOT}/admin/comm/proseries/list.html"><i class="icon icon08"></i>零售产品系列维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="business:*">      
                         	<li><a href="${ROOT}/admin/comm/business/list.html"><i class="icon icon08"></i>行业维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="product:*">      
                         	<li><a href="${ROOT}/admin/psi/product/list.html"><i class="icon icon08"></i>型号维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="sample:*">      
                         	<li><a href="${ROOT}/admin/comm/sample/list.html"><i class="icon icon08"></i>样机维护</a></li>
                         </shiro:hasPermission>
                     </ul>
                     </shiro:hasPermission>
                 </li>
                 <li class="nav-parent">
                 <shiro:hasPermission name="roleManage"> 
                      <a href="#role" data-toggle="collapse"><i class="icon icon01"></i><span>角色维护</span></a>
                      <ul class="nav nav-children collapse" id="role">
                    	  <shiro:hasPermission name="role:*">      
                        	 <li><a href="${ROOT}/admin/comm/role/list.html"><i class="icon icon08"></i>级别维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="user:*">      
                         	<li><a href="${ROOT}/admin/comm/user/list.html"><i class="icon icon08"></i>用户维护</a></li>
                         </shiro:hasPermission>
                     </ul>
                     </shiro:hasPermission>
                 </li>
                  
                     <shiro:hasPermission name="uploadEst"> 
		                 <li>
		                      <a href="${ROOT}/admin/psi/uploadsell.html"><i class="icon icon04"></i><span>上传销售预估模板</span></a>
		                  </li>
		                  </shiro:hasPermission>
		                  
		                  <shiro:hasPermission name="selltarget"> 
		                   <li>
		                      <a href="${ROOT}/admin/psi/selltarget.html"><i class="icon icon04"></i><span>上传销售目标量模板</span></a>
		                  </li>
		                  </shiro:hasPermission>
				                  
		                  <shiro:hasPermission name="uploadSellInEst"> 
		                  <li>
		                      <a href="${ROOT}/admin/psi/estreport.html"><i class="icon icon04"></i><span>导出销售预估</span></a>
		                  </li>
                  </shiro:hasPermission>
                  
                 <shiro:hasPermission name="agentManage"> 
                 <li>
                      <a href="${ROOT}/admin/comm/agent/list.html"><i class="icon icon04"></i><span>代理商维护</span></a>
                  </li>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="sellOutUpdate"> 
                 <li>
                  <a href="${ROOT}/admin/psi/selloutupdate/list.html"><i class="icon icon02"></i><span>Sell out修改</span></a>
                  </li>
                  </shiro:hasPermission>
               <!--    <li>
                      <a href="#"><i class="icon icon03"></i><span>代打款账号维护</span></a>
                  </li> -->
                  <shiro:hasPermission name="monthEst"> 
                  <li>
                  <a href="${ROOT}/admin/psi/estimate/listall.html"><i class="icon icon01"></i><span>月度销售预估</span></a>
                  </li>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="updateReason"> 
                  <li>
                      <a href="${ROOT}/admin/psi/reason/list.html"><i class="icon icon04"></i><span>数据修改理由维护</span></a>
                  </li>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="psiExcel">
                  <li>
                      <a href="${ROOT}/admin/psi/report.html"><i class="icon icon07"></i><span>PSI数据查询导出</span></a>
                  </li>
                  </shiro:hasPermission>
                   <shiro:hasPermission name="appTemp">
                   <li>
                      <a href="${ROOT}/admin/comm/approvetemplate/list.html"><i class="icon icon7"></i><span>审批模板定制</span></a>
                  </li>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="b2cCondition">
                  <li>
                      <a href="${ROOT}/admin/b2c/condition/list.html"><i class="icon icon07"></i><span>考核条件或目标</span></a>
                  </li>
                  </shiro:hasPermission>
                  <li class="nav-parent">
                  <shiro:hasPermission name="iretail">
                      <a href="#iretail" data-toggle="collapse"><i class="icon icon01"></i><span>Iretail</span></a>
                      <ul class="nav nav-children collapse" id="iretail">
                     	 <shiro:hasPermission name="market:*">      
                         	<li><a href="${ROOT}/admin/iretail/market/list.html"><i class="icon icon08"></i>卖场维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="material:*">     
                       	  	<li><a href="${ROOT}/admin/iretail/material/list.html"><i class="icon icon08"></i>物料维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="propagandatype:*"> 
                         	<li><a href="${ROOT}/admin/iretail/propagandatype/list.html"><i class="icon icon08"></i>宣传品类型维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="storeLevel:*"> 
                         	<li><a href="${ROOT}/admin/iretail/storeLevel/list.html"><i class="icon icon08"></i>店面级别维护</a></li>
                         </shiro:hasPermission>
                          <shiro:hasPermission name="ppnedit:*"> 
                         <li><a href="${ROOT}/admin/iretail/ppn/detail.html"><i class="icon icon08"></i>PPN单据字段维护</a></li>
                         </shiro:hasPermission>
                          <shiro:hasPermission name="budget">
		                	 <li><a href="${ROOT}/admin/iretail/budget/maintain.html"><i class="icon icon08"></i><span>预算维护</span></a></li>
		                  </shiro:hasPermission>
		                 <shiro:hasPermission name="descsetting:*"> 
                         <li><a href="${ROOT}/admin/iretail/descsetting/list.html"><i class="icon icon08"></i>装修设置维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="iretailstore:*"> 
                         <li><a href="${ROOT}/admin/iretail/iretailstore/list.html"><i class="icon icon08"></i>零售店维护</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="iretaillist:*"> 
                        	 <li><a href="${ROOT}/admin/iretail/report/list.html"><i class="icon icon08"></i>单据查询与管理</a></li>
                         </shiro:hasPermission>
                        <%--  <shiro:hasPermission name="r01:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r01.html"><i class="icon icon08"></i>R01报表</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="r02:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r02.html"><i class="icon icon08"></i>R02报表</a></li>
                         </shiro:hasPermission>
                         <shiro:hasPermission name="r03:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r03.html"><i class="icon icon08"></i>R03报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r04:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r04.html"><i class="icon icon08"></i>R04报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r05:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r05.html"><i class="icon icon08"></i>R05报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r06:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r06.html"><i class="icon icon08"></i>R06报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r07:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r07.html"><i class="icon icon08"></i>R07报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r08:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r08.html"><i class="icon icon08"></i>R08报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r09:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r09.html"><i class="icon icon08"></i>R09报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r10:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r10.html"><i class="icon icon08"></i>R10报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r11:*"> 
                         	<li><a href="${ROOT}/admin/iretail/report/r11.html"><i class="icon icon08"></i>R11报表</a></li>
                          </shiro:hasPermission>
                         <shiro:hasPermission name="r12:*"> 
                         <li><a href="${ROOT}/admin/iretail/report/r12.html"><i class="icon icon08"></i>R12报表</a></li>
                          </shiro:hasPermission> --%>
                     </ul>
                     </shiro:hasPermission>
                 </li>
                 <li class="nav-parent">
                 	<shiro:hasPermission name="b2i"> 
                      <a href="#b2i" data-toggle="collapse"><i class="icon icon01"></i><span>B2I</span></a>
                      <ul class="nav nav-children collapse" id="b2i">
                    	  <shiro:hasPermission name="applyTemplate:*">      
                        	 <li><a href="${ROOT}/admin/b2i/template/list.html"><i class="icon icon08"></i>申请模板维护</a></li>
                         </shiro:hasPermission>
                     </ul>
                     </shiro:hasPermission>
                 </li>
              </ul>
          </nav>
      </div>
    </div>  
</div>
<script type="text/javascript">
$(function () {
    var show = function (dom) {
      var the = $(dom);
      if (!dom)
        return;
      the.css({"background-color": "white", "color": "black","font-weight":"bold"});
      the.addClass("current");
      the.parent().parent().removeClass("collapse");
      the.parent().parent().prev().css("font-weight", "bold");
    };
    var menuFlag = location.href.split("?")[0];
    var the = false;
    var ckk = "menu_index";
    var ck = getCookie(ckk);
    $("#first-menu a").each(function (i) {
        var hrf = $(this).attr("href").split("?")[0];
        if (hrf === menuFlag) {
          setCookie(ckk, i, {path: "/"});
          the = $(this);
          show(the);
        }
      });
    if (!the && ck) {
      show($("#first-menu a")[ck]);
    }
  });
</script>
