<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>区域媒体广告申请管理</title>
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
		.select{
			width: 160px;
			height:21px;
		}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
	  <div class="wrap">
    	<%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
    <%@include file="/WEB-INF/include/iretail_menu.jsp" %>
      	<form id="form" class="form-horizontal"  action="${ROOT}/iretail/project/apply/post" enctype="multipart/form-data" method="post" >
      		 <input  type="hidden"  class="input_style" name="type" value="R07" />
      		 <table class="maintable table table-hover"  style="width: 85%;">
      		 <tr><td colspan="5"><span class="project_title">区域媒体广告申请管理</span></td></tr>
    		 <tr>
						<td rowspan="5">基本信息</td>
						<td><span class="star">*</span>选择门店：</td>
						<td>
							<div class="multiple-select">
								<select class="selectpicker show-tick form-control"
									name="storeId" data-live-search="true">
									<c:forEach items="${store }" var="item">
										<option value="${item.id }">${item.irName }</option>
									</c:forEach>
								</select>
							</div>
						</td>
						<td ><span class="star">*</span>申请编号：</td>
     	 			 <td><input  type="text"  class="input_style" name="ppn" value="${ppn }" /></td>
     	 			 
           			 <!-- <td><span class="star">*</span>核销编号：</td>
     	 			 <td><input  type="text"  class="input_style" name="cpn" /></td> -->
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>所属大区：</td>
     	 			 <td>
     	 			 	<select class="select" name="provinceId">  
	      					<c:forEach items="${provinces}" var="item">
		            			<option value="${item.id}" >${item.province }</option>
		            		</c:forEach>
	      				<select>
     	 				<%-- <input  type="text"  class="input_style" name="area" value="${commAgent.commCity.commArea.areaName }" readonly="readonly"/>
     	 				<input  type="hidden"  class="input_style" name="cityId" value="${commAgent.commCity.id }"/> --%>
     	 			</td>
           			 <td><span class="star">*</span>申请日期：</td>
     	 			 <td><input  type="text"   class="input_style" name="applyDate"  readonly value="${applyDate }"/></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>申请主题：</td>
     	 			 <td><!-- <input  type="text"  class="input_style" name="applyTheme" /> -->  
     	 			<select class="select" name="applyTheme1" style="width:100px;">  
	      				<c:forEach items="${provinces}" var="item">
		            		<option value="${item.province}" >${item.province }</option>
		            	</c:forEach>
	      			<select>
     	 			<input  type="text" class="form-control text datetimepicker"  style="width: 100px;height: 21px;"   name="applyTheme2" id="pBidTime"/>
     	 			<input  type="text"  class="input_style" name="applyTheme3" value="区域媒体广告" style="width:100px;" readonly/>
     	 		</td>
           			 <td><span class="star">*</span>申请费用：</td>
     	 			 <td><input  type="text"  class="input_style" name="applyPrice" onchange="checkApplyPrice()" /></td>
     		 </tr>
     		 <tr>
           			 <td><span class="star">*</span>有效期：</td>
     	 			 <td><input type="text" class="input_style"   name="startTime"  value="${applyDate }" readonly/></td>
     	 			 <td >至</td>
     	 			<td><input type="text" class="input_style"   name="endTime"  value="${endTime }" readonly/></td>
     		 </tr>
     		<!--  <tr>
           			 <td><span class="star">*</span>供应商：</td>
     	 			 <td><input  type="text"  class="input_style" name="provider"/></td>
     		 </tr> -->
     		 
     		  <tr>
     		 		<td><span class="star">*</span>邮件特批：</td>
     		 		<td>
     		 			<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="approveEmails" accept="image/gif,image/jpeg,image/png"  />
     		 			<input ss="photoUrl" type="hidden" value="" id="approveEmail" name="approveEmail" />   <div ss="photoUrl" name="emailPosition"  ></div>
     		 		</td>
     		 </tr>
     		 
     		 <!-- <tr>
     		 	<td><span class="star">*</span>邮件特批：</td>
     		 	<td colspan="2">
     		 		<input type="file" id="approveEmail" name="files"/>
     		 		<textarea class="col-md-6 form-control textarea" rows="3"  name="approveEmailRemark" placeholder="备注">邮件说明</textarea>
     		 </tr> -->
     		 <tr>
     		 	<td rowspan="4"><span class="star">*</span>申请内容：<br />
     		 		<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a></td>
     		 	<td><span class="star">*</span>媒体名称:</td>
     		 	<td><input  type="text"  class="input_style" name="mediaName" /></td>
     		 	<td><span class="star">*</span>媒体性质:</td>
     		 	<td><input  type="text"  class="input_style" name="mediaNature" /></td>		
     		 </tr>
     		 
     		 <tr>
     		 	<td><span class="star">*</span>广告尺寸:</td>
     		 	<td><input  type="text"  class="input_style" name="adSize" /></td>
     		 	<td><span class="star">*</span>发布费用:</td>
     		 	<td>￥<input  type="text"  class="input_style" name="releaseCost" /></td></tr>
     		 </tr>
     		 
     		 <tr>
     		 		<td>情况说明：</td>
      				<td colspan="4"><textarea cols="83" rows="5"   name="remark" >情况说明</textarea></td>
     		 </tr>
     		 
     		 	<tr>
     		 		<td><span class="star">*</span>效果图:</td>
     		 		<td>
     		 			<input type="file" id="salerMark" onchange="uploadPhotos(this)"  accept="image/gif,image/jpeg,image/png" class="input_style" name="imgFile"/>
     		 			<textarea style="width:300px;height:35px;" id="salerMarkContent" name="salerMarkContent">备注</textarea>
     		 			<input type="hidden" name="pictureUrl"  />   <div name="picturePosition"  ></div>
     		 		</td>
     		 	</tr>
     		 			
    
    		<tr>
      			<td colspan="6">
      				<input type="submit" onclick="return valiForm();" class="button button-lightblue" value="确认提交">
      				<input type="button" onclick="addContent(this)" class="button button-lightblue" value="新增内容">
      			</td>
      		</tr>
	</table>
			
	</form>
  	</div>
  	</div>
</body>
</html>
<script type="text/javascript">
$(document).ready(function(){
    //日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm',
        autoclose: true,
        minView:2
    });
})


function uploadPhoto(dom){
	var ids=$(dom).attr("id");
	/* var purl=$(dom).siblings("input[name=photoUrl]");
	var pon=$(dom).siblings("div[name=photoPosition]"); */
	var  purl=$(dom).siblings("input[ss=photoUrl]");
	var pon=$(dom).siblings("div[ss=photoUrl]");
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
	var purl=$(dom).siblings("input[name=pictureUrl]");
	var pon=$(dom).siblings("div[name=picturePosition]");
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

valiForm = function(){
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		var provider = $("input[name='provider']").val();
		var emailFile = $("#approveEmail").val(); 
		if(applyTheme==''){
			alert('申请主题不能为空！');
			$("input[name='applyTheme']").focus();
			return false;
		}
		if(applyPrice==''){
			alert('申请费用不能为空！');
			$("input[name='applyPrice']").focus();
			return false;
		}
		if(isNaN(parseInt(applyPrice))){
			alert("申请费用必须为数字！");
			$("input[name='applyPrice']").focus();
			return false;
		}
		if(provider==''){
			alert('供应商不能为空！');
			$("input[name='provider']").focus();
			return false;
		}
		if(emailFile==''){
			alert('特批邮件不能为空！');
			$("#emailFile").focus();
			return false;
		}
		if(!checkApplyPrice()){
			return false;
		}
}

var row_count = 1;
//添加申请内容
addContent = function(dom){
	 var salerMarkId="salerMark" + row_count;
	 var html='<tr>'+
		 	'<td rowspan="4"><span class="star">*</span>申请内容：<br />'+
		 		'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a></td>'+
		 	'<td><span class="star">*</span>媒体名称:</td>'+
		 	'<td><input  type="text"  class="input_style" name="mediaName" /></td>'+
		 	'<td><span class="star">*</span>媒体性质:</td>'+
		 	'<td><input  type="text"  class="input_style" name="mediaNature" /></td>'+		
		 '</tr>'+
		 '<tr>'+
		 	'<td><span class="star">*</span>广告尺寸:</td>'+
		 	'<td><input  type="text"  class="input_style" name="adSize" /></td>'+
		 	'<td><span class="star">*</span>发布费用:</td>'+
		 	'<td>￥<input  type="text"  class="input_style" name="releaseCost" /></td></tr>'+
		 '</tr>'+
		 '<tr>'+
		 		'<td>情况说明：</td>'+
				'<td colspan="4"><textarea cols="83" rows="5"   name="remark" >情况说明</textarea></td>'+
		 '</tr>'+
		 	'<tr>'+
		 		'<td><span class="star">*</span>效果图:</td>'+
		 		'<td>'+
		 			'<input type="file" id="'+salerMarkId+'" onchange="uploadPhotos(this)"  accept="image/gif,image/jpeg,image/png" class="input_style" name="imgFile"/>'+
		 			'<textarea style="width:300px;height:35px;" id="salerMarkContent" name="salerMarkContent">备注</textarea>'+
		 			'<input type="hidden" name="pictureUrl"  />   <div name="picturePosition"  ></div>'+
		 		'</td>'+
		 	'</tr>';
	
	row_count++;
	$(dom).parent().parent().before(html);
}

delContent = function(dom){
	for(var i=0; i<3; i++){
		$(dom).parent().parent().next().remove();
	}
	$(dom).parent().parent().remove();
	row_count--;
}

//校验申请费用
checkApplyPrice = function(){
	var isSubmit = true;
	var provinceId = $("select[name='provinceId'] option:selected").val();
	var applyPrice = $("input[name='applyPrice']").val();
	var type = $("input[name='type']").val();
	if(provinceId==''){
		alert('请选择省份！');
		isSubmit = false;
		return;
	}
	if(applyPrice==""){
		alert("申请费用不能为空!");
		isSubmit = false;
		$("input[name='applyPrice']").focus();
		return;
	}
	if(isNaN(parseInt(applyPrice))){
		alert("申请费用必须为数字！");
		isSubmit = false;
		$("input[name='applyPrice']").focus();
		return;
	}
	$.ajax({
		type : "POST",
		url : "${ROOT}/iretail/project/checkApplyPrice",
		data : {
			provinceId : provinceId,
			applyPrice : applyPrice,
			type : type
		},
		dataType : "json",
		success : function(json){
			if(json.status == "OK"){
		 	}else{
		 		alert(json.errors[0].msg);
		 		isSubmit = false;
		 		$("input[name='applyPrice']").focus();
		 	}
		}
	});
	return isSubmit;
}
</script>



