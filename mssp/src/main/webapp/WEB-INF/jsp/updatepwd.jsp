<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
	<title>首页</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <script type="text/javascript" >
    $(function(){
    	
    	$("#pwdBtn").click(function(){
    		if($("#pwd").val()==null||$("#pwd").val()==""){
        		alert("新密码不能为空！");
        		return;
        	}
    		
    		if($("#pwd").val()!=$("#apwd").val()){
    			alert("两次密码不一致，请重新输入！");
    			return;
    		}
    		
    		 $.ajax({
    	 			type : "POST",
    	 			url : "${ROOT}/updatepwd",
    	 			data : "password=" +$("#pwd").val(),
    	 			dataType : "json",
    	 			success : function(json) {
    	 				if(json.status=="OK"){
    	 					alert("修改成功,请重新登陆！");
    	 					window.location.href="${ROOT}"
    	 				}else{
    	 					alert("修改失败！");
    	 				}
    	 			}
    	 		}); 
    		
    	})
    	
    })
</script>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
	<div class="wrap" align="center">
		<table class="maintable table table-hover"  style="width: 30%">
			<tr>
				<td>新密码：</td>
				<td><input  type="password"  class="form-control text" name="password"  id="pwd"/></td>
			</tr>
			<tr>
				<td>再次输入新密码：</td>
				<td><input  type="password"  class="form-control text" name="apassword"  id="apwd"/></td>
			</tr>
			<tr>
				<td colspan="2"><input id="pwdBtn" type="button" class="button button-lightblue" value="提交"></td>
			</tr>
		</table>
	</div>
</body>
</html>