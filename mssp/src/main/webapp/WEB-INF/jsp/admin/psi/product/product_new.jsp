<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-型号维护</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
      <ol class="breadcrumb">
        <li><a href="#">型号维护</a></li>
        <li>新增</li>
      </ol>
      <div>
        <form class="form-horizontal" action="${ROOT}/admin/psi/product/newSave.html" method="post">
          <div class="form-group">
            <label class="control-label">产品型号：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="name" placeholder="产品型号">
            </div>
          </div>
         <div class="form-group">
            <label class="control-label">产品规格：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="prodectFormat" placeholder="产品规格">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">寸别一：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="size1" placeholder="寸别一">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">寸别二：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="size2" placeholder="寸别二">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">财报价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="financePrice" placeholder="财报价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">NET价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="netPrice" placeholder="NET价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">网吧公价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="interPublicPrice" placeholder="网吧公价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">B2B公价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="b2bPublicPrice" placeholder="B2B公价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">开票价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="poPrice" placeholder="开票价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">产品系列：</label>
            <div class="controls">
              <select class="select" name="seriesId">
              	<c:forEach items="${commSeriesList}" var="item">
            		<option value="${item.id }">${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">零售产品系列：</label>
            <div class="controls">
              <select class="select" name="productSeriesId">
              	<c:forEach items="${productSeriesList}" var="item">
            		<option value="${item.id }">${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">面板：</label>
            <div class="controls">
              <select class="select" name="pannelId">
              	<c:forEach items="${pannelList}" var="item">
            		<option value="${item.id }">${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">资料：</label>
            <div class="controls">
              <textarea name="material" class="col-md-6 form-control textarea" rows="3"></textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">状态：</label>
            <div class="controls">
              <select class="select" name="status">
                <option value="1">激活</option>
                <option value="0">EOL</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">出货类型：</label>
            <div class="controls">
              <select class="select" name="channelType">
                <option value="1">飞生</option>
                <option value="2">越海</option>
                <option value="3">飞生/越海</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">销售类型：</label>
            <div class="controls">
              <select class="select" name="sellType">
                <option value="1">网吧型号</option>
                <option value="2">行业型号</option>
                <option value="3">零售型号</option>
              </select>
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