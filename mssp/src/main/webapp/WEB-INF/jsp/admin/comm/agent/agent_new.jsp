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
        <li><a href="#">区域维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/comm/agent/newSave.html" method="post">
          <div class="form-group">
            <label class="control-label">代理商名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="irName" placeholder="代理商名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">登录名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="loginName" placeholder="登录名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">真实名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="realName" placeholder="真实名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">所属省份：</label>
            <div class="controls">
            	<select class="select" name="provinceId"  id="provinceId">
	              	<option value="请选择">请选择</option>
	              	<c:forEach items="${provinceList}" var="item">
	            		<option value="${item.id }">${item.province}</option>
	            	</c:forEach>
	            </select>[ Iretail系统的代理商 ]
            </div>
         </div>
          <div class="form-group">
            <label class="control-label">所属城市：</label>
            <div class="controls">
              <select class="select" name="cityId" id="cityId">
              	<option value="请选择">请选择</option>
              	<c:forEach items="${commCityList}" var="item">
            		<option value="${item.id }">${item.cityName}</option>
            	</c:forEach>
              </select>[ 非Iretail系统的代理商 ]
            </div>
          </div>
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
              <input  type="submit"  onclick="return valiForm();" class="button button-blue" value="提交">
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
	
	valiForm=function(){
		if($("#provinceId").val()=="请选择"&&$("#cityId").val()=="请选择"){
			alert("所属省份 与 所属城市只能选一个！");
			return false;
		}
		if($("#provinceId").val()!="请选择"&&$("#cityId").val()!="请选择"){
			alert("所属省份 与 所属城市只能选一个！");
			return false;
		}
		/* boolean flag = false;
		$.ajax({
			type : "POST",
			url : "${ROOT}/admin/agent/isExists",
			data : "province="+$("#provinceId").val()+"&city="+$("#cityId").val(),
			dataType : "json",
			async: false,
			success : function(json){
			 	if(json.status == "OK"){
			 		if(json.data==null||json.data==""){
			 			flag=true;
			 		}else{
			 			if($("#provinceId").val()=="请选择"&&$("#cityId").val()!="请选择"){
				 			alert("城市 "+$("#cityId").find("option:selected").text()+ " 在系统中的代理商已存在，代理商名称： "+json.data+" 不能再创建！")
				 			flag=false;
				 		}
			 			if($("#provinceId").val()=="请选择"&&$("#cityId").val()!="请选择"){
			 				alert("省份 "+$("#provinceId").find("option:selected").text()+ " 在系统中的代理商已存在，为 "+json.data)
			 				flag=false;
				 		}
			 		}
			 	}else{
			 		alert(json.errors[0].msg);
			 		flag=false;
			 	}
			}
		});
		return flag; */
	}
})
</script>
</body>
</html>