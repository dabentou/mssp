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
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
	</style>
	<script type="text/javascript">
	$(function(){
			$("#pass").click(function(){
				$("#isPass").val("0");
				$("#myFrom").submit();
			});
			
			$("#reject").click(function(){
				if(valiForm()){
					$("#isPass").val("1");
					$("#myFrom").submit();
				}
			});
			$("#saveImg").click(function(){
					var file = $("input[name=files]");
					for(var i=0;i<file.length;i++){
						if(file[i].value.length == 0)
						alert("请上传第"+(i+1)+"张图片！");
					}
					$("#myFroms").submit();
			});
			valiForm = function(){
				if($("#approveMsg").val()==null||$("#approveMsg").val()==""){
					alert("请输入不通过的原因！");
					$("#approveMsg").focus();
					return false;
				}
				return true;
			}
	});
	</script>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
		<div class="content"   align="center">
		 	<form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
		 	 <input type="hidden" name="isPass"  id="isPass"/>
      	  <input type="hidden" name="flowType" value="${flowType }"/>
      	  <input type="hidden" name="projectId"  value="${project.id }" />
      	<table class="maintable table table-hover"  style="width: 85%;" border="1">
      	<tr><td colspan="6"><span class="project_title">R01零售店装修申请信息</span></td></tr>
      		<tr>
      			<td colspan="2" rowspan="5">基本信息</td>
      			<td> 申请编号：</td>
      			<td>${project.ppn }</td>
      		</tr>
      		<tr>
      			<td> 申请代理商：</td>
      			<td>${project.applyUser.irName }</td>
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
      			<td>
      				 <c:if test="${project.provider== null }">
      					<input  type="text"  class="input_style" name="provider" value="${project.provider }" />
      				</c:if>
      				 <c:if test="${project.provider!= null }">
      				 	${project.provider }
      				 </c:if>
      				
      			</td> 
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
      				<%-- <img src ="${project.approveEmail}" height="200px" width="300px"></img> --%>
      				<c:forEach items="${fn:split(project.approveEmail,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
					</c:forEach>
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
<%--       		<tr name = "tr2">
      			<td>费用明细：</td>
      			<td colspan="5">
      				<img src ="${iretailR01.chargesDetail}"width="40%"></img>
      			</td>
      		</tr> --%>
  <%--     		<tr>
      			<td colspan="2">费用明细备注：</td>
      			<td colspan="4">${iretailR01.retailProject.approveEmailRemark}</td>
      		</tr> --%>
<%-- 	      	<tr name = "tr1" >
	      		<td colspan="6"><span class="project_title">实景图</span></td>
	      	</tr>
	      	<tr name = "tr1">
	      		<td colspan="6" >
		      			<c:forEach items="${fn:split(iretailR01.iRealPic,',')}" var="item" varStatus="itemVs">
								<img src ="${item}" width="20%"></img>
				     	</c:forEach>
	      		</td>
	      	</tr>
	      	
	      	<tr name = "tr1" >
	      		<td colspan="6"><span class="project_title">效果图</span></td>
	      	</tr>
	      	<tr name = "tr1">
	      		<td colspan="6" >
		      			<c:forEach items="${fn:split(iretailR01.iResultPic,',')}" var="item" varStatus="itemVs">
								<img src ="${item}" width="20%"></img>
				     	</c:forEach>
	      		</td>
	      	</tr> --%>
      	</table>
      	<c:if test="${check }">
       		<table  class="maintable table table-hover"  style="width: 90%;" border="1">
       		<tr>
	      			<td colspan="6"><span class="project_title">核销资料</span></td>
	      		</tr>
       		<tr>
	      			<td colspan="2">效果图：</td>
	      			<td colspan="4"><img src ="${image.budget}" width="20%"></img></td>
	      	</tr> 
	      	<tr>
	      			<td colspan="2">装修预算：</td>
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
      	 <table  class="maintable table table-hover"  style="width:80%;">
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
      		<c:if test="${isShowApprove }">
	      		<tr>
	      			<td colspan="1">
	 					<input id="pass" type="button" class="button button-lightblue" value="通过" />&nbsp;&nbsp;&nbsp;  
	      				<input id="reject" type="button" class="button button-lightblue" value="不通过" />
					</td>
	      			<td colspan="4" >
	      				<textarea class="col-md-6 form-control textarea" rows="3"  style="width: 100%;" id="approveMsg" name="approveMsg" ></textarea>
	      			</td>
	      		</tr>
      		</c:if>
      	</table>
      	
        </form>
        <form id="uploadForm" action="${ROOT}/iretail/project/upload" enctype="multipart/form-data" method="post" >
       	 <input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        	<c:if test="${isUploadVerificImg }">
		      	<table  class="maintable table table-hover"  style="width: 90%;" border="1">
	      		<tr>
	      			<td colspan="6"><span class="project_title">上传核销资料</span></td>
	      		</tr>
	      		<tr>
	      			<td colspan="2">效果图：</td>
	      			<td colspan="4"><input type="file" id="effectPicture" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="2">装修预算：</td>
	      			<td colspan="4"><input type="file" id="budget" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="6"><input id="image" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
      		</c:if>
        </form>
       
       <form id="uploadForm2" action="${ROOT}/iretail/project/uploadR01Over" enctype="multipart/form-data" method="post" >
       	 <input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        	<c:if test="${R01Over }">
		      	<table  class="maintable table table-hover"  style="width: 90%;" border="1">
	      		<tr>
	      			<td colspan="6"><span class="project_title">上传验收单和完工照片</span></td>
	      		</tr>
	      		<tr>
	      			<td colspan="2">上传验收单：</td>
	      			<td colspan="4"><input type="file" id="checkedPicture" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="2">上传完工照片：</td>
	      			<td colspan="4"><input type="file" id="overPicture" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="6"><input id="image2" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
      		</c:if>
        </form>
       
        
		</div>
</html>		
<script type="text/javascript">
$("#image").click(function(){
		if($("#effectPicture").val()==null||$("#effectPicture").val()==""){
			alert("请上传效果图");
			return false;
		}
		if($("#budget").val()==null||$("#budget").val()==""){
			alert("请上传装修预算");
			return false;
		}
		$("#uploadForm").submit();
})		
$("#image2").click(function(){
		if($("#checkedPicture").val()==null||$("#checkedPicture").val()==""){
			alert("请上传验收单");
			return false;
		}
		if($("#overPicture").val()==null||$("#overPicture").val()==""){
			alert("请上传完工照片");
			return false;
		}
		$("#uploadForm2").submit();
})		
</script>

