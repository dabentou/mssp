<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>申请列表</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
   <style>
		.input_style{
			width: 160px;
			text-align:center;
		}
		.project_title{
		font-weight: bold;
		}
		.star{
			color: red;
		}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
	  <div class="wrap">
    	<%@include file="/WEB-INF/include/notice.jsp" %>
    		<div class="content">
    		<c:if test="${enabled}">
    			<%@include file="/WEB-INF/include/iretail_menu.jsp" %>
    			<p style="height:10px;"></p>
    		</c:if>
			  <div class="filter">
				<form id="form" class="form-inline" action="${ROOT }/iretail/project/search">
					<%-- <c:if test="${!enabled}">
						<div class="form-group">
				            <label>申请日期：</label>
				            <input type="text" class="form-control text datetimepicker" name="applyTimeStart" value="${applyTimeStart}" /> -
				            <input type="text" class="form-control text datetimepicker" name="applyTimeEnd" value="${applyTimeEnd}" />
				          </div>
				           <p style="height:10px;"></p>
					</c:if> --%>
		           <div class="form-group">
		            <label>申请编号：</label>
		            <input type="text" class="form-control text" name="ppn" value="${ppn }" />
		           </div>
		           <div class="form-group">
		            <label>省份：</label>
		            <select class="form-control text" name="provinceId">
		            	<option value="">--请选择--</option>
			            <c:forEach items="${provinces}" var="item">
							<option value="${item.id}" <c:if test="${item.id==provinceId}">selected</c:if>>${item.province}</option>
			     		</c:forEach>
		     		</select>
		           </div>
		           
		           <div class="form-group">
		            <label>审批状态：</label>
		            <select class="form-control text" name="processStepId">
		            	<option value="">--请选择--</option>
			            <c:forEach items="${processSteps}" var="item">
							<option value="${item.id}" <c:if test="${item.id==processStepId}">selected</c:if>>${item.operateStatus}</option>
			     		</c:forEach>
			     		
			     			<c:if test="${dataType == 1 }">
			     				<option value="0">审批完毕</option>
			     			</c:if>
			     			<c:if test="${dataType == 2 }">
			     				<option value="-1">核销完毕</option>
			     			</c:if>
			     		
			     			
		     		</select>
		     		<input type="hidden" name="type" value="${type}"/>
		           </div>
		          
		          
		           <input type="submit" value="查询" class="button button-lightblue">
		           <a id="checkedApply"  href="${ROOT}/iretail/project/apply.html?type=${type}" class="button button-lightblue">申请</a>
				</form>
			  </div>
<!--     		<form class="form-inline" >
         		<div class="form-group"  style="float: right;">
					
          		</div>
       		</form> -->
    		
			<div class="table-responsive">
				<table class="maintable table table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>申请编号</th>
							<th>申请代理商</th>
							<th>申请日期</th>
							<th>申请主题</th>
							<th>类型</th>
							<th>是否冻结</th>
							<th>下一步状态</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paging.content}" var="item">
							<tr>
								<td>${item.id}</td>
								<td>${item.ppn}</td>
								<td>${item.store.irName}</td>
								<td>
									<fmt:formatDate value="${item.applyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${item.applyTheme}</td>
								<td>${item.iType}</td>
								<td>${item.states==1?"是":"否"}</td>
								<%-- <td>${item.processStep.operateStatus}</td> --%>
								<c:if test="${item.processStep!=null}">
									<td>${item.processStep.operateStatus}</td>
								</c:if>
								<c:if test="${item.processStep ==null}">
									<td>审批流程结束</td>
								</c:if>
								
								<td>
									<a href="${ROOT}/iretail/project/view-${item.id}-${item.iType}"
										class="operate operate4"><i></i>查看</a>
									<c:if test="${item.processStep.isFirst==1}">
										<a href="${ROOT }/iretail/project/delete-${ item.id}-${item.iType}" class="operate operate2"><i></i>删除</a>
									</c:if>	
										<c:choose>
											<c:when test="${(sessionScope.user.commRole.id==item.processStep.commRole.id)&&(item.processStep.statusValue==10) }">
													<a href="${ROOT }/iretail/project/flow-${ item.id}/2">上传资料</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.processStep.commRole.id)&&(item.processStep.statusValue==100) }">
													<a href="${ROOT }/iretail/project/flow-${ item.id}/3">上传验收单和完工照片</a>
											</c:when>
											<c:when test="${sessionScope.user.commRole.id==item.processStep.commRole.id }">
													<a href="${ROOT }/iretail/project/flow-${ item.id}/1">审批</a>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>
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
</body>
<script type="text/javascript">
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
$(document).ready(function(){
	//日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
    $("#exportButton").click(function(){
		 location.href = "${ROOT}/b2i/report/excel?"+$("#form").serialize();
	});
    if(${type != 'R01'}&&${dataType} == 2){
		   $("#checkedApply").remove();
	   } 
});

//日期控件
$('.datetimepicker').datetimepicker({
    format: 'yyyy-mm-dd',
    autoclose: true,
    minView:2
});
</script>
</html>





