<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-考核条件或目标维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">考核条件或目标维护</a></li>
        <li><c:if test="${empty condition}">新增</c:if><c:if test="${!empty condition}">编辑</c:if></li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/b2c/condition/save" method="post">
          <div class="form-group">
          	<input type="hidden" value="${condition.id }" name="id" />
            <label class="control-label">考核条件或目标：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="name" value="${condition.name }" placeholder="考核条件或目标" datatype="s1-18" ajaxurl="${ROOT}/admin/comm/area/checkName?id=${commArea.id }" nullmsg="此项必填！">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">类别：</label>
            <div class="controls">
              <select class="select" name="type" datatype="/^((?!请选择).)+$/" errormsg="请选择所属类别！">
              		<option value="请选择">请选择</option>
              		<option <c:if test="${condition.type==2}">selected</c:if> value="2">先进达成特批</option>
              		<option <c:if test="${condition.type==3}">selected</c:if> value="3">月度各项考核指标特批</option>
              		<option <c:if test="${condition.type==4}">selected</c:if> value="4">季度各项考核指标特批</option>
              		<option <c:if test="${condition.type==5}">selected</c:if> value="5">NET外奖励特批</option>
              </select>
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
	$("#form").Validform({tiptype:2});  //就这一行代码！;
		
})
</script>
</body>
</html>