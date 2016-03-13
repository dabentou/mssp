<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <div class="head">
    <div class="wrap">
      <img src="${ROOT }/images/logo_ht.png" />
      <div class="topinfo">
        <p>欢迎<span>${sessionScope.user.realName }</span></p>
        <ul class="clearfix">
              <li><i class="icon icon12"></i><a href="${ROOT }/updatepwd.html">修改密码</a></li>
              <li><i class="icon icon13"></i><a href="${ROOT}/logout">退出</a></li>
            </ul>
      </div>
    </div>
  </div>
