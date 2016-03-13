<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
    	.table td{border:1px solid #ddd;}
    	.input-mini{
			width: 70px;
		}
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
      			<td rowspan="2">项目信息</td>
      			<td> 项目名称：</td>
      			<td>${project.pName }</td>
      			<td> 项目编号：</td>
      			<td>${project.pCode }</td>
      		</tr>
      		<tr>
   <%--    			<td> 行业：</td>
      			<td>
      				${project.business.name }
      			</td> --%>
      			<td> 预计采购时间：</td>
      			<td><fmt:formatDate value="${project.purchaseTime}" pattern="yyyy-MM-dd" /></td> 
      		</tr>
      		<tr>
      			<td rowspan="3">客户信息</td>
      			<td>请选择客户:</td>
      			<td>
      				${project.customer.name}
      			</td>
      			<td>联系人:</td>
      			<td id="person">${project.customer.person}</td>
      		</tr>
      		<tr>
      			<td>手机：</td>
      			<td id="mobile">${project.customer.mobile}</td>
      			<td>电话：</td>
      			<td id="phone">${project.customer.phone}</td>
      		</tr>
      		<tr>
      			<td>邮编：</td>
      			<td id="zipcode">${project.customer.zipcode}</td>
      			<td>地址：</td>
      			<td id="address">${project.customer.address}</td>
      		</tr>
      		<tr>
      			<td rowspan="2">SI信息</td>
      			<td>SI名称:</td>
      			<td>
      				${project.commSi.irName}
      			</td>
      			<td>联系人</td>
      			<td id="siperson">${project.commSi.irName}</td>
      		</tr>
      		<tr>
      			<td>手机</td>
      			<td id="siphone">${project.commSi.phone}</td>
      			<td>邮箱</td>
      			<td id="siemail">${project.commSi.email}</td>
      		</tr>
      		<tr>
      			<td>项目背景：</td>
      			<td colspan="2">${project.projectContext}</td>
      			<td>项目重要性：</td>
      			<td colspan="2">${project.projectImportant}</td>
      		</tr>
      		<tr>
      			<td>竞争对手情况：</td>
      			<td colspan="2">${project.competeCondition}</td>
      			<td>申请理由：</td>
      			<td colspan="2">${project.applyReason}</td>
      		</tr>
      		
			<tr>
				<td colspan="6">
					<table id="pro-table" class="maintable table table-hover" border="1">
							<tr>
								<td width="48">编号</td>
								<td>产品型号</td>
								<td>累计Sell In</td>
								<td>台数(台)</td>
								<td>当月公价</td>
								<td>主要竞争品牌</td>
								<td>主要竞争品牌价格</td>
								<td>代理申请价(元)</td>
								<c:if test="${sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR}"><!-- 全国经理 -->
									<td>大区经理批复价(元)</td>
								</c:if>
								<c:if test="${sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 销售总监 -->
										<td>销售总监批复价(元)</td>
								</c:if>
								<c:if test="${type==3 }"><!-- 返利查询-->
										<td>返利</td>
								</c:if>
							</tr>
						<tbody>
							<c:forEach items = "${SAPlist }" var ="item">
								<tr>
									<td>${item.id }</td>
									<td>${item.productPrice.product.name }</td>
									<td>${item.psiSellIn }</td>
									<td>${item.applyNumber }</td>
									<td>${item.productPrice.interPublicPrice }</td>
									<td>${item.competeProduct }</td>
									<td>${item.competePrice }</td>
									<td>${item.applyPrice }</td>
									<c:if test="${sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 全国经理 -->
										<td>
											<input class="input-mini" <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=NATIONAL_MANAGER  }">readonly='readonly'</c:if> type="text" name="areaMangePrice" value="${item.areaMangePrice==null?item.applyPrice:item.areaMangePrice }" />
											<c:if test="${flowType==1 && sessionScope.user.commRole.id==NATIONAL_MANAGER}">
												<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
											</c:if>
										</td>
									</c:if>
									<c:if test="${sessionScope.user.commRole.id==SALES_DIRECTOR }"><!-- 销售总监 -->
										<td>
											<input  class="input-mini"  <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=SALES_DIRECTOR }">readonly='readonly'</c:if> type="text" name="sellMangePrice" value="${item.sellMangePrice==null?item.areaMangePrice:item.sellMangePrice }" />
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
				<td>特殊支持：</td>
      			<td colspan="5">${project.specificSupportInfo}</td>
			</tr>
			<tr>
				<td>图片：</td>
				<td colspan="5">
					<img width="330" src="${project.image }" />
				</td>
			</tr>
			<c:if test="${project.step.statusValue>=10}">
			<tr>
      			<td>签收单：</td>
      			<td colspan="5">
					<img  width="330" src="${project.signIn }" />
				</td>
      		</tr>
			<tr>
      			<td>合同：</td>
      			<td colspan="5">
					<img  width="330" src="${project.contract }" />
				</td>
      		</tr>
      		</c:if>
      	  </table>
<%--       	  <table  class="maintable table table-hover"  style="width: 90%;" border="1">
      		<tr>
      			<td colspan="5"><span class="project_title">修改记录</span></td>
      		</tr>
      		<c:forEach items="${productLogList }" var="item">
      		  <tr>
      		  	<td>修改人：${item.user.realName }</td>
      		  	<td>修改时间：<fmt:formatDate value="${item.updatetime }"  pattern="yyyy-MM-dd HH:mm:SS"/></td>
      		  	<td>型号：${item.b2CSpecialApplyProduct.product.name }</td>
      		  	<td>单价：${item.oldPrice }——>${item.targetPrice }</td>
      		  	<td>数量：${item.oldNumber }——>${item.targetNumber }</td>
      		  </tr>
      		</c:forEach>
      	  </table> --%>
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
      		<c:if test="${flowType==3 }">
	      		<tr>
	      			<td colspan="5">
	 					<input id="pass" type="button" class="button button-lightblue" value="申请" /> 
					</td>
	      		</tr>
      		</c:if>
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
	}
});
</script>
</html>