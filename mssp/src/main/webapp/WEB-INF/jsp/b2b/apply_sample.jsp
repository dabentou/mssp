<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>样机申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
			
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
					<c:forEach items="${list}" var="item">
						<tr>
							<td>${item.product.name}</td>
							<td>${item.total}</td>
							<td>${item.balance}</td>
							<td><input type="text" name="inputSellout"
								onblur="checkSellOut(this)" <%-- value="${item.total }" --%>
								placeholder="0" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


<hr />
<hr />
		<form class="form-horizontal" id = "form"  action="${ROOT}/a/input" enctype="multipart/form-data"  method="post" >
				<div class="form-group">
					<label class="control-label">导入类型：</label>
					<div class="controls">
						<input type="text" class="select form-control"
							placeholder="样机借用协议" name="channelType">
					</div>
				</div>

				<div class="form-group">
					<label class="control-label">选择文件：</label>
					<div class="controls">
						<input type="file" id="file" name="file" />
					</div>
				</div>

				<div class="controls">
					<input type="submit" id="submit" class="button button-lightblue"
						value="确定提交" />
				</div>



			</form>

			<img src = "${img}"/>
		</div>
  </div>
</body>
</html>
<script type="text/javascript">
	

	$(document).ready(function(){
		
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




