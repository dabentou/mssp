<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-iretail数据查询与导出</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<style>
		.main-container .text{
			width: 150px;
			height:34px;
		}
    </style>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="table-responsive">
                	<form id="form" class="form-inline" action="${ROOT }/admin/iretail/report/search.html" method="post">
                	   <div class="form-group">
			      			<label>日期：</label>
					      	<input type="text" name="applyTimeStart" class="form-control text datetimepicker" value="<fmt:formatDate value="${applyTimeStart}" type="date" />" /> -
					      	<input type="text" name="applyTimeEnd" class="form-control text datetimepicker" value="<fmt:formatDate value="${applyTimeEnd}" type="date" />" />
			      		</div>
                	   <div class="form-group">
				            <label>申请编号：</label>
				            <input type="text" class="form-control text" name="ppn" value="${ppn }">
			           </div>
			           <div class="form-group">
				            <label>项目类型：</label>
				           <div class="multiple-select">
			                 <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="type">
			                	<option value="R01">R01</option>
			                	<option value="R02">R02</option>
			                	<option value="R03">R03</option>
			                	<option value="R04">R04</option>
			                	<option value="R05">R05</option>
			                	<option value="R06">R06</option>
			                	<option value="R07">R07</option>
			                	<option value="R08">R08</option>
			                	<option value="R09">R09</option>
			                	<option value="R10">R10</option>
			                	<option value="R11">R11</option>
			                	<option value="R12">R12</option>
			                </select>
			              </div>
			           </div>
			           <%-- <div class="form-group">
			              <label>区域：</label>
			              <div class="multiple-select">
			              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="areaId">
			              	<c:forEach items="${areaList}" var="item">
			              		<c:if test="${areaId==null}">
			              			<option value="${item.id}" >${item.areaName }
			              		</c:if>
			              		<c:if test="${areaId!=null}">
			              			<c:set var="flag" value="true" />
			              			<c:forEach items="${areaId}" var="id" varStatus="stat">
			              			  <c:if test="${id==item.id}"><option value="${item.id}" selected>${item.areaName }<c:set var="flag" value="false"/></c:if>
				              		  <c:if test="${stat.last && flag}">
				              		  	<c:if test="${id!=item.id}"><option value="${item.id}" >${item.areaName }</c:if>
				              		  </c:if>
				              		</c:forEach>
			              		</c:if>
			            	</c:forEach>
			              </select>
			              </div>
			           </div> --%>
			           <div class="form-group">
			              <label>审核状态：</label>
			              <div class="multiple-select">
			              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="processStepId">
			              	<c:forEach items="${processStepList}" var="item">
			              		<c:if test="${processStepId==null}">
			              			<option value="${item.id}" >${item.operateStatus }
			              		</c:if>
			              		<c:if test="${processStepId!=null}">
			              			<c:set var="flag" value="true" />
			              			<c:forEach items="${processStepId}" var="id" varStatus="stat">
			              			  <c:if test="${id==item.id}"><option value="${item.id}" selected>${item.operateStatus }<c:set var="flag" value="false"/></c:if>
				              		  <c:if test="${stat.last && flag}">
				              		  	<c:if test="${id!=item.id}"><option value="${item.id}" >${item.operateStatus }</c:if>
				              		  </c:if>
				              		</c:forEach>
			              		</c:if>
			            	</c:forEach>
			              </select>
			              </div>
			           </div>
			           <input type="submit" value="查询" class="button button-blue" />
			           <input type="button" id="exportButton" value="导出报表" class="button button-blue" />
			           <!-- <input type="button" id="exportPDF" value="生成pdf" class="button button-blue" /> --> 
                	</form>
                	<p></p>
                    <table class="table table-hover mytable">
	                    <thead>
							<tr>
								<th><!--  <input type="checkbox" onclick="batchCheck_cBox()"/> -->  ID</th>
								<th>申请编号</th>
								<th>申请代理商</th>
								<th>申请日期</th>
								<th>申请主题</th>
								<th>申请价格</th>
								<th>类型</th>
								<th>状态</th>
								<th>操作</th>
							</tr>
						</thead>
                        <tbody>
						<c:forEach items="${paging.content}" var="item">
							<tr>
								<td> <%-- <input type="checkbox" name="comm-cBox" value="${item.id}"/> --%>  ${item.id}</td>
								<td>${item.ppn}</td>
								<td>${item.applyUser.irName}</td>
								<td>
									<fmt:formatDate value="${item.applyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${item.applyTheme}</td>
								<td>${item.applyPrice}</td>
								<td>${item.iType}</td>
								<td>${item.processStep.operateStatus}</td>
								<td>
									<a href="${ROOT }/admin/iretail/report/delete-${ item.id}" class="operate operate2"><i></i>删除</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
                    </table>
                    <div class="page">
						<p class="pull-left">当前显示<span>${paging.from+1}-${paging.from+50}</span>,共<span>${paging.totalElements}</span>条记录</p>
		  				<ms:page from="${paging.from}" size="${paging.size}" totle="${paging.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
	  				</div> 
                </div>
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

window.batchCheck_cBox = function(){
	$("input[name='comm-cBox']").each(function(){
		if($(this).prop("checked")){
			$(this).prop("checked",false);
		}else{
			$(this).prop("checked",true);
		}
	});
}

$(document).ready(function(){
	$('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
	$("#exportButton").click(function(){
		var type=$("select[name='type']").val();
		if(type == null||type == ""){
			alert("请选择要导出的报表的项目类型");
		}else{
			location.href = "${ROOT}/admin/iretail/report/excel?"+$("#form").serialize();
		}
	});
	
	//生成pdf按钮事件
 	$("#exportPDF").click(function(){
		var projectIds = "";
		$("input[name='comm-cBox']").each(function(){
			if($(this).prop("checked")){
				projectIds += $(this).val() + ",";
			}
		});
		location.href = "${ROOT}/admin/iretail/project/exportPDF?projectIds="+projectIds;
	});
})
</script>
</body>
</html>