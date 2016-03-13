<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>二级代理商申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <form id="form" class="form-horizontal" action="${ROOT }/b2i/agent/newSave.html" method="post">
      	 <div class="form-group">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="irName" datatype="s1-18"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="phone"  datatype="m"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="email" datatype="e"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address"></textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">是否创建用户：</label>
            <div class="controls">
              <label class="radio-inline">
                <input type="radio" value=1 name="isUser"> 是
              </label>
              <label class="radio-inline">
                <input type="radio" value=0 name="isUser" checked> 否
              </label>
            </div>
          </div>
          <div id="user" style="display:none;">
          	<div class="form-group">
	            <label class="control-label">登录名称：</label>
	            <div class="controls">
	              <input type="text" class="form-control text" name="loginName" placeholder="登录名称" />
	            </div>
	          </div>
	          <div class="form-group">
	            <label class="control-label">真实名称：</label>
	            <div class="controls">
	              <input type="text" class="form-control text" name="realName" placeholder="真实名称" />
	            </div>
	          </div>
          </div>
          <div class="form-group form-group-btn">
            <div class="controls">
              <input type="submit" class="button button-lightblue" value="确定提交">
            </div>
          </div>
      </form>
    </div>
  </div>
</body>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("input[name=isUser]").change(function(){
		var selectedvalue = $("input[name=isUser]:checked").val();
		if(selectedvalue==1){
			$("#user").show();
			$("input[name=loginName]").attr("datatype","s1-18");
			
		}else{
			$("#user").hide();
			$("input[name=loginName]").removeAttr("datatype").removeAttr("nullmsg");
		}
	});
})
$(function(){
	$("#form").Validform({tiptype:2});
})
</script>
</html>