<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-零售店维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">零售店维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/iretail/iretailstore/newSave.html" method="post">
          <div class="form-group">
            <label class="control-label">零售店名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="irName" placeholder="代理商名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">所属总代：</label>
            <div class="controls">
              <select class="select" name="commAgentId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属总代！">
	              	<option value="请选择">请选择</option>
	              	<c:forEach items="${commAgentList}" var="item">
	            		<option value="${item.id }">${item.irName}</option>
	            	</c:forEach>
	            </select>
            </div>
          </div>
         <%--  <div class="form-group">
            <label class="control-label">所属省份：</label>
            <div class="controls">
            	<select class="select" name="provinceId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属省份！">
	              	<option value="请选择">请选择</option>
	              	<c:forEach items="${provinceList}" var="item">
	            		<option value="${item.id }">${item.province}</option>
	            	</c:forEach>
	            </select>
            </div>
         </div> --%>
          <%-- <div class="form-group">
            <label class="control-label">所属城市：</label>
            <div class="controls">
              <select class="select" name="cityId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属城市！">
              	<option value="请选择">请选择</option>
              	<c:forEach items="${commCityList}" var="item">
            		<option value="${item.id }">${item.cityName}</option>
            	</c:forEach>
              </select>
            </div>
          </div> --%>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="phone" placeholder="手机号码" datatype="m"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="email" placeholder="邮箱" datatype="e" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address"></textarea>
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