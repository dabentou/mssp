<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
    	.table td{border:1px solid #ddd;}
    </style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
      	<form id="form" class="form-horizontal"  action="${ROOT}/b2i/project/flow"  method="post" >
      	  <input type="hidden" name="isPass" />
      	  <input type="hidden" name="flowType" value="${flowType }"/>
      	  <input type="hidden" name="applyTemplateType" value="${applyTemplateType }"/><!--申请模板类型-->
      	  <input type="hidden" name="projectId" value="${project.id }" />
      	  <table class="maintable table table-hover"  style="width: 90%;">
      		<tr><td colspan="5"><span class="project_title">项目报备信息</span></td></tr>
      		<tr>
      			<td rowspan="4">项目信息</td>
      			<td> 项目名称：</td>
      			<td>${project.pName }</td>
      			<td> 项目编号：</td>
      			<td>${project.pCode }</td>
      		</tr>
       		<tr>
      			<td>代理：</td>
      			<td>${project.agent.irName}</td>
      			<td>区域：</td>
      			<td>${project.agent.province.province}</td>
      		</tr>
      		<tr>
      			<td>二级代理商：</td>
      			<td>${project.commSi.irName}</td>
      			<td>客户名称：</td>
      			<td>${project.customer.name}</td>
      		</tr>
      		<tr>
      			<td>申请人：</td>
      			<td>${project.applyPerson.realName}</td>
      			<td>申请时间：</td>
      			<td><fmt:formatDate value="${project.applyDate}" pattern="yyyy-MM-dd" /></td>
      		</tr>
      		
			<tr>
				<td colspan="6">
					<table id="pro-table" class="maintable table table-hover" border="1">
							<tr>
								<td width="48">编号</td>
								<td>产品型号</td>
								<td>可用台数</td>
								<td>台数(台)</td>
								<td>网吧公价</td>
								<td>申请价格</td>
								<!-- <td>批复价格</td> -->
								<td>支持金</td>
								<td>竞品型号</td>
								<td>竞争价格</td>
								<c:if test="${sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR}"><!-- 全国经理 -->
									<td>大区经理批复价(元)</td>
								</c:if>
								<c:if test="${sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 销售总监 -->
										<td>销售总监批复价(元)</td>
								</c:if>
								<c:if test="${type==3 }"><!-- 返利查询 -->
										<td>返利</td>
								</c:if>
							</tr>
						<tbody>
							<c:forEach items = "${SAPlist }" var ="item">
								<tr>
									<td>${item.id }</td>
									<td>${item.productPrice.product.name }</td>
									<td></td>
									<td>${item.applyNumber }</td>
									<td>${item.productPrice.interPublicPrice }</td>
									<td>${item.applyPrice }</td>
									<%-- <td>${item.replyPrice }</td> --%>
									<td>${item.supportMoney }</td>
									<td>${item.competeProduct }</td>
									<td>${item.competePrice }</td>
									<c:if test="${sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 全国经理 -->
										<td>
											<input class="input-mini" <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=NATIONAL_MANAGER }">readonly='readonly'</c:if> type="text" name="areaMangePrice" value="${item.areaMangePrice==null?item.applyPrice:item.areaMangePrice }" />
											<c:if test="${flowType==1 && sessionScope.user.commRole.id==NATIONAL_MANAGER }">
												<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
											</c:if>
										</td>
									</c:if>
									<c:if test="${sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 销售总监 -->
										<td>
											<input class="input-mini" <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=SALES_DIRECTOR}">readonly='readonly'</c:if> type="text" name="sellMangePrice" value="${item.sellMangePrice==null?item.areaMangePrice:item.sellMangePrice }"/>
											<c:if test="${flowType==1 && sessionScope.user.commRole.id==SALES_DIRECTOR}">
												<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
											</c:if>
										</td>
									</c:if>
									<c:if test="${type==3 }"><!-- 返利查询-->
										<td>${item.rebate }</td>
									</c:if>
								</tr>
								<c:if test="${type==3 }"><!-- 返利查询-->
									<tr>
										<td colspan="8">汇总</td>
										<td>${sumRabate }</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td>赛事活动方案说明：</td>
      			<td colspan="5">${project.explain}</td>
			</tr>
			<tr>
      			<td>签收单：</td>
      			<td colspan="5">
					<img src="${project.signIn }" />
				</td>
      		</tr>
			<tr>
      			<td>合同：</td>
      			<td colspan="5">
					<img src="${project.contract }" />
				</td>
      		</tr>
      	  </table>
      	  <table  class="maintable table table-hover"  style="width:90%;">
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
      		<c:if test="${isShowApprove && flowType!=3 }">
	      		<tr>
	      			<td colspan="1">
	 					<input id="pass" type="button" class="button button-lightblue" value="通过" />&nbsp;&nbsp;&nbsp;  
	      				<input id="reject" type="button" class="button button-lightblue" value="不通过" />
					</td>
	      			<td colspan="4" >
	      				<textarea class="col-md-6 form-control textarea" rows="3"  style="width: 100%;"  name="approveMsg" ></textarea>
	      			</td>
	      		</tr>
      		</c:if>
<%--       		<c:if test="${flowType==3 }">
	      		<tr>
	      			<td colspan="1">
	 					<input id="pass" type="button" class="button button-lightblue" value="申请" /> 
					</td>
	      		</tr>
      		</c:if> --%>
      	</table>
        </form>
        
              	
      	 <form id="uploadForm" action="${ROOT}/b2i/project/upload" enctype="multipart/form-data" method="post" >
       	 <input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
       	 <input type="hidden" name="applyTemplateType" value="${applyTemplateType }"/><!--申请模板类型-->
        	<c:if test="${isUploadVerificImg }">
		      	<table  class="maintable table table-hover"  style="width: 90%;" border="1">
	      		<tr>
	      			<td colspan="6"><span class="project_title">上传核销图片</span></td>
	      		</tr>
	      		<tr>
	      			<td colspan="2">签收单：</td>
	      			<td colspan="4"><input type="file" id="signIn" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="2">合同：</td>
	      			<td colspan="4"><input type="file" id="contract" name="files" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="6"><input id="saveImg" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
      	</c:if>
        </form>
    </div>
  </div>
</body>
<script type="text/javascript">
$(function(){
	$("#pass").click(function(){
		$("input[name=isPass]").val("0");
		$("#form").submit();
	});
	$("#reject").click(function(){
		$("input[name=isPass]").val("1");
		$("#form").submit();
	});
	
	$("#saveImg").click(function(){
		if($("#signIn").val()==null||$("#signIn").val()==""){
			alert("请上传签收单");
			return false;
		}
		if($("#contract").val()==null||$("#contract").val()==""){
			alert("请上传合同");
			return false;
		}
		$("#uploadForm").submit();
	});
	
	//保存批复价格
	saveReplyPrice = function(dom,applyProductId){
		var replyPrice = $(dom).prev().val();
		$.ajax({
			type : "POST",
			url : "${ROOT}/b2i/project/saveReplyPrice",
			data : {
				replyPrice : replyPrice,
				applyProductId : applyProductId,
				applyTempType : ${applyTemplateType } //第一个基础模板
			},
			dataType : "json",
			success : function(json) {
  				if(json.status=="OK"){
					alert("保存成功！");
  				}else{
					alert(json.errors[0].msg);
				} 
			}
		});
	};
});
	
</script>
</html>