<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>授权申请</title>
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
		      			<h3>飞利浦显示器产品投标项目授权--代理承诺函</h3>
		      		</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">致：飛生（上海）電子科技有限公司</span></p>
		      			<p class="text-left">
		      				我公司，即 <ins> ${project.agent.irName} </ins>(投标代理商名称) ，按中华人民共和国法律设立，主要营业地点设在<ins> <input class="bordernone" name="projectPlace" type="text"/> </ins>(电话 <ins> <input class="bordernone" name="chargePerPhone" type="text"/> </ins>， 项目负责人：<ins> <input class="bordernone" name="chargePerson" type="text"/> </ins>）。
							    正在参与的<ins><input class="bordernone" name="projectName" type="text"/></ins>项目，项目编号<ins><input class="bordernone" name="projectCode" type="text"/></ins>，鉴于我方愿意作为贵方制造的<ins> 飞利浦显示器 </ins>产品的销售代理，并要求贵方出具《制造商出具的投标授权函》，参与该项目的投标活动，为保护贵方作为产品供应厂商的利益，我公司特向贵公司承诺如下:<br/><br/>
						</p>
						<p class="text-left">
							&nbsp;&nbsp;&nbsp;一、我公司将以投标合作者的身份约束自己，依据我公司与贵公司总代理签订的代理协议，参与上述项目的投标活动。<br/>
							&nbsp;&nbsp;&nbsp;二、我公司将在本项目中优先支持飞利浦显示器产品和服务。<br/>
							&nbsp;&nbsp;&nbsp;三、我公司将通过贵公司指定或认可的供货渠道，向招标方提供飞利浦显示器产品。<br/>
							&nbsp;&nbsp;&nbsp;四、我公司就贵方所提供的产品与服务向招标方提供的最终投标价格，保证不恶意价格投标，将不低于贵公司向我公司所提供的价格，且不会在任何时候向任何第三方透露该价格。<br/>
							&nbsp;&nbsp;&nbsp;五、我公司向招标方承诺的服务标准，如果高出贵方所提供的服务标准，我公司将独立承担相应的责任，确保我公司所承诺的合同义务得到履行。<br/>
							<span style="color: red">
								&nbsp;&nbsp;&nbsp;六、我公司将应贵方的要求，在项目投标中标后，向贵方提供与招标方或最终用户就项目所签订的与飞利浦显示器产品相关销售服务合同（包括合同附件)）、发票、运单和单据等文件（或相关复印件）。
							</span><br/><br/>
						</p>
						<p class="text-left">我公司将赔偿因违反本函项下的承诺而给贵方造成的损失。</p><br/><br/>
						<p class="text-left">投标代理名称：<input class="bordernone" name="proxyName" type="text"/></p><br/><br/>
						<p class="text-left">授权代表签字（盖章）：</p><br/><br/>
						<p class="text-left"><input class="input-mini bordernone" name="year" type="text"/>年<input class="input-mini bordernone" name="mon" type="text"/>月<input class="input-mini bordernone" name="day" type="text"/>日 </p><br/><br/>
					</td>
		      	</tr>
	      		
	      		<!-- <tr>
			      		<td colspan="2">承诺函盖章扫描件上传：</td>
			      		<td colspan="4"><input type="file" id="files" name="files"/><input type="hidden" name="subType" value="2"/></td>
	      		</tr> -->
	      		
	      		<tr>
	      				<td colspan="2"><span class="star">*</span>承诺函盖章扫描件上传：<input type="hidden" name="subType" value="2"/></td>
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
			var projectName = $("input[name='projectName']").val();
			var projectCode = $("input[name='projectCode']").val();
			var projectPlace = $("input[name='projectPlace']").val();
			var chargePerPhone = $("input[name='chargePerPhone']").val();
			var chargePerson = $("input[name='chargePerson']").val();
			var proxyName = $("input[name='proxyName']").val();
			var year = $("input[name='year']").val();
			var mon = $("input[name='mon']").val();
			var day = $("input[name='day']").val();
			location.href = "${ROOT}/b2b/project/commitmentNote/exportPDF?projectId="+projectId+"&projectName="+projectName+"&projectCode="+projectCode+"&projectPlace="+projectPlace+"&chargePerPhone="+chargePerPhone+"&chargePerson="+chargePerson+"&proxyName="+proxyName+"&year="+year+"&mon="+mon+"&day="+day;
		});
		
		$("#save").click(function(){
			var file = $("input[name=files]");
			var authorization = ${project.pIsBid };
			if(authorization == 1){
				if(file[0].value.length == 0){
					alert("请上传代理商承诺函");
					return false;
				}
			}
		});
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
