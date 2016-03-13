<%@page import="com.mmd.mssp.comm.Constants"%>
<%@page import="com.mmd.mssp.domain.CommUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8"/>
	</head>
	<body>
		<%-- <div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content" align="center">
			<img alt="" src="${ROOT }/images/500.jpg" />
		</div>
		</div> --%>
		<%
			CommUser user = (CommUser)session.getAttribute(Constants.USER_KEY);
			if(user.getIsAdmin()==0){//非管理员
				request.getRequestDispatcher("web_500.jsp").forward(request,response);
			}else{
				request.getRequestDispatcher("admin/admin_500.jsp").forward(request,response);
			}
		%>
	</body>
</html>
