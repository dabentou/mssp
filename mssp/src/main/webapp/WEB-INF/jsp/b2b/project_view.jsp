<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>查看项目</title>
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
		.table td{
		border:1px solid #ddd
		}
		.table th{
		border:1px solid #ddd
		}
		.table-small tbody tr td{ padding:3px;}
		.table-small tbody tr td input[type=text]{ width:35px;}
    	.input-mini{
			width: 50px;
		}
	</style>
	<script type="text/javascript">
	$(function(){
			$("#pass").click(function(){
				if(${sessionScope.user.commRole.id==NATIONAL_MANAGER }){
					var filesImg = $("input[name=filesImg]").val();
					if(filesImg==''){
						alert("请上传授权文件！");
						return;
					}
				}
				$("#isPass").val("0");
				$("#myFrom").submit();
			});
			
			$("#reject").click(function(){
				if(valiForm()){
					if(${sessionScope.user.commRole.id==NATIONAL_MANAGER }){
						var filesImg = $("input[name=filesImg]").val();
						if(filesImg==''){
							alert("请上传授权文件！");
							return;
						}
					}
					$("#isPass").val("1");
					$("#myFrom").submit();
				}
			});
			$("#saveImg").click(function(){
					var file = $("input[name=files]");
					for(var i=0;i<file.length;i++){
						if(file[i].value.length == 0)
						alert("请上传第"+(i+1)+"张图片！");
					}
					$("#uploadForm").submit();
			});
			$("#grantImg").click(function(){
				var filesss = document.getElementById("grantFile").value;
				if(filesss.length == 0){
					alert("请上传授权书！");
					return false;
				}
				$("#uploadGrantImg").submit();
			});
			valiForm = function(){
				if($("#approveMsg").val()==null||$("#approveMsg").val()==""){
					alert("请输入不通过的原因！");
					$("#approveMsg").focus();
					return false;
				}
				return true;
			}
	});
	</script>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
		<div class="content"   align="center">
		 	<form id="myFrom" class="form-horizontal"  action="${ROOT}/b2b/project/flow" enctype="multipart/form-data" method="post">
      	<input type="hidden" name="isPass"   id="isPass" />
      	<input type="hidden" name="flowType"   id="flowType"  value="${flowType }"/>
      	<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
      	<table class="maintable table table-hover"  style="width: 65%;" border="1">
      	<tr><td colspan="6"><span class="project_title">项目报备信息</span></td></tr>
      		<tr>
      			<td colspan="2" rowspan="2">项目信息</td>
      			<td> 项目名称：</td>
      			<td>${project.pName }</td>
      			<td>项目编号：</td>
      			<td>${project.pCode }</td>
      		</tr>
      		<tr>
      			<td> 是否招投标：</td>
      			<td>
	      			${project.pIsBid==0?"否":"是" }
      			</td>
      			<td> 投标时间：</td>
      			<td><fmt:formatDate value="${project.pBidTime}" pattern="yyyy-MM-dd" /></td> 
      		</tr>
      		<tr>
      			<td colspan="2" rowspan="2">客户信息</td>
      			<td>
      				 客户名称
      			</td>
      			<td>${project.customer.name }</td>
      			<td> 联系人/电话：</td>
      			<td> ${project.customer.person } / ${project.customer.phone } </td>
      		</tr>
      		<tr>
      			<td> 涉及行业：</td>
      			<td>
	      			${project.business.name }
				</td>
      			<td> 客户地址：</td>
      			<td> ${project.customer.address }</td>
      		</tr>
      		
      		<tr>
      			<td colspan="2" rowspan="2">销售信息</td>
      			<td>
      				 型号
      			</td>
      			<td>
					<c:forEach items="${saList}" var="item" >
						${item.productPrice.product.name }<br />
						
					</c:forEach>
				</td>
      			<td>销售数量：</td>
      			<td id="sn">
      				<c:forEach items="${saList}" var="item" >
						${item.number}<br />
						
					</c:forEach>
      			 </td>
      		</tr>
      		
      		
      		<tr>
      			<td> 竞品型号/价格/尺寸：</td>
      			<td id="cppz">
      				<c:forEach items="${saList}" var="item" >
						${item.productPrice.product.name} / ${item.competeProduct} / ${item.competePrice}<br/>
						
					</c:forEach>
      			</td>
      			<td> 样机提供：</td>
      			<td>
					${project.proto==1?"是":"否" }
				</td>
      		</tr>
      		
      		<tr>
      			<td colspan="2" rowspan="2">代理信息</td>
      			<td>
      				 总代名称
      			</td>
      			<td>
      				${project.agent.irName}
      			</td>
      			<td> 联系人/电话：</td>
      			<td>${project.agent.irName}/${project.agent.phone}</td>
      		</tr>
      		<tr>
      			<td> 二级代理商：</td>
      			<td>
					${project.commSi.irName}
				</td>
      			<td> 联系人/电话：</td>
      			<td>${project.commSi.irName}/${project.commSi.phone}</td>
      		</tr>
      		
      		<tr>
      			<td colspan="2">备注：</td>
      			<td colspan="4">${project.afterSalesService}</td>
      		</tr>
      		
      		<c:if test="${project.step.statusValue>=9 }">
      		<tr>
      			<td colspan="2">核销材料：</td>
      			<td colspan="4">
					<%-- <c:forEach items="${ project.uploadImgs}"  var="item">
						<img src="${item }"  width="330"/>
					</c:forEach> --%>
					<c:forEach items="${fn:split(project.uploadImgs,';')}" var="item" varStatus="itemVs">
									<img src ="${item}" width="330"></img>
					</c:forEach>
				</td>
      		</tr>
      		</c:if>
      		
      		
      		<c:if test="${project.step.statusValue>=2 }">
      			<tr height="50"  ></tr>
      			<tr><td colspan="6"><span class="project_title">特殊支持申请</span></td></tr>
      			<tr>
      				<td>申请日期：</td>
      				<td colspan="2"> <fmt:formatDate value="${project.applyTime }"  pattern="yyyy-MM-dd HH:mm:SS"/></td>
      				<td> 项目编号 ：</td>
      				<td colspan="2" style="font-weight: bold;">${project.pCode }</td>
      			</tr>
      			<tr>
      				<td>类型：</td>
      				<td>${project.pType==1?"一次性申请单":"长期申请单" }</td>
      				<td> 投标时间：</td>
      				<td> <fmt:formatDate value="${project.pBidTime }"  pattern="yyyy-MM-dd"/> </td>
      				<td>价格适用时间：</td> 
      				<td><fmt:formatDate value="${project.priceValidBeginTime }"  pattern="yyyy-MM-dd"/>
      					/ <fmt:formatDate value="${project.priceValidEndTime }"  pattern="yyyy-MM-dd"/></td> 
      			</tr>
      			<tr>
      				<td>客户名称：</td>
      				<td><%-- ${project.customerName } --%></td>
      				<td> 联系人：</td>
      				<td><%--  ${project.customerLinkman }  --%></td>
      				<td>电话：</td> 
      				<td><%-- ${project.customerPhone } --%></td> 
      			</tr>
      			<tr>
      				<td>二级代理商：</td>
      				<td>${project.commSi.irName }</td>
      				<td> 联系人：</td>
      				<td> ${project.commSi.address } </td>
      				<td>电话：</td> 
      				<td></td> 
      			</tr>
      			<tr>
      				<td colspan="2">项目决策人 决策链 情况说明：</td>
      				<td colspan="4">${project.decisionInfo }</td>
      			</tr>
      			<tr>
      				<td colspan="2">项目名称：</td>
      				<td colspan="4">${project.pName } </td>
      			</tr>
      			
      			<tr>
      				<td colspan="6">
				 		<textarea id="applyReason" name="applyReason" style="width:759px;height:300px;">
				 			${project.applyReason }
						</textarea>
      				</td>
      			</tr>
      			<tr>
      				<td colspan="6">
      					<table  class="maintable table table-hover"  style="width: 100%;" border="1">
      						<tr>
      							<td>No</td>
      							<td>型号</td>
      							<td>公开价</td>
      							<td>NET价</td>
      							<td>申请单价(元)</td>
      							<td>数量(台)</td>
      							<td>折扣率(%)</td>
      							<td>赢单率(%)</td>
      							<td>备注</td>
      							<td>竞品型号</td>
      							<td>竞品价格</td>
      							<c:if test="${(sessionScope.user.commRole.id==DAQUJINGLI || sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate || type==3}"><!-- 大区经理 -->
									<td>大区经理批复价(元)</td>
								</c:if>
								<c:if test="${(sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate|| type==3}"><!--行业经理 -->
										<td>行业经理批复价(元)</td>
								</c:if>
								<c:if test="${(sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate || type==3}"><!--销售总经理 -->
										<td>销售总经理批复价(元)</td>
								</c:if>
      				 			<c:if test="${rebate || type==3 }">
      								<td>返利</td>
      							</c:if>
      						</tr>
      						<c:forEach items="${saList }" var="item">
      						<tr>
      							<td>${item.id}<input type="hidden" name="saId" value="${item.id}" /></td>
      							<td>${item.productPrice.product.name}</td>
      							<td id="pPrice${item.id }">${item.productPrice.b2bPublicPrice }</td>
      							<td>${item.productPrice.netPrice }</td>
      							<c:choose>
      								<c:when test="${isShowApprove }">
      									<td><input type="text" value="${item.applyPrice }"  name="applyPrice" style="width:70px;" onChange="priceChange(this);"/><input type="hidden" value="${item.applyPrice }"  name="applyPrice0"/></td>
      									<td><input type="text" value="${item.number }"  name="number" style="width:50px;" /><input type="hidden" value="${item.number }"  name="number0" /><input type="hidden" name="publicPrices" value="${item.productPrice.b2bPublicPrice }"/></td>
      									<td><input type="text" value="${item.discountRate }" id="discountRate" name="discountRate" style="width:50px;" readonly/><input type="hidden" value="${item.discountRate }"  name="discountRate0" /></td>
      								</c:when>
      								<c:otherwise>
      									<td>${item.applyPrice }</td>
      									<td>${item.number }</td>
      									<td> ${item.discountRate }</td>
      								</c:otherwise>
      							</c:choose>
      							
      							
      							<td>${item.winRate }</td>
      							<td>${item.remark }</td>
      							<td>${item.competeProduct }</td>
      							<td>${item.competePrice }</td>
      							<c:if test="${(sessionScope.user.commRole.id==DAQUJINGLI || sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate || type==3}"><!-- 大区经理 -->
										<td>
											<input class="input-mini" <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=DAQUJINGLI  }">readonly='readonly'</c:if> type="text" name="areaMangePrice" value="${item.areaMangePrice==null?item.applyPrice:item.areaMangePrice }" />
											<c:if test="${flowType==1 && sessionScope.user.commRole.id==DAQUJINGLI && item.areaMangePrice==null}">
												<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
											</c:if>
										</td>
								</c:if>
								<c:if test="${(sessionScope.user.commRole.id==NATIONAL_MANAGER || sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate || type==3}"><!-- 行业经理 -->
									<td>
										<input  class="input-mini"  <c:if test="${flowType!=55 || sessionScope.user.commRole.id!=NATIONAL_MANAGER }">readonly='readonly'</c:if> type="text" name="sellMangePrice" value="${item.industryMangePrice==null?item.areaMangePrice:item.industryMangePrice }" />
										<c:if test="${flowType==55 && sessionScope.user.commRole.id==NATIONAL_MANAGER && item.industryMangePrice==null}">
											<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
										</c:if>
									</td>
								</c:if>
								<c:if test="${(sessionScope.user.commRole.id==SALES_DIRECTOR) || rebate || type==3}"><!-- 销售总经理 -->
									<td>
										<input  class="input-mini"  <c:if test="${flowType!=1 || sessionScope.user.commRole.id!=SALES_DIRECTOR }">readonly='readonly'</c:if> type="text" name="sellMangePrice" value="${item.sellMangePrice==null?item.industryMangePrice:item.sellMangePrice }" />
										<c:if test="${flowType==1 && sessionScope.user.commRole.id==SALES_DIRECTOR && item.sellMangePrice==null}">
											<a href="javascript:void(0);" onclick="saveReplyPrice(this,'${item.id }')"  class="operate operate2">保存</a>
										</c:if>
									</td>
								</c:if>
      							<%-- <c:if test="${isShowApprove }"><td width=45><a href="#" class="modify" data-target="#myModal">修改</a></td></c:if> --%>
      				 			<c:if test="${rebate || type==3}">
      								<td>${item.rebate}</td>
      							</c:if>
      						</tr>
      					</c:forEach>
      					<c:if test="${type==3 }">
      						<tr>
								<td colspan="14">合计</td>
								<td>${sumRabate }</td>
							</tr>
      					</c:if>
      				</table>
      			</td>
      		</tr>
      		<tr>
      			<td colspan="1">特价申请图片：</td>
      			<td colspan="5">
      				<img src="${project.uploadApplyData}"   width="330"/>
      			</td>
      		</tr>
      		
      		</c:if>
      		
      		
      		<tr name = "tr3"  style="display:none"><td colspan="6">型号数量月份统计</td></tr>
      		<tr name = "tr3"  style="display:none">
      			<td colspan="6">
      			<table  class="maintable table table-hover table-small"  style="width: 100%;" border="1">
      				<tr><td>型号</td><td>数量</td><td>1月</td><td>2月</td><td>3月</td><td>4月</td><td>5月</td><td>6月</td><td>7月</td><td>8月</td><td>9月</td><td>10月</td><td>11月</td><td>12月</td>   </tr>
      				 
      				 <c:forEach items="${casual}" var="item">
      				 <tr>
      					<td>${item.name }</td><td>${item.total }</td><td>${item.one}<td>${item.two}</td><td>${item.three}</td><td>${item.four}</td><td>${item.five}</td>
      					<td>${item.six}</td><td>${item.seven}</td><td>${item.eight}</td><td>${item.nine}</td><td>${item.ten}</td><td>${item.eleven}</td><td>${item.twelve}</td> 
      				</tr>
      				</c:forEach> 
      				
      			</table>
      			</td>
      		</tr>
      		
      		<tr>
      		<c:if test="${project.entrustPic != null }">
      			<td width="110px">一次性发货委托：</td> <td colspan="5" >
      				<%-- <img src ="${project.entrustPic}" width="330"></img> --%>
      				<div class="download">
      									<c:forEach var="url" items="${project.entrustPic}">
      										<a href="${url }" class="thumbnail"></a>
										</c:forEach>
									</div>
      			</td>
      		</c:if>
      		</tr>
      		
      		<c:if test="${project.proto == 1 }">
      		<c:if test="${project.sampleDeal != null }">
	      		<tr  name = "tr2" height="50"  ></tr>
	      		<tr  name = "tr2"><td colspan="6"><span class="project_title">样机申请</span></td></tr>
	      		<tr  name = "tr2" >
	      		<td colspan="6">
	      			<table class="table table-hover maintable" id="myTable">
					<thead>
						<tr>
							<td>样机型号 </td>
							<td>样机总量</td>
							<td>当前可使用数量</td>
							<td>申请数量</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${lists}" var="item">
							<tr>
								<td>${item.sampleInventory.product.name}<input type="hidden" name=sampleId value="${item.id }"></td>
								<td>${item.sampleInventory.total}</td>
								<td>${item.sampleInventory.balance}</td>
								<td>${item.changeVolue}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
	      		</td>
	      	</tr>
      		<tr name = "tr2"><td>样机借用协议：</td> <td colspan="5"> 
      			<%-- <img src ="${project.sampleDeal}"  width="330"></img> --%>
      			<div class="download">
      									<c:forEach var="url" items="${project.sampleDeal}">
      										<a href="${url }" class="thumbnail"></a>
										</c:forEach>
									</div>
      		
      		</td> </tr>
      	</c:if>
      	</c:if>
      	
      	<c:if test="${project.pIsBid == 1 }">
      	<c:if test="${project.commitmentNote != null }">
	      	<tr name = "tr1" height="50"  ></tr>
	      	<tr name = "tr1" ><td colspan="6"><span class="project_title">授权申请</span></td></tr>
	      	<tr name = "tr1"><td width="110px">代理商承诺函：</td> 
	      		<td colspan="5" > 
	      			<%-- <img src ="${project.commitmentNote}" width="330"></img> --%>
	      			<div class="download">
      									<c:forEach var="url" items="${project.commitmentNote}">
      										<a href="${url }" class="thumbnail"></a>
										</c:forEach>
									</div>
	      		</td> 
	      	</tr>
	      	
	      	
	      	</c:if>
	      	<c:if test="${project.granImg != null }">
	      			<tr name = "tr1"><td width="110px">代理商授权书：</td> 
	      				     <td colspan="5" ><img src ="${project.granImg}" width="330"></img>
	      				     </td> 
	      			</tr>
      		</c:if>
      	</c:if>
      	
      	
      	
      	<c:if test="${project.step.statusValue >= 13 }">
      	<table  class="maintable table table-hover"  style="width: 65%;" border="1">
      		<td colspan="6"><span class="project_title">返利申请</span></td>
      		<tr>
      			<td colspan="2" rowspan="12">收发货信息</td>
      			<td>公司名称</td>
      			<td>${project.sendName}</td>
      			<td>公司名称</td>
      			<td>${project.receiveCompany}</td>
      		</tr>
      		<tr>
      			<td>发件人：</td>
      			<td>${project.sendName}</td>
      			<td>收件人：</td>
      			<td>${project.receiveName}</td>
      		</tr>
      		<tr>
      			<td>地址：</td>
      			<td>${project.sendAddress}</td>
      			<td>地址：</td>
      			<td>${project.receiveName}</td>
      		</tr>
      		<tr>
      			<td>地址1：</td>
      			<td>${project.sendAddress1}</td>
      			<td>地址1：</td>
      			<td>${project.receiveAddress1}</td>
      		</tr>
      		<tr>
      			<td>地址2：</td>
      			<td>${project.sendAddress2}</td>
      			<td>地址2：</td>
      			<td>${project.receiveAddress2}</td>
      		</tr>
      		<tr>
      			<td>邮政编码：</td>
      			<td>${project.sendZipcode}</td>
      			<td>邮政编码：</td>
      			<td>${project.receiveZipcode}</td>
      		</tr>
      		<tr>
      			<td>市：</td>
      			<td>${project.sendCity}</td>
      			<td>市：</td>
      			<td>${project.receiveCity}</td>
      		</tr>
      		<tr>
      			<td>省/市/自治区：</td>
      			<td>${project.sendProvince}</td>
      			<td>省/市/自治区：</td>
      			<td>${project.receiveProvince}</td>
      		</tr>
      		<tr>
      			<td>国家或地区：</td>
      			<td>${project.sendCountry}</td>
      			<td>国家或地区：</td>
      			<td>${project.receiveCountry}</td>
      		</tr>
      		<tr>
      			<td>电话号码：</td>
      			<td>${project.sendPhone}</td>
      			<td>电话号码：</td>
      			<td>${project.receivePhone}</td>
      		</tr>
      		<tr>
      			<td>传真号码：</td>
      			<td>${project.sendFax}</td>
      			<td>传真号码：</td>
      			<td>${project.receiveFax}</td>
      		</tr>
      		<tr>
      			<td>电子邮件地址：</td>
      			<td>${project.sendEmail}</td>
      			<td>电子邮件地址：</td>
      			<td>${project.receiveEmail}</td>
      		</tr>
      		
      			<tr>
      			<td colspan="2" rowspan="2">销售信息</td>
      			<td>代理发货时间</td>
      			<td> ${project.agentSendTime}</td>
      			<td>客户收货时间：</td>
      			<td> ${project.customerReceiveTime}</td>
      		</tr>
      		<tr>
      			<td>产品安装情况：</td>
      			<td>${project.productUseInfo}</td>
      			<td>售后服务形式：</td>
      			<td>${project.afterSalesService}</td>
      		</tr>
      		</table>
      	</c:if>
      	
      	<%-- <tr name = "tr1" height="50"  ></tr>
      	<tr name = "tr1" ><td colspan="6"><span class="project_title">项目结案申报</span></td></tr>
      	<tr name = "tr1">
      		<td width="110px">项目信息：</td>
      		<td colspan="5" >${applyCloseProject.proInfo }</td>
      	</tr> --%>
      	</table>
      	
      	<table  class="maintable table table-hover"  style="width: 65%;" border="1">
      		<tr>
      			<td colspan="6"><span class="project_title">修改记录</span></td>
      		</tr>
      		
	      	<tr>
	      		<td>审核人：${item.user.realName }</td>
	      		<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
	      		<td>步骤：${item.step.operateStatus }</td>
	      		<td>是否通过：${item.isPass==0?'通过':'不通过' }</td>
	      		<td>审批备注：${item.approveMsg }</td>
	      	</tr>
	      	
	      	<c:forEach items="${productLogList }" var="item">
      		  <tr>
      		  	<td>修改人：${item.user.realName }</td>
      		  	<td>修改时间：<fmt:formatDate value="${item.updatetime }"  pattern="yyyy-MM-dd HH:mm:SS"/></td>
      		  	<td>型号：${item.specialApplyProduct.product.name }</td>
      		  	<td>单价：${item.oldPrice }——>${item.targetPrice }</td>
      		  	<td>数量：${item.oldNumber }——>${item.targetNumber }</td>
      		  </tr>
      		</c:forEach>
      		
      	</table>
      	
      	
      	
      	
      	<table  class="maintable table table-hover"  style="width: 65%;" border="1">
      		<tr>
      			<td colspan="6"><span class="project_title">审批记录/操作</span></td>
      		</tr>
      		<c:forEach items="${logList }" var="item">
	      		<tr>
	      			<td>审核人：${item.user.realName }</td>
	      			<td>审核时间：<fmt:formatDate value="${item.approveTime }"  pattern="yyyy-MM-dd HH:mm:SS"/> </td>
	      			<td>步骤：${item.step.operateStatus }</td>
	      			<td>是否通过：${item.isPass==0?'通过':'不通过' }</td>
	      			<td>审批备注：${item.approveMsg }</td>
	      		</tr>
      		</c:forEach>
      		<c:if test="${sessionScope.user.commRole.id==NATIONAL_MANAGER && enable && project.granImg==''}"><!--全国经理  -->
      		    <tr>
		      		<td colspan="2">授权书描件上传：</td>
		      		<td colspan="4"><input type="file"  name="filesImg"/></td>
	      		</tr>
      		</c:if>
      		<c:if test="${isShowApprove }">
	      		<tr>
	      			<td colspan="3">
	 					<input id="pass" type="button" class="button button-lightblue" value="通过" />&nbsp;&nbsp;&nbsp;  
	      				<input id="reject" type="button" class="button button-lightblue" value="不通过" />
					</td>	
	      			<td colspan="2">
	      				<textarea rows="4" cols="50"  name="approveMsg" id="approveMsg"></textarea>
	      			</td>
	      		</tr>
      		</c:if>
      	</table>
      		
        </form>
        
        <c:if test="${giveRight }">
        	<form id="uploadGrantImg" action="${ROOT}/b2b/project/uploadGrantImg" enctype="multipart/form-data" method="post" >
        		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        		<table  class="maintable table table-hover"  style="width: 65%;" border="1">
        		<tr>
	      			<td colspan="6"><span class="project_title">上传授权书</span></td>
	      		</tr>
        		<tr>
	      			<td colspan="2">授权书：</td>
	      			<td colspan="4"><input type="file" id="grantFile" name="file" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="6"><input id="grantImg" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      		</table>
        	</form>
        </c:if>
        
         <c:if test="${rebate }">
        	<form action="${ROOT}/b2b/project/rebate" enctype="multipart/form-data" method="post" >
        		<input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        		<table  class="maintable table table-hover"  style="width: 65%;" border="1">
        		<td colspan="6"><span class="project_title">返利申请</span></td>
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
        		
        		
	      		<tr>
	      			<td colspan="6"><input id="grantImg" type="submit" class="button button-lightblue" value="返利申请" /></td>
	      		</tr>
	      		</table>
        	</form>
        </c:if>
        
        
        
        <form id="uploadForm" action="${ROOT}/b2b/project/upload" enctype="multipart/form-data" method="post" >
        <input type="hidden" name="projectId"   id="projectId"  value="${project.id }" />
        	<c:if test="${isUploadVerificImg }">
		      	<table  class="maintable table table-hover"  style="width: 65%;" border="1">
	      		<tr>
	      			<td colspan="6"><span class="project_title">上传核销图片</span></td>
	      		</tr>
	      		<tr>
	      			<td colspan="2">选择图片：</td>
	      			<td colspan="4"><input type="file" id="file1" name="files" /></td>
	      		</tr> 
	      		
	      		<tr>
	      			<td colspan="2">选择图片：</td>
	      			<td colspan="4"><input type="file" id="file2" name="files" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="2">选择图片：</td>
	      			<td colspan="4"><input type="file" id="file3" name="files" /></td>
	      		</tr> 
	      		<tr>
	      			<td colspan="6"><input id="saveImg" type="button" class="button button-lightblue" value="保存" /></td>
	      		</tr>
	      	</table>
      	</c:if>
        </form>
		</div>
</html>		
<script charset="utf-8" src="${ROOT}/js/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${ROOT}/js/kindeditor/lang/zh_CN.js"></script>
<script>
	/* $(document).ready(function(){
		var authorization = ${project.pIsBid };
		if(authorization == 0){
			var tr = $("tr[name=tr1]");
			for (var i=0; i<tr.length; i++) {
				tr[i].style.display="none";
			}
		}
	}) */
	
	var pType = ${project.pType };
	/* if(pType == 2){
		var trs = $("tr[name=tr3]");
		for (var i=0; i<trs.length; i++) {
			 trs[i].style.display="";
		} 
	} */
	
	function priceChange(obj){
		var applyPrice = $(obj).val();
		if(isNaN(applyPrice)){
			alert("申请单价只能为数字，请重填！");
			$(obj).val("");
			return;
		} 
		var pPrice =  $(obj).parent("td").siblings("td").find("input[name=publicPrices]").val();
		var discountRate =applyPrice/pPrice*100;
		$(obj).parent("td").siblings("td").find("input[name=discountRate]").val(discountRate);
	}
	
	$(".modify").click(function(){
		var id = $(this).parents("td").siblings("td").find("input[name=saId]").val();//获得id
		var discountRate = $(this).parents("td").siblings("td").find("input[name=discountRate]").val();//新的折扣率
		var discountRate0 = $(this).parents("td").siblings("td").find("input[name=discountRate0]").val();//旧的折扣率
		var applyPrice = $(this).parents("td").siblings("td").find("input[name=applyPrice]").val();//新申请单价
		var applyPrice0 = $(this).parents("td").siblings("td").find("input[name=applyPrice0]").val();//旧的申请单价
		var number = $(this).parents("td").siblings("td").find("input[name=number]").val();//新的数量
		var number0 = $(this).parents("td").siblings("td").find("input[name=number0]").val();//旧的数量
		if(applyPrice == applyPrice0 && number == number0){
			alert("申请单价和数量至少修改一个才能提交！");
		}else{
			$.ajax({
				type : "POST",
				 url : "${ROOT}/b2b/applyProduct/modify",
				data : "id=" + id +"&discountRate=" + discountRate + "&discountRate0=" + discountRate0 +"&applyPrice=" + applyPrice
								+"&applyPrice0=" + applyPrice0 + "&number=" + number + "&number0=" + number0,
			dataType : "json",
			 success : function(json){
				 	if(json.status == "OK"){
				 		alert("修改成功");
				 		window.location.reload();
				 	}else{
				 		alert("修改失败");
				 	}
				 }
			});
		}
	})
	
	//保存批复价格
	saveReplyPrice = function(dom,applyProductId){
		var replyPrice = $(dom).prev().val();
		$.ajax({
			type : "POST",
			url : "${ROOT}/b2b/project/saveReplyPrice",
			data : {
				replyPrice : replyPrice,
				applyProductId : applyProductId
			},
			dataType : "json",
			success : function(json) {
  				if(json.status=="OK"){
  					alert("保存成功！");
  					window.location.reload();
				}else{
					alert(json.errors[0].msg);
				} 
			}
		});
	}
</script>
<script>
	KindEditor.ready(function(K) {
    	window.editor = K.create('#applyReason', {
			uploadJson : '${ROOT}/editor/image/upload',
			allowFileManager : true,
			afterBlur: function(){this.sync()}
		});
    	editor.html($("#applyReason").val());
	});
</script>


