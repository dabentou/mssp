<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-型号维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
   <%--              <div class="operate-search">
                    <div class="pull-left"><a href="${ROOT}/admin/psi/product/new.html" class="button button-blue"><i class="icon icon11"></i>新增</a></div>
                    <form class="form-inline" action="${ROOT}/admin/psi/product/search">
                      <div class="form-group">
                        <label>产品型号：</label>
                        <input type="text" class="form-control text" name="name" placeholder="产品型号">
                      </div>
                      <input type="hidden" name="seriesId" id="hidden-series" />
                      <input type="submit" class="button button-blue" value="查询" />
                    </form>
                </div> --%>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>产品型号</th>
                                <th>财报价</th>
                                <th>NET价</th>
                                <th>网吧公价</th>
                                <th>B2B公价</th>
                                <th>开票价</th>
                                <th>型号日期</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.product.name}</td>
                                <td>${item.financePrice}</td>
                                <td>${item.netPrice}</td>
                                <td>${item.interPublicPrice}</td>
                                <td>${item.b2bPublicPrice}</td>
                                <td>${item.poPrice}</td>
                          		<td><fmt:formatDate pattern="yyyy-MM" value="${item.dateMonth}"></fmt:formatDate></td>
                                <td>
                                	<a href="${ROOT}/admin/psi/product/price/edit-${item.id}" class="operate operate1"><i></i>编辑</a>
                                </td>
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
function deleteSeries(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		 location.href = "${ROOT}/admin/psi/product/delete?id="+id;
	};
}
</script>
</body>
</html>