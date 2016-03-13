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
		        <form class="form-inline" action="${ROOT}/b2i/project/applylistquery">
		          <div class="form-group">
		            <label>&nbsp;&nbsp;模板类型：</label>
		            	<input type="hidden" name="type" value="${type}"/>
			     		<select class="form-control text" name="applyTempType">
				            <c:forEach items="${applyTemplates}" var="item">
								<option value="${item.id}" <c:if test="${item.id==applyTempType}">selected</c:if>>${item.name}</option>
				     		</c:forEach>
			     		</select>
		          </div>
		          &nbsp;&nbsp;<input type="submit" value="查询" class="button button-lightblue" />
		        </form>
	      </div>
      	<div class="table-responsive">
				<table class="maintable table table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>项目名称</th>
							<th>模板类型</th>
							<th>项目编号</th>
							<!-- <th>行业</th> -->
							<th>客户信息</th>
							<th>代理商</th>
							<th>二级代理商</th>
<!-- 							<th>项目背景</th>
							<th>项目重要性</th>
							<th>竞争对手情况</th> -->
							<th>状态</th>
							<th>操作</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${pagelist.content}" var="item">
							<tr>
								<td>${item.id}</td>
								<td>${item.pName}</td>
								<td>${item.applyTemplate.name}</td>
								<td>${item.pCode}</td>
								<%-- <td>${item.business.name}</td> --%>
								<td>${item.customer.name}</td>
								<td>${item.agent.irName}</td>
								<td>${item.commSi.irName}</td>
	<%-- 							<td>${item.projectContext}</td>
								<td>${item.projectImportant}</td>
								<td>${item.competeCondition}</td> --%>
								<td>${item.step.operateStatus}</td>
								<td>
									<a href="${ROOT}/b2i/project/view-${item.id}-${item.applyTemplate.id}-${type}" class="operate operate4"><i></i>查看</a>
									<a href="javascript:void(0);" onclick="deleteProject('${item.id}','${item.pName }','${item.applyTemplate.id}')" class="operate operate2"><i></i>删除</a>
									<c:choose>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
													<a href="${ROOT }/b2i/project/flow-${item.applyTemplate.id}-${item.id}/2">上传核销资料</a>
											</c:when>
	<%-- 										<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
													<a href="${ROOT }/b2i/project/flow-${item.applyTemplate.id}-${ item.id}/3">申请返利</a>
											</c:when> --%>
											<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
													<a href="${ROOT }/b2i/project/flow-${item.applyTemplate.id}-${ item.id}/1">审批</a>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>					
								
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		
		<div class="page">
				<p class="pull-left">
					当前显示<span>${pagelist.from+1}-${pagelist.from+20}</span>,共<span>${pagelist.totalElements}</span>条记录
				</p>
				<ms:page from="${pagelist.from}" size="${pagelist.size}"
					totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
			</div>  
		  
    </div>
  </div>
</body>
</html>
<script>
	function deleteProject(id,name,applyTempType){
		if (confirm("确定要删除" + name + "吗？")) {
			location.href = "${ROOT}/b2i/project/delete?id=" + id+"&applyTempType="+applyTempType;
		}
	}

</script>