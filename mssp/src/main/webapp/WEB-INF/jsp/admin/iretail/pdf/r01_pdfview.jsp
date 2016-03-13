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
		      	<tr><td colspan="6"><span class="project_title">R01零售店装修申请信息</span></td></tr>
		      		<tr>
		      			<td colspan="2" rowspan="5">基本信息</td>
		      			<td> 申请编号：</td>
		      			<td>${project.ppn }</td>
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
		      			<td> 供应商：</td>
		      			<td>${project.provider }</td> 
		      		</tr>
		      		<tr>
		      			<td> 有效期：</td>
		      			<td><fmt:formatDate value="${project.beginTime}" pattern="yyyy-MM-dd" /></td>
		      			<td> 至：</td>
		      			<td><fmt:formatDate value="${project.endTime}" pattern="yyyy-MM-dd" /></td>
		      		</tr>
		      		<tr name = "tr2">
		      			<td>特批邮件：</td>
		      			<td colspan="5">
		      				<img src ="${project.approveEmail}" height="200px" width="300px"></img>
		      			</td>
		      		</tr>
		      		<tr>
		      			<td colspan="2" rowspan="7">申请内容</td>
		      			<td>零售店名称：</td>
		      			<td>${iretailR01.iName }</td>
		      			<td>装修类型</td>
		      			<td>${iretailR01.decorateLevel}</td>
		      		</tr>
		      		<tr>
		      			<td>城市类别：</td>
		      			<td>${iretailR01.cityLevel}</td>
		      			<td>城市名称：</td>
		      			<td>${iretailR01.cityName}</td>
		      		</tr>
		      		<tr>
		      			<td>卖场名称：</td>
		      			<td>${iretailR01.shopPlace}</td>
		      			<td>店面级别：</td>
		      			<td>${iretailR01.iLevel}</td>
		      		</tr>
		      		<tr>
		      			<td>店面位置：</td>
		      			<td>${iretailR01.iLocation}</td>
		      			<td>月全产品销量：</td>
		      			<td>${iretailR01.yqVolume}</td>
		      		</tr>
		      		<tr>
		      			<td>店面积：</td>
		      			<td>${iretailR01.iAcreage}</td>
		      			<td>月零售产品销量：</td>
		      			<td>${iretailR01.lsVolume}</td>
		      		</tr>
		      		<tr>
		      			<td>店负责人：</td>
		      			<td>${iretailR01.iPrincipal}</td>
		      			<td>月零售产品占比：</td>
		      			<td>${iretailR01.lsRate}</td>
		      		</tr>
		      		<tr>
		      			<td>联系手机：</td>
		      			<td>${iretailR01.phone}</td>
		      			<td>门店目标量：</td>
		      			<td>${iretailR01.iTargetVolume}</td>
		      		</tr>
		      		
		      		
		      		<c:forEach items="${materialDetail}" var="item" varStatus="itemVs">
				     	<c:choose>
							<c:when test="${itemVs.index==0}">
								<tr>
					      			<td colspan="2" rowspan="${fn:length(materialDetail)}">物理明细</td>
					      			<td>物料：</td>
					      			<td> ${item.material}</td>
					      			<td>个数：</td>
					      			<td> ${item.count}</td>
				      			</tr>
							</c:when>
							<c:otherwise>
								<tr>
					      			<td>物料：</td>
					      			<td>${item.material}</td>
					      			<td>个数：</td>
					      			<td>${item.count}</td>
					      		</tr>
							</c:otherwise>
						</c:choose>	
			     	</c:forEach>
		      		<tr>
		      			<td colspan="2">情况说明：</td>
		      			<td colspan="4">${project.remark}</td>
		      		</tr>
		      	</table>
		      	<c:if test="${check }">
		       		<table  class="maintable table table-hover"  style="width: 90%;" border="1">
		       		<tr>
			      			<td colspan="6"><span class="project_title">核销图片</span></td>
			      		</tr>
		       		<tr>
			      			<td colspan="2">核销效果图：</td>
			      			<td colspan="4"><img src ="${image.budget}" width="20%"></img></td>
			      	</tr> 
			      	<tr>
			      			<td colspan="2">核销预算：</td>
			      			<td colspan="4"><img src ="${image.effectPicture}" width="20%"></img></td>
			      	</tr> 
			      </table>
		       </c:if>
		       <c:if test="${check2 }">
		       		<table  class="maintable table table-hover"  style="width: 90%;" border="1">
		       		<tr>
			      			<td colspan="6"><span class="project_title">验收图片</span></td>
			      		</tr>
		       		<tr>
			      			<td colspan="2">验收单：</td>
			      			<td colspan="4"><img src ="${image.over1}" width="20%"></img></td>
			      	</tr> 
			      	<tr>
			      			<td colspan="2">完工照片：</td>
			      			<td colspan="4"><img src ="${image.over2}" width="20%"></img></td>
			      	</tr> 
			      </table>
		       </c:if>
	        </form>
		</div>
	</div>
</body>
</html>		

