 <%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
 <div class="menu">
    <div class="wrap">
      <ul class="first-menu clearfix" id="first-menu">
        <li>
        <shiro:hasPermission name="psiParent"> 
           <a class="psi" href="${ROOT }/psi" id="psi">PSI<br />(${sessionScope.psiToDoNum })</a>
          <ul class="second-menu clearfix">
          <shiro:hasPermission name="sellin:*"> 
            <li><a href="${ROOT }/psi/sellin.html"><i class="iconmenu iconmenu07"></i><span>sellin录入</span></a></li>
             </shiro:hasPermission>
            <shiro:hasPermission name="sellout:*"> 
            <li><a href="${ROOT }/psi/sellout.html"><i class="iconmenu iconmenu07"></i><span>sellout录入</span></a></li>
             </shiro:hasPermission>
            <shiro:hasPermission name="selloutupdate:*"> 
            <li><a href="${ROOT }/psi/selloutupdate.html"><i class="iconmenu iconmenu08"></i><span>sellout库存确认</span></a></li>
             </shiro:hasPermission>
            <shiro:hasPermission name="webEstByM:*"> 
             <li><a href="${ROOT }/psi/estimate.html"><i class="iconmenu iconmenu09"></i><span>月度销售预估</span></a></li>
              </shiro:hasPermission>
             <shiro:hasPermission name="psiReport:*"> 
            <li><a href="${ROOT }/psi/report.html"><i class="iconmenu iconmenu06"></i><span>数据查询&导出</span></a></li>
             </shiro:hasPermission>
          </ul>
          </shiro:hasPermission>
        </li>
         <li>
         <shiro:hasPermission name="iretailParent"> 
          <a class="iretail" href="${ROOT }/iretail" id="iretail">iRetail<br />(${sessionScope.iRetailToDoNum })</a>
          <ul class="second-menu clearfix">
          <shiro:hasPermission name="webr01:*"> 
          	<li><a href="${ROOT }/iretail/project/applylist.html?type=R01"><i class="iconmenu iconmenu05"></i><span>R01零售店装修</span></a></li>
          	 </shiro:hasPermission>
          	<shiro:hasPermission name="poApply:*"> 
            <li><a href="${ROOT }/iretail/project/applyMenuList.html?idataType=1"><i class="iconmenu iconmenu02"></i><span>PO申请</span></a></li>
             </shiro:hasPermission>
            <shiro:hasPermission name="poCheck:*"> 
            <li><a href="${ROOT }/iretail/project/applyMenuList.html?idataType=2"><i class="iconmenu iconmenu03"></i><span>PO核销</span></a></li>
             </shiro:hasPermission>
            <shiro:hasPermission name="download:*"> 
            <li><a href="${ROOT }/iretail/notice/download.html"><i class="iconmenu iconmenu02"></i><span>下载表格</span></a></li>
             </shiro:hasPermission>
          </ul>
          </shiro:hasPermission>
        </li>
        <li>
        <shiro:hasPermission name="b2bParent">
          <a class="b2b" href="${ROOT }/b2b" id="b2b">B2B<br />(${sessionScope.b2bToDoNum })</a>
          <ul class="second-menu clearfix">
          	<shiro:hasPermission name="b2bSecondAgent:*">
            <li><a href="${ROOT }/b2b/agent/list.html"><i class="iconmenu iconmenu10"></i><span>添加二级代理商</span></a></li>
            </shiro:hasPermission>
          	<shiro:hasPermission name="b2bCustomer:*">
            <li><a href="${ROOT }/b2b/customer/list.html"><i class="iconmenu iconmenu10"></i><span>添加客户</span></a></li>
            </shiro:hasPermission>
          	<shiro:hasPermission name="b2bInput:*">
            <li><a href="${ROOT }/b2b/project/input.html"><i class="iconmenu iconmenu04"></i><span>项目报备</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2bProList1:*">
            <li><a href="${ROOT }/b2b/project/list.html?type=1"><i class="iconmenu iconmenu05"></i><span>项目列表</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2bProList2:*">
            <li><a href="${ROOT }/b2b/project/list.html?type=2"><i class="iconmenu iconmenu03"></i><span>项目核销</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2bProList3:*">
            <li><a href="${ROOT }/b2b/project/list.html?type=3"><i class="iconmenu iconmenu06"></i><span>返利查询</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2bReport:*">
            <li><a href="${ROOT }/b2b/project/report.html"><i class="iconmenu iconmenu06"></i><span>数据查询&导出</span></a></li>
            </shiro:hasPermission>
          </ul>
          </shiro:hasPermission>
        </li>
         <li>
         <shiro:hasPermission name="B2IParent">
          <a class="b2i" href="${ROOT }/b2i" id="b2i">B2I<br />(${sessionScope.b2iToDoNum })</a>
          <ul class="second-menu clearfix">
            <shiro:hasPermission name="b2iSecondAgent:*">
            <li><a href="${ROOT }/b2i/agent/list.html"><i class="iconmenu iconmenu10"></i><span>添加二级代理商</span></a></li>
            </shiro:hasPermission>
          	<shiro:hasPermission name="b2iCustomer:*">
            <li><a href="${ROOT }/b2i/customer/list.html"><i class="iconmenu iconmenu10"></i><span>添加客户</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2iProApply:*">
            <li><a href="${ROOT }/b2i/project/apply"><i class="iconmenu iconmenu04"></i><span>项目申请</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2iProList1:*">
            <li><a href="${ROOT }/b2i/project/list.html?type=1"><i class="iconmenu iconmenu05"></i><span>项目列表</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2iProList2:*">
            <li><a href="${ROOT }/b2i/project/list.html?type=2"><i class="iconmenu iconmenu03"></i><span>项目核销</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2iProList3:*">
            <li><a href="${ROOT }/b2i/project/list.html?type=3"><i class="iconmenu iconmenu06"></i><span>返利查询</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2iReport:*">
           <li><a href="${ROOT }/b2i/project/report.html?applyTempType=1"><i class="iconmenu iconmenu06"></i><span>数据查询&导出</span></a></li>
           </shiro:hasPermission>
          </ul>
           </shiro:hasPermission>
        </li>
        <li>
        <shiro:hasPermission name="B2CParent">
          <a class="b2c" href="${ROOT }/b2c" id="b2c">B2C<br />(${sessionScope.b2cToDoNum })</a>
          <ul class="second-menu clearfix">
          <shiro:hasPermission name="b2cAddPro:*">
            <li><a href="${ROOT }/b2c/project/apply.html"><i class="iconmenu iconmenu07"></i><span>添加项目</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2cProList:*">
            <li><a href="${ROOT }/b2c/project/list.html"><i class="iconmenu iconmenu04"></i><span>未完结项目查询</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2cReport:*">
            <li><a href="${ROOT }/b2c/report.html"><i class="iconmenu iconmenu06"></i><span>已完结项目查询</span></a></li>
            </shiro:hasPermission>
            <shiro:hasPermission name="b2cRetProject:*">
            <li><a href="${ROOT }/b2c/retProject.html"><i class="iconmenu iconmenu06"></i><span>退回项目查询</span></a></li>
            </shiro:hasPermission>
          </ul>
          </shiro:hasPermission>
        </li>
      </ul>
    </div>
  </div>
<script type="text/javascript">
$(function () {
    var show = function (dom) {
      var the = $(dom);
      if (!dom)
        return;
      the.css("font-weight", "bold");
      the.parent().addClass("current");
      the.parent().parent().parent().addClass("current");
    };
    var the = false;
    var ckk = "menu_index";
    var menuFlag = location.href.split("?")[0];
    var menuParamFlag;
    if(location.href.split("?").length==2){
    	var param = location.href.split("?")[1].split("&")[0];
    	menuParamFlag = menuFlag+"?"+param;
    }
    var root = "${ROOT}"+"/";
    if(menuFlag==root+"psi"){
    	setCookie(ckk, null, {path: "/"});
    	$("#psi").parent().addClass("current");
    }else if(menuFlag==root+"iretail"){
    	setCookie(ckk, null, {path: "/"});
    	$("#iretail").parent().addClass("current");
    }else if(menuFlag==root+"b2b"){
    	setCookie(ckk, null, {path: "/"});
    	$("#b2b").parent().addClass("current");
    }else if(menuFlag==root+"b2i"){
    	setCookie(ckk, null, {path: "/"});
    	$("#b2i").parent().addClass("current");
    }else if(menuFlag==root+"b2c"){
    	setCookie(ckk, null, {path: "/"});
    	$("#b2c").parent().addClass("current");
    }
    $("#first-menu ul a").each(function (i) {
        var hrf = $(this).attr("href").split("?")[0];
        var hrfParam = $(this).attr("href");
        if (hrf === menuFlag) {
        	setCookie("iretail_menu_index", null, {path: "/"});
        	if(menuParamFlag!=undefined){
        		if(hrfParam==menuParamFlag){
	                setCookie(ckk, i, {path: "/"});
	                the = $(this);
	                show(the);
        		}
        	}else{
                setCookie(ckk, i, {path: "/"});
                the = $(this);
                show(the);
        	}
        }
      });
    var ck = getCookie(ckk);
    if (!the && ck) {
      show($("#first-menu ul a")[ck]);
    }
    
    $("#first-menu li a").click(function () {
        $.blockUI({ message: '<h7><img src="${ROOT}/images/preload.gif" /> <br />正在加载，请稍后...</h7>' });
      });
  });
</script>