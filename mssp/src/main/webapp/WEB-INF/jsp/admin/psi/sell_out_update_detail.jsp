<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-sellOut修改详细</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="${ROOT}/admin/psi/selloutupdate/list.html">sellout修改</a></li>
        <li>详细</li>
      </ol>
      <div>
        <form class="form-horizontal" method="post">
	      <table class="table table-hover mytable" style="width: 50%;">
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
	      	<table  class="table table-hover mytable"  style="width: 50%;">
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
	      	
	      	<table  class="table table-hover mytable"  style="width: 50%;">
	      		<tr>
	      			<td colspan="5"><span class="project_title">管理员修改记录</span></td>
	      		</tr>
	      		<c:forEach items="${adminLog }" var="item">
		      		<tr>
 		      			<td>修改人：${item.commUser.realName }</td>
		      			<td>修改前的值：${item.psiSellOutUpdateApplyLog.sellOutlVolume }</td>
		      			<td>修改后的值：${item.changeVolume }</td>
		      			<td>修改时间：<fmt:formatDate value="${item.createtime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
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
</div>
</div>
</body>
</html>