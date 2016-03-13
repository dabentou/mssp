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
      			<td> 行业：</td>
      			<td>
      				${project.business.name }
      			</td>
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
				<td>会议支持金：</td>
      			<td colspan="5">${project.supportMoney}</td>
			</tr>
			<tr>
      			<td>酒店门头照：</td>
      			<td colspan="5">
					<img  width="330" src="${project.door }" />
				</td>
      		</tr>
			<tr>
      			<td>参会人员照：</td>
      			<td colspan="5">
					<img  width="330" src="${project.persons }" />
				</td>
      		</tr>
			<tr>
      			<td>机器或支持金明细照：</td>
      			<td colspan="5">
					<img  width="330" src="${project.machineMoney }" />
				</td>
      		</tr>
			<tr>
      			<td>背景或条幅：</td>
      			<td colspan="5">
					<img  width="330" src="${project.bgImg }" />
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
	      			<td colspan="2">酒店门头照：</td>
	      			<td colspan="4"><input type="file" id="door" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="2">参会人员照：</td>
	      			<td colspan="4"><input type="file" id="persons" name="files" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">机器或支持金明细照：</td>
	      			<td colspan="4"><input type="file" id="machineMoney" name="files" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">背景或条幅：</td>
	      			<td colspan="4"><input type="file" id="bgImg" name="files" /></td>
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
		if($("#door").val()==null||$("#door").val()==""){
			alert("请上传酒店门头照！");
			return false;
		}
		if($("#persons").val()==null||$("#persons").val()==""){
			alert("请上传参会人员照！");
			return false;
		}
		if($("#machineMoney").val()==null||$("#machineMoney").val()==""){
			alert("请上传机器或支持金明细照！");
			return false;
		}
		if($("#bgImg").val()==null||$("#bgImg").val()==""){
			alert("请上传背景或条幅照！");
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