<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta charset="UTF-8"/>
		<title>二级代理商列表</title>
		<%@include file="/WEB-INF/include/include.jsp" %>
		<%@include file="/WEB-INF/include/header.jsp" %>
		<%@include file="/WEB-INF/include/func.jsp" %>
	</head>
	<body>
		<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content">
		
		<input type="submit" onclick="newAgent()" value="新增二级代理商" class="button button-lightblue" />
		
		<div class="table-responsive">
			<table class="maintable table table-hover">
			<thead>
				<tr>
					<th>编号</th><th>名称</th><th>城市</th><th>状态</th>
					<th>积分</th><th>创建时间</th><th>联系电话</th><th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}"  var="agent" >
					<tr>
						<td>${agent.id}</td><td>${agent.irName}</td><td>${agent.commCity.cityName}</td>
						<td><c:choose><c:when test="${agent.irStatus==0}">未冻结</c:when><c:when test="${agent.irStatus==1}">已冻结</c:when><c:when test="${agent.irStatus==2}">已流失</c:when></c:choose></td>
						<td>${agent.score}</td><td><fmt:formatDate value="${agent.createtime}" pattern="yyyy-MM-dd" /></td><td>${agent.phone}</td>
						<td>
							<a href="javascript:void(0);"  onclick="editAgent(${agent.id})" class="operate operate4"><i></i>编辑</a>
							<a href="javascript:void(0);"onclick="deleteAgent('${agent.id}','${agent.irName}')" class="operate operate2"><i></i>删除</a></td>
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
	function newAgent(){
		location.href = "${ROOT}/b2i/agent/new.html";
	}
	
	function deleteAgent(id,irName){
		if(confirm("确定要删除" + irName + "吗?")){
			location.href = "${ROOT}/b2i/agent/delete?id=" + id;
		}
	}
	
	function editAgent(id){
			location.href = "${ROOT}/b2i/agent/edit?id=" + id;
	}
</script>
