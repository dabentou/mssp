<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8"/>
		<title>客户列表</title>
		<%@include file="/WEB-INF/include/include.jsp" %>
		<%@include file="/WEB-INF/include/header.jsp" %>
		<%@include file="/WEB-INF/include/func.jsp" %>
	</head>
	<body>
		<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content">
		
		<input type="submit" onclick="newCustomer()" value="新增客户" class="button button-lightblue" />
		
		<div class="table-responsive">
			<table class="maintable table table-hover">
			<thead>
				<tr>
					<th>编号</th><th>姓名</th><th>联系地址</th><th>邮政编码</th><th>联系人</th>
					<th>联系电话</th><th>手机</th><th>所属代理商</th><th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}"  var="customer" >
					<tr>
						<td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.zipcode}</td><td>${customer.person}</td>
						<td>${customer.phone}</td><td>${customer.mobile}</td><td>${customer.agent.irName}</td>
						<td>
							<a href="javascript:void(0);"  onclick="editCustomer('${customer.id}')" class="operate operate4"><i></i>编辑</a>
							<a href="javascript:void(0);"onclick="deleteCustomer('${customer.id}','${customer.name}')" class="operate operate2"><i></i>删除</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		</div>
		</div>
		</div>
	</body>
</html>
<script>
	function newCustomer(){
		location.href = "${ROOT}/b2b/customer/new.html";
	}
	
	function deleteCustomer(id,name){
		if(confirm("确定要删除" + name + "吗?")){
			location.href = "${ROOT}/b2b/customer/deleate?id=" + id;
		}
	}
	
	function editCustomer(id,name){
			location.href = "${ROOT}/b2b/customer/edit?id=" + id;
	}
</script>
