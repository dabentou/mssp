<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>sellout修改申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
     <div class="content">
      <form id="sellOutUpdateForm" class="form-horizontal"  action="${ROOT}/psi/selloutupdate/apply/post" method="post" >
          <div class="form-group">
            <label class="control-label">类型：</label>
            <div class="controls">
              <select class="select form-control" id="channelType" name="channelType">
                <option value="1" ${applyLog.channelType=='1'?'selected':'' }>飞生sell in导入</option>
                <option value="2" ${applyLog.channelType=='2'?'selected':'' }>越海sell in导入</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">当前代理商：</label>
     		<div class="controls">
	     		<select class="select form-control" name="agentId">
		            <c:forEach items="${commAgents}" var="item">
						<option value="${item.id}" <c:if test="${item.id==agentId}">selected</c:if>>${item.irName}</option>
		     		</c:forEach>
	     		</select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">型号：</label>
            <div class="controls">
              <select class="select form-control" name="productId" id="product"  onchange="showData()">
              <option value="">型号</option>
                <c:forEach items="${productList}" var="item">
            		<option  value="${item.id }" <c:if test="${item.id == applyLog.product.id}">selected</c:if> >${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">期初库存：</label>
            <div class="controls">
				<input type="text" value="${applyLog.llInventory}" class="text form-control" value="" name="psiInventorValuey" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">累计sell in：</label>
            <div class="controls">
				<input type="text" value="${applyLog.sellInlVolume}" class="text form-control" value="" name="sellInlVolume" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">累计sell out：</label>
            <div class="controls">
				<input type="text" value="${applyLog.sellOutlVolume}" class="text form-control" value="" name="sellOutlVolume" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">库存：</label>
            <div class="controls">
				<input type="text" value="${applyLog.lInventory}" class="text form-control" value="" name="currentInventoryValue" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">实际sell out：</label>
            <div class="controls">
				<input type="text" value="${applyLog.realSellOut}" class="text form-control" value="" name="realSellOut"  onchange="showGAP(this)"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">sell out(GAP)：</label>
            <div class="controls">
				<input type="text" value="${applyLog.sellOutGap}" class="text form-control" value="" name="sellOutGap" readonly="readonly"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">修改原因：</label>
            <div class="controls">
              <select class="select form-control" name="psiReasonId">
                <c:forEach items="${psiReasonsList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == applyLog.psiReason.id}">selected</c:if>>${item.reason }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group form-group-btn">
            <div class="controls">
           		<input type="submit" class="button button-lightblue" onclick="return submitCheck();" value="确定提交"/>
<%--             	<c:choose>
		            <c:when test="${commUser.commRole.id==3}">
		            	<input type="submit" class="button button-lightblue" onclick="return submitCheck();" value="确定提交"/>
		   			</c:when>  
		   			<c:otherwise>
		   				<input type="button" onclick="sellOutUpdateApproval(${applyLog.id},1)" class="button button-lightblue" value="同意" />
	              		<input type="button" onclick="sellOutUpdateApproval(${applyLog.id},-1)" class="button button-lightblue" value="不同意" />
		   			</c:otherwise>
	   			</c:choose> --%>
            </div>
          </div>
        </form>
      </div>
      <a href="javascript:void(0)" id="backtop">返回顶部</a>
    </div>
  </div>
</body>
<script type="text/javascript" >
$(function(){
	//根据类型查型号
	showData = function(){
		$("input[name='realSellOut']").val('');
		$("input[name='sellOutGap']").val('')
		$("input[name='currentInventoryValue']").val('');
		$("input[name='sellInlVolume']").val('');
		$("input[name='sellOutlVolume']").val('');
		$("input[name='psiInventorValuey']").val('');
		var channelType = $("#channelType option:selected").val();
		var productId = $("#product option:selected").val();
		var agentId = $("select[name='agentId'] option:selected").val();
		$fm = $('.content form:first')
		if(productId==''){
			alert("请选择型号！");
			return;
		}
		$.ajax({
			type : "POST",
			url : "${ROOT}/psi/selloutupdate/apply/get",
			data : "productId=" + productId+"&channelType="+channelType+"&agentId="+agentId,
			dataType : "json",
			success : function(json) {
				if(json.status=="OK"){
					$("input[name='currentInventoryValue']").val(json.data.currentInventory.inventoryVolume);
					$("input[name='sellInlVolume']").val(json.data.sumSellInLastMonth);
					$("input[name='sellOutlVolume']").val(json.data.sumSellOutLastMonth);
					$("input[name='psiInventorValuey']").val(json.data.psiInventory.inventoryVolume);
				}else{
					alert(json.errors[0].msg);
				}
			}
		}); 
	}
	
	//计算sell out(GAP)
	showGAP = function(dom){
		var realSellOut = $(dom).val();
		var sumSellOut = $("input[name='sellOutlVolume']").val();
		if(isNaN(realSellOut)){
			alert("Sell Out数量只能为数字");
			$(dom).focus();
			return;
		}
		$("input[name='sellOutGap']").val(realSellOut-sumSellOut);
	}
	
	//sellout修改申请 审批
	sellOutUpdateApproval = function(logId,status){
		var sellOutGAP = $("input[name='sellOutGap']").val();
		$.ajax({
			type : "POST",
			url : "${ROOT}/psi/selloutUpdateApply/approval",
			data : {
				id:logId,
				status:status,
				sellOutGAP:sellOutGAP
			},
			dataType : "json",
			success : function(json) {
				if(json.status=="OK"){
					alert('审批成功!');
				}else{
					alert(json.errors[0].msg);
				}
			}
		});
	}
	
	submitCheck = function(){
		var productId = $("#product option:selected").val();
		var currentInventory = $("input[name='currentInventoryValue']").val();
		var sellInlVolume = $("input[name='sellInlVolume']").val();
		var sellOutlVolume = $("input[name='sellOutlVolume']").val();
		var psiInventor = $("input[name='psiInventorValuey']").val();
		var realSellOut = $("input[name='realSellOut']").val();
		var sellOutGap = $("input[name='sellOutGap']").val();
		if(productId==''){
			alert("请选择型号！");
			return false;
		}
		if(currentInventory==''){
			alert("期初库存不能为空,请重新选择型号自动加载!");
			return false;
		}if(sellInlVolume==''){
			alert("累计sellin不能为空,请重新选择型号自动加载!");
			return false;
		}if(sellOutlVolume==''){
			alert("累计sellout不能为空,请重新选择型号自动加载!");
			return false;
		}if(psiInventor==''){
			alert("库存不能为空,请重新选择型号自动加载!");
			return false;
		}if(realSellOut==''){
			alert("实际sellout不能为空!");
			return false;
		}if(sellOutGap==''){
			alert("selloutGAP不能为空,请重新填写实际sellout!");
			return false;
		}
		return true;
	}
})
</script>
<script type="text/javascript">
function backSorH(){
    h = $(window).height();
    t = $(document).scrollTop();
    if(t > h){
        $('#backtop').show();
    }else{
        $('#backtop').hide();
    }
}
$(document).ready(function(){
    //返回顶部
    backSorH();
    $('#backtop').click(function(){
        $(document).scrollTop(0);
    });
});
$(window).scroll(function(e){
    backSorH();
});
</script>
</html>