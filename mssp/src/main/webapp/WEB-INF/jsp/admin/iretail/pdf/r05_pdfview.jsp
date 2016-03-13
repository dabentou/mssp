<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>查看项目</title>
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
		body {
			font-family: SimSun;
			font-size: 13px;
			margin: 0px;
			padding: 0px;
		}
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
	</style>
</head>
<body>
  <div class="wrap">
		<div class="content"   align="center">
		 	<form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
		 		<table class="maintable table table-hover"  style="width: 55%;">
		      		<tr><td colspan="6"><span class="project_title">R05零售店会议申请信息</span></td></tr>
		      		<tr>
		      			<td rowspan="5">基本信息</td>
		      			<td> 申请编号：</td>
		      			<td>${project.ppn }</td>
		      			<td> 申请代理商：</td>
		      			<td>${project.store.irName }</td>
		      		</tr>
		      		<tr>
		      			
		      			<td> 所属省份：</td>
		      			<td>${project.province.province }</td> 
		      			<td> 申请日期：</td>
		      			<td><fmt:formatDate value="${project.applyDate}" pattern="yyyy-MM-dd" /></td>
		      		</tr>
		      		<tr>
		      			
		      			<td> 申请主题：</td>
		      			<td>${project.applyTheme }</td> 
		      			<td> 申请费用：</td>
		      			<td>${project.applyPrice}</td>
		      		</tr>
		      		<tr>
		      			<td> 有效期：</td>
		      			<td><fmt:formatDate value="${project.beginTime}" pattern="yyyy-MM-dd" /></td>
		      			<td> 至：</td>
		      			<td><fmt:formatDate value="${project.endTime}" pattern="yyyy-MM-dd" /></td>
		      		</tr>
		      		<tr name = "tr2">
		      			<td>批复邮件：</td>
		      			<td colspan="4">
		      				<img src ="${project.approveEmail}" height="200px" width="300px"></img>
		      			</td>
		      		</tr>
		      		
		      		<tr>
		      			<td rowspan="4">会议流程：</td>
		      			<td>会议名称：</td><td colspan="4">${applyContent.meetingName}</td>
		      		</tr>
		      		<tr>
		      			<td>会议地点：</td>
		      			<td colspan="4">${applyContent.meetingPlace}</td>
		      		</tr>
		      		<tr>
		      			<td>会议时间：</td>
		      			<td colspan="4">${applyContent.meetingTime}</td>
		      		</tr>
		      		<tr>
		      			<td>行程安排：</td>
		      			<td colspan="3">
		      				<table style="width: 100%;">
		      					<thead>
		      						<tr>
		      							<td>时间</td>
		      							<td>内容</td>
		      							<td>培训人</td>
		      						</tr>
		      					</thead>
		      					<tbody>
		      						<tr>
		      							<c:forEach items="${meeting}" var="item" >
		      								<tr><td>${item.applyTime}</td>
		      								<td>${item.applyContent}</td>
		      								<td>${item.applyPerson}</td></tr>
		      							</c:forEach>
		      						</tr>
		      					</tbody>
		      				</table>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td rowspan="3">旅游行程：</td>
		      			<td>目的地：</td><td colspan="4">${applyContent.travelPosition}</td>
		      		</tr>
		      		<tr>
		      			<td>参与人数：</td>
		      			<td colspan="4">${applyContent.travelTotalPeople}</td>
		      		</tr>
		      		<tr>
		      			<td>旅游行程：</td>
		      			<td colspan="4">${applyContent.travelTrip}</td>
		      		</tr>
		      		
		      		
		      		<tr>
		      			<td >费用明细：</td>
		      			<td colspan="4">
		      				<table style="width: 100%;">
		      					<thead>
		      						<tr>
		      							<td>会议室（元）</td><td>中餐（元）</td><td>晚餐（元）</td><td>其他（元）</td><td>总金额</td>
		      						</tr>
		      					</thead>
		      					<tbody>
		      						<tr>
		      							<c:forEach items="${costing}" var="item" >
		      								<tr><td>${item.costMeetRoom}</td>
		      								<td>${item.costLunch}</td>
		      								<td>${item.costDinner}</td>
		      								<td>${item.costOther}</td>
		      								<td>${item.costTotal}</td></tr>
		      							</c:forEach>
		      						</tr>
		      					</tbody>
		      				</table>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td>情况说明：</td>
		      			<td colspan="5" >${project.remark}</td>
		      		</tr>
		      		<tr>
		      			<td>其他：</td>
		      			<td colspan="5" >${applyContent.others}</td>
		      		</tr>
      			</table>
       	 	</form>
		</div>
	</div>
</body>
</html>		
