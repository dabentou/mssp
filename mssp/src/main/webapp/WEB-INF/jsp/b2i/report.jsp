<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8" />
<title>b2i数据查询&导出</title>
<%@include file="/WEB-INF/include/include.jsp"%>
<style>
.filter .form-group label { width:62px; text-align:right;}
.multiple-select { width:318px;}
</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp"%>
	<%@include file="/WEB-INF/include/func.jsp"%>
	<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content">
		  <div class="filter">
			<form id="form" class="form-inline" action="${ROOT }/b2i/project/report.html">
			  <div class="form-group">
	            <label>申请日期：</label>
	            <input type="text" class="form-control text datetimepicker" name="applyTimeStart" value="<fmt:formatDate value='${applyTimeStart}' type='date' />" /> -
	            <input type="text" class="form-control text datetimepicker" name="applyTimeEnd" value="<fmt:formatDate value='${applyTimeEnd}' type='date' />" />
	          </div>
	          <div class="form-group">
	            <label>批复日期：</label>
	            <input type="text" class="form-control text datetimepicker" name="approveTimeStart" value="<fmt:formatDate value='${approveTimeStart}' type='date' />" /> -
	            <input type="text" class="form-control text datetimepicker" name="approveTimeEnd" value="<fmt:formatDate value='${approveTimeEnd}' type='date' />" />
	          </div>
	          <div class="form-group">
	              <label>状态：</label>
	              <div class="multiple-select">
	              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="processStepId">
	              	<c:forEach items="${processStepList}" var="item">
	              		<c:if test="${processStepId==null}">
	              			<option value="${item.id}" >${item.operateStatus }
	              		</c:if>
	              		<c:if test="${processStepId!=null}">
	              			<c:set var="flag" value="true" />
	              			<c:forEach items="${processStepId}" var="id" varStatus="stat">
	              			  <c:if test="${id==item.id}"><option value="${item.id}" selected>${item.operateStatus }<c:set var="flag" value="false"/></c:if>
		              		  <c:if test="${stat.last && flag}">
		              		  	<c:if test="${id!=item.id}"><option value="${item.id}" >${item.operateStatus }</c:if>
		              		  </c:if>
		              		</c:forEach>
	              		</c:if>
	            	</c:forEach>
	              </select>
	              </div>
	           </div>
	           <p style="height:10px;"></p>
	           <c:if test="${agent==null }">
	           <div class="form-group">
	              <label>城市：</label>
	              <div class="multiple-select">
	              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="cityId">
	              	<c:forEach items="${cityList}" var="item">
	              		<c:if test="${cityId==null}">
	              			<option value="${item.id}" >${item.cityName }
	              		</c:if>
	              		<c:if test="${cityId!=null}">
	              			<c:set var="flag" value="true" />
	              			<c:forEach items="${cityId}" var="id" varStatus="stat">
	              			  <c:if test="${id==item.id}"><option value="${item.id}" selected>${item.cityName }<c:set var="flag" value="false"/></c:if>
		              		  <c:if test="${stat.last && flag}">
		              		  	<c:if test="${id!=item.id}"><option value="${item.id}" >${item.cityName }</c:if>
		              		  </c:if>
		              		</c:forEach>
	              		</c:if>
	            	</c:forEach>
	              </select>
	              </div>
	           </div>
	           </c:if>
	           <div class="form-group">
	            <label>项目编号：</label>
	            <input type="text" class="form-control text" name="pCode" value="${pCode }" />
	           </div>
	           <div class="form-group">
	            <label>项目名称：</label>
	            <input type="text" class="form-control text" name="pName" value="${pName }" />
	           </div>
	           <p style="height:10px;"></p>
	           <div class="form-group">
		            <label>模板类型：</label>
			     		<select class="form-control text" name="applyTempType">
				            <c:forEach items="${applyTemplates}" var="item">
								<option value="${item.id}" <c:if test="${item.id==applyTempType}">selected</c:if>>${item.name}</option>
				     		</c:forEach>
			     		</select>
		          </div>
	           <input type="submit" value="查询" class="button button-lightblue">
	           <input type="button" id="exportButton" value="导出报表" class="button button-lightblue">
			</form>
		  </div>
			<div class="table-responsive">
				<table class="maintable table table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>项目编号</th>
							<th>项目名称</th>
							<th>模板名称</th>
							<th>城市</th>
							<th>代理商</th>
							<th>SI名称</th>
							<th>客户名称</th>
							<th>状态</th>
							<th>申请时间</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pagelist.content}" var="item">
							<tr>
								<td>${item.id}</td>
								<td>${item.pCode}</td>
								<td>${item.pName}</td>
								<td>${item.applyTemplate.name}</td>
								<td>${item.agent.commCity.cityName}</td>
								<td>${item.agent.irName}</td>
								<td>${item.commSi.irName}</td>
								<td>${item.customer.name } </td>
								<td>${item.step.operateStatus}</td>
								<c:if test="${item.applyTemplate.id==1}">
									<td><fmt:formatDate value="${item.applytime}" type="both" /></td>
								</c:if>
								<c:if test="${item.applyTemplate.id==2}">
									<td><fmt:formatDate value="${item.applyDate}" type="both" /></td>
								</c:if>
								<td><a href="${ROOT}/b2i/project/view-${item.id}-${item.applyTemplate.id}" class="operate operate4"><i></i>查看</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="page">
				<p class="pull-left">
					当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录
				</p>
				<ms:page from="${pagelist.from}" size="${pagelist.size}"
					totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
			</div>
</body>
<script type="text/javascript">
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
$(document).ready(function(){
	//日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
    $("#exportButton").click(function(){
		 location.href = "${ROOT}/b2i/report/excel?"+$("#form").serialize();
	});
});
</script>
</html>