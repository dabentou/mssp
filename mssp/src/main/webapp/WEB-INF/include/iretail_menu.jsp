<%@page contentType="text/html" pageEncoding="UTF-8"%>
<ul class="nav nav-tabs" id="iretail_meun">
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R02">R02零售店样机</a></li>
	  <li role="presentation"><a <%-- href="${ROOT }/iretail/project/applylist.html?type=R03" --%> style="color:grey;">R03卖场绑定</a></li>
	  <li role="presentation"><a <%-- href="${ROOT }/iretail/project/applylist.html?type=R04" --%> style="color:grey;">R04宣传品</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R05">R05零售商会议</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R06">R06促销员工资</a></li>
	  <li role="presentation"><a <%-- href="${ROOT }/iretail/project/applylist.html?type=R07" --%> style="color:grey;">R07区域媒体广告</a></li>
	  <li role="presentation"><a <%-- href="${ROOT }/iretail/project/applylist.html?type=R08" --%> style="color:grey;">R08路演</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R09">R09终端用户促销</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R10">R10零售店支持金</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R11">R11精英网积分兑换</a></li>
	  <li role="presentation"><a href="${ROOT }/iretail/project/applylist.html?type=R12">R12门店零售奖励</a></li>
</ul>
<style type="text/css">
	#iretail_meun li {
		margin-right: -10px;
	}
</style>
<script type="text/javascript">
	$(function () {
		var menuFlag = location.href.split("?")[0];
	    var menuParamFlag;
	    var ckk = "iretail_menu_index";
	    if(location.href.split("?").length==2){
	    	var param = location.href.split("?")[1].split("&")[0];
	    	menuParamFlag = menuFlag+"?"+param;
	    }
		$("#iretail_meun li a").each(function(i){
			$(this).parent().removeClass("active");
			var hrfParam = $(this).attr("href");
			if(hrfParam==menuParamFlag){
				$(this).parent().addClass("active");
				setCookie(ckk, i, {path: "/"});
			}
		});
	    var ck = getCookie(ckk);
	    if (ck) {
	    	var the = $("#iretail_meun li")[ck];
	    	$(the).addClass("active");
	    }
	  /*  if(${dataType} == 2){
		   $("#R02Check").remove();
	   } */
	});
	
	
</script>
