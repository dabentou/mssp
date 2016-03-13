<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>飞利浦显示器-销售预估模板上传</title>
<%@include file="/WEB-INF/include/include.jsp" %>
<body>
<%@include file="/WEB-INF/include/_left.jsp" %>
<div class="mm-page">
<%@include file="/WEB-INF/include/_top.jsp" %>
<div class="main-container">
   <div class="container-fluid">
   	  <div class="operate-search">
   	  	<a href="#" class="button button-blue" <c:if test="${!isSubmit }">data-toggle="modal" data-target="#myModal"</c:if> <c:if test="${isSubmit }">onClick="alert('本月已上传销售预估模板！');"</c:if> ><i class="icon icon11"></i>上传销售预估模板</a>
   	  </div>
      <table class="table table-hover mytable">
          <thead>
              <tr>
                  <th>序号</th>
                  <th>预估模板名称</th>
                  <th>创建时间</th>
                  <th>操作</th>
              </tr>
          </thead>
          <tbody>
          <c:forEach items="${pagelist.content}" var="item">
              <tr>
                  <td>${item.id}</td>
                  <td>${item.name}</td>
                  <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                  <td><a href="${ROOT}/admin/psi/einfo/view-${item.id}" class="operate operate1"><i></i>查看模板详情</a></td>
              </tr>
          </c:forEach>
          </tbody>
      </table>
      <div class="page">
		<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
		<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
	  </div> 
     <!--  <div>
        <form class="form-horizontal" action="" method="post" enctype="multipart/form-data">
          <div class="form-group">
            <label class="control-label">销售目标模板：</label>
            <div class="controls">
              <input type="file" id="sellTargetFile" name="sellTargetFile"/>
            </div>
          </div>
          <div class="form-group">
            <div class="controls">
              <input type="submit" class="button button-blue" value="提交" onclick="return ajaxSellTarget();">
            </div>
          </div>
        </form>
      </div> -->
    </div>
</div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
  <form action="" method="post" enctype="multipart/form-data">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">上传销售预估模板</h4>
      </div>
      <div class="modal-body">
        <input type="file" id="sellFile" name="sellFile"/>
      </div>
      <div class="modal-footer">
        <input type="button" class="button button-blue" value="导入" onclick="return ajaxSellEstimateTemplate();" />
      </div>
    </div>
    </form>
  </div>
</div>
<script>
function ajaxSellEstimateTemplate()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/comm/upload/sell",
			secureuri:false,
			fileElementId:'sellFile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status=='OK') {
				    	alert("导入成功！");
				    	window.location.reload();
				  }else{
				    	alert(dom.errors[0].msg+"需要维护到型号中！");
				  }
			}
		}
	)
	return false;
}
</script>
</body>
</html>