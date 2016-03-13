<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>查看项目</title>
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
		<div class="content"   align="center">
		 <%@include file="/WEB-INF/include/iretail_menu.jsp" %>
		 	<form id="myFrom" class="form-horizontal"  action="${ROOT}/iretail/project/flow"  method="post">
		 		<input type="hidden" name="isPass"   id="isPass" />
	      		<input type="hidden" name="flowType"   id="flowType"  value="${flowType }"/>
	      		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
		 		<table class="maintable table table-hover"  style="width: 90%;" border="1">
      	<tr><td colspan="6"><span class="project_title">R05零售店会议申请信息</span></td></tr>
      		<tr>
      			<td rowspan="6">基本信息</td>
      			<td> 申请编号：</td>
      			<td>${project.ppn }</td>
      			<td> 申请代理商：</td>
      			<td>${project.applyUser.irName }</td>
      		</tr>
      		<tr>
      			
      			<td> 所属省份：</td>
      			<td>${project.province.province }</td> 
      			<td> 申请日期：</td>
      			<td><fmt:formatDate value="${project.applyDate}" pattern="yyyy-MM-dd" /></td>
      		</tr>
      		<tr>
      			
      			<td> 申请主题：</td>
      			<td>${project.applyTheme }</td> 
      			<td> 申请费用：</td>
      			<td>${project.applyPrice}</td>
      		</tr>
      		<tr>
      			<td> 有效期：</td>
      			<td><fmt:formatDate value="${project.beginTime}" pattern="yyyy-MM-dd" /></td>
      			<td> 至：</td>
      			<td><fmt:formatDate value="${project.endTime}" pattern="yyyy-MM-dd" /></td>
      		</tr>
      		<tr>
				<td>门店名称：</td>    
				<td>${project.store.irName }</td>
				<td></td><td></td>  		
      		</tr>
      		<tr name = "tr2">
      			<td>批复邮件：</td>
      			<td colspan="4">
      				<%-- <img src ="${project.approveEmail}" height="200px" width="300px"></img> --%>
      				<c:forEach items="${fn:split(project.approveEmail,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
					</c:forEach>
      			</td>
      		</tr>
      		
      		<tr>
      			<td rowspan="4">会议流程：</td>
      			<td>会议名称：</td><td colspan="4">${applyContent.meetingName}</td>
      		</tr>
      		<tr>
      			<td>会议地点：</td>
      			<td colspan="4">${applyContent.meetingPlace}</td>
      		</tr>
      		<tr>
      			<td>会议时间：</td>
      			<td colspan="4">${applyContent.meetingTime}</td>
      		</tr>
      		<tr>
      			<td>行程安排：</td>
      			<td colspan="3">
      				<table width=100%>
      					<thead>
      						<tr>
      							<td>时间</td><td>内容</td><td>培训人</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<c:forEach items="${meeting}" var="item" >
      								<tr><td>${item.applyTime}</td>
      								<td>${item.applyContent}</td>
      								<td>${item.applyPerson}</td></tr>
      							</c:forEach>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		
      		
      		<tr>
      			<td rowspan="3">旅游行程：</td>
      			<td>目的地：</td><td colspan="4">${applyContent.travelPosition}</td>
      		</tr>
      		<tr>
      			<td>参与人数：</td>
      			<td colspan="4">${applyContent.travelTotalPeople}</td>
      		</tr>
      		<tr>
      			<td>旅游行程：</td>
      			<td colspan="4">${applyContent.travelTrip}</td>
      		</tr>
      		
      		
      		<tr>
      			<td >费用明细：</td>
      			<td colspan="4">
      				<table width=100%>
      					<thead>
      						<tr>
      							<td>会议室（元）</td><td>中餐（元）</td><td>晚餐（元）</td><td>其他（元）</td><td>总金额</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<c:forEach items="${costing}" var="item" >
      								<tr><td>${item.costMeetRoom}</td>
      								<td>${item.costLunch}</td>
      								<td>${item.costDinner}</td>
      								<td>${item.costOther}</td>
      								<td>${item.costTotal}</td></tr>
      							</c:forEach>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td rowspan="12">会议流程：</td>
      			<td><b>一.培训细则</b></td>
      		</tr>
      		<tr>
      			<td>培训时间：</td>
      			<td colspan="4">${applyContent.trainTime}</td>
      		</tr>
      		<tr>
      			<td>培训地点：</td>
      			<td colspan="4">${applyContent.trainPlace}</td>
      		</tr>
      		<tr>
      			<td>培训内容：</td>
      			<td colspan="4">${applyContent.trainContent}</td>
      		</tr>
      		<tr>
      			<td>培训对象：</td>
      			<td colspan="4">${applyContent.trainObject}</td>
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
	      							<td><textarea name="studentEnvironment" style="width:180px;height:60px;" readonly>${applyContent.studentEnvironment}</textarea> </td>
	      							<td><textarea name="studentEnvironmentScore" style="width:180px;height:60px;" readonly>${applyContent.studentEnvironmentScore}</textarea></td>
	      						</tr>
	      						<tr>
	      							<td>2. 课件内容是否清晰、新产品内容是<br />否完整、课件内容是否有助于销售</td>
	      							<td><textarea name="studentCourse" style="width:180px;height:60px;" readonly>${applyContent.studentCourse}</textarea> </td>
	      							<td><textarea name="studentCourseScore" style="width:180px;height:60px;" readonly>${applyContent.studentCourseScore}</textarea></td>
	      						</tr>
	      						<tr>
	      							<td>3. 讲师讲解是否清晰、是否能激发学习<br />兴趣、是否能互动并调动现场气氛</td>
	      							<td><textarea name="studentTeacher" style="width:180px;height:60px;" readonly>${applyContent.studentTeacher}</textarea> </td>
	      							<td><textarea name="studentTeacherScore" style="width:180px;height:60px;" readonly>${applyContent.studentTeacherScore}</textarea></td>
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
      				<textarea name="advantage" style="width:800px;height:80px;" readonly>${applyContent.advantage}</textarea>
      			</td>
      		</tr>
      		<tr>
      			<td colspan="4">
      				<table style="width:95%">
      					<thead>
      						<tr>
      							<td>缺点</td><td>改善</td><td>负责人</td><td>时间点</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<c:forEach items="${disadvantage}" var="item" >
      								<tr><td>${item.shortcoming}</td>
      								<td>${item.improving}</td>
      								<td>${item.chargePerson}</td>
      								<td>${item.timePlace}</td></tr>
      							</c:forEach>
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
      							<td>学员提问</td><td>回复</td>
      						</tr>
      					</thead>
      					<tbody>
      						<tr>
      							<c:forEach items="${questionAndAnswer}" var="item" >
      								<tr><td>${item.studentAsk}</td>
      								<td>${item.reply}</td></tr>
      							</c:forEach>
      						</tr>
      					</tbody>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td>情况说明：</td>
      			<td colspan="5" >${project.remark}</td>
      		</tr>
      		<tr>
      			<td>其他：</td>
      			<td colspan="5" >${applyContent.others}</td>
      		</tr>
			<!-- <tr>
      			<td colspan="6"><input type="submit" onclick="return valiForm();" class="button button-lightblue" value="确认提交"></td>
      		</tr> -->
      	</table>
      	<c:if test="${check }">
       		<table  class="maintable table table-hover"  style="width: 90%;" border="1">
       			<tr>
	      			<td colspan="6"><span class="project_title">核销资料</span></td>
	      		</tr>
	       		<tr>
		      			<td colspan="3">签到表：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(signUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach> 
		      			</td>
		      	</tr> 
		      	<tr>
		      			<td colspan="3">现场培训照片：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(sceneUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach>
		      			</td>
		      	</tr> 
		      	<tr>
		      			<td colspan="3">培训总结：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(summaryUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach>
		      			</td>
		      	</tr> 
		      	<tr>
		      			<td colspan="3">水单：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(waterUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach>
		      			</td>
		      	</tr> 
		      	<tr>
		      			<td colspan="3">发票：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(invoiceUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach>
		      			</td>
		      	</tr> 
		      	<tr>
		      			<td colspan="3">发票真伪截图：</td>
		      			<td colspan="3">
		      				<c:forEach items="${fn:split(invoiceRealUrl,',')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="20%"></img>
							</c:forEach>
		      			</td>
		      	</tr> 
		      	
		      	<tr>
		      		<td colspan="6">
		      		<table style="width:90%;">
						<thead>
							<tr><td>发票代码</td><td>发票号码</td><td>发票金额</td></tr>
						</thead>
						<tbody>
							<c:forEach items="${invoiceInformation}" var="item">
							<tr>
								<td>${item.invoiceCode }</td>
								<td>${item.invoiceNumber }</td>
								<td>${item.invoiceValue }</td>
							</tr>
							</c:forEach>
						</tbody>      		
		      		</table>
		      		</td>
		      	</tr>
		      	
		      	
	      </table>
       </c:if>
      	
      	<table  class="maintable table table-hover"  style="width:90%;">
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
        <form id="uploadForm" action="${ROOT}/iretail/project/uploadR05" enctype="multipart/form-data" method="post" >
       	 <input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        	<c:if test="${isUploadVerificImg }">
		      	<table  class="maintable table table-hover"  style="width: 90%;" border="1">
	      		<tr>
	      			<td colspan="6"><span class="project_title">上传核销资料</span></td>
	      		</tr>
	      		
	      		<tr>
	      			<td colspan="2">签到表：<br>(至少两张)</br></td>
	      			<td colspan="4">
	      				<input type="file" id="signIn"  name="imgFile" onchange="uploadImg(this,'signIn','sign_view','sign_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;'  id="sign_area" name="signUrl"></textarea>
      					<div id="sign_view"> </div>
	      			</td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">现场培训照片：<br>(至少四张)</td>
	      			<td colspan="4">
	      				<input type="file" id="scenePicture"  name="imgFile" onchange="uploadImg(this,'scenePicture','scene_view','scene_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;'  id="scene_area" name="sceneUrl"></textarea>
      					<div id="scene_view"> </div>
	      			</td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">培训总结：</td>
	      			<td colspan="4">
	      				<input type="file" id="train_summary"  name="imgFile" onchange="uploadImg(this,'train_summary','summary_view','summary_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;'  id="summary_area" name="summaryUrl"></textarea>
      					<div id="summary_view"> </div>
	      			</td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">水单：<br>(至少两张)</br></td>
	      			<td colspan="4">
						<input type="file" id="water_menu"  name="imgFile" onchange="uploadImg(this,'water_menu','water_view','water_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;' id="water_area" name="waterUrl"></textarea>
      					<div id="water_view"> </div>
	      			</td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">发票：</td>
	      			<td colspan="4">
	      				<input type="file" id="invoice"  name="imgFile" onchange="uploadImg(this,'invoice','invoice_view','invoice_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;'  id="invoice_area" name="invoiceUrl"></textarea>
      					<div id="invoice_view"> </div>
	      			</td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">发票真伪截图：</td>
	      			<td colspan="4">
	      				<input type="file" id="invoice_real"  name="imgFile" onchange="uploadImg(this,'invoice_real','invoice_real_view','invoice_real_area','ones')" accept="image/gif,image/jpeg,image/png"/>
      					<textarea style='display: none;'  id="invoice_real_area" name="invoiceRealUrl"></textarea>
      					<div id="invoice_real_view"> </div>
	      			</td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="6">
	      			<table style="width:100%;">
	      				<thead>
	      					<tr>
	      						<td>发票代码</td><td>发票号码</td><td>发票金额</td><td>操作</td>
	      					</tr>
	      				</thead>
	      				<tbody>
	      					<tr>
				      			<td><input  type="text"  class="input_style1" name="invoiceCode"/></td>
     		 					<td><input  type="text"  class="input_style1" name="invoiceNumber"/></td>
     		 					<td><input  type="text"  class="input_style1" name="invoiceValue"/></td>
     		 					<td>
     		 						<a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">增加</a>
     		 						<!-- <a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a> -->
     		 					</td>	
	      					</tr>
	      				</tbody>
	      			
	      			
	      			</table>
	      			</td>
	      		</tr>
	      		
	      		
	      		
	      		<tr>
	      			<td colspan="6"><input id="image" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
	      	
	      	
      	</c:if>
        </form>
        
		</div>
</html>		
<script type="text/javascript">
$("#image").click(function(){
		/* if($("#signIn").val()==null||$("#signIn").val()==""){
			alert("请上传照片");
			return false;
		} */var s = $("#sign_area").val();
		alert(s);
		$("#uploadForm").submit();
})		

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
	
	
addContent = function(dom){
	var html ='<tr>'+
			'<td><input  type="text"  class="input_style1" name="invoiceCode"/></td>'+
			'<td><input  type="text"  class="input_style1" name="invoiceNumber"/></td>'+
			'<td><input  type="text"  class="input_style1" name="invoiceValue"/></td>'+
			'<td>'+
				'<a href="javascript:void(0);" onclick="addContent(this)" class="operate operate2">增加</a>'+
				'<a href="javascript:void(0);" onclick="delContent(this)" class="operate operate2">删除</a>'+
			'</td>'+
		'</tr>';
	
	$(dom).parent().parent().after(html);
}

delContent = function(dom){
	$(dom).parent().parent().remove();
}
</script>

