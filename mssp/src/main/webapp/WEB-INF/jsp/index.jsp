<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
	<title>首页</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
	<div class="wrap">
		<ul class="mainnav clearfix">
			<shiro:hasPermission name="psiParent"> 
				<li><a href="${ROOT }/psi"><i class="ipsi">PSI</i>待办事宜<span>(${sessionScope.psiToDoNum })</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="iretailParent"> 
				<li><a href="${ROOT }/iretail"><i class="iiretail">iRetail</i>待办事宜<span>(${sessionScope.iRetailToDoNum })</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="b2bParent"> 
				<li><a href="${ROOT }/b2b"><i class="ib2b">B2B</i>待办事宜<span>(${sessionScope.b2bToDoNum })</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="B2IParent"> 
				<li><a href="${ROOT }/b2i"><i class="ib2i">B2I</i>待办事宜<span>(${sessionScope.b2iToDoNum })</span></a></li>
			</shiro:hasPermission>
			<shiro:hasPermission name="B2CParent"> 
				<li><a href="${ROOT }/b2c"><i class="ib2c">B2C</i>待办事宜<span>(${sessionScope.b2cToDoNum })</span></a></li>
			</shiro:hasPermission>
		</ul>
	</div>
</body>
</html>