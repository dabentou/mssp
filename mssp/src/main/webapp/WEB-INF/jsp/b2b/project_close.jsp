<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目结案申报</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
		.input_style{
			width: 160px;
		}
		.project_title{
		font-weight: bold;
		}
		.star{
			color: red;
		}
		.table td{
		border:1px solid #ddd
		}
		.table th{
		border:1px solid #ddd
		}
	</style>
	<script type="text/javascript">
	$(function(){
			$("#pass").click(function(){
				var projectInfo = $("#projectInfo-area").val();
				if(projectInfo==''){
					alert('项目信息不能为空！');
					return;
				}
		 		$("#isPass").val("0");
				$("#myFrom").submit();
			});
	});
	</script>
</head>
<body>
<%@include file="/WEB-INF/include/header.jsp" %>
<%@include file="/WEB-INF/include/func.jsp" %>
<div class="wrap">
<%@include file="/WEB-INF/include/notice.jsp" %>
<div class="content"   align="center">
	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/applyClose" enctype="multipart/form-data" method="post" >
		<input type="hidden" name="isPass"   id="isPass" />
		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
		<table class="maintable table table-hover"  style="width: 65%;" border="1">
			<tr><td colspan="5"><span class="project_title">项目结案申报</span></td></tr>
      		<tr>
      			<td>项目信息</td>
      			<td> 项目名称：</td>
      			<td>${project.pName }</td>
      			<td>项目编号：</td>
      			<td>${project.pCode }</td>
      		</tr>
      		
      		<tr>
      			<td>销售信息</td>
      			<td>
      				 型号
      			</td>
      			<td>
					<c:forEach items="${saList}" var="item" >
						${item.productPrice.product.name }<br />
					</c:forEach>
				</td>
      			<td>销售数量：</td>
      			<td id="sn">
      				<c:forEach items="${saList}" var="item" >
						${item.number}<br />
						
					</c:forEach>
      			 </td>
      		</tr>
      		
      		<tr>
      			<td rowspan="2">客户信息</td>
      			<td>
      				 最终用户公司名称	
      			</td>
      			<td>${project.customerName }</td>
      			<td> 联系人/电话：</td>
      			<td>${project.customerLinkman } / ${project.customerPhone }</td>
      		</tr>
      		<tr>
      			<td> 涉及行业：</td>
      			<td>
	      			${project.business.name }
				</td>
      			<td> 电子邮件地址：</td>
      			<td> ${project.customerAddress } </td>
      		</tr>
      		
      		<tr>
      			<td rowspan="2">代理信息</td>
      			<td>
      				 总代名称
      			</td>
      			<td>
      				${project.agent.irName}
      			</td>
      			<td> 联系人/电话：</td>
      			<td></td>
      		</tr>
      		<tr>
      			<td> 二级代理商：</td>
      			<td>
					${project.commSi.irName}
				</td>
      			<td> 联系人/电话：</td>
      			<td></td>
      		</tr>
      		
      		<tr >
      			<td ><span class="star">*</span> 项目信息：</td>
      			<td colspan="5"><input  type="textarea" id='projectInfo-area'  name="projectInfo" placeholder="" style="width:670px;height:200px;"/>   </td>
      		</tr>
      		
      		<tr><td colspan="6">
      			<table class="table table-hover maintable" id="myTable">
				<thead>
					<tr>
						<td>No. </td>
						<td>型号</td>
						<td>公开价（元）</td>
						<td>申请单价（元）</td>
						<td>折扣率（%）</td>
						<td>数量（台）</td>
						<td>单台返现 </td>
						<td>合计 </td>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${saList}" var="item">
						<tr>
							<td>${item.id}</td>
							<td>${item.productPrice.product.name}</td>
							<td>${item.competePrice}</td>
							<td>${item.applyPrice}</td>
							<td>${item.discountRate}</td>
							<td>${item.number}</td>
							<td>${item.competePrice -item.applyPrice}</td>
							<td>${(item.competePrice -item.applyPrice)*item.number}</td>
						</tr>
					</c:forEach>
				</tbody>
      		</table>
      		</td>
      		</tr>
		</table>
			<table  class="maintable table table-hover"  style="width: 65%;" border="1">
      		<tr>
      			<td colspan="5"><span class="project_title">审批记录/操作</span></td>
      		</tr>
      		<c:forEach items="${logList }" var="item">
	      		<tr>
	      			<td>审核人：${item.user.realName }</td>
	      			<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
	      			<td>步骤：${item.step.operateStatus }</td>
	      			<td>是否通过：${item.isPass==0?'通过':'不通过' }</td>
	      			<td>审批备注：${item.approveMsg }</td>
	      		</tr>
      		</c:forEach>
      		<tr>
	      			<td colspan="5">
	 					<input id="pass" type="button" class="button button-lightblue" value="申请" />&nbsp;&nbsp;&nbsp;  
					</td>	
	      		</tr>
      	</table>
	</form>
</div>
</html>