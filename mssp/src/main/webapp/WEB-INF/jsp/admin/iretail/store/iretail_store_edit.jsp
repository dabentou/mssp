<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-代理商维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">零售店维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/iretailstore/editSave.html" method="post">
          <input type="hidden" value="${commAgent.id }" name="id" />
          <div class="form-group">
            <label class="control-label">零售店名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="irName" placeholder="代理商名称" value="${commAgent.irName }" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">所属代理商：</label>
            <div class="controls">
            	<select class="select" name="agentId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属省份！">
	              	<option value="请选择">请选择</option>
	              	<c:forEach items="${agentList}" var="item">
	            		<option value="${item.id }" <c:if test="${item.id == commAgent.pId}">selected="selected"</c:if> >${item.irName}</option>
	            	</c:forEach>
	            </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">是否验证通过：</label>
            <div class="controls">
              <label class="radio-inline">
                <input type="radio" value=0 name="verifyStatus" <c:if test="${commAgent.verifyStatus == 0}">checked</c:if>> 未审核
              </label>
              <label class="radio-inline">
                <input type="radio" value=1 name="verifyStatus" <c:if test="${commAgent.verifyStatus == 1}">checked</c:if>> 审核通过
              </label>
              <label class="radio-inline">
                <input type="radio" value=-1 name="verifyStatus" <c:if test="${commAgent.verifyStatus == -1}">checked</c:if>> 审核未通过
              </label>
            </div>
          </div>
         <%--  <div class="form-group">
            <label class="control-label">所属省份：</label>
            <div class="controls">
            	<select class="select" name="provinceId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属省份！">
	              	<option value="请选择">请选择</option>
	              	<c:forEach items="${provinceList}" var="item">
	            		<option value="${item.id }" <c:if test="${item.id == commAgent.province.id}">selected="selected"</c:if> >${item.province}</option>
	            	</c:forEach>
	            </select>
            </div>
          </div> --%>
         <%--  <div class="form-group">
            <label class="control-label">所属城市：</label>
            <div class="controls">
              <select class="select" name="cityId" datatype="/^((?!请选择).)+$/" errormsg="请选择所属城市！">
              	<option value="请选择">请选择</option>
              	<c:forEach items="${commCityList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == commAgent.commCity.id}">selected="selected"</c:if> >${item.cityName}</option>
            	</c:forEach>
              </select>
            </div>
          </div> --%>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="phone" placeholder="手机号码" value="${commAgent.phone }" datatype="m"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="email" placeholder="邮箱" value="${commAgent.email }" datatype="e"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address">${commAgent.address }</textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">是否流失：</label>
            <div class="controls">
              <label class="radio-inline">
                <input type="radio" value=2 name="isLoss" <c:if test="${commAgent.irStatus == 2}">checked</c:if>> 是
              </label>
              <label class="radio-inline">
                <input type="radio" value=0 name="isLoss" <c:if test="${commAgent.irStatus != 2}">checked</c:if>> 否
              </label>
            </div>
          </div>
          <div <c:if test="${commAgent.irStatus != 2}">style="display:none;"</c:if> id="reason" class="form-group" >
            <label class="control-label">流失原因：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="closeMsg">${commAgent.closeMsg }</textarea>
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
	$("input[name=isLoss]").click(function(){ 
		 var selectedvalue = $("input[name=isLoss]:checked").val();
			if(selectedvalue==2){
				$("#reason").show();
				
			}else{
				$("#reason").hide();
			}

	  }) 
	 $("#form").Validform({tiptype:2});
})
</script>
</body>
</html>