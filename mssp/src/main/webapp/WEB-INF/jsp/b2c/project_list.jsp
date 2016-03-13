<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>未完结项目查询</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <div class="filter">
	      	<form id="form" class="form-inline" action="${ROOT }/b2c/project/search">
		      	  <div class="form-group">
			            <label>申请日期：</label>
			            <input type="text" class="form-control text datetimepicker" name="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd" />" /> -
			            <input type="text" class="form-control text datetimepicker" name="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd" />" />
		          </div>
		          <div class="form-group">
			            <label>状态：</label>
			            <select class="form-control text" name="status">
			            	<option value="">--请选择--</option>
				            <c:forEach items="${prSteps}" var="item">
				            	<c:if test="${item.pnextId!=null}">
				            		<option value="${item.id}" <c:if test="${item.id==status }">selected</c:if> >${item.pnextId.operateStatus}</option>
				            	</c:if>
				     		</c:forEach>
				     		<option value="-1">审批流程结束</option>
			     		</select>
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
	      </form>
      </div>
      <table class="maintable table">
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
      		<c:forEach items="${pagelist.content}" var="item">
      			<tr>
      				<td>${item.id}</td>
      				<td>${item.pCode}</td>
      				<td>${item.theme}</td>
      				<td><fmt:formatDate value="${item.applytime}" type="both" /></td>
      				<td>${item.processStep.pnextId==null?'审批流程结束':item.processStep.pnextId.operateStatus}</td>
      				<td>
      					<a href="${ROOT}/b2c/project/view-${item.id}" class="operate operate4"><i></i>查看</a>
  		<%-- 				<c:choose>
							<c:when test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==AREA_MANAGER && item.processStep.pnextId!=null}"><!--区域经理  -->
								<a href="${ROOT}/b2c/project/edit-${item.id}" class="operate operate4"><i></i>修改确认</a>
							</c:when>
							<c:when test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==XIAOFUZHUGUAN && item.processStep.pnextId!=null}"><!--销服主管 -->
								<a href="${ROOT}/b2c/project/view-${item.id}" class="operate operate4"><i></i>填写意见</a>
							</c:when>
							<c:otherwise>
								<a href="${ROOT}/b2c/project/view-${item.id}" class="operate operate4"><i></i>查看</a>
							</c:otherwise>
						</c:choose> --%>
						<c:if test="${item.processStep.pnextId!=null && (sessionScope.user.commRole.id==AGENT || sessionScope.user.commRole.id==AREA_MANAGER)}">
							<a href="javascript:void(0);" class="operate operate2" onclick="deleteProject('${item.id}','${item.theme }');"><i></i>抽单</a>
						</c:if>
<%-- 	      				<c:if test="${sessionScope.user.commRole.id==item.processStep.pnextId.commRole.id && sessionScope.user.commRole.id==SALES_SERVICE_DIRECTOR }">
	      					<a href="${ROOT }/b2c/project/flow-${ item.id}">审批</a>
	      				</c:if> --%>
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
	function deleteProject(id, name) {
		if (confirm("确定要抽单" + name + "吗？")) {
			location.href = "${ROOT}/b2c/project/delete?id=" + id;
		}
	}
	
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