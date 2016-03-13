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
              <form id="form" class="form-inline" action="${ROOT }/admin/psi/report/search" method="post">
                <div class="operate-search">
                    <div class="form-group">
		      			<label>日期：</label>
				      	<input type="text" name="startDate" class="form-control text datetimepicker" value="<fmt:formatDate value="${startDate}" type="date" />" /> -
				      	<input type="text" name="endDate" class="form-control text datetimepicker" value="<fmt:formatDate value="${endDate}" type="date" />" />
		      		</div>
		      		<div class="form-group">
		      			<label>出货渠道：</label>
		      			<select class="select" name="channelType">
		      				<option value="1" <c:if test="${channelType==1}">selected</c:if> >飞生</option>
		      				<option value="2" <c:if test="${channelType==2}">selected</c:if>>越海</option>
		      			</select>
		      		</div>
		      		<div class="form-group">
		              <label>代理商：</label>
		              <div class="multiple-select">
		              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="agentId">
		              	<c:forEach items="${agentList}" var="item">
		            		<option value="${item.id}" >${item.irName }</option>
		            	</c:forEach>
		              </select>
		              </div>
		            </div>
		      		<div class="form-group">
		              <label>产品型号：</label>
		              <div class="multiple-select">
		              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="productId">
		              	<c:forEach items="${productList}" var="item">
		            		<option value="${item.id}" >${item.name }</option>
		            	</c:forEach>
		              </select>
		              </div>
		            </div>
		            <input type="submit" value="查询" class="button button-blue" />
		            <input type="button" id="exportButton" value="导出报表" class="button button-blue" />
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                            	<th>型号</th>
					            <th>销售预估</th>
					            <th>sellIn</th>
					            <th>sellOut</th>
					            <th>库存</th>
					            <th>Nameofsold-toparty</th>
					            <th>Nameoftheship-toparty</th>
					            <th>区域</th>
					            <th>城市</th>
					            <th>寸别</th>
					            <th>寸别-2</th>
					            <th>面板</th>
					            <th>系列</th>
					            <th>零售产品系列</th>
					            <th>出货渠道</th>
					            <th>代理商</th>
					            <th>日期</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${reportVoList}" var="item">
			        	<tr>
			        		<td>${item.product.name}</td>
			        		<td>${item.estimateVolume}</td>
			        		<td>${item.sellIn}</td>
			        		<td>${item.sellOut}</td>
			        		<td>${item.inventory}</td>
			        		<td>${item.nameofsoldToparty}</td>
			        		<td>${item.nameoftheshipToparty}</td>
			        		<td>${item.agent.commCity.commArea.areaName}</td>
			        		<td>${item.agent.commCity.cityName}</td>
			        		<td>${item.product.size1}</td>
			        		<td>${item.product.size2}</td>
			        		<td>${item.product.pannel.name}</td>
			        		<td>${item.product.commSeries.name}</td>
			        		<td>${item.product.productSeries.name}</td>
			        		<td><c:choose><c:when test="${item.channelType==1}">飞生</c:when><c:when test="${item.channelType==2}">越海</c:when></c:choose></td>
			        		<td>${item.agent.irName}</td>
			        		<td><fmt:formatDate value="${item.date}" type="both" /></td>
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
<script type="text/javascript">
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
$(document).ready(function(){
	$('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
	$("#exportButton").click(function(){
		 location.href = "${ROOT}/admin/psi/report/excel?"+$("#form").serialize();
	});
})
</script>
</body>
</html>