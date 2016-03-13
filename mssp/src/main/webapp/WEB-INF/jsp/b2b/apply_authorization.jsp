<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>授权申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      	
      	<form class="form-horizontal" id = "form"  action="${ROOT}/a/input" enctype="multipart/form-data"  method="post" >

				<div class="form-group">
					<label class="control-label">导入类型：</label>
					<div class="controls">
						<input type = "text" class="select form-control"  placeholder="代理商承诺函"  name="channelType"> 
					</div>
				</div>


				<div class="form-group">
				<label class="control-label">选择文件：</label>
				<div class="controls">
					<input type="file" id="file" name="file" />
				</div>
			</div>
				
					<div class="controls">
						<input type="submit" id="submit" class="button button-lightblue"
							value="确定提交" />
					</div>
				
			

			</form>

			<img src = "${img}"/>
		</div>
      	
      	
    </div>
  </div>
</body>
</html>