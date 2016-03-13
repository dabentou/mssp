<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-预算日志查看</title>
<style>
	.budget{
		width:100px;
		text-align:center;
	}
</style>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
   		<div class="col-lg-12">
   		
   		<div class="form-group">
		      	<label>年份：</label>
		      	<input type="text" class="form-control text" id="year"  placeholder="${year }" readonly>
		      	<label>季度：</label>
		      	<input type="text" class="form-control text" id="quarter"  placeholder="${quarter }" readonly>
		</div>
        	
        	<div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>省份</th>
                                <th>类型</th>
                                <th>改变前的值</th>
                                <th>改变后的值</th>
                                <th>修改人</th>
                                <th>修改时间</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list }" var="item">
                            <tr>
                                <td>${item.province.province}</td>
                                <td>${item.type}</td>
                                <td>${item.oldVolum}</td>
                                <td>${item.newVolum}</td>
                                <td>${item.user.loginName}</td>
                                <td><fmt:formatDate value="${item.updatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
   		</div>
   </div>
</div>
</div>
</body>
</html>






















