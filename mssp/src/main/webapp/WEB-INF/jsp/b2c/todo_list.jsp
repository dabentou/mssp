<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
   <div class="content">
		   	      <div class="filter">
			      	<form id="form" class="form-inline" action="${ROOT }/b2c/project/todo/search">
				      	  <div class="form-group">
					            <label>申请日期：</label>
					            <input type="text" class="form-control text datetimepicker" name="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />" /> -
					            <input type="text" class="form-control text datetimepicker" name="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />" />
				          </div>
		   					<div class="form-group">
					            <label>代理：</label>
					            <select class="form-control text" name="agentId">
					            	<option value="">--请选择--</option>
						            <c:forEach items="${agentList}" var="item">
										<option value="${item.id}" <c:if test="${item.id==agentId }">selected</c:if> >${item.irName}</option>
						     		</c:forEach>
					     		</select>
				          	</div>
				          <input type="submit" value="查询" class="button button-lightblue">
			      </form>
		      </div>
			<table class="maintable table table-hover">
				<thead>
					<tr>
		      			<th>序号</th>
		      			<th>项目编号</th>
		      			<th>申请主题</th>
		      			<th>申请时间</th>
		      			<th>状态</th>
		      			<th>操作 </th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty pagelist.content }">
						<tr>
							<td colspan="6">暂无待办事宜！</td>
						</tr>
					</c:if>
		      		<c:forEach items="${pagelist.content}" var="item">
		      			<tr>
		      				<td>${item.id}</td>
		      				<td>${item.pCode}</td>
		      				<td>${item.theme}</td>
		      				<td><fmt:formatDate value="${item.applytime}" type="both" /></td>
		      				<td>${item.processStep.pnextId==null?'审批流程结束':item.processStep.pnextId.operateStatus}</td>
		      				<td>
		  						<c:choose>
									<c:when test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==AREA_MANAGER && item.processStep.pnextId!=null}"><!--区域经理  -->
										<a href="${ROOT}/b2c/project/edit-${item.id}" class="operate operate4"><i></i>修改确认</a>
									</c:when>
									<c:when test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==XIAOFUZHUGUAN && item.processStep.pnextId!=null}"><!--销服主管 -->
										<a href="${ROOT}/b2c/project/view-${item.id}" class="operate operate4"><i></i>填写意见</a>
									</c:when>
								</c:choose>
			      				<c:if test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==SALES_SERVICE_DIRECTOR }">
			      					<a href="${ROOT }/b2c/project/flow-${ item.id}">审批</a>
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
<script>
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