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
			<table class="maintable table table-hover">
				<thead>
					<tr>
						<th>型号</th>
						<th>实际SellOut</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty psiList }">
					<tr>
						<td colspan="3">暂无Sellout修改审批！</td>
					</tr>
				</c:if>
				<c:forEach items="${psiList }" var="item">
					<tr>
						<td>
								<a href="${ROOT }/psi/sellOutUpdate/detail.html?applyLogId=${ item.id}">${item.product.name }</a>
						</td>
						<td>${item.realSellOut }</td>
						<td>${item.step.pnextId.operateStatus  }</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
  </div>
</body>
</html>