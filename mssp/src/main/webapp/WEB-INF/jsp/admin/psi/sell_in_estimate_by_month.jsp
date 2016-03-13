<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-月度销售预估</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
              <form id="form" action="${ROOT }/admin/psi/estimate/save" method="post">
                <div class="operate-search" style="text-align:right;">
                    <c:if test="${isSubmit}"><input type="button" class="button button-blue" onclick="tips()" value="确定提交" /> </c:if>
                    <c:if test="${!isSubmit}"><input type="submit" class="button button-blue" value="确定提交" /> </c:if>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                            	<th>寸别</th>
                                <th>系列</th>
                                <th>型号</th>
                                <th>备注</th>
                                <th>代理<fmt:formatDate value="${date}" pattern="MM" var="d" /></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${sellInTempList}" var="item">
                            <%-- <tr>
                                <td><input type="hidden" value="${item.commAgent.id }" name="agentId" /> ${item.commAgent.irName }</td>
                                <td><input type="text" class="text form-control" value="${item.estimateVolume }" name="sellInVolume" <c:if test="${item.isEstimate == 1}">disabled</c:if> /></td>
                            </tr> --%>
                            <tr>
                                <td>${item.size }</td>
                                <td>${item.series }</td>
                                <td>${item.productName }</td>
                                <td>${item.comment }</td>
                                <td><input type="text" class="text form-control" name="sellInVolume" /></td>
                                <td><input type="text" class="text form-control" name="sellInVolume" /></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
              </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
<script type="text/javascript">
function tips(){
	alert("本月已经提交过！");
}
$(document).ready(function(){
    //提交前判断是否没有填写
    $("#form").submit(function(){
    	var count=0;
    	var $sellInVolume=$("input[name=sellInVolume]");
    	for(var i=0;i<$sellInVolume.length;i++){
    		if($sellInVolume[i].value!=0){
    			count++;
    		}
    	}
    	if(count==0){
    		alert("至少填写一个代理商的预估量");
    		return false;
    	}
    });
});
</script>
</html>