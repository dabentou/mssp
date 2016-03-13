<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
    <style>
    	.wrap .textarea{width:400px;}
    	.selectpicker { width:100%;}
    	.table td{border:1px solid #ddd;}
    	.input-mini{
			width: 70px;
		}
    </style>
    <link rel="stylesheet" href="${ROOT}/css/jquery-ui.css" />
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
      	<form id="form" class="form-horizontal"  action="${ROOT}/b2i/apply/save" enctype="multipart/form-data"  method="post" >
      	  <table class="maintable table table-hover"  style="width: 90%;">
       	    <tr>
				<td>申请模板：</td>
				<td colspan="5">
   					<select class="select selectpicker show-tick form-control" id="applyTemplate-sel" name="applyTemplate"  onchange="getTemplate(this)">
   						<c:forEach items="${applyTemplates}" var="item">
   						  <option value="${item.id }">${item.name }</option>
   						</c:forEach>
   					</select>
				</td>
			</tr>
      		<tr><td colspan="5"><span class="project_title" id="title-td">项目报备信息</span></td></tr>
      		<tr>
      			<td id="project-lable" rowspan="3">项目信息</td>
      			<td><span class="star">*</span> 项目名称：</td>
      			<td>
      				<input  type="text"  class="form-control text" name="pName" />
      				<input  type="hidden" id="template-inp"  class="form-control text" name="templateType" value="1"/>
      			</td>
      			<td><span class="star">*</span>代理：</td>
   				<td>
   					<select class="select" id="agent-sel" name="agentId">
   						<option value="-1" cityname="" projectcode="">请选择</option>
   						<c:forEach items="${agentList}" var="item">
   						  <option value="${item.id }">${item.irName }</option>
   						</c:forEach>
   					</select>
   				</td>
      		</tr>
      		<tr>
      			<td>项目编号：</td>
      			<td id="pcode"></td>
      			<td id="cityOrArea-lable">城市：</td>
      			<td id="city"></td>
      		</tr>
      		<tr class="temp2Tr hide">
      			<td>请选择SI:</td>
      			<td>
      				<select id="temp2TrCommSiSel" class="select" name="commSi">  
	      				<option value="-1" siperson="" siphone="" siemail="">请选择SI</option>
	      				<c:forEach items="${siAgentList}" var="item">
		            		<option value="${item.id}" siperson="${item.irName}" siphone="${item.phone}" siemail="${item.email}" >${item.irName }</option>
		            	</c:forEach>
	      			</select>
      			</td>
      			<td><span class="star">*</span> 客户名称：</td>
      			<td>
      				<select id="temp2TrCustSel" class="select" name="customerId"">  
	      				<option value="-1" person="" mobile="" phone="" zipcode="" address="">请选择客户</option>
	      				<c:forEach items="${customerList}" var="item">
		            		<option value="${item.id}" person="${item.person}" mobile="${item.mobile}" phone="${item.phone}" zipcode="${item.zipcode}" address="${item.address}" >${item.name }</option>
		            	</c:forEach>
	      			</select>
      			</td>
      		</tr>
      		<tr class="temp2Tr hide">
      			<td><span class="star">*</span> 申请人：</td>
      			<td>
      				<input type="hidden" class="form-control text"  name="userId" value="${sessionScope.user.id}"/>
      				<input type="text" class="form-control text"  name="applyPerson" readonly="readonly" value="${sessionScope.user.realName}"/>
      			</td> 
      			<td><span class="star">*</span> 申请日期：</td>
      			<td><input type="text" class="form-control text" readonly="readonly" style="width:220px;" name="applyTime" value="<fmt:formatDate value='${applyDate}' pattern="yyyy-MM-dd" />"/></td> 
      		</tr>
      		<tr class="temp1Tr" id="tempAddTr">
      		
      		    <td class="temp3Tr hide"><span class="star">*</span> 行业：</td>
      			<td class="temp3Tr hide">
      				<select id="temp2TrCustSel" class="select" name="businessId"">  
	      				<option value="-1">请选择行业</option>
	      				<c:forEach items="${blist}" var="item">
		            		<option value="${item.id}" >${item.name }</option>
		            	</c:forEach>
	      			</select>
      			</td>
      			<td><span class="star">*</span> 预计采购时间：</td>
      			<td><input type="text" class="form-control text datetimepicker" style="width:220px;" name="purchaseTime" /></td> 
      		</tr>
			
      		<tr>
       			<td rowspan="3">客户信息</td>
      			<td>请选择客户:</td>
      			<td>
      				<select id="temp1TrCustSel" class="select" name="temp1CustomerId" onchange="infoChange(this);">  
	      				<option value="-1" person="" mobile="" phone="" zipcode="" address="">请选择客户</option>
	      				<c:forEach items="${customerList}" var="item">
		            		<option value="${item.id}" person="${item.person}" mobile="${item.mobile}" phone="${item.phone}" zipcode="${item.zipcode}" address="${item.address}" >${item.name }</option>
		            	</c:forEach>
	      			</select>
      			</td>
      			<td>联系人:</td>
      			<td id="person"></td>
      		</tr>
      		<tr>
      			<td>手机：</td>
      			<td id="mobile"></td>
      			<td>电话：</td>
      			<td id="phone"></td>
      		</tr>
      		<tr>
      			<td>邮编：</td>
      			<td id="zipcode"></td>
      			<td>地址：</td>
      			<td id="address"></td>
      		</tr>
      		<tr>
      			<td rowspan="2">SI信息</td>
      			<td>请选择SI:</td>
      			<td>
      				<select id="temp1TrCommSiSel" class="select" name="temp1CommSi" onchange="infoChange(this);">  
	      				<option value="-1" siperson="" siphone="" siemail="">请选择SI</option>
	      				<c:forEach items="${siAgentList}" var="item">
		            		<option value="${item.id}" siperson="${item.irName}" siphone="${item.phone}" siemail="${item.email}" >${item.irName }</option>
		            	</c:forEach>
	      			</select>
      			</td>
      			<td>联系人</td>
      			<td id="siperson"></td>
      		</tr>
      		<tr>
      			<td>手机</td>
      			<td id="siphone"></td>
      			<td>邮箱</td>
      			<td id="siemail"></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>项目背景：</td>
      			<td colspan="2"><textarea class="col-md-6 form-control textarea" rows="3" name="projectContext"></textarea></td>
      			<td><span class="star">*</span>项目重要性：</td>
      			<td colspan="2"><textarea class="col-md-6 form-control textarea" rows="3" name="projectImportant"></textarea></td>
      		</tr>
      		<tr>
      			<td><span class="star">*</span>竞争对手情况：</td>
      			<td colspan="2"><textarea class="col-md-6 form-control textarea" rows="3" name="competeCondition"></textarea></td>
      			<td><span class="star">*</span>申请理由：</td>
      			<td colspan="2"><textarea class="col-md-6 form-control textarea" rows="3" name="applyReason"></textarea></td>
      		</tr>
			<tr>
				<td colspan="6">
					<table id="pro-table" class="maintable table table-hover" border="1">
						<tbody>
							<tr>
								<td width="48">编号</td>
								<td>产品型号</td>
								<td>可申请台数</td>
								<td><span class="star">*</span>申请台数(台)</td>
								<td>当月公价</td>
								<td><span class="star">*</span>主要竞争品牌</td>
								<td><span class="star">*</span>主要竞争品牌价格</td>
								<td><span class="star">*</span>代理申请价(元)</td>
								<td>操作</td>
							</tr>
							<tr class="no">
								<td></td>
								<td>
									<input type="hidden" name="productPriceId" value="" />
									<input onBlur="getTemp1ByProduct(this)" class="input-mini product-inp" type="text" name="product"/>
									<input type="hidden" value="" name="sellin" />
								</td>
								<td>
								</td>
								<td>
									<input onBlur="checkApplyNum(this)" class="input-mini" type="text" name="number" />
								</td>
								<td></td>
								<td>
									<input class="input-mini" type="text" name="compProduct" />
								</td>
								<td>
									<input class="input-mini" type="text" name="compPrice" />
								</td>
								<td>
									<input class="input-mini" type="text" name="applyPrice" />
								</td>
								<td>
									<a href="javascript:void(0);" onclick="addTemp1Tr(this)"  class="operate operate2">添加</a>
								</td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td>特殊说明：</td>
      			<td colspan="5"><textarea class="col-md-6 form-control textarea" rows="3" style="width:100%;" name="specificSupportInfo"></textarea></td>
			</tr>
			<tr>
				<td><span class="star">*</span>上传图片：</td>
      			<td colspan="5"><input type="file" name="files"></textarea></td>
			</tr>
			<tr>
      			<td colspan="6"><input type="submit" class="button button-lightblue" value="确认提交"></td>
      		</tr>
      		
      		
      	  </table>
        </form>
    </div>
  </div>
</body>
<script type="text/javascript">
$(document).ready(function(){
    //日期控件
    $('.datetimepicker').datetimepicker({
        format: 'yyyy-mm-dd',
        autoclose: true,
        minView:2
    });
    $("#product").change(function(){
    	var product = $("#product option:selected");
		$("#pro-table tbody tr").remove(".no");
		for(var i=0; i<product.length; i++){
			var sellin = product[i].getAttribute("sellin");
			var publicprice = product[i].getAttribute("publicprice");
			$("#pro-table tbody").append("<tr class='no'><td>" + (i+1) + "</td><td><input type='hidden' name='productId' value=" + product[i].value + " />" + product[i].text + "</td><td>"+ sellin +"<input type='hidden' value="+sellin+" name='sellin' /></td><td><input type='text' name='number' /></td><td>"+ publicprice +"</td><td><input type='text' name='compProduct' /></td><td><input type='text' name='compPrice' /></td><td><input type='text' name='customerPrice' /></td><td><input type='text' name='applyPrice' /></td></tr>");
		}
    });
    
    var availableTags = [ ${productNames}];
    $(".product-inp").autocomplete({
        source: availableTags
    });

    //加载不同申请模板 
    getTemplate = function(dom){
    	//var the = $(dom).parent().parent();
    	var the = $("#tempAddTr");
    	the.nextAll().remove();
    	var applyTemplateId = $("#applyTemplate-sel option:selected").val();
    	var html;
    	if(applyTemplateId==1){//基础模板（第一个）
    		$("#cityOrArea-lable").html("城市");
    		$("#title-td").html("项目报备信息");
    		$("#template-inp").val(1);
    		$(".temp2Tr").each(function(){
    			$(this).addClass("hide");
    		});
    		$(".temp3Tr").each(function(){
    			$(this).addClass("hide");
    		});
    		$(".temp1Tr").each(function(){
    			$(this).removeClass("hide");
    		});
    		$("#project-lable").attr("rowspan","3");
    		
    		html = "<tr>"+
			   			"<td rowspan='3'>客户信息</td>"+
			  			"<td>请选择客户:</td>"+
			  			"<td>"+
			  				"<select class='select' name='customerId' onchange='infoChange(this);'>  "+
			      				"<option value='-1' person='' mobile='' phone='' zipcode='' address=''>请选择客户</option>"+
			      				"<c:forEach items='${customerList}' var='item'>"+
				            		"<option value='${item.id}' person='${item.person}' mobile='${item.mobile}' phone='${item.phone}' zipcode='${item.zipcode}' address='${item.address}' >${item.name }</option>"+
				            	"</c:forEach>"+
			      			"</select>"+
			  			"</td>"+
			  			"<td>联系人:</td>"+
			  			"<td id='person'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>手机：</td>"+
			  			"<td id='mobile'></td>"+
			  			"<td>电话：</td>"+
			  			"<td id='phone'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>邮编：</td>"+
			  			"<td id='zipcode'></td>"+
			  			"<td>地址：</td>"+
			  			"<td id='address'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td rowspan='2'>SI信息</td>"+
			  			"<td>请选择SI:</td>"+
			  			"<td>"+
			  				"<select id='temp1TrCommSiSel' class='select' name='commSi' onchange='infoChange(this);'>  "+
			      				"<option value='-1' siperson='' siphone='' siemail=''>请选择SI</option>"+
			      				"<c:forEach items='${siAgentList}' var='item'>"+
				            		"<option value='${item.id}' siperson='${item.irName}' siphone='${item.phone}' siemail='${item.email}' >${item.irName }</option>"+
				            	"</c:forEach>"+
			      			"</select>"+
			  			"</td>"+
			  			"<td>联系人</td>"+
			  			"<td id='siperson'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>手机</td>"+
			  			"<td id='siphone'></td>"+
			  			"<td>邮箱</td>"+
			  			"<td id='siemail'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td><span class='star'>*</span>项目背景：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='projectContext'></textarea></td>"+
			  			"<td><span class='star'>*</span>项目重要性：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='projectImportant'></textarea></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td><span class='star'>*</span>竞争对手情况：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='competeCondition'></textarea></td>"+
			  			"<td><span class='star'>*</span>申请理由：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='applyReason'></textarea></td>"+
			  		"</tr>"+
    				"<tr>"+
						"<td colspan='6'>"+
							"<table id='pro-table' class='maintable table table-hover' border='1'>"+
								"<tbody>"+
									"<tr>"+
										"<td width='48'>编号</td>"+
										"<td>产品型号</td>"+
										"<td>累计Sell In</td>"+
										"<td><span class='star'>*</span>台数(台)</td>"+
										"<td>当月公价</td>"+
										"<td><span class='star'>*</span>主要竞争品牌</td>"+
										"<td><span class='star'>*</span>主要竞争品牌价格</td>"+
										"<td><span class='star'>*</span>代理申请价(元)</td>"+
										"<td>操作</td>"+
									"</tr>"+
									"<tr class='no'>"+
										"<td></td>"+
										"<td>"+
											"<input type='hidden' name='productPriceId' value='' />"+
											"<input onBlur='getTemp1ByProduct(this)' class='input-mini product-inp' type='text' name='product' />"+
											"<input type='hidden' value='' name='sellin' />"+
										"</td>"+
										"<td>"+
										"</td>"+
										"<td>"+
											"<input onBlur='checkApplyNum(this)' class='input-mini' type='text' name='number' />"+
										"</td>"+
										"<td></td>"+
										"<td>"+
											"<input class='input-mini' type='text' name='compProduct' />"+
										"</td>"+
										"<td>"+
											"<input class='input-mini' type='text' name='compPrice' />"+
										"</td>"+
										"<td>"+
											"<input class='input-mini' type='text' name='applyPrice' />"+
										"</td>"+
										"<td>"+
											"<a href='javascript:void(0);' onclick='addTemp1Tr(this)'  class='operate operate2'>添加</a>"+
										"</td>"+
									"</tr>"+
								"</tbody>"+
							"</table>"+
						"</td>"+
					"</tr>"+
				"<tr>"+
					"<td><span class='star'>*</span>特殊支持：</td>"+
					"<td colspan='5'><textarea class='col-md-6 form-control textarea' rows='3' style='width:100%;' name='specificSupportInfo'></textarea></td>"+
				"</tr>"+
				"<tr>"+
					"<td colspan='6'><input type='submit' class='button button-lightblue' value='确认提交'></td>"+
				"</tr>";
    	}else if(applyTemplateId==2){ //赞助模板(第二个)
    		$("#cityOrArea-lable").html("区域");
    		$("#title-td").html("网吧活动报备信息");
    		$("#template-inp").val(2);
    		$(".temp1Tr").each(function(){
    			$(this).addClass("hide");
    		});
    		$(".temp3Tr").each(function(){
    			$(this).addClass("hide");
    		});
    		$(".temp1Tr").each(function(){
    			$(this).removeClass("hide");
    		});
    		$(".temp2Tr").each(function(){
    			$(this).removeClass("hide");
    		});
    		$("#project-lable").attr("rowspan","4");
    		html = "<tr>"+
						"<td colspan='6'>"+
						"<table id='pro-table' class='maintable table table-hover' border='1'>"+
							"<tbody>"+
								"<tr>"+
									"<td>编号</td>"+
									"<td>产品型号</td>"+
									"<td>可用台数</td>"+
									"<td><span class='star'>*</span>台数(台)</td>"+
									"<td>网吧公价</td>"+
									"<td>申请价格</td>"+
								/* 	"<td>批复价格</td>"+ */
									"<td>支持金额</td>"+
									"<td>竞品型号</td>"+
									"<td><span class='star'>*</span>竞争价格</td>"+
									"<td>操作</td>"+
								"</tr>"+
							"</tbody>"+
							"<tr class='no'>"+
								"<td></td>"+
								"<td>"+
									"<input type='hidden' name='productPriceId' value='' />"+
									"<input onBlur='getTemp1ByProduct(this)' class='input-mini product-inp' type='text' name='product' value='' />"+
								"</td>"+
								"<td></td>"+
								"<td><input onBlur='checkApplyNum(this)' class='input-mini' type='text' name='number' /></td>"+
								"<td></td>"+
								"<td><input class='input-mini' type='text' name='applyPrice' /></td>"+
								/* "<td><input class='input-mini' type='text' name='replyPrice' /></td>"+ */
								"<td><input class='input-mini' type='text' name='supportMoney' /></td>"+
								"<td><input class='input-mini' type='text' name='compProduct' /></td>"+
								"<td><input class='input-mini' type='text' name='compPrice' /></td>"+
								"<td>"+
									"<a href='javascript:void(0);' onclick='addTemp2Tr(this)'  class='operate operate2'>添加</a>"+
								"</td>"+
							"</tr>"+
						"</table>"+
					"</td>"+
				"</tr>"+
				"<tr>"+
					"<td><span class='star'>*</span>赛事活动方案说明：</td>"+
						"<td colspan='5'><textarea class='col-md-6 form-control textarea' rows='3' style='width:100%;' name='explain'></textarea></td>"+
				"</tr>"+
				"<tr>"+
		  			"<td colspan='6'><input type='submit' class='button button-lightblue' value='确认提交'></td>"+
		  		"</tr>";
    	}else if(applyTemplateId==3){
    		$("#title-td").html("网吧会议支持");
    		$("#template-inp").val(3);
    		$(".temp2Tr").each(function(){
    			$(this).addClass("hide");
    		});
    		$(".temp3Tr").each(function(){
    			$(this).removeClass("hide");
    		});
    		
    		html = "<tr>"+
			   			"<td rowspan='3'>客户信息</td>"+
			  			"<td>请选择客户:</td>"+
			  			"<td>"+
			  				"<select class='select' name='customerId' onchange='infoChange(this);'>  "+
			      				"<option value='-1' person='' mobile='' phone='' zipcode='' address=''>请选择客户</option>"+
			      				"<c:forEach items='${customerList}' var='item'>"+
				            		"<option value='${item.id}' person='${item.person}' mobile='${item.mobile}' phone='${item.phone}' zipcode='${item.zipcode}' address='${item.address}' >${item.name }</option>"+
				            	"</c:forEach>"+
			      			"</select>"+
			  			"</td>"+
			  			"<td>联系人:</td>"+
			  			"<td id='person'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>手机：</td>"+
			  			"<td id='mobile'></td>"+
			  			"<td>电话：</td>"+
			  			"<td id='phone'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>邮编：</td>"+
			  			"<td id='zipcode'></td>"+
			  			"<td>地址：</td>"+
			  			"<td id='address'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td rowspan='2'>SI信息</td>"+
			  			"<td>请选择SI:</td>"+
			  			"<td>"+
			  				"<select id='temp1TrCommSiSel' class='select' name='commSi' onchange='infoChange(this);'>  "+
			      				"<option value='-1' siperson='' siphone='' siemail=''>请选择SI</option>"+
			      				"<c:forEach items='${siAgentList}' var='item'>"+
				            		"<option value='${item.id}' siperson='${item.irName}' siphone='${item.phone}' siemail='${item.email}' >${item.irName }</option>"+
				            	"</c:forEach>"+
			      			"</select>"+
			  			"</td>"+
			  			"<td>联系人</td>"+
			  			"<td id='siperson'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td>手机</td>"+
			  			"<td id='siphone'></td>"+
			  			"<td>邮箱</td>"+
			  			"<td id='siemail'></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td><span class='star'>*</span>项目背景：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='projectContext'></textarea></td>"+
			  			"<td><span class='star'>*</span>项目重要性：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='projectImportant'></textarea></td>"+
			  		"</tr>"+
			  		"<tr>"+
			  			"<td><span class='star'>*</span>竞争对手情况：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='competeCondition'></textarea></td>"+
			  			"<td><span class='star'>*</span>申请理由：</td>"+
			  			"<td colspan='2'><textarea class='col-md-6 form-control textarea' rows='3' name='applyReason'></textarea></td>"+
			  		"</tr>"+
				"<tr>"+
					"<td><span class='star'>*</span>会议支持金额：</td>"+
					"<td><input type='text' name='meetingSupportMoney' class='form-control text' value=''/></td>"+
				"</tr>"+
				"<tr>"+
					"<td colspan='6'><input type='submit' class='button button-lightblue' value='确认提交'></td>"+
				"</tr>";
    	}
    	the.after(html);
        $(".product-inp").autocomplete({
            source: availableTags
        });
    }
    
    
    //添加模板2的行
    addTemp2Tr = function(dom){
    	var the = $(dom).parent().parent();
    	the.after(
				"<tr class='no'>"+
						"<td></td>"+
						"<td>"+
							"<input type='hidden' name='productPriceId' value='' />"+
							"<input onBlur='getTemp1ByProduct(this)' class='input-mini product-inp' type='text' name='product' value='' />"+
						"</td>"+
						"<td></td>"+
						"<td><input onBlur='checkApplyNum(this)' class='input-mini' type='text' name='number' /></td>"+
						"<td></td>"+
						"<td><input class='input-mini' type='text' name='applyPrice' /></td>"+
						/* "<td><input class='input-mini' type='text' name='replyPrice' /></td>"+ */
						"<td><input class='input-mini' type='text' name='supportMoney' /></td>"+
						"<td><input class='input-mini' type='text' name='compProduct' /></td>"+
						"<td><input class='input-mini' type='text' name='compPrice' /></td>"+
						"<td>"+
							"<a href='javascript:void(0);' onclick='addTemp2Tr(this)'  class='operate operate2'>添加</a>"+
							"<a href='javascript:void(0);' onclick='delTempTr(this)' class='operate operate2'>删除</a>"+
						"</td>"+
				"</tr>"
    	);
        $(".product-inp").autocomplete({
            source: availableTags
        });
    }
    
    //删除模板2的行
    delTempTr = function(dom){
		$(dom).parent().parent().remove();
	}
    
    //添加模板1的行
    addTemp1Tr = function(dom){
    	var the = $(dom).parent().parent();
    	the.after("<tr class='no'>"+
						"<td></td>"+
						"<td>"+
							"<input type='hidden' name='productPriceId' value='' />"+
							"<input onBlur='getTemp1ByProduct(this)' class='input-mini product-inp' type='text' name='product' value='' />"+
							"<input type='hidden' value='' name='sellin' />"+
						"</td>"+
						"<td>"+
						"</td>"+
						"<td>"+
							"<input onBlur='checkApplyNum(this)' class='input-mini' type='text' name='number' />"+
						"</td>"+
						"<td></td>"+
						"<td>"+
							"<input class='input-mini' type='text' name='compProduct' />"+
						"</td>"+
						"<td>"+
							"<input class='input-mini' type='text' name='compPrice' />"+
						"</td>"+
						"<td>"+
							"<input class='input-mini' type='text' name='applyPrice' />"+
						"</td>"+
						"<td>"+
							"<a href='javascript:void(0);' onclick='addTemp1Tr(this)'  class='operate operate2'>添加</a>"+
							"<a href='javascript:void(0);' onclick='delTempTr(this)' class='operate operate2'>删除</a>"+
						"</td>"+
				"</tr>"
			);
        $(".product-inp").autocomplete({
            source: availableTags
        });
    }
    
    //申请数量不能大于可申请数量
    checkApplyNum = function(dom){
    	var applyNum = $(dom).val();
    	if(applyNum==''){
    		alert('申请台数不能为空！');
    		$(dom).focus();
    		return;
    	}
    	if(isNaN(parseInt(applyNum))){
    		alert("申请台数必须为数字!");
    		$(dom).focus();
    		return;
    	}
    	var sellin = $(dom).parent().prev().html();
    	if(parseInt(applyNum)>parseInt(sellin)){
    		alert("申请台数必须小于当前可用台数!");
    		$(dom).focus();
    		return;
    	}
    }
    
    //模板1根据型号名查询 
    getTemp1ByProduct = function(dom){
    	var productName = $(dom).val();
    	var agentId = $("#agent-sel option:selected").val();
    	if(agentId=='-1'){
    		 alert("请选择代理!");
    		 $("#agent-sel").focus();
    		 return;
    	}
    	if(productName==''){
    		alert("型号名称不能为空！");
    		$(dom).focus();
    		return;
    	}
		$.ajax({
			type : "POST",
			url : "${ROOT}/b2i/project/getProductPrice",
			data : {
				productName : productName,
				agentId : agentId
			},
			dataType : "json",
			success : function(json) {
 				if(json.status=="OK"){
					$(dom).prev().val(json.data.id);
					$(dom).parent().prev().html(json.data.product.id);
					$(dom).parent().next().html(json.sellin);
					$(dom).next().val(json.sellin);
					$(dom).parent().next().next().next().html(json.data.interPublicPrice);
				}else{
					alert(json.errors[0].msg);
					$(dom).focus();
				}
			}
		});
    	
    }
    
    
    $("select[name=agentId]").change(function(){
    	if($(this).val()!=-1){
    		$.ajax({
    			type : "POST",
				url : "${ROOT}/b2i/agent/change",
				data : "agentId=" + $(this).val(),
				dataType : "json",
				success : function(data) {
					 $("#pcode").append("<input type='text' class='form-control text' name='pCode' value=" + data.projectCode + " readonly />");
					 $("#city").append(data.agent.commCity.cityName);
					 $.each(data.customerList,function(i,obj){
						 var cusHtml = "";
						 cusHtml = cusHtml + "<option value="+obj.id+" person="+ obj.person +" mobile=" + obj.mobile +" phone=" + obj.phone + " zipcode=" + obj.zipcode + " address=" + obj.address + ">"+ obj.name +"</option>";
						 $("select[name=customerId]").append(cusHtml);
						 $("select[name=temp1CustomerId]").append(cusHtml);
					 });
					 $.each(data.productSellInVolist,function(i,obj){
						 var productHtml = "";
						 if(typeof(obj.product.publicPrice)=="undefined"){
							 publicPrice="";
						 }else{
							 publicPrice = obj.product.publicPrice;
						 }
						 productHtml = productHtml + "<option value=" + obj.product.id +" sellin=" + obj.sellIn + " publicprice=" + publicPrice + " >" + obj.product.name +"</option>";
						 $("#product").append(productHtml);
					 });
					 $('.selectpicker').selectpicker({
					        'selectedText': 'cat'
					 });
					 $.each(data.siAgentList,function(i,obj){
						 var siHtml = "";
						 siHtml = siHtml + "<option value=" + obj.id + " siperson=" + obj.irName + " siphone=" + obj.phone + " siemail=" + obj.email +" >" + obj.irName +"</option>";
						 $("select[name=commSi]").append(siHtml);
						 $("select[name=temp1CommSi]").append(siHtml);
					 });
				}
    		});
    	}else{
    		$("#pcode").empty();
			 $("#city").empty();
    		$("select[name=customerId]").empty().append("<option value='-1' person='' mobile='' phone='' zipcode='' address=''>请选择客户</option>");
    		var attrcst = $("select[name=customerId]").find("option:selected").get(0).attributes;
    		for(var i=1; i<attrcst.length; i++){
    			$("#"+attrcst[i].name).text("");
    		}
    		$("select[name=commSi]").empty().append("<option value='-1' siperson='' siphone='' siemail=''>请选择SI</option>");
    		var attrsi = $("select[name=commSi]").find("option:selected").get(0).attributes;
    		for(var i=1; i<attrsi.length; i++){
    			$("#"+attrsi[i].name).text("");
    		}
    	}
    });
     $("#form").submit(function(){
    	var templateType = $("#template-inp").val();
    	if(templateType==1){
        	if($("input[name=pName]").val()==""){
    			alert("项目名称不能为空！");
    			$("input[name=pName]").focus();
    			return false;
    		}
        	if($("select[name=agentId] option:selected").val()==-1){
        		alert("请选择代理商！");
        		$("select[name=agentId]").focus();
    			return false;
        	}
        	if($("input[name=purchaseTime]").val()==""){
    			alert("请选择预计采购时间！");
    			$("input[name=purchaseTime]").focus();
    			return false;
    		}
        	if($("#temp1TrCustSel option:selected").val()==-1){
        		alert("请选择客户！");
        		$("select[name=customerId]").focus();
    			return false;
        	}
        	if($("#temp1TrCommSiSel option:selected").val()==-1){
        		alert("请选择SI！");
        		$("select[name=commSi]").focus();
    			return false;
        	}
        	if($("textarea[name=projectContext]").val()==""){
    			alert("请填写项目背景！");
    			$("textarea[name=projectContext]").focus();
    			return false;
    		}
        	if($("textarea[name=projectImportant]").val()==""){
    			alert("请填写项目重要性！");
    			$("textarea[name=projectImportant]").focus();
    			return false;
    		}
        	if($("textarea[name=competeCondition]").val()==""){
    			alert("请填写竞争对手情况！");
    			$("textarea[name=competeCondition]").focus();
    			return false;
    		}
        	if($("textarea[name=applyReason]").val()==""){
    			alert("请填写申请理由！");
    			$("textarea[name=applyReason]").focus();
    			return false;
    		}
        	var errorsNum = 0;
        	$("input[name=product]").each(function(){
        		var productName = $(this).val();
        		if(productName==''){
        			errorsNum++;
        			$(this).focus();
        			alert("型号名称不能为空！");
        			return;
        		}
        	});
        	if(errorsNum>0){
        		return false;
        	}
        	if(!errors("number")){
    			alert("申请台数不能为空！");
    			return false;
    		}
        	if(!errors("compProduct")){
    			alert("竞品品牌不能为空！");
    			return false;
    		}
        	if(!errors("compPrice")){
    			alert("竞品价格不能为空！");
    			return false;
    		}
        	if(!errors("customerPrice")){
    			alert("客户需求价格不能为空！");
    			return false;
    		}
        	if(!errors("applyPrice")){
    			alert("申请价格不能为空！");
    			return false;
    		}
        	
        	if(!errors("applyPrice")){
    			alert("申请价格不能为空！");
    			return false;
    		}
        	/* if($("textarea[name=specificSupportInfo]").val()==""){
    			alert("请填写特殊支持！");
    			$("textarea[name=specificSupportInfo]").focus();
    			return false;
    		} */
    		
    		if($("input[name=files]")[0].value.length == 0){
    				alert("请上传图片");
    				return false;
    		}
    	}
    	if(templateType==2){
        	if($("input[name=pName]").val()==""){
    			alert("项目名称不能为空！");
    			$("input[name=pName]").focus();
    			return false;
    		}
        	if($("select[name=agentId] option:selected").val()==-1){
        		alert("请选择代理商！");
        		$("select[name=agentId]").focus();
    			return false;
        	}
        	if($("#temp2TrCustSel option:selected").val()==-1){
        		alert("请选择客户！");
        		$("#temp2TrCustSel").focus();
    			return false;
        	}
        	if($("#temp2TrCommSiSel option:selected").val()==-1){
        		alert("请选择SI！");
        		$("#temp2TrCommSiSel").focus();
    			return false;
        	}
        	var errorsNum = 0;
        	$("input[name=product]").each(function(){
        		var productName = $(this).val();
        		if(productName==''){
        			errorsNum++;
        			$(this).focus();
        			alert("型号名称不能为空！");
        			return;
        		}
        	});
        	if(errorsNum>0){
        		return false;
        	}
        	if(!errors("number")){
    			alert("申请台数不能为空！");
    			return false;
    		}
        	if(!errors("applyPrice")){
    			alert("申请价格不能为空！");
    			return false;
    		}
        	if(!errors("supportMoney")){
    			alert("支持金不能为空！");
    			return false;
    		}
        	if(!errors("compProduct")){
    			alert("竞品品牌不能为空！");
    			return false;
    		}
        	if(!errors("compPrice")){
    			alert("竞品价格不能为空！");
    			return false;
    		}
        	if($("textarea[name=explain]").val()==""){
    			alert("请填写赛事活动方案说明！");
    			$("textarea[name=explain]").focus();
    			return false;
    		}
    	}
    });
});
function infoChange(obj){
	var attr = $(obj).find("option:selected").get(0).attributes;
	for(var i=1; i<attr.length; i++){
		$("#"+attr[i].name).text(attr[i].value);
	}
}
function errors(para){
	var error = 0;
	$("input[name="+para+"]").each(function(){
		if(this.value==""){
			error++;
			return false;
		}
	});
	if(error==0){
		return true;
	}
}
</script>
</html>