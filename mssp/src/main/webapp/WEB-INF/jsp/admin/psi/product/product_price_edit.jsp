<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-型号价格维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">型号价格维护</a></li>
        <li>编辑</li>
      </ol>
      <div>
        <form class="form-horizontal" action="${ROOT}/admin/psi/product/price/edit" method="post">
          <input type="hidden" name="id" value="${productPrice.id }" />
          <div class="form-group">
            <label class="control-label">产品型号：</label>
            <div class="controls">
              <input type="text" class="form-control text" readonly="readonly" name="name" value="${productPrice.product.name }" placeholder="产品型号">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">财报价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="financePrice" value="${productPrice.financePrice }" placeholder="财报价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">NET价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="netPrice" value="${productPrice.netPrice }" placeholder="NET价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">网吧公价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="interPublicPrice" value="${productPrice.interPublicPrice }" placeholder="网吧公价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">B2B公价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="b2bPublicPrice" value="${productPrice.b2bPublicPrice }" placeholder="B2B公价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">开票价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="poPrice" value="${productPrice.poPrice }" placeholder="开票价">
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
</body>
</html>