<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-申请模板维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">申请模板维护</a></li>
        <li>${isNew?"新增":"编辑" }</li>
      </ol>
      <div>
        <form id="form" class="form-horizontal" action="${ROOT}/admin/b2i/template/save" enctype="multipart/form-data" method="post">
          <div class="form-group">
          	<input type="hidden" value="${b2iApplyTemplate.id }" name="id" />
            <label class="control-label">模板名称：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="name" value="${b2iApplyTemplate.name }" placeholder="模板名称" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">模板样式：</label>
            <div class="controls">
      				<input type="file" id="emailFile"  name="imgFile" onchange="uploadImg(this,'emailFile','email_view','email_area','one')" accept="image/gif,image/jpeg,image/png"/>
      				<input type="hidden" id="email_area" name="templateImg"/>
      				<div id="email_view">
      					<c:if test="${b2iApplyTemplate.templateImg !=null }">
      						<img title='重复上传可替换' width=400 src='${b2iApplyTemplate.templateImg }'> 
      					</c:if>
                    </div>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交">
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
	
	
	/* 
	上传图片  type参数，one表示重复上传可替换，many表示重复上传可添加
	*/
	uploadImg = function(dom,inputId,viewId,areaId,type){
		$.ajaxFileUpload({
			type : "POST",
			url : "${ROOT}/editor/image/upload",
			secureuri:false,  
		    fileElementId:inputId,//文件选择框的id属性 
			dataType : "json",
			success : function(json){
				if(json.error==0){
					var uv = $("#"+viewId);
					var url ="http://" + window.location.host+json.url;
					if(type=='one'){
						$("#"+areaId).val(url);
						uv.html("<img title='重复上传可替换' width=400 src='" + url + "'> ");
					}else{
						$("#"+areaId).append(url+",");
						uv.append("<img ondblclick='delImg(this,\""+areaId+"\",\""+url+"\")' title='双击删除' width=400 src='" + url + "'> ");
					}
				}else{
					alert(json.error);
				}
			}
		});
	}
		
})
</script>
</body>
</html>