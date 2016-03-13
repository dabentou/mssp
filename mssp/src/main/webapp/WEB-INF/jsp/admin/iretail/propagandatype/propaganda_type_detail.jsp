<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-宣传品类型维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="${ROOT}/admin/iretail/propagandatype/list.html">宣传品类型维护</a></li>
        <li>${isNew?"新增":"编辑" }</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/iretail/propagandatype/save" method="post">
          <div class="form-group">
          	<input type="hidden" value="${iretailPropagandaType.id }" name="id" />
            <label class="control-label">宣传品类型：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="type" value="${iretailPropagandaType.type }" placeholder="类型" datatype="s1-18" />
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