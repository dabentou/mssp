<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8"/>
		<title>b2i待办事宜</title>
		<%@include file="/WEB-INF/include/include.jsp" %>
		<%@include file="/WEB-INF/include/header.jsp" %>
		<%@include file="/WEB-INF/include/func.jsp" %>
	</head>
	<body>
		<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content">
		<div class="table-responsive">
			<table class="maintable table table-hover">
			<thead>
				<tr>
					<th>项目编号</th><th>项目名称</th><th>状态</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${plist}"  var="item" >
					<tr>
						<td>${item.pCode}</td>
						<td>
							<c:choose>
								<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
										<a href="${ROOT }/b2i/project/flow-${ item.applyTemplate.id}-${ item.id}/2">${item.pName}</a>
								</c:when>
								<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
										<a href="${ROOT }/b2i/project/flow-${ item.applyTemplate.id}-${ item.id}/3">${item.pName}</a>
								</c:when>
								<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
										<a href="${ROOT }/b2i/project/flow-${ item.applyTemplate.id}-${ item.id}/1">${item.pName}</a>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>		
						</td>
						<td>${item.step.operateStatus}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
	</body>
</html>
