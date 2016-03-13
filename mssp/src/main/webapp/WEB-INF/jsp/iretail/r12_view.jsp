<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>门店零售奖励申请查看</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
   <style>
		.input_style{
			width: 160px;
			text-align:center;
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
     	<%@include file="/WEB-INF/include/iretail_menu.jsp" %>
    
    <div class="content"  align="center">
      	<form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
		 		<input type="hidden" name="isPass"   id="isPass" />
	      		<input type="hidden" name="flowType"   id="flowType"  value="${flowType }"/>
	      		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
      		 <input  type="hidden"  class="input_style" name="type" value="R06" />
      		 <table class="maintable table table-hover"  style="width: 85%;">
      		 <tr><td colspan="5"><span class="project_title">门店零售奖励申请查看</span></td></tr>
    		 <tr>
    		 		 <td rowspan="6">基本信息</td>
           			 <td >申请编号：</td>
     	 			 <td >${project.ppn }</td>
     	 			 <td>申请代理商：</td>
     	 			 <td>${project.applyUser.irName }</td>
     		 </tr>
     		 <tr>
           			 <td> 所属省份：</td>
      				 <td>${project.province.province }</td> 
           			 <td>申请日期：</td>
     	 			 <td><fmt:formatDate value="${project.beginTime }" pattern="yyyy-MM-dd" /></td>
     		 </tr>
     		 <tr>
           			 <td>申请主题：</td>
     	 			 <td>${project.applyTheme }</td>
           			 <td>申请费用：</td>
     	 			 <td>${project.applyPrice }</td>
     		 </tr>
     		 <tr>
           			 <td>有效期：</td>
     	 			 <td><fmt:formatDate value="${project.beginTime }" pattern="yyyy-MM-dd" /></td>
     	 			 <td >至</td>
     	 			<td><fmt:formatDate value="${project.endTime }" pattern="yyyy-MM-dd" /></td>
     		 </tr>
     		 <tr>
           			 <td>供应商：</td>
     	 			 <td>${project.store.irName }</td>
     	 			 <td></td><td></td>
     		 </tr>
     		 
     		 <tr>
     		 	<td>邮件特批：</td>
     		 	<td colspan="3">
     		 		<%-- <img width="330" src="${project.approveEmail}"><br> --%>
     		 		<c:forEach items="${fn:split(project.approveEmail,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
					</c:forEach>
     		 </tr>
     		  <tr>
     		 	<td>情况说明：</td>
     		 	<td colspan="4">
     		 		${project.remark }</td>
     		 </tr>
     		 <tr>
     		 	<td>申请内容：
     		 	</td>
     		 	<td colspan="4">
     		 	<div class="download">
     		 	<c:forEach var="url" items="${applyCont }">
     					<a href="${url }" class="thumbnail"></a>
				</c:forEach>
     		 	</div>
     		 	</td>
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
					<td>申请人：${project.applyUser.irName }</td>
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
											<td>申请人：${project.applyUser.irName }</td>
											<td>上传资料时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
									</c:when>
									<c:otherwise>
											<td>审核人：${item.user.realName }</td>
											<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
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
  	</div>
</body>
</html>
<script type="text/javascript">

function uploadPhoto (dom){
	var ids=$(dom).attr("id");
	var purl=$(dom).siblings("input[name=photoUrl]");
	var pon=$(dom).siblings("div[name=photoPosition]");
	$.ajaxFileUpload({
		type : "POST",
		url : "${ROOT}/editor/image/upload",
		secureuri:false,  
	    fileElementId:ids,//文件选择框的id属性 
		dataType : "json",
		success : function(json){
			if(json.error==0){
				var urls ="http://" + window.location.host+json.url;
				pon.append("<img title='重新上传可追加' width=300 src='" + urls + "'> ");
				purl.val(urls); 
			}else{
				alert(json.error);
			}
		} 
	});
}

function uploadPhotos (dom){
	var ids=$(dom).attr("id");
	var purl=$(dom).siblings("input[name=markUrl]");
	var pon=$(dom).siblings("div[name=markPosition]");
	$.ajaxFileUpload({
		type : "POST",
		url : "${ROOT}/editor/image/upload",
		secureuri:false,  
	    fileElementId:ids,//文件选择框的id属性 
		dataType : "json",
		success : function(json){
			if(json.error==0){
				var urls ="http://" + window.location.host+json.url;
				pon.append("<img title='重新上传可追加' width=300 src='" + urls + "'> ");
				purl.val(urls); 
			}else{
				alert(json.error);
			}
		} 
	});
}
	
$("#add").click(function(){
var html =  '<tr><td>促销员姓名:</td><td><input  type="text"  class="input_style" name="applyName" /></td></tr>'+
            '<tr><td>身份证号码:</td><td><input  type="text"  class="input_style" name="applyIDCardNumber" /></td></tr>'+
			'<tr><td>联系手机:</td><td><input  type="text"  class="input_style" name="applyPhone" /></td></tr>'+
			'<tr><td>基本工资:</td><td>￥<input  type="text"  class="input_style" name="basePay" /></td></tr>'+
			'<tr><td>奖金:</td><td>￥<input  type="text"  class="input_style" name="bonus" /></td></tr>'+
			'<tr><td>工资总计:</td><td>￥<input  type="text"  class="input_style" name="totalSalary" /></td></tr>'+
			'<tr><td>情况说明:</td><td><textarea style="width:300px;height:80px;" name="remark"></textarea></td></tr>'+
			'<tr><td>负责零售店名称:</td><td><input  type="text"  class="input_style" name="retailName" /></td></tr>'+
			'<tr><td>月产品总销量:</td><td><input  type="text"  class="input_style" name="monthProductSale" placeholder = "0"/>&nbsp;台</td></tr>'+
			'<tr><td>月零售产品总销量:</td><td><input  type="text"  class="input_style" name="monthRetailSale"   placeholder = "0"/>&nbsp;台</td></tr>'+
			'<tr><td>月零售产品占比:</td><td>%&nbsp;<input  type="text"  class="input_style" name="retailProductPercent" /></td></tr>'+
			'<tr><td>促销员照片:</td><td colspan="2">'+
			'<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="salerPhoto1" accept="image/gif,image/jpeg,image/png"/>'+
			'<textarea style="width:300px;height:35px;" name="salerPhotoContent">备注</textarea>'+
			'<input type="hidden" name="photoUrl" /><div  name="photoPosition"></div></td></tr>'+
			'<tr><td>促销员打分表:</td>'+
			'<td><input type="file" id="salerMark1" onchange="uploadPhotos(this)"  accept="image/gif,image/jpeg,image/png" class="input_style" name="imgFile"/>'+
			'<textarea style="width:300px;height:35px;" id="salerMarkContent" name="salerMarkContent">备注</textarea>'+
			'<input type="hidden" name="markUrl"  />   <div name="markPosition"  ></div></td></tr>';
	$("#tables").append(html);
})


</script>
<script type="text/javascript">
$("#image").click(function(){
		if($("#signIn").val()==null||$("#signIn").val()==""){
			alert("请上传照片");
			return false;
		}
		$("#uploadForm").submit();
})		
</script>
