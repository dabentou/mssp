<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-城市维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">城市维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/city/editSave.html" method="post">
          <div class="form-group">
          	<input type="hidden" value="${commCity.id }" name="id" />
            <label class="control-label">城市维护：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="cityName" value="${commCity.cityName }" datatype="s1-18" placeholder="城市名称" ajaxurl="${ROOT}/admin/comm/city/checkName?id=${commCity.id }" nullmsg="此项必填！">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">城市区码：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="cityCode" value="${commCity.cityCode }" placeholder="城市区码" datatype="s1-18" nullmsg="此项必填！">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">所属区域：</label>
            <div class="controls">
              <select class="select" name="area_id" datatype="/^((?!请选择).)+$/" errormsg="请选择所属区域！">
              		<option value="请选择">请选择</option>
              	<c:forEach items="${commAreaList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == commCity.commArea.id}">selected="selected"</c:if>>${item.areaName }</option>
            	</c:forEach>
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
</body>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("#form").Validform({tiptype:2});
})
</script>
</html>