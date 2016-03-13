<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>一次性发货委托申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
		.input_style{
			width: 100px;
		}
		.project_title{
		font-weight: bold;
		}
		.star{
			color: red;
		}
		.table td{
		border:1px solid #ddd;
		}
		.table th{
		border:1px solid #ddd
		}
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
		body {
			font-family: SimSun;
		}
		.input-mini{
			width: 30px;
		}
		.bordernone { background-color: transparent; border:none; border-bottom: 1px solid #222;}
    .bordernone:focus{ outline: none;}
	</style>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
      	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/next"  enctype="multipart/form-data" method="post" >
	      	<input id="projectId" type="hidden" name="projectId"  value="${project.id }"/>
	      	<table class="maintable table table-hover"  style="width: 65%;" border="1">
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<h3>（一次性地址）发货委托函</h3>
		      		</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">致：飛生（上海）電子科技有限公司</span></p>
		      			<p class="text-left">
		      				我公司委托贵司将我司此批所采购货物发送到以下指定地址：
						</p>
						<p class="text-left">
							发货委托函有效期：<ins><input class="bordernone" id="validTime" type="text"/></ins>(具体日期 周期为7-15天)
						</p>
						<p class="text-left">
							收货公司：<ins><input class="bordernone" id="receiveCompany" type="text"/></ins>(与客户签收章样本一致)
						</p>
						<p class="text-left">
							收货地址：<ins><input class="bordernone" id="receiveAddress" type="text"/></ins>(指定地址)
						</p>
						<p class="text-left">
							收货人：<ins><input class="bordernone" id="receivePerson" type="text"/></ins>(必须与身份证复印件一致) 
						</p>
						<p class="text-left">
							联系电话：<ins><input class="bordernone" id="phone" type="text"/></ins> (必须是收货人联系方式) 
						</p>
						<p class="text-left">
							身份证号码：<ins><input class="bordernone" id="idcardNum" type="text"/></ins>(必须与身份证复印件一致)
						</p>
						
					<br />
					<p class="text-left">身份证复印件：</p>
					<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','one')" accept="image/gif,image/jpeg,image/png"/>
      						<input type="hidden" id="email_area" name="approveEmail"/>
      						<div id="email_view">
                   			 </div><br />
					<p class="text-left">客户签收盖章样本:</p>
					<input type="file" id="emailFiles"  name="imgFile" onchange="uploadImg(this,'emailFiles','email_views','email_areas','one')" accept="image/gif,image/jpeg,image/png"/>
      						    <input type="hidden" id="email_areas" name="approveEmail"/>
      						    <div id="email_views">
                   			   </div><br />
					
						</td>
		      	</tr>
		      	<tr>
		      	<td  colspan="6">
					<p class="text-left">以上委托人收货视同我司收货，因此产生的一切纠纷与风险由我司承担，与贵司无关！<!--  委托发货明细如下： --></p>
					</td>
		      	</tr>
		      	<!-- <tr>
		      	<td  colspan="6">
		      		<table width="100%">
		      			<thead><tr>
		      				<td>订单编号</td><td>产品名称</td><td>型号</td><td>数量</td>
		      			</tr>
		      			</thead>
		      			
		      		</table>
		      	</td>
		      	</tr> -->
		      	<tr>
		      		<td>
		      			公司盖章：
		      		</td>
		      	</tr>
		      	
		      	<!-- <tr>
			      		<td colspan="2">发货委托函扫描件上传：</td>
			      		<td colspan="4"><input type="file" id="files" name="files"/><input type="hidden" name="subType" value="5"/></td>
	      		</tr> -->
		      	
		      	<tr>
	      				<td colspan="2"><span class="star">*</span>发货委托函扫描件上传：<input type="hidden" name="subType" value="5"/></td>
     		 			<td colspan="4">
     		 				<input type="file" name="excelFile"  onchange="uploadExcel(this)"  id="upload_excel" />
     		 				<input type="hidden" value="" name="samleContent" />
     		 				<div class="download"></div>
     		 			</td>
     		     </tr>
		      	
	      		<tr>
	      			<td colspan="6">
	      				<input id="toPDFBut" type="button" class="button button-lightblue" value="生成PDF" />
	      				<input id="save" type="submit" class="button button-lightblue" value="保存" />
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
		
		//生成pdf 
		$("#toPDFBut").click(function(){
			var projectId = $("#projectId").val();
			var validTime = $("#validTime").val();
			var receiveCompany = $("#receiveCompany").val();
			var receiveAddress = $("#receiveAddress").val();
			var receivePerson = $("#receivePerson").val();
			var phone = $("#phone").val();
			var idcardNum = $("#idcardNum").val();
			var idUrl = $("#email_area").val();
			var customerSign = $("#email_areas").val();
			location.href = "${ROOT}/b2b/project/entrustPic/exportPDF?projectId="+projectId+"&validTime="+validTime+"&receiveCompany="+receiveCompany+"&receiveAddress="+receiveAddress+"&receivePerson="+receivePerson+"&phone="+phone+"&idcardNum="+idcardNum+"&idUrl="+idUrl+"&customerSign="+customerSign;
		});
		
		$("#save").click(function(){
			var file = $("input[name=files]");
			if(file[0].value.length == 0){
				alert("请上传一次性发货委托函");
				return false;
			}
		});
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
	})
	
	function uploadExcel(dom){
		var ids=$(dom).attr("id");
		$.ajaxFileUpload({
			type : "POST",
			url : "${ROOT}/editor/excel/upload",
			secureuri:false,  
		    fileElementId:ids,//文件选择框的id属性 
			dataType : "json",
			success : function(json){
				if(json.error==0){
					var urls ="http://" + window.location.host+json.url;
					if($("input[name=samleContent]").val()==""){
						$("input[name=samleContent]").val(urls);
					}else{
						$("input[name=samleContent]").val($("input[name=samleContent]").val()+","+urls);
					}
					$(".download").append('<a href="'+ urls +'" class="thumbnail"></a>');
				}else{
					alert(json.error);
				}
			} 
		});
	}
</script>
