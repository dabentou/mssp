<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="ms" tagdir="/WEB-INF/tags"%>
<div class="wrap">
  <div class="content" align="center">
    <form id="myFrom" action="#" method="post" style="width: 50%">
    	<table class="maintable table table-hover" >
    		<tbody>
    			<tr><td colspan="6"><span class="project_title">特殊支持申请</span></td></tr>
    			<tr>
    				<td width="210">申请日期：</td>
    				<td colspan="2"> <fmt:formatDate value="${b2CProject.applytime }" /></td>
    				<td colspan="2"><span class="star">*</span>代理：</td>
    				<td width="240">${b2CProject.agent.irName }</td>
    			</tr>
    			<tr>
    				<td>项目编号：</td>
    				<td>${b2CProject.pCode }</td>
    				<td>城市：</td>
    				<td>${b2CProject.agent.commCity.cityName }</td>
    				<td>申请人：</td>
    				<td>${b2CProject.user.loginName }</td>
    			</tr>
    			<tr>
    				<td><span class="star">*</span>申请主题：</td>
    				<td colspan="5"> ${b2CProject.theme }</td>
    			</tr>
    			<tr>
    				<td><span class="star">*</span>申请原因：</td>
    				<td colspan="5"> ${b2CProject.applyReason }</td>
    			</tr>
    			<c:if test="${sessionScope.user.commRole.id==XIAOSHOUZONGJINGLI }">
     			<tr>
     				<td>已累计审批金额：</td>
     				<td colspan="5"> ${applyTotalPrice } </td>
     			</tr>
    			</c:if>
    		</tbody>
    	</table>
    	<c:if test="${!empty applyProductList }">
    		<table class="maintable table table-hover" style="width: 30%">
     		<tbody>
     			<tr>
     				<td colspan="17">特殊资源申请</td>
     			</tr>
     			<tr>
     			<td>No.</td>
				<td>操作人</td>
				<td>考核条件或目标</td>
				<td>申请项目内容</td>
				<td>单台支持额度（元）</td>
				<td>数量</td>
				<td>开票价</td>
				<td>NET价</td>
				<c:if test="${sessionScope.user.commRole.id!=AGENT}">
					<td>财报价</td>
					<td>单台损益</td>
				</c:if>
				<td>@零售型号是否环比增长</td>
				<td>零售型号环比增长率</td>
				<td>竞品型号</td>
				<td>竞品价格（含税价）</td>
				<td>申请支持总金额（元）</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${applyProductList}" var="item" varStatus="status">
				<tr class="cnt${status.index+1}">
					<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}</td>
					<td></td>
					<td>${item.conditions }</td>
					<td>${item.content }</td>
					<td>${item.applyPrice }</td>
					<td>${item.number }</td>
					<td>${item.poPrice }</td>
					<td>${item.netPrice }</td>
					<c:if test="${sessionScope.user.commRole.id!=AGENT}">
						<td>${item.financePrice }</td>
						<td>${item.syPrice }</td>
					</c:if>
					<td>${item.isGrowth==1?'是':'否' }</td>
					<td>${item.growthRate }</td>
					<td>${item.compProduct }</td>
					<td>${item.compPrice }</td>
					<td>${item.applyTotalPrice }</td>
					<td>${item.remark }</td>
				</tr>
				<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
					<tr class="cnt${status.index+1}">
						<td>${it.user.realName }</td>
						<td>${it.conditions }</td>
						<td>${it.content }</td>
						<td>${it.replyPrice }</td>
						<td>${it.number }</td>
						<td>${item.poPrice }</td>
						<td>${item.netPrice }</td>
						<c:if test="${sessionScope.user.commRole.id!=AGENT}">
							<td>${item.financePrice }</td>
							<td>${item.syPrice }</td>
						</c:if>
						<td>${item.isGrowth==1?'是':'否' }</td>
						<td>${item.growthRate }</td>
						<td>${item.compProduct }</td>
						<td>${item.compPrice }</td>
						<td>${it.applyTotalPrice }</td>
						<td>${item.remark }</td>
					</tr>
				</c:forEach>
			</c:forEach>
			<tr>
				<td>合计</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td><input type="text" name="totalNumber" class="form-control text" readonly="readonly" value="${sumApplyNumber }" /></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<c:if test="${sessionScope.user.commRole.id!=AGENT && sessionScope.user.commRole.id!=AREA_MANAGER}">
					<td></td>
					<td></td>
				</c:if>
				<td></td>
				<td><input type="text" name="totalPrice" class="form-control text" readonly="readonly" value="${sumApplyTotalPrice }" /></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
     	</table>
    	</c:if>
    	<c:if test="${!empty xjApplyOtherList }">
    		<table class="maintable table table-hover applytable">
     		<tbody>
     			<tr>
     				<td colspan="17" class="project_title">先进达成特批</td>
     			</tr>
     			<tr>
     				<td>No.</td>
				<td><span class="star">*</span>考核条件或目标</td>
				<td><span class="star">*</span>申请项目内容</td>
				<td>批复意见</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${xjApplyOtherList}" var="item" varStatus="status">
				<tr class="cnt${status.index+1}">
					<td class="no">${status.index+1}<input type="hidden" value="${item.id }" name="applyId2" /></td>
					<td>${item.condition.name }</td>
					<td>${item.content }</td>
					<td>${item.approveSug }</td>
					<td>${item.approveRem }</td>
				</tr>
			</c:forEach>
		</tbody>
     	</table>
    	</c:if>
    	<c:if test="${!empty ydApplyOtherList }">
    		<table class="maintable table table-hover applytable">
     		<tbody>
     			<tr>
     				<td colspan="17" class="project_title">月度各项考核指标特批</td>
     			</tr>
     			<tr>
     				<td>No.</td>
				<td><span class="star">*</span>考核条件或目标</td>
				<td><span class="star">*</span>申请项目内容</td>
				<td>批复意见</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${ydApplyOtherList}" var="item" varStatus="status">
				<tr class="cnt${status.index+1}">
					<td class="no">${status.index+1}<input type="hidden" value="${item.id }" name="applyId3" /></td>
					<td>${item.condition.name }</td>
					<td>${item.content }</td>
					<td>${item.approveSug }</td>
					<td>${item.approveRem }</td>
				</tr>
			</c:forEach>
		</tbody>
     	</table>
    	</c:if>
    	<c:if test="${!empty jdApplyOtherList }">
    		<table class="maintable table table-hover applytable">
     		<tbody>
     			<tr>
     				<td colspan="17" class="project_title">季度各项考核指标特批</td>
     			</tr>
     			<tr>
     				<td>No.</td>
				<td><span class="star">*</span>考核条件或目标</td>
				<td><span class="star">*</span>申请项目内容</td>
				<td>批复意见</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${jdApplyOtherList}" var="item" varStatus="status">
				<tr class="cnt${status.index+1}">
					<td class="no">${status.index+1} <input type="hidden" value="${item.id }" name="applyId4" /></td>
					<td>${item.condition.name }</td>
					<td>${item.content }</td>
					<td>${item.approveSug }</td>
					<td>${item.approveRem }</td>
				</tr>
			</c:forEach>
		</tbody>
     	</table>
    	</c:if>
    	<c:if test="${!empty netApplyOtherList }">
    		<table class="maintable table table-hover applytable">
     		<tbody>
     			<tr>
     				<td colspan="17" class="project_title">NET外奖励特批</td>
     			</tr>
     			<tr>
     				<td>No.</td>
				<td><span class="star">*</span>考核条件或目标</td>
				<td><span class="star">*</span>申请项目内容</td>
				<td>批复意见</td>
				<td>备注</td>
			</tr>
			<c:forEach items="${netApplyOtherList}" var="item" varStatus="status">
				<tr class="cnt${status.index+1}">
					<td class="no">${status.index+1}<input type="hidden" value="${item.id }" name="applyId5" /></td>
					<td>${item.condition.name }</td>
					<td>${item.content }</td>
					<td>${item.approveSug }</td>
					<td>${item.approveRem }</td>
				</tr>
			</c:forEach>
		</tbody>
     	</table>
    	</c:if>
    	
    	
    	 <table  class="maintable table table-hover" >
    		<tr>
    			<td colspan="6"><span class="project_title">审批记录/操作</span></td>
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
    	</table>
    </form>
  </div>
</div>