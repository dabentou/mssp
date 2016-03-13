<%@page import="org.apache.commons.logging.LogFactory"%>
<%@page import="org.apache.commons.logging.Log"%>
<%@page import="com.mmd.mssp.comm.Constants"%>
<%@page import="com.mmd.mssp.domain.CommUser"%>
<%@page contentType="text/html" pageEncoding="UTF-8"  isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8"/>
		<title></title>
		<%@include file="/WEB-INF/include/include.jsp" %>
		<%@include file="/WEB-INF/include/header.jsp" %>
		<%@include file="/WEB-INF/include/func.jsp" %>
		<script type="text/javascript">
		$(function(){
			$("#msg").hide();
			$("#img").show();
			$("#showMsg").click(function(){
				$("#img").hide();
				$("#msg").show();
			})	;		
		})
		</script>
	</head>
	<body>
		<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content" >
			<div class="error_500" id="img" >
				<a id="showMsg" class="button-error" href="#">查看错误日志</a>
			</div>
				<div id="msg">
						<%
							Log logger = LogFactory.getLog("web_500.jsp");
							logger.error("web_500", exception);
						%><pre><%
							out.flush();
							exception.printStackTrace(response.getWriter());
						%>
					</div>
		</div>
		</div> 
	</body>
</html>
