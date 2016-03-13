<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-审批模板列表</title>
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
                    <div class="pull-left"><a href="${ROOT}/admin/comm/approvetemplate/new.html" class="button button-blue"><i class="icon icon11"></i>新增</a></div>
                    <form class="form-inline" action="${ROOT}/admin/comm/approvetemplate/search" method="post">
                      <div class="form-group">
                        <label>模板名称：</label>
                        <input type="text" class="form-control text" name="typeName" placeholder="模板名称">
                      </div>
                      <input type="submit" class="button button-blue" value="查询" />
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>模板名称</th>
                                <th>描述</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${paging.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.tempName}</td>
                                <td>${item.desc}</td>
                                <td>
                                	<a href="${ROOT}/admin/comm/approvetemplate/edit?id=${item.id}" class="operate operate1"><i></i>编辑</a>
                                	<a href="javascript:void(0);" onclick="deleteTemplate('${item.id}','${item.tempName }')" class="operate operate2"><i></i>删除</a>
                                	<a href="${ROOT}/admin/comm/approvetemplate/detail.html?id=${item.id}" class="operate operate1"><i></i>模板</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${paging.from+1}-${paging.from+10}</span>,共<span>${paging.totalElements}</span>条记录</p>
						<ms:page from="${paging.from}" size="${paging.size}" totle="${paging.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
            </div>
        </div>
    </div>
</div>
</div>
<script>
$(document).ready(function(){
	$("input[id^=hidden]").each(function(index, element) {
		var mid=element.id.split("-")[1];
		var value=$("input[id$="+mid+"]").val();
		$("select[id$="+mid+"]").val(value);
    });
})
function changeSelect(obj){
	var mid=obj.id.split("-")[1];
	$("input[id$="+mid+"]").val(obj.value);
}
function deleteTemplate(id,tempName){
	if(confirm("确定要删除"+tempName+"吗？")){
		 location.href = "${ROOT}/admin/comm/approvetemplate/delete?id="+id;
	};
}
</script>
</body>
</html>