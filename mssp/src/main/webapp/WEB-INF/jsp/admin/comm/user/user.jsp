<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-用户维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="operate-search">
                    <a href="${ROOT}/admin/comm/user/new.html" class="button button-blue pull-left"><i class="icon icon11"></i>新增</a>
                	<form class="form-inline" action="${ROOT }/admin/comm/user/search">
                		<div class="form-group">
	                        <label>角色：</label>
	                        <select name="roleId" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
	                          <c:forEach items="${roleList}" var="item">
	                          	<option value="${item.id }">${item.roleName }</option>
	                          </c:forEach>
	                        </select>
	                    </div>
                		<div class="form-group">
	                        <label>登录名：</label>
	                        <input type="text" class="form-control text" name="loginName" placeholder="登录名">
	                    </div>
	                    <input type="submit" class="button button-blue" value="查询">
                	</form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>登录名</th>
                                <th>真实名称</th>
                                <th>级别</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.loginName}</td>
                                <td>${item.realName}</td>
                                <td>${item.commRole.roleName}</td>
                                <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                                <td><a href="${ROOT}/admin/comm/user/edit-${item.id}" class="operate operate1"><i></i>编辑</a><a href="javascript:void(0);" onclick="deleteUser('${item.id}','${item.loginName }')" class="operate operate2"><i></i>删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
						<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
            </div>
        </div>
    </div>
</div>
</div>
<script>
function deleteUser(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		 location.href = "${ROOT}/admin/comm/user/delete?id="+id;
	};
}
</script>
</body>
</html>