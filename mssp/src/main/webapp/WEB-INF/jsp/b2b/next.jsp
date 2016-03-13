<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
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
			$("#save").click(function(){
				if(valiForm()){
					$("#myFrom").submit();
				}
			});
			valiForm=function(){
				var pType = document.getElementById('pType').value;
				if(pType == 2){
					$(".num1").each(function(){
						var total = $(this).val();//总数
						var nums = $(this).parent("td").siblings("td").find("input");
						var sum=0;
						for(var i = 0;i< nums.length;i++){
							sum=sum*1 + nums[i].value*1;
						}
						if(total != sum){
							alert("请重新核对型号月份统计");
							return false;
						}
					});
				} 
				if($("#priceValidBeginTime").val()==null||$("#priceValidBeginTime").val()==""){
					alert("价格适用开始时间不能为空！");
					$("#priceValidBeginTime").focus();
					return false;
				}
				if($("#priceValidEndTime").val()==null||$("#priceValidEndTime").val()==""){
					alert("价格适用结束时间不能为空！");
					$("#priceValidEndTime").focus();
					return false;
				}
				var count=0;
				$("input[name=applyPrice]").each(function(){
					if($(this).val()==null||$(this).val()==""){
						count++;
						alert("申请单价不能为空！");
						return false;
					}
				});
				if(count>0){
					return false;
				}
				return true;
			}
			//计算折扣率  公式：（公开价 - 申请单价）/ 公开价
			calDiscountRate=function(dom,id){
				var the=$(dom);
				if(isNaN(the.val())){
					alert("申请单价只能位数字！");
					the.val("");
					return;
				} 
				var pPrice=$("#pPrice"+id+"").html();//公开价
				if(pPrice==0){
					$("#discountRate"+id+"").val(0+"%");
					return;
				}
				var applyPrice=the.val();//申请单价
				var discountRate =applyPrice/pPrice*100;
				$("#discountRate"+id+"").val(discountRate.toFixed(2)+"%");
			}
	});
	</script>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content"  align="center">
      	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/next"  enctype="multipart/form-data" method="post" >
      	<!-- <input type="hidden" name="subType"   id="subType" /> -->
      	<input type="hidden" name="projectId"  value="${project.id }"/>
      	<table class="maintable table table-hover"  style="width: 65%;" border="1">
      	<tr><td colspan="6"><span class="project_title">特殊支持申请</span></td></tr>
      		<tr>
      			<td>申请日期：</td>
      			<td colspan="2"> <fmt:formatDate value="${project.applyTime }"  pattern="yyyy-MM-dd HH:mm:SS"/></td>
      			<td> 项目编号 ：</td>
      			<td colspan="2" style="font-weight: bold;">${project.pCode }</td>
      		</tr>
      		<tr>
      			<td>类型：</td>
      			<td>
	      			<select  class="input_style" name="pType" id="pType">  
	      				<option value="1">一次性申请单</option>
	      				<option value="2">长期申请单</option>
	      			</select>
      			</td>
      			<td> 投标时间：</td>
      			<td> <fmt:formatDate value="${project.pBidTime }"  pattern="yyyy-MM-dd"/> </td>
      			<td>价格适用时间：</td> 
      			<td> <input type="text" class="form-control text datetimepicker"  style="width: 100px;height: 22px;"   name="priceValidBeginTime" id="priceValidBeginTime"/> -
      						<input type="text" class="form-control text datetimepicker"  style="width: 100px;height: 22px;"   name="priceValidEndTime" id="priceValidEndTime"/>
      			</td> 
      		</tr>
      		<tr>
      			<td>客户名称：</td>
      			<td><input name="customerName" type="text" value="${project.customer.name }" style="width:100px;"/></td>
      			<td> 联系人：</td>
      			<td> <input name="customerLinkman" type="text" value="${project.customer.person }"  style="width:90px;"/> </td>
      			<td>电话：</td> 
      			<td><input type="text" name="customerPhone"  value="${project.customer.phone }"  style="width:150px;"/></td> 
      		</tr>  
      		<tr>
      			<td>二级代理商：</td>
      			<td>${project.commSi.irName }</td>
      			<td> 联系人：</td>
      			<td> ${project.commSi.irName } </td>
      			<td>电话：</td> 
      			<td>${project.commSi.phone }</td> 
      		</tr>
      		<tr>
      			<td colspan="2">项目决策人 决策链 情况说明：</td>
      			<td colspan="4"><textarea rows="2" cols="80" name="decisionInfo" ></textarea> </td>
      		</tr>
      		<tr>
      			<td colspan="2">项目名称：</td>
      			<td colspan="4">${project.pName }  </td>
      		</tr>
      		<tr>
      			<td colspan="6">
      				<textarea id="applyReason" name="applyReason" style="width:759px;height:300px;">
								申请原因	1：应对竞品   
								2：项目情况说明及所需支持      
						</textarea>
      			</td>
      		</tr>
      		<tr>
      		<td colspan="6">
      			<table  class="maintable table table-hover"  style="width: 100%;" border="1">
      				<tr><td>No</td><td>型号</td><td>公开价</td><td>NET价</td><td>申请单价(元)</td><td>数量(台)</td><td>折扣率(%)</td><td>赢单率(%)</td><td>备注</td><td>竞品型号</td><td>竞品价格</td></tr>
      				<c:forEach items="${saList }" var="item">
      					<tr>
      						<td>${item.id}<input type="hidden" name="saId" value="${item.id}" /></td>
      						<td>${item.productPrice.product.name}</td>
      						<td id="pPrice${item.id }">${item.productPrice.b2bPublicPrice }</td>
      						<td>${item.productPrice.netPrice }</td>
      						<td><input type="text" name="applyPrice"  style="width: 50px"  onblur="calDiscountRate(this,${item.id})"/></td>
      						<td>${item.number }</td>
      						<td><input type="text" id="discountRate${item.id }"  name="discountRate" readonly="readonly"  style="width: 50px"  value="" /></td>
      						<td>
								<select name="winRate">
									<option value="100">100%</option>
									<option value="95">95%</option>
									<option value="90">90%</option>
									<option value="85">85%</option>
									<option value="80">80%</option>
									<option value="75">75%</option>
									<option value="70">70%</option>
									<option value="65">65%</option>
									<option value="60">60%</option>
									<option value="55">55%</option>
									<option value="50">50%</option>
									<option value="45">45%</option>
									<option value="40">40%</option>
									<option value="35">35%</option>
									<option value="30">30%</option>
									<option value="25">25%</option>
									<option value="20">20%</option>
									<option value="15">15%</option>
									<option value="10">10%</option>
									<option value="5">5%</option>
									<option value="0">0%</option>
								</select>
							</td>
      						<td><input type="text" name="remark"  /></td>
      						<td>${item.competeProduct }</td>
      						<td>${item.competePrice }</td>
      					</tr>
      				</c:forEach>
      			</table>
      			</td>
      		</tr>
      		
      		<tr name = "tr3"  style="display:none"><td colspan="6">型号数量月份统计</td></tr>
      		<tr name = "tr3" style="display:none">
      			<td colspan="6">
      			<table  class="maintable table table-hover table-small"  style="width: 100%;" border="1">
      				<tr><td>型号</td><td>数量</td><td>1月</td><td>2月</td><td>3月</td><td>4月</td><td>5月</td><td>6月</td><td>7月</td><td>8月</td><td>9月</td><td>10月</td><td>11月</td><td>12月</td>   </tr>
      				<c:forEach items="${saList }" var="item">
      					<tr>
      						<td>${item.productPrice.product.name }</td>
      						<td><input type="text" id="totalNum" class="num1" value="${item.number}" readonly/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }1"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }2"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }3"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }4"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }5"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }6"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }7"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }8"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }9"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }10"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }11"/></td>
      						<td><input type = "text" class="num" name="${item.productPrice.product.name }12"/></td>
      					</tr>
      				</c:forEach>
      			</table>
      			</td>
      		</tr>
      		<tr>
      			<td >上传图片：</td>
      			<td colspan="5">  <input type="file" name="files"></td>
      		</tr>
      		
      		<tr>
      			<td colspan="6"> <input id="save" type="button" class="button button-lightblue" value="提交" /><input type="hidden" name="subType" value="4"/></td>  
      		</tr>
      	</table>
        </form>
    </div>
  </div>
  
    <script charset="utf-8" src="${ROOT}/js/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${ROOT}/js/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${ROOT}/js/kindeditor/plugins/code/prettify.js"></script>
</body>
<script type="text/javascript">
KindEditor.ready(function(K) {
	K.create('#applyReason', {
		uploadJson : '${ROOT}/editor/image/upload',
		allowFileManager : true,
		afterBlur: function(){this.sync()}
	});
});
$(document).ready(function(){
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
});
</script>
</html>
<script type="text/javascript">
	$(document).ready(function(){
		var authorization = ${project.pIsBid };
		if(authorization == 0){
			var tr = $("tr[name=tr1]");
			for (var i=0; i<tr.length; i++) {
				tr[i].style.display="none";
			}
		}
		var sample = ${project.proto };
		if(sample == 0){
			var trs = $("tr[name=tr2]");
			for (var i=0; i<trs.length; i++) {
				trs[i].style.display="none";
			}
		}
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
		
		
		
	})
	
	$('#pType').change(function(){
			/* val type = $('#pType').val; */
			var type = document.getElementById('pType').value;
			if(type == 2){
				var date = new Date();
				var y = date.getFullYear();
				var m = date.getMonth()+1;
				$("#priceValidBeginTime").val(y+"-"+m);
				$("#priceValidEndTime").val(y+1+"-"+m);
				var trs = $("tr[name=tr3]");
				for (var i=0; i<trs.length; i++) {
					 trs[i].style.display="";
				}
			}
			if(type == 1){
					var trss = $("tr[name=tr3]");
					for (var j=0; j<trss.length; j++) {
						 trss[j].style.display="none";
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
	
	
</script>
