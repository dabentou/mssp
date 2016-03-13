<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-卖场维护</title>
<style>
	.budget{
		width:100px;
		text-align:center;
	}
</style>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
   		<div class="col-lg-12">
   		
   		<div class="form-group">
		      	<label>年份：</label>
		      	<input type="text" class="form-control text" id="year"  value="${year }">
		      	<label>月份：</label>
		      	<input type="text" class="form-control text" id="quarter"  value="${quarter }">
		</div>
        	
        	<div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>城市</th>
                                <th>R02</th>
                                <th>R03</th>
                                <th>R04</th>
                                <th>R05</th>
                                <th>R06</th>
                                <th>R07</th>
                                <th>R08</th>
                                <th>R09</th>
                                <th>R10</th>
                                <th>R11</th>
                                <th>R12</th>
                                <th>操作</th>
                                
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list }" var="item">
                            <tr>
                                <td>${item.commonProvince.province}<input type="hidden" name="cityName" value="${item.commonProvince.id}"/> </td>
                                <td><input type="text" class="budget" name="R02" old-value="${item.r02}" value="${item.r02}"/> </td>
                                <td><input type="text" class="budget" name="R03" old-value="${item.r03}" value="${item.r03}"/> </td>
                                <td><input type="text" class="budget" name="R04" old-value="${item.r04}" value="${item.r04}"/> </td>
                                <td><input type="text" class="budget" name="R05" old-value="${item.r05}" value="${item.r05}"/> </td>
                                <td><input type="text" class="budget" name="R06" old-value="${item.r06}" value="${item.r06}"/> </td>
                                <td><input type="text" class="budget" name="R07" old-value="${item.r07}" value="${item.r07}"/> </td>
                                <td><input type="text" class="budget" name="R08" old-value="${item.r08}" value="${item.r08}"/> </td>
                                <td><input type="text" class="budget" name="R09" old-value="${item.r09}" value="${item.r09}"/> </td>
                                <td><input type="text" class="budget" name="R10" old-value="${item.r10}" value="${item.r10}"/> </td>
                                <td><input type="text" class="budget" name="R11" old-value="${item.r11}" value="${item.r11}"/> </td>
                                <td><input type="text" class="budget" name="R12" old-value="${item.r12}" value="${item.r12}"/> </td>
                           		<td><a href="#" onclick="addBudget(this)">追加预算</a> 
                           			  <a href="#" onclick="logView(this)">日志查看</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
						<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
   		</div>
   </div>
</div>
</div>
<script type="text/javascript">
	
	function addBudget(dom){
		var year = $("#year").val();
		var quarter = $("#quarter").val();
		var jsonData ="&year="+year+"&quarter="+quarter+ "&provinceID="+$(dom).parents("tr").find("input[type=hidden]").val();
		var num = 0;
		$(dom).parents("tr").find("input[type=text]").each(function(){
			/* $(this).attr("name"));  获得要修改的字段类型*/
			/*  alert($(this).val()); 获得改变后的值*/
		 	/*  alert($(this).attr("old-value"));  	获得改变前的值*/
		 	if($(this).val() > $(this).attr("old-value")){
		 		num ++;
		 		jsonData +="&"+$(this).attr("name") +"=" + $(this).attr("name");
		 		jsonData +="&"+$(this).attr("name")+"newVal="+$(this).val();
		 		jsonData +="&"+$(this).attr("name")+"oldVal="+ $(this).attr("old-value");
		 	}
			
		});
		if(num >0){
			$.ajax({
				type : "POST",
				url : "${ROOT}/admin/iretail/budget/addBudget",
				data : jsonData,
				dataType : "json",
				success : function(json) {
					if(json.status=="OK"){
						alert("修改成功！");
						window.location.reload();
					}else{
						alert("修改失败！");
					}
				}
			});
		}else{
			alert("至少要修改一项，并且预算值只能变大");
		}
		
	}

		function logView(dom){
			var year = $("#year").val();
			var quarter = $("#quarter").val();
			var provinceId = $(dom).parents("tr").find("input[type=hidden]").val();
			window.location="${ROOT}/admin/iretail/budget/logView-"+year+"-"+quarter+"-"+provinceId;
		}

</script>
</body>
</html>
<script type="text/javascript">





















