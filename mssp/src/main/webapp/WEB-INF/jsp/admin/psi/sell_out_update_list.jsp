<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-sell out修改</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12">
                 <div class="operate-search">
                    <form class="form-inline" action="${ROOT}/admin/psi/sellOutApply/search">
                      <div class="form-group">
            				<div class="controls">
                        		<label>代理商：</label>
                        		<select class="select" name="agentId">
					              	<option value="">--选择代理商--</option>
					              	<c:forEach items="${agentList}" var="item">
            							<option <c:if test="${item.id==agentId}">selected</c:if> value="${item.id }">${item.irName }</option>
            						</c:forEach>
                        		</select>
            				</div>
                      </div>
                      <div class="form-group">
            				<div class="controls">
                        		<label>审批状态：</label>
                        		<select class="select" name="approveStatus">
                        			<option value="">--审批状态--</option>
                        			<c:forEach items="${prStepList}" var="item">
                        				<c:choose>
											<c:when test="${item.isFirst==1}">
											</c:when>
											<c:otherwise>
												<option <c:if test="${item.id==approveStatus}">selected</c:if> value="${item.id }">${item.operateStatus }</option>
											</c:otherwise>
										</c:choose>	
            						</c:forEach>
            						<option value="0" <c:if test="${approveStatus==0}">selected</c:if>>已修改</option>
                        		</select>
            				</div>
                      </div>
                      <div class="form-group">
                        <label>产品型号：</label>
                        <input type="text" class="form-control text" name="productName" placeholder="产品型号" value="${productName }">
                      </div>
                      <input type="submit" class="button button-blue" value="查询" />
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>型号</th>
                                <th>期初库存</th>
                                <th>sell in</th>
                                <th>sell out</th>
                                <th>库存</th>
                                <th>实际sell out</th>
                                <th>sell out(GAP)</th>
                                <th>sell out修改原因</th>
                                <th>申请人</th>
                                <th>申请时间</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${paging.content}" var="item">
                            <tr>
				     			<td>${item.product.name}</td>
				     			<td>${item.llInventory}</td>
				     			<td>${item.sellInlVolume}</td>
				     			<td>${item.sellOutlVolume}</td>
				     			<td>${item.lInventory}</td>
				     			<td><input type="text" name="inputRealSellOut" <c:if test="${item.status!=0}">readonly="readonly"</c:if> value="${item.realSellOut }" onchange="showGAP(this,${item.id},'${item.product.name}')"  placeholder="0"/></td>
				     			<td>${item.sellOutGap}</td>
				     			<td>
									<select>
 						              	<c:forEach items="${psiReasonsList}" var="itemRea">
						            		<option value="${itemRea.id }" <c:if test="${itemRea.id == item.psiReason.id}">selected</c:if> >${itemRea.reason}</option>
						            	</c:forEach>
						            </select>
				     			</td>
				     			<td>${item.commAgent.irName}</td>
				     			<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${item.applyTime}"></fmt:formatDate></td>
				     			<td>${item.step.pnextId==null?'已修改':item.step.pnextId.operateStatus }</td>
				     			<td>
				     				<a href="${ROOT}/admin/psi/sellOutUpdate/detail.html?applyLogId=${item.id}" class="operate operate1"><i></i>查看</a>
				     				<c:if test="${item.step.pnextId.pnextId==null && item.step.pnextId!=null}">
										<a href="${ROOT }/admin/psi/sellOut/modify?appLogId=${item.id}&realSellOut=${item.realSellOut}&sellOutGAP=${item.realSellOut-item.sellOutlVolume}">确认修改</a>
									</c:if>
				     			</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${paging.from+1}-${paging.from+10}</span>,共<span>${paging.totalElements}</span>条记录</p>
						<ms:page from="${paging.from}" size="${paging.size}" totle="${paging.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
            </div>
        </div>
    </div>
</div>
</div>
<script>
$(document).ready(function(){
	$("input[id^=hidden]").each(function(index, element) {
		var mid=element.id.split("-")[1];
		var value=$("input[id$="+mid+"]").val();
		$("select[id$="+mid+"]").val(value);
    });
	
	//计算sell out(GAP)
	showGAP = function(dom,appLogId,name){
		var realSellOut = $(dom).val();
		var productName = name;
		var sumSellOut = $(dom).parent().prev().prev().html();
		if(isNaN(realSellOut)){
			alert("Sell Out数量只能为数字");
			$(dom).focus();
			return;
		}
		if(confirm("确定要修改"+productName+"的实际sell out值吗？")){
			$(dom).parent().next().html(realSellOut-sumSellOut);
			$("input[name='inputSellOutGap']").val(realSellOut-sumSellOut);
			location.href = "${ROOT}/admin/psi/sellOut/modify?appLogId="+appLogId+"&realSellOut="+realSellOut+"&sellOutGAP="+(realSellOut-sumSellOut);
		};
	}
})
function changeSelect(obj){
	var mid=obj.id.split("-")[1];
	$("input[id$="+mid+"]").val(obj.value);
}
</script>
</body>
</html>