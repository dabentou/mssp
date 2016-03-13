<%@page import="com.mmd.mssp.comm.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>sellout录入</title>
  <style>
  #myTable  thead {display:block; padding-right:17px;}
  #myTable td,#myTable th { width:128px; height:40px; display:inline-block;}
  </style>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
     <div class="content">
      <div class="filter">
        <form class="form-inline" action="${ROOT }/psi/sellout/query">
        <div class="form-group">
            <label>数据日期：</label>
            <span><fmt:formatDate value="${datadate }" pattern="yyyy-MM-dd" /> </span>
            <!-- <input id="d12" type="text" />
			<img onclick="WdatePicker({el:'d12'})" src="../images/datePicker.gif" width="16" height="22" align="absmiddle"> -->
          </div>
          <div class="form-group">
            <label>&nbsp;&nbsp;数据类型：</label>
            <select class="select form-control" name="channelType" id="channelType">
                <option value="1"  ${channelType=='1'?'selected':'' }>飞生Sell out录入</option>
                <option value="2"  ${channelType=='2'?'selected':'' }>越海sell out录入</option>
              </select>
          </div>
         <!--  <div class="form-group">
            <label>&nbsp;&nbsp;型号：</label>
            <input type="text" class="form-control text" />
          </div> -->
          <div class="form-group">
            <label>&nbsp;&nbsp;当前代理商：</label>
         	<%-- ${sessionScope.user.commAgent.irName}   --%>
	     		<select class="form-control text" name="agentId">
		            <c:forEach items="${commAgents}" var="item">
						<option value="${item.id}" <c:if test="${item.id==agentId}">selected</c:if>>${item.irName}</option>
		     		</c:forEach>
	     		</select>
          </div>
          &nbsp;&nbsp;<input type="submit"  onclick="showLoading()" value="查询" class="button button-lightblue" />
          <div class="form-group"  style="float: right;">
            <label></label>
          <input id="addSellOut" type="button" value="保存修改" class="button button-lightblue" />
          </div>
          
        </form>
      </div>
      <table class="table table-hover maintable"  id="myTable"  style="display:block;">
        <thead>
          <tr>
            <th>型号 <span style="font-weight: normal;"><input id="productFilter"  type="text"  style="width: 80px;" placeholder="型号过滤" /></span></th>
            <th>预估销量</th>
            <th>累计Sell In</th>
            <th>累计Sell Out</th>
            <th>当前库存（库存+在途）</th>
            <th>Sell Out数量</th>
            <th>寸别-1</th>
            <th>寸别-2</th>
            <th>面板</th>
          </tr>
          	 <c:set var="estimateVolumeSum"  value="0" />
     		 <c:set var="totalSellinSum"  value="0" />
     		 <c:set var="totalSelloutSum"  value="0" />
     		 <c:set var="currentInventorySum"  value="0" />
     		 <c:set var="selloutVolumeSum"  value="0" />
	     	<tr>
	     			<th>合计</th>
	     			<th id="estimateVolumeSum"></th>
	     			<th id="totalSellinSum"></th>
	     			<th id="totalSelloutSum"></th>
	     			<th id="currentInventorySum"></th>
	     			<th id="selloutVolumeSum"></th>
	     			<th>-</td>
	     			<th>-</td>
	     			<th>-</td>
	     		</tr>
        </thead>
        <tbody style="display:block; max-height:800px; overflow-y:scroll;">
     	<c:forEach items="${list}" var="item">
     		<tr id="${item.product.name}"  align="center">
     			<td>${item.product.name}</td>
     			<td class=".yugu">${item.estimateVolume}</td>
     			<td class="sellin">${item.totalSellin}</td>
     			<td class="sellout">${item.totalSellout}</td>
     			<td class="kucun">${item.currentInventory}</td>
     			<td class="inputSellout"><input type="text"  id="inputSellOutVal" name="inputSellout"  onblur="checkSellOut(this)" style="width: 100px;"  value="${item.selloutVolume==0?'':item.selloutVolume }"  placeholder="0"/></td>
     			<td>${item.product.size1 }</td>
     			<td>${item.product.size2 }</td>
     			<td>${item.product.pannel.name }</td>
     			
     			<c:set value="${estimateVolumeSum + item.estimateVolume}" var="estimateVolumeSum" />
     			<c:set value="${totalSellinSum + item.totalSellin}" var="totalSellinSum" />
     			<c:set value="${totalSelloutSum + item.totalSellout}" var="totalSelloutSum" />
     			<c:set value="${currentInventorySum + item.currentInventory}" var="currentInventorySum" />
     			<c:set value="${selloutVolumeSum + item.selloutVolume}" var="selloutVolumeSum" />
     		</tr>
     	</c:forEach>
          <tr>
            <th style="width:384px;">月度目标总量 ：${sellInEstimateByMonth.sellInVolume }</th>
            <th style="width:384px;" id="sellinp"> sellIn进度：</th>
            <th style="width:384px;"  id="selloutp">sellOut进度：</th>
          </tr>
          <tr>
            <th style="width:384px;">中尺寸sellin：${tmSizeSellin}</th>
            <th style="width:384px;">中尺寸sellout：${tmSizeSellout}</th>
            <th style="width:384px;">中尺寸库存：${tmSizeInventory}</th>
          </tr>
          <tr>
            <th style="width:384px;">大尺寸sellin：${tbSizeSellin}</th>
            <th style="width:384px;">大尺寸sellout：${tbSizeSellout}</th>
            <th style="width:384px;">大尺寸库存：${tbSizeInventory}</th>
          </tr>
          <tr>
            <th style="width:384px;">超大尺寸sellin：${tebSizeSellin}</th>
            <th style="width:384px;">超大尺寸sellout：${tebSizeSellout}</th>
            <th style="width:384px;">超大尺寸库存：${tebSizeInventory}</th>
          </tr>
          <tr>
          	<td  style="width:1152px;" ><a href="#" onclick="allShow()">全部展开 <img src="${ROOT }/images/down0.jpg" width="15px" height="15px"></img></a></td>
          </tr>
        </tbody>
      </table>
      <a href="javascript:void(0)" id="backtop">返回顶部</a>
    </div>
  </div>
</body>
<script type="text/javascript" >
if(!${isUpload}){
	alert("月度目标量未上传，请联系管理员！");
}
$(function(){
	showLoading=function(){
		$.blockUI({ message: '<h7><img src="${ROOT}/images/preload.gif" /> <br />正在加载，请稍后...</h7>' });
	}
	
	var estimateVolumeSum = ${estimateVolumeSum};
	var totalSellinSum = ${totalSellinSum};
	var totalSelloutSum = ${totalSelloutSum};
	var currentInventorySum = ${currentInventorySum};
	var selloutVolumeSum = ${selloutVolumeSum};
	
	$("#estimateVolumeSum").html(estimateVolumeSum);
	$("#totalSellinSum").html(totalSellinSum);
	$("#totalSelloutSum").html(totalSelloutSum);
	$("#currentInventorySum").html(currentInventorySum);
	$("#selloutVolumeSum").html(selloutVolumeSum);

 	
	$('#productFilter').bind('input propertychange', function() {
		var pf = $("#productFilter").val();
		var  list= $("#myTable tbody tr").find("td:eq(0)");
		var tsellin = 0;
		var tsellout = 0;
		
		//合计专用
		var tyugu=0;
		var tkucun=0;
		var inputSellout=0;
		  for(var i =0;i<list.length-1;i++){
			var tr =$(list[i]).parent();
			if($(list[i]).text().indexOf(pf.toUpperCase())<0){
				$(tr).hide();
			}else{
				$(tr).show();
				tsellin = tsellin*1 + $(tr).find(".sellin").text()*1;
				tsellout = tsellout*1 + $(tr).find(".sellout").text()*1;
				
				//合计专用
				tyugu = tyugu*1 + $(tr).find(".yugu").text()*1;
				tkucun = tkucun*1 + $(tr).find(".kucun").text()*1;
				inputSellout = inputSellout*1 + $(tr).find(".inputSellout").find("#inputSellOutVal").val()*1; 
				
				$("#estimateVolumeSum").html(tyugu);
				$("#totalSellinSum").html(tsellin);
				$("#totalSelloutSum").html(tsellout);
				$("#currentInventorySum").html(tkucun);
				 $("#selloutVolumeSum").html(inputSellout); 
			}
		}
		  var jindusellin=tsellin/${sellInEstimateByMonth.sellInVolume};
		  var jindusellout=tsellout/${sellInEstimateByMonth.sellInVolume};
		$("#sellinp").text("sellIn进度:" + jindusellin.toFixed(2)+"%");
		$("#selloutp").text("sellOut进度:" + jindusellout.toFixed(2)+"%");
	});
	
	allShow=function(){
		var  list= $("#myTable tbody tr").find("td:eq(0)");
		if(list.length<=1){
			alert("未查询到数据！");
			return ;
		}
		 for(var i =0;i<list.length-1;i++){
			var tr =$(list[i]).parent();
			$(tr).show();
		}  
		 $("#estimateVolumeSum").html(estimateVolumeSum);
			$("#totalSellinSum").html(totalSellinSum);
			$("#totalSelloutSum").html(totalSelloutSum);
			$("#currentInventorySum").html(currentInventorySum);
			$("#selloutVolumeSum").html(selloutVolumeSum);
	}
	
	checkSellOut=function(dom){
		var the = $(dom);
		if(isNaN(the.val())){
			alert("Sell Out数量只能为数字");
			the.focus();
			return;
		}
		var pi = the.parent().prev().html();
		if(the.val()>parseInt(pi)){
			alert("Sell Out数量不能大于库存");
			the.focus();
			return;
		}
	}
	
	$("#addSellOut").click(function(){
		var sv = $("input[name='inputSellout']");
		var jsonStr = "{";
		var count=0;	
		for(var i =0;i<sv.length;i++){
			if($(sv[i]).val()!=0){
				jsonStr+="\""+$(sv[i]).parent().parent().attr("id")+"\"";
				jsonStr+=":";
				jsonStr+=$(sv[i]).val();
				jsonStr+=",";
				count++;
			}
		}
		if(count<=0){
			alert("至少填入一项sell out数量才能保存");
			return ;
		}
		jsonStr=jsonStr.substring(0,jsonStr.length-1);//删除最后一个逗号 
		jsonStr+="}";
		var agentId = $("select[name='agentId'] option:selected").val();
		$.ajax({
			type : "POST",
			url : "${ROOT}/psi/sellout/input",
			data : "inputVol=" + jsonStr+"&channelType="+$("#channelType").val()+"&agentId="+agentId,
			dataType : "json",
			success : function(json) {
				if(json.status=="OK"){
					alert("录入成功！");
				}else{
					alert("录入失败！");
				}
			}
		}); 
	});
	
})
</script>
<script type="text/javascript">
function backSorH(){
    h = $(window).height();
    t = $(document).scrollTop();
    if(t > h){
        $('#backtop').show();
    }else{
        $('#backtop').hide();
    }
}
$(document).ready(function(){
    //返回顶部
    backSorH();
    $('#backtop').click(function(){
        $(document).scrollTop(0);
    });
    var tsellin = 0;
    var tsellout = 0;
    $(".maintable .sellin").each(function(){
    	tsellin = tsellin*1 + $(this).text()*1;
    });
    $(".maintable .sellout").each(function(){
    	tsellout = tsellout*1 + $(this).text()*1;
    });
	$("#sellinp").text("sellIn进度：" + tsellin/${sellInEstimateByMonth.sellInVolume});
	$("#selloutp").text("sellOut进度：" + tsellout/${sellInEstimateByMonth.sellInVolume});
});
$(window).scroll(function(e){
    backSorH();
});
</script>
</html>