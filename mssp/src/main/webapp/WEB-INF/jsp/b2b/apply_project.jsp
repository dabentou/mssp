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
	</style>
	<script type="text/javascript">
	$(function(){
			
			//是否投标
			var pIsBidVal = $("#pIsBidSel").find("option:selected").val();
			if(pIsBidVal == 0){
				//$("#pIsBidBut").addClass("hide");
				$('#pIsBidBut').attr("disabled","disabled");
			}else{
				//$("#pIsBidBut").removeClass("hide");
				$('#pIsBidBut').removeAttr("disabled");
			}
			
	    	var protoVal = $("#protoSel").find("option:selected").val();
	    	if(protoVal == 0){
	    		//$("#protoBut").addClass("hide");
	    		$('#protoBut').attr("disabled","disabled");
	    	}else{
	    		//$("#protoBut").removeClass("hide");
	    		$('#protoBut').removeAttr("disabled");
	    	}
		
			$("#save").click(function(){
				if(valiForm()){
					$("#subType").val("1");
					$("#myFrom").submit();
				}  
			});
			
			//第三步 特殊支持申请
			$("#continue").click(function(){
		    	var projectId = $("#projectId").val();
		    	if(projectId==''){
		    		alert("请先保存项目报备！");
		    		return false;
		    	}
		    	window.open ( "${ROOT}/b2b/project/next.html?projectId="+projectId, "_blank","z-look=yes");
			});
			valiForm=function(){
				if($("#pName").val()==null||$("#pName").val()==""){
					alert("项目名称不能为空！");
					$("#pName").focus();
					return false;
				}
				if($("#pIsBidSel").find("option:selected").val() == 1){
					if($("#pBidTime").val()==null||$("#pBidTime").val()==""){
						alert("投标时间不能为空！");
						$("#pBidTime").focus();
						return false;
					}
				}
				if($("#business").val()<=0){
					alert("请选择行业！");
					$("#business").focus();
					return false;
				}
				if($("select[name=customerId]").val()<0){
					alert("请选客户！");
					$(this).focus();
					return false;
				}
				if($("#productss").val()<=0){
					alert("请选择型号！");
					$("#productss").focus();
					return false;
				}
				if($("#agent").val()<=0){
					alert("请选择总代！");
					$("#agent").focus();
					return false;
				}
				if($("#agentLikMan").val()==null||$("#agentLikMan").val()==""){
					alert("代理联系人不能为空！");
					$("#agentLikMan").focus();
					return false;
				}
				if($("#agentPhone").val()==null||$("#agentPhone").val()==""){
					alert("代理联系电话不能为空！");
					$("#agentPhone").focus();
					return false;
				}
				var errorCount=0;
				$("input[name=cptf]").each(function(){
					if($(this).val()==null||$(this).val()==""){
						errorCount++;
						alert("竞品型号不能为空");
						$(this).focus();
						return false;
					}
				});
				$("input[name=cppf]").each(function(){
					if($(this).val()==null||$(this).val()==""){
						errorCount++;
						alert("竞品价格不能为空");
						$(this).focus();
						return false;
					}
				});
				$("input[name=cpnf]").each(function(){
					if($(this).val()==null||$(this).val()==""){
						errorCount++;
						alert("竞品尺寸不能为空");
						$(this).focus();
						return false;
					}
				});
				$("input[name=pronum]").each(function(){
					if($(this).val()==null||$(this).val()==""){
						errorCount++;
						alert("型号数量不能为空！");
						$(this).focus();
						return false;
					}
				});
				if(errorCount>0){
					return false;
				}
				return true;
			}
			
			$("#products").change(function(){
				var sntd = $("#sn");
				var cppz=$("#cppz");
				var sntdhtml="";
				var cppzhtml="";
				$("#products option:selected").each(function(){
					sntdhtml+=$(this).html()+" ：<input type='hidden' name='pronumid' value='"+$(this).val()+"' ><input type='text' name='pronum' style='width:80px;' placeholder='数量' ><br />";
					cppzhtml+="<input type='text' name='cptf' style='width:80px;' placeholder='竞品型号' >/<input type='text' name='cpnf' style='width:50px;' placeholder='数量' >/<input type='text' name='cppf' style='width:50px;' placeholder='单价' ><br />"
		        });
				sntd.html(sntdhtml);
				cppz.html(cppzhtml);
			});
			
			
			editcp=function(){
				$("#myModal").modal();
			}
			addcp=function(dom){
				var the = $(dom);
				var cloneObj = the.parent().first("p").clone();
				if(the.next("a").attr("id")!="deleteItem"){
					cloneObj.find("a").after(" <a  href='javascripit:(0);' id='deleteItem' onclick='deleteItem(this)'>删除 </a>")
				}  
				cloneObj.find("input[type='text']").val("");
				the.parent().after(cloneObj);
			}
			deleteItem=function(dom){
				var the = $(dom);
				the.parent().remove();
			}
			
			savecp=function(){
				//var cpbodys = $("#cpbody p");
				var cphtml ="";
				$("#cpbody p").each(function(){
				    cphtml+=$(this).find("input[name=cpt]").val()+" / "+$(this).find("input[name=cpp]").val()+" / "+$(this).find("input[name=cpn]").val()+"</p>"
				    cphtml+="<input type='hidden' name='cptf' value='"+$(this).find("input[name=cpt]").val()+"'>";
				    cphtml+= "<input type='hidden' name='cppf' value='"+$(this).find("input[name=cpp]").val()+"'>";
				    cphtml+="<input type='hidden' name='cpnf' value='"+$(this).find("input[name=cpn]").val()+"'>";
				  });
				$("#cProduct").html(cphtml);
				$("#myModal").modal('hide');
			}
			
		  //是否一次性发货委托
		    pIsEntrust = function(dom){
		    	var selectedvalue = $("input[name=entrust]:checked").val();
		    	if(selectedvalue == 0){
		    		//$("#pIsBidBut").addClass("hide");
		    		$('#weituo').attr("disabled","disabled");
		    	}else{
		    		//$("#pIsBidBut").removeClass("hide");
		    		$('#weituo').removeAttr("disabled");
		    	}
		    }
		    
		    //第二步 授权申请
		    $("#pIsBidBut").click(function(){
		    	var projectId = $("#projectId").val();
		    	if(projectId==''){
		    		alert("请先保存项目报备！");
		    		return false;
		    	}
		    	window.open ( "${ROOT}/b2b/project/commitmentNote.html?projectId="+projectId, "_blank","z-look=yes");
	/* 			if(valiForm()){
					$("#subType").val("2");
					var params = $("#myFrom").serialize(); // http request parameters. 
					window.open ( "${ROOT}/b2b/project/apply?"+params, "_blank","z-look=yes");
					//$("#myFrom").submit();
					//window.open ( "${ROOT}/b2b/project/commitmentNote.html?projectId=77", "_blank","z-look=yes");
				} */
		    });
		    
		    //样机提供
		    protoChange = function(dom){
		    	var protoVal = $(dom).find("option:selected").val();
		    	if(protoVal == 0){
		    		//$("#protoBut").addClass("hide");
		    		$('#protoBut').attr("disabled","disabled");
		    	}else{
		    		//$("#protoBut").removeClass("hide");
		    		$('#protoBut').removeAttr("disabled");
		    	}
		    }
		    
		  //是否招投标
		    pIsBidChange = function(dom){
		    	var pIsBidVal = $(dom).find("option:selected").val();
		    	if(pIsBidVal == 0){
		    		//$("#protoBut").addClass("hide");
		    		$('#pIsBidBut').attr("disabled","disabled");
		    		$('.bidTime').css("display","none");
		    	}else{
		    		//$("#protoBut").removeClass("hide");
		    		$('#pIsBidBut').removeAttr("disabled");
		    		$('.bidTime').css("display","table-cell"); 
		    	}
		    }
		    
		    //第三步样机申请
		    $("#protoBut").click(function(){
		    	var projectId = $("#projectId").val();
		    	if(projectId==''){
		    		alert("请先保存项目报备！");
		    		return false;
		    	}
		    	window.open ( "${ROOT}/b2b/project/sampleDeal.html?projectId="+projectId, "_blank","z-look=yes");
/* 	 			if(valiForm()){
					var subType = $("#subType").val();
					var pIsBidVal = $("#pIsBidSel").find("option:selected").val();//是否投标
					if(pIsBidVal==1 && subType!='2'){
						alert("您尚未完成授权申请，暂不能进行此操作！");
						return;
					}
					$("#subType").val("3");
					$("#myFrom").submit();
				}  */
		    });
		    //一次性发货委托
		    $("#weituo").click(function(){
		    	var projectId = $("#projectId").val();
		    	if(projectId==''){
		    		alert("请先保存项目报备！");
		    		return false;
		    	}
		    	window.open ( "${ROOT}/b2b/project/entrustPic.html?projectId="+projectId, "_blank","z-look=yes");
	
		    });
	});
	</script>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
      	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/apply"  method="post" >
      	<input type="hidden" name="subType"   id="subType" />
      	<table class="maintable table table-hover"  style="width: 65%;">
      	<tr><td colspan="5"><span class="project_title">项目报备信息</span></td></tr>
      	    
      	    <tr>
      			<td rowspan="2">代理信息</td>
      			<td>
      				<span class="star">*</span> 代理商
      			</td>
      			<td>
      				<select  class="input_style" name="agent"  id="agent">   
	      				<option value="-1">选择总代</option>
	      				<option value="${agent.id }"<c:if test="${b2bProject.agent.id==agent.id}">selected</c:if>>${agent.irName }</option>
	      			</select>
      			</td>
      			<td><span class="star">*</span> 联系人/电话：</td>
      			<td><input value="${agentUser.realName}" type="text"  style="width: 55px;"  id="agentLikMan"  name="agentLikMan" /> / <input value="${agentUser.phone}" type="text"   style="width: 85px;"    id="agentPhone"  name="agentPhone"  /></td>
      		</tr>
      		<tr>
      			<td> <span class="star">*</span>二级代理商：</td>
      			<td>
					<select  class="input_style" name="commSi" id="commSi">   
	      				    <option value="-1">无</option>
	      				   <c:forEach items="${siList}" var="item">
	      						<option value="${item.id }" siLikeManss="${item.irName }"  phones="${item.phone }" <c:if test="${item.id==b2bProject.commSi.id}">selected</c:if>>${item.irName }</option>
	      				   </c:forEach>
	      			</select>
				</td>
      			<td id="siTd1" style="display:none;"><span class="star">*</span> 联系人/电话：</td>
      			<td id="siTd2" style="display:none;"><input value="${commSiUser.realName}" type="text"  style="width: 55px;"  id="siLikMan"  name="siLikMan" /> / <input value="${commSiUser.phone}" type="text"   style="width: 85px;"    id="siPhone"  name="siPhone" /></td>
      		</tr>
      	    
      		<tr>
      			<td rowspan="2">客户信息</td>
      			<td>
      				<span class="star">*</span> 客户名称
      			</td>
      			<td>
      				<select class="input_style" name="customerId">
      					<option value="-1" phone="" person="" address="">选择客户</option>
      					<c:forEach items="${customerList}" var="item">
	      					<option value="${item.id }" phone="${item.phone }" person="${item.person }" address="${item.address }" <c:if test="${item.id==b2bProject.customer.id}">selected</c:if>>${item.name }</option>
	      				</c:forEach>
      				</select>
      			</td>
      			<td><span class="star">*</span> 联系人/电话：</td>
      			<td><input value="${b2bProject.customer.person }" type="text" id="customerPerson" style="width: 55px;" /> / <input value="${b2bProject.customer.phone }" type="text" style="width: 85px;" id="customerPhone" /></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> 涉及行业：</td>
      			<td>
					<select  class="input_style" name="business"  id="business">  
						<option value="-1">选择行业</option>
						<c:forEach items="${blist }" var="item">
							<option value="${item.id }" <c:if test="${b2bProject.business.id==item.id }">selected</c:if> >${item.name }</option>
						</c:forEach>
	      			</select>
				</td>
      			<td><span class="star">*</span> 客户地址：</td>
      			<td><input value="${b2bProject.customer.address }" type="text"  class="input_style" id="customerAddress" /></td>
      		</tr>
      		
      		<tr>
      			<td rowspan="2">销售信息</td>
      			<td>
      				<span class="star">*</span> 型号
      			</td>
      			<td>
					<select  id="products" class="select selectpicker show-tick form-control"  multiple="multiple"  data-live-search="true"  >
		<%--                 <c:forEach items="${productlist}" var="item">
							<option value="${item.id }">${item.name }</option>				                
		                </c:forEach> --%>
		                
             			<c:forEach items="${productlist}" var="itemPro">
		              			<c:forEach items="${saList}" var="item" varStatus="itemVs">
						   				<c:if test="${itemPro.id==item.productPrice.product.id}">
									   			<option value="${itemPro.id}" selected="selected"  >${itemPro.name }</option>
									   			<c:set scope="page" var="flag" value="${itemPro.id}"/>
									   	</c:if>
						     	</c:forEach>
						     	<c:if test="${flag==''}">
										<option value="${itemPro.id}" >${itemPro.name }</option>
								</c:if>
								<c:set scope="page" var="flag" value=""/>
		            	</c:forEach>
            		</select>
				</td>
      			<td><span class="star">*</span>销售数量：</td>
      			<td id="sn">
      				<c:forEach items="${saList}" var="item" >
      					${item.productPrice.product.name }：<input type='hidden' name='pronumid' value='${item.productPrice.product.id }' ><input value="${item.number }" type='text' name='pronum' style='width:80px;' placeholder='数量' ><br />
					</c:forEach>
      			 </td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> 竞品型号/价格/尺寸：</td>
      			<td id="cppz">
      				<!-- <span id="cProduct">
      				</span>
      				<a href="javascript:(0);" onclick="editcp()">编辑竞品</a> -->
      				<c:forEach items="${saList}" var="item" >
						<input value="${item.productPrice.product.name}" type='text' name='cptf' style='width:80px;' placeholder='竞品型号' >/<input value="${item.competeProduct} " type='text' name='cpnf' style='width:50px;' placeholder='数量' >/<input value="${item.competePrice}" type='text' name='cppf' style='width:50px;' placeholder='单价' ><br />
					</c:forEach>
      				
      			</td>
      			<td><span class="star">*</span> 样机提供：</td>
      			<td>
					<select id="protoSel" class="input_style" name="proto" onchange="protoChange(this)">   
	      				<option value="1"<c:if test="${b2bProject.proto==1}">selected</c:if>>是</option>
	      				<option value="0"<c:if test="${b2bProject.proto==0}">selected</c:if>>否</option>
	      			</select>
				</td>
      		</tr>
      		<tr>
      			<td rowspan="2">项目信息</td>
      			<td><span class="star">*</span> 项目名称：</td>
      			<td><input  type="text"  class="input_style"  id="pName"  name="pName" value="${b2bProject.pName }"/><input  type="hidden"  class="input_style"  id="projectId"  name="projectId" value="${b2bProject.id}"/></td>
      			<td><span class="star">* </span>项目编号：</td>
      			<td><input type="text"  class="input_style"    id="pCode"  name="pCode" readonly="readonly" value="${b2bProject.pCode==null?projectCode:b2bProject.pCode }"/></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span> 是否招投标：</td>
      			<td>
	      			<select id="pIsBidSel" class="input_style" name="pIsBid" onchange="pIsBidChange(this)">  
	      				<option value="1"<c:if test="${b2bProject.pIsBid==1}">selected</c:if>>是</option>
	      				<option value="0"<c:if test="${b2bProject.pIsBid==0}">selected</c:if>>否</option>
	      			</select>
      			</td>
      			<td class="bidTime"><span class="star">*</span> 投标时间：</td>
      			<td class="bidTime"> <input value="<fmt:formatDate value='${b2bProject.pBidTime}' pattern='yyyy-MM-dd' />" type="text" class="form-control text datetimepicker"  style="width: 160px;height: 22px;"   name="pBidTime" id="pBidTime"/></td> 
      		</tr>
      		
      		
      		<c:if test="${project.step.statusValue >12 }">
	      		<tr>
	      			<td rowspan="12">收发货信息</td>
	      			<td>公司名称</td>
	      			<td><input value="${b2bProject.sendName}" type="text"  class="input_style"   id="sendCompany"  name="sendCompany" /></td>
	      			<td>公司名称</td>
	      			<td><input value="${b2bProject.receiveCompany}" type="text"  class="input_style"  id="sendCompany"  name="receiveCompany" /></td>
	      		</tr>
	      		<tr>
	      			<td>发件人：</td>
	      			<td><input value="${b2bProject.sendName}" type="text"  class="input_style"   id="sendName"  name="sendName" /></td>
	      			<td>收件人：</td>
	      			<td><input value="${b2bProject.receiveName}" type="text"  class="input_style"    id="receiveName"  name="receiveName" /></td>
	      		</tr>
	      		<tr>
	      			<td>地址：</td>
	      			<td><input value="${b2bProject.sendAddress}" type="text"  class="input_style"    id="sendAddress"  name="sendAddress" /></td>
	      			<td>地址：</td>
	      			<td><input value="${b2bProject.receiveName}" type="text"  class="input_style"   id="receiveAddress"  name="receiveAddress" /></td>
	      		</tr>
	      		<tr>
	      			<td>地址1：</td>
	      			<td><input value="${b2bProject.sendAddress1}" type="text"  class="input_style"   id="sendAddress1"  name="sendAddress1" /></td>
	      			<td>地址1：</td>
	      			<td><input value="${b2bProject.receiveAddress1}" type="text"  class="input_style"  id="receiveAddress1"  name="receiveAddress1" /></td>
	      		</tr>
	      		<tr>
	      			<td>地址2：</td>
	      			<td><input value="${b2bProject.sendAddress2}" type="text"  class="input_style"   id="sendAddress2"  name="sendAddress2" /></td>
	      			<td>地址2：</td>
	      			<td><input value="${b2bProject.receiveAddress2}" type="text"  class="input_style"  id="receiveAddress2"  name="receiveAddress2" /></td>
	      		</tr>
	      		<tr>
	      			<td>邮政编码：</td>
	      			<td><input value="${b2bProject.sendZipcode}" type="text"  class="input_style"    id="sendZipcode"  name="sendZipcode" /></td>
	      			<td>邮政编码：</td>
	      			<td><input value="${b2bProject.receiveZipcode}" type="text"  class="input_style"  id="receiveZipcode"  name="receiveZipcode" /></td>
	      		</tr>
	      		<tr>
	      			<td>市：</td>
	      			<td><input value="${b2bProject.sendCity}" type="text"  class="input_style"   id="sendCity"  name="sendCity"/></td>
	      			<td>市：</td>
	      			<td><input value="${b2bProject.receiveCity}" type="text"  class="input_style"  id="receiveCity"  name="receiveCity" /></td>
	      		</tr>
	      		<tr>
	      			<td>省/市/自治区：</td>
	      			<td><input  value="${b2bProject.sendProvince}" type="text"  class="input_style"   id="sendProvince"  name="sendProvince"/></td>
	      			<td>省/市/自治区：</td>
	      			<td><input value="${b2bProject.receiveProvince}" type="text"  class="input_style"  id="receiveProvince"  name="receiveProvince"/></td>
	      		</tr>
	      		<tr>
	      			<td>国家或地区：</td>
	      			<td><input value="${b2bProject.sendCountry}" type="text"  class="input_style"   id="sendCountry"  name="sendCountry"/></td>
	      			<td>国家或地区：</td>
	      			<td><input value="${b2bProject.receiveCountry}" type="text"  class="input_style"  id="receiveCountry"  name="receiveCountry"/></td>
	      		</tr>
	      		<tr>
	      			<td>电话号码：</td>
	      			<td><input value="${b2bProject.sendPhone}" type="text"  class="input_style"  id="sendPhone"  name="sendPhone"/></td>
	      			<td>电话号码：</td>
	      			<td><input value="${b2bProject.receivePhone}" type="text"  class="input_style"  id="receivePhone"  name="receivePhone"/></td>
	      		</tr>
	      		<tr>
	      			<td>传真号码：</td>
	      			<td><input value="${b2bProject.sendFax}" type="text"  class="input_style"  id="sendFax"  name="sendFax"/></td>
	      			<td>传真号码：</td>
	      			<td><input value="${b2bProject.receiveFax}" type="text"  class="input_style"  id="receiveFax"  name="receiveFax"/></td>
	      		</tr>
	      		<tr>
	      			<td>电子邮件地址：</td>
	      			<td><input type="text" value="${b2bProject.sendEmail}"  class="input_style"  id="sendEmail"  name="sendEmail" /></td>
	      			<td>电子邮件地址：</td>
	      			<td><input type="text" value="${b2bProject.receiveEmail}" class="input_style"   id="receiveEmail"  name="receiveEmail"/></td>
	      		</tr>
	      		
	      			<tr>
	      			<td rowspan="2">销售信息</td>
	      			<td>代理发货时间</td>
	      			<td> <input value="<fmt:formatDate value='${b2bProject.agentSendTime}' pattern='yyyy-MM-dd' />" type="text"  class="form-control text datetimepicker"  style="width: 160px;height: 22px;" name="agentSendTime"/></td>
	      			<td>客户收货时间：</td>
	      			<td> <input value="<fmt:formatDate value='${b2bProject.customerReceiveTime}' pattern='yyyy-MM-dd' />" type="text"  class="form-control text datetimepicker"  style="width: 160px;height: 22px;" name="customerReceiveTime"/></td>
	      		</tr>
	      		<tr>
	      			<td>产品安装情况：</td>
	      			<td><input value="${b2bProject.productUseInfo}" type="text"  class="input_style"  id="productUseInfo"  name="productUseInfo" /></td>
	      			<td>售后服务形式：</td>
	      			<td><input value="${b2bProject.afterSalesService}" type="text"  class="input_style"  id="afterSalesService"  name="afterSalesService" /></td>
	      		</tr>
	      	</c:if>
	      		
	      		
	      		
	      		
      		<tr>
      			<td>备注：</td>
      			<td colspan="4"><textarea cols="100" rows="5"   id="remark"  name="remark" > ${b2bProject.remark}</textarea></td>
      		</tr>
      		<tr>
      			<td>一次性发货委托：</td>
      			<td>
      				<label class="radio-inline">
               			 <input type="radio" value=1 name="entrust" checked> 是
              		</label>
              		<label class="radio-inline">
               			 <input type="radio" value=0 name="entrust"> 否
              		</label>
      			</td>
      		</tr>
      		<tr>
      			<td colspan="5"> <input id="save" type="button" class="button button-lightblue" value="保存项目报备" /><br/><br/>
      			<!-- 
      				第一步：授权申请
      				第二步：样机申请
      				第三步：特殊支持申请
      			 -->
      			<input id="pIsBidBut" type="button" class="button button-lightblue" value="授权申请" />&nbsp;&nbsp;&nbsp;
      			<input id="protoBut" type="button" class="button button-lightblue" value="样机申请" />&nbsp;&nbsp;&nbsp;
      			<input id="weituo"  type="button" class="button button-lightblue" value="一次性发货委托" />&nbsp;&nbsp;&nbsp;
      			<input id="continue" type="button" class="button button-lightblue" value="特价申请" /></td>
      		</tr>
      	</table>
        </form>
    </div>
  </div>
  
  
  
  
  
  <div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">编辑竞品</h4>
      </div>
      <div class="modal-body" id="cpbody">
        <p>竞品型号/价格/尺寸：<input type="text"  name="cpt" placeholder="竞品型号"  style="width: 150px;"/> 
        <input type="text"  placeholder="价格"  name="cpp"  style="width: 100px;"/>
        <input type="text"  placeholder="数量"  name="cpn"   style="width: 100px;"/>  <a href="javascript:(0);" onclick="addcp(this)">新增</a></p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
        <button type="button" class="btn btn-primary" onclick="savecp()">保存</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div>
  
</body>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name=entrust]").change(function(){
		 var selectedvalue = $("input[name=entrust]:checked").val();
	    	if(selectedvalue == 0){
	    		$('#weituo').attr("disabled","disabled");
	    	}else{
	    		$('#weituo').removeAttr("disabled");
	    	}
	}) 
	
    //日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
    
    $(window).on('load', function () {

        $('.selectpicker').selectpicker({
            'selectedText': 'cat'
        });

    });
    $('#agent').change(function(){
    	var agent = $('#agent').val();
    	if(agent == -1){
    		$('#agentPhone').val("");
    		$('#agentLikMan').val('');
    	}else{
    		$('#agentPhone').val(${agent.phone});
    		$('#agentLikMan').val('${agent.irName}');
    	}
    });

    $('#commSi').change(function(){
    	/* var s = $("#commSi option:selected").attr("phones");
    	$('#siPhone').val(s); */
    	
    	var si = $('#commSi').val();
    	if(si == -1){
    		$('#siPhone').val("");
    		$('#siLikMan').val('');
    		$('#siTd1').css("display","none");
    		$('#siTd2').css("display","none");
    		
    		
    	}else{
    		$('#siPhone').val($("#commSi option:selected").attr("phones"));
    		$('#siLikMan').val($("#commSi option:selected").attr("siLikeManss"));
    		$('#siTd1').css("display","table-cell");
    		$('#siTd2').css("display","table-cell");
    	}
    });
    $("select[name=customerId]").change(function(){
    	var person = $("select[name=customerId] option:selected").attr("person");
    	var phone = $("select[name=customerId] option:selected").attr("phone");
    	var address = $("select[name=customerId] option:selected").attr("address");
    	$("#customerPerson").val(person);
    	$("#customerPhone").val(phone);
    	$("#customerAddress").val(address);
    });
    //获取项目编码 
    getPCode = function(dom){
    	var agentId = $("select[name='agentId'] option:selected").val();
    	location.href = "${ROOT}/b2b/project/input.html?agentId="+agentId;
    }
    
});
</script>
</html>