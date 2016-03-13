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
      				<tr><td colspan="6"><span class="project_title">R03卖场绑定申请信息</span></td></tr>
		      		<tr>
		      			<td colspan="2" rowspan="5">基本信息</td>
		      			<td> 申请编号：</td>
		      			<td>${project.ppn }</td>
		      			<td>核销编号：</td>
		      			<td>${project.cpn }</td>
		      		</tr>
		      		<tr>
		      			<td> 申请代理商：</td>
		      			<td>${project.store.irName }</td>
		      			<td> 所属省份：</td>
		      			<td>${project.province.province }</td> 
		      		</tr>
		      		<tr>
		      			<td> 申请日期：</td>
		      			<td><fmt:formatDate value="${project.applyDate}" pattern="yyyy-MM-dd" /></td>
		      			<td> 申请主题：</td>
		      			<td>${project.applyTheme }</td> 
		      		</tr>
		      		<tr>
		      			<td> 申请费用：</td>
		      			<td>${project.applyPrice}</td>
		      		</tr>
		      		<tr name = "tr2">
		      			<td>特批邮件：</td>
		      			<td colspan="4">
		      				<img src ="${project.approveEmail}" height="200px" width="300px"></img>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td colspan="2" rowspan="6">申请内容</td>
		      			<td>卖场名称：</td>
		      			<td>${applyContent.storeName }</td>
		      			<td>卖场地址</td>
		      			<td>${applyContent.storeAddress}</td>
		      		</tr>
		      		<tr>
		      			<td>[SS]金额：</td>
		      			<td>${applyContent.SSMoney}</td>
		      			<td>[SS]店数：</td>
		      			<td>${applyContent.SSStoreCount}</td>
		      		</tr>
		      		<tr>
		      			<td>[AOC]金额：</td>
		      			<td>${applyContent.AOCMoney}</td>
		      			<td>[AOC]店数：</td>
		      			<td>${applyContent.AOCtoreCount}</td>
		      		</tr>
		      		<tr>
		      			<td>[ASUS]金额：</td>
		      			<td>${applyContent.ASUSMoney}</td>
		      			<td>[ASUS]店数：</td>
		      			<td>${applyContent.ASUStoreCount}</td>
		      		</tr>
		      		<tr>
		      			<td>PH：</td>
		      			<td>${applyContent.PH}</td>
		      			<td>总投入：</td>
		      			<td>${applyContent.totalInvest}</td>
		      		</tr>
		      		<tr>
		      			<td>MM投入：</td>
		      			<td>${applyContent.MMInvest}</td>
		      			<td>代理商投入：</td>
		      			<td>${applyContent.agentInvest}</td>
		      		</tr>
		      		<c:forEach items="${applyContent.retailStore}" var="item" varStatus="itemVs">
				     	<c:choose>
							<c:when test="${itemVs.index==0}">
								<tr>
					      			<td colspan="2" rowspan="${fn:length(applyContent.retailStore)}">店面</td>
					      			<td>零售店名称：</td>
					      			<td> ${item.retailStoreName}</td>
					      			<td>年预估销量：</td>
					      			<td> ${item.estimateSum}</td>
				      			</tr>
							</c:when>
							<c:otherwise>
								<tr>
					      			<td>零售店名称：</td>
					      			<td> ${item.retailStoreName}</td>
					      			<td>年预估销量：</td>
					      			<td> ${item.estimateSum}</td>
					      		</tr>
							</c:otherwise>
						</c:choose>	
			     	</c:forEach>
		      		<tr>
		      			<td colspan="2">情况说明：</td>
		      			<td colspan="4">${project.remark}</td>
		      		</tr>
      			</table>
       		 </form>
		</div>
	</div>
</body>
</html>		

