<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-月度销售预估</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <input id="exportButton" type="submit" class="button button-blue" value="导出该月销售预估" />
    </div>
</div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("#exportButton").click(function(){
		 location.href = "${ROOT}/admin/psi/report/excel";
	});
})
</script>
</body>
</html>