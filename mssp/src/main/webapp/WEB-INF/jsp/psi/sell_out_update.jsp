<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>sellout库存确认模块</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <div class="filter">
      	<form id="form" class="form-inline" action="${ROOT }/psi/sellOutUpdate/search">
      		<div class="form-group">
      			<label>日期：</label>
		      	<input type="text" name="startDate" class="form-control text datetimepicker" value="<fmt:formatDate value="${startDate}" type="date" />" /> -
		      	<input type="text" name="endDate" class="form-control text datetimepicker" value="<fmt:formatDate value="${endDate}" type="date" />" />
      		</div>
      		<div class="form-group">
      			<label>出货渠道：</label>
      			<select class="select" name="channelType">
      				<option value="1" <c:if test="${channelType==1}">selected</c:if> >飞生</option>
      				<option value="2" <c:if test="${channelType==2}">selected</c:if>>越海</option>
      				<option value="3" <c:if test="${channelType==3}">selected</c:if>>飞生&越海</option>
      			</select>
      		</div>
      		<div class="form-group">
              <label>产品型号：</label>
              <div class="multiple-select">
              <select id="bs3Select" class="select selectpicker show-tick form-control" multiple data-live-search="true" name="productId">
              	<option value="-1" <c:if test="${productId[0]==-1}">selected</c:if>>全选</option>
            	<c:forEach items="${productList}" var="item">
	             		<c:if test="${productId==null}">
	             			<option value="${item.id}" >${item.name }
	             		</c:if>
	             		<c:if test="${productId!=null}">
	             			<c:set var="flag" value="true" />
	             			<c:forEach items="${productId}" var="id" varStatus="stat">
	             			  <c:if test="${id==item.id}"><option value="${item.id}" selected>${item.name }<c:set var="flag" value="false"/></c:if>
	              		  <c:if test="${stat.last && flag}">
	              		  	<c:if test="${id!=item.id}"><option value="${item.id}" >${item.name }</c:if>
	              		  </c:if>
	              		</c:forEach>
	             		</c:if>
	           	</c:forEach>
              </select>
              </div>
            </div>
            <p></p>
            <div class="form-group">
              <label>代理商：</label>
              <div class="multiple-select">
              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="agentId">
              	<c:forEach items="${commAgents}" var="item">
              		<c:if test="${agentId==null}">
             			<option value="${item.id}" >${item.irName }
             		</c:if>
             		<c:if test="${agentId!=null}">
             			<c:set var="flag" value="true" />
             			<c:forEach items="${agentId}" var="id" varStatus="stat">
             				<option value="${item.id}" selected>${item.irName }</option><c:set var="flag" value="false"/>
             				<c:if test="${stat.last && flag}">
		              		  <c:if test="${id!=item.id}"><option value="${item.id}" >${item.irName }</c:if>
		              		</c:if>
             			</c:forEach>
             		</c:if>
            		
            	</c:forEach>
              </select>
              </div>
            </div>
      		<input type="submit" value="查询" class="button button-lightblue" />
      	</form>
      </div>
      <table class="table table-hover maintable">
      	<thead>
          <tr>
            <th>型号</th>
            <th>累计sellIn</th>
            <th>累计sellOut</th>
            <th>差异值</th>
            <th>库存参考值</th>
            <th>库存</th>
            <th>城市</th>
            <th>寸别</th>
            <th>寸别-2</th>
            <th>面板</th>
            <th>出货渠道</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
        <c:forEach items="${pagelist.content}" var="item">
        	<tr>
        		<td>${item.product.name}</td>
        		<td>${item.totalSellIn}</td>
        		<td>${item.totalSellOut}</td>
        		<td>
        			<input type="text" name="realSellOut" onchange="getRealSellOut(this,${item.totalSellOut})"/>
        		</td>
        		<td></td>
        		<td>${item.currentInventory}</td>
        		<td>${item.agent.commCity.cityName}</td>
        		<td>${item.product.size1}</td>
        		<td>${item.product.size2}</td>
        		<td>${item.product.pannel.name}</td>
        		<td>
        			<c:choose>
        				<c:when test="${item.product.channelType==1}">飞生</c:when>
        				<c:when test="${item.product.channelType==2}">越海</c:when>
        				<c:when test="${item.product.channelType==3}">飞生/越海</c:when>
        			</c:choose>
        		</td>
        		<td>
        			<c:if test="${sessionScope.user.commRole.id==LINGSHOUZHUANYUAN }">
        				<a href="javascript:void(0);"  onclick="save(this,'${item.product.id}','${item.product.channelType }','${item.agent.id }','${item.currentInventory}');">保存</a><!-- 零售专员 -->
        			</c:if>
        			<c:if test="${sessionScope.user.commRole.id==DAQUJINGLI }">
        				<a href="javascript:void(0);"  onclick="save(this,'${item.product.id}','${item.product.channelType }','${item.agent.id }','${item.currentInventory}');">确认修改</a><!-- 零售专员 -->
        			</c:if>
        		</td>
        	</tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="page">
		<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
		<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
	</div> 
    </div>
  </div>
</body>
<script type="text/javascript">
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
$(document).ready(function(){
	$('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
	$("#form").submit(function(){
		if(typeof($("select[name=productId] option:selected").val())=="undefined"){
    		alert("请选择产品型号！");
    		$("select[name=productId]").focus();
			return false;
    	}
		if(typeof($("select[name=agentId] option:selected").val())=="undefined"){
    		alert("请选择代理商！");
    		$("select[name=agentId]").focus();
			return false;
    	}
	});
})

//获取临时参考库存 
getRealSellOut = function(dom,sellOut){
	var realSellOut = $(dom).val();
	if(isNaN(parseInt(realSellOut))){
		alert("差异值必须为数字！");
		$(dom).focus();
	}else{
		var tempVal = sellOut - parseInt(realSellOut);
		$(dom).parent().next().html(tempVal);
	}
}

save = function(dom,productId,channelType,agentId,currentInventory){
	var realSellOut = $(dom).parent().parent().find("input[name='realSellOut']").val();
	if(realSellOut==""){
		alert("差异值不能为空！");
		$(dom).parent().parent().find("input[name='realSellOut']").focus();
		return;
	}
	var GAP = currentInventory - parseInt(realSellOut);
	$.ajax({
		type : "POST",
		url : "${ROOT}/psi/sellOutUpdate/save",
		data : {
			productId : productId,
			channelType : channelType,
			agentId : agentId,
			GAP : GAP
		},
		dataType : "json",
		success : function(json){
 		 	if(json.status == "OK"){
		 		alert("保存成功！");
		 	}else{
		 		alert("保存失败！");
		 	} 
		}
	});
}
</script>
</html>