<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-零售店维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <%-- <div class="operate-search">
                    <a href="${ROOT}/admin/iretail/iretailstore/new.html" class="button button-blue" style="margin-right: 10px;"><i class="icon icon11"></i>新增零售店</a>
                </div> --%>
                <div class="table-responsive">
                
                <form id="form" class="form-inline" action="${ROOT }/admin/iretail/iretailstore/search" method="post">
                	 <div class="form-group">
				            <label>零售店名称：</label>
				            <input type="text" class="form-control text" name="irName" value="${irName }">
			           </div>
			           <div class="form-group">
				            <label>店面ID：</label>
				            <input type="text" class="form-control text" name="storeId" value="${storeId }">
			           </div>
                
                	 <input type="submit" value="查询" class="button button-blue" />&nbsp;
                	  <a href="${ROOT}/admin/iretail/iretailstore/new.html" class="button button-blue" style="margin-right: 10px;"><i class="icon icon11"></i>新增零售店</a>
                </form>
                </div>
                <p></p>
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>零售店名称</th>
                                <!-- <th>城市</th> -->
                                <th>积分</th>
                                <th>审核状态</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <!-- <th>最后登录时间</th> -->
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.irName}</td>
                                <%-- <td>${item.commCity.cityName}</td> --%>
                                <td>${item.score}</td>
                                <td><c:choose><c:when test="${item.verifyStatus==1}">已审核</c:when><c:when test="${item.verifyStatus==0}">未审核</c:when><c:when test="${item.verifyStatus==-1}">审核未通过</c:when></c:choose></td>
                                <td><c:choose><c:when test="${item.irStatus==0}">未冻结</c:when><c:when test="${item.irStatus==1}">已冻结</c:when><c:when test="${item.irStatus==2}">已流失</c:when></c:choose></td>
                                <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                                <%-- <td><fmt:formatDate value="${item.lastLoginTime}" type="both" /></td> --%>
                                <td><a href="${ROOT}/admin/iretail/iretailstore/edit-${item.id}" class="operate operate1"><i></i>编辑</a><a href="javascript:void(0);" onclick="deleteAgent('${item.id}','${item.irName}')" class="operate operate2"><i></i>删除</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                
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
function deleteAgent(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		location.href = "${ROOT}/admin/iretail/iretailstore/delete?id="+id;
	};
}
</script>
</body>
</html>