<%@page contentType="text/html" pageEncoding="UTF-8"%>
   <div class="navbar" role="navigation">
    <div class="container-fluid">
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon icon10"></i><span class="badge">6</span></a>
          <ul class="dropdown-menu">
            <li><a href="#">产品手册</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">系统关闭公告</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">XX显示器</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">系统关闭公告</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">XX显示器</a></li>
        </ul>
        </li>
        <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false"><i class="icon icon09"></i></a>
          <ul class="dropdown-menu">
            <li><a href="#">用户名：${sessionScope.user.realName }</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="${ROOT}/admin/logout ">退出</a></li>
        </ul>
    </li>
</ul>
</div>
</div>