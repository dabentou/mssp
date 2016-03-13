<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持申请</title>
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
      		 <input  type="hidden"  class="input_style" name="type" value="R06" />
      		 <table id="tables" class="maintable table table-hover"  style="width: 85%;">
      		 <tr><td colspan="5"><span class="project_title">促销员工资申请</span></td></tr>
      		 <tr>
      		 		 <td rowspan="5">基本信息</td>
      		 		 <td><span class="star">*</span>选择门店：</td>
     		 	<td>
     		 		<div class="multiple-select">
     	 			<select  class="selectpicker show-tick form-control" name="storeId" data-live-search="true">
         				 <c:forEach items="${store }" var="item">
         				 	<option value="${item.id }">${item.irName }</option>
         				 </c:forEach>
        		 </select>
				</div>
     		 	</td>
           			 <td ><span class="star">*</span>申请编号：</td>
     	 			 <td><input  type="text"  class="input_style" name="ppn" value="${ppn }" /></td>
     	 			 
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
     	 		<td><input  type="text"  class="input_style" name="applyDate" value="${applyDate }" readonly="readonly"/></td>
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
     	 			<input  type="text"  class="input_style" name="applyTheme4" value="促销员工资" style="width:100px;" readonly/>
     	 		</td>
     	 		<td><span class="star">*</span>申请费用：</td>
     	 		<td><input  type="text"  class="input_style" name="applyPrice" onchange="checkApplyPrice()"/></td>
     		 </tr>
     		 <tr>
     		 	<td><span class="star">*</span>申请代理商：</td>
     	 		<td><input  type="text"  class="input_style" name="agnet" value="${commAgent.irName }" readonly="readonly"/></td>
     		 	<td></td><td></td>
     		 </tr>
     		 <tr>
     		 		<td><span class="star">*</span>邮件特批：</td>
     		 		<td>
     		 			<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="approveEmail" accept="image/gif,image/jpeg,image/png"  />
     		 			<!-- <input ss="photoUrl" type="hidden" value="" id="approveEmail" name="approveEmail" />   -->
     		 			<textarea ss="photoUrl" style='display: none;'  name="approveEmail"></textarea>
     		 			 <div ss="photoUrl" name="emailPosition"  ></div>
     		 		</td>
     		 </tr>
     		 
     		 <tr>
     		 		<td rowspan="7">申请内容<br /><a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">添加</a>
     		 									<!-- <a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a> -->
     		 		</td>
     		 		<td><span class="star">*</span>促销员姓名：</td>
     		 		<td><input  type="text"  class="input_style" name="applyName" /></td>
     		 		<td><span class="star">*</span>身份证号：</td>
     		 		<td><input  type="text"  class="input_style" name="applyIDCardNumber" /></td>
     		 </tr>
     		<tr>
     		 		<td><span class="star">*</span>联系手机：</td><td><input  type="text"  class="input_style" name="applyPhone" /></td>
     		 		<td><span class="star">*</span>工资总计：</td><td>￥<input  type="text"  class="input_style" name="totalSalary" /></td>
     		 </tr>
     		 <tr>
     		 		<td>情况说明：</td>
      				<td colspan="4"><textarea cols="83" rows="5"   name="remark" ></textarea></td>
     		 </tr>
     		 <tr>
     		 		<td><span class="star">*</span>零售店名称：</td><td><input  type="text"  class="input_style" name="retailName" /></td>
     		 		<td><span class="star">*</span>月产品总销量：</td><td><input  type="text"  class="input_style" name="monthProductSale" placeholder = "0"/></td>
     		 </tr>
     		 <tr>
     		 		<td><span class="star">*</span>月零售产品总销量：</td><td><input  type="text"  class="input_style" name="monthRetailSale"   placeholder = "0"/></td>
     		 		<td><span class="star">*</span>月零售产品占比：</td><td><input  type="text"  class="input_style" name="retailProductPercent" /></td>
     		 </tr>
     		 <tr>
     		 		<td><span class="star">*</span>促销员照片:</td>
     		 		<td colspan="3">
     		 			<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="salerPhoto" accept="image/gif,image/jpeg,image/png"/>
     		 			<textarea style="width:300px;height:35px;" name="salerPhotoContent">备注</textarea>
     		 			<!-- <input type="hidden" ss="photoUrl" name="photoUrl" /> -->
     		 			<textarea ss="photoUrl" style='display: none;' name="photoUrl"></textarea>
     		 			<div  ss="photoUrl" name="photoPosition"></div>
     		 		</td>
     		  </tr>
     		 <tr>
     		 	<td><span class="star">*</span>促销员打分：</td>
     		 		<td colspan="3">
     		 			<input type="file" name="excelFile"  onchange="uploadExcel(this)"  id="upload_excel" />
     		 			<input type="hidden" value="" name="salerMarkContent" />
     		 			<div class="download"></div>
     		 		</td>
     		 </tr>	
    		<tr>
      			<td colspan="6">
      				<input type="submit" onclick="return valiForm();" class="button button-lightblue" value="确认提交">
      				<!-- <input type="button" onclick="addContent(this)" class="button button-lightblue" value="新增内容"> -->
      			</td>
      		</tr>
	</table>
	</form>
  	</div>
  	</div>
</body>
</html>
<script type="text/javascript">
$('.datetimepicker').datetimepicker({
    format: 'yyyy-mm',
    autoclose: true,
    minView:2
});

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
				pon.append("<img title='重新上传可追加' width=100 src='" + urls + "'> ");
				purl.append(urls+","); 
			}else{
				alert(json.error);
			}
		} 
	});
}
	
var row_count = 1;
	//添加申请内容
	addContent = function(dom){
		var salerPhotoId = "salerPhoto" + row_count;
		var upload_excel_id = "upload_excel" + row_count;
		var html = '<tr>' +
	 		'<td rowspan="7">申请内容<br/><a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">添加</a><a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a></td>'+
		 		'<td>促销员姓名：</td>'+
		 		'<td><input  type="text"  class="input_style" name="applyName" /></td>'+
		 		'<td>身份证号码：</td>'+
		 		'<td><input  type="text"  class="input_style" name="applyIDCardNumber" /></td>'+
		 '</tr>'+
		'<tr>'+
		 		'<td>联系手机：</td><td><input  type="text"  class="input_style" name="applyPhone" /></td>'+
		 		'<td>工资总计：</td><td>￥<input  type="text"  class="input_style" name="totalSalary" /></td>'+
		 '</tr>'+
		 '<tr>'+
		 		'<td>情况说明：</td>'+
				'<td colspan="4"><textarea cols="83" rows="5"   name="remark" ></textarea></td>'+
		 '</tr>'+
		 '<tr>'+
		 		'<td>零售店名称：</td><td><input  type="text"  class="input_style" name="retailName" /></td>'+
		 		'<td>月产品总销量：</td><td><input  type="text"  class="input_style" name="monthProductSale" placeholder = "0"/></td>'+
		 '</tr>'+
		 '<tr>'+
		 		'<td>月零售产品总销量：</td><td><input  type="text"  class="input_style" name="monthRetailSale"   placeholder = "0"/></td>'+
		 		'<td>月零售产品占比：</td><td><input  type="text"  class="input_style" name="retailProductPercent" /></td>'+
		 '</tr>'+
		 '<tr>'+
		 		'<td>促销员照片:</td>'+
		 		'<td colspan="3">'+
		 			'<input type="file" name="imgFile"  onchange="uploadPhoto(this)"  id="'+salerPhotoId +'" accept="image/gif,image/jpeg,image/png"/>'+
		 			'<textarea style="width:300px;height:35px;" name="salerPhotoContent">备注</textarea>'+
		 			'<input type="hidden" ss="photoUrl"  name="photoUrl" /><div ss="photoUrl"   name="photoPosition"></div>'+
		 		'</td>'+
		  '</tr>'+
		 '<tr>'+
		 		'<td>促销员打分:</td>'+
		 		'<td colspan="3">'+
		 			'<input type="file" name="excelFile"  onchange="uploadExcel(this)" id="'+upload_excel_id+'"  />'+
		 			'<input type="hidden" value="" name="salerMarkContent" />'+
		 			'<div class="download"></div>'+
		 		'</td>'+
		 '</tr>';
		 row_count++;
		/* $(dom).parent().parent().before(html); */
		/* $("tr:last").before(html); */
		/*  $("tr:last").before("ssss"); */
		$("#tables").find("tr:last").before(html); 
	}
	
	delContent = function(dom){
		for(var i=0; i<6; i++){
			$(dom).parent().parent().next().remove();
		}
		$(dom).parent().parent().remove();
		row_count--;
	}
	
	valiForm = function(){
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		/* var provider = $("input[name='provider']").val(); */
		var emailFile = $("#emailPosition").val();
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
		/* if(provider==''){
			alert('供应商不能为空！');
			$("input[name='provider']").focus();
			return false;
		} */
		if(emailFile == ''){
			alert('特批邮件不能为空！');
			$("#emailFile").focus();
			return false;
		}
		var errorCount = 0;
/* 		$("select[name='propagandaLevel']").each(function(){
			$(this).find().find("option:selected").val();
		}); */
		$("input[name='applyName']").each(function(){
			if($(this).val()==''){
				alert("促销员姓名不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='applyIDCardNumber']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("身份证号不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='applyPhone']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("联系电话不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='totalSalary']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("工资总计不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='retailName']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("零售店名称不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='monthProductSale']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("月产品总销量不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='monthRetailSale']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("月零售产品总销量不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='retailProductPercent']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("月零售产品占比不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("textarea[name='photoUrl']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("促销员照片不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("textarea[name='salerMarkContent']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("促销员打分表不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		if(errorCount>0){
			return false;
		}
		if(!checkApplyPrice()){
			return false;
		}
	}
	function uploadExcel(dom){
		var ids=$(dom).attr("id");
		var input = $(dom).next();
		var download = $(dom).next().next();
		$.ajaxFileUpload({
			type : "POST",
			url : "${ROOT}/editor/excel/upload",
			secureuri:false,  
		    fileElementId:ids,//文件选择框的id属性 
			dataType : "json",
			success : function(json){
				if(json.error==0){
					var urls ="http://" + window.location.host+json.url;
					if(input.val()==""){
						input.val(urls);
					}else{
						input.val(input.val() + "," +urls);
					}
					download.append('<a href="'+ urls +'" class="thumbnail"></a>');
				}else{
					alert(json.error);
				}
			} 
		});
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
	})
</script>





