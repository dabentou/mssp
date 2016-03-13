<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-级别维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">级别维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
        <form class="form-horizontal" action="${ROOT}/admin/comm/role/editSave.html" method="post">
          <div class="form-group">
          	<input type="hidden" value="${commRole.id }" name="id" />
            <label class="control-label">级别维护：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="roleName" value="${commRole.roleName }" placeholder="级别名称">
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交">
            </div>
          </div>
        </form>
      </div>
    </div>
</div>
</div>
</body>
</html>