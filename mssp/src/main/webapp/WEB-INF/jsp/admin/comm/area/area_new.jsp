<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-区域维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">区域维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/area/newSave.html" method="post">
          <div class="form-group">
            <label class="control-label">区域名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="areaName" placeholder="区域名称" datatype="s1-18" ajaxurl="${ROOT}/admin/comm/area/checkName?id=" nullmsg="此项必填！">
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
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("#form").Validform({tiptype:2});
})
</script>
</body>
</html>