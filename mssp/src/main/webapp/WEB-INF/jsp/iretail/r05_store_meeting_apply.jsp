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
      	  	<tr><td colspan="5"><span class="project_title">零售店会议申请</span></td></tr>
      		  <tr>
      		  	<td rowspan="6">基本信息</td>
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
       			<td><span class="star">*</span>申请编号：</td>
  	 			<td>
  	 				<input  type="text"  class="input_style" name="ppn" value="${ppn }" readonly="readonly"/>
  	 				<input  type="hidden"  class="input_style" name="type" value="R05"/>
  	 			</td>
  	 			
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
     	 			<input  type="text"  class="input_style" name="applyTheme4" value="零售商会议" style="width:100px;" readonly/>
     	 		</td>
     	 		<td><span class="star">*</span>申请费用：</td>
     	 		<td><input  type="text"  class="input_style" name="applyPrice" onchange="checkApplyPrice()"/></td>
     		 </tr>
     		 <tr>
           		<td><span class="star">*</span>有效期：</td>
     	 		<td><input type="text" class="input_style" name="beginTime" readonly="readonly" value="${applyDate }"/></td>
     	 		<td>至</td>
     	 		<td><input type="text" class="input_style" name="endTime" readonly="readonly" value="${endTime }"/></td>
     		 </tr>
     		 <tr>
     		 	<td><span class="star">*</span>申请代理商：</td>
     	 		<td>
     	 			<input  type="text"  class="input_style" name="agnet" value="${commAgent.irName }" readonly="readonly"/>
     	 		</td>
     		 	<td></td>
     		 	<td></td>
     		 </tr>
     		 <tr>
      			<td><span class="star">*</span>批复邮件：</td>
      			<td colspan="4">
      				<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','one')" accept="image/gif,image/jpeg,image/png"/>
      				<!-- <input type="hidden" id="email_area" name="approveEmail"/> -->
      				<textarea style='display: none;'  id="email_area" name="approveEmail"></textarea>
      				<div id="email_view">
                    </div>
      			</td>
      		</tr>
      		<tr>
      			<td rowspan="4">会议流程：</td>
      			<td><span class="star">*</span>会议名称：</td><td><input class="input_style" type="text"  name="meetingName" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>会议地点：</td>
      			<td><input class="input_style" type="text"  name="meetingPlace" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>会议时间：</td>
      			<td><input class="input_style" type="text"  name="meetingTime" /></td>
      		</tr>
      		<tr>
      			<td>行程安排：</td>
      			<td colspan="3">
      				<table>
      					<thead>
      						<tr>
      							<td>时间</td><td>内容</td><td>培训人</td><td>操作</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<td><input class="input_style" type="text"  name="applyTime" style="width:170px"/></td>
      							<td><input class="input_style" type="text"  name="applyContent" style="width:170px"/></td>
      							<td><input class="input_style" type="text"  name="applyPerson" style="width:170px"/></td>
      							<td><a href="javascript:void(0);" onclick="addContentMeeting(this)" class="operate operate2">增加</a></td>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		
      		
      		<tr>
      			<td rowspan="3">旅游行程：</td>
      			<td><span class="star">*</span>目的地：</td><td><input class="input_style" type="text"  name="travelPosition" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>参与人数：</td>
      			<td><input class="input_style" type="text"  name="travelTotalPeople" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>旅游行程：</td>
      			<td><input class="input_style" type="text"  name="travelTrip" /></td>
      		</tr>
      		
      		
      		<tr>
      			<td >费用明细：</td>
      			<td colspan="4">
      				<table>
      					<thead>
      						<tr>
      							<td>会议室（元）</td><td>中餐（元）</td><td>晚餐（元）</td><td>其他（元）</td><td>总金额</td><td>操作</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<td><input class="input_style" type="text"  name="costMeetRoom"  style="width:140px"/></td>
      							<td><input class="input_style" type="text"  name="costLunch" style="width:140px" /></td>
      							<td><input class="input_style" type="text"  name="costDinner" style="width:140px" /></td>
      							<td><input class="input_style" type="text"  name="costOther" style="width:140px" /></td>
      							<td><input class="input_style" type="text"  name="costTotal" style="width:140px" /></td>
      							<td>
      								<a href="javascript:void(0);" onclick="addContentCost(this)" class="operate operate2">增加</a>
      							</td>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td rowspan="12">培训总结：</td>
      			<td><b>一.培训细则</b></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>培训时间：</td>
      			<td><input class="input_style" type="text"  name="trainTime" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>培训地点：</td>
      			<td><input class="input_style" type="text"  name="trainPlace" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>培训内容：</td>
      			<td><input class="input_style" type="text"  name="trainContent" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>培训对象：</td>
      			<td><input class="input_style" type="text"  name="trainObject" /></td>
      		</tr>
      		<tr>
      			<td><b>二.总体评分</b></td>
      		</tr>
      		<tr>
	      		<td colspan="4">
	      				<table style="width:95%">
	      					<thead>
	      						<tr>
	      							<td>评价内容</td><td>学员评价(满分100份)</td><td>总部评价(满分100分)</td>
	      						</tr>
	      					</thead>
	      					<tbody>
	      						<tr>
	      							<td>1. 现场环境是否舒适、样机和宣传品是<br />否到位、时间场地安排是否合适</td>
	      							<td><textarea name="studentEnvironment" style="width:180px;height:60px;"></textarea> </td>
	      							<td><textarea name="studentEnvironmentScore" style="width:180px;height:60px;"></textarea></td>
	      						</tr>
	      						<tr>
	      							<td>2. 课件内容是否清晰、新产品内容是<br />否完整、课件内容是否有助于销售</td>
	      							<td><textarea name="studentCourse" style="width:180px;height:60px;"></textarea> </td>
	      							<td><textarea name="studentCourseScore" style="width:180px;height:60px;"></textarea></td>
	      						</tr>
	      						<tr>
	      							<td>3. 讲师讲解是否清晰、是否能激发学习<br />兴趣、是否能互动并调动现场气氛</td>
	      							<td><textarea name="studentTeacher" style="width:180px;height:60px;"></textarea> </td>
	      							<td><textarea name="studentTeacherScore" style="width:180px;height:60px;"></textarea></td>
	      						</tr>
	      					</tbody>
	      				</table>
	      			</td>
      		</tr>
      		<tr>
      			<td><b>三.培训总结</b></td>
      		</tr>
      		<tr>
      			<td colspan="4">优点</td>
      		</tr>
      		<tr>
      			<td colspan="4">
      				<textarea name="advantage" style="width:800px;height:80px;"></textarea>
      			</td>
      		</tr>
      		<tr>
      			<td colspan="4">
      				<table style="width:95%">
      					<thead>
      						<tr>
      							<td>缺点</td><td>改善</td><td>负责人</td><td>时间点</td><td>操作</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<td><input class="input_style" type="text"  name="shortcoming"  style="width:170px"/></td>
      							<td><input class="input_style" type="text"  name="improving" style="width:170px" /></td>
      							<td><input class="input_style" type="text"  name="chargePerson" style="width:170px" /></td>
      							<td><input class="input_style" type="text"  name="timePlace" style="width:170px" /></td>
      							<td><a href="javascript:void(0);" onclick="addSummary(this)" class="operate operate2">增加</a></td>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td colspan="4">
      			<table style="width:95%">
      					<thead>
      						<tr>
      							<td>学员提问</td><td>回复</td><td>操作</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<td><input class="input_style" type="text"  name="studentAsk"  style="width:240px"/></td>
      							<td><input class="input_style" type="text"  name="reply" style="width:240px" /></td>
      							<td><a href="javascript:void(0);" onclick="addQuestion(this)" class="operate operate2">增加</a></td>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td>情况说明：</td>
      			<td colspan="4"><textarea class="col-md-6 form-control textarea" rows="3"   name="remark" ></textarea></td>
      		</tr>
      		<tr>
      			<td>其他：</td>
      			<td colspan="4"><textarea class="col-md-6 form-control textarea" rows="3" name="others"></textarea></td>
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

	valiForm = function(){
		var applyTheme2 = $("input[name='applyTheme2']").val();
		var applyPrice = $("input[name='applyPrice']").val();
		var emailFile = $("#email_area").val();
		var meetingName = $("input[name='meetingName']").val();
		var meetingPlace = $("input[name='meetingPlace']").val();
		var meetingTime = $("input[name='meetingTime']").val();
		var travelPosition = $("input[name='travelPosition']").val();
		var travelTotalPeople = $("input[name='travelTotalPeople']").val();
		var travelTrip = $("input[name='travelTrip']").val();
		
		var meetingPerNum = $("input[name='meetingPerNum']").val();
		var meetingProcess = $("textarea[name='meetingProcess']").val();
		var travelProcess = $("textarea[name='travelProcess']").val();
		var chargesDetail = $("textarea[name='chargesDetail']").val();
		if(applyTheme2==''){
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
		if(emailFile==''){
			alert('批复邮件不能为空！');
			$("#emailFile").focus();
			return false;
		}
		if(meetingName==''){
			alert('会议名称不能为空！');
			$("input[name='meetingName']").focus();
			return false;
		}
		if(meetingPlace==''){
			alert('会议地点不能为空！');
			$("input[name='meetingPlace']").focus();
			return false;
		}
		if(meetingTime==''){
			alert('会议时间不能为空！');
			$("input[name='meetingTime']").focus();
			return false;
		}
		if(travelPosition==''){
			alert('目的地不能为空！');
			$("input[name='travelPosition']").focus();
			return false;
		}
		if(travelTotalPeople==''){
			alert('参与人数不能为空！');
			$("input[name='travelTotalPeople']").focus();
			return false;
		}
		if(isNaN(parseInt(travelTotalPeople))){
			alert("参与人数必须为数字！");
			$("input[name='travelTotalPeople']").focus();
			return false;
		}
		if(travelTrip==''){
			alert('旅游行程不能为空！');
			$("input[name='travelTrip']").focus();
			return false;
		}
		if(!checkApplyPrice()){
			return false;
		}
		/* if(meetingPerNum==''){
			alert('会议人数不能为空！');
			$("input[name='meetingPerNum']").focus();
			return false;
		}
		if(isNaN(parseInt(meetingPerNum))){
			alert("会议人数必须为数字！");
			$("input[name='meetingPerNum']").focus();
			return false;
		}
		
		if(meetingProcess==''){
			alert('会议流程不能为空！');
			$("textarea[name='meetingProcess']").focus();
			return false;
		}
		if(travelProcess==''){
			alert('旅游行程不能为空！');
			$("textarea[name='travelProcess']").focus();
			return false;
		}
		if(chargesDetail==''){
			alert('费用明细不能为空！');
			$("textarea[name='chargesDetail']").focus();
			return false;
		} */	
	}
	
	addContentCost = function(dom){
		var html ='<tr>'+
				'<td><input class="input_style" type="text"  name="costMeetRoom"  style="width:140px"/></td>'+
				'<td><input class="input_style" type="text"  name="costLunch" style="width:140px" /></td>'+
				'<td><input class="input_style" type="text"  name="costDinner" style="width:140px" /></td>'+
				'<td><input class="input_style" type="text"  name="costOther" style="width:140px" /></td>'+
				'<td><input class="input_style" type="text"  name="costTotal" style="width:140px" /></td>'+
				'<td>'+
					'<a href="javascript:void(0);" onclick="addContentCost(this)" class="operate operate2">增加</a>'+
					'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
				'</td>'+
			'</tr>';
		
		$(dom).parent().parent().after(html);
	}
	
	addContentMeeting = function(dom){
		var html ='<tr>'+
				'<td><input class="input_style" type="text"  name="applyTime"  style="width:170px"/></td>'+
				'<td><input class="input_style" type="text"  name="applyContent" style="width:170px" /></td>'+
				'<td><input class="input_style" type="text"  name="applyPerson" style="width:170px" /></td>'+
				'<td>'+
					'<a href="javascript:void(0);" onclick="addContentMeeting(this)" class="operate operate2">增加</a>'+
					'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
				'</td>'+
			'</tr>';
		
		$(dom).parent().parent().after(html);
	}
	
	addSummary = function(dom){
		var html ='<tr>'+
				'<td><input class="input_style" type="text"  name="shortcoming"  style="width:170px"/></td>'+
				'<td><input class="input_style" type="text"  name="improving" style="width:170px" /></td>'+
				'<td><input class="input_style" type="text"  name="chargePerson" style="width:170px" /></td>'+
				'<td><input class="input_style" type="text"  name="timePlace" style="width:170px" /></td>'+
				'<td>'+
					'<a href="javascript:void(0);" onclick="addSummary(this)" class="operate operate2">增加</a>'+
					'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
				'</td>'+
			'</tr>';
		
		$(dom).parent().parent().after(html);
	}
	
	addQuestion = function(dom){
		var html ='<tr>'+
				'<td><input class="input_style" type="text"  name="studentAsk"  style="width:240px"/></td>'+
				'<td><input class="input_style" type="text"  name="reply" style="width:240px" /></td>'+
				'<td>'+
					'<a href="javascript:void(0);" onclick="addQuestion(this)" class="operate operate2">增加</a>'+
					'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
				'</td>'+
			'</tr>';
		
		$(dom).parent().parent().after(html);
	}
	
	delContent = function(dom){
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
	})
</script>
</html>
