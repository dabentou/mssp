<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>退回项目查询</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
<%--       <div class="filter">
      	<form id="form" class="form-inline" action="${ROOT }/b2c/report/search">
      	  <div class="form-group">
            <label>申请日期：</label>
            <input type="text" class="form-control text datetimepicker" name="startDate" value="<fmt:formatDate value="${startDate}" type="date" />" /> -
            <input type="text" class="form-control text datetimepicker" name="endDate" value="<fmt:formatDate value="${endDate}" type="date" />" />
          </div>
          <div class="form-group">
            <label>项目编号：</label>
            <input type="text" class="form-control text" name="pCode" value="${pCode }" />
          </div>
          <input type="submit" value="查询" class="button button-lightblue">
          <input type="button" id="exportButton" value="导出报表" class="button button-lightblue">
      	</form>
      </div> --%>
      <table class="maintable table">
      	<thead>
      		<tr>
      			<th>序号</th>
      			<th>项目编号</th>
      			<th>申请主题</th>
      			<th>申请时间</th>
      			<th>状态</th>
      			<th>操作</th>
      		</tr>
      	</thead>
      	<tbody>
      		<c:forEach items="${pagelist.content}" var="item">
      			<tr>
      				<td>${item.id}</td>
      				<td>${item.pCode}</td>	
      				<td>${item.theme}</td>
      				<td><fmt:formatDate value="${item.applytime}" type="both" /></td>
      				<td>${item.processStep.pnextId.operateStatus}</td>
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
});
</script>
</html>