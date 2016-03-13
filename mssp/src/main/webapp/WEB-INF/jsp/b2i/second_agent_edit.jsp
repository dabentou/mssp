<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>二级代理商编辑</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <form id="form" class="form-horizontal" action="${ROOT }/b2i/agent/editSave" method="post">
      	<input type="hidden"  name="id" id="id" value="${agent.id }"/>
      
      	 <div class="form-group">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="name" value="${agent.irName }" datatype="s1-18"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="phone" value="${agent.phone }" datatype="m" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="email" value="${agent.email }" datatype="e" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address">${agent.address }</textarea>
            </div>
          </div>
          <div class="form-group form-group-btn">
            <div class="controls">
              <input type="submit" class="button button-lightblue" value="确认修改">
            </div>
          </div>
      </form>
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