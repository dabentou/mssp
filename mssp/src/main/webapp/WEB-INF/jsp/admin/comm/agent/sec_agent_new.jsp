<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-二级代理商维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">二级维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/scoagent/newSave" method="post">
          <div class="form-group">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="irName" placeholder="代理商名称" value="${commAgent.irName }" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">所属总代：</label>
            <div class="controls">
              <select class="select" name="pId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属总代！">
              	<option value="请选择">请选择</option>
              	<c:forEach items="${agentList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == commAgent.pId}">selected="selected"</c:if> >${item.irName}</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="phone" placeholder="手机号码" value="${commUser.phone }" datatype="m"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="email" placeholder="邮箱" value="${commUser.email }" datatype="e"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address">${commAgent.address }</textarea>
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
          <input type="hidden" value="${commUser.id }" name="userId" />
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
	$("#form").Validform({tiptype:2});
})
</script>
</body>
</html>