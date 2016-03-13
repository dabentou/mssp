<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-PPN单据字段维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
 <style>
		.input_style{
			width: 160px;
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
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
</style>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">PPN单据字段维护</a></li>
      </ol>
      <div>
		 <form id="myFrom" class="form-horizontal"  action="${ROOT}/admin/iretail/ppn/projectEdit"  method="post">
	      	<table class="maintable table table-hover"  style="width: 75%;" >
	      		<tr>
	      			<td rowspan="5">基本信息</td>
	      			<td> 申请编号：</td>
	      			<td>
						<input  type="text"  class="input_style" name="ppn" value="${ppn }"/>
						<input type="button" onclick="getRO1Project()" class="button button-blue" value="查询">
	      			</td>
	      		</tr>
	      		<tr>
	      			<td> 有效期：</td>
	      			<td id='validityDate-td'></td>
	      		</tr>
	      		<tr>
	      			<td> 申请主题：</td>
	      			<td><input  type="text"  class="input_style" name="applyTheme" /></td> 
	      			<td> 申请费用：</td>
	      			<td><input  type="text"  class="input_style" name="applyPrice" /></td>
	      		</tr>
	      		<tr>
	      			<td> 申请日期：</td>
	      			<td id='applyDate-td'></td>
	      			<td> 申请代理商：</td>
	      			<td id='agnet-td'></td>
	      		</tr>
	      		<tr>
	      			<td> 所属大区：</td>
	      			<td id='area-td'></td>
	      			<td> 供应商：</td>
	      			<td id="provider-td"></td> 
	      		</tr>
	      		<tr name = "tr2">
	      			<td><span class="star">*</span>特批邮件：</td>
	      			<td colspan="4">
	      				<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','one')" accept="image/gif,image/jpeg,image/png"/>
	      				<input type="hidden" id="email_area" name="approveEmail"/>
	      				<div id="email_view">
	                    </div>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td>特批邮件备注：</td>
	      			<td id="approveEmailRemark-td" colspan="4"></td>
	      		</tr>
	      		<tr>
	      		    <td rowspan="12">申请内容</td>
	      			<td><span class="star">*</span> 零售店名称：</td>
	      			<td><input  type="text"  class="input_style" name="iName" /></td>
	      			<td><span class="star">* </span>装修类型：</td>
	      			<td>
	      				<select class="input_style" name="decorateLevel">
							<option value="翻新">翻新</option>
							<option value="新建">新建</option>
		      			</select>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">* </span>城市类别：</td>
	      			<td>
	      				<select class="input_style" name="cityLevel">
							<option value="中心城市">中心城市</option>
							<option value="地级市">地级市</option>
							<option value="县级市">县级市</option>
		      			</select>
	      			</td>
	      			<td><span class="star">* </span>城市名称：</td>
	      			<td>
	      				<select class="input_style" name="cityName">
							<option value="上海">上海</option>
							<option value="沈阳">沈阳</option>
							<option value="广州">广州</option>
		      			</select>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span> 卖场名称：</td>
	      			<td>
	      				<!-- <input  type="text"  class="input_style" name="shopPlace" /> -->
	      				<select  class="input_style" name="shopPlace">  
							<option value="">选择卖场</option>
							<c:forEach items="${storeList }" var="item">
								<option value="${item.irName }">${item.irName }</option>
							</c:forEach>
		      			</select>
	      			</td>
	      			<td><span class="star">*</span> 店面位置：</td>
	      			<td><input  type="text"  class="input_style" name="iLocation" />(层号)</td>
	      		</tr>
	      		<tr>
	      		    <td><span class="star">*</span> 店负责人：</td>
	      			<td><input  type="text"  class="input_style" name="iPrincipal" /></td>
	      			<td><span class="star">*</span> 联系手机：</td>
	      			<td><input  type="text"  class="input_style" name="phone" /></td>
	      		</tr>
	      		<tr>
	      		   <td><span class="star">*</span> 店面积：</td>
	      			<td><input  type="text"  class="input_style" name="iAcreage" />(平方米)</td>
	      			<td><span class="star">*</span> 月平均零售产品占比：</td>
	      			<td><input  type="text"  class="input_style" name="lsRate" />%</td>
	      		</tr>
	      		<tr>
	      		    <td><span class="star">*</span> 月平均全产品销量：</td>
	      			<td><input  type="text"  class="input_style" name="yqVolume" />(台)</td>
	      			<td><span class="star">*</span> 月平均零售产品销量：</td>
	      			<td><input  type="text"  class="input_style" name="lsVolume" />(台)</td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span> 门店目标量：</td>
	      			<td><input  type="text"  class="input_style" name="iTargetVolume" onchange="getILevel(this)"/></td>
	      			<td><span class="star">*</span> 店面级别：</td>
	      			<td><input  type="text" id="iLevelInput"  class="input_style" name="iLevel" /></td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span>实景图：</br>(重复上传可以追加)</td>
	      			<td colspan="4">
	      				<input type="file" id="iRealPicImg" value="选择图片" onchange="uploadImg(this,'iRealPicImg','iRealPic_view','iRealPic-area','many')" name="imgFile" accept="image/gif,image/jpeg,image/png" />
	      				<textarea id="iRealPic-area" style='display: none;' name="iRealPic"></textarea>
	      				<div id="iRealPic_view">
	                    </div>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span>效果图：</br>(重复上传可以追加)</td>
	      			<td colspan="4">
	      				<input type="file" id="iResultPicImg" onchange="uploadImg(this,'iResultPicImg','iResultPic_view','iResultPic-area','many')" name="imgFile" accept="image/gif,image/jpeg,image/png" />
	      				<textarea id="iResultPic-area" style='display: none;' name="iResultPic"></textarea>
	      				<div id="iResultPic_view">
	                    </div>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span>费用明细：</td>
	      			<td colspan="2">
	      				<input type="file" id="chargesDetailImg" name="imgFile" onchange="uploadImg(this,'chargesDetailImg','chargesDetail_view','chargesDetail_area','one')" accept="image/gif,image/jpeg,image/png" />
	      				<!-- <textarea class="col-md-6 form-control textarea" rows="3" name="chargesDetailRemark"></textarea> -->
	      				<input type="hidden" id="chargesDetail_area" name="chargesDetail"/>
	      				<div id="chargesDetail_view">
	                    </div>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td>情况说明：</td>
	      			<td colspan="4"><textarea class="col-md-6 form-control textarea" rows="3" name="remark"></textarea></td>
	      		</tr>
	      		<tr>
	      			<td><span class="star">*</span>物料明细：</td>
	      			<td>
	      				<ul class="form-inline" >
	      				
	      					<c:forEach items="${listMaterials}" var="item" varStatus="itemVs">
								<li>
				      				<input name="materialName" type="checkbox" value="${item.name}" />
									<span>${item.name}</span>
				 					<input type="text" class="input-mini" style="width: 50px;" placeholder="个数" name="materialCount"> 个(单位)
		      					</li>
					     	</c:forEach>
	      				</ul>
	      			</td>
	      		</tr>
	      		<tr>
	      			<td colspan="6"><input type="submit" onclick="return valiForm();" class="button button-lightblue" value="确认提交"></td>
	      		</tr>
	      	</table>
        </form>
      </div>
    </div>
</div>
</div>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("#form").Validform({tiptype:2});  //就这一行代码！;
		
})
	
	valiForm = function(){
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		var provider = $("input[name='provider']").val();
		var iName = $("input[name='iName']").val();
		var shopPlace = $("select[name='shopPlace'] option:selected").val();
		var iLevel = $("input[name='iLevel']").val();
		var iLocation = $("input[name='iLocation']").val();
		var yqVolume = $("input[name='yqVolume']").val();
		var iAcreage = $("input[name='iAcreage']").val();
		var lsVolume = $("input[name='lsVolume']").val();
		var iPrincipal = $("input[name='iPrincipal']").val();
		var phone = $("input[name='phone']").val();
		var lsRate = $("input[name='lsRate']").val();
		var iTargetVolume = $("input[name='iTargetVolume']").val();
		var iRealPic = $("textarea[name='iRealPic']").val();
		var iResultPic = $("textarea[name='iResultPic']").val();
		var emailFile = $("#email_area").val();
		var chargesDetailImg = $("#chargesDetail_area").val();
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
		if(iName==''){
			alert('零售店不能为空！');
			$("input[name='iName']").focus();
			return false;
		}
		if(shopPlace==''){
			alert('卖场名称不能为空！');
			$("select[name='shopPlace']").focus();
			return false;
		}
		if(iLevel==''){
			alert('店面级别不能为空！');
			$("input[name='iLevel']").focus();
			return false;
		}
		if(iLocation==''){
			alert('店面位置不能为空！');
			$("input[name='iLocation']").focus();
			return false;
		}
		if(yqVolume==''){
			alert('月全产品销量不能为空！');
			$("input[name='yqVolume']").focus();
			return false;
		}
		if(isNaN(parseInt(yqVolume))){
			alert("月全产品销量必须为数字！");
			$("input[name='yqVolume']").focus();
			return false;
		}
		if(iAcreage==''){
			alert('店面积不能为空！');
			$("input[name='iAcreage']").focus();
			return false;
		}
		if(lsVolume==''){
			alert('月零售产品销量不能为空！');
			$("input[name='lsVolume']").focus();
			return false;
		}
		if(isNaN(parseInt(lsVolume))){
			alert("月零售产品销量必须为数字！");
			$("input[name='lsVolume']").focus();
			return false;
		}
		if(parseInt(lsVolume)>parseInt(yqVolume)){
			alert("月零售产品销量不能大于月全产品销量！");
			$("input[name='lsVolume']").focus();
			return false;
		}
		if(iPrincipal==''){
			alert('店负责人不能为空！');
			$("input[name='iPrincipal']").focus();
			return false;
		}
		if(lsRate==''){
			alert('月零售产品占比不能为空！');
			$("input[name='lsRate']").focus();
			return false;
		}
		if(phone==''){
			alert('联系人手机不能为空！');
			$("input[name='phone']").focus();
			return false;
		}
		if(iTargetVolume==''){
			alert('门店目标量不能为空！');
			$("input[name='iTargetVolume']").focus();
			return false;
		}
		if(isNaN(parseInt(iTargetVolume))){
			alert("门店目标量必须为数字！");
			$("input[name='iTargetVolume']").focus();
			return false;
		}
		if(iRealPic==''){
			alert('实景图不能为空！');
			$("input[name='iRealPic']").focus();
			return false;
		}
		if(iResultPic==''){
			alert('效果图不能为空！');
			$("input[name='iResultPic']").focus();
			return false;
		}
		if(chargesDetailImg==''){
			alert('费用明细不能为空！');
			$("#chargesDetail_area").focus();
			return false;
		}
		if(emailFile==''){
			alert('特批邮件不能为空！');
			$("#email_area").focus();
			return false;
		}
		if($('input[name="materialName"]:checked').length==0){
			alert('请选择物料！');
			return false;
		}
		var errorCount = 0;
		$('input[name="materialName"]:checked').each(function(){
			var materialCount = $(this).next().next().val();
			if(materialCount==''){
				alert($(this).next().html()+"的个数不能为空！");
				$(this).next().next().focus();
				errorCount++;
				return false;
			}
	    });
		if(errorCount>0){
			return false;
		}
	}
	
	
	//加载店面级别
	getILevel = function(dom){
		var iTargetVolume = $(dom).val();
		if(iTargetVolume==''){
			alert('门店目标量不能为空！');
			$("input[name='iTargetVolume']").focus();
			return false;
		}
		if(isNaN(parseInt(iTargetVolume))){
			alert("门店目标量必须为数字！");
			$("input[name='iTargetVolume']").focus();
			return false;
		}
		$.ajax({
			type : "POST",
			url : "${ROOT}/iretail/project/getILevel",
			data : {iTargetVolume:iTargetVolume},
			dataType : "json",
			success : function(json){
			 	if(json.status == "OK"){
			 		$("input[name='iLevel']").val(json.data.name);
			 	}else{
			 		alert(json.errors[0].msg);
			 		$("input[name='iTargetVolume']").focus();
			 		$("input[name='iLevel']").val('');
			 	}
			}
		});
	}
		
	/* 
	上传图片  type参数，one表示重复上传可替换，many表示重复上传可添加
	*/
	uploadImg = function(dom,inputId,viewId,areaId,type){
		$.ajaxFileUpload({
			type : "POST",
			url : "${ROOT}/editor/image/upload",
			secureuri:false,  
		    fileElementId:inputId,//文件选择框的id属性 
			dataType : "json",
			success : function(json){
				if(json.error==0){
					var uv = $("#"+viewId);
					var url ="http://" + window.location.host+json.url;
					if(type=='one'){
						$("#"+areaId).val(url);
						uv.html("<img title='重复上传可替换' width=100 src='" + url + "'> ");
					}else{
						var urlCont = $("#"+areaId).val();
						$("#"+areaId).val(urlCont+url+",");
						uv.append("<img ondblclick='delImg(this,\""+areaId+"\",\""+url+"\")' title='双击删除' width=100 src='" + url + "'> ");
					}
				}else{
					alert(json.error);
				}
			}
		});
	}
	
	//删除图片
	delImg = function(dom,areaId,imgUrl){
		var urlCont = $("#"+areaId).val().replace(imgUrl+",","");
		$("#"+areaId).val(urlCont)
		$(dom).remove();
	}
	
	
	function formatTen(num) { 
			return num > 9 ? (num + "") : ("0" + num); 
	} 
	
	// GMT时间格式转换 
	function formatDate(date) { 
		var year = date.getFullYear(); 
		var month = date.getMonth() + 1; 
		var day = date.getDate(); 
		var hour = (date.getHours()==0?"00":date.getHours()); 
		var minute = date.getMinutes(); 
		var second = date.getSeconds(); 
		return year + "-" + formatTen(month) + "-" + formatTen(day)+"  " + hour +":"+formatTen(minute)+":"+formatTen(second); 
	} 
	
	
	//根据申请编号查询r01项目信息
	getRO1Project = function(){
		var ppn = $("input[name='ppn']").val();
		if(ppn==''){
			alert("申请编号不能为空！");
			$("input[name='ppn']").focus();
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ROOT}/admin/iretail/ppn/search",
			data : {ppn:ppn},
			dataType : "json",
			success : function(json){
			 	if(json.status == "OK"){
			 		$("#validityDate-td").html(formatDate(new Date(json.data.beginTime))+" / "+formatDate(new Date(json.data.endTime)));
			 		$("input[name='applyTheme']").val(json.data.applyTheme);
			 		$("input[name='applyPrice']").val(json.data.applyPrice);
			 		$("#applyDate-td").html(formatDate(new Date(json.data.applyDate)));
			 		$("#agnet-td").html(json.data.store.irName);
			 		$("#area-td").html(json.data.city.commArea.areaName);
			 		$("#provider-td").html(json.data.provider);
			 		$("#email_view").html("<img title='重复上传可替换' width=100 src='" + json.data.approveEmail + "'> ");
			 		$("#email_area").val(json.data.approveEmail);
			 		$("#approveEmailRemark-td").html(json.data.approveEmailRemark);
			 		var applyContent = jQuery.parseJSON(json.data.applyContent);
			 		$("input[name='iName']").val(applyContent.iName);
			 		$("select[name='decorateLevel']").children("option").each(function(){  
			              var temp_value = $(this).val();  
			              if(temp_value == applyContent.decorateLevel){  
			                    $(this).attr("selected","selected");  
			              }  
			         });
			 		$("select[name='cityLevel']").children("option").each(function(){  
			              var temp_value = $(this).val();  
			              if(temp_value == applyContent.cityLevel){  
			                    $(this).attr("selected","selected");  
			              }  
			         });
			 		$("select[name='cityName']").children("option").each(function(){  
			              var temp_value = $(this).val();  
			              if(temp_value == applyContent.cityName){  
			                    $(this).attr("selected","selected");  
			              }  
			         });
			 		$("select[name='shopPlace']").children("option").each(function(){  
			              var temp_value = $(this).val();  
			              if(temp_value == applyContent.shopPlace){  
			                    $(this).attr("selected","selected");  
			              }
			         });
			 		$("input[name='iLocation']").val(applyContent.iLocation);
			 		$("input[name='iPrincipal']").val(applyContent.iPrincipal);
			 		$("input[name='phone']").val(applyContent.phone);
			 		$("input[name='iAcreage']").val(applyContent.iAcreage);
			 		$("input[name='lsRate']").val(applyContent.lsRate);
			 		$("input[name='yqVolume']").val(applyContent.yqVolume);
			 		$("input[name='lsVolume']").val(applyContent.lsVolume);
			 		$("input[name='iTargetVolume']").val(applyContent.iTargetVolume);
			 		$("input[name='iLevel']").val(applyContent.iLevel);
			 		var iRealPicArr = applyContent.iRealPic.split(',');
			 		var iRealPic_areaId = "iRealPic-area";
			 		for(var i=0; i<iRealPicArr.length; i++){
			 			if(iRealPicArr[i]!=''){
			 				$("#iRealPic_view").append("<img ondblclick='delImg(this,\""+iRealPic_areaId+"\",\""+iRealPicArr[i]+"\")' title='双击删除' width=100 src='" + iRealPicArr[i] + "'> ");
			 			}
			 		}
			 		$("#iRealPic-area").val(applyContent.iRealPic);
			 		var iResultPicArr = applyContent.iResultPic.split(',');
			 		var iResultPic_areaId = "iResultPic-area";
			 		for(var i=0; i<iResultPicArr.length; i++){
			 			if(iResultPicArr[i]!=''){
			 				$("#iResultPic_view").append("<img ondblclick='delImg(this,\""+iResultPic_areaId+"\",\""+iResultPicArr[i]+"\")' title='双击删除' width=100 src='" + iResultPicArr[i] + "'> ");
			 			}
			 		}
			 		$("#iResultPic-area").val(applyContent.iResultPic);
			 		$("#chargesDetail_view").html("<img title='重复上传可替换' width=100 src='" + applyContent.chargesDetail + "'> ");
			 		$("#chargesDetail_area").val(applyContent.chargesDetail);
			 		var materialDetailJson = jQuery.parseJSON(applyContent.materialDetail);
			 		$("[name = materialName]:checkbox").each(function () {
			 			for(var k=0; k<materialDetailJson.length; k++){
		                    if ($(this).next().html()==materialDetailJson[k].material) {
		                        $(this).attr("checked", true);
		                        $(this).next().next().val(materialDetailJson[k].count);
		                    }
			 			}
	                });
			 		$("textarea[name='remark']").val(json.data.remark);
			 	}else{
			 		alert(json.errors[0].msg);
			 		
			 	}
			}
		});
	}
</script>
</body>
</html>