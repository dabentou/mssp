<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>精英网积分兑换申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
   <style>
		.input_style{
			width: 160px;
			text-align:center;
		}
		.input_style1{
			width: 60px;
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
      		 <input  type="hidden"  class="input_style" name="type" value="R11" />
      		 <table class="maintable table table-hover"  style="width: 85%;">
      		 <tr><td colspan="5"><span class="project_title">精英网积分兑换申请</span></td></tr>
					<tr>
						<td rowspan="7">基本信息</td>
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
						<td><span class="star">*</span>申请编号：</td>
						<td><input type="text" class="input_style" name="ppn"
							value="${ppn }" /></td>

					</tr>
					<tr>
           			 <td><span class="star">*</span>所属省份：</td>
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
     	 			<!-- <input  type="text" class="form-control text datetimepicker"  style="width: 100px;height: 21px;"   name="applyTheme2" id="pBidTime"/> -->
     	 			<select name="applyTheme2" style="width:50px;">
     	 				<option value="2015" >2015</option>
     	 				<option value="2016" >2016</option>
     	 				<option value="2017" >2017</option>
     	 				<option value="2018" >2018</option>
     	 				<option value="2019" >2019</option>
     	 				<option value="2020" >2020</option>
     	 			</select>
     	 			<select name="applyTheme3" style="width:40px;">
     	 				<option value="01" >01</option>
     	 				<option value="02" >02</option>
     	 				<option value="03" >03</option>
     	 				<option value="04" >04</option>
     	 				<option value="05" >05</option>
     	 				<option value="06" >06</option>
     	 				<option value="07" >07</option>
     	 				<option value="08" >08</option>
     	 				<option value="09" >09</option>
     	 				<option value="10" >10</option>
     	 				<option value="11" >11</option>
     	 				<option value="12" >12</option>
     	 			</select>
     	 			<input  type="text"  class="input_style" name="applyTheme4" value="精英网积分兑换" style="width:100px;" readonly/>
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
     		 
     		 <tr>
     		 	<td><span class="star">*</span>申请代理商：</td>
     	 		<td><input  type="text"  class="input_style" name="agnet" value="${commAgent.irName }" readonly="readonly"/></td>
     		 	<td></td><td></td>
     		 </tr>
     		 
     		 <tr>
     		 		<td><span class="star">*</span>邮件特批：</td>
     		 		<td>
     		 			<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="approveEmails" accept="image/gif,image/jpeg,image/png"  />
     		 			<!-- <input ss="photoUrl" type="hidden" value="" id="approveEmail" name="approveEmail" />   -->
     		 			<textarea ss="photoUrl" style='display: none;'  name="approveEmail"></textarea>
     		 			<div ss="photoUrl" name="emailPosition"  ></div>
     		 		</td>
     		 </tr>
     		 
     		<!--  <tr>
     		 	<td><span class="star">*</span>邮件特批：</td>
     		 	<td colspan="2">
     		 		<input type="file" id="approveEmail" name="files"/>
     		 		<textarea class="col-md-6 form-control textarea" rows="3"  name="approveEmailRemark" placeholder="备注">邮件说明</textarea>
     		 </tr> -->
     		 <tr>
     		 	<td><span class="star">*</span>情况说明：</td>
     		 	<td colspan="2">
     		 		<textarea class="col-md-6 form-control textarea" rows="3"  name="remark" >情况说明</textarea>
     		 </tr>
     		 <tr>
     		 	<td>申请内容：
     		 	</td>
     		 	<td colspan="4">
     		 		<table>
     		 				<thead><tr> <td>下单人姓名</td> <td>商品名称</td> <td>商品数量</td> <td>商品单价</td> <td>产品积分</td>
     		 							 <td>订单积分</td> <td>下单时间</td> <td>订单状态</td><td>当月代理成本价</td><td>操作</td>
     		 				</tr></thead>
     		 				
     		 				<tbody id="tables">
     		 					<tr>
     		 						<td><input  type="text"  class="input_style1" name="col1"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col2"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col3"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col4"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col5"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col6"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col7"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col8"/></td>
     		 						<td><input  type="text"  class="input_style1" name="col9"/></td>
     		 						<td>
     		 							<a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">增加</a>
     		 							<!-- <a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a> -->
     		 						</td>
     		 					</tr>
     		 				
     		 				</tbody>
     		 				
     		 		</table>
     		 		
     		 	</td>
     		 </tr>
    
	</table>
			<input  type="submit" onclick="return valiForm();"  class="button button-lightblue"  value="确认提交"/>
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
		if(emailFile == ''){
			alert('特批邮件不能为空！');
			$("#emailFile").focus();
			return false;
		}
		if(!checkApplyPrice()){
			return false;
		}
}

function uploadPhoto(dom){
	var ids=$(dom).attr("id");
	/* var purl=$(dom).siblings("input[name=photoUrl]");
	var pon=$(dom).siblings("div[name=photoPosition]"); */
	var  purl=$(dom).siblings("textarea[ss=photoUrl]");
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
				/* purl.val(urls);  */
				purl.append(urls+","); 
			}else{
				alert(json.error);
			}
		} 
	});
}

addContent = function(dom){
	var html ='<tr>'+
			'<td><input  type="text"  class="input_style1" name="col1"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col2"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col3"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col4"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col5"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col6"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col7"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col8"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col9"/></td>'+
			'<td>'+
				'<a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">增加</a>'+
				'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
			'</td>'+
		'</tr>';
	
	$(dom).parent().parent().after(html);
}

delContent = function(dom){
	var html ='<tr>'+
			'<td><input  type="text"  class="input_style1" name="col1"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col2"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col3"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col4"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col5"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col6"/></td>'+
			'<td><input  type="text"  class="input_style1" name="col7"/></td>'+
			'<td>'+
				'<a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">增加</a>'+
				'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
			'</td>'+
		'</tr>';
	
	$(dom).parent().parent().remove();
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
$(window).on('load', function () {

    $('.selectpicker').selectpicker({
        'selectedText': 'cat'
    });

    // $('.selectpicker').selectpicker('hide');
});
</script>



