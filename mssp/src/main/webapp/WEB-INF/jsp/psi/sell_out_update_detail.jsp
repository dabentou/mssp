<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>sellout修改详细</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
        <style>
    	.wrap .text,.wrap .textarea,#pro-table input[type=text]{ width:100%; }
		.input_style{
			width: 100px;
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
				$("#isPass").val("0");
				$("#sellOutUpdateForm").submit();
			});
			
			$("#reject").click(function(){
				if(valiForm()){
					$("#isPass").val("1");
					$("#sellOutUpdateForm").submit();
				}
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
      <div class="content" align="center">
      <form id="sellOutUpdateForm" class="form-horizontal"  method="post"  action="${ROOT }/psi/selloutupdate/flow">
      	<input type="hidden" id="logId" name="logId" value="${ applyLog.id}"/>
      	<input type="hidden" name="isPass"  id="isPass" />
      	<table class="maintable table table-hover" style="width: 65%;" border="1">
      		<tbody>
      			<tr><td colspan="2"><span class="project_title">Sell Out修改申请</span></td></tr>
      			<tr>
      				<td>代理商：</td>
      				<td>${applyLog.commAgent.irName }</td>
      			</tr>
      			<tr>
      				<td>型号：</td>
      				<td>${applyLog.product.name }</td>
      			</tr>
      			<tr>
      				<td>累计sellout：</td>
      				<td>${applyLog.sellOutlVolume }</td>
      			</tr>
      			<tr>
      				<td>实际sellout：</td>
      				<td>${applyLog.realSellOut }</td>
      			</tr>
      			<tr>
      				<td>selloutGAP：</td>
      				<td>${applyLog.sellOutGap }</td>
      			</tr>
      		</tbody>
      	</table>
      	<table  class="maintable table table-hover"  style="width: 65%;" border="1">
      		<tr>
      			<td colspan="5"><span class="project_title">审批记录/操作</span></td>
      		</tr>
      		<c:forEach items="${approveList }" var="item">
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
	      			<td colspan="3">
	 					<input id="pass" type="button" class="button button-lightblue" value="通过" />&nbsp;&nbsp;&nbsp;  
	      				<input id="reject" type="button" class="button button-lightblue" value="不通过" />
					</td>	
	      			<td colspan="2">
	      				<textarea rows="4" cols="50"  name="approveMsg" id="approveMsg"></textarea>
	      			</td>
	      		</tr>
      		</c:if>
      	</table>
      </form>
    </div>
    </div>
</body>
</html>