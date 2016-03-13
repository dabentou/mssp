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
      	  	<tr><td colspan="5"><span class="project_title">卖场绑定申请</span></td></tr>
      		  <tr>
      		  	<td rowspan="4">基本信息</td>
       			<td><span class="star">*</span>申请编号：</td>
  	 			<td>
  	 				<input  type="text"  class="input_style" name="ppn" value="${ppn }" readonly="readonly"/>
  	 				<input  type="hidden"  class="input_style" name="type" value="R03"/>
  	 			</td>
           		<td><span class="star">*</span>申请费用：</td>
     	 		<td><input  type="text"  class="input_style" name="applyPrice" onchange="checkApplyPrice()"/></td>
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
	      			</select>
     	 		</td>
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>申请日期：</td>
     	 		<td><input  type="text"  class="input_style" name="applyDate" value="${applyDate }" readonly="readonly"/></td>
     		 	<td><span class="star">*</span>申请主题：</td>
     	 		<td>
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
     	 			<input  type="text"  class="input_style" name="applyTheme3" value="卖场绑定" style="width:100px;" readonly/>
     	 		</td>
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
      			<td id="applyCont-td" rowspan="8">基本信息</td>
      			<td><span class="star">*</span> 卖场名称：</td>
      			<td>
      				<select  class="input_style" name="storeName">  
						<option value="">选择卖场</option>
						<c:forEach items="${storeList }" var="item">
							<option value="${item.irName }">${item.irName }</option>
						</c:forEach>
	      			</select>
      			</td>
      			<td><span class="star">* </span>卖场地址：</td>
      			<td><input  type="text"  class="input_style" name="storeAddress" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> [SS]金额：</td>
      			<td><input  type="text"  class="input_style" name="SSMoney" /></td>
      			<td><span class="star">*</span> [SS]店数：</td>
      			<td><input  type="text" id="iLevelInput"  class="input_style" name="SSStoreCount" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> [AOC]金额：</td>
      			<td><input  type="text"  class="input_style" name="AOCMoney" /></td>
      			<td><span class="star">*</span> [AOC]店数：</td>
      			<td><input  type="text"  class="input_style" name="AOCtoreCount" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> [ASUS]金额：</td>
      			<td><input  type="text"  class="input_style" name="ASUSMoney" /></td>
      			<td><span class="star">*</span> [ASUS]店数：</td>
      			<td><input  type="text"  class="input_style" name="ASUStoreCount" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> PH：</td>
      			<td><input  type="text"  class="input_style" name="PH" /></td>
      			<td><span class="star">*</span> 总投入：</td>
      			<td><input  type="text"  class="input_style" name="totalInvest" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> MMD投入：</td>
      			<td><input  type="text"  class="input_style" name="MMInvest" /></td>
      			<td><span class="star">*</span> 代理商投入：</td>
      			<td><input  type="text"  class="input_style" name="agentInvest" /></td>
      		</tr>
      		<tr>
      			<td>
      				<span class="star">*</span> 零售店：<br />
      				<a href='javascript:void(0);' onclick='addCont()' class='operate operate2'>添加</a>
      				</td>
      			<td>
      				<select  class="input_style" name="retailStoreName">  
						<option value="">选择零售店</option>
						<c:forEach items="${storeList }" var="item">
							<option value="${item.irName }">${item.irName }</option>
						</c:forEach>
	      			</select>
      			</td>
      			<td><span class="star">*</span> 年预估销量：</td>
      			<td><input  type="text"  class="input_style" name="estimateSum" /></td>
      		</tr>
      		<tr  id="estimateSum-tr">
      			<td>情况说明：</td>
      			<td colspan="4"><textarea cols="83" rows="5" name="remark"></textarea></td>
      		</tr>
			<tr>
      			<td colspan="6">
      				<input type="submit" onclick="return valiForm();" class="button button-lightblue" value="确认提交">
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
	var row_count=1;
	//添加店面
	addCont = function(){
		var dom = $("#estimateSum-tr");
		var html = "<tr>"+
			  			"<td>"+
						"<span class='star'>*</span> 零售店：<br />"+
					"<a href='javascript:void(0);' onclick='delCont(this)' class='operate operate2'>删除</a>"+
						"</td>"+
					"<td>"+
						"<select  class='input_style' name='retailStoreName'>"+
						"<option value=''>选择零售店</option>"+
						"<c:forEach items='${storeList }' var='item'><option value='${item.irName }'>${item.irName }</option></c:forEach>"+
						"</select>"+
					"</td>"+
					"<td><span class='star'>*</span> 年预估销量：</td>"+
					"<td><input  type='text'  class='input_style' name='estimateSum' /></td>"+
				"</tr>";
		dom.before(html);
		var rowspan = $("#applyCont-td").attr("rowspan");
		$("#applyCont-td").attr("rowspan",parseInt(rowspan)+1);
		row_count++;
	}
	
	//删除店面
	delCont = function(dom){
		$(dom).parent().parent().remove();
		var rowspan = $("#applyCont-td").attr("rowspan");
		$("#applyCont-td").attr("rowspan",parseInt(rowspan)-1);
		row_count--;
	}
	valiForm = function(){
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
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
		var storeName = $("select[name='storeName'] option:selected").val();
		if(storeName==''){
			alert('卖场名称不能为空！');
			$("select[name='storeName']").focus();
			return false;
		}
		var storeAddress = $("input[name='storeAddress']").val();
		if(storeAddress==''){
			alert('卖场地址不能为空！');
			$("input[name='storeAddress']").focus();
			return false;
		}
		
		var SSMoney = $("input[name='SSMoney']").val();
		if(SSMoney==''){
			alert('SS金额不能为空！');
			$("input[name='SSMoney']").focus();
			return false;
		}
		if(isNaN(parseInt(SSMoney))){
			alert("SS金额必须为数字！");
			$("input[name='SSMoney']").focus();
			return false;
		}
		
		var SSStoreCount = $("input[name='SSStoreCount']").val();
		if(SSStoreCount==''){
			alert('SS店数不能为空！');
			$("input[name='SSStoreCount']").focus();
			return false;
		}
		if(isNaN(parseInt(SSStoreCount))){
			alert("SS店数必须为数字！");
			$("input[name='SSStoreCount']").focus();
			return false;
		}
		
		var AOCMoney = $("input[name='AOCMoney']").val();
		if(AOCMoney==''){
			alert('AOC金额不能为空！');
			$("input[name='AOCMoney']").focus();
			return false;
		}
		if(isNaN(parseInt(AOCMoney))){
			alert("AOC金额必须为数字！");
			$("input[name='AOCMoney']").focus();
			return false;
		}
		
		var AOCtoreCount = $("input[name='AOCtoreCount']").val();
		if(AOCtoreCount==''){
			alert('AOC店数不能为空！');
			$("input[name='AOCtoreCount']").focus();
			return false;
		}
		if(isNaN(parseInt(AOCtoreCount))){
			alert("AOC店数必须为数字！");
			$("input[name='AOCtoreCount']").focus();
			return false;
		}
		
		var ASUSMoney = $("input[name='ASUSMoney']").val();
		if(ASUSMoney==''){
			alert('ASUS金额不能为空！');
			$("input[name='ASUSMoney']").focus();
			return false;
		}
		if(isNaN(parseInt(ASUSMoney))){
			alert("ASUS金额必须为数字！");
			$("input[name='ASUSMoney']").focus();
			return false;
		}
		
		var ASUStoreCount = $("input[name='ASUStoreCount']").val();
		if(ASUStoreCount==''){
			alert('ASUS店数不能为空！');
			$("input[name='ASUStoreCount']").focus();
			return false;
		}
		if(isNaN(parseInt(ASUStoreCount))){
			alert("ASUS店数必须为数字！");
			$("input[name='ASUStoreCount']").focus();
			return false;
		}
		
		var PH = $("input[name='PH']").val();
		if(PH==''){
			alert('PH不能为空！');
			$("input[name='PH']").focus();
			return false;
		}
		
		var totalInvest = $("input[name='totalInvest']").val();
		if(totalInvest==''){
			alert('总投入不能为空！');
			$("input[name='totalInvest']").focus();
			return false;
		}
		if(isNaN(parseInt(totalInvest))){
			alert("总投入必须为数字！");
			$("input[name='totalInvest']").focus();
			return false;
		}
		
		var MMInvest = $("input[name='MMInvest']").val();
		if(MMInvest==''){
			alert('MM投入不能为空！');
			$("input[name='MMInvest']").focus();
			return false;
		}
		var agentInvest = $("input[name='agentInvest']").val();
		if(agentInvest==''){
			alert('代理商投入不能为空！');
			$("input[name='agentInvest']").focus();
			return false;
		}
		var errorCount = 0;
	 	$("select[name='retailStoreName']").each(function(){
				var retailStoreName = $(this).find("option:selected").val();
				if(retailStoreName==''){
					alert("请选择零售店！");
					$(this).focus();
					errorCount++;
					return false;
				}
		});
		
		$("input[name='estimateSum']").each(function(){
			if(errorCount==0 && $(this).val()==''){
				alert("年预估销量不能为空！");
				$(this).focus();
				errorCount++;
				return false;
			}
			if(errorCount==0 && isNaN(parseInt($(this).val()))){
				alert("年预估销量必须为数字！");
				$(this).focus();
				return false;
			}
		});
		if(errorCount==0 && emailFile==''){
			alert('特批邮件不能为空！');
			$("#emailFile").focus();
			return false;
		}
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