<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-样机维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">样机维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
      
      
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/sample/editSave.html" method="post">
          <input type="hidden" value="${sampleInventory.id }" name="id" />
          <div class="form-group">
            <label class="control-label">样机型号：</label>
            
            <div class="controls">
              <select class="select" name="product_id" datatype="/^((?!请选择).)+$/"  disabled  errormsg="请选择所属区域！">
              		<option value="请选择">请选择</option>
              	<c:forEach items="${productList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == sampleInventory.product.id}">selected="selected"</c:if>>${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          
          <div class="form-group">
            <label class="control-label">样机总量维护：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="sampletTotal" value="${sampleInventory.total }" datatype="s1-18" placeholder="城市名称" ajaxurl="${ROOT}/admin/comm/city/checkName?id=${commCity.id }" nullmsg="此项必填！">
            </div>
          </div>
          
          <div class="form-group">
            <label class="control-label">样机可用数量维护：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="sampleBalance" value="${sampleInventory.balance }" datatype="s1-18" placeholder="城市名称" ajaxurl="${ROOT}/admin/comm/city/checkName?id=${commCity.id }" nullmsg="此项必填！">
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