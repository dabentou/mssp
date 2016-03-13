<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-审批模板定制</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                <div class="operate-search">
                    <div class="pull-left">
                    	<input type="button"  onclick="saveStep()" class="button button-blue" value="保存模板" />
                    	<input type="hidden" id="templateId" value="${id }"/>
                    	</div>
                	<br />
                	<br />
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>状态</th>
                                <th>执行操作</th>
                                <th>操作角色</th>
                                 <th>是否自动审批</th>
                                <th>是否是第一步</th>
                                <th colspan="2">后续环节</th>
                                <th>邮件收件人</th>
                                <th>收件人邮箱</th>
                            </tr>
                        </thead>
                        <tbody id="step-tbody">
                        	<c:forEach items="${proStepMap}" var="map" varStatus="itemVs">
					     		<tr id="tr-step">
					     			<td style='display: none;' id="proStepId">${map.key.id}</td>
					     			<td>${map.key.statusValue}</td>
					     			<td id='operateStatusHtml'>${map.key.operateStatus}</td>
					     			<td>${map.key.temp.tempName}${map.key.operateStatus}</td>
					     			<td>
					     				<select id="roleId">
							              	<c:forEach items="${roleList}" var="itemRo">
												<option <c:if test="${itemRo.id==map.key.commRole.id}">selected</c:if> value="${itemRo.id }">${itemRo.roleName }</option>
											</c:forEach>
							            </select>
					     			</td>
					     			<td>
					     				<select id="isAutoApprove" >
											<option <c:if test="${map.key.isAutoApprove=='0'}">selected</c:if>  value="0">否</option>
											<option <c:if test="${map.key.isAutoApprove=='1'}">selected</c:if>  value="1">是</option>
							            </select>
					     			</td>
					     			<td>
					     				<select id="isFirst" onchange="selIsFirst(this,${map.key.id})">
											<option <c:if test="${map.key.isFirst=='0'}">selected</c:if> id='notFir' value="0">否</option>
											<option <c:if test="${map.key.isFirst=='1'}">selected</c:if> id='isFir' value="1">是</option>
							            </select>
					     			</td>
		     						<td>
										<select id="pnextId"  <c:if test="${fn:length(proStepMap)-1==itemVs.index}">style='display: none;'</c:if>>
											<option value="0">--通过--</option>
							              	<c:forEach items="${prStepPassOrReject}" var="itemPrSt">
												<option <c:if test="${itemPrSt.id==map.key.pnextId.id}">selected</c:if> value="${itemPrSt.id }">${itemPrSt.operateStatus }</option>
											</c:forEach>
							            </select>
									</td>
									<td>
										<select id="rnextId">
											<option value="0">--驳回--</option>
							              	<c:forEach items="${prStepPassOrReject}" var="itemPrSt">
												<option <c:if test="${itemPrSt.id==map.key.rnextId.id}">selected</c:if> value="${itemPrSt.id }">${itemPrSt.operateStatus }</option>
											</c:forEach>
							            </select>
									</td>
									<td>
							              <select class="select selectpicker show-tick form-control" multiple data-live-search="true" name="emailUser" id="emailUserId" onchange="addEmail(this)">
							              	<c:forEach items="${userList}" var="itemUser">
							              			<c:forEach items="${map.value}" var="item" varStatus="itemVs">
											   				<c:if test="${itemUser.id==item.id}">
														   			<option value="${itemUser.id}" selected="selected" email="${itemUser.email }"  >${itemUser.realName }</option>
														   			<c:set scope="page" var="flag" value="${itemUser.id}"/>
														   	</c:if>
											     	</c:forEach>
											     	<c:if test="${flag==''}">
															<option value="${itemUser.id}" email="${itemUser.email }" >${itemUser.realName }</option>
													</c:if>
													<c:set scope="page" var="flag" value=""/>
							            	</c:forEach>
							              </select>
					     			</td>
					     			<td id="recEmail">
						     			<c:forEach items="${map.value}" var="item" varStatus="itemVs">
								     	     ${item.realName} ${item.email}<br />
								     	</c:forEach>
					     			</td>
					     		</tr>
						    </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script>
$(window).on('load', function () {
    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });
});

$(document).ready(function(){
	//控制第一步只能设置一个 
	var firstStep=0;
	selIsFirst = function(dom,stepId){
		var now_isFirstSel = $(dom).find("option:selected").val();
		if(firstStep!=0 && now_isFirstSel==1){
			alert('第一步只能有一个！');
			$(dom).find("option[value=0]").attr("selected", true);
		}
		if(firstStep==0 && now_isFirstSel==1){
			firstStep = stepId;
		}else{
			if(firstStep==stepId && now_isFirstSel==0){
				firstStep = "";
			}
		}
	}
	
	
    //保存模板
    saveStep = function(){
		var tr = $("#step-tbody #tr-step");
		var subFlag = true;
		var jsonArr = "[";
 		tr.each(function(i){
			var proStepId = $(this).find("#proStepId").html();
			var roleId = $(this).find("#roleId option:selected").val();
			var isFirst = $(this).find("#isFirst option:selected").val();
			var isAutoApprove = $(this).find("#isAutoApprove option:selected").val();
			var pnextId = $(this).find("#pnextId option:selected").val();
			var rnextId = $(this).find("#rnextId option:selected").val();
			var emailUsers='';
			if(pnextId=='0' && tr.length-1!=i){
				alert($(this).find("#operateStatusHtml").html()+'通过后续环节不能为空！');
				$(this).find("#pnextId").focus();
				subFlag = false;
				return false;
			}
			if(rnextId=='0'){
				alert($(this).find("#operateStatusHtml").html()+'驳回后续环节不能为空！');
				$(this).find("#rnextId").focus();
				subFlag = false;
				return false;
			}
			$(this).find("#emailUserId option:selected").each(function(){
				emailUsers += $(this).val()+",";
			});
			var jsonStr = "{\"proStepId\":"+proStepId+",\"roleId\":\""+roleId+"\",\"pnextId\":"+pnextId+",\"rnextId\":"+rnextId+",\"isAutoApprove\":"+isAutoApprove+",\"isFirst\":"+isFirst+",\"emailUsers\":\""+emailUsers+"\"}";
			jsonArr+= jsonStr +",";
		});
		jsonArr=jsonArr.substring(0,jsonArr.length-1);//删除最后一个逗号 
		jsonArr+="]";
		var templateId = $("#templateId").val();
		if(subFlag){
	 		$.ajax({
				type : "POST",
				url : "${ROOT}/admin/comm/approvetemplate/detail/post",
				data : {
					id:templateId,
					templateDetail:jsonArr
				},
				dataType : "json",
				success : function(json) {
	 				if(json.status=="OK"){
						alert("保存成功！");
					}else{
						alert("保存失败！");
					} 
				}
			});
		}
    }
    
    addEmail = function(dom){
    	var emailHtml = "";
    	$(dom).find("option:selected").each(function(){
    		emailHtml += ($(this).html()+" "+$(this).attr("email")) +"<br />";
    	});
    	$(dom).parent().parent().find("#recEmail").html(emailHtml);
    }
})
</script>
</body>
</html>