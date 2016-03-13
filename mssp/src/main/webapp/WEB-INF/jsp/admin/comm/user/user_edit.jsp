<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-用户维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<style>
.multiple-select{width:80%;}
</style>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">用户维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/user/editSave.html" method="post">
          <input type="hidden" value="${commUser.id }" name="id" />
          <div class="form-group">
            <label class="control-label">登录名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="loginName" value="${commUser.loginName }" placeholder="登录名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">真实名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="realName" value="${commUser.realName }" placeholder="真实名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="phone" value="${commUser.phone }" placeholder="手机号码" datatype="m" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮箱：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="email" value="${commUser.email }" placeholder="邮箱" datatype="e" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">级别：</label>
            <div class="controls">
              <select class="select" name="roleId" onchange="isAgent(this);">
              	<option value="-1">请选择</option>
              	<c:forEach items="${commRoleList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == commUser.commRole.id}">selected="selected"</c:if> >${item.roleName }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group" id="agent" style="display:none;">
            <label class="control-label">代理商：</label>
            <div class="controls">
              <select class="select" name="agentId">
              	<c:forEach items="${agentList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == commUser.commAgent.id}">selected="selected"</c:if> >${item.irName }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">是否管理员：</label>
            <div class="controls">
              <label class="radio-inline">
                <input type="radio" value="1" name="isAdmin" <c:if test="${commUser.isAdmin == 1}">checked</c:if> > 是
              </label>
              <label class="radio-inline">
                <input type="radio" value ="0" name="isAdmin" <c:if test="${commUser.isAdmin == 0}">checked</c:if>> 否
              </label>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">iretail用户所属省份：</label>
            <div class="controls">
	            <div class="multiple-select">
	            <select class="selectpicker form-control" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true" name="provinceId">
	            	<c:forEach items="${provinceList}" var="item">
	            		<c:if test="${userProvinceList==null}">
			              	<option value="${item.id}" >${item.province }</option>
			            </c:if>
			            <c:if test="${userProvinceList!=null}">
			            	<c:set var="flag" value="true" />
			            	<c:forEach items="${userProvinceList}" var="userProvince" varStatus="stat">
			            		<c:if test="${userProvince.province.id==item.id}"><option value="${item.id}" selected>${item.province }<c:set var="flag" value="false"/></c:if>
			            		<c:if test="${stat.last && flag}">
				              		<c:if test="${userProvince.province.id!=item.id}"><option value="${item.id}" >${item.province }</c:if>
				              	</c:if>
			            	</c:forEach>
			            </c:if>
	          		</c:forEach>
	            </select>
	            </div>
            </div>
         </div>
         <div class="form-group">
            <label class="control-label">非iretail用户所属城市：</label>
            <div class="controls">
            	<div class="multiple-select">
            	<select class="selectpicker form-control" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true" name="cityId">
	            	<c:forEach items="${cityList}" var="item">
	            		<c:if test="${userCityList==null}">
			              	<option value="${item.id}" >${item.cityName }</option>
			            </c:if>
			            <c:if test="${userCityList!=null}">
			            	<c:set var="flag" value="true" />
			            	<c:forEach items="${userCityList}" var="userCity" varStatus="stat">
			            		<c:if test="${userCity.city.id==item.id}"><option value="${item.id}" selected>${item.cityName }<c:set var="flag" value="false"/></c:if>
			            		<c:if test="${stat.last && flag}">
				              		<c:if test="${userCity.city.id!=item.id}"><option value="${item.id}" >${item.cityName }</c:if>
				              	</c:if>
			            	</c:forEach>
			            </c:if>
	          		</c:forEach>
	            </select>
	            </div>
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
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
function isAgent(obj){
	if(obj.value==3){
		$("#agent").show();
	}else{
		$("#agent").hide();
	}
}
$(document).ready(function(){
	if($("select[name=roleId] option:selected").text()=="代理商"){
		$("#agent").show();	
	}
	$("#form").Validform({tiptype:2});
})
</script>
</body>
</html>