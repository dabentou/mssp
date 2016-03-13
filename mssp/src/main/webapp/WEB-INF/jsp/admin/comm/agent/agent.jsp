<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
        <div class="row">
            <div class="col-lg-12">
                <div class="operate-search">
                    <a href="${ROOT}/admin/comm/agent/new.html" class="button button-blue" style="margin-right: 10px;"><i class="icon icon11"></i>新增总代</a><a href="${ROOT}/admin/comm/secagent/new.html" class="button button-blue"><i class="icon icon11"></i>新增二级代理商</a>
	                <form style="margin-top:20px;" class="form-inline" action="${ROOT }/admin/comm/agent/search">
	                      <div class="form-group">
	                        <label>代理商：</label>
	                        <input type="text" class="form-control text" name="irName" placeholder="代理商名称">
	                      </div>
	                      <div class="form-group">
	                        <label>省份：</label>
	                        <select name="provinceId" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
	                          <c:forEach items="${provinceList}" var="item">
	                          	<option value="${item.id }">${item.province }</option>
	                          </c:forEach>
	                        </select>
	                      </div>
	                      <div class="form-group">
	                        <label>城市：</label>
	                        <select name="cityId" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
	                          <c:forEach items="${cityList}" var="item">
	                          	<option value="${item.id }">${item.cityName }</option>
	                          </c:forEach>
	                        </select>
	                      </div>
	                      <div class="form-group">
	                        <label>审核状态：</label>
	                        <select name="verifyStatus" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
	                          <option value="0">未审核</option>
	                          <option value="1">已审核</option>
	                          <option value="-1">审核未通过</option>
	                        </select>
	                      </div>
	                      <div class="form-group">
	                        <label>状态：</label>
	                        <select name="irStatus" class="selectpicker" multiple data-live-search="true" data-live-search-placeholder="Search" data-actions-box="true">
	                          <option value="0">未冻结</option>
	                          <option value="1">已冻结</option>
	                          <option value="2">已流失</option>
	                        </select>
	                      </div>
	                      <input type="submit" class="button button-blue" value="查询">
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>代理商</th>
                                <th>城市</th>
                                <th>积分</th>
                                <th>审核状态</th>
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>最后登录时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.irName}</td>
                                <td>${item.commCity.cityName}</td>
                                <td>${item.score}</td>
                                <td><c:choose><c:when test="${item.verifyStatus==1}">已审核</c:when><c:when test="${item.verifyStatus==0}">未审核</c:when><c:when test="${item.verifyStatus==-1}">审核未通过</c:when></c:choose></td>
                                <td><c:choose><c:when test="${item.irStatus==0}">未冻结</c:when><c:when test="${item.irStatus==1}">已冻结</c:when><c:when test="${item.irStatus==2}">已流失</c:when></c:choose></td>
                                <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                                <td><fmt:formatDate value="${item.lastLoginTime}" type="both" /></td>
                                <td><a href="${ROOT}/admin/comm/agent/edit-${item.id}" class="operate operate1"><i></i>编辑</a><a href="javascript:void(0);" onclick="deleteAgent('${item.id}','${item.irName}')" class="operate operate2"><i></i>删除</a></td>
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
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });

    // $('.selectpicker').selectpicker('hide');
});
function deleteAgent(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		location.href = "${ROOT}/admin/comm/agent/delete?id="+id;
	};
}
</script>
</body>
</html>