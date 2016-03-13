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
		 <%@include file="/WEB-INF/include/iretail_menu.jsp" %>
		 	<form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
		 		<input type="hidden" name="isPass"   id="isPass" />
	      		<input type="hidden" name="flowType"   id="flowType"  value="${flowType }"/>
	      		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
		 		<table class="maintable table table-hover"  style="width: 85%;" border="1">
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
      	<c:if test="${check }">
       		<table  class="maintable table table-hover"  style="width: 90%;" border="1">
       			<tr>
	      			<td colspan="6"><span class="project_title">核销资料</span></td>
	      		</tr>
       		<tr>
	      			<td colspan="2">核销照片：</td>
	      			<td colspan="4"><img src ="${image.picture}" width="330"></img></td>
	      	</tr> 
	      </table>
       </c:if>
      	
      	<table  class="maintable table table-hover"  style="width:85%;">
      		<tr>
      			<td colspan="5"><span class="project_title">申请审批记录</span></td>
      		</tr>
				<c:forEach items="${logList }" var="item"  varStatus="status">
				<c:if test="${status.index==0 }">
					<td>申请人：${item.user.realName }</td>
					<td>申请时间：<fmt:formatDate value="${item.approveTime }"
							pattern="yyyy-MM-dd HH:mm:SS" />
					</td>
					<td></td>
					<td></td>
					<td></td>
				</c:if>
      			<c:if test="${item.step.statusValue < 10}">
	      		<tr>
	      			<td>审核人：${item.user.realName }</td>
	      			<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
	      			<td>步骤：${item.step.operateStatus }</td>
	      			<td>是否通过：${item.isPass==0?'通过':'不通过' }</td>
	      			<td>审批备注：${item.approveMsg }</td>
	      		</tr>
	      		</c:if>
      		</c:forEach>
      		
      		
      		<tr>
      			<td colspan="5"><span class="project_title">核销审批记录</span></td>
      		</tr>
      		<c:forEach items="${logList }" var="item">
      		<c:if test="${item.step.statusValue >= 10}">
	      		<tr>
								<c:choose>
									<c:when test="${item.step.statusValue == 10}">
										<option value="${state.stateAbbr}" selected="selected">
											<td>审请人：${project.applyUser.irName }</td>
											<td>上传资料时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
										</option>
									</c:when>
									<c:otherwise>
										<option value="${state.stateAbbr}">
											<td>审核人：${item.user.realName }</td>
											<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
										</option>
									</c:otherwise>
								</c:choose>
	      			<td>步骤：${item.step.operateStatus }</td>
	      			<td>是否通过：${item.isPass==0?'通过':'不通过' }</td>
	      			<td>审批备注：${item.approveMsg }</td>
	      		</tr>
	      	</c:if>
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
	      			<td colspan="2">上传图片：</td>
	      			<td colspan="4"><input type="file" id="signIn" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="6"><input id="image" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
      	</c:if>
        </form>
        
		</div>
</html>		
<script type="text/javascript">
$("#image").click(function(){
		if($("#signIn").val()==null||$("#signIn").val()==""){
			alert("请上传照片");
			return false;
		}
		$("#uploadForm").submit();
})		
</script>
