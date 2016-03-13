<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-店面级别维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="${ROOT}/admin/iretail/storeLevel/list.html">店面级别维护</a></li>
        <li>${isNew?"新增":"编辑" }</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/iretail/storeLevel/save" method="post">
          <div class="form-group">
          	<input type="hidden" value="${iretailStoreLevel.id }" name="id" />
            <label class="control-label">级别名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="name" value="${iretailStoreLevel.name }" placeholder="级别名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">装修标准：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="decorateLevel" value="${iretailStoreLevel.decorateLevel }" placeholder="装修标准" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">支持金：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="supportMoney" value="${iretailStoreLevel.supportMoney }" placeholder="支持金" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">目标量：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="targetVolume" value="${iretailStoreLevel.targetVolume }" placeholder="目标量" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">积分：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="integralVolume" value="${iretailStoreLevel.integralVolume }" placeholder="积分" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" onclick="return valiForm()" value="提交">
            </div>
          </div>
        </form>
      </div>
    </div>
</div>
</div>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("#form").Validform({tiptype:2});  //就这一行代码！;
		
})
	
	//数字验证
	valiForm = function(){
		var supportMoney = $("input[name='supportMoney']").val();
		var targetVolume = $("input[name='targetVolume']").val();
		var integralVolume = $("input[name='integralVolume']").val();
		if(isNaN(parseInt(supportMoney))){
			alert("支持金必须为数字！");
			$("input[name='supportMoney']").val("");
			$("input[name='supportMoney']").focus();
			return false;
		}
		if(isNaN(parseInt(targetVolume))){
			alert("目标量必须为数字！");
			$("input[name='targetVolume']").val("");
			$("input[name='targetVolume']").focus();
			return false;
		}
		if(isNaN(parseInt(integralVolume))){
			alert("积分必须为数字！");
			$("input[name='integralVolume']").val("");
			$("input[name='integralVolume']").focus();
			return false;
		}
	}
</script>
</body>
</html>