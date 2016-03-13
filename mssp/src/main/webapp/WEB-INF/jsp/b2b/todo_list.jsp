<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>项目申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
   <div class="content">
			<table class="maintable table table-hover">
				<thead>
					<tr>
						<th>项目编号</th>
						<th>项目名称</th>
						<th>项目阶段</th>
						<th>状态</th>
					</tr>
				</thead>
				<tbody>
				<c:if test="${empty plist }">
					<tr>
						<td colspan="4">暂无待办事宜！</td>
					</tr>
				</c:if>
				<c:forEach items="${plist }" var="item">
					<tr>
						<td>${item.pCode }</td>
						<td>
								<%-- <c:choose>
											<c:when test="${item.commitmentNote==null&&item.step.statusValue==1 }">
													<a href="${ROOT }/b2b/project/commitmentNote.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${item.sampleDeal==null && item.commitmentNote!=null&&item.step.statusValue==1}">
													<a href="${ROOT }/b2b/project/sampleDeal.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${item.pType==null && item.commitmentNote!=null && item.sampleDeal!=null&&item.step.statusValue==1}">
													<a href="${ROOT }/b2b/project/next.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.statusValue==1) }">
														<a href="${ROOT }/b2b/project/flow-${ item.id}/2">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/3">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/4">${item.pName }</a>
											</c:when>
											<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
												<a href="${ROOT }/b2b/project/flow-${ item.id}/1">${item.pName }</a>
											</c:when>
											<c:otherwise>
											</c:otherwise> 
										</c:choose>	--%>
										
										<c:choose>
											<c:when test="${item.pIsBid==1&&item.commitmentNote==null&&item.step.statusValue==1 }">
													<a href="${ROOT }/b2b/project/commitmentNote.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${item.proto==1&&item.step.statusValue==1&&item.sampleDeal==null&&((item.pIsBid==0)||(item.pIsBid==1&&item.commitmentNote!=null))}">
													<a href="${ROOT }/b2b/project/sampleDeal.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${(item.entrust==1&&item.step.statusValue==1&&item.entrustPic==null&&(item.pIsBid==0||(item.pIsBid==1&&item.commitmentNote!=null))&&(item.proto==0||(item.proto==1&&item.sampleDeal != null))  )}">
													<a href="${ROOT }/b2b/project/entrustPic.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${(item.pType==null&&item.step.statusValue==1&&((item.pIsBid==1&&item.commitmentNote!=null)||(item.sampleDeal!=null&&item.proto==1))) ||(item.pIsBid==0&&item.proto==0&&item.step.statusValue==1)}">
													<a href="${ROOT }/b2b/project/next.html?projectId=${ item.id}">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.statusValue==1) }">
														<a href="${ROOT }/b2b/project/flow-${ item.id}/2">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/3">${item.pName }</a>
											</c:when>
											<%-- <c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/4">申请结案</a>
											</c:when> --%>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==4)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/55">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==13)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/66">${item.pName }</a>
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==14)  }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/1">${item.pName }</a>
											</c:when>
											<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/1">${item.pName }</a>
											</c:when>
											<c:otherwise>
											</c:otherwise>
										</c:choose>						 
						</td>
						<td>${item.step.operateStatus  }</td>
						<td>	
							<%-- <c:choose>
								<c:when test="${item.commitmentNote==null&&item.step.statusValue==1 }">
										授权申请
								</c:when>
								<c:when test="${item.sampleDeal==null && item.commitmentNote!=null&&item.step.statusValue==1}">
										样机申请
								</c:when>
								<c:when test="${item.pType==null && item.commitmentNote!=null && item.sampleDeal!=null&&item.step.statusValue==1}">
										特价申请
								</c:when>
								<c:when test="${item.step.pnextId.statusValue>=0&&item.step.pnextId.statusValue<10 }">
									项目申请
								</c:when>
								<c:when test="${item.step.pnextId.statusValue>=10&&item.step.pnextId.statusValue<100 }">
									项目核销
								</c:when>
								<c:when test="${item.step.pnextId.statusValue>=100&&item.step.pnextId.statusValue<1000 }">
									项目结案
								</c:when>
							</c:choose> --%>
							
							<c:choose>
											<c:when test="${item.pIsBid==1&&item.commitmentNote==null&&item.step.statusValue==1 }">
													授权申请
											</c:when>
											<c:when test="${item.proto==1&&item.step.statusValue==1&&item.sampleDeal==null&&((item.pIsBid==0)||(item.pIsBid==1&&item.commitmentNote!=null))}">
														样机申请	
											</c:when>
											<c:when test="${(item.entrust==1&&item.step.statusValue==1&&item.entrustPic==null&&(item.pIsBid==0||(item.pIsBid==1&&item.commitmentNote!=null))&&(item.proto==0||(item.proto==1&&item.sampleDeal != null))  )}">
													一次性发货委托
											</c:when>
											<c:when test="${(item.pType==null&&item.step.statusValue==1&&((item.pIsBid==1&&item.commitmentNote!=null)||(item.sampleDeal!=null&&item.proto==1))) ||(item.pIsBid==0&&item.proto==0&&item.step.statusValue==1)}">
													特价申请
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.statusValue==1) }">
														项目申请
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==10) }">
													上传资料
											</c:when>
											<%-- <c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==100) }">
													<a href="${ROOT }/b2b/project/flow-${ item.id}/4">申请结案</a>
											</c:when> --%>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==4)  }">
													上传授权书
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==13)  }">
													返利申请
											</c:when>
											<c:when test="${(sessionScope.user.commRole.id==item.step.pnextId.commRole.id)&&(item.step.pnextId.statusValue==14)  }">
													返利结算
											</c:when>
											<c:when test="${sessionScope.user.commRole.id==item.step.pnextId.commRole.id }">
													审批
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
  </div>
</body>
</html>