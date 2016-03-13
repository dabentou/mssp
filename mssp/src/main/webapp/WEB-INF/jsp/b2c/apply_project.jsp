<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
    	.wrap .text,.wrap .textarea,#pro-table input[type=text]{ width:100%; }
		.input_style{
			width: 100px;
		}
		.table td{
		border:1px solid #ddd
		}
		.table th{
		border:1px solid #ddd
		}
		input[type=checkbox] {margin-top:1px;}
		.maintable .text{ padding:2px; height: 26px; font-size: 12px;}
		.button-small { padding:0 6px; height:24px; line-height:24px; font-weight:normal; }
		.select {height:26px;}
		.applytable{display: none;}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content" align="center">
      <form id="form" action="${ROOT }/b2c/apply/save" method="post">
      <input type="hidden" name="projectId" value="${b2CProject.id }" />
      <input type="hidden" name="flowType"   id="flowType"  value="1"/><!-- 审批第一步大区经理改数据 -->
      	<table class="maintable table table-hover" border="1">
      		<tbody>
      			<tr><td colspan="6"><span class="project_title">特殊支持申请</span></td></tr>
      			<tr>
      				<td width="210">申请日期：</td>
      				<td colspan="2"> <fmt:formatDate value="${b2CProject==null?date:b2CProject.applytime }" /></td>
      				<td colspan="2"><span class="star">*</span>代理：</td>
      				<td width=240>
      					<select class="select" onchange="infoChange();" name="agentId" id="agentSel">
      						<c:forEach items="${agentMapList}" var="item">
      						  <option value="${item.agentId }" <c:if test="${item.agentId==b2CProject.agent.id }">selected</c:if>  cityname="${item.cityName }" projectcode="${item.projectCode }">${item.irName }</option>
      						</c:forEach>
      					</select>
      				</td>
      				<%-- <td>项目编号 ：</td>
      				<td colspan="2" style="font-weight: bold;">${projectCode }<input type="hidden" name="projectCode" value="${projectCode }" /></td> --%>
      			</tr>
      			<tr>
      				<td>项目编号：</td>
      				<td id="projectcode"></td><input type="hidden" name="projectcode" value="${b2CProject.pCode }" />
      				<td>城市：</td>
      				<td id="cityname">${b2CProject.agent.commCity.cityName }</td>
      				<td>申请人：</td>
      				<td>${b2CProject==null?user.loginName:b2CProject.user.loginName }</td>
      			</tr>
      			<tr>
      				<td><span class="star">*</span>申请主题：</td>
      				<td colspan="5"> <input type="text" class="form-control text" name="theme" value="${b2CProject.theme }"/> </td>
      			</tr>
      			<tr>
      				<td><span class="star">*</span>申请原因：</td>
      				<td colspan="5"> <textarea rows="5" class="form-control textarea" placeholder="申请原因   1：应对竞品   2：项目情况说明及所需支持" name="applyReason">${b2CProject.applyReason }</textarea> </td>
      			</tr>
      			<tr>
      				<td>选择您要申请的项目类别：</td>
      				<td colspan="5">
      					<label class="checkbox-inline"><input type="checkbox" <c:if test="${type1 }">checked</c:if> value="1" onchange="sorh(this)" name="applyType" />特殊资源申请</label>
      					<label class="checkbox-inline"><input type="checkbox" <c:if test="${type2 }">checked</c:if> value="2" onchange="sorh(this)" name="applyType" />先进达成特批</label>
      					<label class="checkbox-inline"><input type="checkbox" <c:if test="${type3 }">checked</c:if> value="3" onchange="sorh(this)" name="applyType" />月度各项考核指标特批</label>
      					<label class="checkbox-inline"><input type="checkbox" <c:if test="${type4 }">checked</c:if> value="4" onchange="sorh(this)" name="applyType" />季度各项考核指标特批</label>
      					<label class="checkbox-inline"><input type="checkbox" <c:if test="${type5 }">checked</c:if> value="5" onchange="sorh(this)" name="applyType" />NET外奖励</label>
      				</td>
      			</tr>
      		</tbody>
      	</table>
      	<table class="maintable table table-hover applytable" border="1">
      		<tbody>
      			<tr>
      				<td colspan="17">特殊资源申请</td>
      			</tr>
      			<tr>
      				<td width="42">No.</td>
					<td><span class="star">*</span>考核条件或目标</td>
					<td><span class="star">*</span>申请项目内容</td>
					<td><span class="star">*</span>单台支持额度（元）</td>
					<td><span class="star">*</span>数量</td>
					<!-- <td><span class="star">*</span>是否以实际提货量计算</td> -->
					<td>开票价</td>
					<td>NET价</td>
					<c:if test="${sessionScope.user.commRole.id!=AGENT && sessionScope.user.commRole.id!=AREA_MANAGER}"><!-- 代理商 -->
						<td>财报价</td>
						<td>单台损益</td>
					</c:if>
					<td>零售型号是否环比增长</td>
					<td>零售型号环比增长率</td>
					<td><span class="star">*</span>竞品型号</td>
					<td><span class="star">*</span>竞品价格（含税价）</td>
					<td>申请支持总金额（元）</td>
					<td>备注</td>
					<td>操作</td>
				</tr>
				
				<c:forEach items="${applyProductList}" var="item" varStatus="status">
						<tr class="cnt">
							<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}</td>
							<td><input type="text" class="form-control text" value="${item.conditions }" name="conditions" /><input type="hidden" value="${item.id }" name="applyId" /></td>
							<td><input type="text" class="form-control text" value="${item.content }" onblur="productBlur(this)" name="content" /></td>
							<td><input type="text" class="form-control text" value="${item.applyPrice }" name="applyPrice" onChange="priceChange(this)" /></td>
							<td><input type="text" class="form-control text" value="${item.number }" name="number" onChange="numberChange(this)" /></td>
							<!-- <td><select name="isPicking"><option value="1">是</option><option value="0">否</option></select></td> -->
							<td>${item.poPrice }</td>
							<td>${item.netPrice }</td>
							<c:if test="${sessionScope.user.commRole.id!=AGENT && sessionScope.user.commRole.id!=AREA_MANAGER}"><!-- 代理商 -->
								<td>${item.financePrice }</td>
								<td>${item.syPrice }</td>
							</c:if>
							<td><select name="isGrowth"><option value="1">是</option><option value="0">否</option></select></td>
							<td><input type="text" class="form-control text" value="${item.growthRate }" name="growthRate" /></td>
							<td><input type="text" class="form-control text" value="${item.compProduct }" name="compProduct" /></td>
							<td><input type="text" class="form-control text" value="${item.compPrice }" name="compPrice" /></td>
							<td><input type="text" class="form-control text" readonly="readonly" value="${item.applyTotalPrice }" name="applyTotalPrice" /></td>
							<td><input type="text" class="form-control text" value="${item.remark }" name="remark" /></td>
							<td>
								<input type="button" class="button button-lightblue button-small" onClick="deletetr(this,${item.id },1)" value="删除" />
							</td>
						</tr>
				</c:forEach>
				<c:if test="${empty applyProductList }">
					<tr class="cnt">
						<td class="no">1</td>
						<td><input type="text" class="form-control text" name="conditions" /></td>
						<td><input type="text" class="form-control text" name="content" onblur="productBlur(this)" /></td>
						<td><input type="text" class="form-control text" name="applyPrice" onChange="priceChange(this)" /></td>
						<td><input type="text" class="form-control text" name="number" onChange="numberChange(this)" /></td>
						<!-- <td><select name="isPicking"><option value="1">是</option><option value="0">否</option></select></td> -->
						<td></td>
						<td></td>
						<c:if test="${sessionScope.user.commRole.id!=AGENT && sessionScope.user.commRole.id!=AREA_MANAGER}"><!-- 代理商 -->
							<td></td>
							<td></td>
						</c:if>
						<td><select name="isGrowth"><option value="1">是</option><option value="0">否</option></select></td>
						<td><input type="text" class="form-control text" name="growthRate" /></td>
						<td><input type="text" class="form-control text" name="compProduct" /></td>
						<td><input type="text" class="form-control text" name="compPrice" /></td>
						<td><input type="text" class="form-control text" readonly="readonly" name="applyTotalPrice" /></td>
						<td><input type="text" class="form-control text" name="remark" /></td>
						<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,0,1)" value="删除" /></td>
					</tr>
				</c:if>
				<tr>
					<td>合计</td>
					<td></td>
					<td></td>
					<td></td>
					<td><input type="text" name="totalNumber" class="form-control text" readonly value=${sumApplyNumber } /></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<td></td>
					<!-- <td></td> -->
					<c:if test="${sessionScope.user.commRole.id!=AGENT && sessionScope.user.commRole.id!=AREA_MANAGER}"><!-- 代理商 -->
						<td></td>
						<td></td>
					</c:if>
					<td></td>
					<td><input type="text" name="totalPrice" class="form-control text" readonly value=${sumApplyTotalPrice } /></td>
					<td></td>
					<td></td>
				</tr>
				<tr>
      				<td colspan="17" style="text-align: left;"><input type="button" class="button button-lightblue button-small" onClick="addtr(this)" value="继续添加"/><!-- <input type="submit" class="button button-lightblue" value="保存" /> --></td>
      			</tr>
			</tbody>
      	</table>
      	<table class="maintable table table-hover applytable" border="1">
      		<tbody>
      			<tr>
      				<td colspan="17">先进达成特批</td>
      			</tr>
      			<tr>
      				<td>No.</td>
					<td><span class="star">*</span>考核条件或目标</td>
					<td><span class="star">*</span>申请项目内容</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${xjApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt">
							<td rowspan="${fn:length(item.replyLogList)+1 }" class="no">${status.index+1}<input type="hidden" value="${item.id }" name="applyId2" /></td>
							<td>
								<select name="conditionId2" class="select">
									<c:forEach items="${xjList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" name="content2" /></td>
							<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,${item.id},2)" value="删除" /></td>
						</tr>
				</c:forEach>
				<c:if test="${empty xjApplyOtherList }">
					<tr class="cnt">
						<td class="no">1</td>
						<td>
							<select name="conditionId2" class="select">
								<option value="-1" >请选择</option>
								<c:forEach items="${xjList}" var="item">
	      						  <option value="${item.id }" >${item.name }</option>
	      						</c:forEach>
							</select>
						</td>
						<td><input type="text" class="form-control text" name="content2" /></td>
						<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,0,2)" value="删除" /></td>
					</tr>
				</c:if>
				<tr>
      				<td colspan="17" style="text-align: left;"><input type="button" class="button button-lightblue button-small" onClick="addtr(this)" value="继续添加"/><!-- <input type="submit" class="button button-lightblue" value="保存" /> --></td>
      			</tr>
			</tbody>
      	</table>
      	<table class="maintable table table-hover applytable" border="1">
      		<tbody>
      			<tr>
      				<td colspan="17">月度各项考核指标特批</td>
      			</tr>
      			<tr>
      				<td>No.</td>
					<td><span class="star">*</span>考核条件或目标</td>
					<td><span class="star">*</span>申请项目内容</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${ydApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt">
							<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}<input type="hidden" value="${item.id }" name="applyId3" /></td>
							<td>
								<select name="conditionId3" class="select">
									<c:forEach items="${ydList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" name="content3" /></td>
							<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,${item.id},3)" value="删除" /></td>
						</tr>
				</c:forEach>
				<c:if test="${empty ydApplyOtherList }">
					<tr class="cnt">
						<td class="no">1</td>
						<td>
							<select name="conditionId3" class="select">
								<option value="-1" >请选择</option>
								<c:forEach items="${ydList}" var="item">
	      						  <option value="${item.id }" >${item.name }</option>
	      						</c:forEach>
							</select>
						</td>
						<td><input type="text" class="form-control text" name="content3" /></td>
						<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,0,3)" value="删除" /></td>
					</tr>
				</c:if>
				<tr>
      				<td colspan="17" style="text-align: left;"><input type="button" class="button button-lightblue button-small" onClick="addtr(this)" value="继续添加"/><!-- <input type="submit" class="button button-lightblue" value="保存" /> --></td>
      			</tr>
			</tbody>
      	</table>
      	<table class="maintable table table-hover applytable" border="1">
      		<tbody>
      			<tr>
      				<td colspan="17">季度各项考核指标特批</td>
      			</tr>
      			<tr>
      				<td>No.</td>
					<td><span class="star">*</span>考核条件或目标</td>
					<td><span class="star">*</span>申请项目内容</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${jdApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt">
							<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}<input type="hidden" value="${item.id }" name="applyId4" /></td>
							<td>
								<select name="conditionId4" class="select">
									<c:forEach items="${jdList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" name="content4" /></td>
							<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,${item.id},4)" value="删除" /></td>
						</tr>
				</c:forEach>
				<c:if test="${empty jdApplyOtherList }">
					<tr class="cnt">
						<td class="no">1</td>
						<td>
							<select name="conditionId4" class="select">
								<option value="-1" >请选择</option>
								<c:forEach items="${jdList}" var="item">
	      						  <option value="${item.id }" >${item.name }</option>
	      						</c:forEach>
							</select>
						</td>
						<td><input type="text" class="form-control text" name="content4" /></td>
						<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,0,4)" value="删除" /></td>
					</tr>
				</c:if>
				<tr>
      				<td colspan="17" style="text-align: left;"><input type="button" class="button button-lightblue button-small" onClick="addtr(this)" value="继续添加"/><!-- <input type="submit" class="button button-lightblue" value="保存" /> --></td>
      			</tr>
			</tbody>
      	</table>
      	<table class="maintable table table-hover applytable" border="1">
      		<tbody>
      			<tr>
      				<td colspan="17">NET外奖励特批</td>
      			</tr>
      			<tr>
      				<td>No.</td>
					<td><span class="star">*</span>考核条件或目标</td>
					<td><span class="star">*</span>申请项目内容</td>
					<td>操作</td>
				</tr>
				<c:forEach items="${netApplyOtherList}" var="item" varStatus="status">
						<tr class="cnt">
							<td class="no" rowspan="${fn:length(item.replyLogList)+1 }">${status.index+1}<input type="hidden" value="${item.id }" name="applyId5" /></td>
							<td>
								<select name="conditionId5" class="select">
									<c:forEach items="${netList}" var="conditions">
		      						  <option value="${conditions.id }" <c:if test="${item.condition.id==conditions.id }">selected</c:if> >${conditions.name }</option>
		      						</c:forEach>
								</select>
							</td>
							<td><input type="text" class="form-control text" value="${item.content }" name="content5" /></td>
							<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,${item.id},5)" value="删除" /></td>
						</tr>
				</c:forEach>
				<c:if test="${empty netApplyOtherList }">
					<tr class="cnt">
						<td class="no">1</td>
						<td>
							<select name="conditionId5" class="select">
								<option value="-1" >请选择</option>
								<c:forEach items="${netList}" var="item">
	      						  <option value="${item.id }" >${item.name }</option>
	      						</c:forEach>
							</select>
						</td>
						<td><input type="text" class="form-control text" name="content5" /></td>
						<td><input type="button" class="button button-lightblue button-small" onClick="deletetr(this,0,5)" value="删除" /></td>
					</tr>
				</c:if>
				<tr>
      				<td colspan="17" style="text-align: left;"><input type="button" class="button button-lightblue button-small" onClick="addtr(this)" value="继续添加"/><!-- <input type="submit" class="button button-lightblue" value="保存" /> --></td>
      			</tr>
			</tbody>
      	</table>
      	<input type="submit" class="button button-lightblue" value="确认提交"/>
      </form>
    </div>
  </div>
</body>
<link rel="stylesheet" href="${ROOT}/css/jquery-ui.css" />
<script type="text/javascript" src="${ROOT}/js/jquery-ui.js"></script>
<script type="text/javascript">
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});
var availableTags = [${product}];
$("input[name^=content]").autocomplete({
    source: availableTags
});
$(document).ready(function(){
	$('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
	$("#form").submit(function(){
		if($("select[name$=agentId] option:selected").val()==-1){
    		alert("请选择代理商！");
    		$("select[name$=agentId]").focus();
			return false;
    	}
		if($("input[name$=theme]").val()==""){
			alert("申请主题不能为空！");
			return false;
		}
		if($("textarea[name$=applyReason]").val()==""){
			alert("申请原因不能为空！");
			return false;
		}
		var applyType = $("input[name=applyType]:checked");
		if(applyType.length==0){
			alert("至少选择一种申请类型！");
			return false;
		}
		for(var i=0; i<applyType.length; i++){
			if(applyType[i].value==1){
				if(!errors("conditions")){
					alert("考核条件或目标不能为空！");
					return false;
				}
				if(!errors("content")){
					alert("申请项目内容不能为空！");
					return false;
				}
				if(!errors("applyPrice")){
					alert("申请单价不能为空！");
					return false;
				}
				if(!errors("number")){
					alert("申请数量不能为空！");
					return false;
				}
				if(!errors("compProduct")){
					alert("竞品型号不能为空！");
					return false;
				}
				if(!errors("compPrice")){
					alert("竞品价格不能为空！");
					return false;
				}
			}else if(applyType[i].value==2){
				if($("select[name=conditionId2] option:selected").val()==-1){
		    		alert("请选择考核条件或目标！");
		    		$("select[name=conditionId2]").focus();
					return false;
		    	}
				if(!errors("content2")){
					alert("申请项目内容不能为空！");
					return false;
				}
			}else if(applyType[i].value==3){
				if($("select[name=conditionId3] option:selected").val()==-1){
		    		alert("请选择考核条件或目标！");
		    		$("select[name=conditionId3]").focus();
					return false;
		    	}
				if(!errors("content3")){
					alert("申请项目内容不能为空！");
					return false;
				}
			}else if(applyType[i].value==4){
				if($("select[name=conditionId4] option:selected").val()==-1){
		    		alert("请选择考核条件或目标！");
		    		$("select[name=conditionId4]").focus();
					return false;
		    	}
				if(!errors("content4")){
					alert("申请项目内容不能为空！");
					return false;
				}
			}else if(applyType[i].value==5){
				if($("select[name=conditionId5] option:selected").val()==-1){
		    		alert("请选择考核条件或目标！");
		    		$("select[name=conditionId5]").focus();
					return false;
		    	}
				if(!errors("content5")){
					alert("申请项目内容不能为空！");
					return false;
				}
			}
		}
	}); 
	
	infoChange();
	showTable();
});
//5种申请类型的显示和隐藏
function sorh(obj){
	if($(obj).is(":checked")){
		$(".applytable")[$(obj).val()-1].style.display="table";
	}else{
		$(".applytable")[$(obj).val()-1].style.display="none";
	}
}

function showTable(){
	$('input[name="applyType"]:checked').each(function(){
		var value = $(this).val();
		$(".applytable")[value-1].style.display="table";
    });
	
}
//添加行
function addtr(obj){
	var $lastcnt = $(obj).parents("tbody").find(".cnt:last");
	var tr = "<tr class='cnt'>" +$lastcnt.html() + "</tr>";
	$lastcnt.after(tr);
	var nowTr = $(obj).parents("tbody").find(".cnt:last");
	nowTr.find("input[name^=applyId]").val(0);
	nowTr.find("input[name^=content]").val("");
	$(obj).parents("tbody").find(".no").each(function(i){
		$(this).text(i+1);
	});
	$(obj).parents("tbody").find(".cnt:last").find("td:eq(5)").html("");
	$(obj).parents("tbody").find(".cnt:last").find("td:eq(6)").html("");
	$("input[name$=content]").autocomplete({
	    source: availableTags
	});
}
//删除行
function deletetr(obj,id,type){
	var $tbody = $(obj).parents("tbody");
	var id = $(obj).parent().parent().find("input[name^='applyId']").val();
	if(id==undefined){
		id = 0;
	}
	if($tbody.find(".cnt").length > 1){
		$(obj).parents(".cnt").remove();
		$tbody.find(".no").each(function(i){
			$(this).text(i+1);
		});
		totalNumber($tbody);
		totalPrice($tbody);
 		if(id!=0){
			$.ajax({
				type : "POST",
				url : "${ROOT}/b2c/project/deleteApplyProduct",
				data : {
					id : id,
					type : type
				},
				dataType : "json",
				success : function(data) {
					window.location.reload();
				}
			});
		}
	}else{
		alert("最后一条不能删除！");
	}
}

//只能是汉字
function isFullChar(str){
	   var reg = /^[\u4e00-\u9fa5]+$/;
	   return reg.test(str);
} 

//字母和数字
function isLetterAndNum(str){
	   var reg = /(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{2,})$/;
	   return reg.test(str);
} 
//获取产品价格
 function productBlur(obj){
	var productName = $(obj).val();
	if(productName==''){
		alert("型号名称不能为空！");
		$(obj).focus();
		return;
	}
 	if(!isFullChar(productName) && !isLetterAndNum(productName)){
		alert("只能输入纯文字或选择具体型号");
		$(obj).focus();
		return;
	}
	$(obj).parent().parent().find("td:eq(5)").html("");
	$(obj).parent().parent().find("td:eq(6)").html("")
	$.ajax({
		type : "POST",
		url : "${ROOT}/b2c/project/getProductPrice",
		data : "productName=" + productName,
		dataType : "json",
		success : function(data) {
			if(data.status=="OK"){
				$(obj).parent().parent().find("td:eq(5)").html(data.data.poPrice);
				$(obj).parent().parent().find("td:eq(6)").html(data.data.netPrice)
			}
		}
	});
} 
function infoChange(){
	var attr = $("#agentSel").find("option:selected").get(0).attributes;
	for(var i=1; i<attr.length; i++){
		$("#"+attr[i].name).text(attr[i].value);
		$("input[name$="+ attr[i].name +"]").val(attr[i].value);
	}
}
function errors(name){
	var error = 0;
	$("input[name=" + name +"]").each(function(){
		if(this.value==""){
			error++;
			return false;
		}
	});
	if(error==0){
		return true;
	}
}
function priceChange(obj){
	var price = $(obj).val();
	var number = $(obj).parent("td").siblings("td").find("input[name$=number]").val();
	$(obj).parent("td").siblings("td").find("input[name$=applyTotalPrice]").val(price*number);
	totalPrice();
}
function numberChange(obj){
	var number = $(obj).val();
	var price = $(obj).parent("td").siblings("td").find("input[name$=applyPrice]").val();
	$(obj).parent("td").siblings("td").find("input[name$=applyTotalPrice]").val(price*number);
	totalNumber(obj);
	totalPrice(obj);
}
function totalNumber(obj){
	var numbers = $(obj).parents("table").find("input[name$=number]");
	var total_number = 0;
	for(var i=0; i<numbers.length; i++){
		total_number = total_number*1 + numbers[i].value*1;
	}
	$("input[name$=totalNumber]").val(total_number);
}
function totalPrice(obj){
	var tprice = $(obj).parents("table").find("input[name$=applyTotalPrice]");
	var total_price = 0;
	for(var i=0; i<tprice.length; i++){
		total_price = total_price*1 + tprice[i].value*1;
	}
	$("input[name$=totalPrice]").val(total_price);
}
</script>
</html>