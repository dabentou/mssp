<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>样机申请</title>
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
		border:1px none #ddd;
		}
		.table table td{
		border:1px solid #ddd;
		}
		.table th{
		border:1px solid #ddd
		}
		.input-mini{
			width: 30px;
		}
		.input-long{
			width: 600px;
		}
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
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
      	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/next" enctype="multipart/form-data"  method="post" >
	      	<input id="projectId" type="hidden" name="projectId"  value="${project.id }"/>
	      	<table class="maintable table table-hover"  style="width: 65%;">
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<h3>样机借用协议</h3>
		      		</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td>
		      			<p class="text-left"><span class="project_title">需 方： ${project.agent.irName} </span></p>
		      			<p class="text-left"><span class="project_title">供 方：飞生（上海）电子科技有限公司 </span></p>
					</td>
					<td>
		      			<p class="text-left"><span class="project_title">编号：${project.pCode }</span></p>
		      			<p class="text-left"><span class="project_title">日期：<fmt:formatDate value="${project.pBidTime}" pattern="yyyy-MM-dd" /></span></p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
						<p class="text-left"><span class="project_title">一、借用双方根据《合同法》及相关法律规定，经友好协商，现确定如下事项：</span></p>
					      	<table class="maintable table table-hover" >
					      		<thead>
					      			<tr>
							      		<th>客户名称</th>
							      		<th>产品型号</th>
							      		<th>样机总量</th>
										<th>当前可使用数量</th>
							      		<th>申请数 量</th>
							      		<th>代理商公司名称</th>
							      		<th>备 注</th>
						      		</tr>
					      		</thead>
					      		<tbody id="tbody-pro">
						      		<c:forEach items="${sList}" var="item" varStatus="itemVs">
	     								<c:choose>
										   <c:when test="${itemVs.index==0}">
										   		<tr name = "tr1" >
										      		<td><%-- ${project.customerName } --%></td>
										      		<td>${item.product.name}<input id="productId" type="hidden" name="productId" value="${item.id}"/></td>
										      		<td>${item.total}</td>
													<td>${item.balance}</td>
										      		<td><input onchange="count(this)" type="text" name="number" value="0"/></td>
										      		<td>${project.agent.irName}</td>
										      		<td rowspan="${fn:length(saList)+1}">样机申请</td>
								      			</tr>
										   </c:when>
										   <c:otherwise>
										   		<tr name = "tr1" >
												   	<td><%-- ${project.customerName } --%></td>
										      		<td>${item.product.name}<input id="productId" type="hidden" name="productId" value="${item.id}"/></td>
										      		<td>${item.total}</td>
													<td>${item.balance}</td>
										      		<td><input onchange="count(this)" id="proNum" type="text" name="number" value="0"/></td>
										      		<td>${project.agent.irName}</td>
										      	</tr>
										   </c:otherwise>
										</c:choose>
									</c:forEach>
							      	<tr name = "tr1" >
							      		<td colspan="1">总计</td>
							      		<td colspan="4" id="sumNum"></td>
							      	</tr>
					      		</tbody>
					      	</table>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
						<p class="text-left">
							二、1、出货时间：<input class="bordernone" type="text" name="sendTime"/><br/>
							&nbsp;&nbsp;2、收 件 人：<input class="bordernone" type="text" name="receiveName" value="${project.receiveName}"/><br/>
							&nbsp;&nbsp;&nbsp; 地 址：<input class="bordernone" type="text" name="receiveAddress" value="${project.receiveAddress}"/><br/>
							&nbsp;&nbsp;4、借用时间：从<input class="input-mini bordernone" type="text" onchange="getDays()" name="startYear"/>年<input class="input-mini bordernone" type="text" onchange="getDays()" name="startMon"/>月<input class="input-mini bordernone" type="text" onchange="getDays()" name="startDay"/>日
							 至<input class="input-mini bordernone" type="text" onchange="getDays()" name="endYear"/>年<input class="input-mini bordernone" type="text" onchange="getDays()" name="endMon"/>月<input class="input-mini bordernone" type="text" name="endDay" onchange="getDays()"/>日，共计<input class="input-mini bordernone" type="text" name="countDays"/>天<br/>
						</p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">三、归还规定：</span></p>
						<p class="text-left">
							&nbsp;&nbsp;&nbsp;1、需方应在协议约定的试用期限内归还给供方，逾期未还超过三天的，供方有权视需方此行为为购买，该试用合同自动转为销售合同，押金转为销售款。未交押金的需方应在三天内将货款汇入供方指定帐号。<br/>
							&nbsp;&nbsp;&nbsp;2、需方在试用期限内交还的产品，必须保证其产品包装、外观、配件的完整性和完好性，以不影响供方的二次销售为原则。<br/>
							&nbsp;&nbsp;&nbsp;3、如果需方在试用的期限内交还的产品有所损坏，供方有权追究需方的赔偿责任。<br/>
							&nbsp;&nbsp;&nbsp;4.需方可申请延长样机展示时间，须另外签订新协议。<br/>
						</p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">四、所有权与风险转移：</span></p>
						<p class="text-left">
							&nbsp;&nbsp;&nbsp;1、在需方支付全部货款前，货物的所有权归供方所有。如需方未能按合同约定的期限归还或付款的，供方有权以任何方式将货物收回，需方须承担因违约而给供方造成的经济损失，包括但不限于违约金、收回货物的运费及其他费用等。<br/>
							&nbsp;&nbsp;&nbsp;2、货物的风险责任自供方发出货物或需方提货之日起转移至需方承担。<br/>
						</p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">五、解决合同纠纷的方式：</span>双方协商或调解不成由供方所在地法院裁决。</p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">六、版本及生效：</span>本合同正本一式二份，双方各执一份，传真件同样有效。本合同自双方签字盖章之日起生效。</p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td colspan="6">
		      			<p class="text-left"><span class="project_title">七、其它：</span><input type="text" name="otherText" class="input-long bordernone"/></p>
					</td>
		      	</tr>
		      	<tr name = "tr1" >
		      		<td>
		      			<p class="text-left"><span class="project_title">需 方：</span></p>
		      			<p class="text-left">（盖章）</p><br/><br/><br/><br/><br/>
		      			<p class="text-left">签约代表人：</p>
		      			<p class="text-left">电话：</p>
		      			<p class="text-left">传真：</p>
		      			<p class="text-left">地址：</p>
					</td>
					<td>
		      			<p class="text-left"><span class="project_title">供方：</span></p>
		      			<p class="text-left">（盖章）</p><br/><br/><br/><br/><br/>
		      			<p class="text-left">签约代表人：</p>
		      			<p class="text-left">电话：</p>
		      			<p class="text-left">传真：</p>
		      			<p class="text-left">地址：</p>
					</td>
		      	</tr>
 <%--       		<tr>
		      		<td colspan="6">
	      			<table class="table table-hover maintable" id="myTable">
					<thead>
						<tr>
							<th>样机型号 ：<span style="font-weight: normal;"><input
									id="productFilter" type="text" style="width: 80px;"
									placeholder="型号过滤" /></span></th>
							<th>样机总量</th>
							<th>当前可使用数量</th>
							<th>申请数量</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lists}" var="item">
							<tr>
								<td>${item.product.name}<input type="hidden" name=sampleId value="${item.id }"></td>
								<td>${item.total}</td>
								<td>${item.balance}</td>
								<td><input type="text" name="sampleData"
									onblur="checkSellOut(this)" 
									value ="0" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
	      		</td>
	      		</tr> --%>
	      		<!-- <tr>
			      		<td colspan="2">样机协议盖章扫描件上传：</td>
			      		<td colspan="4"><input type="file" id="files" name="files"/><input type="hidden" name="subType" value="3"/></td>
	      		</tr> -->
	      		<tr>
	      				<td colspan="2"><span class="star">*</span>样机协议盖章扫描件上传：<input type="hidden" name="subType" value="3"/></td>
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
			var number = '';
			var productId='';
/* 			$("input[name=productId]").each(function(){
				if($(this).val()!=''){
					productId += $(this).val()+",";
				}
			});; */
			
			$("input[name=number]").each(function(){
				if($(this).val()!=''){
					productId += $(this).parent().parent().find("#productId").val()+",";
					number += $(this).val()+",";
				}
			});
			if(number==''){
				alert("请填写要申请的样机数量！");
				return false;
			}
			var sendTime = $("input[name=sendTime]").val();
			var receiveName = $("input[name=receiveName]").val();
			var receiveAddress = $("input[name=receiveAddress]").val();
			var startYear = $("input[name=startYear]").val();
			var startMon = $("input[name=startMon]").val();
			var startDay = $("input[name=startDay]").val();
			var endYear = $("input[name=endYear]").val();
			var endMon = $("input[name=endMon]").val();
			var endDay = $("input[name=endDay]").val();
			var countDays = $("input[name=countDays]").val();
			var otherText = $("input[name=otherText]").val();
			var countNum = $("#sumNum").html();
			
			location.href = "${ROOT}/b2b/project/sampleDeal/exportPDF?projectId="+projectId+"&productId="+productId+"&number="+number+"&sendTime="+sendTime+"&receiveName="+receiveName+"&receiveAddress="+receiveAddress+"&startYear="+startYear+"&startMon="+startMon+"&startDay="+startDay+"&endYear="+endYear+"&endMon="+endMon+"&endDay="+endDay+"&countDays="+countDays+"&otherText="+otherText+"&countNum="+countNum;
		});
		
 		$("#save").click(function(){
			var file = $("input[name=files]");
			var samples = ${project.proto };
			if(samples == 1){
				if(file[0].value.length == 0){
					alert("请上传样机借用协议");
					return false;
				}
			}
		});
		
		$('#productFilter').bind('input propertychange',function(){
			var pf =$("#productFilter").val();
			var list=$("#myTable tbody tr").find("td:eq(0)");
			for(var i = 0;i<list.length;i++){
				var tr = $(list[i]).parent();
				if($(list[i]).text().indexOf(pf.toUpperCase()) <0){
					$(tr).hide();
				}else{
					$(tr).show();
				}
			}
		})
		
		checkSellOut = function(dom){
			var the = $(dom);
			if(isNaN(the.val())){
				alert("申请数量只能为数字！");
				the.focus();
				return;
			}
			var pi = the.parent().prev().html();
			if(the.val()>parseInt(pi)){
				alert("申请数量不能大于当前可使用数量！");
				the.focus();
				return;
			}
		}
		
		count = function(dom){
			var the = $(dom);
			var countNum=0;
			$("input[name='number']").each(function(){
				if(!$(this).val()==''){
					if(isNaN($(this).val())){//不是数字 
						alert('样机数量必须为数字！');
						$(this).focus();
					return false;
					}
					var pi = the.parent().prev().html();
					if(the.val()>parseInt(pi)){
						alert("申请数量不能大于当前可使用数量！");
						the.focus();
						return;
					}
					countNum += parseInt($(this).val());
				}
			});
			if(countNum!=0){
				$("#sumNum").html(countNum);
			}
		} 
		
		getDays = function(){
			var startYear = $("input[name='startYear']").val();
			var startMon = $("input[name='startMon']").val();
			var startDay = $("input[name='startDay']").val();
			var endYear = $("input[name='endYear']").val();
			var endMon = $("input[name='endMon']").val();
			var endDay = $("input[name='endDay']").val();
			if(startYear==''){
				//alert('借用时间开始年份不能为空！');
				$("input[name='startYear']").focus();
				return false;
			}
			if(isNaN(parseInt(startYear))){
				alert("借用时间开始年份必须为数字！");
				$("input[name='startYear']").focus();
				return false;
			}
			if(startMon==''){
				//alert('借用时间开始月份不能为空！');
				$("input[name='startMon']").focus();
				return false;
			}
			if(isNaN(parseInt(startMon))){
				alert("借用时间开始月份必须为数字！");
				$("input[name='startMon']").focus();
				return false;
			}
			if(startDay==''){
				//alert('借用时间开始日不能为空！');
				$("input[name='startDay']").focus();
				return false;
			}
			if(isNaN(parseInt(startDay))){
				alert("借用时间开始日必须为数字！");
				$("input[name='startDay']").focus();
				return false;
			}
			if(endYear==''){
				//alert('借用时间结束年份不能为空！');
				$("input[name='endYear']").focus();
				return false;
			}
			if(isNaN(parseInt(endYear))){
				alert("借用时间结束年份必须为数字！");
				$("input[name='endYear']").focus();
				return false;
			}
			if(endMon==''){
				//alert('借用时间结束月份不能为空！');
				$("input[name='endMon']").focus();
				return false;
			}
			if(isNaN(parseInt(endMon))){
				alert("借用时间结束月份必须为数字！");
				$("input[name='endMon']").focus();
				return false;
			}
			if(endDay==''){
				//alert('借用时间结束日不能为空！');
				$("input[name='endDay']").focus();
				return false;
			}
			if(isNaN(parseInt(endDay))){
				alert("借用时间结束日必须为数字！");
				$("input[name='endDay']").focus();
				return false;
			}
			var startDate = startYear+"-"+startMon+"-"+startDay;
			var endDate = endYear+"-"+endMon+"-"+endDay;
			countDays = DateDiff(startDate,  endDate);
			if(isNaN(countDays)){
				alert("日期填写有误！");
				return false;
			}
			$("input[name='countDays']").val(countDays);
		}
		
	//计算日期差 
	   function  DateDiff(sDate1,  sDate2){
	       var  aDate,  oDate1,  oDate2,  iDays;  
	       aDate  =  sDate1.split("-");
	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]); 
	       aDate  =  sDate2.split("-");
	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);
	       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);//把相差的毫秒数转换为天数  
	       return  iDays  
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
