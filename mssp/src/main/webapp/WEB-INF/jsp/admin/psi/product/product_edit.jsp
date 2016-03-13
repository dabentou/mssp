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
        <li>编辑</li>
      </ol>
      <div>
        <form class="form-horizontal" action="${ROOT}/admin/psi/product/editSave.html" method="post">
          <input type="hidden" name="id" value="${product.id }" />
          <div class="form-group">
            <label class="control-label">产品型号：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="name" value="${product.name }" placeholder="产品型号">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">产品规格：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="prodectFormat" value="${product.prodectFormat }" placeholder="产品规格">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">寸别一：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="size1" value="${product.size1 }" placeholder="寸别一">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">寸别二：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="size2" value="${product.size2 }" placeholder="寸别二">
            </div>
          </div>
<%--           <div class="form-group">
            <label class="control-label">产品公价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="publicPrice" value="${product.publicPrice }" placeholder="产品公价">
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">开票价：</label>
            <div class="controls">
              <input type="text" class="form-control text" name="poPrice" value="${product.poPrice }" placeholder="开票价">
            </div>
          </div> --%>
          <div class="form-group">
            <label class="control-label">产品系列：</label>
            <div class="controls">
              <select class="select" name="series_id">
              	<option value="-1" <c:if test="${empty product.commSeries}"> selected="selected"</c:if> ></option>
              	<c:forEach items="${commSeriesList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == product.commSeries.id}">selected="selected"</c:if> >${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">零售产品系列：</label>
            <div class="controls">
              <select class="select" name="product_series_id">
              	<option value="-1" <c:if test="${empty product.productSeries}"> selected="selected"</c:if> ></option>
              	<c:forEach items="${productSeriesList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == product.productSeries.id}">selected="selected"</c:if> >${item.name }</option>
            	</c:forEach>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">面板：</label>
            <div class="controls">
              <select class="select" name="pannel_id">
              	<option value="-1" <c:if test="${empty product.pannel}"> selected="selected"</c:if> ></option>
              	<c:forEach items="${pannelList}" var="item">
            		<option value="${item.id }" <c:if test="${item.id == product.pannel.id}">selected="selected"</c:if> >${item.name }</option>
            	</c:forEach>
            	
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">资料：</label>
            <div class="controls">
              <textarea name="material" class="col-md-6 form-control textarea" rows="3">${product.material }</textarea>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">状态：</label>
            <div class="controls">
              <select class="select" name="status">
              	<c:if test="${item.status == 0}">selected="selected"</c:if>
                <option value="1" <c:if test="${item.status == 1}">selected="selected"</c:if> >激活</option>
                <option value="0" <c:if test="${item.status == 0}">selected="selected"</c:if>>EOL</option>
              </select>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">类型：</label>
            <div class="controls">
              <select class="select" name="channelType">
              	<option value="1" <c:if test="${product.channelType == 1}">selected="selected"</c:if> >飞生</option>
              	<option value="2" <c:if test="${product.channelType == 2}">selected="selected"</c:if> >越海</option>
              	<option value="3" <c:if test="${product.channelType == 3}">selected="selected"</c:if> >飞生/越海</option>
              </select>
            </div>
          </div>
         <div class="form-group">
            <label class="control-label">行业类型：</label>
            <div class="controls">
              <select class="select" name="sellType">
	          		<option value="1" <c:if test="${product.sellType == 1}">selected="selected"</c:if> >网吧型号</option>
	              	<option value="2" <c:if test="${product.sellType == 2}">selected="selected"</c:if> >行业型号</option>
	              	<option value="3" <c:if test="${product.sellType == 3}">selected="selected"</c:if> >零售型号</option>
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