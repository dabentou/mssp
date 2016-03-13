<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持-项目查看</title>
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
		input[type=checkbox] {margin-top:1px;}
		.maintable .text{ padding:2px; height: 26px; font-size: 12px;}
		.button-small { padding:0 6px; height:24px; line-height:24px; font-weight:normal; margin:2px; }
		.select {height:26px;}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content" align="center">
      <form id="myFrom" action="${ROOT }/b2c/project/flowStep" method="post">
      	<input type="hidden" name="flowType"   id="flowType"  value="3"/><!-- 审批第s三步 销服总监审批 -->
      	<input type="hidden" name="projectId" value="${b2CProject.id }" />
      	<input type="hidden" name="isPass"  id="isPass"/>
      	<table class="maintable table table-hover" border="1">
      		<tbody>
      			<tr><td colspan="6"><span class="project_title">特殊支持申请</span></td></tr>
      			<tr>
      				<td width="210">申请日期：</td>
      				<td colspan="2"> <fmt:formatDate value="${b2CProject.applytime }" /></td>
      				<td colspan="2"><span class="star">*</span>代理：</td>
      				<td width=240>${b2CProject.agent.irName }</td>
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
      				<td colspan="5"> ${b2CProject.applyReason } </td>
      			</tr>
      			<tr>
      				<td><strong>已累计审批金额：</strong></td>
      				<td colspan="5"><strong> ${applyTotalPrice } </strong></td>
      			</tr>
      		</tbody>
      	</table>
      	<c:if test="${!empty applyProductList }">
      		<table class="maintable table table-hover applytable" border="1">
	      		<tbody>
	      			<tr>
	      				<td colspan="17">特殊资源申请</td>
	      			</tr>
	      			<tr>
	      				<td width="42">No.</td>
						<td>操作人</td>
						<td><span class="star">*</span>考核条件或目标</td>
						<td><span class="star">*</span>申请项目内容</td>
						<td><span class="star">*</span>单台支持额度（元）</td>
						<td><span class="star">*</span>数量</td>
						<!-- <td><span class="star">*</span>是否以实际提货量计算</td> -->
						<td>开票价</td>
						<td>NET价</td>
						<td>财报价</td>
						<td>单台损益</td>
						<td>零售型号是否环比增长</td>
						<td>零售型号环比增长率</td>
						<td><span class="star">*</span>竞品型号</td>
						<td><span class="star">*</span>竞品价格（含税价）</td>
						<td>申请支持总金额（元）</td>
						<td>备注</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${applyProductList}" var="item" varStatus="status">
						<tr class="cnt${status.index+1}">
							<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}</td>
							<td class="operator"></td>
							<td><input type="text" class="form-control text" value="${item.conditions }" readonly="readonly" name="conditions" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td><input type="text" class="form-control text" value="${item.content }" readonly="readonly" name="content" /></td>
							<td><input type="text" class="form-control text" value="${item.applyPrice }" readonly="readonly" name="applyPrice" onChange="priceChange(this)"/></td>
							<td><input type="text" class="form-control text" value="${item.number }" readonly="readonly" name="number" onChange="numberChange(this)"/></td>
						<!-- 	<td><select name="isPicking" disabled="disabled"><option value="1">是</option><option value="0">否</option></select></td> -->
							<td>${item.poPrice }</td>
							<td>${item.netPrice }</td>
							<td>${item.financePrice }</td>
							<td>${item.syPrice }</td>
							<td><select name="isGrowth" disabled="disabled"><option value="1">是</option><option value="0">否</option></select></td>
							<td><input type="text" class="form-control text" value="${item.growthRate }" readonly="readonly" name="growthRate" /></td>
							<td><input type="text" class="form-control text" value="${item.compProduct }" readonly="readonly" name="compProduct" /></td>
							<td><input type="text" class="form-control text" value="${item.compPrice }" readonly="readonly" name="compPrice" /></td>
							<td><input type="text" class="form-control text" readonly="readonly" value="${item.applyTotalPrice }" readonly="readonly" name="applyTotalPrice" /></td>
							<td><input type="text" class="form-control text" value="${item.remark }" readonly="readonly" name="remark" /></td>
							<td><c:if test="${fn:length(item.replyLogList)==0}"><input type="button" onclick="notApprove(this,1)" class="button button-lightblue button-small" value="不同意" /></c:if></td>
						</tr>
						<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
							<tr class="cnt${status.index+1}">
								<td class="operator">${it.user.realName}</td>
								<td><input type="text" class="form-control text" value="${it.conditions }" readonly="readonly" name="conditions" /><input type="hidden" value="${item.id }" name="applyId" /></td>
								<td><input type="text" class="form-control text" value="${it.content }" readonly="readonly" name="content" /></td>
								<td><input type="text" class="form-control text" value="${it.replyPrice }" readonly="readonly" name="applyPrice" onChange="priceChange(this)"/></td>
								<td><input type="text" class="form-control text" value="${it.number }" readonly="readonly" name="number" onChange="numberChange(this)"/></td>
								<!-- <td><select name="isPicking" disabled="disabled"><option value="1">是</option><option value="0">否</option></select></td> -->
								<td>${item.poPrice }</td>
								<td>${item.netPrice }</td>
								<td>${item.financePrice }</td>
								<td>${item.syPrice }</td>
								<td><select name="isGrowth" disabled="disabled"><option value="1">是</option><option value="0">否</option></select></td>
								<td><input type="text" class="form-control text" value="${it.applyProduct.growthRate }" readonly="readonly" name="growthRate" /></td>
								<td><input type="text" class="form-control text" value="${it.applyProduct.compProduct }" readonly="readonly" name="compProduct" /></td>
								<td><input type="text" class="form-control text" value="${it.applyProduct.compPrice }" readonly="readonly" name="compPrice" /></td>
								<td><input type="text" class="form-control text" readonly="readonly" value="${it.applyTotalPrice }" name="applyTotalPrice" /></td>
								<td><input type="text" class="form-control text" value="${it.applyProduct.remark }" readonly="readonly" name="remark" /></td>
								<td>
									<c:if test="${fn:length(item.replyLogList)==(status1.index+1) && sessionScope.user.commRole.id!=it.user.commRole.id}">
										<input type="button" onclick="notApprove(this,1)" class="button button-lightblue button-small" value="不同意" />
									</c:if>
								</td>
							</tr>	
						</c:forEach>
					</c:forEach>
					<tr>
						<td>合计</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td><input type="text" name="totalNumber" class="form-control text" readonly value=${sumApplyNumber } /></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<!-- <td></td> -->
						<td></td>
						<td><input type="text" name="totalPrice" class="form-control text" readonly value=${sumApplyTotalPrice } /></td>
						<td></td>
						<td></td>
					</tr>
				</tbody>
	      	</table>
      	</c:if>
      	<c:if test="${!empty xjApplyOtherList }">
      		<table class="maintable table table-hover applytable" border="1">
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
						<td>操作</td>
					</tr>
					<c:forEach items="${xjApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt${status.index+1}">
							<td class="no">${status.index+1}</td>
							<td>
								<select name="conditionId2" class="select" disabled="disabled">
									<c:forEach items="${xjList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" readonly="readonly" name="content2" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveSug" value="${item.approveSug }" />
							</td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveRem" value="${item.approveRem }" />
							</td>
							<td>
								<c:if test="${fn:length(item.replyLogList)==0}">
									<input type="button" onclick="notApprove(this,2)" class="button button-lightblue button-small" value="不同意" />
								</c:if>
							</td>
						</tr>
<%-- 						<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
							<tr class="cnt${status.index+1}">
								<td class="operator">${it.user.realName}</td>
								<td>
									<select name="conditionId2" class="select" disabled="disabled">
										<c:forEach items="${xjList}" var="conditions">
			      						  <option value="${conditions.id }" <c:if test="${it.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
			      						</c:forEach>
									</select>
								</td>
								<td><input type="text" class="form-control text" value="${it.content }" readonly="readonly" name="content2" /><input type="hidden" value="${item.id }" name="applyId" /></td>
								<td>
									<c:if test="${fn:length(item.replyLogList)==(status1.index+1) && sessionScope.user.commRole.id!=it.user.commRole.id}">
										<input type="button" onclick="notApprove(this,2)" class="button button-lightblue button-small" value="不同意" />
									</c:if>
								</td>
							</tr>	
						</c:forEach> --%>
					</c:forEach>
				</tbody>
	      	</table>
      	</c:if>
      	<c:if test="${!empty ydApplyOtherList }">
      		<table class="maintable table table-hover applytable" border="1">
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
						<td>操作</td>
					</tr>
					<c:forEach items="${ydApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt${status.index+1}">
							<td class="no">${status.index+1}</td> 
							<td>
								<select name="conditionId3" class="select" disabled="disabled">
									<c:forEach items="${ydList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text"  value="${item.content }" readonly="readonly" name="content3" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveSug" value="${item.approveSug }" />
							</td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveRem" value="${item.approveRem }" />
							</td>
							<td>
								<c:if test="${fn:length(item.replyLogList)==0}">
									<input type="button" onclick="notApprove(this,3)" class="button button-lightblue button-small" value="不同意" />
								</c:if>
							</td>
						</tr>
<%-- 						<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
							<tr class="cnt${status.index+1}">
								<td class="operator">${it.user.realName}</td>
								<td>
									<select name="conditionId3" class="select" disabled="disabled">
										<c:forEach items="${ydList}" var="conditions">
			      						  <option value="${conditions.id }" <c:if test="${it.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
			      						</c:forEach>
									</select>
								</td>
								<td><input type="text" class="form-control text" value="${it.content }" readonly="readonly" name="content3" /><input type="hidden" value="${item.id }" name="applyId" /></td>
								<td>
									<c:if test="${fn:length(item.replyLogList)==(status1.index+1) && sessionScope.user.commRole.id!=it.user.commRole.id}">
										<input type="button" onclick="notApprove(this,3)" class="button button-lightblue button-small" value="不同意" />
									</c:if>
								</td>
							</tr>	
						</c:forEach> --%>
					</c:forEach>
				</tbody>
	      	</table>
      	</c:if>
      	<c:if test="${!empty jdApplyOtherList }">
      		<table class="maintable table table-hover applytable" border="1">
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
						<td>操作</td>
					</tr>
					<c:forEach items="${jdApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt${status.index+1}">
							<td class="no">${status.index+1}</td>
							<td>
								<select name="conditionId4" class="select" disabled="disabled">
									<c:forEach items="${jdList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" readonly="readonly" name="content4" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveSug" value="${item.approveSug }" />
							</td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveRem" value="${item.approveRem }" />
							</td>
							<td>
								<c:if test="${fn:length(item.replyLogList)==0}">
									<input type="button" onclick="notApprove(this,4)" class="button button-lightblue button-small" value="不同意" />
								</c:if>
							</td>
						</tr>
		<%-- 				<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
							<tr class="cnt${status.index+1}">
								<td class="operator">${it.user.realName}</td>
								<td>
									<select name="conditionId4" class="select" disabled="disabled">
										<c:forEach items="${jdList}" var="conditions">
			      						  <option value="${conditions.id }" <c:if test="${it.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
			      						</c:forEach>
									</select>
								</td>
								<td><input type="text" class="form-control text" value="${it.content }" readonly="readonly" name="content4" /><input type="hidden" value="${item.id }" name="applyId" /></td>
								<td>
									<c:if test="${fn:length(item.replyLogList)==(status1.index+1) && sessionScope.user.commRole.id!=it.user.commRole.id}">
										<input type="button" onclick="notApprove(this,4)" class="button button-lightblue button-small" value="不同意" />
									</c:if>
								</td>
							</tr>	
						</c:forEach> --%>
					</c:forEach>
				</tbody>
	      	</table>
      	</c:if>
      	<c:if test="${!empty netApplyOtherList }">
      		<table class="maintable table table-hover applytable" border="1">
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
						<td>操作</td>
					</tr>
					<c:forEach items="${netApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt${status.index+1}">
							<td class="no">${status.index+1}</td>
							<td>
								<select name="conditionId5" class="select" disabled="disabled">
									<c:forEach items="${netList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" readonly="readonly" name="content5" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveSug" value="${item.approveSug }" />
							</td>
							<td>
								<input type="text" class="form-control text" readonly="readonly" name="approveRem" value="${item.approveRem }" />
							</td>
							<td><c:if test="${fn:length(item.replyLogList)==0}"><input type="button" onclick="notApprove(this,5)" class="button button-lightblue button-small" value="不同意" /></c:if></td>
						</tr>
<%-- 						<c:forEach items="${item.replyLogList}" var="it" varStatus="status1">
							<tr class="cnt${status.index+1}">
								<td class="operator">${it.user.realName}</td>
								<td>
									<select name="conditionId5" class="select" disabled="disabled">
										<c:forEach items="${netList}" var="conditions">
			      						  <option value="${conditions.id }" <c:if test="${it.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
			      						</c:forEach>
									</select>
								</td>
								<td><input type="text" class="form-control text" value="${it.content }" readonly="readonly" name="content5" /><input type="hidden" value="${item.id }" name="applyId" /></td>
								<td>
									<c:if test="${fn:length(item.replyLogList)==(status1.index+1) && sessionScope.user.commRole.id!=it.user.commRole.id}">
										<input type="button" onclick="notApprove(this,5)" class="button button-lightblue button-small" value="不同意" />
									</c:if>
								</td>
							</tr>	
						</c:forEach> --%>
					</c:forEach>
				</tbody>
	      	</table>
      	</c:if>
      	<input type="submit" onclick="return getIsPass();" class="button button-lightblue" id="submitBut" value="提交">
      </form>
    </div>
  </div>
</body>
<script type="text/javascript">
function notApprove(dom,i){
	$('#submitBut').attr("disabled","disabled");
	var clone = $(dom).parents("tr[class^='cnt']").clone();
	clone.children("td.no").remove();
	clone.children("td.operator").html("");
	clone.children("td:last-child").remove();
	var className = $(dom).parents("tr[class^='cnt']").attr("class");
	if(i==1){
		clone.find("input[name='conditions']").removeAttr("readonly");
		clone.find("input[name='content']").removeAttr("readonly");
		clone.find("input[name='applyPrice']").removeAttr("readonly");
		clone.find("input[name='number']").removeAttr("readonly");
/* 		clone.find("input[name='growthRate']").removeAttr("readonly");
		clone.find("input[name='compProduct']").removeAttr("readonly");
		clone.find("input[name='compPrice']").removeAttr("readonly");
		clone.find("input[name='remark']").removeAttr("readonly"); */
		clone.find("select").removeAttr("disabled");
		clone.append('<td><input type="button" class="button button-lightblue button-small" onclick="modifypro(this)" value="保存" /></td>');
		
		var tr = "<tr class=" + className +">" + clone.html() + "</tr>";
		$(dom).parents("tr[class^='cnt']").after(tr);
	}else{
		$(dom).parent().parent().find("select").removeAttr("disabled");
		$(dom).parent().parent().find("input[type='text']").removeAttr("readonly");
		$(dom).parent().append('<input type="button" class="button button-lightblue button-small" onclick="modify(this)" value="保存" />');
	}

	var first = $(dom).parents(".applytable").find("."+className).first().find(".no");
	first.attr('rowspan',first.attr('rowspan')*1+1);
	$(dom).val("撤销").attr("onclick", "revocation(this,"+ i +")");
}
function revocation(dom,i){
	$('#submitBut').removeAttr("disabled");
	window.location.reload();
	$(dom).parents("tr").next().remove();
	var className = $(dom).parents("tr[class^='cnt']").attr("class");
	var first = $(dom).parents(".applytable").find("."+className).first().find(".no");
	first.attr('rowspan',first.attr('rowspan')*1-1);
	if(i!=1){
		$(dom).parent().parent().find("select").attr("disabled","disabled");
		$(dom).parent().parent().find("input[type='text']").attr("readonly","readonly");
		$(dom).next().remove();
		window.location.reload();
	}
	$(dom).val("不同意").attr("onclick", "notApprove(this,"+ i +")");
}
function modifypro(dom) {
	$('#submitBut').removeAttr("disabled");
	var tr = $(dom).parents("tr");
	var applyId = tr.find("input[name=applyId]").val();
	var projectId = ${b2CProject.id };
	var conditions = tr.find("input[name=conditions]").val();
	var content = tr.find("input[name=content]").val();
	var replyPrice = tr.find("input[name=applyPrice]").val();
	var applyTotalPrice = tr.find("input[name=applyTotalPrice]").val();
	var number = tr.find("input[name=number]").val();
	$.ajax({
		type : "POST",
		url : "${ROOT}/b2c/modify/product",
		data : "projectId=" + projectId +"&applyId="+applyId+"&conditions="+conditions+"&content="+content+"&replyPrice="+replyPrice+"&number="+number+"&applyTotalPrice="+applyTotalPrice,
		dataType : "json",
		success : function(json) {
			if(json.status=="OK"){
				alert("修改成功，请点击[提交]！");
				window.location.reload();
			}else{
				alert("修改失败！");
			}
		}
	});
}
function modify(dom) {
	$('#submitBut').removeAttr("disabled");
	var tr = $(dom).parent().parent();
	var applyId = tr.find("input[name=applyId]").val();
	var conditionId = tr.find("select[name^=conditionId]").val();
	var content = tr.find("input[name^=content]").val();
	var approveSug = $(dom).parent().parent().find("input[name=approveSug]").val();
	var approveRem = $(dom).parent().parent().find("input[name=approveRem]").val();
  	$.ajax({
		type : "POST",
		url : "${ROOT}/b2c/modify/other",
		data : {
			applyId : applyId,
			conditionId : conditionId,
			content : content,
			approveSug : approveSug,
			approveRem : approveRem
		},
		dataType : "json",
		success : function(json) {
			if(json.status=="OK"){
				alert("保存成功，请点击[提交]！");
				window.location.reload();
			}else{
				alert("保存失败！");
			}
		}
	});
}

function priceChange(obj){
	var price = $(obj).val();
	var number = $(obj).parent("td").siblings("td").find("input[name$=number]").val();
	$(obj).parent("td").siblings("td").find("input[name$=applyTotalPrice]").val(price*number);
	totalPrice(obj);
}
function numberChange(obj){
	var number = $(obj).val();
	var price = $(obj).parent("td").siblings("td").find("input[name$=applyPrice]").val();
	$(obj).parent("td").siblings("td").find("input[name$=applyTotalPrice]").val(price*number);
	totalNumber(obj);
	totalPrice(obj);
}
function totalNumber(obj){
	var oldNumber = $(obj).parents("tr").prev().find("input[name='number']").val();
	var numbers = $(obj).parents("table").find("input[name$=number]");
	var total_number = 0;
	for(var i=0; i<numbers.length; i++){
		total_number = total_number*1 + numbers[i].value*1;
	}
	$("input[name$=totalNumber]").val(total_number-oldNumber);
}
function totalPrice(obj){
	var oldPrice = $(obj).parents("tr").prev().find("input[name='applyTotalPrice']").val();
	var tprice = $(obj).parents("table").find("input[name$=applyTotalPrice]");
	var total_price = 0;
	for(var i=0; i<tprice.length; i++){
		total_price = total_price*1 + tprice[i].value*1;
	}
	$("input[name$=totalPrice]").val(total_price-oldPrice);
}
</script>
</html>