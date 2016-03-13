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
      	<form id="form" class="form-horizontal"  action="${ROOT}/iretail/project/apply/post" enctype="multipart/form-data" method="post" >
      	  <table class="maintable table table-hover" style="width: 75%;">
      	  	<tr><td colspan="5"><span class="project_title">零售店装修申请</span></td></tr>
      	  	
      	  	<tr>
      	  			<td rowspan="6">基本信息</td>
      	  			<td><span class="star">* </span>装修类型：</td>
      	  			<td>
      					<select class="input_style" name="decorateLevel" onchange="isNew()" >
							<option value="翻新">翻新</option>
							<option value="新建">新建</option>
	      				</select>
      				</td>
      				<td  name="isNew"><span class="star">*</span>选择门店：</td>
     	 			<td  name="isNew">
     	 				<div class="multiple-select">
     	 				<select  class="selectpicker show-tick form-control" name="storeId" data-live-search="true">
         					 <c:forEach items="${store }" var="item">
         				 		<option value="${item.id }">${item.irName }</option>
         					 </c:forEach>
        				 </select>
						</div>
					</td>
      	  	</tr>
      	  	
      		  <tr>
      		  
       			<td><span class="star">*</span>申请编号：</td>
  	 			<td> 
  	 				<input  type="text"  class="input_style" name="ppn" value="${ppn }" readonly="readonly"/>
  	 				<input  type="hidden"  class="input_style" name="type" value="R01"/>
  	 			</td>
  	 			<td><span class="star">*</span>申请代理商：</td>
     	 		<td>
     	 			<%-- <input  type="text"  class="input_style" name="agnet" value="${commAgent.irName }" readonly="readonly"/> --%>
     	 			<select class="select" name="commAgentId" onchange="checkIsDesc()">  
	      				<c:forEach items="${commAgents}" var="item">
		            		<option value="${item.id}" >${item.irName }</option>
		            	</c:forEach>
	      			<select>
     	 		</td>
     		 </tr>
     		 <tr>
           		
     	 		<td><span class="star">*</span>所属省份：</td>
     	 		<td>
     	 			<select class="select" name="provinceId" onchange="checkIsDesc()">  
	      				<c:forEach items="${provinces}" var="item">
		            		<option value="${item.id}" >${item.province }</option>
		            	</c:forEach>
	      			<select>
     	 		</td>
     	 		<td><span class="star">*</span>申请日期：</td>
     	 		<td><input  type="text"  class="input_style" name="applyDate" value="${applyDate }" readonly="readonly"/></td>
     		 </tr>
     		 <tr>
           		
     		 	<td><span class="star">*</span>申请主题：</td>
     	 		<td><input  type="text"  class="input_style" name="applyTheme1" value="道具申请" readonly/></td>
           		<td><span class="star">*</span>申请费用：</td>
     	 		<td><input  type="text"  class="input_style" name="applyPrice" /></td>
     	 		<!-- <td><span class="star">*</span>供应商：</td>
     	 		<td><input  type="text"  class="input_style" name="provider" /></td> -->
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
      				<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      				<!-- <input type="hidden" id="email_area" name="approveEmail"/> -->
      				<textarea style='display: none;'  id="email_area" name="approveEmail"></textarea>
      				<div id="email_view">
                    </div>
      			</td>
      		</tr>
      		<tr>
      			<td rowspan="12">申请内容</td>
      			<td><span class="star">* </span>城市名称：</td>
      			<td>
	      			<select class="select" name="cityName" onchange="checkIsDesc()">  
	      				<c:forEach items="${provinces}" var="item">
		            		<option value="${item.id}" >${item.province }</option>
		            	</c:forEach>
	      			<select>
      			</td>
      			<td name="newDisPlay" style="display:none;"><span class="star">*</span> 零售店名称：</td>
      			<td name="newDisPlay" style="display:none;"><input  type="text"  class="input_style" name="iName" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> 卖场名称：</td>
      			<td>
      				<!-- <input  type="text"  class="input_style" name="shopPlace" /> -->
      				<select  class="input_style" name="shopPlace">  
						<option value="">选择卖场</option>
						<c:forEach items="${storeList }" var="item">
							<option value="${item.marketName }">${item.marketName }</option>
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
      		   <td> 店面积：</td>
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
<!--       		<tr>
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
      				<textarea class="col-md-6 form-control textarea" rows="3" name="chargesDetailRemark"></textarea>
      				<input type="hidden" id="chargesDetail_area" name="chargesDetail"/>
      				<div id="chargesDetail_view">
                    </div>
      			</td>
      		</tr> -->
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
</body>
<script type="text/javascript">
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
	
	//校验所选省份是否可以装修
	checkIsDesc = function(){
		var provinceId = $("select[name='provinceId'] option:selected").val();
		var flag=true;
		$.ajax({
			type : "POST",
			url : "${ROOT}/iretail/project/descsetting",
			data : {provinceId : provinceId},
			dataType : "json",
			async: false,
			success : function(json){
			 	if(json.status == "OK"){
			 	}else{
			 		flag = false;
			 		alert(json.errors[0].msg);
			 		$("select[name='provinceId']").focus();
			 	}
			}
		});
		return flag;
	}
	
	valiForm = function(){
		var agent = $("select[name='commAgentId']").val();
		var applyTheme = $("input[name='applyTheme']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		var provider = $("input[name='provider']").val();
		var iName = $("input[name='iName']").val();
		var shopPlace = $("select[name='shopPlace'] option:selected").val();
		var iLevel = $("input[name='iLevel']").val();
		var iLocation = $("input[name='iLocation']").val();
		var yqVolume = $("input[name='yqVolume']").val();
// 		var iAcreage = $("input[name='iAcreage']").val();
 		var lsVolume = $("input[name='lsVolume']").val();
		var iPrincipal = $("input[name='iPrincipal']").val();
		var phone = $("input[name='phone']").val();
		var lsRate = $("input[name='lsRate']").val();
		var iTargetVolume = $("input[name='iTargetVolume']").val();
		var iRealPic = $("textarea[name='iRealPic']").val();
		var iResultPic = $("textarea[name='iResultPic']").val();
		var emailFile = $("#email_area").val();
		var chargesDetailImg = $("#chargesDetail_area").val();
		if(!checkIsDesc()){
			return false;
		} 
		if(agent == null || agent==''){
			alert("代理商不能为空");
			$("select[name='commAgentId']").focus();
			return false;
		}
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
		if($("select[name='decorateLevel']").val() == '新建' && iName==''){
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
/* 		if(iAcreage==''){
			alert('店面积不能为空！');
			$("input[name='iAcreage']").focus();
			return false;
		} */
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
/* 		if(iRealPic==''){
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
		} */
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
	
	isNew = function(){
		var type = $("select[name='decorateLevel']").val();
		if(type == '翻新'){
			$("td[name='isNew']").show();
			$("td[name='newDisPlay']").hide();
			
		}else{
			$("td[name='isNew']").hide();
			$("td[name='newDisPlay']").show();
		}
	}
	
</script>
</html>