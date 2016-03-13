<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>PO核销</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
   <div class="content">
			<table class="maintable table table-hover">
				<thead>
					<tr>
						<th>类型</th>
						<th>项目主题</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty plist }">
					<tr>
						<td colspan="4">暂无待办事宜！</td>
					</tr>
				</c:if>
				<c:forEach items="${plist}"  var="item" >
					<tr>
						<td>${item.iType}</td>
						<td>
							<c:choose>
								<c:when test="${(sessionScope.user.commRole.id==item.processStep.commRole.id)&&(item.processStep.statusValue==10) }">
										<a href="${ROOT }/iretail/project/flow-${ item.id}/2">${item.applyTheme}</a>
								</c:when>
								<c:when test="${sessionScope.user.commRole.id==item.processStep.commRole.id }">
										<a href="${ROOT }/iretail/project/flow-${ item.id}/1">${item.applyTheme}</a>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</td>
						<td>${item.processStep.operateStatus}</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
  </div>
</body>
</html>