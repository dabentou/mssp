<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
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
      	  <table class="maintable table table-hover" style="width: 85%;">
      	  	<tr><td colspan="5"><span class="project_title">宣传品申请</span></td></tr>
      		<tr>
      			<td rowspan="6">基本信息</td>
       			<td><span class="star">*</span>申请编号：</td>
  	 			<td>
  	 				<input  type="text"  class="input_style" name="ppn" value="${ppn }" readonly="readonly"/>
  	 				<input  type="hidden"  class="input_style" name="type" value="R04"/>
  	 			</td>
  	 			<!-- <td><span class="star">*</span>核销编号：</td>
     	 		<td><input  type="text"  class="input_style" name="cpn" /></td> -->
      		</tr>
     		 <tr>
           		<td><span class="star">*</span>申请代理商：</td>
     	 		<td>
     	 			<input  type="text"  class="input_style" name="agnet" value="${commAgent.irName }" readonly="readonly"/>
     	 		</td>
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
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>申请日期：</td>
     	 		<td><input  type="text"  class="input_style" name="applyDate" value="${applyDate }" readonly="readonly"/></td>
     		 	<td><span class="star">*</span>申请主题：</td>
     	 		<td><!-- <input  type="text"  class="input_style" name="applyTheme" /> -->  
     	 			<select class="select" name="applyTheme1" style="width:100px;">  
	      				<c:forEach items="${provinces}" var="item">
		            		<option value="${item.province}" >${item.province }</option>
		            	</c:forEach>
	      			<select>
     	 			<input  type="text" class="form-control text datetimepicker"  style="width: 100px;height: 21px;"   name="applyTheme2" id="pBidTime"/>
     	 			<input  type="text"  class="input_style" name="applyTheme3" value="宣传品" style="width:100px;" readonly/>
     	 		</td>
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>申请费用：</td>
     	 		<td><input  type="text"  class="input_style" name="applyPrice" onchange="checkApplyPrice()" /></td>
     	 		<td><span class="star">*</span>供应商：</td>
     	 		<td><input  type="text"  class="input_style" name="provider" /></td>
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>有效期：</td>
     	 		<td><input type="text" class="input_style" name="beginTime" readonly="readonly" value="${applyDate }"/></td>
     	 		<td>至</td>
     	 		<td><input type="text" class="input_style" name="endTime" readonly="readonly" value="${endTime }"/></td>
     		 </tr>
     		 <tr>
      			<td><span class="star">*</span>特批邮件：</td>
      			<td colspan="4">
      				<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','one')" accept="image/gif,image/jpeg,image/png"/>
      				<!-- <input type="hidden" id="email_area" name="approveEmail"/> -->
      				<textarea style='display: none;'  id="email_area" name="approveEmail"></textarea>
      				<div id="email_view">
                    </div>
      			</td>
      		</tr>

      		<tr>
      			<td rowspan="5">
      				申请内容
      			</td>
      			<td><span class="star">* </span>宣传品类型：</td>
      			<td>
      				<select class="input_style" name="propagandaLevel">
						<option value="单页">单页</option>
						<option value="多页">多页</option>
	      			</select>
      			</td>
      			<td><span class="star">*</span> 宣传品尺寸：</td>
      			<td><input  type="text"  class="input_style" name="propagandaSize" />(横cm*竖cm)</td>
      		</tr>
     		 <tr>
           		<td><span class="star">*</span>宣传品数量：</td>
     	 		<td>
     	 			<input  type="text" onchange="getMoneySum('countInput','priceInput','moneySumInp')" id="countInput" class="input_style" name="propagandaCount"/>
     	 		</td>
     	 		<td><span class="star">*</span>制作单价：</td>
     	 		<td>
     	 			￥<input  type="text" onchange="getMoneySum('countInput','priceInput','moneySumInp')" id="priceInput"  class="input_style" name="price"/>
     	 		</td>
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>费用总计：</td>
     	 		<td><input  type="text" id="moneySumInp" class="input_style" name="moneySum" readonly="readonly"/></td>
     		 	<td><span class="star">*</span>宣传品主题：</td>
     	 		<td><input  type="text"  class="input_style" name="propagandaTheme" /></td>
     		 </tr>
      		<tr>
      			<td><span class="star">*</span>效果图：</br>(重复上传可以追加)</td>
      			<td colspan="4">
      				<input type="file" id="iResultPicImg" onchange="uploadImg(this,'iResultPicImg','iResultPic_view','iResultPic-area')" name="imgFile" accept="image/gif,image/jpeg,image/png" />
      				<textarea id="iResultPic-area" style='display: none;' name="iResultPic"></textarea>
      				<div id="iResultPic_view">
                    </div>
      			</td>
      		</tr>
     		 <tr>
      			<td>情况说明：</td>
      			<td colspan="4"><textarea cols="83" rows="5"   name="remark" ></textarea></td>
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
<script type="text/javascript">
$('.datetimepicker').datetimepicker({
    format: 'yyyy-mm',
    autoclose: true,
    minView:2
});
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
					console.info(url);
					console.info(inputId);
					if(type=='one'){
						$("#"+areaId).val(url);
						uv.html("<img title='重复上传可替换' width=100 src='" + url + "'> ");
					}else{
						$("#"+areaId).append(url+",");
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
		var urlCont = $("#"+areaId).html().replace(imgUrl+",","");
		$("#"+areaId).html(urlCont)
		$(dom).remove();
	}

	var row_count = 1;
	//添加申请内容
	addContent = function(dom){
		var inputId = "iResultPicImg"+row_count;
		var viewId = "iResultPic_view"+row_count;
		var areaId = "iResultPic-area"+row_count;
		var countId = "countInput"+row_count;
		var priceId = "priceInput"+row_count;
		var sumMoneyId = "moneySumInp"+row_count;
		var html = "<tr>"+
		  			"<td rowspan='5'>"+
					"申请内容<br/>"+
					"<a href='javascript:void(0);' onclick='delContent(this)' class='operate operate2'>删除</a>"+
				"</td>"+
				"<td><span class='star'>* </span>宣传品类型：</td>"+
				"<td>"+
					"<select class='input_style' name='propagandaLevel'>"+
					"<option value='单页'>单页</option>"+
					"<option value='多页'>多页</option>"+
					"</select>"+
				"</td>"+
				"<td><span class='star'>*</span> 宣传品尺寸：</td>"+
				"<td><input  type='text'  class='input_style' name='propagandaSize' />(横cm*竖cm)</td>"+
			"</tr>"+
			 "<tr>"+
				"<td><span class='star'>*</span>宣传品数量：</td>"+
				"<td>"+
					"<input  type='text' id='"+countId+"' onchange='getMoneySum(\""+countId+"\",\""+priceId+"\",\""+sumMoneyId+"\")' class='input_style' name='propagandaCount'/>"+
				"</td>"+
				"<td><span class='star'>*</span>制作单价：</td>"+
				"<td>"+
					"￥<input  type='text' id='"+priceId+"'  onchange='getMoneySum(\""+countId+"\",\""+priceId+"\",\""+sumMoneyId+"\")'  class='input_style' name='price'/>"+
				"</td>"+
			 "</tr>"+
			 "<tr>"+
				"<td><span class='star'>*</span>费用总计：</td>"+
				"<td><input  type='text'  class='input_style' id='"+sumMoneyId+"' name='moneySum' readonly='readonly'/></td>"+
			 	"<td><span class='star'>*</span>申请主题：</td>"+
				"<td><input  type='text'  class='input_style' name='propagandaTheme' /></td>"+
			 "</tr>"+
			"<tr>"+
				"<td><span class='star'>*</span>效果图：</br>(重复上传可以追加)</td>"+
				"<td colspan='4'>"+
					"<input type='file' id='"+inputId+"' onchange='uploadImg(this,\"iResultPicImg"+row_count+"\",\"iResultPic_view"+row_count+"\",\"iResultPic-area"+row_count+"\")' name='imgFile' accept='image/gif,image/jpeg,image/png' />"+
					"<textarea id='"+areaId+"' style='display: none;' name='iResultPic'></textarea>"+
					"<div id='"+viewId+"'>"+
		        "</div>"+
				"</td>"+
			"</tr>"+
			 "<tr>"+
				"<td>情况说明：</td>"+
				"<td colspan='4'><textarea cols='83' rows='5'   name='remark' ></textarea></td>"+
			"</tr>";
			row_count++;
		$(dom).parent().parent().before(html);
	}
	
	//删除申请内容
	delContent = function(dom){
		for(var i=0; i<4; i++){
			$(dom).parent().parent().next().remove();
		}
		$(dom).parent().parent().remove();
		row_count--;
	}
	
	//计算总费用
	getMoneySum = function(countId,priceId,sumMoneyId){
		var count = $("#"+countId).val();
		var price = $("#"+priceId).val();parseInt();
		if(count==''){
			return false;
		}
		if(price==''){
			return false;
		}
		if(isNaN(parseInt(count))){
			alert("宣传品数量必须为数字！");
			$("#"+countId).focus();
			return false;
		}
		if(price!='' && isNaN(parseInt(price))){
			alert("制作单价必须为数字！");
			$("#"+priceId).focus();
			return false;
		}
		$("#"+sumMoneyId).val(count*price);
	}

	valiForm = function(){
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		var provider = $("input[name='provider']").val();
		var emailFile = $("#email_area").val();
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
		var errorCount = 0;
/* 		$("select[name='propagandaLevel']").each(function(){
			$(this).find().find("option:selected").val();
		}); */
		$("input[name='propagandaSize']").each(function(){
			if($(this).val()==''){
				alert("宣传品尺寸不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("input[name='propagandaCount']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("宣传品数量不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
			if(errorCount==0 && isNaN(parseInt($(this).val()))){
				alert("宣传品数量必须为数字！");
				$(this).focus();
				return false;
			}
		});
		
		$("input[name='price']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("制作单价不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
			if(errorCount==0 && isNaN(parseInt($(this).val()))){
				alert("制作单价必须为数字！");
				$(this).focus();
				return false;
			}
		});
		
		$("input[name='propagandaTheme']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("宣传品主题不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
		});
		
		$("textarea[name='iResultPic']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("效果图不能为空！");
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
</html>