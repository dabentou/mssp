<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持-项目查看</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
    	.wrap .text,.wrap .textarea,#pro-table input[type=text]{ width:100%; }
		.input_style{
			width: 100px;
		}
		.project_title{
		font-weight: bold;
		}
		.star{
			color: red;
		}
		.table td{
		border:1px solid #ddd
		}
		.table th{
		border:1px solid #ddd
		}
		input[type=checkbox] {margin-top:1px;}
		.maintable .text{ padding:2px; height: 26px; font-size: 12px;}
		.button-small { padding:0 6px; height:24px; line-height:24px; font-weight:normal; margin:2px; }
		.select {height:26px;}
		body {
			font-family: SimSun;
			font-size: 13px;
			margin: 0px;
			padding: 0px;
		}
	</style>
</head>
<body>
<c:forEach items="${projectIdArr}" var="item">
	<jsp:include page="/b2c/project/export_inner" >
		<jsp:param name="projectId" value="${item }" />
	</jsp:include>
</c:forEach>
</body>
<link rel="stylesheet" href="${ROOT}/css/jquery-ui.css" />
</html>