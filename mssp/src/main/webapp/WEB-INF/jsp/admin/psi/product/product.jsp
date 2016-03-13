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
        <div class="row">
            <div class="col-lg-12">
                <div class="operate-search">
                    <div class="pull-left">
                    	<a href="${ROOT}/admin/psi/product/new.html" class="button button-blue"><i class="icon icon11"></i>新增</a>
                    	<a href="#" style="margin-left:10px;" class="button button-blue" <c:if test="${!isSubmit }">data-toggle="modal" data-target="#myModal"</c:if> <c:if test="${isSubmit }">onClick="alert('本月已导入型号价格！');"</c:if> ><i class="icon icon11"></i>导入型号价格</a>
                    </div>
                    <form class="form-inline" action="${ROOT}/admin/psi/product/search">
                      <div class="form-group">
                        <label>产品型号：</label>
                        <input type="text" class="form-control text" name="name" placeholder="产品型号">
                      </div>
                      <input type="hidden" name="seriesId" id="hidden-series" />
                      <input type="hidden" name="productSeriesId" id="hidden-productSeries" />
                      <input type="hidden" name="pannelId" id="hidden-pannel" />
                      <input type="submit" class="button button-blue" value="查询" />
                    </form>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover mytable">
                        <thead>
                            <tr>
                                <th>序号</th>
                                <th>产品型号</th>
                                <!-- <th>产品规格</th> -->
                                <th>寸别一</th>
                                <th>寸别二</th>
      <!--                           <th>产品公价</th>
                                <th>开票价</th> -->
                                <th>
                                  <select id="select-series" onchange="changeSelect(this)">
                                  	<option value="">产品系列</option>
					              	<c:forEach items="${commSeriesList}" var="item">
					            		<option value="${item.id }" <c:if test="${item.id == product.commSeries.id}">selected="selected"</c:if> >${item.name }</option>
					            	</c:forEach>
					              </select>
                                </th>
                                <th>
                                  <select id="select-productSeries" onchange="changeSelect(this)">
                                  	<option value="">零售产品系列</option>
					              	<c:forEach items="${productSeriesList}" var="item">
					            		<option value="${item.id }" <c:if test="${item.id == product.commSeries.id}">selected="selected"</c:if> >${item.name }</option>
					            	</c:forEach>
					              </select>
                                </th>
                                <th>
                                  <select id="select-pannel" onchange="changeSelect(this)">
                                  	<option value="">面板</option>
					              	<c:forEach items="${pannelList}" var="item">
					            		<option value="${item.id }" <c:if test="${item.id == product.commSeries.id}">selected="selected"</c:if> >${item.name }</option>
					            	</c:forEach>
					              </select>
                                </th>
                                <th>资料</th>
                                <th>渠道类型</th>
                                <th>行业类型</th>
                               <!--  <th>型号日期</th> -->
                                <th>状态</th>
                                <th>创建时间</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagelist.content}" var="item">
                            <tr>
                                <td>${item.id}</td>
                                <td>${item.name}</td>
                                <%-- <td>${item.prodectFormat}</td> --%>
                                <td>${item.size1}</td>
                                <td>${item.size2}</td>
<%--                                 <td>${item.publicPrice}</td>
                                <td>${item.poPrice}</td> --%>
                                <td>${item.commSeries.name}</td>
                                <td>${item.productSeries.name}</td>
                                <td>${item.pannel.name}</td>
                                <td>${item.material}</td>
                                <td>${item.channelType==1?'飞生':(item.channelType==2?'越海':(item.channelType==3?'飞生&越海':''))}</td>
                                <td>${item.sellType==1?'网吧型号':(item.sellType==2?'行业型号':(item.sellType==3?'零售型号':''))}</td>
                          <%--       <td><fmt:formatDate pattern="yyyy-MM" value="${item.productDate}"></fmt:formatDate></td> --%>
                                <td><c:if test="${item.status == 0}">EOL</c:if><c:if test="${item.status == 1}">激活</c:if></td>
                                <td><fmt:formatDate value="${item.createtime}" type="both" /></td>
                                <td>
                                	<a href="${ROOT}/admin/psi/product/edit-${item.id}" class="operate operate1"><i></i>编辑</a>
                                	<a href="${ROOT}/admin/psi/product/listPrice?id=${item.id}" class="operate operate1"><i></i>查看价格</a>
                                	<a href="javascript:void(0);" onclick="deleteSeries('${item.id}','${item.name }')" class="operate operate2"><i></i>删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="page">
						<p class="pull-left">当前显示<span>${pagelist.from+1}-${pagelist.from+50}</span>,共<span>${pagelist.totalElements}</span>条记录</p>
						<ms:page from="${pagelist.from}" size="${pagelist.size}" totle="${pagelist.totalElements}" url="${REQUEST_URI}?page={0}"></ms:page>
					</div> 
            </div>
        </div>
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
        <h4 class="modal-title" id="myModalLabel">导入型号资料</h4>
      </div>
      <div class="modal-body">
        <input type="file" name="pfile" id="pfile" />
      </div>
      <div class="modal-footer">
        <input type="button" class="button button-blue" value="导入" onclick="return ajaxUploadProduct();" />
      </div>
    </div>
    </form>
  </div>
</div>
<script>
$(document).ready(function(){
	$("input[id^=hidden]").each(function(index, element) {
		var mid=element.id.split("-")[1];
		var value=$("input[id$="+mid+"]").val();
		$("select[id$="+mid+"]").val(value);
    });
})
function changeSelect(obj){
	var mid=obj.id.split("-")[1];
	$("input[id$="+mid+"]").val(obj.value);
}
function deleteSeries(id,name){
	if(confirm("确定要删除"+name+"吗？")){
		 location.href = "${ROOT}/admin/psi/product/delete?id="+id;
	};
}
function ajaxUploadProduct()
{
	$.ajaxFileUpload
	(
		{
			url:"${ROOT}/admin/psi/upload/product",
			secureuri:false,
			fileElementId:'pfile',
			dataType: 'json',
			success: function (dom)
			{
				 if (dom.status=='OK') {
				    	alert("导入成功");
				    	//$('#myModal').modal('hide');
				    	window.location.reload();
				  }else{
				    	alert(dom.errors);
				  }
			}
		}
	)
	return false;
}
</script>
</body>
</html>