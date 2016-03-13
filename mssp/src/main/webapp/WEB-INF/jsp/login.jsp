<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>登录</title>
	<%@include file="/WEB-INF/include/include.jsp" %>
<style type="text/css">
	*{-webkit-box-sizing:content-box;box-sizing:content-box;}
	.login-div{padding-top: 330px;}
	.error { display: block; margin-left: 50px; color: #f00;  margin-top: 15px; font-size: 14px;}
	</style>
</head>
<body>
<div class="login">
	<div class="login-div">
		<h3>用户登录</h3>
		<span class="error">${message }</span>
		<form name="myForm" action="${ROOT }/user/login" method="post">
			<p><label class="icon-username"></label><input class="text" name="loginName" type="text" placeholder="登录名/Login name" /></p>
			<p><label class="icon-password"></label><input class="password"  name="password" type="password" placeholder="密码/Password" /></p>
			<p><input class="submit" type="submit" value="登录/Login" /><a href="#">忘记密码？</a></p>
		</form>
	</div>
	<div class="bottom">
			————&nbsp;&nbsp;&nbsp;专业品质&nbsp;&nbsp;用心服务&nbsp;&nbsp;&nbsp;————
	</div>
</div>
</body>
</html>