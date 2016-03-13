<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>零售店支持金申请管理</title>
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
		.table td{
		border:1px solid #ddd
		}
		.table th{
		border:1px solid #ddd
		}
		body {
			font-family: SimSun;
			font-size: 13px;
			margin: 0px;
			padding: 0px;
		}
	</style>
</head>
<body>
	<div class="wrap">
	    <div class="content"  align="center">
	      	 <form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
	      		 <table class="maintable table table-hover"  style="width: 55%;">
		      		 <tr><td colspan="5"><span class="project_title">零售店支持金申请管理</span></td></tr>
		    		 <tr>
		    		 		 <td rowspan="6">基本信息</td>
		           			 <td ><span class="star">*</span>申请编号：</td>
		     	 			 <td colspan="3">${project.ppn }</td>
		     		 </tr>
		     		 <tr>
		           			 <td> 所属省份：</td>
		      				 <td>${project.province.province }</td> 
		           			 <td><span class="star">*</span>申请日期：</td>
		     	 			 <td><fmt:formatDate value="${project.beginTime }" pattern="yyyy-MM-dd" /></td>
		     		 </tr>
		     		 <tr>
		           			 <td><span class="star">*</span>申请主题：</td>
		     	 			 <td>${project.applyTheme }</td>
		           			 <td><span class="star">*</span>申请费用：</td>
		     	 			 <td>${project.applyPrice }</td>
		     		 </tr>
		     		 <tr>
		           			 <td><span class="star">*</span>有效期：</td>
		     	 			 <td><fmt:formatDate value="${project.beginTime }" pattern="yyyy-MM-dd" /></td>
		     	 			 <td >至</td>
		     	 			<td><fmt:formatDate value="${project.endTime }" pattern="yyyy-MM-dd" /></td>
		     		 </tr>
		     		 <tr>
		           			 <td><span class="star">*</span>供应商：</td>
		     	 			 <td colspan="3">${project.provider }</td>
		     		 </tr>
		     		 
		     		 <tr>
		     		 	<td><span class="star">*</span>邮件特批：</td>
		     		 	<td colspan="3">
		     		 		<img width="330" src="${project.approveEmail}"/><br/>
		     		 	</td>
		     		 </tr>
		     		 
		     		 <tr>
		     		 	<td><span class="star">*</span>情况说明：</td>
		     		 	<td colspan="4">
		     		 		${project.remark }
		     		 	</td>
		     		 </tr>
		     		 
		     		 <tr>
		     		 	<td><span class="star">*</span>申请内容：
		     		 	</td>
		     		 	<td colspan="4">
		     		 		${project.remark }
		     		 	</td>
		     		 </tr>
				</table>
			</form>
	  	</div>
  	</div>
</body>
</html>
