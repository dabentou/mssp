<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-销售预估模板上传</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <table class="table table-hover mytable">
          <thead>
              <tr>
                  <th>序号</th>
                  <th>型号</th>
                  <th>备注</th>
                  <th>月份</th>
              </tr>
          </thead>
          <tbody>
          <c:forEach items="${list}" var="item">
              <tr>
                  <td>${item.id}</td>
                  <td>${item.product.name}</td>
                  <td>${item.comment}</td>
                  <td><fmt:formatDate value="${item.dateMonth}" pattern="yyyy/MM" /></td>
              </tr>
          </c:forEach>
          </tbody>
      </table>
    </div>
</div>
</div>
</body>
</html>