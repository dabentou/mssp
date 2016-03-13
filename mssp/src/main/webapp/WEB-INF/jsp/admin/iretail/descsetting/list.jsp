<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-装修设置维护</title>
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
                    <button onclick="batchCheck_btn()" class="button button-blue">保存</button>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>
                                	<input type="checkbox" onclick="batchCheck_cBox()" 	<c:if test="${fn:length(provinces)==fn:length(descSettings)}">checked="checked" </c:if>/> 序号
                                </th>
                                <th>省份</th>
                                <th>能否装修</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${provinces}" var="item">
                            <tr>
                                <td>
                                	<c:set scope="page" var="flagPro" value=""/>
                                	<c:forEach items="${descSettings}" var="DSitem" varStatus="itemVs">
							   				<c:if test="${item.id==DSitem.province.id}">
										   			<input type="checkbox" name='comm-cBox' checked="checked" value="${item.id}"/> ${item.id}
										   			<c:set scope="page" var="flagPro" value="${DSitem.id}"/>
										   	</c:if>
							     	</c:forEach>
							     	<c:if test="${flagPro==''}">
											<input type="checkbox" name='comm-cBox' value="${item.id}"/> ${item.id}
									</c:if>
									<c:set scope="page" var="flagPro" value=""/>
                                </td>
                                <td>${item.province}</td>
                                <td>
<%--                                 	<c:set var="flag" value="true" />
                            		<c:forEach items="${descSettings}" var="DSitem">
										<c:choose>
											<c:when test="${item.id==DSitem.province.id }">
													是
													  <c:set var="flag" value="false"/>
											</c:when>
											<c:otherwise>
												    <c:if test="${flag}">
											             否
											       </c:if>
											</c:otherwise>
										</c:choose>
							     	</c:forEach> --%>
							     	
							     	<c:set scope="page" var="flag" value=""/>
			     					<c:forEach items="${descSettings}" var="DSitem" varStatus="itemVs">
							   				<c:if test="${item.id==DSitem.province.id}">
										   			是
										   			<c:set scope="page" var="flag" value="${DSitem.id}"/>
										   	</c:if>
							     	</c:forEach>
							     	<c:if test="${flag==''}">
											  否
									</c:if>
									<c:set scope="page" var="flag" value=""/>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script>

window.batchCheck_cBox = function(){
	$("input[name='comm-cBox']").each(function(){
		if($(this).prop("checked")){
			$(this).prop("checked",false);
		}else{
			$(this).prop("checked",true);
		}
	});
}

window.batchCheck_btn = function(){
	var provinceIds = "";
	$("input[name='comm-cBox']").each(function(){
		if($(this).prop("checked")){
			provinceIds += $(this).val() + ",";
		}
	});
	$.ajax({
		type : "POST",
		url : "${ROOT}/admin/iretail/descsetting/save",
		data : {provinceIds : provinceIds},
		dataType : "json",
		success : function(json){
 		 	if(json.status == "OK"){
		 		alert(json.data);
		 		window.location.reload();
		 	}else{
		 		alert("操作失败！");
		 	} 
		}
	});
}
</script>
</body>
</html>