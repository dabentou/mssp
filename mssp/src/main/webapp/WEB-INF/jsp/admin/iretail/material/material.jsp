<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-物料维护</title>
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
                    <a href="${ROOT}/admin/iretail/material/new.html" class="button button-blue"><i class="icon icon11"></i>新增</a>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>物料名称</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.name}</td>
                                 <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                                <td><a href="${ROOT}/admin/iretail/material/edit-${item.id}" class="operate operate1"><i></i>编辑</a><a href="javascript:void(0);" onclick="deleteArea('${item.id}','${item.name }')" class="operate operate2"><i></i>删除</a></td>
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
function deleteArea(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		 location.href = "${ROOT}/admin/iretail/material/delete?id="+id;
	};
}
</script>
</body>
</html>