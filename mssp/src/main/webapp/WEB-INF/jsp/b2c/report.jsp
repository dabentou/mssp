<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>已完结项目查询</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <div class="filter">
      	<form id="form" class="form-inline" action="${ROOT }/b2c/report/search">
      	  <div class="form-group">
            <label>申请日期：</label>
            <input type="text" class="form-control text datetimepicker" name="startDate" value="<fmt:formatDate value="${startDate}" type="date" />" /> -
            <input type="text" class="form-control text datetimepicker" name="endDate" value="<fmt:formatDate value="${endDate}" type="date" />" />
          </div><br/>
          
          <div class="form-group">
            <label>审批完结时间：</label>
            <input type="text" class="form-control text datetimepicker" name="approvStartDate" value="<fmt:formatDate value="${approvStartDate}" type="date" />" /> -
            <input type="text" class="form-control text datetimepicker" name="approvEndDate" value="<fmt:formatDate value="${approvEndDate}" type="date" />" />
          </div>
          
          <div class="form-group">
            <label>项目编号：</label>
            <input type="text" class="form-control text" name="pCode" value="${pCode }" />
          </div>
          <c:if test="${sessionScope.user.commRole.id==AREA_MANAGER || sessionScope.user.commRole.id==SALES_SERVICE_DIRECTOR }">
         				<div class="form-group">
	            <label>代理：</label>
	            <select class="form-control text" name="agentId">
	            	<option value="">--请选择--</option>
		            <c:forEach items="${agentList}" var="item">
						<option value="${item.id}">${item.irName}</option>
		     		</c:forEach>
	     		</select>
        		</div>
		</c:if>
          <input type="submit" value="查询" class="button button-lightblue">
          <input type="button" id="exportButton" value="导出报表" class="button button-lightblue">
         <!--  <input type="button" id="exportPDF" value="生成pdf" class="button button-lightblue"> -->
      	</form>
      </div>
      <table class="maintable table">
      	<thead>
      		<tr>
      			<th><!--   <input type="checkbox" onclick="batchCheck_cBox()"/> --> 序号</th>
      			<th>项目编号</th>
      			<th>申请主题</th>
      			<th>申请时间</th>
      			<th>操作</th>
      		</tr>
      	</thead>
      	<tbody>
      		<c:forEach items="${pagelist.content}" var="item">
      			<tr>
      				<td><%--   <input type="checkbox" name="comm-cBox" value="${item.id}"/> --%>${item.id}</td>
      				<td>${item.pCode}</td>	
      				<td>${item.theme}</td>
      				<td><fmt:formatDate value="${item.applytime}" type="both" /></td>
      				<td><a href="${ROOT}/b2c/project/view-${item.id}" class="operate operate4"><i></i>查看</a></td>
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
$(document).ready(function(){
	//日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
    $("#exportButton").click(function(){
		 location.href = "${ROOT}/b2c/report/excel?"+$("#form").serialize();
	});
    
    
    window.batchCheck_cBox = function(){
    	$("input[name='comm-cBox']").each(function(){
    		if($(this).prop("checked")){
    			$(this).prop("checked",false);
    		}else{
    			$(this).prop("checked",true);
    		}
    	});
    }
    
	//生成pdf按钮事件
 	$("#exportPDF").click(function(){
		var projectIds = "";
		$("input[name='comm-cBox']").each(function(){
			if($(this).prop("checked")){
				projectIds += $(this).val() + ",";
			}
		});
		location.href = "${ROOT}/b2c/project/exportPDF?projectIds="+projectIds;
	});
});
</script>
</html>