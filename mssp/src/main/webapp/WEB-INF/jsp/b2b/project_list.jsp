<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="UTF-8" />
<title>项目申请</title>
<%@include file="/WEB-INF/include/include.jsp"%>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp"%>
	<%@include file="/WEB-INF/include/func.jsp"%>
	<div class="wrap">
		<%@include file="/WEB-INF/include/notice.jsp"%>
		<div class="content">
			<div class="filter">
		        <form class="form-inline" action="${ROOT}/b2b/project/search">
		        	<div class="form-group">
		            <label>项目编号：</label>
		            <input type="text" class="form-control text" name="ppn" value="${ppn }" />
		           </div>
		           <div class="form-group">
		            <label>申请时间：</label>
		           <%--  <input type="text" class="form-control text" name="applyTime" value="${applyTime }" /> --%>
		            <input value="${startApplyTime}" type="text" class="form-control text datetimepicker"  style="width: 100px;height: 30px;"   name="startApplyTime" />
		          	 - <input value="${endApplyTime}" type="text" class="form-control text datetimepicker"  style="width: 100px;height: 30px;"   name="endApplyTime" />
		           </div>
		          
		           <div class="form-group">
		            <label>状态：</label>
		            	<select class="form-control text" name="processStepId">
		            	<option value="">--请选择--</option>
			            	<c:forEach items="${processSteps}" var="item">
								<option value="${item.id}" <c:if test="${item.id==processStepId}">selected</c:if>>${item.operateStatus}</option>
			     			</c:forEach>
			     		</select>
		           </div>
		           
		           <div class="form-group">
		            <label>&nbsp;&nbsp;代理商：</label>
		            	<input type="hidden" name="type" value="${type}"/>
			     		<select class="form-control text" name="agentId">
				            <c:forEach items="${commAgents}" var="item">
								<option value="${item.id}" <c:if test="${item.id==agentId}">selected</c:if>>${item.irName}</option>
				     		</c:forEach>
			     		</select>
		          </div> 
		          &nbsp;&nbsp;<!-- <input onclick="getData()" type="button" value="查询" class="button button-lightblue" /> -->
		          			<input onclick="getData()" type="submit" value="查询" class="button button-lightblue" />
		        </form>
		      </div>
			<div class="table-responsive">
				<table class="maintable table table-hover">
					<thead>
						<tr>
							<th>ID</th>
							<th>项目名称</th>
							<th>项目编号</th>
							<th>城市</th>
							<th>投标</th>
							<th>申请时间</th>
							<th>申请代理商</th>
							<th>状态</th>
							<th>操作${sessionScope.user.commRole.id}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pagelist.content}" var="item">
							<tr>
								<td>${item.id}</td>
								<td>${item.pName }</td>
								<td>${item.pCode}</td>
								<td>${item.agent.commCity.cityName}</td>
								<td>${item.pIsBid==1?"是":"否"}</td>
								<td><fmt:formatDate value="${item.applyTime}"  pattern="yyyy-MM-dd HH:mm:SS"/>  </td>
								<td>${item.agent.irName }</td>
								<td>${item.step.operateStatus}</td>
								<td><a href="${ROOT}/b2b/project/view-${item.id}-${type}"
										class="operate operate4"><i></i>查看</a>
										 <c:if test="${item.step.isFirst==1 }">
										 	<a href="javascript:void(0);" onclick="deleteProject('${item.id}','${item.pName }')" class="operate operate2"><i></i>删除</a>
											<%-- <a href="javascript:void(0);" onclick="editorProject('${item.id}','${item.pName }')" class="operate operate2"><i></i>修改</a> --%>
										 </c:if>
										<c:choose>
											<c:when test="${item.pIsBid==1&&item.commitmentNote==null&&item.step.statusValue==1 }">
													<a href="${ROOT }/b2b/project/commitmentNote.html?projectId=${ item.id}">授权申请</a>
											</c:when>
											<c:when test="${item.proto==1&&item.step.statusValue==1&&item.sampleDeal==null&&((item.pIsBid==0)||(item.pIsBid==1&&item.commitmentNote!=null))}">
													<a href="${ROOT }/b2b/project/sampleDeal.html?projectId=${ item.id}">样机申请</a>
											</c:when>
											<c:when test="${(item.entrust==1&&item.step.statusValue==1&&item.entrustPic==null&&(item.pIsBid==0||(item.pIsBid==1&&item.commitmentNote!=null))&&(item.proto==0||(item.proto==1&&item.sampleDeal != null))  )}">
													<a href="${ROOT }/b2b/project/entrustPic.html?projectId=${ item.id}">一次性发货委托</a>
											</c:when>
											<c:when test="${(item.pType==null&&item.step.statusValue==1&&((item.pIsBid==1&&item.commitmentNote!=null)||(item.sampleDeal!=null&&item.proto==1))) ||(item.pIsBid==0&&item.proto==0&&item.step.statusValue==1)}">
													<a href="${ROOT }/b2b/project/next.html?projectId=${ item.id}">特价申请</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.statusValue==1) }">
														<a href="${ROOT }/b2b/project/flow-${ item.id}/2">项目申请</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/3">上传资料</a>
											</c:when>
											<%-- <c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/4">申请结案</a>
											</c:when> --%>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==4)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/55">上传授权书</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==13)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/66">返利申请</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==14)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/1">返利结算</a>
											</c:when>
											<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/1">审批</a>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>						 
										</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="page">
				<p class="pull-left">
					当前显示<span>${pagelist.from+1}-${pagelist.from+20}</span>,共<span>${pagelist.totalElements}</span>条记录
				</p>
				<ms:page from="${pagelist.from}" size="${pagelist.size}"
					totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
			</div>
</body>
<script>
	function deleteProject(id, name) {
		if (confirm("确定要删除" + name + "吗？")) {
			location.href = "${ROOT}/b2b/project/delete?id=" + id;
		};
	}
	
	function editorProject(id, name) {
		location.href = "${ROOT}/b2b/project/editor?id=" + id; 
	}
	
/* 	getData = function(){
		var agentId = $("select[name='agentId'] option:selected").val();
		location.href = "${ROOT}/b2b/project/list.html?agentId="+agentId+"&type="+${type};
	} */
	
	//日期控件
	$('.datetimepicker').datetimepicker({
	    format: 'yyyy-mm-dd',
	    autoclose: true,
	    minView:2
	});
</script>
</html>