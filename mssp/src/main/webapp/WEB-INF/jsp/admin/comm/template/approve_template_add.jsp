<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-审批模板维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="${ROOT}/admin/comm/approvetemplate/list.html">审批模板列表</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form class="form-horizontal" action="${ROOT}/admin/comm/approvetemplate/post" method="post">
          <div class="form-group">
            <label class="control-label">模板名称：</label>
            <div class="controls">
              <input type="hidden" class="form-control text" name="id" value="${approveTemplate.id }">
              <input type="text" class="form-control text" id="approTempType" name="tempName" placeholder="模板名称" value="${approveTemplate.tempName }">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">模板类型：</label>
            <div class="controls">
   				<select class="select" name="type" id="typeSel">
              		<option value="">请选择</option>
	              	<c:forEach items="${listStatusValue}" var="itemRo">
						<option <c:if test="${itemRo.type==approveTemplate.type}">selected</c:if> value="${itemRo.type }">${itemRo.typeName }</option>
					</c:forEach>
            	</select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">描述：</label>
            <div class="controls">
              <textarea name="desc" class="col-md-6 form-control textarea" rows="3">${approveTemplate.desc }</textarea>
            </div>
          </div>
          <c:forEach items="${stepList}" var="item" varStatus="itemVs">
					<div class='form-group'>
				        <%-- <label class='control-label'>第<span id='step'>${itemVs.index+1}</span>步：</label> --%>
				        <div class='controls'>
					           <input type='text' class='form-control text' name='operateStatus' placeholder='步骤名称' value='${item.operateStatus}' />
					          操作值：  <input type='text'  class='form-control text' id='step' name='statusValue' value='${item.statusValue}' />
					           <input type='hidden' class='form-control text' name='processStepId' value='${item.id}' />
					           <input type='button' class='button button-blue' onClick='del(this);' value='删除' />
				        </div>
				    </div>
          </c:forEach>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return submitCheck();" />
              <input type="button" onclick="addStep(this)" class="button button-blue" value="新增步骤" />
            </div>
          </div>
        </form>
      </div>
    </div>
</div>
</div>
<script>
$(document).ready(function(){
	var row_count = ${rowCount==0?1:(rowCount+1)};
	//增加步骤
	addStep = function(dom){
		var html = "<div class='form-group'>"+
				        "<div class='controls'>"+
					           "<input type='text' class='form-control text' name='operateStatus' placeholder='步骤名称'> "+
					           "操作值：  <input type='text' class='form-control text' id='step' name='statusValue' value=''> "+
					           "<input type='hidden' class='form-control text' name='processStepId' value='0'> "+
					           "<input type='button' class='button button-blue' onClick='del(this);' value='删除'>"+
				        "</div>"+
				    "</div>"
		$(dom).parent().parent().before(html);
		row_count++;
	}
	//删除步骤
	del = function(dom){
		var pStepId = $(dom).prev().val();
		if(pStepId==0){
			$(dom).parent().parent().nextAll().each(function(){
					var index = parseInt($(this).find("#step").val());
					$(this).find("#step").val(index-1);
				});
				$(dom).parent().parent().remove();
				row_count--;
				return;
		}
 		$.ajax({
			type : "POST",
			url : "${ROOT}/admin/comm/processStep/delete",
			data : {
				pStepId:pStepId
			},
			dataType : "json",
			success : function(json) {
 				if(json.status=="OK"){
 					$(dom).parent().parent().nextAll().each(function(){
 						var index = parseInt($(this).find("#step").val());
 						$(this).find("#step").val(index-1);
 					});
 					$(dom).parent().parent().remove();
 					row_count--;
				}else{
					alert(json.errors[0].msg);
				}
			}
		});
	}
	
	//提交验证
	submitCheck = function(){
		var typeName = $("#approTempType").val();
		var typeSel = $("#typeSel option:selected").val();
		var submit = true;
		if(typeName==''){
			alert('模板名称不能为空！');
			$("#approTempType").focus();
			submit = false;
		}else if(typeSel==''){
			alert('请选择模板类型！');
			$("#typeSel").focus();
			submit = false;
		}
		$("input[name='operateStatus']").each(function(){
			var operateStatus = $(this).val();
			if(operateStatus==''){
				alert("步骤名称不能为空！");
				$(this).focus();
				submit = false;
				return false;
			}
		});
		return submit;
	}
})
</script>
</body>
</html>