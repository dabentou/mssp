<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8" />
  <title>特殊支持申请</title>
    <%@include file="/WEB-INF/include/include.jsp" %>
</head>
<body>
	<%@include file="/WEB-INF/include/header.jsp" %>
 	<%@include file="/WEB-INF/include/func.jsp" %>
  <div class="wrap">
     <%@include file="/WEB-INF/include/notice.jsp" %>
    <div class="content">
      <form id="form" class="form-horizontal" action="${ROOT }/b2i/customer/save" method="post">
      	 <div class="form-group">
            <label class="control-label">客户名称：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="name" datatype="s1-18"/>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">联系人：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="person" datatype="s1-18" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">手机号码：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="mobile" datatype="m" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">电话号码：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="phone" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">邮政编码：</label>
            <div class="controls">
              <input type="text" class="text form-control" name="zipcode" datatype="p" />
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">地址：</label>
            <div class="controls">
              <textarea class="col-md-6 form-control textarea" rows="3" name="address"></textarea>
            </div>
          </div>
          <div class="form-group form-group-btn">
            <div class="controls">
              <input type="submit" class="button button-lightblue" value="确定提交">
            </div>
          </div>
      </form>
    </div>
  </div>
</body>
<script src="${ROOT}/js/validform_v5.3.2_min.js"></script>
<script type="text/javascript">
$(function(){
	$("#form").Validform({tiptype:2});
})
</script>
</html>