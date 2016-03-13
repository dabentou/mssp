<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>申请列表</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
     <div class="content">
      <div class="filter">
        <form class="form-inline" >
          <div class="form-group"  style="float: right;">
			<a href="${ROOT}/iretail/project/apply.html?type=R01" class="button button-lightblue">申请</a>
          </div>
        </form>
      </div>
      <table class="table table-hover maintable"  id="myTable">
        <thead>
          <tr>
          	<th>类型 </th>
            <th>型号 </th>
            <th>申请主题</th>
            <th>申请费用</th>
            <th>供应商</th>
            <th>申请代理商</th>
            <th>所属大区</th>
            <th>申请时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
     	<c:forEach items="${list}" var="item">
     		<tr>
     			<td>${item.retailProject.iType}</td>
     			<td>${item.id}</td>
     			<td>${item.retailProject.applyTheme}</td>
     			<td>${item.retailProject.applyPrice}</td>
     			<td>${item.retailProject.provider}</td>
     			<td>${item.retailProject.store.irName}</td>
     			<td>${item.retailProject.city.commArea.areaName}</td>
     			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${item.retailProject.applyDate}"></fmt:formatDate></td>
     			<td>${item.retailProject.processStep.operateStatus}</td>
     			<td>
     				<a href="${ROOT}/iretail/project/view-${item.id}-${item.retailProject.iType}" class="operate operate1"><i></i>查看</a>
     			</td>
     		</tr>
     	</c:forEach>
        </tbody>
      </table>
<%--       <div class="page">
		<p class="pull-left">当前显示<span>${R01List.from+1}-${R01List.from+10}</span>,共<span>${R01List.totalElements}</span>条记录</p>
		<ms:page from="${R01List.from}" size="${R01List.size}" totle="${R01List.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
	</div>  --%>
      </div>
    </div>
  </div>
</body>
<script type="text/javascript">
function backSorH(){
    h = $(window).height();
    t = $(document).scrollTop();
    if(t > h){
        $('#backtop').show();
    }else{
        $('#backtop').hide();
    }
}
$(document).ready(function(){
    //返回顶部
    backSorH();
    $('#backtop').click(function(){
        $(document).scrollTop(0);
    });
    //日期控件
    $('#datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
});
$(window).scroll(function(e){
    backSorH();
});
</script>
</html>